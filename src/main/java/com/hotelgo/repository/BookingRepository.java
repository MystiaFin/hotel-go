package com.hotelgo.repository;

import com.hotelgo.config.DatabaseConfig;
import com.hotelgo.model.BookingStatus;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class BookingRepository {
    private final Sql2o sql2o = DatabaseConfig.getSql2o();

    public int updateExpirePendingBookings() {
        String sql = "UPDATE booked_histories SET booked_status = 'EXPIRED' WHERE booked_status = 'PENDING' AND expiration_date < NOW()";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeUpdate().getResult();
        }
    }

    public boolean createBooking(long userId, long roomId) {
        String sql = "INSERT INTO booked_histories (user_id, room_id, booked_status, expiration_date) VALUES (:userId, :roomId, :bookedStatus, DATE_ADD(NOW(), INTERVAL 1 HOUR))";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("userId", userId)
                    .addParameter("roomId", roomId)
                    .addParameter("bookedStatus", BookingStatus.PENDING.name())
                    .executeUpdate();
            return true;
        }
    }
}
