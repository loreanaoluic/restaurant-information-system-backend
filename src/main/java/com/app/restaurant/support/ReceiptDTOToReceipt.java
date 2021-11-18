package com.app.restaurant.support;

import com.app.restaurant.dto.ReceiptDTO;
import com.app.restaurant.dto.ReceiptItemDTO;
import com.app.restaurant.model.Receipt;
import com.app.restaurant.model.ReceiptItem;
import com.app.restaurant.service.implementation.DrinkCardItemService;
import com.app.restaurant.service.implementation.MenuItemService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ReceiptDTOToReceipt implements Converter<ReceiptDTO, Receipt> {

    private final MenuItemService menuItemService;
    private final DrinkCardItemService drinkCardItemService;

    public ReceiptDTOToReceipt(MenuItemService menuItemService, DrinkCardItemService drinkCardItemService) {
        this.menuItemService = menuItemService;
        this.drinkCardItemService = drinkCardItemService;
    }

    @Override
    public Receipt convert(ReceiptDTO dto) {
        Receipt receipt = new Receipt();

        receipt.setId(dto.getId());
        receipt.setIssueDate(dto.getIssueDate());
        receipt.setReceiptItems(new ArrayList<ReceiptItem>());

        for(ReceiptItemDTO riDTO : dto.getItemDTOs()){
            ReceiptItem ri = new ReceiptItem();
            ri.setId(riDTO.getId());
            ri.setReceipt(receipt);
            ri.setQuantity(riDTO.getQuantity());
            ri.setItemStatus(riDTO.getStatus());
            ri.setAdditionalNote(riDTO.getAdditionalNote());
            if(riDTO.getMenuItemId() != null && riDTO.getMenuItemId() > 0) ri.setItem(this.menuItemService.findOne(riDTO.getMenuItemId()));
            else if(riDTO.getDrinkCardItemId() != null && riDTO.getDrinkCardItemId() > 0) ri.setItem(this.drinkCardItemService.findOne(riDTO.getDrinkCardItemId()));



            receipt.getReceiptItems().add(ri);
        }

        return receipt;
    }
}
