/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tateti;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.ir.TryNode;

/**
 *
 * @author ElianaBcrra
 */
public class UDPClient implements Runnable {

    final int puerto = 4444;
    byte[] buffer = new byte[1024];
    UDPServer server = new UDPServer();
    private Thread hiloC;
    int port = 0;

    private TCPServer servTCP = new TCPServer();

    public static void main(String[] args) {

        UDPClient cliente = new UDPClient();

        cliente.hiloC = new Thread(new UDPClient());
        cliente.hiloC.start();

    }

    public UDPClient() {
    }

    @Override
    public void run() {
        try {

            String ipC = "";
            int puertoC = 0;
            boolean band = false;

            while (!band) {
                DatagramSocket socketUDP = new DatagramSocket(puerto);
                DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);/**/
                socketUDP.receive(peticion);

                if (peticion != null) {
                    String mensaje = new String(peticion.getData());
                    InetAddress address = peticion.getAddress();
                    System.out.println(" mensaje recibido:    " + mensaje);
                    String recibido[] = mensaje.split(":");
                    puertoC = Integer.parseInt(recibido[1]);
                    ipC = address.toString();
                    if (Integer.parseInt(recibido[0]) == 123456) {
                        port = Integer.parseInt(recibido[1]);

                        band = true;
                        socketUDP.close();
                    }
                }

            }
            String prueba[] = ipC.split("/");
            Thread hilo = new Thread(new TCPClient(port, prueba[1]));
            hilo.start();
        } catch (SocketException ex) {
            Logger.getLogger(UDPClient.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(UDPClient.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

  
}
