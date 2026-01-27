/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.biblioteca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author alumne
 */
public class Biblioteca {

    private static final String URL  = "jdbc:h2:file:./data/biblioteca_rodrigo;AUTO_SERVER=TRUE";
    private static final String USER = "sa";
    private static final String PASS = "";

    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
