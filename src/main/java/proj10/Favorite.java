package proj10;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "Favorites")
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")  // Corrected column name
    private int favoriteId;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "resource_id", nullable = false)
    private int resourceId;

    @Column(name = "favorite_date", nullable = false)
    private Date favoriteDate;

    public int getFavoriteId() {
        return favoriteId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public Date getFavoriteDate() {
        return favoriteDate;
    }

    public void setFavoriteDate(Date favoriteDate) {
        this.favoriteDate = favoriteDate;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
}
