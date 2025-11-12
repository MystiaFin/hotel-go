package com.hotelgo.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
  private Timestamp createdAt;
  private String roomId;
  private Long hotelId;
  private BigDecimal price;

  private Hotel hotel;
}
