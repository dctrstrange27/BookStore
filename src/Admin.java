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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.*;



public class Admin extends JFrame {
    public JPanel contentPane;
    private JTextField searchfield;
    private JLabel lblSelectedBook;
    private JLabel lblNewBook;
    private JLabel lblISBN;
    private JLabel lblISBN2;
    private JLabel lblAuthor;
    private JLabel lblAuthor2;
    private JLabel lblPrice;
    private JLabel lblPrice_1;
    private JLabel lblTitle;
    private JLabel lblTitle2;
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
    private Connect con;
    private JTable table;
    private boolean isSelected = true;
    private ImageIcon b1;
    private JLabel bb1;
    private JLabel tableHeader;


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

    public void loadTable() { table.setModel(con.getContent()); }

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
        Save.setText("Edit Selected");
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

        b1 = new ImageIcon("/home/rohan/IdeaProjects/FinalAct/img/admin.png");
        bb1 = new JLabel(b1);
        contentPane.add(bb1);
        bb1.setBounds(0,-20,1100,795);

        JLabel lblProducts = new JLabel("BOOKS");
        lblProducts.setFont(new Font("Montserrat", Font.PLAIN, 18));
        lblProducts.setBounds(37, 30, 155, 15);
        contentPane.add(lblProducts);

        JButton search = new JButton("Search");
        search.setForeground(Color.WHITE);
        search.setFont(new Font("Gotham Pro", Font.BOLD, 10));
        search.setBackground(Color.DARK_GRAY);
        search.setBounds(760, 21, 79, 25);
        search.addActionListener(e -> {
            con.query(String.format("Select * from Books where Book_ID = %s", searchfield.getText()));
            loadTable();
        });
        contentPane.add(search);

        searchfield = new JTextField();
        searchfield.setFont(new Font("Poppins Medium", Font.PLAIN, 10));
        searchfield.setBounds(582, 22, 171, 23);
        contentPane.add(searchfield);
        searchfield.setColumns(10);
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

        lblSelectedBook = new JLabel("Selected Books");
        lblSelectedBook.setFont(new Font("Montserrat Medium", Font.BOLD, 12));
        lblSelectedBook.setBounds(213, 336, 155, 15);
        contentPane.add(lblSelectedBook);

        lblNewBook = new JLabel("Add book");
        lblNewBook.setFont(new Font("Montserrat Medium", Font.BOLD, 12));
        lblNewBook.setBounds(648, 336, 155, 15);
        contentPane.add(lblNewBook);



        lblISBN = new JLabel("ISBN");
        lblISBN.setFont(new Font("Montserrat", Font.PLAIN, 10));
        lblISBN.setBounds(87, 400, 70, 15);
        contentPane.add(lblISBN);

        lblISBN2 = new JLabel("ISBN");
        lblISBN2.setFont(new Font("Montserrat", Font.PLAIN, 10));
        lblISBN2.setBounds(514, 397, 70, 15);
        contentPane.add(lblISBN2);

        lblAuthor = new JLabel("Author");
        lblAuthor.setFont(new Font("Montserrat", Font.PLAIN, 10));
        lblAuthor.setBounds(87, 480, 70, 15);
        contentPane.add(lblAuthor);

        lblAuthor2 = new JLabel("Author");
        lblAuthor2.setFont(new Font("Montserrat", Font.PLAIN, 10));
        lblAuthor2.setBounds(514, 477, 70, 15);
        contentPane.add(lblAuthor2);

        lblPrice = new JLabel("Price");
        lblPrice.setFont(new Font("Montserrat", Font.PLAIN, 10));
        lblPrice.setBounds(87, 453, 70, 15);
        contentPane.add(lblPrice);

        lblPrice_1 = new JLabel("Price");
        lblPrice_1.setFont(new Font("Montserrat", Font.PLAIN, 10));
        lblPrice_1.setBounds(514, 450, 70, 15);
        contentPane.add(lblPrice_1);

        lblTitle = new JLabel("Title");
        lblTitle.setFont(new Font("Montserrat", Font.PLAIN, 10));
        lblTitle.setBounds(87, 428, 70, 15);
        contentPane.add(lblTitle);

        lblTitle2 = new JLabel("Title");
        lblTitle2.setFont(new Font("Montserrat", Font.PLAIN, 10));
        lblTitle2.setBounds(514, 425, 70, 15);
        contentPane.add(lblTitle2);



        boxBookISBN = new JTextField();
        boxBookISBN.setEnabled(false);
        boxBookISBN.setBounds(170, 397, 213, 19);
        contentPane.add(boxBookISBN);
        boxBookISBN.setColumns(10);

        boxBookAuthor = new JTextField();
        boxBookAuthor.setEnabled(false);
        boxBookAuthor.setBounds(170, 425, 213, 19);
        contentPane.add(boxBookAuthor);
        boxBookAuthor.setColumns(10);

        boxBookPrice = new JTextField();
        boxBookPrice.setEnabled(false);
        boxBookPrice.setBounds(170, 450, 213, 19);
        contentPane.add(boxBookPrice);
        boxBookPrice.setColumns(10);

        boxBookTitle = new JTextField();
        boxBookTitle.setEnabled(false);
        boxBookTitle.setBounds(170, 477, 213, 19);
        contentPane.add(boxBookTitle);
        boxBookTitle.setColumns(10);


        boxBookISBN2 = new JTextField();
        boxBookISBN2.setBounds(605, 395, 204, 19);
        contentPane.add(boxBookISBN2);
        boxBookISBN2.setColumns(10);

        boxBookAuthor2 = new JTextField();
        boxBookAuthor2.setBounds(606, 423, 203, 19);
        contentPane.add(boxBookAuthor2);
        boxBookAuthor2.setColumns(10);

        boxBookPrice2 = new JTextField();
        boxBookPrice2.setBounds(606, 448, 203, 19);
        contentPane.add(boxBookPrice2);
        boxBookPrice2.setColumns(10);

        boxBookTitle2 = new JTextField();
        boxBookTitle2.setBounds(605, 475, 204, 19);
        contentPane.add(boxBookTitle2);
        boxBookTitle2.setColumns(10);

        add = new JButton("Add");
        add.setForeground(Color.WHITE);
        add.setFont(new Font("Gotham Pro", Font.BOLD, 10));
        add.setBackground(Color.DARK_GRAY);
        add.setBounds(615, 506, 79, 25);
        add.addActionListener(e->{ try{ create(); }catch(Exception ex){ JOptionPane.showMessageDialog(this, "Unable To Create Product"); }});
        contentPane.add(add);

        clear = new JButton("Clear");
        clear.setForeground(Color.WHITE);
        clear.setFont(new Font("Gotham Pro", Font.BOLD, 10));
        clear.setBackground(Color.DARK_GRAY);
        clear.setBounds(716, 506, 79, 25);
        clear.addActionListener(e->{ clear(new JTextField[]{boxBookISBN2, boxBookAuthor2, boxBookPrice2, boxBookTitle2}); });
        contentPane.add(clear);

        clear2 = new JButton("Clear");
        clear2.setForeground(Color.WHITE);
        clear2.setFont(new Font("Gotham Pro", Font.BOLD, 10));
        clear2.setBackground(Color.DARK_GRAY);
        clear2.setBounds(400, 510, 79, 25);
        contentPane.add(clear2);

        clear2.addActionListener(e->{
            clear(new JTextField[]{boxBookISBN, boxBookAuthor, boxBookPrice, boxBookTitle});
        });




        Save = new JButton("Edit Selected");
        Save.addActionListener(e -> {
                setColorForSelected();
            try {
                if (Save.getText().equals("Edit Selected")) enableEdit();
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
        Save.setFont(new Font("Gotham Pro", Font.BOLD, 10));
        Save.setBackground(Color.DARK_GRAY);
        Save.setBounds(116, 508, 122, 25);
        contentPane.add(Save);


        Remove = new JButton("Remove Selected");
        Remove.setEnabled(false);
        Remove.setForeground(Color.WHITE);
        Remove.setFont(new Font("Gotham Pro", Font.BOLD, 10));
        Remove.setBackground(Color.DARK_GRAY);
        Remove.setBounds(250, 508, 133, 25);
        Remove.addActionListener(e->{ try{ remove(); }catch(SQLException ex){  } });
        contentPane.add(Remove);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(100, 120, 833, 250);

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
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumn col = table.getColumnModel().getColumn(0);
        col.setPreferredWidth(10);
    }

    private Component contentPane() {
        JPanel a = new JPanel();
        return  a;
    }
}