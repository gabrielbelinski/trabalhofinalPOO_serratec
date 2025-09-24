package ENUM;

public enum Parentesco {
    FILHO("Filho"),
    SOBRINHO("Sobrinho"),
    OUTROS("Outros");

    private final String tipo;

    private Parentesco(String tipo){
        this.tipo = tipo;
    }

    public String getTipo(){
        return this.tipo;
    }
}
