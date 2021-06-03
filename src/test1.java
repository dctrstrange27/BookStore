import javax.swing.*;
import java.sql.*;
public class test1{

  public static void main(String cms[]){
    String con ="jdbc:mysql://localhost:3306/FinalActivity?useSSL=false";
    String un="root";
    String pw="password";

    int arr[] = {3,43,4,3,5,45,4};

    try{
        System.out.println(arr[7]);
    }catch (Exception e){
        System.out.println("there's an error at your code");
    }
    try{
        Connection conn = DriverManager.getConnection(con,un,pw);
        Statement st = conn.createStatement();
        st.executeUpdate("Insert into Products values(5,'luckyME','Big',243,13,'noodles')");
        JOptionPane.showMessageDialog(null,"successfully established connection");

    }catch (Exception e){
        e.printStackTrace();
    }
  }
    public int method(int a, int b){
        return a+ b;
    }
}
