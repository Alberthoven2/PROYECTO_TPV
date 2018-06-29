/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectotpv;

import java.sql.*;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alb19
 */
public class DataBaseConnect {
    
    private static final String RUTA = "jdbc:mysql://localhost:3306/tpv?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String PASS = "";
    private Connection con;
    private Statement state;
    private ResultSet result;
    
    public DataBaseConnect(){
        
        try {
            con = DriverManager.getConnection(RUTA, USUARIO, PASS);
            System.out.println("Conectado!!");
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnect.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error en la conexión!");
        }
    }
    
}
