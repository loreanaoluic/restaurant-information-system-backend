package com.app.restaurant.service.implementation;

import com.app.restaurant.model.*;
import com.app.restaurant.model.users.Chef;
import com.app.restaurant.model.users.HeadBartender;
import com.app.restaurant.repository.RequestRepository;
import com.app.restaurant.service.IRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService implements IRequestService {

    private final RequestRepository requestRepository;
    private final MenuItemService menuItemService;
    private final DrinkCardItemService drinkCardItemService;
    private final MenuService menuService;
    private final DrinkCardService drinkCardService;
    private final PriceService priceService;

    @Autowired
    public RequestService(RequestRepository requestRepository, MenuItemService menuItemService, DrinkCardItemService drinkCardItemService, MenuService menuService, DrinkCardService drinkCardService, PriceService priceService) {
        this.requestRepository = requestRepository;
        this.menuItemService = menuItemService;
        this.drinkCardItemService = drinkCardItemService;
        this.menuService = menuService;
        this.drinkCardService = drinkCardService;
        this.priceService = priceService;
    }

    @Override
    public List<Request> findAll() {
        return this.requestRepository.findAll();
    }

    @Override
    public Request findOne(Integer id) {
        return this.requestRepository.findById(id).orElse(null);
    }

    @Override
    public Request save(Request entity) {
        return this.requestRepository.save(entity);
    }

    @Override
    public Integer createItem(Request request){ //1 - request author is neither head bartender or chef
        if(request.getUser() instanceof Chef){
            MenuItem approvedMenuItem = new MenuItem(0, this.menuService.findOne(1), request.getPreparationTime());
            approvedMenuItem.setPreparationTime(request.getPreparationTime());
            approvedMenuItem.setDescription(request.getDescription());
            approvedMenuItem.setImage(request.getImage());
            approvedMenuItem.setIngredients(request.getIngredients());

            approvedMenuItem = this.menuItemService.save(approvedMenuItem);

            Price price = this.priceService.save(new Price(0, request.getPrice(), System.currentTimeMillis(), -1, approvedMenuItem));
            approvedMenuItem.setPrice(price);
            this.menuItemService.save(approvedMenuItem);

            return 0;
        }else if(request.getUser() instanceof HeadBartender){
            DrinkCardItem approvedDrinkCardItem = new DrinkCardItem(0, this.drinkCardService.findOne(1));
            approvedDrinkCardItem.setDescription(request.getDescription());
            approvedDrinkCardItem.setImage(request.getImage());
            approvedDrinkCardItem.setIngredients(request.getIngredients());

            approvedDrinkCardItem = this.drinkCardItemService.save(approvedDrinkCardItem);

            Price price = this.priceService.save(new Price(0, request.getPrice(), System.currentTimeMillis(), -1, approvedDrinkCardItem));
            approvedDrinkCardItem.setPrice(price);
            this.drinkCardItemService.save(approvedDrinkCardItem);

            return 0;
        }else{
            return 1;
        }
    }
}
