import java.sql.*;
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

class Vertex implements Comparable<Vertex>
{
    public String name;
    public Edge[] adjacencies = new Edge[50];
    public int edge=0;
    public double minDistance = Double.POSITIVE_INFINITY;
    public Vertex previous;
    public Vertex(String argName) { name = argName; }
    public String toString() { return name; }
    public int compareTo(Vertex other)
    {
        return Double.compare(minDistance, other.minDistance);
    }
}

class Edge
{
    public final Vertex target;
    public final double weight;
    public Edge(Vertex argTarget, double argWeight)
    { target = argTarget; weight = argWeight; }
}

public class DijkstrasAlgo
{
    int citytocompute =0;
    String query =null;
        Connection con;
        Statement s;
        ResultSet rs ;
        Double minneeded = 0d;
        String carid=null;
    public static void computePaths(Vertex source)
    {
        //System.out.println("in compute paths");
        source.minDistance = 0.;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
      	vertexQueue.add(source);
        while (!vertexQueue.isEmpty()) {
	    Vertex u = vertexQueue.poll();
            for (Edge e : u.adjacencies)
            {
               if(e!=null){
                Vertex vx = e.target;
                double weight = e.weight;
                double distanceThroughU = u.minDistance + weight;
		if (distanceThroughU < vx.minDistance) {
		    vertexQueue.remove(vx);
		    vx.minDistance = distanceThroughU ;
		    vx.previous = u;
		    vertexQueue.add(vx);
		}
            }}
        }
    }

    public static List<Vertex> getShortestPathTo(Vertex target)
    {
        List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);
        Collections.reverse(path);
        return path;
    }
    public boolean cityhascab(String city){
        try {
            // TODO add your handling code here:
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            try {
                con = DriverManager.getConnection("jdbc:odbc:test","root","");
                s = con.createStatement();
                query = "SELECT Location FROM carregistration WHERE Status ="+0;
                rs = s.executeQuery(query);
                //System.out.println("sm");
                while(rs.next()){
                    if(rs.getString("Location").equals(city)){
                        return true;
                    }
                    //System.out.println("sm");
                }
                
                
            } catch (SQLException ex) {
                Logger.getLogger(CustomerRegistration.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CustomerRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public String cab(String city){
        try {
            // TODO add your handling code here:
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            try {
                con = DriverManager.getConnection("jdbc:odbc:test","root","");
                s = con.createStatement();
                //System.out.println("sm---");
                query = "SELECT * FROM carregistration WHERE Status ="+0;
                rs = s.executeQuery(query);
                //System.out.println("sm---");
                while(rs.next()){
                    if(rs.getString("Location").equals(city)){
                       // System.out.println(rs.getString("CarNo"));
                        return rs.getString("CarNo");
                        
                    }
                }
                
                
            } catch (SQLException ex) {
                Logger.getLogger(CustomerRegistration.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CustomerRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public String main(boolean bool,String source,String dest) //bool =0 getcab and 1 means getshortest to dest
    {
        Vertex v[] = new Vertex[50];
        int citycount=0;
        //System.out.println("in main ");
        try {
                Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                try {
                    con = DriverManager.getConnection("jdbc:odbc:test","root","");
                    s = con.createStatement();
                    query = "SELECT * FROM citylocation";
                    rs = s.executeQuery(query);
                    String citys = null;
                    while(rs.next()){
                        citys =rs.getString("CityName");
                        v[citycount] = new Vertex(citys);
                        citycount++;
                    }
                    query ="SELECT * FROM cityroad";
                    rs = s.executeQuery(query);
                    while(rs.next()){
                        String city1 = rs.getString("City1");
                        String city2 = rs.getString("City2");
                        int city1id=-1;
                        int city2id =-1;
                        for(int i=0;i<citycount;i++){
                            if(v[i].name.equals(city1))
                               city1id = i;
                            if(v[i].name.equals(city2))
                                city2id =i;
                        }
                        double dist =0;
                        double x = Integer.parseInt(rs.getString("Location1X"))-Integer.parseInt(rs.getString("Location2X"));
                        x = Math.pow(x, 2);
                        double y = Integer.parseInt(rs.getString("Location1Y"))-Integer.parseInt(rs.getString("Location2Y"));
                        y = Math.pow(y,2);
                        if(city1id>-1&&city2id>-1)
                            {   v[city1id].adjacencies[v[city1id].edge]=new Edge(v[city2id],Math.sqrt(x+y));
                                v[city1id].edge++;
                                v[city2id].adjacencies[v[city2id].edge]=new Edge(v[city1id],Math.sqrt(x+y));
                                v[city2id].edge++;
                            }
                    }
                    for(int i=0;i<citycount;i++){
                        if(v[i].name.equals(source)){
                            citytocompute=i;
                            break;
                        }
                            
                    }
                    computePaths(v[citytocompute]); //compute 
                    Double min =Double.POSITIVE_INFINITY;
                    List<Vertex> pathtocab =null; 
                    if(bool ==false){
                        for (Vertex v1 : v)
                    {
                        if(v1!=null){
                        System.out.println("Distance to " + v1 + ": " + v1.minDistance);
                        List<Vertex> path = getShortestPathTo(v1);
                        System.out.println("Path: " + path);
                        if(v1.minDistance<min && cityhascab(v1.name)){
                            min = v1.minDistance;
                            pathtocab = path;
                            minneeded = v1.minDistance;
                            carid = cab(v1.name);
                            System.out.println("carbook="+carid);
                        }
                        }
                    }
                        return carid;
                    }
                    
                    else if(bool ==true){
                       for (Vertex v1 : v)
                    {   
                        if(v1!=null&&v1.name.equals(dest)){
                        //System.out.println("Distance to " + v1 + ": " + v1.minDistance);
                        List<Vertex> path = getShortestPathTo(v1);
                        //System.out.println("Path: " + path);
                        minneeded = v1.minDistance;
                        return null;
                        } 
                   }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(RegisterCar.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(RegisterCar.class.getName()).log(Level.SEVERE, null, ex);
            }
        return null;
    }
}