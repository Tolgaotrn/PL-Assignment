package proj10;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Type of resource, e.g., hospital, shelter, etc.
// This class is used to store the type of resource
// "Parent class"
// Other classes will inherit from this one
// When the admin creates a new type of resource, it will be stored as a default type with default attributes

@Entity
@Table(name = "Type") // Table name "Type"
public class Type { // Class name changed to "Type"
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // Resource type ID

    @Column(nullable = false)
    private String name; // Resource type name

    public Type() {}

    public Type(String name) {
        this.name = name; // Set the resource type name
    }

    public int getId() {
        return id; // Get the resource type ID
    }

    public void setId(int id) {
        this.id = id; // Set the resource type ID
    }

    public String getName() {
        return name; // Get the resource type name
    }

    public void setName(String name) {
        this.name = name; // Set the resource type name
    }
}
