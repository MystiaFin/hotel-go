package com.hotelgo.config;

import java.sql.Connection;
import java.sql.DriverManager;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.sql2o.Sql2o;

public class DatabaseConfig {
  private static Sql2o sql2o;

  // Get database connection properties from environment variables
  private static final String DB_HOST = System.getenv().getOrDefault("DB_HOST", "localhost");
  private static final String DB_PORT = System.getenv().getOrDefault("DB_PORT", "3306");
  private static final String DB_NAME = System.getenv().getOrDefault("DB_NAME", "hotelgo_db");
  private static final String DB_USER = System.getenv().getOrDefault("DB_USER", "root");
  private static final String DB_PASSWORD = System.getenv().getOrDefault("DB_PASSWORD", "password");

  private static final String DB_URL =
      String.format(
          "jdbc:mysql://%s:%s/%s?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true",
          DB_HOST, DB_PORT, DB_NAME);

  public static Sql2o getSql2o() {
    if (sql2o == null) {
      sql2o = new Sql2o(DB_URL, DB_USER, DB_PASSWORD);
    }
    return sql2o;
  }

  public static void runMigrations() {
    try {
      // Load MySQL driver
      Class.forName("com.mysql.cj.jdbc.Driver");

      System.out.println("Connecting to database: " + DB_URL);
      System.out.println("Database user: " + DB_USER);

      // Wait for database to be ready (important for Docker)
      int maxRetries = 30;
      int retryCount = 0;
      Connection connection = null;

      while (retryCount < maxRetries) {
        try {
          connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
          System.out.println("Successfully connected to database!");
          break;
        } catch (Exception e) {
          retryCount++;
          if (retryCount >= maxRetries) {
            throw e;
          }
          System.out.println(
              "Waiting for database to be ready... (attempt "
                  + retryCount
                  + "/"
                  + maxRetries
                  + ")");
          Thread.sleep(2000); // Wait 2 seconds before retry
        }
      }

      if (connection == null) {
        throw new RuntimeException(
            "Failed to connect to database after " + maxRetries + " attempts");
      }

      // Setup Liquibase
      Database database =
          DatabaseFactory.getInstance()
              .findCorrectDatabaseImplementation(new JdbcConnection(connection));

      Liquibase liquibase =
          new Liquibase(
              "db/changelog/db.changelog-master.xml", new ClassLoaderResourceAccessor(), database);

      // Run migrations
      System.out.println("Running database migrations...");
      liquibase.update("");

      System.out.println("Database migrations completed successfully!");

      connection.close();

    } catch (Exception e) {
      System.err.println("Error running database migrations: " + e.getMessage());
      e.printStackTrace();
      throw new RuntimeException("Failed to run database migrations", e);
    }
  }
}
