import sys
import os
import re
import json
from langchain_ollama import ChatOllama
from langchain_core.prompts import ChatPromptTemplate
from langchain_core.output_parsers import JsonOutputParser
from pydantic import BaseModel, Field
from langchain_huggingface import HuggingFaceEmbeddings
from langchain_chroma import Chroma
from langchain_core.documents import Document
from typing import Literal


os.environ['CURL_CA_BUNDLE'] = ''
os.environ['REQUESTS_CA_BUNDLE'] = ''
os.environ['HF_HUB_DISABLE_SSL_VERIFY'] = '1'

# 1. Forçar encoding UTF-8 para integração limpa com o ProcessBuilder do Java
sys.stdout.reconfigure(encoding='utf-8')

# 2. Ingestão da Transcrição Dinâmica (Enviada pelo Spring Boot via arquivo temporário)
if len(sys.argv) > 1:
    caminho_arquivo = sys.argv[1]
    try:
        with open(caminho_arquivo, 'r', encoding='utf-8') as f:
            transcricao_bruta = f.read()
    except Exception as e:
        transcricao_bruta = f"Erro ao ler o arquivo: {e}"
else:
    transcricao_bruta = "Teste falhou: Nenhum caminho de arquivo foi enviado pelo back-end."


# Filtro de extração e limpeza cirúrgica
try:
    dados = json.loads(transcricao_bruta)
    texto_limpo = dados.get("ANON_TRANSCRICAO", transcricao_bruta)
except Exception:
    match = re.search(r'"ANON_TRANSCRICAO"\s*:\s*"(.*?)"\s*,\s*"UF"', transcricao_bruta, re.DOTALL)
    if match:
        texto_limpo = match.group(1)
    else:
        texto_limpo = transcricao_bruta

# Remove os marcadores de locutor e quebras de linha literais
texto_limpo = re.sub(r'\[LOCUTOR \d+\]:', '', texto_limpo)
texto_limpo = texto_limpo.replace('\\n', ' ').strip()

# 3. Base de Conhecimento RAG (Catálogo TOTVS Enriquecido)
catalogo = [
    Document(page_content="TOTVS RM: Ideal para gestão de RH, folha de pagamento, controle de ponto (ClockIn), Meu RH e SESMT."),
    Document(page_content="TOTVS Protheus: ERP completo para manufatura pesada, varejo e backoffice robusto (financeiro/faturamento)."),
    Document(page_content="TOTVS Fluig: Plataforma de produtividade, BPM, aprovações, gestão de documentos e portais internos."),
    Document(page_content="TOTVS Logix: Solução especialista EXCLUSIVA para logística, WMS, expedição e transporte.")
]

# 4. Motor RAG (Busca Vetorial Ampliada k=3)
vetorizador = HuggingFaceEmbeddings(model_name="all-MiniLM-L6-v2")
vector_store = Chroma.from_documents(catalogo, vetorizador)
resultados_rag = vector_store.similarity_search(texto_limpo, k=3)
contexto_rag = "\n".join([doc.page_content for doc in resultados_rag])

# 5. Modelagem Pydantic (Travas Absolutas)
class AnaliseIATarget(BaseModel):
    raciocinio: str = Field(
        description="Foque APENAS em software, ignore conversas sobre e-mails ou propostas perdidas. Passo a passo: 1) Qual sistema o cliente usa hoje na operação? 2) Qual é a falha operacional exata (dor técnica)? 3) Qual produto do catálogo substitui esse sistema atual? 4) Houve menção a suporte ruim?"
    )
    riscoChurn: Literal["BAIXO", "MEDIO", "ALTO"] = Field(
        description="Se o cliente citar QUALQUER atraso no suporte (ex: 'demorou dias'), retorne MEDIO. Use BAIXO apenas se o suporte for impecável."
    )
    oportunidadeUpsell: Literal["NENHUMA", "UPSELL", "CROSS_SELL", "RETENCAO"] = Field(
        description="Se o cliente vai trocar um sistema 'desenvolvido internamente' ou de um 'concorrente' por uma nova solução da TOTVS, retorne CROSS_SELL. Se for apenas renovar, retorne RETENCAO."
    )
    produtoRecomendado: str = Field(
        description="NOME OFICIAL do produto TOTVS que resolve a dor do cliente. Se a dor for RH/Ponto, escolha TOTVS RM. Se a dor for Logística, escolha TOTVS Logix. Nunca invente nomes."
    )
    sentimentoGeral: Literal["POSITIVO", "NEUTRO", "NEGATIVO"] = Field(
        description="Classifique como POSITIVO se houver agendamento de reunião/demo ou orçamento aprovado. Use NEGATIVO apenas se o cliente se recusar a avançar na negociação."
    )
    orcamentoEstimado: float = Field(
        description="Valor financeiro do orçamento (ex: 90000.0). Se não houver, 0.0."
    )

parser = JsonOutputParser(pydantic_object=AnaliseIATarget)
llm = ChatOllama(model="qwen2.5", temperature=0.0, format="json")

# 6. Prompt Restritivo
prompt = ChatPromptTemplate.from_messages([
    ("system", "Você é um especialista em inteligência comercial da TOTVS. "
               "Baseie a recomendação do produto EXCLUSIVAMENTE nestas opções do catálogo: '{contexto_rag}'\n\n"
               "--- EXEMPLO DE RACIOCÍNIO (NÃO COPIE OS VALORES ABAIXO) ---\n"
               "Transcrição: 'Portal interno ruim. Pensamos no TOTVS RM. Temos 50 mil.'\n"
               "Saída Esperada: {{\"raciocinio\": \"Cliente tem dor no RH e reclamou do portal. O catálogo indica TOTVS RM. Não há reclamação da TOTVS, risco baixo.\", \"riscoChurn\": \"BAIXO\", \"oportunidadeUpsell\": \"CROSS_SELL\", \"produtoRecomendado\": \"TOTVS RM\", \"sentimentoGeral\": \"POSITIVO\", \"orcamentoEstimado\": 50000.0}}\n"
               "-----------------------------\n"),
    ("human", "Transcrição da Reunião real:\n{transcricao}\n\n"
              "CRÍTICO: Retorne APENAS um JSON válido. Preencha o campo 'raciocinio' primeiro.\n{format_instructions}")
])

# Processamento Silencioso (O Java ignorará tudo até achar a primeira '{')
print("Processando busca semântica e análise LLM no Qwen 2.5...")
resultado = (prompt | llm | parser).invoke({
    "transcricao": texto_limpo,
    "contexto_rag": contexto_rag,
    "format_instructions": parser.get_format_instructions()
})

print("\n")
print(json.dumps(resultado, indent=4, ensure_ascii=False))