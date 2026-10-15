public class Tanque {
    private final TipoCombustivel tipo;
    private final double capacidadeLitros;
    private double estoqueLitros;
    private final double consumoMedioDiarioLitros;

    public Tanque(TipoCombustivel tipo, double capacidadeLitros,
                  double estoqueLitros, double consumoMedioDiarioLitros) {
        if (estoqueLitros < 0 || estoqueLitros > capacidadeLitros) {
            throw new IllegalArgumentException("Estoque inicial inválido.");
        }
        this.tipo = tipo;
        this.capacidadeLitros = capacidadeLitros;
        this.estoqueLitros = estoqueLitros;
        this.consumoMedioDiarioLitros = consumoMedioDiarioLitros;
    }

    public TipoCombustivel getTipo() { return tipo; }
    public double getCapacidadeLitros() { return capacidadeLitros; }
    public double getEstoqueLitros() { return estoqueLitros; }

    public double percentualOcupacao() {
        return estoqueLitros / capacidadeLitros * 100;
    }

    // Quantos dias o estoque atual dura, considerando o consumo médio diário
    public double diasDeAutonomia() {
        if (consumoMedioDiarioLitros <= 0) {
            return Double.MAX_VALUE;
        }
        return estoqueLitros / consumoMedioDiarioLitros;
    }

    public boolean precisaReabastecer(double limiteDias) {
        return diasDeAutonomia() < limiteDias;
    }

    // Litros necessários para chegar a um percentual da capacidade (ex.: 0.90)
    public double litrosParaReabastecer(double percentualAlvo) {
        double alvo = capacidadeLitros * percentualAlvo;
        return Math.max(0, alvo - estoqueLitros);
    }

    public void registrarVenda(double litros) {
        if (litros <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        }
        if (litros > estoqueLitros) {
            throw new IllegalArgumentException("Estoque insuficiente para essa venda.");
        }
        estoqueLitros -= litros;
    }

    public void registrarRecebimento(double litros) {
        if (litros <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        }
        if (estoqueLitros + litros > capacidadeLitros) {
            throw new IllegalArgumentException("Recebimento excede a capacidade do tanque.");
        }
        estoqueLitros += litros;
    }
}
