package com.hotelgo.repository;

import com.hotelgo.config.DatabaseConfig;
import com.hotelgo.model.HotelRoom;

import java.math.BigDecimal;
import java.util.List;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class RoomRepository {
    private final Sql2o sql2o = DatabaseConfig.getSql2o();

    public HotelRoom findById(Long id) {
        String sql = "SELECT id, hotel_id AS hotelId, room_number AS roomNumber, price FROM hotel_rooms WHERE id = :id";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(HotelRoom.class);
        }
    }

    public List<HotelRoom> findByHotelId(Long hotelId) {
        String sql = "SELECT id, hotel_id AS hotelId, room_number AS roomNumber, price, created_at AS createdAt, updated_at AS updatedAt FROM hotel_rooms WHERE hotel_id = :hotelId ORDER BY id";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("hotelId", hotelId)
                    .executeAndFetch(HotelRoom.class);
        }
    }

    public Long createRoom(Long hotelId, String roomNumber, BigDecimal price) {
        String sql = "INSERT INTO hotel_rooms (hotel_id, room_number, price) VALUES (:hotelId, :roomNumber, :price)";
        try (Connection con = sql2o.open()) {
            Object keyObj = con.createQuery(sql, true)
                    .addParameter("hotelId", hotelId)
                    .addParameter("roomNumber", roomNumber)
                    .addParameter("price", price)
                    .executeUpdate()
                    .getKey();
            return keyObj == null ? null : ((Number) keyObj).longValue();
        }
    }

    public boolean updateRoom(Long id, Long hotelId, String roomNumber, BigDecimal price) {
        String sql = "UPDATE hotel_rooms SET hotel_id = :hotelId, room_number = :roomNumber, price = :price WHERE id = :id";
        try (Connection con = sql2o.open()) {
            int updated = con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("hotelId", hotelId)
                    .addParameter("roomNumber", roomNumber)
                    .addParameter("price", price)
                    .executeUpdate()
                    .getResult();
            return updated > 0;
        }
    }

    public boolean deleteRoom(Long id) {
        String sql = "DELETE FROM hotel_rooms WHERE id = :id";
        try (Connection con = sql2o.open()) {
            int deleted = con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate()
                    .getResult();
            return deleted > 0;
        }
    }

    public List<HotelRoom> findAllRooms() {
        String sql = "SELECT id, hotel_id AS hotelId, room_number AS roomNumber, price FROM hotel_rooms";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(HotelRoom.class);
        }
    }
}
