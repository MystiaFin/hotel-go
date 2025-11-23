package com.hotelgo.repository;

import com.hotelgo.config.DatabaseConfig;
import com.hotelgo.model.BookedHistory;
import com.hotelgo.model.BookingStatus;

import java.util.List;

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

    public boolean isRoomBooked(long roomId) {
        String sql = "SELECT COUNT(*) FROM booked_histories WHERE room_id = :roomId AND booked_status IN ('PENDING', 'ACTIVE') AND (expiration_date IS NULL OR expiration_date > NOW())";
        try (Connection con = sql2o.open()) {
            int count = con.createQuery(sql)
                        .addParameter("roomId", roomId)
                        .executeScalar(Integer.class);
            return count > 0;
        }
    }

    public List<BookedHistory> getBookings(Long userId, String status, boolean excludeActive) {
        String sql = "SELECT b.id AS id, b.room_id AS roomId, b.user_id AS userId, b.booked_status AS bookedStatus, " +
                "b.checkin_date AS checkinDate, b.checkout_date AS checkoutDate, b.created_at AS createdAt, " +
                "b.updated_at AS updatedAt, r.room_number AS roomNumber, r.price AS roomPrice, " +
                "h.name AS hotelName, h.location AS hotelLocation " +
                "FROM booked_histories b " +
                "JOIN hotel_rooms r ON b.room_id = r.id " +
                "JOIN hotels h ON r.hotel_id = h.id " +
                "WHERE b.user_id = :userId ";

        if (status != null && !status.isEmpty()) {
            sql += "AND b.booked_status = :status ";
        } else {
            sql += "AND b.booked_status != 'ACTIVE' ";
        }

        sql += "ORDER BY b.created_at DESC";

        try (Connection con = sql2o.open()) {
            var query = con.createQuery(sql).addParameter("userId", userId);
            if (status != null && !status.isEmpty()) {
                query.addParameter("status", status);
            }
            return query.executeAndFetch(BookedHistory.class);
        }
    }

    public boolean cancelBooking(Long id) {
        try (Connection con = sql2o.open()) {
            con.createQuery("UPDATE booked_histories SET booked_status = 'CANCEL' WHERE id = :id")
                    .addParameter("id", id)
                    .executeUpdate();
            return true;
        }
    }
}
