package br.com.meetinginsights.repository;

import br.com.meetinginsights.domain.Transcricao;

import java.util.ArrayList;
import java.util.List;

public class TranscricaoRepository {

    private List<Transcricao> transcricoes = new ArrayList<>();
    private int proximoId = 1;

    public Transcricao salvar(Transcricao transcricao) {
        transcricao.setId(proximoId);
        proximoId++;
        transcricoes.add(transcricao);
        return transcricao;
    }

    public List<Transcricao> listarTodas() {
        return transcricoes;
    }

    public Transcricao buscarPorId(int id) {
        for (Transcricao transcricao : transcricoes) {
            if (transcricao.getId() == id) {
                return transcricao;
            }
        }
        return null;
    }
}