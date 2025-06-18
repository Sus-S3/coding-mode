package com.sus.codecademy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class TestConexionBD {
    public static void main(String[] args) {
        String URL_DB = "jdbc:mysql://localhost:3306/codeacademy";
        String USER_DB = "root";
        String PASSWORD_DB = "SusanaSuarez";

        try {
            // Cambiar el driver a MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB);
            System.out.println("Conexión exitosa: " + conexion);
            conexion.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Error: MySQL JDBC Driver no encontrado.");
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }
}
