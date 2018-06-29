/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectotpv;

import java.sql.*;

/**
 *
 * @author alb19
 */
public class DataBaseConnect {
    
    String ruta;
    String usuario;
    String password;
    Connection con;
    
    public DataBaseConnect(){
        
        this.ruta = "jdbc:mysql://localhost:3306/tpv";
        this.usuario = "root";
        this.password = "";
        try{
            con = DriverManager.getConnection(ruta, usuario,password);
            System.out.println("Conectado!!");
        }catch(SQLException sqle){
            sqle.getStackTrace();
            System.out.println("Error!!");
        }
    }
    
}
