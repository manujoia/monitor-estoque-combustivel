public enum TipoCombustivel {
    GASOLINA_COMUM("Gasolina Comum"),
    ETANOL("Etanol"),
    DIESEL_S10("Diesel S10");

    private final String nome;

    TipoCombustivel(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
