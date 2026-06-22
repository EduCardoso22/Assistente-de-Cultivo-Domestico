package repository;

import model.Especie;
import model.Planta;
import java.util.List;

public interface IDadosRepository {
    void salvarPlanta(Planta planta);
    List<Planta> obterTodasPlantas();
    void salvarEspecie(Especie especie);
    List<Especie> obterTodasEspecies();
    void salvarDados();
    void carregarDados();
}
