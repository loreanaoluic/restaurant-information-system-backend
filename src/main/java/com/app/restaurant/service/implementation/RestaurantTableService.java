package com.app.restaurant.service.implementation;

import com.app.restaurant.model.ReceiptItem;
import com.app.restaurant.model.RestaurantTable;
import com.app.restaurant.model.enums.ReceiptItemStatus;
import com.app.restaurant.model.enums.TableStatus;
import com.app.restaurant.repository.RestaurantTableRepository;
import com.app.restaurant.service.IRestaurantTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantTableService implements IRestaurantTableService {

    private final RestaurantTableRepository restaurantTableRepository;

    @Autowired
    public RestaurantTableService(RestaurantTableRepository restaurantTableRepository) {
        this.restaurantTableRepository = restaurantTableRepository;
    }


    @Override
    public List<RestaurantTable> findAll() {
        List<RestaurantTable> restaurantTables = restaurantTableRepository.findAll();
        this.checkAvailability(restaurantTables);
        return restaurantTables;
    }

    public void checkAvailability (List<RestaurantTable> restaurantTables) {
        for (RestaurantTable restaurantTable : restaurantTables) {
            if (restaurantTable.getReceipt() != null) {
                int counter = 0;
                for (ReceiptItem receiptItem : restaurantTable.getReceipt().getReceiptItems()) {
                    if (receiptItem.getItemStatus().equals(ReceiptItemStatus.DONE)) {
                        counter++;
                    }
                }
                if (counter == restaurantTable.getReceipt().getReceiptItems().size()) {
                    restaurantTable.setTableStatus(TableStatus.NOT_OCCUPIED);
                    restaurantTable.setReceipt(null);
                    this.save(restaurantTable);
                } else {
                    restaurantTable.setTableStatus(TableStatus.OCCUPIED);
                    this.save(restaurantTable);
                }
            } else {
                restaurantTable.setTableStatus(TableStatus.NOT_OCCUPIED);
                restaurantTable.setReceipt(null);
                this.save(restaurantTable);
            }
        }
    }

    @Override
    public RestaurantTable findOne(Integer id) {
        return restaurantTableRepository.findById(id).orElse(null);
    }

    @Override
    public RestaurantTable save(RestaurantTable restaurantTable) {
        return restaurantTableRepository.save(restaurantTable);
    }

    @Override
    public void delete(Integer id) {
        RestaurantTable restaurantTable = this.findOne(id);
        restaurantTable.setDeleted(true);
        this.save(restaurantTable);
    }

    @Override
    public RestaurantTable update(RestaurantTable restaurantTable) {
        RestaurantTable found = this.findOne(restaurantTable.getId());
        found.setTableStatus(restaurantTable.getTableStatus());
        found.setTableShape(restaurantTable.getTableShape());
        found.setCoordinateX(restaurantTable.getCoordinateX());
        found.setCoordinateY(restaurantTable.getCoordinateY());
        found.setDeleted(restaurantTable.getDeleted());
        this.save(found);
        return found;
    }
}
