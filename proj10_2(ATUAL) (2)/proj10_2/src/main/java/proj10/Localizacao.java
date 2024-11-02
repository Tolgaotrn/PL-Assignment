package proj10;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Localizacao")
public class Localizacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_localizacao;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false)
    private String distrito;

    // Construtor padrão
    public Localizacao() {}

    // Construtor com parâmetros
    public Localizacao(String cidade, String distrito) {
        this.cidade = cidade;
        this.distrito = distrito;
    }

    // Getters e Setters
    public int getIdLocalizacao() {
        return id_localizacao;
    }

    public void setIdLocalizacao(int id_localizacao) {
        this.id_localizacao = id_localizacao;
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


}

