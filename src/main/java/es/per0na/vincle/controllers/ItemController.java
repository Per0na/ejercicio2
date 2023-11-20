package es.per0na.vincle.controllers;

import es.per0na.vincle.data.Event;
import es.per0na.vincle.data.Item;
import es.per0na.vincle.data.types.ItemState;
import es.per0na.vincle.services.EventService;
import es.per0na.vincle.services.ItemService;
import es.per0na.vincle.services.WebSocketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/items")
@Tag(name = "Items", description = "CRUD de ítems")
public class ItemController {
    private final ItemService itemService;
    private final EventService eventService;
    private final WebSocketService webSocketService;

    @Autowired
    public ItemController(ItemService itemService, EventService eventService, WebSocketService webSocketService) {
        this.itemService = itemService;
        this.eventService = eventService;
        this.webSocketService = webSocketService;
    }

    @Operation(summary = "Crear un nuevo ítem")
    @PostMapping
    public ResponseEntity<Item> createItem(@RequestBody CreateItemRequest request) {
        Item item = request.getItem();
        String clientName = request.getClientName();

        Item newItem = itemService.saveItem(item);

        Event event = eventService.saveEvent(newItem, ItemState.CREATED, clientName);
        webSocketService.sendEvent(event);

        return new ResponseEntity<>(newItem, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener todos los ítems")
    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> items = itemService.getAllItems();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @Operation(summary = "Obtener un ítem por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        return itemService.getItemById(id)
                .map(item -> new ResponseEntity<>(item, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Actualizar un ítem por ID")
    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestBody CreateItemRequest request) {
        Item newItemData = request.getItem();
        String clientName = request.getClientName();

        Item updatedItem = itemService.updateItem(id, newItemData);

        Event event = eventService.saveEvent(updatedItem, ItemState.EDITED, clientName);
        webSocketService.sendEvent(event);

        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }

    @Operation(summary = "Eliminar un ítem por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        Item item = itemService.getItemById(id).orElseThrow(() -> new RuntimeException("Item not found with id " + id));

        Event event = eventService.saveEvent(item, ItemState.DELETED, "Per0na");
        webSocketService.sendEvent(event);

        itemService.deleteItem(item.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public static class CreateItemRequest {
        private Item item;
        private String clientName;

        public Item getItem() {
            return item;
        }

        public String getClientName() {
            return clientName;
        }
    }
}
