package com.hotelgo.model;

public enum BookingStatus {
  PENDING,
  ACTIVE,
  CANCELLED,
  EXPIRED,
  COMPLETED;

  public static BookingStatus fromString(String status) {
    if (status == null) {
      return null;
    }
    try {
      return BookingStatus.valueOf(status.toUpperCase());
    } catch (IllegalArgumentException e) {
      return null;
    }
  }
}
