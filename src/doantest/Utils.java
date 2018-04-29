package doantest;

import java.sql.ResultSet;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class Utils {
    
    // 
    public static String doubleArraytoString(double[] array, String delimiter) {
      StringBuilder sb = new StringBuilder();
      double sum = 0;
      for (int i = 0; i < array.length; i++) {
          sum += array[i];
         if (i > 0) {
            sb.append(delimiter);
         }
         double item = array[i];
         sb.append(item);
      }
      if(sum < 1) {
          sb.append(delimiter);
          sb.append(1 - sum);
      }

      return sb.toString();
   }
    
    // 
    public static String printString(String[] array, String delimiter) {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < array.length; i++) {
         if (i > 0) {
            sb.append(delimiter);
         }
         String item = array[i];
         sb.append(item);
      }

      return sb.toString();
   }
    
    public Object readNodes(ResultSet rs, Graph graph) {
        Object nodes = null;
        
        
        return nodes;
    }
    
    public static void setNodesSizes(Graph graph, int minimumsize,int maximumsize){
		int smaller = -1;
		int greater = -1;
		for(Node n:graph.getEachNode()){
			if(n.getDegree() > greater || smaller == -1)
				greater = n.getDegree();
			if(n.getDegree() < smaller || greater == -1)
				smaller = n.getDegree();
		}
		for(Node n:graph.getEachNode()){
			double scale = (double)(n.getDegree() - smaller)/(double)(greater - smaller);
			if(null != n.getAttribute("ui.style")){
				n.setAttribute("ui.style", n.getAttribute("ui.style") + " size:"+ Math.round((scale*maximumsize)+minimumsize) +"px;");
			}
			else{
				n.addAttribute("ui.style", " size:"+ Math.round((scale*maximumsize)+minimumsize) +"px;");
			}
		}
	}
}
