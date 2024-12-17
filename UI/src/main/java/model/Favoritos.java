package model;
import java.io.Serializable;
public class Favoritos implements Serializable {
    private int idFavorito;
    private Utilizador utilizador;
    private Recursos recurso;
    private String dataFavorito;
    public Favoritos() {}

    public Favoritos(Utilizador utilizador, Recursos recurso, String dataFavorito) {
        this.utilizador = utilizador;
        this.recurso = recurso;
        this.dataFavorito = dataFavorito;
    }

    public int getIdFavorito() {
        return idFavorito;
    }

    public void setIdFavorito(int idFavorito) {
        this.idFavorito = idFavorito;
    }

    public Utilizador getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(Utilizador utilizador) {
        this.utilizador = utilizador;
    }

    public Recursos getRecurso() {
        return recurso;
    }

    public void setRecurso(Recursos recurso) {
        this.recurso = recurso;
    }

    public String getDataFavorito() {
        return dataFavorito;
    }

    public void setDataFavorito(String dataFavorito) {
        this.dataFavorito = dataFavorito;
    }

    @Override
    public String toString() {
        return 
                "Recurso: " + recurso.toString() + "\n" +
                "Data: '" + dataFavorito;
    }
}
