package com.app.restaurant.service.implementation;

import com.app.restaurant.model.Item;
import com.app.restaurant.model.Receipt;
import com.app.restaurant.model.Request;
import com.app.restaurant.model.users.Bartender;
import com.app.restaurant.model.users.Chef;
import com.app.restaurant.model.users.HeadBartender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

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
    public void findAll_successfullyFindsAll(){
        List<Request> allReq = this.requestService.findAll();

        assertEquals(3, allReq.size());
    }

    @Test
    public void findOne_existingId_returnsRequest(){
        Request req = this.requestService.findOne(2);

        assertNotNull(req);
        assertEquals("Latte", req.getItemName());
        assertEquals("description", req.getDescription());
        assertTrue(req.getUser().getClass().equals(HeadBartender.class));
    }

    @Test
    public void findOne_nonExistingId_returnsNull(){
        Request req = this.requestService.findOne(22);

        assertNull(req);
    }

    @Test
    public void save_validRequest_returnsSaved(){
        Chef author = new Chef();
        author.setId(3);

        Request request = new Request();
        request.setDescription("Opis novog jela");
        request.setUser(author);
        request.setItemName("Svinjska muckalica");
        request.setPrice(550);
        request.setIngredients("Sastojci");
        request.setPreparationTime(15);

        Request saved = this.requestService.save(request);

        assertNotNull(saved);
        assertEquals(request.getDescription(), saved.getDescription());
        assertEquals(request.getItemName(), saved.getItemName());
        assertEquals(request.getPrice(), saved.getPrice(), 0);
        assertEquals(request.getPreparationTime(), saved.getPreparationTime());
    }

    @Test
    public void save_missingSomeFields_returnsSaved(){
        Chef author = new Chef();
        author.setId(3);

        Request request = new Request();
        request.setDescription("Opis novog jela");
        request.setUser(author);
        request.setPrice(550);
        request.setPreparationTime(15);

        Request saved = this.requestService.save(request);

        assertNotNull(saved);
        assertEquals(request.getDescription(), saved.getDescription());
        assertEquals(request.getPrice(), saved.getPrice(), 0);
        assertEquals(request.getPreparationTime(), saved.getPreparationTime());
    }

    @Test
    public void save_missingChefId_throwsInvalidDataAccessApiUsage(){
        Chef author = new Chef();

        Request request = new Request();
        request.setDescription("Opis novog jela");
        request.setUser(author);
        request.setPrice(550);
        request.setPreparationTime(15);

        assertThrows(InvalidDataAccessApiUsageException.class, ()-> this.requestService.save(request));
    }

    @Test
    public void save_nullEntity_throwsInvalidDataAccessApiUsage(){
        InvalidDataAccessApiUsageException idaaue = assertThrows(InvalidDataAccessApiUsageException.class, ()-> this.requestService.save(null));
        assertEquals("Entity must not be null.; nested exception is java.lang.IllegalArgumentException: Entity must not be null.",
                idaaue.getMessage());
    }


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
        author.setId(3);

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
