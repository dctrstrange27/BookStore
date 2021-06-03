import com.mysql.cj.log.Log;

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
        b1 = new ImageIcon("/home/rohan/IdeaProjects/FinalAct/img/Group 11.png");
        bb1 = new JLabel(b1);
        pane.add(bb1);
        bb1.setBounds(0,-20,900,700);

        admin = new JButton("Admin");
        admin.setBounds(170,250,120,40);
        admin.setFont(new Font("Ubuntu,thin", Font.PLAIN, 18));
        admin.setBackground(new Color(41,29,62));
        admin.setForeground(new Color(255,255,255));
        admin.setBorderPainted(false);
        admin.setFocusPainted(false);
        admin.setBorder(null);
        pane.add(admin);

        customer = new JButton("Customer");
        customer.setBounds(170,335,120,40);
        customer.setFont(new Font("Ubuntu,thin", Font.PLAIN, 18));
        customer.setBackground(new Color(41,29,62));
        customer.setForeground(new Color(255,255,255));
        customer.setBorderPainted(false);
        customer.setFocusPainted(false);
        customer.setBorder(null);
        pane.add(customer);


        logout = new JButton("logout");
        logout.setBounds(194,493,80,30);
        logout.setFont(new Font("Ubuntu,thin", Font.PLAIN, 12));
        logout.setBackground(new Color(41,29,62));
        logout.setForeground(new Color(255,255,255));
        logout.setBorderPainted(false);
        logout.setFocusPainted(false);
        logout.setBorder(null);
        pane.add(logout);

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








