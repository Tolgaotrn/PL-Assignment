package proj10;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Type of resource
// e.g. hospital, shelter, etc.
// This class is used to store the type of resource
// "Parent class"
// Other classes will inherit from this one
// When the admin creates a new type of resource, it will be stored as the default type with default attributes (as mentioned in Discord)

@Entity
@Table(name = "ResourceType") // Table name "ResourceType"
public class ResourceType { // ResourceType class
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int typeId; // Type ID

    @Column(nullable = false)
    private String type; // Type name

    public ResourceType() {}

    public ResourceType(String type) {
        this.type = type; // Set the type name
    }

    public int getTypeId() {
        return typeId; // Get the type ID
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId; // Set the type ID
    }

    public String getType() {
        return type; // Get the type name
    }

    public void setType(String type) {
        this.type = type; // Set the type name
    }
}
