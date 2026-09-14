# TOTVS Meeting Insights — DDD

Plataforma de inteligência conversacional corporativa desenvolvida para análise automatizada de transcrições de reuniões comerciais, identificação de gaps operacionais, cálculo de risco de churn e recomendação de soluções do ecossistema TOTVS.

---

## 🛠️ Pré-requisitos do Ambiente

Antes de inicializar o ecossistema, certifique-se de possuir instalado na máquina:

* **Java JDK 17+** (com suporte a Spring Boot 3)
* **Python 3.10+** (com gerenciador `pip`)
* **Ollama** ([Download oficial](https://ollama.com/download))
* **Acesso à rede/internet** (para conexão com o banco Oracle FIAP)

---

## 🚀 Passo a Passo para Execução da IA e Agente

### 1. Inicializar o Modelo de Linguagem Local (LLM)
O projeto utiliza o modelo **Qwen 2.5** executado localmente via Ollama para garantir privacidade de dados sensíveis e custo zero de inferência.

Abra o primeiro terminal e baixe/inicie o modelo:
```bash
pip install langchain langchain-ollama pydantic
pip install langchain-chroma langchain-huggingface sentence-transformers
ollama run qwen2.5

Nota: Mantenha este terminal aberto em segundo plano com o serviço do Ollama ativo.

```
## ☕ Execução do Backend Java (Spring Boot)
O backend centraliza a segurança, regras de negócio e a persistência relacional no Oracle DB.

Iniciar a Aplicação Web:

Execute a classe principal br.com.meetinginsights.app.Main ou utilize o Maven Wrapper:

```bash
./mvnw spring-boot:run
O servidor inicializará na porta padrão: http://localhost:8080.
```

🔄 Fluxo de Integração Integrado

Terminal 1: ollama run qwen2.5 (Serviço de inferência local).

Terminal 2: Main.java (API Spring Boot conectada ao banco de dados Oracle).

---

## 💻 Aplicação Frontend (Interface do Usuário)

* **Repositório do Frontend:** [Disponível em: GitHub](https://github.com/MatheusFVieira/SiteTOTVS)
* **URL Local Padrão:** `http://localhost:8081`

### 📦 Pré-requisitos
* **Node.js** (versão 18 ou superior)
* Gerenciador de pacotes **npm** ou **pnpm**

### ⚙️ Instalação e Execução
1. Abra um **quarto terminal** na pasta raiz do projeto frontend.
2. Instale as dependências:

```bash
   npm install
   npm run dev
```

Acesse a aplicação no navegador em: http://localhost:8081

## 🧭 Como Usar a Plataforma
### Login e Identificação:

Acesse a tela de login ou selecione seu usuário no menu inferior da barra lateral. Toda a navegação e carteira de clientes se adaptarão dinamicamente ao vendedor conectado.

### Gestão de Carteira de Clientes:

Acesse a aba Clientes para visualizar as empresas associadas à sua gestão.

Clique em Novo Cliente para cadastrar uma nova conta corporativa (CNPJ, Razão Social, Segmento e E-mail corporativo). O registro é gravado no Oracle DB com vínculo automático ao seu ID de vendedor.

### Envio e Análise de Transcrições (IA):

Navegue até a aba Transcrições.

Selecione o cliente desejado e cole a transcrição da reunião.

Clique em Analisar com IA. O script Python em conjunto com o Qwen 2.5 processará o texto, identificará o sentimento, o risco de churn e sugerirá o produto TOTVS adequado (Protheus, RM, Fluig ou Logix).

### Plano de Ação Comercial (Tarefas):

Vá para a aba Tarefas para inspecionar os cartões gerados pela IA.

Clique em qualquer ação sugerida para abrir o modal de diagnóstico com detalhes sobre o orçamento estimado, nível de risco e direcionamento estratégico de contato com o cliente.