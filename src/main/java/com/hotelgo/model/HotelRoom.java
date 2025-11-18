package com.hotelgo.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelRoom {
    private Long id;
    private Long hotelId;
    private String roomNumber;
    private BigDecimal price;
    private boolean isAvailable;
    private String createdAt;
    private String updatedAt;
}
