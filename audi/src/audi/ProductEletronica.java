package audi;

public class ProductEletronica {

    private String nome = "Eletronica";
    private final int tempo = 7;
    private final int estoque = 8;
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
