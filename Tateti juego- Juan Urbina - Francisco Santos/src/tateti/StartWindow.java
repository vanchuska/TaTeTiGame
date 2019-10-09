/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tateti;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.scene.layout.BackgroundRepeat.ROUND;
import javax.swing.*;
/**
 *
 * @author Equipo
 */
public final class StartWindow extends JFrame{
    
    JButton ini_server,ini_cliente, select_Server, ini_partida, select_cartones,BINGO;
    UDPServer _UDPServer;
    UDPClient _UDPClient;
    
     public StartWindow(){
        super("T A - T E -  T I");
        this.setResizable(false);
        this.setSize(605, 628);
        this.setLocationRelativeTo(null);
        this.getContentPane().setLayout(null);
        this.setVisible(true);
        
        super.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent ev){
                System.exit(0);
            }
                });
        this.iniciar();
        this.repaint();
        }
    public void iniciar(){
        ini_server = new JButton("Iniciar como Servidor");
        ini_server.setBounds(100, 200, 200, 50);
        ini_server.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent ev){
                try {
                    IniciarComoServer();
                    
                    
                } catch (UnknownHostException | SocketException ex) {
                    Logger.getLogger(StartWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }});
        
        super.getContentPane().add(ini_server,0);
        
        
        ini_cliente = new JButton("Iniciar como Cliente");
        ini_cliente.setBounds(300, 200, 200, 50);
        ini_cliente.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent ev){
                try {
                    IniciarComoCliente();
                } catch (UnknownHostException ex) {
                    Logger.getLogger(StartWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }});
        super.getContentPane().add(ini_cliente,0);
        this.repaint();
    }
     public void IniciarComoServer() throws UnknownHostException, SocketException{

        this.setVisible(false);
        //StartWindow obj = new StartWindow();
        System.out.println("#################");
        System.out.println("#   SERVIDOR    #");
        System.out.println("#################");
        UDPServer servidor = new UDPServer();
        servidor.setServerThread(new Thread(new UDPServer()));
        servidor.getServerThread().start();
        TCPServer serv = new TCPServer();
        serv.listenPlayers(servidor.serverPort);
        
        
        

     
     }
     public void IniciarComoCliente() throws UnknownHostException{
        System.out.println("#################");
        System.out.println("#   cliente     #");
        System.out.println("#################");
        UDPServer servidor = new UDPServer();
        servidor.setServerThread(new Thread(new UDPClient()));
        servidor.getServerThread().start();
     
     }
}
