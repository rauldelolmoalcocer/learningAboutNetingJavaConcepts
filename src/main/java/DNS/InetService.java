/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DNS;

import bayern.steinbrecher.jsch.Channel;
import bayern.steinbrecher.jsch.ChannelExec;
import bayern.steinbrecher.jsch.JSch;
import bayern.steinbrecher.jsch.Session;
import java.io.Console;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

/**
 *
 * @author Rauld
 */
public class InetService {

    public static InetAddress HostnameToIP(String hostname) throws UnknownHostException {
        InetAddress addr = InetAddress.getByName(hostname);
        return (addr);
    }

    public static void inicializarConexionSSH(String hostname, String user, String contrasennia) {

        try {
            JSch jsch = new JSch();

            Session session = jsch.getSession(user, hostname, 22);
            session.setPassword(contrasennia);

            session.setConfig("StrictHostKeyChecking", "no");

            session.connect();

            Channel channel = session.openChannel("shell");

            
            OutputStream inputstream_for_the_channel = channel.getOutputStream();
            InputStream outputstream_from_the_channel = channel.getInputStream();

            channel.connect();

            Scanner scan = new Scanner(System.in);

            
            while (true) {
                System.out.println("");
                System.out.println("Dime un comando (o 'salir' para terminar):");
                String command = scan.nextLine();
                if ("salir".equalsIgnoreCase(command)) {
                    break;
                }
                command += "\n";
                inputstream_for_the_channel.write(command.getBytes());
                inputstream_for_the_channel.flush();

                
                Thread.sleep(1000);

                
                while (outputstream_from_the_channel.available() > 0) {
                    byte[] tmp = new byte[1024];
                    int i = outputstream_from_the_channel.read(tmp, 0, 1024);
                    if (i < 0) {
                        break;
                    }
                    System.out.print(new String(tmp, 0, i));
                }
            }

            // Cerrar recursos
            inputstream_for_the_channel.close();
            outputstream_from_the_channel.close();
            channel.disconnect();
            session.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void inicializarServerSSH() {
        try {

            Console console = System.console();
            Scanner scan = new Scanner(System.in);

            System.out.println("Dime el nombre de host que quiere que me conecte");
            String host = scan.nextLine();

            InetAddress addr = HostnameToIP(host);

            System.out.println("Constrasennia:");
            String password = scan.nextLine();

            System.out.println("Usuario:");
            String user = scan.nextLine();
            System.out.println(addr.getHostAddress());
            inicializarConexionSSH(addr.getHostAddress(), user, password);

        } catch (UnknownHostException ex) {
            Logger.getLogger(InetService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
