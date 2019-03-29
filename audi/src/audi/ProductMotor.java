package audi;

public class ProductMotor {

    private String nome = "Motor";
    private final int tempo = 12;
    private final int estoque = 10;
    private final int quantidade = 1;
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getTempo() {
        return tempo;
    }

    public int getEstoque() {
        return estoque;
    }

    public int getQuantidade() {
        return quantidade;
    }

}
