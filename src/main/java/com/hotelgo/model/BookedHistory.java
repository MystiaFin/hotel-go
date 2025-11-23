package com.hotelgo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookedHistory {
  private Long id;
  private Long roomId;
  private Long userId;
  private String userName;
  private String bookedStatus;
  private String checkinDate;
  private String checkoutDate;
  private String expirationDate;
  private String createdAt;
  private String updatedAt;
  
  private String hotelName;
  private String hotelLocation;
  private String roomNumber;
  private String roomPrice;
}
