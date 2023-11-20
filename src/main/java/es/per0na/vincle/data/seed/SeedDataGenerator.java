package es.per0na.vincle.data.seed;

import es.per0na.vincle.data.Item;
import es.per0na.vincle.data.types.CapacityType;
import es.per0na.vincle.data.types.ContainerType;
import es.per0na.vincle.data.types.ItemType;
import es.per0na.vincle.data.types.RefrigerationType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class SeedDataGenerator {

    private final RestTemplate restTemplate;

    public SeedDataGenerator(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void generateSeedData(String[] args) {
        if (args.length < 2 || !"--seed".equals(args[0])) {
            System.out.println("Usage: java -jar vincle.jar --seed <totalItems>");
            return;
        }

        int totalItemsToCreate = Integer.parseInt(args[1]);
        int halfTotalItems = totalItemsToCreate / 2;

        // Operations
        List<Long> itemIds = createItems(totalItemsToCreate);
        performRandomEditOperations(itemIds, halfTotalItems);
        performRandomDeleteOperations(itemIds, halfTotalItems);
    }

    private List<Long> createItems(int totalItems) {
        List<Long> itemIds = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < totalItems; i++) {
            ItemType itemType = ItemType.values()[random.nextInt(ItemType.values().length)];
            RefrigerationType refrigerationType = RefrigerationType.values()[random.nextInt(RefrigerationType.values().length)];
            CapacityType capacityType = CapacityType.values()[random.nextInt(CapacityType.values().length)];
            ContainerType containerType = ContainerType.values()[random.nextInt(ContainerType.values().length)];

            String itemName = generateRandomName();

            // Create new item
            Item newItem = new Item();
            newItem.setType(itemType);
            newItem.setRefrigerationType(refrigerationType);
            newItem.setCapacity(capacityType);
            newItem.setContainerType(containerType);
            newItem.setName(itemName);

            Item _newItem = restTemplate.postForObject("http://localhost:8080/api/items", newItem, Item.class);
            itemIds.add(_newItem.getId());
        }

        return itemIds;
    }

    public void performRandomEditOperations(List<Long> itemIds, int numEdits) {
        Random random = new Random();

        for (int i = 0; i < numEdits; i++) {
            Long itemId = itemIds.get(random.nextInt(itemIds.size()));

            Item currentItem = restTemplate.getForObject("http://localhost:8080/api/items/" + itemId, Item.class);
            String newName = generateRandomName();
            currentItem.setName(newName);

            restTemplate.put("http://localhost:8080/api/items/" + itemId, currentItem, Item.class);
        }
    }

    private String generateRandomName() {
        // Lógica para generar un nombre aleatorio (puedes personalizar según tus necesidades)
        return "NewName" + System.currentTimeMillis();
    }

    private void performRandomDeleteOperations(List<Long> itemIds, int numDeletes) {
        Random random = new Random();

        for (int i = 0; i < numDeletes; i++) {
            Long itemId = itemIds.get(random.nextInt(itemIds.size()));

            restTemplate.delete("http://localhost:8080/api/items/" + itemId);
        }
    }
}

