package es.per0na.vincle.services;

import es.per0na.vincle.data.Item;
import es.per0na.vincle.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    public Optional<Item> getItemById(Long id) {
        return itemRepository.findById(id);
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    public Item updateItem(Long id, Item newItemData) {
        return itemRepository.findById(id).map(item -> {
            item.setType(newItemData.getType());
            item.setRefrigerationType(newItemData.getRefrigerationType());
            item.setCapacity(newItemData.getCapacity());
            item.setContainerType(newItemData.getContainerType());
            item.setName(newItemData.getName());
            return itemRepository.save(item);
        }).orElseThrow(() -> new RuntimeException("Item not found with id " + id));
    }
}
