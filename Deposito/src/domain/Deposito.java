
package domain;

import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.sql.CallableStatement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.sql.SQLException;

public class Deposito {
    int Número;
    String Producto;
    String Cantidad;

    public int getNúmero() {
        return Número;
    }

    public void setNúmero(int Número) {
        this.Número = Número;
    }

    public String getProducto() {
        return Producto;
    }

    public void setProducto(String Producto) {
        this.Producto = Producto;
    }

    public String getCantidad() {
        return Cantidad;
    }

    public void setCantidad(String Cantidad) {
        this.Cantidad = Cantidad;
    }
    
    public void InsertarProducto(JTextField producto, JTextField cantidad){
        setProducto(producto.getText());
        setCantidad(cantidad.getText());
        
        ConexionBD conexion = new ConexionBD();
        
        String consulta = ("insert into Depósito(Producto, Cantidad) values(?,?)");
        
        try {
            
            CallableStatement cs = conexion.estableceConexion().prepareCall(consulta);
            cs.setString(1, getProducto());
            cs.setString(2, getCantidad());
            
            cs.execute();
            
            
            JOptionPane.showMessageDialog(null, "El producto fue añadido correctamente");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "El producto no pudo ser añadido, Error: "+e.toString());
        }
        
    }
    public void mostrarProducto(JTable productos){
        
        ConexionBD conexion = new ConexionBD();
        DefaultTableModel modelo = new DefaultTableModel();
        TableRowSorter<TableModel>OrdenarTable = new TableRowSorter<TableModel>(modelo);
        productos.setRowSorter(OrdenarTable);
        
        String sql = "";
        
        modelo.addColumn("id");
        modelo.addColumn("Producto");
        modelo.addColumn("Cantidad");
        
        productos.setModel(modelo);
        
        sql = "select * from depósito;";
        
        String[] datos = new String[3];
        
        Statement st;
        
        try {
            st= conexion.estableceConexion().createStatement();

            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()){
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                
                modelo.addRow(datos);
            }
            productos.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo mostrar registro. Error: "+e.toString());
        }
    }
    public void seleccion(JTable tablaProducto, JTextField id, JTextField producto, JTextField Cantidad){
        try {
            int fila;
            fila = tablaProducto.getSelectedRow();
            
            if (fila >= 0){
                id.setText((String) tablaProducto.getValueAt(fila, 0));
                producto.setText((String) tablaProducto.getValueAt(fila, 1));
                Cantidad.setText((String) tablaProducto.getValueAt(fila, 2));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Fila no seleccionada. Error: "+e.toString());
        }
        
    }
    public void modificar(JTextField id, JTextField producto, JTextField cantidad){
        setNúmero(Integer.parseInt(id.getText()));
        
        
//        int NumeroCantidad = Integer.parseInt(cantidad.getText()+Integer.parseInt(getCantidad()));
//        String NuevaCantidad = String.valueOf(NumeroCantidad);
        
//        setCantidad(NuevaCantidad);

        setProducto(producto.getText());
        setCantidad(cantidad.getText());
        
        
        ConexionBD conexion = new ConexionBD();
        conexion.estableceConexion();
        
        String consulta = "UPDATE depósito SET depósito.Producto =?, depósito.Cantidad =? WHERE depósito.id=?;";
        try {
            CallableStatement cs = conexion.estableceConexion().prepareCall(consulta);
            
            cs.setString(1, getProducto());
            cs.setString(2, getCantidad());
            cs.setInt(3, getNúmero());
            
            cs.execute();
            JOptionPane.showMessageDialog(null, "Modificación Exitosa");
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No ha podido realizarse la modificación. Error "+e.toString());
        }
        
        
    }
    public void Eliminar(JTextField numero){
        setNúmero(Integer.parseInt(numero.getText()));
        
        ConexionBD conexion = new ConexionBD();
        
        String consulta = "DELETE FROM depósito WHERE id=?;";
        try {
            
            CallableStatement cs = conexion.estableceConexion().prepareCall(consulta);
            cs.setInt(1, getNúmero());
            cs.execute();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se ha eliminado el elemento. Error "+e.toString());
        }
    }
}
