package proj10;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Food_Banks")
public class FoodBank extends Resources {
    @Column(name = "available_food_types")
    private String availableFoodTypes;

    @Column(name = "additional_costs")
    private String additionalCosts;

    @Column(name = "extra_information")
    private String extraInformation;

    // ADD GETTERS AND SETTERS
    public String getAvailableFoodTypes() {
        return availableFoodTypes;
    }

    public void setAvailableFoodTypes(String availableFoodTypes) {
        this.availableFoodTypes = availableFoodTypes;
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
}
