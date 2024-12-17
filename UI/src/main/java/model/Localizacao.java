package model;

public class Localizacao {
	
    private int idLocalizacao;

    
    private String cidade;

   
    private String distrito;

    // Getters e Setters
    public int getIdLocalizacao() {
        return idLocalizacao;
    }

    public void setIdLocalizacao(int idLocalizacao) {
        this.idLocalizacao = idLocalizacao;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }
    @Override
    public String toString() {
        return 
                "Cidade: " + cidade + "\n" +
                "Distrito: " + distrito + "\n";
    }
}
