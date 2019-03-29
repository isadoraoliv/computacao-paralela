package audi;

public class ProductCarroceria {

    private String nome = "Carroceria";
    private final int quantidade = 1;
    private final int tempo = 15;
    private final int estoque = 20;
  
   
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

    int getEstoque(ProductCarroceria heli) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
       
}
