package com.hotelgo.repository;

import com.hotelgo.config.DatabaseConfig;
import com.hotelgo.model.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class UserRepository {
    private final Sql2o sql2o = DatabaseConfig.getSql2o();

    public User findByEmail(String email) {
        String sql = "SELECT id, nama, email, username, password, role, created_at AS createdAt, updated_at AS updatedAt FROM users WHERE email=:email";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("email", email)
                    .executeAndFetchFirst(User.class);
        }
    }

    public User findByUsername(String username) {
        String sql = "SELECT id, nama, email, username, password, role, created_at AS createdAt, updated_at AS updatedAt FROM users WHERE username=:username";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("username", username)
                    .executeAndFetchFirst(User.class);
        }
    }

    public void save(User user) {
        String sql = "INSERT INTO users(nama, email, username, password, role) VALUES(:nama, :email, :username, :password, :role)";
        try (Connection con = sql2o.beginTransaction()) {
            con.createQuery(sql)
                    .addParameter("nama", user.getNama())
                    .addParameter("email", user.getEmail())
                    .addParameter("username", user.getUsername())
                    .addParameter("password", user.getPassword())
                    .addParameter("role", user.getRole())
                    .executeUpdate();
            con.commit();
        }
    }

    public void updatePassword(String email, String newPassword) {
        String sql = "UPDATE users SET password=:password WHERE email=:email";
        try (Connection con = sql2o.beginTransaction()) {
            con.createQuery(sql)
                    .addParameter("password", newPassword)
                    .addParameter("email", email)
                    .executeUpdate();
            con.commit();
        }
    }

    public void updateUser(User user) {
        String sql = "UPDATE users SET nama=:nama, email=:email WHERE username=:username";
        try (Connection con = sql2o.beginTransaction()) {
            con.createQuery(sql)
                    .addParameter("nama", user.getNama())
                    .addParameter("email", user.getEmail())
                    .addParameter("username", user.getUsername())
                    .executeUpdate();
            con.commit();
        }
    }
}
