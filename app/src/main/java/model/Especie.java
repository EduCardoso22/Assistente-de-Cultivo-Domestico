package model;

public class Especie {
    private String nome;
    private int intervaloRegaDias;
    private int intervaloAdubacaoDias;
    private int intervaloPodaDias;

    public Especie(String nome, int intervaloRegaDias, int intervaloAdubacaoDias, int intervaloPodaDias) {
        this.nome = nome;
        this.intervaloRegaDias = intervaloRegaDias;
        this.intervaloAdubacaoDias = intervaloAdubacaoDias;
        this.intervaloPodaDias = intervaloPodaDias;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIntervaloRegaDias() {
        return intervaloRegaDias;
    }

    public void setIntervaloRegaDias(int intervaloRegaDias) {
        this.intervaloRegaDias = intervaloRegaDias;
    }

    public int getIntervaloAdubacaoDias() {
        return intervaloAdubacaoDias;
    }

    public void setIntervaloAdubacaoDias(int intervaloAdubacaoDias) {
        this.intervaloAdubacaoDias = intervaloAdubacaoDias;
    }

    public int getIntervaloPodaDias() {
        return intervaloPodaDias;
    }

    public void setIntervaloPodaDias(int intervaloPodaDias) {
        this.intervaloPodaDias = intervaloPodaDias;
    }

    @Override
    public String toString() {
        return nome + " (Rega: " + intervaloRegaDias + "d, Adubação: " + intervaloAdubacaoDias + "d, Poda: " + intervaloPodaDias + "d)";
    }
}
