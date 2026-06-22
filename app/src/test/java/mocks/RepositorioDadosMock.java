package mocks;

import model.Especie;
import model.Planta;
import repository.IDadosRepository;

import java.util.ArrayList;
import java.util.List;

public class RepositorioDadosMock implements IDadosRepository {
    private final List<Planta> plantas = new ArrayList<>();
    private final List<Especie> especies = new ArrayList<>();
    public boolean salvouDados = false;
    public boolean carregouDados = false;

    @Override
    public void salvarPlanta(Planta planta) {
        plantas.removeIf(p -> p.getApelido().equalsIgnoreCase(planta.getApelido()));
        plantas.add(planta);
    }

    @Override
    public List<Planta> obterTodasPlantas() {
        return plantas;
    }

    @Override
    public void salvarEspecie(Especie especie) {
        especies.removeIf(e -> e.getNome().equalsIgnoreCase(especie.getNome()));
        especies.add(especie);
    }

    @Override
    public List<Especie> obterTodasEspecies() {
        return especies;
    }

    @Override
    public void salvarDados() {
        salvouDados = true;
    }

    @Override
    public void carregarDados() {
        carregouDados = true;
    }
}
