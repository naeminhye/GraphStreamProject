package doantest;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.graphstream.ui.spriteManager.*;

public class GraphUtils {
    
    public enum TypeOfNode {
        PAPER,
        TOPIC
    }
    
    public enum TypeOfRelationship {
        RELATED_TO,
        CITES
    }
    
    /**
     * 
     * @param graph
     * @param nodeObj 
     */
    public static void showMoreNodeInfo(Graph graph, String NodeId, JSONObject graphInfo) {
        if(!graphInfo.isNull("nodes")) {
            String label = "[Node's Information]: ";
            int length = graphInfo.getJSONArray("nodes").length();
            for(int i = 0; i < length; i ++) {
                JSONObject obj = (JSONObject) graphInfo.getJSONArray("nodes").get(i);
                if(obj.get("id").toString().equals(NodeId)) {
                    Iterator<String> itr = obj.keys();

                    while(itr.hasNext()){
                        String key = itr.next();
                        String value = obj.get(key).toString();
                        label += key.toUpperCase() + ":" + value + "; ";
                    }
                }
            }
            
            if(graph.getNode(NodeId) != null) { 
//                if(graph.getNode(NodeId).getAttribute("ui.label").toString())
                    System.out.println("[LABEL]: " + graph.getNode(NodeId).getAttribute("ui.label").toString());
                graph.getNode(NodeId).addAttribute("ui.label", label);
            }
            
        }
    }
    
    /**
     * 
     * @param graph
     * @param nodeObj 
     */
    public static void showLessNodeInfo(Graph graph, String NodeId) { 
        if(graph.getNode(NodeId) != null) { 
            graph.getNode(NodeId).addAttribute("ui.label", "ID: " + NodeId);
        }
    }
    
    public static String addNodeToGraph(Graph graph, JSONObject nodeObj, String color) {
        
        String NodeId = String.valueOf(nodeObj.getInt("id"));

        String NodeLabel = nodeObj.getJSONArray("labels").getString(0);
        Random random = new Random();
        String workingDir = System.getProperty("user.dir");
        
        if(graph.getNode(NodeId) == null) {
            graph.addNode(NodeId);        
//            System.out.println("Node: " + nodeObj);

            graph.getNode(NodeId).setAttribute("xyz", random.nextInt(5), random.nextInt(5), 0);
            showLessNodeInfo(graph, NodeId);
//            graph.getNode(NodeId).addAttribute("ui.label", "ID: " + NodeId);
//            graph.getNode(NodeId).addAttribute("ui.label", "Label: " + NodeLabel + ", ID: " + NodeId);
            switch(NodeLabel) {
                case "Paper":
                    graph.getNode(NodeId).addAttribute("ui.style", " fill-mode: image-scaled; shadow-mode: none; shape: box; fill-image: url('" + workingDir + "/src/images/icon-news-32.png');");
                    break;
                case "Topic":
                    graph.getNode(NodeId).addAttribute("ui.style", "fill-color: " + color + ";");
                    break;
                default:
                    break;
            }
        }
        return NodeId;
    }
    
    public static void addEdgeToGraph(Graph graph, String SourceNodeId, String TargetNodeId, TypeOfRelationship type, String color) {
        if(graph.getEdge(SourceNodeId + TargetNodeId) == null) {
            graph.addEdge(SourceNodeId + TargetNodeId, SourceNodeId, TargetNodeId, true);
            switch(type) {
                case RELATED_TO:
                    graph.getEdge(SourceNodeId + TargetNodeId).addAttribute("ui.style", "fill-color: " + color + ";");
                    break;
                case CITES:
                    graph.getEdge(SourceNodeId + TargetNodeId).addAttribute("ui.style", "fill-color: " + color + " ;");
                    break;
                default:
                    graph.getEdge(SourceNodeId + TargetNodeId).addAttribute("ui.style", "fill-color: " + color + ";");
                    break;
            }
            
        }
    }
    
    /**
     * Kiểm tra xem trong JSONArray có chứa JSONObject không
     * @param arr
     * @param obj
     * @return 
     */
    private static boolean arrayContainsObject(JSONArray arr, JSONObject obj) {
        int length = arr.length();
        for(int i = 0; i < length; i ++) {
            if(obj.toString().equals(arr.get(i).toString())) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 
     * @param query luôn trả về Result Set có các giá trị p1, p2, t và proportion
     * @param graph
     * @param shownNodes 
     */
    private static void runQuery(String query, Graph graph, JSONObject graphInfo/*JSONArray shownNodes*/) {
        /** TODO: Kiểm tra nếu Topic có màu rồi thì lấy màu có sẳn */
        Random random;
        String colorCode;
        
        /** 
         *  Tạo một biến JSONObject 
         *  lưu lại cái node Paper, Topic
         *  và các relationship giữa các node 
         */
        JSONObject jsonObj = new JSONObject(); 
//                graphInfo = new JSONObject(jsonObj, JSONObject.getNames(jsonObj));
        if(!graphInfo.isNull("topic_colors")) {
            jsonObj.put("topic_colors", graphInfo.getJSONObject("topic_colors"));
        }
        else
        System.out.println("graphInfo: " + graphInfo);
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
                JSONObject t = new JSONObject(rs.getString("t"));
                String prop = String.valueOf(rs.getDouble("prop"));
                /** 
                 *  Tạo một JSONObject lưu các relationship loại related_to,
                 *  mỗi Obj lưu giá trị node paper, topic, tỉ lệ topic và màu của topic
                 */
                JSONObject related_to = new JSONObject()
                                            .put("paper", p1.get("id").toString())
                                            .put("topic", t.get("id").toString())
                                            .put("proportion", prop);
                JSONObject cites = new JSONObject()
                                            .put("sourcePaper", p1.get("id").toString())
                                            .put("targetPaper", p2.get("id").toString());
                        
                /** 
                 *  Kiểm tra xem trong jsonObj đã có mảng (JSONArray) "papers" hay chưa,  
                 *  nếu chưa thì tạo một mảng "papers" mới, 
                 *  nếu đã có rồi thì kiểm tra đã có p1 trong "papers" chưa,
                 *  nếu chưa có thì thêm p1 vào "papers".
                 */
                if(jsonObj.isNull("papers")) {
                    jsonObj.put("papers", new JSONArray().put(p1));
                    
                } else {
                    if(!arrayContainsObject(jsonObj.getJSONArray("papers"), p1)) {
                        jsonObj.getJSONArray("papers").put(p1);
                    }
                }
                addNodeToGraph(graph, p1, "");
                
                if(jsonObj.isNull("nodes")) {
                    jsonObj.put("nodes", new JSONArray().put(p1));
                    
                } else {
                    if(!arrayContainsObject(jsonObj.getJSONArray("nodes"), p1)) {
                        jsonObj.getJSONArray("nodes").put(p1);
                    }
                }
                if(jsonObj.isNull("nodes")) {
                    jsonObj.put("nodes", new JSONArray().put(p2));
                    
                } else {
                    if(!arrayContainsObject(jsonObj.getJSONArray("nodes"), p2)) {
                        jsonObj.getJSONArray("nodes").put(p2);
                    }
                }
                addNodeToGraph(graph, p2, "");
                addEdgeToGraph(graph, p1.get("id").toString(), p2.get("id").toString(), TypeOfRelationship.CITES, "black");
                
                if(jsonObj.isNull("topics")) {
                    jsonObj.put("topics", new JSONArray().put(t));
                    /** Tạo một cặp key-value lưu giá trị của từng node Topic ứng với từng màu */
                    jsonObj.put("topic_colors", new JSONObject().put(t.get("id").toString(), colorCode));
                    addNodeToGraph(graph, t, colorCode);
                } else {
                    if(!arrayContainsObject(jsonObj.getJSONArray("topics"), t)) {
                        jsonObj.getJSONArray("topics").put(t);
                        jsonObj.getJSONObject("topic_colors").put(t.get("id").toString(), colorCode);
                        addNodeToGraph(graph, t, colorCode);
                    }
                    else {
                        colorCode = jsonObj.getJSONObject("topic_colors").get(t.get("id").toString()).toString();
                    }
                }
//                System.out.println("Topic color: " + jsonObj.getJSONObject("topic_colors"));

                if(jsonObj.isNull("nodes")) {
                    jsonObj.put("nodes", new JSONArray().put(t));
                    
                } else {
                    if(!arrayContainsObject(jsonObj.getJSONArray("nodes"), t)) {
                        jsonObj.getJSONArray("nodes").put(t);
                    }
                }
                /**  
                 *
                 */
                if(jsonObj.isNull("related_to")) {
                    jsonObj.put("related_to", new JSONArray().put(related_to));
                } else {
                    if(!arrayContainsObject(jsonObj.getJSONArray("related_to"), related_to)) {
                        jsonObj.getJSONArray("related_to").put(related_to);
                    }
                }
                addEdgeToGraph(graph, p1.get("id").toString(), t.get("id").toString(), TypeOfRelationship.RELATED_TO, "black");
                

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
        JSONArray jArray = (JSONArray)jsonObj.getJSONArray("papers"); 
        if (jArray != null) { 
           for (int i = 0; i < jArray.length(); i++){ 
                papers.add(((JSONObject) jArray.getJSONObject(i)).get("id").toString());
           } 
        } 
        if(!papers.isEmpty()) {
            Iterator<String> iterator = papers.iterator();
            while (iterator.hasNext()) {
                ArrayList<String> proportion = new ArrayList<>();
                ArrayList<String> colors = new ArrayList<>();
                
                String paperId = iterator.next();
                for(Object rls: jsonObj.getJSONArray("related_to")) {
//                    JSONObject rls = (JSONObject) jsonObj.getJSONArray("related_to").getJSONObject(i);
                    JSONObject jsonRls = (JSONObject)rls;
                    jsonRls.put("color", jsonObj.getJSONObject("topic_colors").get(jsonRls.getString("topic")));

                    if(jsonRls.getString("paper").equals(paperId)) {
                        proportion.add(jsonRls.getString("proportion"));
                        colors.add(jsonRls.getString("color"));
                    }
                }
                System.out.println("Colors: " + colors.toString());
                /** Màu trắng là dành cho % còn lại trong biểu đồ tròn. */
                colors.add("#fff"); 

                /** Lưu danh sách thông tin các node hiển thị trên màn hình */
//                for(int j = 0; j < jsonObj.getJSONArray("nodes").length(); j ++) {
//                    shownNodes.put(jsonObj.getJSONArray("nodes").get(j));
//                }
                graphInfo = new JSONObject(jsonObj, JSONObject.getNames(jsonObj));
                /** Kiểm tra thông tin JSON đã lưu lại */
                System.out.println("[graphInfo]: " + graphInfo);

                graph.getNode(paperId).addAttribute("ui.style", "fill-color: " + GraphUtils.printString(colors.toArray(new String[colors.size()]), ",") + "; shape: pie-chart;");
                graph.getNode(paperId).addAttribute("ui.pie-values", GraphUtils.printStringForDouble(proportion.toArray(new String[proportion.size()]), ","));
//                colors.clear();
//                proportion.clear();
            }
        }
        
//        if(!jsonObj.isNull("papers"))
//        {
//            //jsonObj.getJSONArray("papers").forEach((Object p) -> 
//            for(int i = 0; i < jsonObj.getJSONArray("papers").length(); i++)
//            {
////                Object p = jsonObj.getJSONArray("papers").getJSONObject(i);
//            String paperId = ((JSONObject) jsonObj.getJSONArray("papers").getJSONObject(i)).get("id").toString();
//            if(!papers.contains(paperId)) {
//                papers.add(paperId);
//                
//                ArrayList<String> proportion = new ArrayList<>();
//                ArrayList<String> colors = new ArrayList<>();
//                                
//                for(Object rls: jsonObj.getJSONArray("related_to")) {
////                    JSONObject rls = (JSONObject) jsonObj.getJSONArray("related_to").getJSONObject(i);
//                    JSONObject jsonRls = (JSONObject)rls;
//                    jsonRls.put("color", jsonObj.getJSONObject("topic_colors").get(jsonRls.getString("topic")));
//                    
//                    if(jsonRls.getString("paper").equals(paperId)) {
//                        proportion.add(jsonRls.getString("proportion"));
//                        colors.add(jsonRls.getString("color"));
//                    }
//                }
//                /** Màu trắng là dành cho % còn lại trong biểu đồ tròn. */
//                colors.add("#fff"); 
//                
//                /** Lưu danh sách thông tin các node hiển thị trên màn hình */
//                for(int j = 0; j < jsonObj.getJSONArray("nodes").length(); j ++) {
////                    JSONObject obj = (JSONObject) jsonObj.getJSONArray("nodes").get(j);
//                    shownNodes.put(jsonObj.getJSONArray("nodes").get(j));
//                }
//                
//                /** Kiểm tra thông tin JSON đã lưu lại */
//                System.out.println("[shownNodes]: " + jsonObj);
////                System.out.println("[papers]: " + jsonObj.getJSONArray("papers"));
////                System.out.println("[nodes]: " + jsonObj.getJSONArray("nodes"));
////                System.out.println("[related_to]: " + jsonObj.getJSONArray("related_to"));
////                System.out.println("[proportion]: " + proportion);
////                System.out.println("[colors]: " + colors);
////                System.out.println("[topic_colors]: " + jsonObj.getJSONObject("topic_colors"));
//
//
//                graph.getNode(paperId).addAttribute("ui.style", "fill-color: " + GraphUtils.printString(colors.toArray(new String[colors.size()]), ",") + "; shape: pie-chart;");
//                graph.getNode(paperId).addAttribute("ui.pie-values", GraphUtils.printStringForDouble(proportion.toArray(new String[proportion.size()]), ","));
//            }
//        };
          //  );
        
    }
    
    public static boolean reset(JFrame frame, Graph graph, JSONObject graphInfo/*JSONArray shownNodes*/) {
        if(!graphInfo.isNull("nodes"))
        {
            if(graphInfo.getJSONArray("nodes").length() > 0) {
            int output = JOptionPane.showConfirmDialog(frame
                   , "Reset Graph"
                   ,"Reset Graph"
                   ,JOptionPane.YES_NO_OPTION,
                   JOptionPane.INFORMATION_MESSAGE);

                if(output == JOptionPane.YES_OPTION){
                    graph.clear();
                    graphInfo = new JSONObject();
                    //shownNodes = new JSONArray();
                    return true;
                }else if(output == JOptionPane.NO_OPTION){
                    return false;
                }
            }
        }
        return true;
    }
    
    /** 
     * Tạo mới graph với parameter "query" truyền vào
     * execute tham số query, nhận resultset rồi lưu các node, edge vào graph
     * @param startYear
     * @param endYear
     * @param topic
     * @param graph
     * @param graphInfo //shownNodes 
     */
    public static void setGraph(int startYear, int endYear, String topic, String display, Graph graph, JSONObject graphInfo, /*JSONArray shownNodes,*/ int limit) {
        String query = "MATCH (p1:Paper)-[r:RELATED_TO]->(t:Topic), (p1)-[:CITES]->(p2:Paper) WHERE p1.Year >= " + startYear + " AND p1.Year <= " + endYear + " RETURN p1, p2, t, r.Proportion AS prop LIMIT " + limit;
        runQuery(query, graph, graphInfo);
    }
    
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
    
    public static String printStringForDouble(String[] array, String delimiter) {
        StringBuilder sb = new StringBuilder();
        double sum = 0;
        for (int i = 0; i < array.length; i++) {
           if (i > 0) {
              sb.append(delimiter);
           }
           sum = sum + Double.parseDouble(array[i]);
           String item = array[i];
           sb.append(item);
        }
        if(sum < 1) {
            sb.append(delimiter);
            sb.append(1 - sum);
        }
        return sb.toString();
    }
    
    /**
     * Thêm node vào graph đã có sau khi double click một node
     * @param graph
     * @param nodeId
     * @param graphInfo//shownNodes 
     */
    public static void getMoreNodes(Graph graph, String nodeId, JSONObject graphInfo /*JSONArray shownNodes*/, int limit){
        String query = "MATCH (p1:Paper)-[r:RELATED_TO]->(t:Topic), (p1)-[:CITES]->(p2:Paper) WHERE ID(p1) = " + nodeId + " RETURN p1, p2, t, r.Proportion AS prop LIMIT " + limit;
        runQuery(query, graph, graphInfo/*shownNodes*/);
        System.out.println("After getting more nodes: " + graphInfo);

    }
    
}
