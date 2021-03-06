import com.mysql.cj.log.Log;

import javax.swing.plaf.ScrollBarUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.Color;
import java.awt.Font;
import java.awt.color.CMMException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.*;



public class Admin extends JFrame {
    public JPanel contentPane;
    private JTextField searchfield;
    private JTextField boxBookISBN;
    private JTextField boxBookISBN2;
    private JTextField boxBookAuthor;
    private JTextField boxBookAuthor2;
    private JTextField boxBookPrice;
    private JTextField boxBookPrice2;
    private JTextField boxBookTitle;
    private JTextField boxBookTitle2;
    private JButton add;
    private JButton clear;
    private JButton clear2;
    private JButton Save;
    private JButton Remove;
    private JButton logout;
    private JButton home;
    private JButton customerB;
    private Connect con;
    private JTable table;
    private ImageIcon b1;
    private JLabel bb1;
    private JTextField filter;


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Admin con = new Admin("BOOK STORE");
                    con.setVisible(true);
                    con.START();
                } catch (Exception e) { }
            }
        });
    }

    public void START() throws Exception {
        con = new Connect("localhost", 3306, "FinalActivity", "root", "password");
        con.query(null);
        loadTable();
    }

    public void loadTable() {
        table.setModel(con.getContent());
    }

    public void remove()throws SQLException{
        PreparedStatement ps = con.getConnection().prepareStatement("DELETE FROM Books WHERE Book_ID=?");
        ps.setInt(1,Integer.parseInt(table.getValueAt(table.getSelectedRow(),0).toString()));
        con.update(ps);
        con.query(null);
        loadTable();
        clearSelection();
    }

    public void select(String s) {
        con.query(s);
        loadTable();
    }

    public void create()throws SQLException{
        PreparedStatement ps = con.getConnection().prepareStatement("INSERT INTO Books(ISBN,Title,Price,Author) values (?,?,?,?)");
        ps.setFloat(1,Float.parseFloat(boxBookISBN2.getText()));
        ps.setString(2,(boxBookTitle2.getText()));
        ps.setInt(3,Integer.parseInt(boxBookPrice2.getText()));
        ps.setString(4,(boxBookAuthor2.getText()));

        clear(new JTextField[]{boxBookISBN2, boxBookAuthor2, boxBookPrice2, boxBookTitle2});

        con.update(ps);
        con.query(null);
        loadTable();
    }

    public void clear(JTextField [] fields){
        for(JTextField f : fields) f.setText("");
    }

    public void update(PreparedStatement s) {
        if (con.update(s)) {
            con.query(null);
            loadTable();
        } else JOptionPane.showMessageDialog(this, "Error Updating Books, Please Check Ur Inputs");
    }

    public void clearSelection() {
        Save.setText("Select");
        clear(new JTextField[] {boxBookISBN, boxBookAuthor, boxBookPrice, boxBookTitle});
        Remove.setEnabled(false);
        boxBookISBN.setEnabled(false);
        boxBookAuthor.setEnabled(false);
        boxBookPrice.setEnabled(false);
        boxBookTitle.setEnabled(false);
    }

    public void setColorForSelected(){
        Color selectedColor = new Color(178, 179, 189);
        Color selectedInput = new Color(255,255,255);
        boxBookISBN.setBackground(selectedColor);
        boxBookTitle.setBackground(selectedColor);
        boxBookPrice.setBackground(selectedColor);
        boxBookAuthor.setBackground(selectedColor);
        boxBookISBN.setForeground(selectedInput);
        boxBookTitle.setForeground(selectedInput);
        boxBookPrice.setForeground(selectedInput);
        boxBookAuthor.setForeground(selectedInput);
    }

    public void enableEdit() {
        clearSelection();
        boxBookISBN.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
        boxBookAuthor.setText(table.getValueAt(table.getSelectedRow(), 2).toString());
        boxBookPrice.setText(table.getValueAt(table.getSelectedRow(), 3).toString());
        boxBookTitle.setText(table.getValueAt(table.getSelectedRow(), 4).toString());
        Save.setText("Update");
        Remove.setEnabled(true);
        boxBookISBN.setEnabled(true);
        boxBookAuthor.setEnabled(true);
        boxBookPrice.setEnabled(true);
        boxBookTitle.setEnabled(true);
    }

    public Admin(String title) {

        super(title);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setBounds(400,150,1100,795);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);

        Color textBoxColor = new Color(51,38,71);


        b1 = new ImageIcon("/home/rohan/IdeaProjects/FinalAct/img/admin.png");
        bb1 = new JLabel(b1);
        contentPane.add(bb1);
        bb1.setBounds(0,-20,1100,795);

        JLabel lblProducts = new JLabel("BOOKS");
        lblProducts.setFont(new Font("Montserrat", Font.PLAIN, 18));
        lblProducts.setBounds(37, 30, 155, 15);
        contentPane.add(lblProducts);

        JButton search = new JButton("Search");
        search.setFont(new Font("Ubuntu,thin", Font.PLAIN, 10));
        search.setBounds(970, 15, 50, 20);
        search.setBackground(new Color(47,33,70));
        search.setBorder(null);
        search.setForeground(Color.WHITE);
        search.addActionListener(e -> {
            con.query(String.format("Select * from Books where %s like '%%%s%%'",filter.getText(), searchfield.getText()));
            loadTable();
        });
        contentPane.add(search);


        filter = new JTextField();
        filter.setFont(new Font("Gotham Pro", Font.PLAIN, 10));
        filter.setBackground(new Color(47,33,70));
        filter.setBounds(600, 15, 120, 20);
        filter.setBorder(null);
        filter.setForeground(Color.WHITE);
        filter.setCaretColor(Color.WHITE);
        contentPane.add(filter);


        searchfield = new JTextField();
        searchfield.setFont(new Font("Ubuntu,thin", Font.PLAIN, 10));
        searchfield.setBounds(790, 18, 120, 20);
        searchfield.setBackground(new Color(47,33,70));
        searchfield.setBorder(null);
        searchfield.setForeground(Color.WHITE);
        searchfield.setColumns(10);
        searchfield.setCaretColor(Color.WHITE);
        contentPane.add(searchfield);
        searchfield.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                clearSelection();
                String text = searchfield.getText();
                if (text.trim().length() == 0) {
                    con.query(null);
                    loadTable();
                } else con.query(String.format("Select * from Books where Book_ID = %s", searchfield.getText()));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                clearSelection();
                String text = searchfield.getText();
                if (text.trim().length() == 0) {
                    con.query(null);
                    loadTable();
                } else con.query(String.format("Select * from Books where Book_ID = %s", searchfield.getText()));
            }

            @Override
            public void changedUpdate(DocumentEvent e) { throw new UnsupportedOperationException("");}
        });




        boxBookISBN = new JTextField();
        boxBookISBN.setEnabled(false);
        boxBookISBN.setBounds(354, 493, 130, 20);
        boxBookISBN.setBackground(textBoxColor);
        boxBookISBN.setBorder(null);
        boxBookISBN.setForeground(Color.WHITE);
        boxBookISBN.setCaretColor(Color.WHITE);
        contentPane.add(boxBookISBN);
        boxBookISBN.setColumns(10);

        boxBookAuthor = new JTextField();
        boxBookAuthor.setEnabled(false);
        boxBookAuthor.setBounds(354, 493+33+33+30, 130, 20);
        boxBookAuthor.setBackground(textBoxColor);
        boxBookAuthor.setBorder(null);
        boxBookAuthor.setForeground(Color.WHITE);
        boxBookAuthor.setCaretColor(Color.WHITE);
        contentPane.add(boxBookAuthor);
        boxBookAuthor.setColumns(10);

        boxBookPrice = new JTextField();
        boxBookPrice.setEnabled(false);
        boxBookPrice.setBounds(354, 525+32, 130, 20);
        boxBookPrice.setBackground(textBoxColor);
        boxBookPrice.setBorder(null);
        boxBookPrice.setForeground(Color.WHITE);
        boxBookPrice.setCaretColor(Color.WHITE);
        contentPane.add(boxBookPrice);
        boxBookPrice.setColumns(10);

        boxBookTitle = new JTextField();
        boxBookTitle.setEnabled(false);
        boxBookTitle.setBounds(354, 493+32, 130, 20);
        boxBookTitle.setBackground(textBoxColor);
        boxBookTitle.setBorder(null);
        boxBookTitle.setCaretColor(Color.WHITE);
        boxBookTitle.setForeground(Color.WHITE);
        contentPane.add(boxBookTitle);
        boxBookTitle.setColumns(10);

        int x_axis = 690;
        boxBookISBN2 = new JTextField();
        boxBookISBN2.setBounds(x_axis, 495, 130, 20);
        boxBookISBN2.setBackground(textBoxColor);
        boxBookISBN2.setBorder(null);
        boxBookISBN2.setForeground(Color.WHITE);
        boxBookISBN2.setCaretColor(Color.WHITE);
        contentPane.add(boxBookISBN2);
        boxBookISBN2.setColumns(10);

        boxBookAuthor2 = new JTextField();
        boxBookAuthor2.setBounds(x_axis, 493+(33)*3, 130, 20);
        boxBookAuthor2.setBackground(textBoxColor);
        boxBookAuthor2.setBorder(null);
        boxBookAuthor2.setForeground(Color.WHITE);
        boxBookAuthor2.setCaretColor(Color.WHITE);
        contentPane.add(boxBookAuthor2);
        boxBookAuthor2.setColumns(10);

        boxBookPrice2 = new JTextField();
        boxBookPrice2.setBounds(x_axis, 493+66, 130, 20);
        boxBookPrice2.setBackground(textBoxColor);
        boxBookPrice2.setBorder(null);
        boxBookPrice2.setForeground(Color.WHITE);
        boxBookPrice2.setCaretColor(Color.WHITE);
        contentPane.add(boxBookPrice2);
        boxBookPrice2.setColumns(10);

        boxBookTitle2 = new JTextField();
        boxBookTitle2.setBounds(x_axis, 493+33, 130, 20);
        boxBookTitle2.setBackground(textBoxColor);
        boxBookTitle2.setBorder(null);
        boxBookTitle2.setCaretColor(Color.WHITE);
        boxBookTitle2.setForeground(Color.WHITE);
        contentPane.add(boxBookTitle2);
        boxBookTitle2.setColumns(10);

        Color colorButton = new Color(47,46,65);
        logout = new JButton("logout");
        logout.setBounds(50,14,40,20);
        logout.setBackground(new Color(95,72,135));
        logout.setForeground(new Color(255,255,255));
        logout.setFont(new Font("Ubuntu", Font.PLAIN, 11));
        logout.setBorderPainted(false);
        logout.setFocusPainted(false);
        logout.setBorder(null);
        contentPane.add(logout);

        logout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            dispose();
                            JOptionPane.showMessageDialog(null,"Logout");
                            Login frame = new Login();
                            frame.setVisible(true);
                        } catch (Exception e) { }
                    }
                });
            }
        });

        home = new JButton("home");
        home.setBounds(180,14,40,20);
        home.setBackground(new Color(41,29,62));
        home.setForeground(new Color(255,255,255));
        home.setFont(new Font("Ubuntu", Font.BOLD, 11));
        home.setBorderPainted(false);
        home.setFocusPainted(false);
        home.setBorder(null);
        contentPane.add(home);

        home.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            dispose();
                            JOptionPane.showMessageDialog(null,"Going back to home");
                            Home frame = new Home("Home");
                            frame.setVisible(true);
                        } catch (Exception e) { }
                    }
                });
            }
        });

        customerB = new JButton("Buy Books");
        customerB.setBounds(290,14,80,20);
        customerB.setBackground(new Color(41,29,62));
        customerB.setForeground(new Color(255,255,255));
        customerB.setFont(new Font("Ubuntu", Font.BOLD, 11));
        customerB.setBorderPainted(false);
        customerB.setFocusPainted(false);
        customerB.setBorder(null);
        contentPane.add(customerB);

        home.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            dispose();
                            JOptionPane.showMessageDialog(null,"Going back to home");
                            Home frame = new Home("Home");
                            frame.setVisible(true);
                        } catch (Exception e) { }
                    }
                });
            }
        });





        add = new JButton("Add");
        add.setForeground(Color.WHITE);
        add.setFont(new Font("Ubuntu,thin", Font.PLAIN, 11));
        add.setBackground(colorButton);
        add.setBounds(630, 683, 70, 23);
        add.setBorderPainted(false);
        add.setFocusPainted(false);
        add.addActionListener(e->{ try{ create(); }catch(Exception ex){ JOptionPane.showMessageDialog(this, "Unable To Create Product"); }});
        contentPane.add(add);

        clear = new JButton("cancel");
        clear.setForeground(Color.WHITE);
        clear.setFont(new Font("Ubuntu,thin", Font.PLAIN, 11));
        clear.setBackground(colorButton);
        clear.setBounds(735, 683, 70, 23);
        clear.setBorderPainted(false);
        clear.setFocusPainted(false);
        clear.addActionListener(e->{ clear(new JTextField[]{boxBookISBN2, boxBookAuthor2, boxBookPrice2, boxBookTitle2}); });
        contentPane.add(clear);

        clear2 = new JButton("cancel");
        clear2.setForeground(Color.WHITE);
        clear2.setFont(new Font("Ubuntu,thin", Font.PLAIN, 11));
        clear2.setBackground(colorButton);
        clear2.setBounds(430, 683, 70, 23);
        clear2.setBorderPainted(false);
        clear2.setFocusPainted(false);
        contentPane.add(clear2);

        clear2.addActionListener(e->{
            clear(new JTextField[]{boxBookISBN, boxBookAuthor, boxBookPrice, boxBookTitle});
        });




        Save = new JButton("Select");
        Save.addActionListener(e -> {
            try {
                if (Save.getText().equals("Select")) enableEdit();
                else{
                    try {
                        PreparedStatement ps = con.getConnection().prepareStatement(
                                "UPDATE Books SET ISBN=?, Title=?, Price=?,Author=? WHERE Book_ID=?");
                        ps.setFloat(1,Float.parseFloat(boxBookISBN.getText()));
                        ps.setString(2,(boxBookTitle.getText()));
                        ps.setInt(3,Integer.parseInt(boxBookPrice.getText()));
                        ps.setString(4,(boxBookAuthor.getText()));
                        ps.setInt(5,Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString()));
                        update(ps);
                    } catch (SQLException ex) { }
                    clearSelection();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "You Haven't Selected A BOOK");
                return;
            }
        });
        Save.setForeground(Color.WHITE);
        Save.setFont(new Font("Ubuntu,thin", Font.PLAIN, 11));
        Save.setBackground(colorButton);
        Save.setBounds(270, 683, 70, 23);
        Save.setBorderPainted(false);
        Save.setFocusPainted(false);
        contentPane.add(Save);


        Remove = new JButton("Remove");
        Remove.setEnabled(true);
        Remove.setFont(new Font("Ubuntu,thin", Font.PLAIN, 11));
        Remove.setBackground(colorButton);
        Remove.setForeground(Color.white);
        Remove.setBounds(350, 683, 70, 23);
        Remove.setBorderPainted(false);
        Remove.setFocusPainted(false);
        Remove.setBorder(null);
        Remove.addActionListener(e->{ try{ remove(); }catch(SQLException ex){  } });
        contentPane.add(Remove);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(126, 110, 833, 250);

        contentPane.add(scrollPane);
        table = new JTable();
        scrollPane.setViewportView(table);
        scrollPane.setViewportBorder(null);
        scrollPane.setPreferredSize(new Dimension(200,200));

        final Color newColor = new Color(255,255,255);
        ScrollBarUI yourUI = new BasicScrollBarUI() {
            @Override
            protected JButton createIncreaseButton(int orientation) {
                JButton button = super.createIncreaseButton(orientation);
                button.setBackground(newColor);
                return button;
            }
        };
        scrollPane.getVerticalScrollBar().setUI(yourUI);
        scrollPane.getHorizontalScrollBar().setUI(yourUI);
        table.setBackground(new Color(69,53,96));
        table.setForeground(new Color(255,255,255));
        table.setFont(new Font("Ubuntu,thin", Font.PLAIN, 13));
        table.setBorder(null);
        table.setShowGrid(false);
        table.setRowHeight(30);
        JTableHeader head = table.getTableHeader();
        head.setBackground(new Color(43,28,67));
        head.setForeground(new Color(255,255,255));
        head.setPreferredSize(new Dimension(50, 50)); Border headerBorder = UIManager.getBorder("TableHeader.cellBorder");
        UIManager.getDefaults().put("TableHeader.cellBorder" , BorderFactory.createEmptyBorder(0,0,0,0));
    }

    private Component contentPane() {
        JPanel a = new JPanel();
        return  a;
    }
}