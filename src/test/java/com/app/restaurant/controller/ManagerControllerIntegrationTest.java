package com.app.restaurant.controller;

import com.app.restaurant.dto.*;
import com.app.restaurant.model.*;
import com.app.restaurant.model.enums.ReceiptItemStatus;
import com.app.restaurant.repository.PriceRepository;
import com.app.restaurant.repository.UserRepository;
import com.app.restaurant.security.auth.JwtAuthenticationRequest;
import com.app.restaurant.service.*;
import com.app.restaurant.service.implementation.WaiterService;
import com.app.restaurant.support.DrinkCardItemDTOToDrinkCardItem;
import com.app.restaurant.support.ManagerDTOToManager;
import com.app.restaurant.support.MenuItemDTOToMenuItem;
import com.app.restaurant.support.UserToUserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@Transactional
public class ManagerControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private IManagerService managerService;
    @Autowired
    private IRequestService requestService;
    @Autowired
    private IUserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IDrinkCardItemService drinkCardItemService;
    @Autowired
    private IMenuItemService menuItemService;
    @Autowired
    private IRestaurantTableService restaurantTableService;
    @Autowired
    private WaiterService waiterService;
    @Autowired
    private MenuItemDTOToMenuItem menuItemDTOToMenuItem;
    @Autowired
    private DrinkCardItemDTOToDrinkCardItem drinkCardItemDTOToDrinkCardItem;
    @Autowired
    private ManagerDTOToManager managerDTOToManager;
    @Autowired
    private UserToUserDTO userToUserDTO;
    @Autowired
    private PriceRepository priceRepository;

    private HttpHeaders headers;

    @BeforeEach
    public void login() {

        ResponseEntity<UserTokenState> responseEntity =
                restTemplate.postForEntity("/api/auth/login",
                        new JwtAuthenticationRequest("dusan", "1234"),
                        UserTokenState.class);

        String accessToken = Objects.requireNonNull(responseEntity.getBody()).getAccessToken();
        headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void CreateWaiter_ValidWaiter_ReturnsCreated(){

        WaiterDTO waiterDTO = new WaiterDTO(15,"Waiter", "Milorad", "Micic", "mico", "$2a$10$vnbp6TE0PEATtxRoxzzGHOUfb76RxBI.O9l8WAJAA.L.aZIE6O5ry",  false);
        HttpEntity<WaiterDTO> httpEntity = new HttpEntity<>(waiterDTO, headers);
        ResponseEntity<WaiterDTO> responseEntity = restTemplate.exchange("/api/manager/new-waiter", HttpMethod.POST, httpEntity, WaiterDTO.class );

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void CreateWaiter_InvalidWaiter_ReturnsBadRequest(){

        WaiterDTO waiterDTO = new WaiterDTO(15,"Waiter", "Milorad", "Micic", "mladen", "$2a$10$vnbp6TE0PEATtxRoxzzGHOUfb76RxBI.O9l8WAJAA.L.aZIE6O5ry",  false);
        HttpEntity<WaiterDTO> httpEntity = new HttpEntity<>(waiterDTO, headers);
        ResponseEntity<WaiterDTO> responseEntity = restTemplate.exchange("/api/manager/new-waiter", HttpMethod.POST, httpEntity, WaiterDTO.class );

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void CreateMenuItem_ValidMenuItemDTO_ReturnsCreated(){

        PriceDTO priceDTO=new PriceDTO();
        priceDTO.setValue(4000);
        int current_size = menuItemService.findAll().size();
        MenuItemDTO menuItemDTO = new MenuItemDTO(15,"MenuItemNew","ing","img","desc",priceDTO,1,10);

        HttpEntity<MenuItemDTO> httpEntity = new HttpEntity<>(menuItemDTO, headers);
        ResponseEntity<MenuItemDTO> responseEntity = restTemplate.exchange("/api/manager/new-menu-item", HttpMethod.POST, httpEntity, MenuItemDTO.class );

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(current_size+1, menuItemService.findAll().size());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void CreateMenuItem_InvalidMenuItemPrice_ReturnsInternalServerError(){

        PriceDTO priceDTO=new PriceDTO();
        priceDTO.setValue(-4);
        int current_size = menuItemService.findAll().size();
        MenuItemDTO menuItemDTO = new MenuItemDTO(15,"MenuItemNew","ing","img","desc",priceDTO,1,10);

        HttpEntity<MenuItemDTO> httpEntity = new HttpEntity<>(menuItemDTO, headers);
        ResponseEntity<MenuItemDTO> responseEntity = restTemplate.exchange("/api/manager/new-menu-item", HttpMethod.POST, httpEntity, MenuItemDTO.class );

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void CreateDrinkCardItem_ValidDrinkCardItemDTO_ReturnsCreated(){
        PriceDTO priceDTO=new PriceDTO();
        priceDTO.setValue(4000);
        int current_size = drinkCardItemService.findAll().size();
        DrinkCardItemDTO drinkCardItemDTO = new DrinkCardItemDTO(15,"MenuItemNew","ing","img","desc",priceDTO,1);

        HttpEntity<DrinkCardItemDTO> httpEntity = new HttpEntity<>(drinkCardItemDTO, headers);
        ResponseEntity<DrinkCardItemDTO> responseEntity = restTemplate.exchange("/api/manager/new-drink-card-item", HttpMethod.POST, httpEntity, DrinkCardItemDTO.class );

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(current_size+1, drinkCardItemService.findAll().size());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void CreateDrinkCardItem_InvalidDrinkCardItemDTO_ReturnsInternalServerError(){
        PriceDTO priceDTO=new PriceDTO();
        priceDTO.setValue(-4);
        DrinkCardItemDTO drinkCardItemDTO = new DrinkCardItemDTO(15,"MenuItemNew","ing","img","desc",priceDTO,1);

        HttpEntity<DrinkCardItemDTO> httpEntity = new HttpEntity<>(drinkCardItemDTO, headers);
        ResponseEntity<DrinkCardItemDTO> responseEntity = restTemplate.exchange("/api/manager/new-drink-card-item", HttpMethod.POST, httpEntity, DrinkCardItemDTO.class );

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void DeleteUser_ValidUsername_ReturnsOk(){

        int current_size = userService.findAll().size();

        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<?> responseEntity = restTemplate.exchange("/api/manager/delete-user/ana", HttpMethod.POST, httpEntity, ResponseEntity.class );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(current_size-1, userService.findAll().size());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void DeleteUser_InvalidUsername_ReturnsNotFound(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<UserDTO> responseEntity = restTemplate.exchange("/api/manager/delete-user/anaana", HttpMethod.POST, httpEntity, UserDTO.class );

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void DeleteDrinkCardItem_ValidDrinkCardItemDTO_ReturnsOk(){

        DrinkCardItemDTO drinkCardItemDTO = new DrinkCardItemDTO(2, "Coca Cola", "ingredients", "image", "description", new PriceDTO(),1);
        HttpEntity<DrinkCardItemDTO> httpEntity = new HttpEntity<>(drinkCardItemDTO,headers);
        ResponseEntity<?> responseEntity = restTemplate.exchange("/api/manager/delete-drink-card-item", HttpMethod.POST, httpEntity, ResponseEntity.class );

        DrinkCardItem drinkCardItem = drinkCardItemService.findOne(2);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(true,drinkCardItem.getDeleted());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void DeleteDrinkCardItem_InvalidDrinkCardItemDTO_ReturnsNotFound(){

        DrinkCardItemDTO drinkCardItemDTO = new DrinkCardItemDTO(15, "Coca Cola", "ingredients", "image", "description", new PriceDTO(),1);
        HttpEntity<DrinkCardItemDTO> httpEntity = new HttpEntity<>(drinkCardItemDTO,headers);
        ResponseEntity<DrinkCardItemDTO> responseEntity = restTemplate.exchange("/api/manager/delete-drink-card-item", HttpMethod.POST, httpEntity, DrinkCardItemDTO.class );

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void DeleteMenuItem_ValidMenuItemDTOId_ReturnsOk(){

        MenuItemDTO menuItemDTO = new MenuItemDTO(1, "Pizza", "ingredients", "image", "description", new PriceDTO(),1,100);
        HttpEntity<MenuItemDTO> httpEntity = new HttpEntity<>(menuItemDTO,headers);
        ResponseEntity<?> responseEntity = restTemplate.exchange("/api/manager/delete-menu-item", HttpMethod.POST, httpEntity, ResponseEntity.class );

        MenuItem menuItem= menuItemService.findOne(1);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(true,menuItem.getDeleted());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void DeleteMenuItem_InvalidMenuItemDTOId_ReturnsNotFound(){

        MenuItemDTO menuItemDTO = new MenuItemDTO(15, "Pizza", "ingredients", "image", "description", new PriceDTO(),1,100);
        HttpEntity<MenuItemDTO> httpEntity = new HttpEntity<>(menuItemDTO,headers);
        ResponseEntity<MenuItemDTO> responseEntity = restTemplate.exchange("/api/manager/delete-menu-item", HttpMethod.POST, httpEntity, MenuItemDTO.class );

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void UpdateMenuItem_ValidMenuItemDTO_ReturnsCreated(){
        Optional<Price> price = priceRepository.findById(1);
        PriceDTO priceDTO = new PriceDTO();
        priceDTO.setId(price.get().getId());
        priceDTO.setValue(price.get().getValue());
        MenuItemDTO menuItemDTO = new MenuItemDTO(1, "Pizza", "new ing", "image", "description",priceDTO,1,100);
        HttpEntity<MenuItemDTO> httpEntity = new HttpEntity<>(menuItemDTO,headers);
        ResponseEntity<MenuItemDTO> responseEntity = restTemplate.exchange("/api/manager/update-menu-item", HttpMethod.POST, httpEntity, MenuItemDTO.class );

        MenuItem menuItem=menuItemService.findOne(1);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("new ing", menuItem.getIngredients());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void UpdateMenuItem_InvalidMenuItemDTOPrice_ReturnsNotFound(){
        PriceDTO priceDTO = new PriceDTO();
        priceDTO.setId(15);
        MenuItemDTO menuItemDTO = new MenuItemDTO(1, "Pizza", "new ing", "image", "description",priceDTO,1,100);
        HttpEntity<MenuItemDTO> httpEntity = new HttpEntity<>(menuItemDTO,headers);
        ResponseEntity<MenuItemDTO> responseEntity = restTemplate.exchange("/api/manager/update-menu-item", HttpMethod.POST, httpEntity, MenuItemDTO.class );

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void CreateManager_ValidUser_ReturnsCreated(){

        ManagerDTO userDTO = new ManagerDTO();
        userDTO.setName("Mirko");
        userDTO.setLastName("Zmirko");
        userDTO.setEmailAddress("mirko@gmail.com");
        userDTO.setUsername("mirko");
        userDTO.setPassword("1234");
        userDTO.setDeleted(false);

        HttpEntity<Object> httpEntity = new HttpEntity<>(userDTO, headers);
        ResponseEntity<ManagerDTO> responseEntity =
                restTemplate.exchange("/api/manager/new-manager", HttpMethod.POST, httpEntity, ManagerDTO.class);

        ManagerDTO user = responseEntity.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assert user != null;
        assertEquals("Mirko", user.getName());
    }

    @Test
    public void CreateManager_InvalidUser_ReturnsBadRequest(){

        ManagerDTO userDTO = new ManagerDTO();
        userDTO.setName("Mirko");
        userDTO.setLastName("Zmirko");
        userDTO.setEmailAddress("mirko@gmail.com");
        userDTO.setUsername("mladen");
        userDTO.setPassword("1234");
        userDTO.setDeleted(false);

        HttpEntity<Object> httpEntity = new HttpEntity<>(userDTO, headers);
        ResponseEntity<ManagerDTO> responseEntity = restTemplate.exchange("/api/manager/new-manager", HttpMethod.POST, httpEntity, ManagerDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void GetAllRequests_ReturnsAccepted(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<RequestDTO[]> responseEntity = restTemplate.exchange("/api/manager/requests", HttpMethod.GET, httpEntity, RequestDTO[].class );

        RequestDTO[] requests = responseEntity.getBody();

        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        assertEquals(2, requests.length);
    }

    @Test
    public void UpdateManager_InvalidUser_ReturnsNotFound(){

        ManagerDTO userDTO = new ManagerDTO();
        userDTO.setId(15);
        userDTO.setName("Miki");
        userDTO.setLastName("Mikic");
        userDTO.setEmailAddress("miladen@gmail.com");
        userDTO.setUsername("dusan");
        userDTO.setPassword("1234");
        userDTO.setDeleted(false);

        HttpEntity<Object> httpEntity = new HttpEntity<>(userDTO, headers);
        ResponseEntity<ManagerDTO> responseEntity = restTemplate.exchange("/api/manager/update-manager", HttpMethod.POST, httpEntity, ManagerDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void UpdateUser_ValidUser_ReturnsOk(){

        UserDTO userDTO = new UserDTO();
        userDTO.setDtype("Manager");
        userDTO.setName("Miki");
        userDTO.setLastName("Mikic");
        userDTO.setEmailAddress("miladen@gmail.com");
        userDTO.setUsername("mladen");
        userDTO.setPassword("1234");
        userDTO.setDeleted(false);

        HttpEntity<Object> httpEntity = new HttpEntity<>(userDTO, headers);
        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/api/manager/update-user", HttpMethod.POST, httpEntity, UserDTO.class);

        UserDTO user = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Miki", user.getName());
    }

    @Test
    public void UpdateUser_InvalidUsername_ReturnsNotFound(){

        UserDTO userDTO = new UserDTO();
        userDTO.setDtype("Manager");
        userDTO.setName("Miki");
        userDTO.setLastName("Mikic");
        userDTO.setEmailAddress("miladen@gmail.com");
        userDTO.setUsername("mladen12");
        userDTO.setPassword("1234");
        userDTO.setDeleted(false);

        HttpEntity<Object> httpEntity = new HttpEntity<>(userDTO, headers);
        ResponseEntity<UserDTO> responseEntity = restTemplate.exchange("/api/manager/update-user", HttpMethod.POST, httpEntity, UserDTO.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void GetUsers_ReturnsAccepted(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<UserDTO[]> responseEntity = restTemplate.exchange("/api/manager/getAll", HttpMethod.GET, httpEntity, UserDTO[].class );

        UserDTO[] userDTOS = responseEntity.getBody();

        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        assertEquals(7, userDTOS.length);
    }

    @Test
    public void GetRestaurantTables_ReturnsOk(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<UserDTO[]> responseEntity = restTemplate.exchange("/api/manager/restaurant-tables", HttpMethod.GET, httpEntity, UserDTO[].class );

        UserDTO[] userDTOS = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, userDTOS.length);
    }

    @Test
    public void AddRestaurantTable_ValidRestaurantTable_ReturnsOk(){
        RestaurantTable restaurantTable=restaurantTableService.findOne(1);
        HttpEntity<Object> httpEntity = new HttpEntity<>(restaurantTable,headers);
        ResponseEntity<RestaurantTable> responseEntity = restTemplate.exchange("/api/manager/add-restaurant-table", HttpMethod.POST, httpEntity, RestaurantTable.class );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void DeleteRestaurantTable_ValidRestaurantTableId_ReturnsOk(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<?> responseEntity = restTemplate.exchange("/api/manager/delete-restaurant-table/1", HttpMethod.POST, httpEntity, ResponseEntity.class );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void DeleteRestaurantTable_InvalidRestaurantTableId_ReturnsNotFound(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<?> responseEntity = restTemplate.exchange("/api/manager/delete-restaurant-table/15", HttpMethod.POST, httpEntity, ResponseEntity.class );

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void UpdateRestaurantTable_ValidRestaurantTable_ReturnsOk(){
        RestaurantTable restaurantTable = restaurantTableService.findOne(1);
        HttpEntity<Object> httpEntity = new HttpEntity<>(restaurantTable,headers);
        ResponseEntity<?> responseEntity = restTemplate.exchange("/api/manager/update-restaurant-table", HttpMethod.POST, httpEntity, ResponseEntity.class );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void UpdateRestaurantTable_InvalidRestaurantTable_ReturnsNotFound(){
        RestaurantTable restaurantTable = new RestaurantTable();
        restaurantTable.setId(15);
        HttpEntity<Object> httpEntity = new HttpEntity<>(restaurantTable,headers);
        ResponseEntity<RestaurantTable> responseEntity = restTemplate.exchange("/api/manager/update-restaurant-table", HttpMethod.POST, httpEntity, RestaurantTable.class );

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
