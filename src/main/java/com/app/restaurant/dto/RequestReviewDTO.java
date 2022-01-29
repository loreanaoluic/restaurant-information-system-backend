package com.app.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequestReviewDTO {
    private RequestDTO dto;
    private boolean approved;
}
