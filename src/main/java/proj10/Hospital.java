package proj10;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Hospitals")
public class Hospital extends Resources {
    @Column(name = "specialties")
    private String specialties;

    @Column(name = "slots")
    private int slots;

    @Column(name = "additional_costs")
    private String additionalCosts;

    @Column(name = "extra_information")
    private String extraInformation;

    // IMPLEMENT GETTERS AND SETTERS
    public String getSpecialties() {
        return specialties;
    }

    public void setSpecialties(String specialties) {
        this.specialties = specialties;
    }

    public int getSlots() {
        return slots;
    }

    public void setSlots(int slots) {
        this.slots = slots;
    }

    public String getAdditionalCosts() {
        return additionalCosts;
    }

    public void setAdditionalCosts(String additionalCosts) {
        this.additionalCosts = additionalCosts;
    }

    public String getExtraInformation() {
        return extraInformation;
    }

    public void setExtraInformation(String extraInformation) {
        this.extraInformation = extraInformation;
    }

    public void setResourceId(int resourceId) {
        super.setResourceId(resourceId);
    }
}
