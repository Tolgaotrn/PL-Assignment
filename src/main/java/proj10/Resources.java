package proj10;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Resources")
public class Resources {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resource_id")
    private int resourceId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone")
    private String phone;

    // Relationship with Location (composition)
    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    // Relationship with Type (composition)
    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private ResourceType type;

    // Getters and Setters

    public int getResourceId() {
        return resourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

}
