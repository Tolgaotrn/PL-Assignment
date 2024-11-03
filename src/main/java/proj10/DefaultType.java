package proj10;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "default_type")
@PrimaryKeyJoinColumn(name = "id_tipo")
public class DefaultType extends Tipo {

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "location", length = 255)
    private String location;

    @Column(name = "contact", length = 255)
    private String contact;

    @Column(name = "extra_info", columnDefinition = "TEXT")
    private String extraInfo;

    public DefaultType() {
    }

    public DefaultType(String type, String name, String location, String contact, String extraInfo) {
        super(type);
        this.name = name;
        this.location = location;
        this.contact = contact;
        this.extraInfo = extraInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }


}
