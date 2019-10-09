//
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tateti;

/**
 *
 * @author Equipo
 */
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class TCPClient implements Runnable {

    private Socket socketCliente;
    private DataInputStream inp;
    private DataOutputStream out;
    private int port = 4444;
    private String host;
    private String msg;
    private GameFrame gFrame;

    public TCPClient(int port, String ip) {
        this.port = port;
        this.host = ip;

        try {
            System.out.println(host);
            this.gFrame = new GameFrame(this);
            socketCliente = new Socket(host, port);
            inp = new DataInputStream(socketCliente.getInputStream());
            out = new DataOutputStream(socketCliente.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TCPClient() {

        try {
            System.out.println(host);
            this.gFrame = new GameFrame(this);
            socketCliente = new Socket(host, port);
            inp = new DataInputStream(socketCliente.getInputStream());
            out = new DataOutputStream(socketCliente.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try {
            msg = inp.readUTF();

            String cadena[] = msg.split(";");
            String chipPosition = cadena[0].split(" ")[1];
           // System.out.println("chipPosition " + chipPosition);
            gFrame.getBoard().setPlayer(Integer.parseInt(chipPosition));
            gFrame.getBoard().repaint();
            gFrame.getPl().setTurn(Boolean.valueOf(cadena[1]));
            gFrame.getBoard().repaint();

            while (true) {

                msg = inp.readUTF();

                String[] mensajes = msg.split(";");
                int numberChip = Integer.parseInt(mensajes[0]);
                int rowChip = Integer.parseInt(mensajes[1]);
                int colchip = Integer.parseInt(mensajes[2]);
                int ganar = Integer.parseInt(mensajes[3]);
                System.out.println("turno " + numberChip);

                //asignacion de la imagen que corresponde
                gFrame.getBoard().getBoard()[rowChip][colchip].setImg(numberChip);
                gFrame.getBoard().repaint();

                if (ganar == Integer.parseInt(chipPosition) && ganar != 0) {
                    
                    JOptionPane.showMessageDialog(null, "Gano!", "", JOptionPane.WARNING_MESSAGE);
                } else if (ganar != Integer.parseInt(chipPosition) && ganar != 0) {
                    JOptionPane.showMessageDialog(null, "Perdio!", "", JOptionPane.WARNING_MESSAGE);
                    
                }

                if (numberChip == 2 || numberChip == 1) {
                    gFrame.getPl().setTurn(!gFrame.getPl().getTurn());
                    gFrame.getBoard().getTurnPlayer().setImg((gFrame.getBoard().getTurnPlayer().getImg() == 2) ? 1 : 2);
                }

            }

        } catch (IOException ex) {
            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void movTurno(int idC, int fila, int columna) {
        try {

            if (gFrame.getPl().getTurn()) {
                String datos = "";
                datos += idC + ";";
                datos += fila + ";";
                datos += columna + ";";
                out.writeUTF(datos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
