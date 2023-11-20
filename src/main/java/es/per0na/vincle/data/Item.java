package es.per0na.vincle.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import es.per0na.vincle.data.types.CapacityType;
import es.per0na.vincle.data.types.ContainerType;
import es.per0na.vincle.data.types.ItemType;
import es.per0na.vincle.data.types.RefrigerationType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ItemType type;

    @Enumerated(EnumType.STRING)
    private RefrigerationType refrigerationType;

    @Enumerated(EnumType.STRING)
    private CapacityType capacity;

    @Enumerated(EnumType.STRING)
    private ContainerType containerType;

    private String name;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Event> events;

    public Item() {
    }

    public Long getId() {
        return id;
    }

    public ItemType getType() {
        return type;
    }

    public RefrigerationType getRefrigerationType() {
        return refrigerationType;
    }

    public CapacityType getCapacity() {
        return capacity;
    }

    public ContainerType getContainerType() {
        return containerType;
    }

    public String getName() {
        return name;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public void setRefrigerationType(RefrigerationType refrigerationType) {
        this.refrigerationType = refrigerationType;
    }

    public void setCapacity(CapacityType capacity) {
        this.capacity = capacity;
    }

    public void setContainerType(ContainerType containerType) {
        this.containerType = containerType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
