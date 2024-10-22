package proj10;
import jakarta.persistence.*;


@Entity
@Table(name = "Recursos")
public class Recursos{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recurso")
    private int idRecurso;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "localizacao", nullable = false, length = 255)
    private String localizacao;

    @Column(name = "telefone", length = 20)
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_recurso", nullable = false)
    private TipoRecurso tipoRecurso;

    public enum TipoRecurso {
        hospital, abrigo, cozinha, centro_trocas, banco_alimento
    }

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

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public TipoRecurso getTipoRecurso() {
        return tipoRecurso;
    }

    public void setTipoRecurso(TipoRecurso tipoRecurso) {
        this.tipoRecurso = tipoRecurso;
    }
}

