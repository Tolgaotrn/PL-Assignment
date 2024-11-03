package proj10;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Table(name = "default_type")
public class DefaultType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // ID único para a entrada

    @Column(name = "name", nullable = false, length = 255)
    private String name; // Nome associado ao tipo padrão

    @ManyToOne
    @JoinColumn(name = "id_tipo", foreignKey = @ForeignKey(name = "FK_tipo"))
    private Tipo tipo; // Relação com a tabela Tipo

    // Construtor padrão
    public DefaultType() {}

    // Construtor com parâmetros
    public DefaultType(String name, Tipo tipo) {
        this.name = name;
        this.tipo = tipo;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
}
