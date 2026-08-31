package br.com.meetinginsights.domain;

import br.com.meetinginsights.domain.enums.NivelRisco;
import br.com.meetinginsights.domain.enums.Sentimento;
import br.com.meetinginsights.domain.enums.TipoOportunidade;

public class AnaliseIA {
    private int idAnalise;
    private int idTranscricao;
    private NivelRisco riscoChurn;
    private TipoOportunidade oportunidadeUpsell;
    private Sentimento sentimentoGeral;
    private Double orcamentoEstimado;

    public AnaliseIA(int idTranscricao, NivelRisco riscoChurn, TipoOportunidade oportunidadeUpsell, Sentimento sentimentoGeral, Double orcamentoEstimado) {
        this.idTranscricao = idTranscricao;
        this.riscoChurn = riscoChurn;
        this.oportunidadeUpsell = oportunidadeUpsell;
        this.sentimentoGeral = sentimentoGeral;
        this.orcamentoEstimado = orcamentoEstimado;
    }

    public NivelRisco getRiscoChurn() { return riscoChurn; }
    public Double getOrcamentoEstimado() { return orcamentoEstimado; }
}