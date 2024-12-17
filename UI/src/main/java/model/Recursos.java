package model;

public class Recursos {
	    private int idRecurso;
	    private String nome;
	    private String telefone;
	    private Localizacao localizacao;
	    private Tipo tipo;
	    public Recursos() {
	    }
	    public Recursos(String nome, String telefone, Localizacao localizacao, Tipo tipo) {
	        this.nome = nome;
	        this.telefone = telefone;
	        this.localizacao = localizacao;
	        this.tipo = tipo;
	    }
	    // Getters e Setters
	    public int getIdRecurso() {
	        return idRecurso;
	    }

	    public void setIdRecurso(int idRecurso) {
	        this.idRecurso = idRecurso;
	    }

	    public String getNome() {
	        return nome;
	    }

	    public void setNome(String nome) {
	        this.nome = nome;
	    }

	    public String getTelefone() {
	        return telefone;
	    }

	    public void setTelefone(String telefone) {
	        this.telefone = telefone;
	    }

	    public Localizacao getLocalizacao() {
	        return localizacao;
	    }

	    public void setLocalizacao(Localizacao localizacao) {
	        this.localizacao = localizacao;
	    }

	    public Tipo getTipo() {
	        return tipo;
	    }

	    public void setTipo(Tipo tipo) {
	        this.tipo = tipo;
	    }
	    @Override
	    public String toString() {
	        return ( "Nome: " + nome + 
	                "\nTelefone: " + telefone +
	                "\nCidade: " + localizacao.getCidade() +
	                "\nDistrito: " + localizacao.getDistrito()
					);
	}
}