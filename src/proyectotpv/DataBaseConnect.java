/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectotpv;

import java.sql.*;
import java.time.LocalDateTime;
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
    
    public ResultSet cargaCamareros(){
        try {
            
            String SQL = "SELECT nombre, primer_ape FROM camarero";
            state = con.createStatement();
            result = state.executeQuery(SQL);
            
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public ResultSet cargaMesas(){
        try {
            
            String SQL = "SELECT nombre FROM mesa";
            state = con.createStatement();
            result = state.executeQuery(SQL);
            
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public boolean crearTiquet(String idMesa, String idCamarero){
        try {
            
            String SQL = "INSERT INTO tiquet (id_mesa, id_camarero) " +
                    "VALUES " +
                    "("+ idMesa +", "+ idCamarero +"";
            state = con.createStatement();
            result = state.executeQuery(SQL);
            
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    
    //Todavía hay que implementar este metodo, no está terminado
    public boolean cerrarTiquet(String idTiquet){
        
        Timestamp ts = Timestamp.valueOf(LocalDateTime.now());
        
        try {
            
            String SQL = "UPDATE tiquet SET fecha_cierre = '"+ ts.getTime() +"' WHERE id = "+ idTiquet +"";
            state = con.createStatement();
            result = state.executeQuery(SQL);
            
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    
    public boolean reabrirTiquet(String idTiquet){
        try {
            
            String SQL = "UPDATE tiquet SET fecha_cierre = null WHERE id = "+ idTiquet +"";
            state = con.createStatement();
            result = state.executeQuery(SQL);
            
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    
    public boolean comprobarMesa(String nombreMesa){
        
        boolean existeMesa = false, mesaOcupada = false;
        
        try {
            
            String SQL = "SELECT * FROM tiquet WHERE id_mesa = ( SELECT mesa.id FROM mesa WHERE nombre LIKE '"+ nombreMesa +"')";
            String SQL2 = "";
            state = con.createStatement();
            result = state.executeQuery(SQL);
            
            if(result.next()){
                existeMesa = true;
                SQL2 = "SELECT * FROM tiquet WHERE fecha_cierre is null AND id_mesa = ( SELECT mesa.id FROM mesa WHERE nombre LIKE '"+ nombreMesa +"')";
                state = con.createStatement();
                result = state.executeQuery(SQL);
                
                if(result.next()) mesaOcupada = true;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return existeMesa && mesaOcupada;
    }
    
    public void closeConnection(){
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
