package com.app.restaurant.service.implementation;

import com.app.restaurant.model.Item;
import com.app.restaurant.model.Request;
import com.app.restaurant.model.users.Bartender;
import com.app.restaurant.model.users.Chef;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("classpath:application-test.properties")
public class RequestServiceIntegrationTest {

    @Autowired
    RequestService requestService;
    @Autowired
    MenuItemService menuItemService;
    @Autowired
    DrinkCardItemService drinkCardItemService;
    @Autowired
    MenuService menuService;
    @Autowired
    DrinkCardService drinkCardService;
    @Autowired
    PriceService priceService;

    @Test
    public void createItem_validAuthor_returnsZero(){
        Chef author = new Chef();
        author.setName("Imenko");
        author.setLastName("Prezimenic");

        Request request = new Request();
        request.setDescription("Opis novog jela");
        request.setUser(author);
        request.setItemName("Svinjska muckalica");
        request.setPrice(550);
        request.setIngredients("Sastojci");
        request.setPreparationTime(15);

        try {
            int creationResult = this.requestService.createItem(request);
            assertEquals(0, creationResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(2, this.menuItemService.findAll().size());

        Item createdItem = null;
        for(Item i : this.menuItemService.findAll()){
            if(i.getName().equals(request.getItemName()) && i.getDescription().equals(request.getDescription()) &&
                i.getPrice().getValue() == request.getPrice() && i.getIngredients().equals(request.getIngredients()))
                createdItem = i;
        }

        assertNotNull(createdItem);

    }

    @Test
    public void createItem_invalidAuthor_returnsOne(){
        Bartender author = new Bartender();
        author.setName("Imenko");
        author.setLastName("Prezimenic");

        Request request = new Request();
        request.setDescription("Opis novog jela");
        request.setUser(author);
        request.setItemName("Svinjska muckalica");
        request.setPrice(550);
        request.setIngredients("Sastojci");
        request.setPreparationTime(15);

        try {
            int creationResult = this.requestService.createItem(request);
            assertEquals(1, creationResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(1, this.menuItemService.findAll().size());

        Item createdItem = null;
        for(Item i : this.menuItemService.findAll()){
            if(i.getName().equals(request.getItemName()) && i.getDescription().equals(request.getDescription()) &&
                    i.getPrice().getValue() == request.getPrice() && i.getIngredients().equals(request.getIngredients()))
                createdItem = i;
        }

        assertNull(createdItem);


    }

    @Test
    public void createItem_multipleCreations_createsDuplicates(){
        Chef author = new Chef();
        author.setName("Imenko");
        author.setLastName("Prezimenic");

        Request request = new Request();
        request.setDescription("Opis novog jela");
        request.setUser(author);
        request.setItemName("Svinjska muckalica");
        request.setPrice(550);
        request.setIngredients("Sastojci");
        request.setPreparationTime(15);

        try {
            int creationResult = this.requestService.createItem(request);
            creationResult = this.requestService.createItem(request);
            creationResult = this.requestService.createItem(request);
            assertEquals(0, creationResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(5, this.menuItemService.findAll().size());

        Item createdItem = null;
        int duplicateCounter = 0;

        for(Item i : this.menuItemService.findAll()){
            if(createdItem != null) ++duplicateCounter;

            if(i.getName().equals(request.getItemName()) && i.getDescription().equals(request.getDescription()) &&
                    i.getPrice().getValue() == request.getPrice() && i.getIngredients().equals(request.getIngredients()))
                createdItem = i;
        }

        assertEquals(3, duplicateCounter);
        assertNotNull(createdItem);
    }
}
