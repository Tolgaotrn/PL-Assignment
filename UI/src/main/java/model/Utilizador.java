package model;

public class Utilizador {
    private int idUtilizador;
    private String nome;   
    private String username;
    private String password;
    private String telemovel;
    public Utilizador() {
    }
    public Utilizador(String nome, String username, String password, String telemovel) {
        this.nome = nome;
        this.username = username;
        this.password = password;
        this.telemovel = telemovel;
    }
    // Getters e Setters
    public int getIdUtilizador() {
        return idUtilizador;
    }

    public void setIdUtilizador(int idUtilizador) {
        this.idUtilizador = idUtilizador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    @Override
    public String toString() {
        return "--------------------------------------------------\n" +
                "ID: " + idUtilizador + "\n" +
                "Nome: " + nome + "\n" +
                "Username: " + username + "\n" +
                "Password: ********" + "\n" +
                "Telemovel: " + telemovel + "\n" +
                "--------------------------------------------------\n";
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
}
