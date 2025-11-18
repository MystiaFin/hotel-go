package com.hotelgo.repository;

import com.hotelgo.config.DatabaseConfig;
import com.hotelgo.model.Hotel;
import com.hotelgo.model.HotelRoom;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

public class HotelRepository {
    private final Sql2o sql2o = DatabaseConfig.getSql2o();

    public List<Hotel> findAllHotels() {
        String sql = "SELECT DISTINCT h.id, h.name, h.location, h.created_at AS createdAt, h.updated_at AS updatedAt FROM hotels h JOIN hotel_rooms r ON h.id = r.hotel_id";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Hotel.class);
        }
    }

    public List<HotelRoom> findRoomsByHotelId(long hotelId) {
        String sql = "SELECT r.id, r.hotel_id AS hotelId, r.room_number AS roomNumber, r.price, CASE WHEN EXISTS (SELECT 1 FROM booked_histories b WHERE b.room_id = r.id AND b.booked_status IN ('PENDING','ACTIVE')) THEN 0 ELSE 1 END AS isAvailable, r.created_at AS createdAt, r.updated_at AS updatedAt FROM hotel_rooms r WHERE r.hotel_id = :hotelId";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("hotelId", hotelId)
                    .executeAndFetch(HotelRoom.class);
        }
    }

    public Hotel getHotelById(Long id) {
        String sql = "SELECT id, name, location FROM hotels WHERE id = :id";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Hotel.class);
        }
    }
}
