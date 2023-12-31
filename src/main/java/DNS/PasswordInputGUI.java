/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DNS;

/**
 *
 * @author Rauld
 */
import javax.swing.*;

public class PasswordInputGUI {
    public static void main(String[] args) {
        JPasswordField pwd = new JPasswordField(10);
        int action = JOptionPane.showConfirmDialog(null, pwd,"Enter Password", JOptionPane.OK_CANCEL_OPTION);
        if (action < 0) JOptionPane.showMessageDialog(null, "Cancelado.");
        else {
            char[] password = pwd.getPassword();
            // Procesa la contraseña
            JOptionPane.showMessageDialog(null, "Contraseña ingresada con éxito.");
        }
    }
}