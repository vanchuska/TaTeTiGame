/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tateti;

import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Equipo
 */
public class TCPServer implements Runnable {

    public static final int PORT = 4444;
    public static final int nroConexiones = 2;
    private LinkedList<Socket> jugadores = new LinkedList<Socket>();
    private Boolean turno;
    private int T[][] = new int[3][3];
    private int turnos = 1;

  
    private Thread hiloS;
    private Board b;
    private int contador = 0;

    private LinkedList<Socket> usuarios = new LinkedList<Socket>();
    private Socket socket;
    private int turn;
    private int[][] matrixGame;
    private DataOutputStream out;
    private DataInputStream inp;
    private int contator = 0;
    
    
    private UDPServer servUDP= new UDPServer();

   /*public static void main(String[] args) throws IOException {
        TCPServer serv = new TCPServer();
        serv.listenPlayers();

    }
*/
    public TCPServer() {
    }

    public TCPServer(Socket soc, LinkedList jugs, int xo, int[][] t) {
        socket = soc;
        usuarios = jugs;
        turn = xo;
        matrixGame = t;
    }

    public void cargarMatriz() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                T[i][j] = 0;
            }
        }
    }

    public void listenPlayers(int Puerto) {

        try {
            cargarMatriz();
            ServerSocket socketServidor = new ServerSocket(Puerto);
            System.out.println("Esperando jugadores...");

            while (turnos <= 2) {
                Socket cliente = socketServidor.accept();
                jugadores.add(cliente);

                System.out.println("Cliente conectado");

                int turnP = turnos % 2 == 0 ? 1 : 2;

                turnos++;
                hiloS = new Thread(new TCPServer(cliente, jugadores, turnP, T));
                hiloS.start();

            }
            
                
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("No puede escuchar en el puerto: " + PORT);
            System.exit(-1);
        }
    }

    @Override
    public void run() {

        try {
            inp = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            turno = turn == 1;
            System.out.println("Turno " + turn);
            String msg = "";
            msg += "JUGAR " + (turno ? 1 : 2);
            msg += ";";

            msg += turno;
            System.out.println("msg " + msg);
            out.writeUTF(msg);

            while (true) {

                String recibidos = inp.readUTF();
                String recibido[] = recibidos.split(";");

                int fila = Integer.parseInt(recibido[1]);//
                int columna = Integer.parseInt(recibido[2]);//

                matrixGame[fila][columna] = Integer.parseInt(recibido[0]);

                String info = "";
                info += Integer.parseInt(recibido[0]) + ";";
                info += fila + ";";
                info += columna + ";";

                if ((Integer.parseInt(recibido[0]) == 2 || Integer.parseInt(recibido[0]) == 1) && ganador(Integer.parseInt(recibido[0])) == true) {
                    info += Integer.parseInt(recibido[0]) + ";";

                } else {
                    info += 0 + ";";
                }

                for (Socket user : usuarios) {
                    out = new DataOutputStream(user.getOutputStream());
                    out.writeUTF(info);

                }
            }

        } catch (IOException ex) {

            Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void Ocultar() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print("--" + matrixGame[i][j]);
            }
            System.out.println();
        }
    }

    public boolean ganador(int ficha) {

        int contFic = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (matrixGame[i][j] == ficha) {
                    contador++;
                }
            }
            if (contador == 3) {
                return true;
            } else {
                contador = 0;
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (matrixGame[j][i] == ficha) {
                    contador++;
                }
            }
            if (contador == 3) {
                return true;
            } else {
                contador = 0;
            }
        }

        if (matrixGame[0][0] == ficha && matrixGame[1][1] == ficha && matrixGame[2][2] == ficha) {
            return true;
        }

        if (matrixGame[2][0] == ficha && matrixGame[1][1] == ficha && matrixGame[0][2] == ficha) {
            return true;
        }

        return false;
    }
}
