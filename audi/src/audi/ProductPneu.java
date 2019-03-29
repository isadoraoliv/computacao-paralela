package audi;

public class ProductPneu {

    private String nome = "Pneu";
    private final int tempo = 9;
    private final int estoque = 100;
    private final int quantidade = 4;
        
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
