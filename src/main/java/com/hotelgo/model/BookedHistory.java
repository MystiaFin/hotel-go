package com.hotelgo.model;

import java.sql.Date;
import java.sql.Timestamp;
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
  private Timestamp createdAt;
  private Long bookedBy;
  private Long roomId;
  private Date checkinDate;
  private Date checkoutDate;
  private BookingStatus bookedStatus;
  private Date expirationDate;

  private User user;
  private HotelRoom hotelRoom;

  // Helper method to check if booking is active for a date range
  public boolean isActiveForDateRange(Date start, Date end) {
    if (bookedStatus != BookingStatus.ACTIVE) {
      return false;
    }
    // Check if date ranges overlap
    return !checkoutDate.before(start) && !checkinDate.after(end);
  }

  // Helper method to check if booking has expired
  public boolean isExpired() {
    Date today = new Date(System.currentTimeMillis());
    return expirationDate.before(today);
  }
}
