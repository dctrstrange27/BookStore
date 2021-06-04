import com.mysql.cj.log.Log;

import javax.naming.ldap.StartTlsRequest;
import javax.swing.*;
import java.awt.*;

import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.imageio.*;
import javax.swing.JFrame;
import java.awt.event.*;
public class Home extends JFrame{

    private JPanel pane;
    private JButton admin;
    private JButton customer;
    private JButton logout;
    private JButton exit;
    private ImageIcon b1;
    private JLabel bb1;


    public Home(String title){
        pane = new JPanel();
        setBounds(100,100,900,695);
        setContentPane(pane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        b1 = new ImageIcon("/home/rohan/IdeaProjects/FinalAct/img/home.png");
        bb1 = new JLabel(b1);
        pane.add(bb1);
        bb1.setBounds(0,-20,900,700);

        admin = new JButton("Admin");
        admin.setBounds(170,243,120,40);

        admin.setBackground(new Color(41,29,62));
        admin.setForeground(new Color(255,255,255));
        admin.setBorderPainted(false);
        admin.setFocusPainted(false);
        admin.setBorder(null);
        pane.add(admin);

        customer = new JButton("Buy Books");
        customer.setBounds(170,300,120,40);
        customer.setFont(new Font("Ubuntu", Font.BOLD, 15));
        customer.setBackground(new Color(41,29,62));
        customer.setForeground(new Color(255,255,255));
        customer.setBorderPainted(false);
        customer.setFocusPainted(false);
        customer.setBorder(null);
        pane.add(customer);


        logout = new JButton("logout");
        logout.setBounds(194,360,80,30);
        logout.setFont(new Font("Ubuntu", Font.BOLD, 15));
        logout.setBackground(new Color(41,29,62));
        logout.setForeground(new Color(255,255,255));
        logout.setBorderPainted(false);
        logout.setFocusPainted(false);
        logout.setBorder(null);
        pane.add(logout);

        exit = new JButton("exit");
        exit.setBounds(194,490,80,30);
        exit.setFont(new Font("Ubuntu", Font.BOLD, 15));
        exit.setBackground(new Color(69,53,96));
        exit.setForeground(new Color(255,255,255));
        exit.setBorderPainted(false);
        exit.setFocusPainted(false);
        exit.setBorder(null);
        pane.add(exit);

        customer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            dispose();
                            customer frame = new customer("Books");
                            frame.setVisible(true);
                            frame.START();
                        } catch (Exception e) { }
                    }
                });
            }
        });


        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            dispose();
                            JOptionPane.showMessageDialog(null,"Byeeee");
                        } catch (Exception e) { }
                    }
                });
            }
        });



        logout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            Login frame = new Login();
                            frame.setVisible(true);
                            dispose();
                        } catch (Exception e) { }
                    }
                });
            }
        });



        admin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                    EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            try {
                                Admin frame = new Admin("Admin");
                                frame.setVisible(true);
                                frame.START();
                                dispose();
                            } catch (Exception e) { }
                        }
                    });
                }
        });
    }
    public static void main(String cms[]){
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Home frame = new Home("Home");
                    frame.setVisible(true);
                } catch (Exception e) { }
            }
        });

    }
}








