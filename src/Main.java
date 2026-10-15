import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static final List<Posto> postos = new ArrayList<>();
    private static final ServicoReabastecimento servico = new ServicoReabastecimento();

    public static void main(String[] args) {
        carregarDadosExemplo();
        Scanner entrada = new Scanner(System.in);
        int opcao;
        do {
            System.out.println("\n=== Monitor de Estoque de Combustível ===");
            System.out.println("1 - Ver estoque dos postos");
            System.out.println("2 - Alertas de reabastecimento");
            System.out.println("3 - Registrar venda");
            System.out.println("4 - Registrar recebimento");
            System.out.println("5 - Gerar pedidos de reabastecimento");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");
            opcao = lerInteiro(entrada);
            switch (opcao) {
                case 1 -> listarEstoque();
                case 2 -> mostrarAlertas();
                case 3 -> movimentar(entrada, true);
                case 4 -> movimentar(entrada, false);
                case 5 -> gerarPedidos();
                case 0 -> System.out.println("Encerrando.");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void carregarDadosExemplo() {
        Posto p1 = new Posto("Posto Shell Centro", "Piracicaba");
        p1.adicionarTanque(new Tanque(TipoCombustivel.GASOLINA_COMUM, 30000, 5000, 2500));
        p1.adicionarTanque(new Tanque(TipoCombustivel.ETANOL, 20000, 14000, 1500));
        p1.adicionarTanque(new Tanque(TipoCombustivel.DIESEL_S10, 25000, 6000, 1800));

        Posto p2 = new Posto("Posto Shell Rodovia", "Paulínia");
        p2.adicionarTanque(new Tanque(TipoCombustivel.GASOLINA_COMUM, 40000, 30000, 3500));
        p2.adicionarTanque(new Tanque(TipoCombustivel.DIESEL_S10, 40000, 7000, 4000));

        Posto p3 = new Posto("Posto Shell Paulista", "São Paulo");
        p3.adicionarTanque(new Tanque(TipoCombustivel.GASOLINA_COMUM, 30000, 20000, 3000));
        p3.adicionarTanque(new Tanque(TipoCombustivel.ETANOL, 20000, 3000, 1200));

        postos.add(p1);
        postos.add(p2);
        postos.add(p3);
    }

    private static void listarEstoque() {
        for (Posto posto : postos) {
            System.out.println("\n" + posto.getNome() + " - " + posto.getCidade());
            for (Tanque t : posto.getTanques()) {
                System.out.printf("  %-15s %8.0f / %.0f L (%.0f%%) | autonomia %.1f dia(s)%n",
                    t.getTipo().getNome(), t.getEstoqueLitros(),
                    t.getCapacidadeLitros(), t.percentualOcupacao(), t.diasDeAutonomia());
            }
        }
    }

    private static void mostrarAlertas() {
        boolean algum = false;
        for (Posto posto : postos) {
            for (Tanque t : posto.getTanques()) {
                if (t.precisaReabastecer(ServicoReabastecimento.LIMITE_DIAS_ALERTA)) {
                    algum = true;
                    System.out.printf("ALERTA: %s (%s) - %s com %.1f dia(s) de autonomia%n",
                        posto.getNome(), posto.getCidade(),
                        t.getTipo().getNome(), t.diasDeAutonomia());
                }
            }
        }
        if (!algum) {
            System.out.println("Nenhum tanque abaixo do limite de autonomia.");
        }
    }

    private static void gerarPedidos() {
        List<String> pedidos = servico.gerarPedidos(postos);
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido necessário.");
            return;
        }
        pedidos.forEach(System.out::println);
        System.out.printf("Total a pedir: %.0f L%n", servico.totalLitrosPedidos(postos));
    }

    private static void movimentar(Scanner entrada, boolean venda) {
        System.out.println("Posto:");
        for (int i = 0; i < postos.size(); i++) {
            System.out.println((i + 1) + " - " + postos.get(i).getNome()
                + " (" + postos.get(i).getCidade() + ")");
        }
        System.out.print("Número do posto: ");
        int idxPosto = lerInteiro(entrada) - 1;
        if (idxPosto < 0 || idxPosto >= postos.size()) {
            System.out.println("Posto inválido.");
            return;
        }

        TipoCombustivel[] tipos = TipoCombustivel.values();
        for (int i = 0; i < tipos.length; i++) {
            System.out.println((i + 1) + " - " + tipos[i].getNome());
        }
        System.out.print("Número do combustível: ");
        int idxTipo = lerInteiro(entrada) - 1;
        if (idxTipo < 0 || idxTipo >= tipos.length) {
            System.out.println("Combustível inválido.");
            return;
        }

        Optional<Tanque> tanque = postos.get(idxPosto).buscarTanque(tipos[idxTipo]);
        if (tanque.isEmpty()) {
            System.out.println("Esse posto não tem tanque desse combustível.");
            return;
        }

        System.out.print("Litros: ");
        double litros = lerDecimal(entrada);
        try {
            if (venda) {
                tanque.get().registrarVenda(litros);
            } else {
                tanque.get().registrarRecebimento(litros);
            }
            System.out.println("Movimentação registrada.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static int lerInteiro(Scanner entrada) {
        try {
            return Integer.parseInt(entrada.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static double lerDecimal(Scanner entrada) {
        try {
            return Double.parseDouble(entrada.nextLine().trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
