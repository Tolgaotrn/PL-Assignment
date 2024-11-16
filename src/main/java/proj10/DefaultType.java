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
    private int id; // Unique ID for the entry

    @Column(name = "name", nullable = false, length = 255)
    private String name; // Name associated with the default type

    @ManyToOne
    @JoinColumn(name = "type_id", foreignKey = @ForeignKey(name = "FK_type"))
    private ResourceType type; // Relationship with the ResourceType table

    // Default constructor
    public DefaultType() {}

    // Constructor with parameters
    public DefaultType(String name, ResourceType type) {
        this.name = name;
        this.type = type;
    }

    // Getters and Setters
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

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }
}
