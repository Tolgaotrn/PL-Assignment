package proj10;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Community_Kitchens")
public class CommunityKitchen extends Resources {
    @Column(name = "food_type")
    private String foodType;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "additional_costs")
    private String additionalCosts;

    @Column(name = "extra_information")
    private String extraInformation;

    // ADD GETTERS AND SETTERS
    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
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
