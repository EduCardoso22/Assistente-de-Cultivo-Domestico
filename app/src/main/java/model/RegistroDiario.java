package model;

import java.time.LocalDate;

public class RegistroDiario {
    private LocalDate data;
    private String tipoAtividade; // "REGA", "ADUBACAO", "PODA"
    private String observacoes;

    public RegistroDiario(LocalDate data, String tipoAtividade, String observacoes) {
        this.data = data;
        this.tipoAtividade = tipoAtividade;
        this.observacoes = observacoes;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getTipoAtividade() {
        return tipoAtividade;
    }

    public void setTipoAtividade(String tipoAtividade) {
        this.tipoAtividade = tipoAtividade;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    @Override
    public String toString() {
        return "[" + data + "] " + tipoAtividade + " - " + observacoes;
    }
}
