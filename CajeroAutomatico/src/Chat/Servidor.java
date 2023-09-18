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
import java.net.*;
import java.io.*;
import java.util.Arrays;
import java.util.Observable;
import javax.swing.JOptionPane;

public class Servidor extends Observable implements Runnable {

    private int puerto;

    public Servidor(int puerto) {
        this.puerto = puerto;
    }

    @Override
    public void run() {
        ServerSocket servidor = null;
        Socket socket = null;
        DataInputStream entrada;
        Cliente cliente = new Cliente();
        try {
            servidor = new ServerSocket(puerto);
            System.out.println("Servidor inicializado....");
            while (true) {
                socket = servidor.accept();
                entrada = new DataInputStream(socket.getInputStream());
                String mensaje = entrada.readUTF();
                char[] charArray = mensaje.toCharArray();
                String opcionMenu = "";
                String valor = "";
                int contador = 0;

                for (int i = 0; i < charArray.length; i++) {
                    String aux = charArray[i] + "";
                    if (!aux.equals(":")) {
                        opcionMenu += charArray[i];
                        contador++;
                    } else {
                        break;
                    }
                }
                
                charArray = Arrays.copyOfRange(charArray, contador + 1, charArray.length);
                double cantidad = 0;

                if (!opcionMenu.equals("Consultar")) {
                    for (int i = 0; i < charArray.length; i++) {
                        valor += charArray[i];
                    }
                    cantidad = Double.valueOf(valor);
                }
                
                String mensajeInfo = "";

                switch (opcionMenu) {
                    case "Retirar":
                        if (cantidad > cliente.getCantidadDinero()) {
                            JOptionPane.showMessageDialog(null, "No se pudo realizar la operacion");
                             mensajeInfo = "\nNo se pudo realizar la operacion"; 
                        } else {
                            cliente.setCantidadDinero(cliente.getCantidadDinero() - cantidad);
                            JOptionPane.showMessageDialog(null, "Su nuevo saldo es " + cliente.getCantidadDinero());
                            mensajeInfo = "\nSu nuevo saldo es " + cliente.getCantidadDinero(); 
                        }
                        break;
                    case "Depositar":
                        cliente.setCantidadDinero(cantidad + cliente.getCantidadDinero());
                        JOptionPane.showMessageDialog(null, "Su nuevo saldo es " + cliente.getCantidadDinero());
                        mensajeInfo = "\nSu nuevo saldo es " + cliente.getCantidadDinero();
                        break;
                    case "Consultar":
                        JOptionPane.showMessageDialog(null, "Su saldo es " + cliente.getCantidadDinero());
                        mensajeInfo = "\nSu saldo es " + cliente.getCantidadDinero();
                        break;
                }
                
                mensaje = "\n"+mensaje + mensajeInfo;
                this.setChanged();
                this.notifyObservers(mensaje);
                this.clearChanged();
                entrada.close();
                socket.close();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
