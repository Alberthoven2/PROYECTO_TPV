/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectotpv;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alb19
 */
public class DataBaseConnect {
    
    String ruta;
    String usuario;
    String password;
    Connection con;
    Statement state;
    ResultSet result;
    
    public DataBaseConnect(){
        
        this.ruta = "jdbc:mysql://localhost:3306/tpv";
        this.usuario = "root";
        this.password = "";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(ruta, usuario, password);
            System.out.println("Conectado!!");
        }catch(SQLException sqle){
            sqle.getStackTrace();
            System.out.println("Error!!");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataBaseConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
