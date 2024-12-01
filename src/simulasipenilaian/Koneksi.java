/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simulasipenilaian;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Acer
 */
public class Koneksi {

    public Connection dbKoneksi;
    public Statement statement;
    public PreparedStatement preparedStatement;
    public ResultSet resultSet;

    public Koneksi() {
        this.dbKoneksi = null;
    }

    public Connection bukaKoneksi() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            dbKoneksi = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/db_racing?user=root&password=");
            return dbKoneksi;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void tutupKoneksi() {
        try {
            if (statement != null) {
                statement.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (dbKoneksi != null) {
                dbKoneksi.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
