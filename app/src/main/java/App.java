import repository.ArquivoDadosRepository;
import repository.IDadosRepository;
import service.CultivoService;
import ui.MenuPrompt;

public class App {
    public static void main(String[] args) {
        IDadosRepository repository = new ArquivoDadosRepository("dados.txt");
        CultivoService service = new CultivoService(repository);
        service.inicializarDados();
        MenuPrompt menu = new MenuPrompt(service);
        menu.iniciar();
    }
}
