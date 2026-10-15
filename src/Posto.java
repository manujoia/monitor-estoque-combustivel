import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Posto {
    private final String nome;
    private final String cidade;
    private final List<Tanque> tanques = new ArrayList<>();

    public Posto(String nome, String cidade) {
        this.nome = nome;
        this.cidade = cidade;
    }

    public String getNome() { return nome; }
    public String getCidade() { return cidade; }
    public List<Tanque> getTanques() { return tanques; }

    public void adicionarTanque(Tanque tanque) {
        tanques.add(tanque);
    }

    public Optional<Tanque> buscarTanque(TipoCombustivel tipo) {
        return tanques.stream().filter(t -> t.getTipo() == tipo).findFirst();
    }
}
