package mocks;

import java.util.ArrayList;
import java.util.List;

public class EmissaoAlertasMock {
    private final List<String> alertasSimulados = new ArrayList<>();

    public void adicionarAlerta(String alerta) {
        alertasSimulados.add(alerta);
    }

    public List<String> getAlertasSimulados() {
        return alertasSimulados;
    }

    public boolean contemAlertaPara(String apelido) {
        return alertasSimulados.stream().anyMatch(a -> a.contains(apelido));
    }
}
