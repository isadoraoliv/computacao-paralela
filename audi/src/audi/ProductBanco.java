package audi;

public class ProductBanco {

    private String nome = "Banco";
    private final int tempo = 6;
    private final int estoque = 25;
    private final int quantidade = 5;
   
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
