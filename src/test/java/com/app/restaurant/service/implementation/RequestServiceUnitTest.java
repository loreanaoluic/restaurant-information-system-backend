package com.app.restaurant.service.implementation;

import com.app.restaurant.model.*;
import com.app.restaurant.model.users.Bartender;
import com.app.restaurant.model.users.Chef;
import com.app.restaurant.repository.RequestRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("classpath:application-test.properties")
public class RequestServiceUnitTest {

    @Autowired
    RequestService requestService;

    @MockBean
    RequestRepository requestRepository;
    @MockBean
    MenuItemService menuItemService;
    @MockBean
    DrinkCardItemService drinkCardItemService;
    @MockBean
    MenuService menuService;
    @MockBean
    DrinkCardService drinkCardService;
    @MockBean
    PriceService priceService;

    @BeforeAll
    public void setUp(){
        List<MenuItem> menuItems = new ArrayList<MenuItem>();
        List<DrinkCardItem> drinkCardItems = new ArrayList<DrinkCardItem>();

        Menu menu = new Menu(1, menuItems);
        DrinkCard drinkCard = new DrinkCard(1, drinkCardItems);

        List<Request> requests = new ArrayList<>();

        Chef chef = new Chef();
        chef.setName("Imenko");
        chef.setLastName("Prezimenic");

        Request requestValidAuthor = new Request(1, 550, "Sastojci", "Opis novog jela",
                "", "Svinjska muckalica", 15, false, chef);


        Bartender bart = new Bartender();
        bart.setName("Bart");
        bart.setLastName("Simpson");

        Request requestInvalidAuthor = new Request(2, 250, "Tele valjda", "Opis novog jela",
                "", "Teleca corba", 10, false, bart);


        requests.add(requestValidAuthor);
        requests.add(requestInvalidAuthor);

        given(requestRepository.findAll()).willReturn(requests);
        given(requestRepository.findById(1)).willReturn(java.util.Optional.of(requestValidAuthor));
        given(requestRepository.findById(2)).willReturn(java.util.Optional.of(requestInvalidAuthor));
        when(requestRepository.save(Mockito.any(Request.class))).thenAnswer(i -> i.getArguments()[0]);


        when(menuItemService.save(Mockito.any(MenuItem.class))).thenAnswer(i -> i.getArguments()[0]);
        when(drinkCardItemService.save(Mockito.any(DrinkCardItem.class))).thenAnswer(i -> i.getArguments()[0]);

        when(priceService.save(Mockito.any(Price.class))).thenAnswer(i -> i.getArguments()[0]);
    }

    @Test
    public void CreateItem_ValidAuthor_ReturnsZero(){
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
    }

    @Test
    public void CreateItem_InvalidAuthor_ReturnsOne(){
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

    }
}
