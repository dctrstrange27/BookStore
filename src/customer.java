import com.mysql.cj.log.Log;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.plaf.ScrollBarUI;
import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
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
import java.sql.Statement;


public class customer extends JFrame {
    public JPanel contentPane;
    private JTextField searchfield;
    private JTextField itemNo;
    private JTextField boxBookISBN;
    private JTextField boxBookAuthor;
    private JTextField boxBookPrice;
    private JTextField boxBookTitle;
    private JTextField total;
    private JTextField payment;
    private JButton pay;
    private JTextField change;
    private JButton Remove2;
    private JButton RemoveAll;
    private JButton logout;
    private JButton home;
    private JButton customer;
    private JButton addTo;
    private Connect con;
    private JTable table;
    private JTable table2;
    private JTable jt;
    private ImageIcon b1;
    private JLabel bb1;
    private JTextField filter;
    private int totalamount;
    private int changes;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    customer con = new customer("BOOK STORE");
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

    public void loadTable2() {
        table2.setModel(con.getContent2());
    }





    public void remove()throws SQLException{
        PreparedStatement ps = con.getConnection().prepareStatement("DELETE FROM ATC WHERE item_no=?");
        ps.setInt(1,Integer.parseInt(table.getValueAt(table.getSelectedRow(),0).toString()));
        con.update(ps);
        con.query2(null);
        loadTable2();

    }

    public void select(String s) {
        con.query(s);
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

        clear(new JTextField[] {boxBookISBN, boxBookAuthor, boxBookPrice, boxBookTitle});
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


    public void enableEdit2() {

        itemNo.setText(table.getValueAt(table.getSelectedRow(),0).toString());
        boxBookISBN.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
        boxBookAuthor.setText(table.getValueAt(table.getSelectedRow(), 2).toString());
        boxBookPrice.setText(table.getValueAt(table.getSelectedRow(), 3).toString());
        boxBookTitle.setText(table.getValueAt(table.getSelectedRow(), 4).toString());

    }

    public customer(String title) {

        super(title);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setBounds(400,90,1133,875);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);

        Color textBoxColor = new Color(51,38,71);


        b1 = new ImageIcon("/home/rohan/IdeaProjects/FinalAct/img/customer.png");
        bb1 = new JLabel(b1);
        contentPane.add(bb1);
        bb1.setBounds(0,-3,1133,875);

        JLabel lblProducts = new JLabel("BOOKS");
        lblProducts.setFont(new Font("Montserrat", Font.PLAIN, 18));
        lblProducts.setBounds(37, 30, 155, 15);
        contentPane.add(lblProducts);

        JButton search = new JButton("Search");
        search.setFont(new Font("Ubuntu,thin", Font.PLAIN, 10));
        search.setBounds(970, 44, 50, 20);
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
        filter.setBounds(600, 44, 120, 20);
        filter.setBorder(null);
        filter.setForeground(Color.WHITE);
        filter.setCaretColor(Color.WHITE);
        contentPane.add(filter);


        searchfield = new JTextField();
        searchfield.setFont(new Font("Ubuntu,thin", Font.PLAIN, 10));
        searchfield.setBounds(790, 44, 120, 20);
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



        int x_axis =  200;
        int txtHeight = 18;
        int txtW = 160;
        itemNo = new JTextField();
        itemNo.setEnabled(false);
        itemNo.setBounds(x_axis, 540, txtW, txtHeight);
        itemNo.setBackground(textBoxColor);
        itemNo.setBorder(null);
        itemNo.setForeground(Color.WHITE);
        itemNo.setCaretColor(Color.WHITE);
        contentPane.add(itemNo);
        itemNo.setColumns(10);


        boxBookISBN = new JTextField();
        boxBookISBN.setEnabled(false);
        boxBookISBN.setBounds(x_axis, 560, txtW, txtHeight);
        boxBookISBN.setBackground(textBoxColor);
        boxBookISBN.setBorder(null);
        boxBookISBN.setForeground(Color.WHITE);
        boxBookISBN.setCaretColor(Color.WHITE);
        contentPane.add(boxBookISBN);
        boxBookISBN.setColumns(10);

        boxBookTitle = new JTextField();
        boxBookTitle.setEnabled(false);
        boxBookTitle.setBounds(x_axis, 580, txtW, txtHeight);
        boxBookTitle.setBackground(textBoxColor);
        boxBookTitle.setBorder(null);
        boxBookTitle.setCaretColor(Color.WHITE);
        boxBookTitle.setForeground(Color.WHITE);
        contentPane.add(boxBookTitle);
        boxBookTitle.setColumns(10);

        boxBookPrice = new JTextField();
        boxBookPrice.setEnabled(false);
        boxBookPrice.setBounds(x_axis, 600, txtW, txtHeight);
        boxBookPrice.setBackground(textBoxColor);
        boxBookPrice.setBorder(null);
        boxBookPrice.setForeground(Color.WHITE);
        boxBookPrice.setCaretColor(Color.WHITE);
        contentPane.add(boxBookPrice);
        boxBookPrice.setColumns(10);

        boxBookAuthor = new JTextField();
        boxBookAuthor.setEnabled(false);
        boxBookAuthor.setBounds(x_axis, 620, txtW, txtHeight);
        boxBookAuthor.setBackground(textBoxColor);
        boxBookAuthor.setBorder(null);
        boxBookAuthor.setForeground(Color.WHITE);
        boxBookAuthor.setCaretColor(Color.WHITE);
        contentPane.add(boxBookAuthor);
        boxBookAuthor.setColumns(10);



        Color colorButton = new Color(47,46,65);
        logout = new JButton("logout");
        logout.setBounds(50,44,40,20);
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
        home.setBounds(180,43,40,20);
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



        Remove2 = new JButton("Remove");
        Remove2.setForeground(Color.WHITE);
        Remove2.setFont(new Font("Ubuntu,thin", Font.PLAIN, 11));
        Remove2.setBackground(colorButton);
        Remove2.setBounds(470, 683, 90, 23);
        Remove2.setBorderPainted(false);
        contentPane.add(Remove2);
        Remove2.addActionListener(e->{ try{ remove(); }catch(SQLException ex){  } });


        RemoveAll = new JButton("Remove All");
        RemoveAll.setForeground(Color.WHITE);
        RemoveAll.setFont(new Font("Ubuntu,thin", Font.PLAIN, 11));
        RemoveAll.setBackground(colorButton);
        RemoveAll.setBounds(580, 683, 110, 23);
        RemoveAll.setBorderPainted(false);
        contentPane.add(RemoveAll);
        RemoveAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    PreparedStatement ps = con.getConnection().prepareStatement("truncate table ATC");
                    con.update(ps);
                    ((DefaultTableModel)table2.getModel()).setNumRows(0);
                    totalamount = 0;
                    total.setText("");
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });


        customer = new JButton("select");
        customer.setForeground(Color.WHITE);
        customer.setFont(new Font("Ubuntu,thin", Font.BOLD, 11));
        customer.setBackground(colorButton);
        customer.setBounds(170, 720, 80, 25);
        customer.setBorderPainted(false);
        customer.setFocusPainted(false);
        contentPane.add(customer);

        customer.addActionListener(e -> {
                try {
                    if (customer.getText().equals("select")) enableEdit2();
                }catch (Exception a){
                    JOptionPane.showMessageDialog(null,"Select books first");
                }
        });

        addTo = new JButton("Add to Cart");
        addTo.setForeground(Color.WHITE);
        addTo.setFont(new Font("Ubuntu,thin", Font.BOLD, 10));
        addTo.setBackground(colorButton);
        addTo.setBounds(255, 720, 110, 25);
        addTo.setBorderPainted(false);
        addTo.setFocusPainted(false);
        contentPane.add(addTo);
        addTo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    PreparedStatement ps = con.getConnection().prepareStatement("INSERT INTO ATC(item_no,ISBN,Title,Price,Author) values (?,?,?,?,?)");
                    ps.setInt(1,Integer.parseInt(itemNo.getText()));
                    ps.setFloat(2,Float.parseFloat(boxBookISBN.getText()));
                    ps.setString(3,(boxBookTitle.getText()));
                    ps.setInt(4,Integer.parseInt(boxBookPrice.getText()));
                    ps.setString(5,(boxBookAuthor.getText()));
                    con.update(ps);
                    con.query2(null);
                    loadTable2();


                }catch(Exception e){
                   e.printStackTrace();
                }
            }
        });

        JButton placeOrder = new JButton("Place order");
        placeOrder.setForeground(Color.WHITE);
        placeOrder.setFont(new Font("Ubuntu,thin", Font.PLAIN, 11));
        placeOrder.setBackground(colorButton);
        placeOrder.setBounds(710, 683, 100, 23);
        placeOrder.setBorderPainted(false);
        contentPane.add(placeOrder);


        placeOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                for (int i = 0; i < table2.getRowCount(); i++){
                    totalamount +=Integer.parseInt( table2.getValueAt(i, 3).toString() );// 3rd column . row column indexes are 0 based
                }
                System.out.println(totalamount);
                String total1 = Integer.toString(totalamount);
                total.setText(total1);
            }
        });
         total = new JTextField();
        total.setForeground(Color.WHITE);
        total.setFont(new Font("Ubuntu,thin", Font.PLAIN, 11));
        total.setBackground(colorButton);
        total.setBounds(530, 765, 100, 23);
        contentPane.add(total);

        payment = new JTextField();
        payment.setForeground(Color.WHITE);
        payment.setFont(new Font("Ubuntu,thin", Font.PLAIN, 11));
        payment.setBackground(colorButton);
        payment.setBounds(720, 765, 100, 23);
        payment.setCaretColor(Color.WHITE);
        payment.setBorder(null);
        contentPane.add(payment);

        pay = new JButton("pay");
        pay.setForeground(Color.WHITE);
        pay.setFont(new Font("Ubuntu,thin", Font.BOLD, 12));
        pay.setBackground(colorButton);
        pay.setBounds(825, 765, 60, 23);
        pay.setBorderPainted(false);
        pay.setFocusPainted(false);
        contentPane.add(pay);

        pay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
               String pay = payment.getText();
               int paym = Integer.parseInt(pay);
               try {
                   if(totalamount > paym){
                       JOptionPane.showMessageDialog(null,"payment Insufficient");
                   }
                   else {
                       changes = paym - totalamount;
                       String c = String.valueOf(changes);
                       change.setText(c);
                   }
               }catch (Exception e){
                   JOptionPane.showMessageDialog(null,"error in data types");
               }
            }
        });



        change = new JTextField();
        change.setForeground(Color.WHITE);
        change.setFont(new Font("Ubuntu,thin", Font.PLAIN, 11));
        change.setBackground(colorButton);
        change.setBounds(960, 765, 100, 23);
        change.setCaretColor(Color.WHITE);
        change.setBorder(null);
        contentPane.add(change);




        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(160, 140, 833, 250);

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


        JScrollPane scrollPane2 = new JScrollPane();
        scrollPane2.setBounds(470, 500, 550, 150);


        contentPane.add(scrollPane2);
        table2 = new JTable();
        scrollPane2.setViewportView(table2);
        table2.setBackground(new Color(69,53,96));
        table2.setForeground(new Color(255,255,255));
        table2.setFont(new Font("Ubuntu,thin", Font.PLAIN, 13));
        table2.setBorder(null);
        table2.setShowGrid(false);
        table2.setRowHeight(30);
        JTableHeader head2= table2.getTableHeader();
        head2.setBackground(new Color(43,28,67));
        head2.setForeground(new Color(255,255,255));
        head2.setPreferredSize(new Dimension(30, 30)); Border headerBorder2 = UIManager.getBorder("TableHeader.cellBorder");
        UIManager.getDefaults().put("TableHeader.cellBorder" , BorderFactory.createEmptyBorder(0,0,0,0));

        scrollPane2.getVerticalScrollBar().setBackground(new Color(43,28,67));

        scrollPane2.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Color.BLACK;
            }
        });




    }

    private Component contentPane() {
        JPanel a = new JPanel();
        return  a;
    }
}