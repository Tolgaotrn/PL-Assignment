package proj10;

import jakarta.persistence.*;
@Entity
@Table(name = "utilizador")
public class Utilizador {
	@Id
    @Column(name = "id_utilizador")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUtilizador;
    private String nome;
    private String username;
    private String password;
    private String telemovel;
    private String localizacao;
    private String tipo_utilizador;

    
    
    public Utilizador( String nome, String username, String password, String telemovel,
			String localizacao, String tipoUtilizador) {
	
		
		this.nome = nome;
		this.username = username;
		this.password = password;
		this.telemovel = telemovel;
		this.localizacao = localizacao;
		this.tipo_utilizador = tipoUtilizador;
	}

    
    public Utilizador() {
		// TODO Auto-generated constructor stub
	}


	public int getIdUtilizador() {
        return idUtilizador;
    }

   

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelemovel() {
        return telemovel;
    }

    public void setTelemovel(String telemovel) {
        this.telemovel = telemovel;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getTipoUtilizador() {
        return tipo_utilizador;
    }

    public void setTipoUtilizador(String tipoUtilizador) {
        this.tipo_utilizador = tipoUtilizador;
    }
}


