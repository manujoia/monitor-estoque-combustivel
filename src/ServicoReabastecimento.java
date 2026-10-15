import java.util.ArrayList;
import java.util.List;

public class ServicoReabastecimento {
    // Regras de negócio simples e ajustáveis
    public static final double LIMITE_DIAS_ALERTA = 3.0;
    public static final double PERCENTUAL_ALVO = 0.90;

    // Gera uma linha de pedido para cada tanque abaixo do limite de autonomia
    public List<String> gerarPedidos(List<Posto> postos) {
        List<String> pedidos = new ArrayList<>();
        for (Posto posto : postos) {
            for (Tanque tanque : posto.getTanques()) {
                if (tanque.precisaReabastecer(LIMITE_DIAS_ALERTA)) {
                    pedidos.add(String.format(
                        "%s (%s) | %s | autonomia %.1f dia(s) | pedir %.0f L",
                        posto.getNome(), posto.getCidade(),
                        tanque.getTipo().getNome(),
                        tanque.diasDeAutonomia(),
                        tanque.litrosParaReabastecer(PERCENTUAL_ALVO)));
                }
            }
        }
        return pedidos;
    }

    public double totalLitrosPedidos(List<Posto> postos) {
        double total = 0;
        for (Posto posto : postos) {
            for (Tanque tanque : posto.getTanques()) {
                if (tanque.precisaReabastecer(LIMITE_DIAS_ALERTA)) {
                    total += tanque.litrosParaReabastecer(PERCENTUAL_ALVO);
                }
            }
        }
        return total;
    }
}
