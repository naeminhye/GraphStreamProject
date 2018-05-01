package doantest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.json.JSONArray;
import org.json.JSONObject;
import static doantest.style.StyleImporter.getStyle;

public class Utils {
    
    public enum TypeOfNode {
        PAPER,
        TOPIC
    }
    
    public enum TypeOfRelationship {
        RELATED_TO,
        CITES
    }
    
    public static String addNodeToGraph(Graph graph, JSONObject nodeObj, String color) {
        
        String NodeId = String.valueOf(nodeObj.getInt("id"));
//        System.out.println("Node ID *: " + nodeIdP1);

        String NodeLabel = nodeObj.getJSONArray("labels").getString(0);
        Random random = new Random();
        
        if(graph.getNode(NodeId) == null) {
            graph.addNode(NodeId);
            graph.getNode(NodeId).setAttribute("xyz", random.nextInt(5), random.nextInt(5), 0);
            graph.getNode(NodeId).addAttribute("ui.label", "Label: " + NodeLabel + ", ID: " + NodeId);
            switch(NodeLabel) {
                case "Paper":
                    graph.getNode(NodeId).addAttribute("ui.style", "size: 20px, 20px; stroke-mode: plain; stroke-color: #f84f48;text-mode: hidden; shape: pie-chart;");
                    graph.getNode(NodeId).addAttribute("ui.pie-values", Utils.printString(new String[]{"0.5", "0.5"}, ","));
                    break;
                case "Topic":
                    graph.getNode(NodeId).addAttribute("ui.style", "fill-color: " + color + "; size: 20px; stroke-mode: plain; stroke-color: #8ab3cd; stroke-width: 3px; text-mode: hidden;");
                    break;
                default:
                    break;
            }
        }
        return NodeId;
    }
    
    public static void addEdgeToGraph(Graph graph, String SourceNodeId, String TargetNodeId, TypeOfRelationship type) {
        if(graph.getEdge(SourceNodeId + TargetNodeId) == null) {
            graph.addEdge(SourceNodeId + TargetNodeId, SourceNodeId, TargetNodeId);
            switch(type) {
                case RELATED_TO:
                    graph.getEdge(SourceNodeId + TargetNodeId).addAttribute("ui.style", "fill-color: blue; shape: line; arrow-size: 3px, 2px;");
                    break;
                case CITES:
                    graph.getEdge(SourceNodeId + TargetNodeId).addAttribute("ui.style", "fill-color: red;");
                    break;
                default:
                    graph.getEdge(SourceNodeId + TargetNodeId).addAttribute("ui.style", "fill-color: black;");
                    break;
            }
            
        }
    }
    
    /**
     * execute tham số query, nhận resultset rồi lưu các node, edge vào graph
     * @param query
     * @param graph
     */
    public static void setGraph(String query, Graph graph) {
        Random random;
        String colorCode;
        
        /** 
         *  Tạo một biến JSONObject 
         *  lưu lại cái node Paper, Topic
         *  và các relationship giữa các node 
         */
        JSONObject jsonObj = new JSONObject(); 
        ResultSet rs;
        PreparedStatement stmt = null;
        
        // Kết nối
        Connection con;
        try {
            con = DriverManager.getConnection(Constants.CONNECTION_URL, Constants.USERNAME, Constants.PASSWORD);
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
            
            while (rs.next()) {                
                /** In node với màu ngẫu nhiên */
                random = new Random();
                colorCode = String.format("#%06x", random.nextInt(256*256*256));
                // Import style từ file css thông qua lớp StyleImporter
                String style = getStyle("style.css");
                // Add style vào graph
                graph.addAttribute("ui.stylesheet", style);
                
                JSONObject p1 = new JSONObject(rs.getString("p1"));
                JSONObject p2 = new JSONObject(rs.getString("p2"));
                JSONObject t = new JSONObject(rs.getString("t")).put("color", colorCode);
                System.out.println("[Topic]: " + t);
                String prop = String.valueOf(rs.getDouble("prop"));
                /** 
                 *  Tạo một JSONObject lưu các relationship loại related_to,
                 *  mỗi Obj lưu giá trị node paper, topic, tỉ lệ topic và màu của topic
                 */
                JSONObject related_to = new JSONObject()
                                            .put("paper", p1.get("id").toString())
                                            .put("topic", t.get("id").toString())
                                            .put("proportion", prop)
                                            .put("color", colorCode);

                JSONObject cites = new JSONObject()
                                            .put("sourcePaper", p1.get("id").toString())
                                            .put("targetPaper", p2.get("id").toString());
                        
                /** 
                 *  Kiểm tra xem trong jsonObj đã có mảng (JSONArray) "papers" hay chưa,  
                 *  nếu chưa thì tạo một mảng "papers" mới, 
                 *  nếu đã có rồi thì kiểm tra đã có p1 trong "papers" chưa,
                 *  nếu chưa có thì thêm p1 vào "papers".
                 */
                String nodeP1Id = addNodeToGraph(graph, p1, "");
                if(jsonObj.isNull("papers")) {
                    jsonObj.put("papers", new JSONArray().put(p1));
                } else {
                    if(!jsonObj.getJSONArray("papers").toList().contains(p1)) {
                        jsonObj.getJSONArray("papers").put(p1);
                    }
                }
                
                String nodeP2Id = addNodeToGraph(graph, p2, "");
                if(jsonObj.isNull("papers")) {
                    jsonObj.put("papers", new JSONArray().put(p2));
                } else {
                    if(!jsonObj.getJSONArray("papers").toList().contains(p2)) {
                        jsonObj.getJSONArray("papers").put(p2);
                    }
                }
                
                String nodeTId = addNodeToGraph(graph, t, colorCode);
                if(jsonObj.isNull("topics")) {
                    jsonObj.put("topics", new JSONArray().put(t));
                } else {
                    if(!jsonObj.getJSONArray("topics").toList().contains(t)) {
                        jsonObj.getJSONArray("topics").put(t);
                    }
                }
                
                if(jsonObj.isNull("cites")) {
                    jsonObj.put("cites", new JSONArray().put(cites));
//                    graph.addEdge(p1.get("id").toString() + p2.get("id").toString(), p1.get("id").toString(), p2.get("id").toString());
                } else {
                    if(!jsonObj.getJSONArray("cites").toList().contains(cites)) {
                        jsonObj.getJSONArray("cites").put(cites);
//                        graph.addEdge(p1.get("id").toString() + p2.get("id").toString(), p1.get("id").toString(), p2.get("id").toString());
                    }
                }
                addEdgeToGraph(graph, p1.get("id").toString(), p2.get("id").toString(), TypeOfRelationship.CITES);

               System.out.println("jsonObj.getJSONArray(\"cites\")" + jsonObj.getJSONArray("cites"));
                /**  
                 * Tương tự đối với related_to 
                 */
                if(jsonObj.isNull("related_to")) {
                    jsonObj.put("related_to", new JSONArray().put(related_to));
//                    graph.addEdge(p1.get("id").toString() + t.get("id").toString(), p1.get("id").toString(), t.get("id").toString());
                } else {
                    if(!jsonObj.getJSONArray("related_to").toList().contains(related_to)) {
                        jsonObj.getJSONArray("related_to").put(related_to);
//                        graph.addEdge(p1.get("id").toString() + t.get("id").toString(), p1.get("id").toString(), t.get("id").toString());
                    }
                }
                addEdgeToGraph(graph, p1.get("id").toString(), t.get("id").toString(), TypeOfRelationship.RELATED_TO);

            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /** 
         *  Với mỗi node Paper trong graph,
         *  thêm mảng bao gồm các proportion vào attribute "ui.pie-values".
         */
        ArrayList<String> papers = new ArrayList<>();
        jsonObj.getJSONArray("papers").forEach(p -> {
            String paperId = ((JSONObject) p).get("id").toString();
            if(!papers.contains(paperId)) {
                papers.add(paperId);
                
                ArrayList<String> proportion = new ArrayList<>();
                ArrayList<String> colors = new ArrayList<>();

                jsonObj.getJSONArray("related_to").forEach(rls -> {
                    if(((JSONObject) rls).getString("paper").equals(paperId)) {
                        proportion.add(((JSONObject) rls).getString("proportion"));
                        colors.add(((JSONObject) rls).getString("color"));
                    }
                });
                graph.getNode(paperId).addAttribute("ui.style", "fill-color: " + Utils.printString(colors.toArray(new String[colors.size()]), ",") + "; size: 20px; stroke-mode: plain; stroke-color: #f84f48;text-mode: hidden; shape: pie-chart;");
                graph.getNode(paperId).addAttribute("ui.pie-values", Utils.printString(proportion.toArray(new String[proportion.size()]), ","));
            }
        });
    }
    
    // 
//    public static String doubleArraytoString(double[] array, String delimiter) {
//      StringBuilder sb = new StringBuilder();
//      double sum = 0;
//      for (int i = 0; i < array.length; i++) {
//          sum += array[i];
//         if (i > 0) {
//            sb.append(delimiter);
//         }
//         double item = array[i];
//         sb.append(item);
//      }
//      if(sum < 1) {
//          sb.append(delimiter);
//          sb.append(1 - sum);
//      }
//
//      return sb.toString();
//   }
    
    /**
     * 
     * @param array: mảng các giá trị kiểu String, ví dụ [a,b,c]
     * @param delimiter: kí tự phân cách các giá trị trong mảng, ví dụ ","
     * @return trả về một String, ví dụ "a,b,c"
     */
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
    
//    public Object readNodes(ResultSet rs, Graph graph) {
//        Object nodes = null;
//        
//        
//        return nodes;
//    }
    
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
