package proj10;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Recursos")
public class Recursos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recurso")
    private int idRecurso;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "localizacao", nullable = false)
    private String localizacao;

    @Column(name = "telefone")
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_recurso", nullable = false)
    private TipoRecurso tipoRecurso;
    public enum TipoRecurso {
        hospital, abrigo, cozinha, centro_trocas, banco_alimento, tipoRecurso
    }
    // ADICIONAR GETTERS E SETTERS
    public int getIdRecurso() {
        return idRecurso;
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


