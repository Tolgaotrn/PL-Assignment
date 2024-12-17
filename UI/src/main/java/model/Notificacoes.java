package model;
import java.util.Date;

public class Notificacoes {
    private int id;
    private Date data;
    private String descricao;
    private int idUtilizador;
    private int id_recurso;
    public Notificacoes() {
    }

    public Notificacoes(int id, Date data, String descricao, int id_utilizador, int id_recurso) {
        this.id = id;
        this.data = data;
        this.descricao = descricao;
        this.idUtilizador = id_utilizador;
        this.id_recurso = id_recurso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getId_utilizador() {
        return idUtilizador;
    }

    public void setId_utilizador(int id_utilizador) {
        this.idUtilizador = id_utilizador;
    }

    public int getId_recurso() {
        return id_recurso;
    }

    public void setId_recurso(int id_recurso) {
        this.id_recurso = id_recurso;
    }
    public void toString(Notificacoes notificacoes) {
        System.out.println("Notificacoes{" +
                "id=" + notificacoes.getId() +
                ", data=" + notificacoes.getData() +
                ", descricao='" + notificacoes.getDescricao() + '\'' +
                ", id_utilizador=" + notificacoes.getId_utilizador() +
                ", id_recurso=" + notificacoes.getId_recurso() +
                '}');
    }
}
