package com.app.restaurant.support;

import com.app.restaurant.dto.PriceDTO;
import com.app.restaurant.model.Price;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PriceDTOToPrice implements Converter<PriceDTO, Price> {

    @Override
    public Price convert(PriceDTO priceDTO) {
        return new Price(priceDTO.getId(), priceDTO.getValue(), priceDTO.getStartDate(), priceDTO.getEndDate());
    }
}
