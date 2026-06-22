package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Planta {
    private String apelido;
    private Especie especie;
    private LocalDate dataAquisicao;
    private List<RegistroDiario> historicoAtividades;

    public Planta(String apelido, Especie especie, LocalDate dataAquisicao) {
        this.apelido = apelido;
        this.especie = especie;
        this.dataAquisicao = dataAquisicao;
        this.historicoAtividades = new ArrayList<>();
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public LocalDate getDataAquisicao() {
        return dataAquisicao;
    }

    public void setDataAquisicao(LocalDate dataAquisicao) {
        this.dataAquisicao = dataAquisicao;
    }

    public List<RegistroDiario> getHistoricoAtividades() {
        return historicoAtividades;
    }

    public void setHistoricoAtividades(List<RegistroDiario> historicoAtividades) {
        this.historicoAtividades = historicoAtividades;
    }

    public void adicionarAtividade(RegistroDiario registro) {
        this.historicoAtividades.add(registro);
    }

    public LocalDate getUltimaDataAtividade(String tipoAtividade) {
        return historicoAtividades.stream()
                .filter(r -> r.getTipoAtividade().equalsIgnoreCase(tipoAtividade))
                .map(RegistroDiario::getData)
                .max(LocalDate::compareTo)
                .orElse(dataAquisicao); // Se nunca foi feito, assume a data de aquisição
    }

    @Override
    public String toString() {
        return apelido + " [" + especie.getNome() + "] - Adquirida em: " + dataAquisicao;
    }
}
