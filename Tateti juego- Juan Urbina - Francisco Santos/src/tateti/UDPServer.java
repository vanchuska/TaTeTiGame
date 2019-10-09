/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tateti;

import java.io.IOException;
import static java.lang.System.out;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ElianaBcrra
 */
public class UDPServer implements Runnable {

    final int serverPort = 4444;
    byte[] buffer = new byte[1024];
    private Thread serverThread;
    private int contador = 0;

    public Thread getServerThread() {
        return serverThread;
    }

     public static void main(String[] args) {
         
    UDPServer serverUdp = new UDPServer();
    serverUdp.serverThread = new Thread(new UDPServer());
    serverUdp.serverThread.start();
    TCPServer serv = new TCPServer();
    serv.listenPlayers(serverUdp.serverPort);
    }
    public void setServerThread(Thread serverThread) {
        this.serverThread = serverThread;
    }

    public UDPServer() {
    }

    @Override
    public void run() {

        try {
            //InetAddress address_server = InetAddress.getByName("localhost");
            //NetworkInterface nif = NetworkInterface.getByName("wlan0");
            NetworkInterface nif = NetworkInterface.getByName("eth3");// aqui colocar el nombre de la maquina
            if(nif.isUp()){ System.out.println("esta corriendo");} 
            List<InterfaceAddress> addressEnum = nif.getInterfaceAddresses();
            InterfaceAddress address = addressEnum.get(0);
            InetAddress localAddress = address.getAddress();
            InetAddress broadCastAddress = address.getBroadcast();
            System.out.println("Datos maquina: " + localAddress);
            //System.out.println("Datos maquina" + broadCastAddress);
            String BCAddress = new String();
            BCAddress=broadCastAddress.getHostAddress().toString();
            System.out.println("Datos maquina: " + BCAddress);      
  
            DatagramSocket socketUDP = new DatagramSocket();

            while (true) {

                String mensaje = "123456" + ":" + serverPort + ":";
                buffer = mensaje.getBytes();
                DatagramPacket msjEnv = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(BCAddress), serverPort);
                socketUDP.send(msjEnv);
            }

        } catch (SocketException ex) {
            Logger.getLogger(UDPServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(UDPServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UDPServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

   
}
