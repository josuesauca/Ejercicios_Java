/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chat;

/**
 *
 * @author Josue Sauca
 */
import java.net.Socket;
import java.io.DataOutputStream;
import javax.swing.JOptionPane;

public class Cliente implements Runnable {

    private int puerto;
    private String address = "127.0.0.1";
    private String mensaje;

    private String usuario = "Josue";
    private String password = "josuesauca";
    private String opcion = "";
    private double cantidadDinero = 5000;
    
    public Cliente(int puerto, String address, String mensaje) {
        this.puerto = puerto;
        this.address = address;
        this.mensaje = mensaje;
    }

    public Cliente(int puerto, String mensaje) {
        this.puerto = puerto;
        this.mensaje = mensaje;
    }

    public boolean verificarUsuario(String usuario, String password) {
        return ((this.usuario.equals(usuario)) && (this.password.equals(password)));
    }

    public Cliente() {
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }
    
    public void setCantidadDinero(double cantidadDinero) {
        this.cantidadDinero = cantidadDinero;
    }
    
    public double getCantidadDinero() {
        return cantidadDinero;
    }
    
    public String getUsuario() {
        return usuario;
    }

    @Override
    public void run() {
        DataOutputStream salida;
        Socket socket = null;
        try {
            socket = new Socket(address, puerto);
            salida = new DataOutputStream(socket.getOutputStream());
            salida.writeUTF(mensaje);
            salida.close();
            socket.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
