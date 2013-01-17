
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class thread {
    public static void main(String args[]){
        Thread threadA = new Thread(new Runnable(){
            public void run(){
                 while(true){String query =null;
                Connection con;
                Statement s;
                ResultSet rs;
                try {
            // TODO add your handling code here:
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            try {
                con = DriverManager.getConnection("jdbc:odbc:test","root","");
                s = con.createStatement();
                System.out.println("sourabh");
                query = "SELECT * FROM carregistration WHERE Status ="+1;
                rs = s.executeQuery(query);
                while(rs.next()){
                    double d = rs.getDouble("ReservedTime");
                   System.out.println((long)d);
                   System.out.println(getTime());
                    if(d<(double)getTime()){
                        //System.out.println("sou");
                        String car_ = rs.getString("CarNo");
                        String dest = rs.getString("DestinationOfCar");
                        if(car_!=null&&dest!=null){
                        data(car_,dest);
                        }
                    }
                   // System.out.println("sourabhm");
                }
               System.out.println("sourabhm"); 
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(thread.class.getName()).log(Level.SEVERE, null, ex);
                        }
            } catch (SQLException ex) {
                Logger.getLogger(CustomerRegistration.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CustomerRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
            }}
        }, "Thread A");
        threadA.start();
        
    }
  public static long getTime(){
        java.util.Date date = new java.util.Date();
        long time =date.getTime();
        
        return (time/1000);
    }
    public static void data(String car_,String dest){
        String query = null;
        Connection con;
        Statement s;
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(thread.class.getName()).log(Level.SEVERE, null, ex);
        }
            try {
                con = DriverManager.getConnection("jdbc:odbc:test","root","");
                s = con.createStatement();
                System.out.println("sourabh");
                query = "SELECT * FROM carregistration WHERE Status ="+1;
                query = "UPDATE carregistration SET Status ="+0+",Location = '"+dest+"',ReservedTime ="+0+",DestinationOfCar = 'Unknown' WHERE CarNo ='"+car_+"'";
                        s.executeUpdate(query);
               System.out.println("sourabhm"); 
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(thread.class.getName()).log(Level.SEVERE, null, ex);
                        }
            } catch (SQLException ex) {
                Logger.getLogger(CustomerRegistration.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
 

