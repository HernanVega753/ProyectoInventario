
package domain;

import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.DriverManager;

public class ConexionBD {
    
    Connection conectar = null;
    private String usuario = "root";
    private String password = "Corona!1989";
    private String ip = "localhost";
    private String puerto = "3306";
    private String bd = "Depósito";
    
    String cadena = "jdbc:mysql://"+ip+":"+puerto+"/"+bd;
    
    public Connection estableceConexion(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conectar = DriverManager.getConnection(cadena,usuario,password);
            //JOptionPane.showMessageDialog(null, "Se estableció la conexión correctamente");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "La conexión no ha podido establecerse, Error: "+e.toString());
        }
        return conectar;
    }
}
