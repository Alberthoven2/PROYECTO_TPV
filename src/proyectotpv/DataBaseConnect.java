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
    
    protected ResultSet cargaCamareros(){
        try {
            
            String SQL = "SELECT id, nombre, primer_ape FROM camarero";
            state = con.createStatement();
            result = state.executeQuery(SQL);
            
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    protected ResultSet cargaMesas(){
        try {
            
            String SQL = "SELECT id, nombre FROM mesa";
            state = con.createStatement();
            result = state.executeQuery(SQL);
            
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    protected boolean crearTiquet(int idMesa, int idCamarero){
        
        int rows = 0;
        try {
            
            String SQL = "INSERT INTO tiquet (id_mesa, id_camarero) " +
                    "VALUES " +
                    "("+ idMesa +", "+ idCamarero +")";
            state = con.createStatement();
            rows = state.executeUpdate(SQL);
            if(rows != 0) return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rows != 0;
    }
    
    //Todavía hay que implementar este metodo, no está terminado
    protected boolean cerrarTiquet(int idTiquet){
        int rows = 0;
        try {
            
            String SQL = "UPDATE tiquet SET fecha_cierre = CURRENT_TIMESTAMP WHERE id = "+ idTiquet;
            state = con.createStatement();
            rows = state.executeUpdate(SQL);
            if(rows != 0) return true;
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rows != 0;
    }
    //De momento no estoy usando este método.
    protected boolean reabrirTiquet(String idTiquet){
        try {
            
            String SQL = "UPDATE tiquet SET fecha_cierre = null WHERE id = "+ idTiquet +"";
            state = con.createStatement();
            result = state.executeQuery(SQL);
            
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    
    protected boolean comprobarMesa(String nombreMesa){
        
        boolean existeMesa = false, mesaOcupada = false;
        
        try {
            
            String SQL = "SELECT * FROM tiquet WHERE fecha_cierre is null AND id_mesa = ( SELECT mesa.id FROM mesa WHERE nombre LIKE '"+ nombreMesa +"')";
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
    
    protected ResultSet getDatosTabla(int idTiquet){
        try {
            
            String SQL = "SELECT  t.id as 'id_tiquet', p.id as 'id_producto', " +
                    "p.nombre_producto, p.precio_unidad, tp.cantidad, ROUND(tp.cantidad * p.precio_unidad, 2) as 'total' " +
                    "FROM " +
                    "tiquet t INNER JOIN tiquet_producto tp " +
                    "ON t.id = tp.id_tiquet " +
                    "INNER JOIN producto p " +
                    "ON p.id = tp.id_producto " +
                    "WHERE t.id = "+ idTiquet;
            state = con.createStatement();
            result = state.executeQuery(SQL);
            
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    protected int compruebaTiquet(int idMesa, int idCamarero){
        int idTiquet = 0;
        try {
            
            String SQL = "SELECT id FROM tiquet WHERE id_mesa = "+ idMesa +" AND id_camarero = "+ idCamarero +" AND fecha_cierre is null";
            state = con.createStatement();
            result = state.executeQuery(SQL);
            
            if(result.next()) idTiquet = result.getInt("id");
            else idTiquet = 0;
            
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idTiquet;
    }
    
    protected boolean agregarProducto(int idTiquet, int idProducto){
        return true;
    }
    
    protected boolean agregarProducto(int idTiquet, int idProducto, int cantidad){
        return true;
    }
    
    protected void closeConnection(){
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
