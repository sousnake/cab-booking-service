

package edu.colorado.graphs;


public class Graph implements Cloneable
{
 
   private boolean[ ][ ] edges;
   private Object[ ] labels;
   
 
   public Graph(int n)
   {
      edges = new boolean[n][n];  // All values initially false
      labels = new Object[n];     // All values initially null
   }
   

   public void addEdge(int source, int target)   
   {
      edges[source][target] = true;
   }
   
      
 
   public Object clone( )
   {  // Clone a Graph object.
      Graph answer;
      
      try
      {
         answer = (Graph) super.clone( );
      }
      catch (CloneNotSupportedException e)
      {  // This would indicate an internal error in the Java runtime system
         // because super.clone always works for an Object.
         throw new InternalError(e.toString( ));
      }
      
      answer.edges = (boolean [ ][ ]) edges.clone( );
      answer.labels = (Object [ ]) labels.clone( );
      
      return answer;
   }
   


   public static void depthFirstPrint(Graph g, int start)
   {
      boolean[ ] marked = new boolean [g.size( )];
      
      depthFirstRecurse(g, start, marked);
      
   }
   
 
   public static void depthFirstRecurse(Graph g, int v, boolean[ ] marked)
   {
      int[ ] connections = g.neighbors(v);
      int i;
      int nextNeighbor;
      
      marked[v] = true;
      System.out.println(g.getLabel(v));
      
      // Traverse all the neighbors, looking for unmarked vertices:
      for (i = 0; i < connections.length; i++)
      {
         nextNeighbor = connections[i];
         if (!marked[nextNeighbor])
            depthFirstRecurse(g, nextNeighbor, marked);
      } 
   }
   
  
   public Object getLabel(int vertex)
   {
      return labels[vertex];
   }

   public boolean isEdge(int source, int target)
   {
      return edges[source][target];
   }

   public int[ ] neighbors(int vertex)
   {
      int i;
      int count;
      int[ ] answer;
      
      // First count how many edges have the vertex as their source
      count = 0;
      for (i = 0; i < labels.length; i++)
      {
         if (edges[vertex][i])
            count++;
      }
           
      // Allocate the array for the answer
      answer = new int[count];
      
      // Fill the array for the answer
      count = 0;
      for (i = 0; i < labels.length; i++)
      {
         if (edges[vertex][i])
            answer[count++] = i;
      }
      
      return answer;
   }

   public void removeEdge(int source, int target)   
   {
      edges[source][target] = false;
   }
   

   public void setLabel(int vertex, Object newLabel)
   {
      labels[vertex] = newLabel;
   }   
   
 
   public int size( )
   {
      return labels.length;
   }
        
}
           
