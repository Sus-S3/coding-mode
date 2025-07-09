package com.sus.codecademy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase de prueba para verificar la conexión a la base de datos.
 * Utilizada para testing y debugging de la conexión MySQL.
 */
public class TestConexionBD {
    
    /**
     * Método principal que prueba la conexión a la base de datos.
     * Verifica que el driver MySQL esté disponible y la conexión funcione.
     */
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
