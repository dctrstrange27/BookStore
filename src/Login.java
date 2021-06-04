import javax.swing.*;
import java.awt.*;

import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.imageio.*;
import javax.swing.JFrame;
import java.awt.event.*;
public class Login extends JFrame{

    private JPanel pane;
    private JTextField boxUsername;
    private JPasswordField boxPassword;
    private JButton login;
    private JButton clear;
    private JLabel username;
    private JLabel password;
    //img
    private ImageIcon b1;
    private JLabel bb1;

    public Login(){
         pane = new JPanel();
        setBounds(100,100,900,695);
        setContentPane(pane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);

        b1 = new ImageIcon("/home/rohan/IdeaProjects/FinalAct/img/login.png");
        bb1 = new JLabel(b1);
        pane.add(bb1);
        bb1.setBounds(0,-20,900,700);

        Color textBcolor = new Color(76,57,107);
        Color textFontColor = new Color(255,255,255);
        boxUsername = new JTextField();
        boxUsername.setBounds(180,240,240,30);
        boxUsername.setBackground(textBcolor);
        boxUsername.setForeground(textFontColor);
        boxUsername.setBorder(null);
        pane.add(boxUsername);

        boxPassword = new JPasswordField();
        boxPassword.setBounds(180,300,240,30);
        boxPassword.setBackground(textBcolor);
        boxPassword.setForeground(textFontColor);
        boxPassword.setBorder(null);
        pane.add(boxPassword);

        login = new JButton("Login");
        login.setBounds(190,383,72,36);
        login.setFont(new Font("roboto", Font.BOLD, 11));
        login.setBackground(new Color(255,101,132));
        login.setForeground(new Color(255,255,255));
        login.setBorder(null);
        pane.add(login);

        clear = new JButton("cancel");
        clear.setBounds(310,383,72,36);
        clear.setFont(new Font("roboto", Font.BOLD, 11));
        clear.setBackground(new Color(255,101,132));
        clear.setForeground(new Color(255,255,255));
        clear.setBorder(null);
        pane.add(clear);

        clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                boxUsername.setText("");
                boxPassword.setText("");
            }
        });











        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String puname = boxUsername.getText();
                String ppaswd = boxPassword.getText();
                if(puname.equals("Admin") && ppaswd.equals("Admin")) {
                    EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            try {
                                Home frame = new Home("Home");
                                frame.setVisible(true);
                                dispose();
                            } catch (Exception e) { }
                        }
                    });
                } else {
                    JOptionPane.showMessageDialog(null,"Wrong Password / Username");
                    boxUsername.setText("");
                    boxPassword.setText("");
                    boxUsername.requestFocus();
                }
            }
        });
    }
    public static void main(String cms[]){
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login frame = new Login();
                    frame.setVisible(true);
                } catch (Exception e) { }
            }
        });

    }
}








