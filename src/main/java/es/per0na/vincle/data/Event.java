package es.per0na.vincle.data;

import es.per0na.vincle.data.types.ItemState;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clientName;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    private ItemState state;

    public Event() {
        this.timestamp = LocalDateTime.now(ZoneOffset.UTC);
    }

    public Long getId() {
        return id;
    }

    public String getClientName() {
        return clientName;
    }

    public Item getItem() {
        return item;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public ItemState getState() {
        return state;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setState(ItemState state) {
        this.state = state;
    }
}
