package com.hotelgo.repository;

import com.hotelgo.config.DatabaseConfig;
import com.hotelgo.model.HotelRoom;
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
}
