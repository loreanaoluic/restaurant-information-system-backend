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
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ReceiptDTOToReceipt implements Converter<ReceiptDTO, Receipt> {
  
    private final MenuItemService menuItemService;
    private final DrinkCardItemService drinkCardItemService;
    
    @Autowired
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

        if (dto.getItemDTOs() != null) {
          for(ReceiptItemDTO riDTO : dto.getItemDTOs()){
              ReceiptItem ri = new ReceiptItem();
              ri.setId(riDTO.getId());
              ri.setReceipt(receipt);
              ri.setQuantity(riDTO.getQuantity());
              ri.setItemStatus(riDTO.getStatus());
              ri.setAdditionalNote(riDTO.getAdditionalNote());

              ri.setItem(this.menuItemService.findOne(riDTO.getItemId()));
              ri.setItem(this.drinkCardItemService.findOne(riDTO.getItemId()));

              receipt.getReceiptItems().add(ri);

          }
        }
        
        return receipt;
    }

}



