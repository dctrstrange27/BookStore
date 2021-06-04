

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
public class Connect {

    private Connection con;
    private ResultSet datas;
    private ResultSet datas2;

    public Connect(String url, int pr, String d, String u, String p) throws SQLException {
        con = DriverManager.getConnection(String.format("jdbc:mysql://%s:%d/%s?useSSL=false", url, pr, d), u, p);
    }

    public Connection getConnection() { return con; }

    public boolean update(PreparedStatement ps) {
        try {
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public DefaultTableModel getContent(){
        DefaultTableModel tmodel = new DefaultTableModel();
        tmodel.addColumn("Book_ID");
        tmodel.addColumn("ISBN");
        tmodel.addColumn("TITLE");
        tmodel.addColumn("PRICE");
        tmodel.addColumn("AUTHOR");
        

        try{
            while(datas.next())
                tmodel.addRow(new String [] {
                        datas.getString(1),
                        datas.getString(2),
                        datas.getString(3),
                        datas.getString(4),
                        datas.getString(5)
                });

        }catch(Exception e){ }
        return tmodel;
    }
    public boolean query(String s) {
        try {
            datas = con.createStatement().executeQuery(s==null?"Select * from Books":s);
            return true;
        } catch (SQLException e) { return false; }
    }




    public DefaultTableModel getContent2(){
        DefaultTableModel tmodel2 = new DefaultTableModel();
        tmodel2.addColumn("item_no");
        tmodel2.addColumn("ISBN");
        tmodel2.addColumn("TITLE");
        tmodel2.addColumn("PRICE");
        tmodel2.addColumn("AUTHOR");


        try{
            while(datas2.next())
                tmodel2.addRow(new String [] {
                        datas2.getString(1),
                        datas2.getString(2),
                        datas2.getString(3),
                        datas2.getString(4),
                        datas2.getString(5)
                });

        }catch(Exception e){ }
        return tmodel2;
    }



    public boolean query2(String s) {
        try {
            datas2 = con.createStatement().executeQuery(s==null?"Select * from ATC":s);
            return true;
        } catch (SQLException e) { return false; }
    }



}