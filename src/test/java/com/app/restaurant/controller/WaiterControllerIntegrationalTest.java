package com.app.restaurant.controller;

import com.app.restaurant.dto.*;
import com.app.restaurant.model.*;
import com.app.restaurant.model.enums.ReceiptItemStatus;
import com.app.restaurant.security.auth.JwtAuthenticationRequest;
import com.app.restaurant.service.*;
import com.app.restaurant.service.implementation.ExpenseService;
import com.app.restaurant.support.DrinkCardItemDTOToDrinkCardItem;
import com.app.restaurant.support.MenuItemDTOToMenuItem;
import com.app.restaurant.support.ReceiptItemDTOToReceiptItem;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@Transactional
public class WaiterControllerIntegrationalTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    ExpenseService expenseService;
    @Autowired
    private IMenuItemService menuItemService;
    @Autowired
    private IDrinkCardItemService drinkCardItemService;
    @Autowired
    private IWaiterService waiterService;
    @Autowired
    private IReceiptItemService receiptItemService;
    @Autowired
    private IReceiptService receiptService;
    @Autowired
    private DrinkCardItemDTOToDrinkCardItem drinkCardItemDTOToDrinkCardItem;
    @Autowired
    private MenuItemDTOToMenuItem menuItemDTOToMenuItem;
    @Autowired
    private ReceiptItemDTOToReceiptItem receiptItemDTOToReceiptItem;

    private HttpHeaders headers;

    @BeforeEach
    public void login() {

        ResponseEntity<UserTokenState> responseEntity =
                restTemplate.postForEntity("/api/auth/login",
                        new JwtAuthenticationRequest("ana", "1234"),
                        UserTokenState.class);

        String accessToken = Objects.requireNonNull(responseEntity.getBody()).getAccessToken();
        headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
    }

    @Test
    public void GetMenuItems_ReturnsOk(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<MenuItem[]> responseEntity = restTemplate.exchange("/api/waiter/all-menu-items", HttpMethod.GET, httpEntity, MenuItem[].class );

        MenuItem[] menuItems = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1,menuItems.length);
    }

    @Test
    public void GetDrinkCardItems_ReturnsOk(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<DrinkCardItem[]> responseEntity = restTemplate.exchange("/api/waiter/all-drink-card-items", HttpMethod.GET, httpEntity, DrinkCardItem[].class );

        DrinkCardItem[] menuItems = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1,menuItems.length);
    }

    @Test
    public void UpdateWaiter_ValidWaiter_ReturnsAccepted() {
        WaiterDTO waiterDTO = new WaiterDTO();
        waiterDTO.setId(7);
        waiterDTO.setName("Ana");
        waiterDTO.setLastName("Anicic");
        waiterDTO.setEmailAddress("ana@gmail.com");
        waiterDTO.setUsername("ana");
        waiterDTO.setPassword("1234");
        waiterDTO.setDeleted(false);

        HttpEntity<Object> httpEntity = new HttpEntity<>(waiterDTO, headers);
        ResponseEntity<WaiterDTO> responseEntity = restTemplate.exchange("/api/waiter/update-waiter", HttpMethod.POST, httpEntity, WaiterDTO.class);

        WaiterDTO updatedWaiter = responseEntity.getBody();

        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        assert updatedWaiter != null;
        assertEquals("Anicic", updatedWaiter.getLastName());
    }

    @Test
    public void UpdateWaiter_InvalidWaiter_ReturnsBadRequest() {
        WaiterDTO waiterDTO = new WaiterDTO();
        waiterDTO.setId(17);
        HttpEntity<Object> httpEntity = new HttpEntity<>(waiterDTO, headers);
        ResponseEntity<WaiterDTO> responseEntity = restTemplate.exchange("/api/waiter/update-waiter", HttpMethod.POST, httpEntity, WaiterDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void NewReceipt_ValidTableId_ReturnsCreated() {
        HttpEntity<Object> httpEntity = new HttpEntity<>( headers);
        ResponseEntity<Receipt> responseEntity = restTemplate.exchange("/api/waiter/order/1", HttpMethod.POST, httpEntity, Receipt.class);

        Receipt receipt = responseEntity.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(2, receipt.getId());
    }

    @Test
    public void NewReceipt_InvalidTableId_ReturnsNotFound() {
        HttpEntity<Object> httpEntity = new HttpEntity<>( headers);
        ResponseEntity<Receipt> responseEntity = restTemplate.exchange("/api/waiter/order/15", HttpMethod.POST, httpEntity, Receipt.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void AddDrinkToReceipt_ValidTableIdValidReceiptId_ReturnsCreated() {
        DrinkCardItemDTO drinkCardItemDTO=new DrinkCardItemDTO(1, "cola", "ing" ,"img","desc",new PriceDTO(),1);
        HttpEntity<Object> httpEntity = new HttpEntity<>(drinkCardItemDTO, headers);
        ResponseEntity<?> responseEntity = restTemplate.exchange("/api/waiter/order-drink/1/1", HttpMethod.POST, httpEntity,ResponseEntity.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void AddDrinkToReceipt_InvalidReceiptId_ReturnsNotFound() {
        DrinkCardItemDTO drinkCardItemDTO=new DrinkCardItemDTO(1, "cola", "ing" ,"img","desc",new PriceDTO(),1);
        HttpEntity<Object> httpEntity = new HttpEntity<>(drinkCardItemDTO, headers);
        ResponseEntity<Receipt> responseEntity = restTemplate.exchange("/api/waiter/order-drink/1/15", HttpMethod.POST, httpEntity,Receipt.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void AddMealToReceipt_ValidTableIdValidReceiptId_ReturnsCreated() {
        MenuItemDTO menuItemDTO=new MenuItemDTO(1, "sataras", "ing" ,"img","desc",new PriceDTO(),1,1);
        HttpEntity<Object> httpEntity = new HttpEntity<>(menuItemDTO, headers);
        ResponseEntity<?> responseEntity = restTemplate.exchange("/api/waiter/order-meal/1/1", HttpMethod.POST, httpEntity,ResponseEntity.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void AddMealToReceipt_InvalidReceiptId_ReturnsNotFound() {
        MenuItemDTO menuItemDTO=new MenuItemDTO(1, "sataras", "ing" ,"img","desc",new PriceDTO(),1,1);
        HttpEntity<Object> httpEntity = new HttpEntity<>(menuItemDTO, headers);
        ResponseEntity<Receipt> responseEntity = restTemplate.exchange("/api/waiter/order-meal/1/15", HttpMethod.POST, httpEntity,Receipt.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void GetReceiptItems_ValidReceiptId_ReturnsOk(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<ReceiptItemDTO[]> responseEntity = restTemplate.exchange("/api/waiter/receipt-items/1", HttpMethod.GET, httpEntity, ReceiptItemDTO[].class );

        ReceiptItemDTO[] receiptItems = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2,receiptItems.length);
    }

    @Test
    public void GetReceiptItems_InvalidReceiptId_ReturnsNotFound(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<ReceiptItemDTO> responseEntity = restTemplate.exchange("/api/waiter/receipt-items/15", HttpMethod.GET, httpEntity, ReceiptItemDTO.class );

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void GetOrders_ReturnsOk(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<ReceiptItemDTO[]> responseEntity = restTemplate.exchange("/api/waiter/orders", HttpMethod.GET, httpEntity, ReceiptItemDTO[].class );

        ReceiptItemDTO[] receiptItems = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void ChangeStatus_ValidReceiptItemId_ReturnsOk(){
        HttpEntity<ReceiptItemDTO> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<ReceiptItemDTO> responseEntity = restTemplate.exchange("/api/waiter/2/change-status", HttpMethod.POST, httpEntity, ReceiptItemDTO.class );

        ReceiptItemDTO receiptItem = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(ReceiptItemStatus.DONE, receiptItem.getItemStatus());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void ChangeStatus_InvalidReceiptItemId_ReturnsNotFound(){
        HttpEntity<ReceiptItemDTO> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<ReceiptItemDTO> responseEntity = restTemplate.exchange("/api/waiter/15/change-status", HttpMethod.POST, httpEntity, ReceiptItemDTO.class );

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void UpdateReceiptItem_ValidReceiptItemDTO_ReturnsOk(){
        ReceiptItemDTO receiptItemDTO=new ReceiptItemDTO(2, 1, "new note", ReceiptItemStatus.READY, new ReceiptDTO(), 1);

        HttpEntity<ReceiptItemDTO> httpEntity = new HttpEntity<>(receiptItemDTO,headers);
        ResponseEntity<ReceiptItemDTO> responseEntity = restTemplate.exchange("/api/waiter/update-receipt-item", HttpMethod.POST, httpEntity, ReceiptItemDTO.class );

        ReceiptItemDTO receiptItem = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("new note", receiptItem.getAdditionalNote());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void UpdateReceiptItem_InvalidReceiptItemDTO_ReturnsNotFound(){
        ReceiptItemDTO receiptItemDTO=new ReceiptItemDTO(15, 1, "new note", ReceiptItemStatus.READY, new ReceiptDTO(), 1);

        HttpEntity<ReceiptItemDTO> httpEntity = new HttpEntity<>(receiptItemDTO,headers);
        ResponseEntity<ReceiptItemDTO> responseEntity = restTemplate.exchange("/api/waiter/update-receipt-item", HttpMethod.POST, httpEntity, ReceiptItemDTO.class );

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void DeleteReceiptItem_ValidReceiptItemId_ReturnsOk(){;

        int count = receiptItemService.findAll().size();
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<?> responseEntity = restTemplate.exchange("/api/waiter/delete-receipt-item/2", HttpMethod.POST, httpEntity, ResponseEntity.class );

        int newCount = receiptItemService.findAll().size();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(count-1,newCount);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void DeleteReceiptItem_InvalidReceiptItemId_ReturnsNotFound(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<ReceiptItemDTO> responseEntity = restTemplate.exchange("/api/waiter/delete-receipt-item/15", HttpMethod.POST, httpEntity, ReceiptItemDTO.class );

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
