package co.ke.emtechhouse.es.utils;

import javax.persistence.*;
import java.time.LocalDateTime;

@Embeddable
public class Embeds {

    @Column(name = "dateCreated")
    private LocalDateTime dateCreated = LocalDateTime.now();

    @Column(name = "isAvailable")
    private boolean isActive = true;

    // Getters and setters for isActive

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    // You can also add a setter for dateCreated if needed

    // Method to update dateCreated before persisting
    @PrePersist
    public void prePersist() {
        dateCreated = LocalDateTime.now();
    }
}
