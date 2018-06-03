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
import org.json.JSONArray;
import org.json.JSONObject;
import static doantest.style.StyleImporter.getStyle;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.geom.Line2D;
import java.util.Iterator;
import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.swingViewer.DefaultView;
import org.graphstream.ui.swingViewer.LayerRenderer;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerPipe;
import org.graphstream.ui.view.View;

public class GraphUtils {
    
    public enum TypeOfNode {
        PAPER,
        TOPIC,
        NONE
    }
    
    public enum TypeOfRelationship {
        RELATED_TO,
        CITES
    }
    
    public static String getNodeInfo(Graph graph, String NodeId, StorageObject graphInfo) {
        String label = "";
        if(!graphInfo.getObject().isNull("shown_nodes")) {
            label = "Node's Information:\n";
            int _length = graphInfo.getObject().getJSONArray("shown_nodes").length();
            for(int i = 0; i < _length; i ++) {
                JSONObject obj = (JSONObject) graphInfo.getObject().getJSONArray("shown_nodes").getJSONObject(i);
                if(obj.length() > 0) {
                    if(obj.get("id").toString().equals(NodeId)) {
                        Object[] keys = obj.keySet().toArray();
                        for(Object key: keys) {
                            String value = obj.get(key.toString()).toString();
                            label += key.toString().toUpperCase() + ":" + value + " \n";
                        }
                    }
                }
            }
            
//            if(graph.getNode(NodeId) != null) { 
//                System.out.println("[LABEL]: " + graph.getNode(NodeId).getAttribute("ui.label").toString());
//                graph.getNode(NodeId).addAttribute("ui.label", label);
//            }
            
        
//            if(!graphInfo.getObject().isNull("hidden_nodes")) {
//                JSONArray hidden_nodes = (JSONArray) graphInfo.getObject().getJSONArray("hidden_nodes");
//                for(Object _node: hidden_nodes) {
//                    JSONObject node = (JSONObject) _node;
//                    graph.getNode(node.get("id").toString()).removeAttribute("ui.hide");
//                    graph.getNode(node.get("id").toString()).getEnteringEdgeSet().forEach(edge -> {
//                        edge.removeAttribute("ui.hide");
//                    });
//                    graph.getNode(node.get("id").toString()).getLeavingEdgeSet().forEach(edge -> {
//                        edge.removeAttribute("ui.hide");
//                    });
//                }
//            }
        }
        return label;
    }
    
    /**
     * 
     * @param graph
     * @param nodeObj 
     */
    public static void showMoreNodeInfo(Graph graph, String NodeId, StorageObject graphInfo, View view) {
        if(graph.getNode(NodeId) != null) { 
            graph.getNode(NodeId).addAttribute("ui.label", "Node ID: " + NodeId);
            graph.getNode(NodeId).setAttribute("ui.style", "text-mode: normal;");
        }
        if(!graphInfo.getObject().isNull("shown_nodes")) {
            String label = "Node's Information:\n";
            int _length = graphInfo.getObject().getJSONArray("shown_nodes").length();
            for(int i = 0; i < _length; i ++) {
                JSONObject obj = (JSONObject) graphInfo.getObject().getJSONArray("shown_nodes").getJSONObject(i);
                if(obj.length() > 0) {
                    if(obj.get("id").toString().equals(NodeId)) {
                        Object[] keys = obj.keySet().toArray();
                        for(Object key: keys) {
                            String value = obj.get(key.toString()).toString();
                            label += key.toString().toUpperCase() + ":" + value + " \n";
                        }
                        
                        DefaultView _view = (DefaultView) view;
                        drawTextOnView(_view, label, 20f, 20f);
                        return;
                    }
                }
            }
        }
        
        if(!graphInfo.getObject().isNull("hidden_nodes")) {
            String label = "Node's Information:\n";
            int _length = graphInfo.getObject().getJSONArray("hidden_nodes").length();
            for(int i = 0; i < _length; i ++) {
                JSONObject obj = (JSONObject) graphInfo.getObject().getJSONArray("hidden_nodes").getJSONObject(i);
                if(obj.length() > 0) {
                    if(obj.get("id").toString().equals(NodeId)) {
                        Object[] keys = obj.keySet().toArray();
                        for(Object key: keys) {
                            String value = obj.get(key.toString()).toString();
                            label += key.toString().toUpperCase() + ":" + value + " \n";
                        }
                        
                        DefaultView _view = (DefaultView) view;
                        drawTextOnView(_view, label, 20f, 20f);
                        return;
                    }
                }
            }
        }
    }
    
    private static void drawTextOnView(DefaultView view, String text, float x, float y) {
        
            view.setForeLayoutRenderer(new LayerRenderer() {
                @Override
                public void render(Graphics2D graphics2D, GraphicGraph graphicGraph, double v, int i, int i1, double v1, double v2, double v3, double v4) {
                    float heightOfText = graphics2D.getFontMetrics().getHeight() * 1.5f;
                    float newY = y;
                    Font myFont = new Font ("Arial", Font.CENTER_BASELINE, 14);
                    graphics2D.setColor(Color.BLACK);
                    graphics2D.setFont(myFont);
                    for (String line : text.split("\n")) {
                    graphics2D.setFont(myFont);
                        graphics2D.drawString(line, x, newY);
                        newY = newY + heightOfText;
                    }
                }
            });
    }
    
    /**
     * 
     * @param graph
     * @param nodeObj 
     */
    public static void showLessNodeInfo(Graph graph, String NodeId, StorageObject graphInfo) { 
        if(graph.getNode(NodeId) != null) { 
//            graph.getNode(NodeId).addAttribute("ui.label", "Node ID: " + NodeId);
            graph.getNode(NodeId).setAttribute("ui.style", "text-mode: hidden;");
        }
        
//        if(!graphInfo.getObject().isNull("hidden_nodes")) {
//            JSONArray hidden_nodes = (JSONArray) graphInfo.getObject().getJSONArray("hidden_nodes");
//                for(Object _node: hidden_nodes) {
//                    JSONObject node = (JSONObject) _node;
//                    graph.getNode(node.get("id").toString()).addAttribute("ui.hide");
//                    graph.getNode(node.get("id").toString()).getEnteringEdgeSet().forEach(edge -> {
//                        edge.addAttribute("ui.hide");
//                    });
//                    graph.getNode(node.get("id").toString()).getLeavingEdgeSet().forEach(edge -> {
//                        edge.addAttribute("ui.hide");
//                    });
//                }
//        }
    }
    
    public static void addNodeToGraph(Graph graph, JSONObject nodeObj, String color, boolean isHidden) {
        
        String NodeId = nodeObj.get("id").toString();

        String NodeLabel = nodeObj.getJSONArray("labels").getString(0);
        Random random = new Random();
//        String workingDir = System.getProperty("user.dir");
        
        /** Nếu node chưa có trong graph --> tạo node mới */
        if(graph.getNode(NodeId) == null) {
            graph.addNode(NodeId);        

            graph.getNode(NodeId).addAttribute("ui.label", "Node ID: " + NodeId);
            graph.getNode(NodeId).setAttribute("xyz", random.nextInt(5), random.nextInt(5), 0);
            switch(NodeLabel) {
                case "Paper":
                    graph.getNode(NodeId).addAttribute("ui.style", "shape: circle; fill-color: #cccccc; text-mode: hidden;");
                    break;
                case "Topic":
                    graph.getNode(NodeId).addAttribute("ui.style", "fill-color: " + color + "; shape: box; text-mode: hidden;");
                    break;
                default:
                    break;
            }
            if(isHidden) {
//                graph.getNode(NodeId).setAttribute("layout.frozen");
                graph.getNode(NodeId).addAttribute("ui.hide");
            }
        }
        /** Nếu node đã có trong graph */
        else {
            if(!graph.getNode(NodeId).hasAttribute("ui.label")) {
                graph.getNode(NodeId).addAttribute("ui.label", "Node ID: " + NodeId);
            }
            switch(NodeLabel) {
                case "Paper":
                    graph.getNode(NodeId).setAttribute("ui.style", "shape: circle; fill-color: #cccccc; text-mode: hidden;");
                    break;
                case "Topic":
                    graph.getNode(NodeId).setAttribute("ui.style", "fill-color: " + color + "; shape: box; text-mode: hidden;");
                    break;
                default:
                    break;
            }
        }
    }
    
    public static void addEdgeToGraph(Graph graph, String SourceNodeId, String TargetNodeId, TypeOfRelationship type, String color, String sprite, boolean isHidden) {
        String edgeName = SourceNodeId + "_" + TargetNodeId;
        if(graph.getEdge(edgeName) == null) {
            graph.addEdge(edgeName, SourceNodeId, TargetNodeId, true);
            switch(type) {
                case RELATED_TO:
                    graph.getEdge(edgeName).setAttribute("ui.style", "fill-color: " + color + ";");
                    if(!sprite.equals("")) {
                        graph.getEdge(edgeName).setAttribute("ui.label", "Proportion: " + String.format("%.2f", (Float.parseFloat(sprite) * 100)) + "%");
                    }
                    break;
                case CITES:
                    graph.getEdge(edgeName).setAttribute("ui.style", "fill-color: " + color + " ;");
                    break;
                default:
                    graph.getEdge(edgeName).setAttribute("ui.style", "fill-color: " + color + ";");
                    break;
            }
            if(isHidden) {
                graph.getEdge(edgeName).setAttribute("ui.hide");
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
    private static void runGraphQuery(String query, Graph graph, StorageObject graphInfo) {
        /** TODO: Kiểm tra nếu Topic có màu rồi thì lấy màu có sẳn */
        Random random;
        String colorCode;
        
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
                if(graph.getNodeCount() == 0) {
                    // Import style từ file css thông qua lớp StyleImporter
                    String style = getStyle("style.css");
                    // Add style vào graph
                    graph.addAttribute("ui.stylesheet", style);
                }
                
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
                
                graphInfo.putObjectToArray("papers_having_topics", p1, false);
                graphInfo.putObjectToArray("papers", p1, false);
                
                if(graphInfo.hasPutObjectToArray("hidden_nodes", p1)) {
                    makeNodeVisible(graph, p1, graphInfo);
                }
                graphInfo.putObjectToArray("shown_nodes", p1, false);
                addNodeToGraph(graph, p1, "", false);

                if(!graphInfo.hasPutObjectToArray("shown_nodes", p2)) {
                    graphInfo.putObjectToArray("hidden_nodes", p2, false);
                }
                graphInfo.putObjectToArray("papers", p2, false);
                addNodeToGraph(graph, p2, "", true);
                graphInfo.putObjectToArray("cites", cites, false);
                addEdgeToGraph(graph, p1.get("id").toString(), p2.get("id").toString(), TypeOfRelationship.CITES, "black", "", true);
                graphInfo.putObjectToArray("cites", cites, false);
                
                if(graphInfo.hasPutObjectToArray("topics", t)) {
                    colorCode = graphInfo.getObject().getJSONObject("topic_colors").get(t.get("id").toString()).toString();
                }
                else {
                    colorCode = String.format("#%06x", random.nextInt(256*256*256));
                }
                graphInfo.putObjectToArray("topics", t, false);
                addNodeToGraph(graph, t, colorCode, false);
                graphInfo.putKeyValueToObject("topic_colors", t.get("id").toString(), colorCode, false);
                graphInfo.putObjectToArray("shown_nodes", t, false);
                
                related_to.put("color", colorCode);
                graphInfo.putObjectToArray("related_to", related_to, false);
                addEdgeToGraph(graph, p1.get("id").toString(), t.get("id").toString(), TypeOfRelationship.RELATED_TO, "black", prop, false);
                
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("graphInfo: " + graphInfo.getObject());
    }
    
    /** 
     * 
     * @param query
     * @param graph
     * @param graphInfo 
     */
    private static void runTimelineQuery(String query, Graph graph, StorageObject graphInfo) {
        Random random;
        String colorCode;
           
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
                // Import style từ file css thông qua lớp StyleImporter
                String style = getStyle("style.css");
                // Add style vào graph
                graph.addAttribute("ui.stylesheet", style);
                
                JSONObject p = new JSONObject(rs.getString("p"));  
                JSONObject t = new JSONObject(rs.getString("t"));
                String prop = String.valueOf(rs.getDouble("prop"));
                JSONObject p1 = new JSONObject(rs.getString("p1"));
                String year = String.valueOf(rs.getInt("year"));
                JSONObject related_to = new JSONObject()
                                            .put("paper", p.get("id").toString())
                                            .put("topic", t.get("id").toString())
                                            .put("proportion", prop);
                JSONObject cites = new JSONObject()
                                            .put("sourcePaper", p.get("id").toString())
                                            .put("targetPaper", p1.get("id").toString());
                
                graphInfo.putObjectToArray("papers_having_topics", p, false);
                if(graphInfo.hasPutObjectToArray("hidden_nodes", p)) {
                    makeNodeVisible(graph, p, graphInfo);
                }
                graphInfo.putObjectToArray("shown_nodes", p, false);
                graphInfo.putObjectToArray("papers", p, false);
                addNodeToGraph(graph, p, "", false);
                if(p1.get("Year").toString().equals(year)) {
                    graphInfo.putObjectToArray("shown_nodes", p1, false);
                    graphInfo.putObjectToArray("papers", p1, false);
                    addNodeToGraph(graph, p1, "", false);
                    graphInfo.putObjectToArray("cites", cites, false);
                    addEdgeToGraph(graph, p.get("id").toString(), p1.get("id").toString(), TypeOfRelationship.CITES, "black", "", false);
                
                }
                else {
                    if(!graphInfo.hasPutObjectToArray("shown_nodes", p1)) {
                        graphInfo.putObjectToArray("hidden_nodes", p1, false);
                        graphInfo.putObjectToArray("papers", p1, false);
                        addNodeToGraph(graph, p1, "", true);
                        graphInfo.putObjectToArray("cites", cites, false);
                        addEdgeToGraph(graph, p.get("id").toString(), p1.get("id").toString(), TypeOfRelationship.CITES, "black", "", true);

                    }
                    
                }
                
                graphInfo.putKeyValueToArray("years", year, p.get("id").toString(), false);
                
                if(graphInfo.hasPutObjectToArray("topics", t)) {
                    colorCode = graphInfo.getObject().getJSONObject("topic_colors").get(t.get("id").toString()).toString();
                }
                else {
                    colorCode = String.format("#%06x", random.nextInt(256*256*256));
                }
                
                graphInfo.putObjectToArray("topics", t, false);
                addNodeToGraph(graph, t, colorCode, true);
                graphInfo.putKeyValueToObject("topic_colors", t.get("id").toString(), colorCode, false);

                if(!graphInfo.hasPutObjectToArray("shown_nodes", t)) {
                    graphInfo.putObjectToArray("hidden_nodes", t, false);
                }
                
                related_to.put("color", colorCode);
                graphInfo.putObjectToArray("related_to", related_to, false);
                addEdgeToGraph(graph, p.get("id").toString(), t.get("id").toString(), TypeOfRelationship.RELATED_TO, "black", prop, true);
                
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("graphInfo: " + graphInfo.getObject());
    }
    
    /** 
     * 
     * @param query
     * @param graph
     * @param graphInfo 
     */
    private static void runPaperFlowQuery(String query, Graph graph, StorageObject graphInfo) {
//        int colCount= rs.getMetaData().getColumnCount();
        Random random;  
        String colorCode;
           
        ResultSet rs;
        PreparedStatement stmt = null;
        // Kết nối
        Connection con;
        try {
            con = DriverManager.getConnection(Constants.CONNECTION_URL, Constants.USERNAME, Constants.PASSWORD);
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
            int colCount = rs.getMetaData().getColumnCount();
            System.out.println("colCount: " + colCount);
            while (rs.next()) {   
                JSONObject t = new JSONObject(rs.getString("t"));
                graphInfo.putObjectToArray("shown_nodes", t, false);
                graphInfo.putObjectToArray("topics", t, false);
//                addNodeToGraph(graph, t, "purple", true);
                for(int i = 0; i < colCount / 5; i++) {
                    JSONObject p = new JSONObject(rs.getString("p" + i));
                    graphInfo.putObjectToArray("shown_nodes", p, false);
                    graphInfo.putObjectToArray("papers", p, false);
                    addNodeToGraph(graph, p, "", false);
//                    addEdgeToGraph(graph, p.get("id").toString(), t.get("id").toString(), TypeOfRelationship.RELATED_TO, "black", "", true);
                
                    if(i != colCount / 5 - 1) {
                        String temp = "p" + (i + 1);
                        JSONObject pNext = new JSONObject(rs.getString(temp));
                        graphInfo.putObjectToArray("shown_nodes", pNext, false);
                    graphInfo.putObjectToArray("papers", pNext, false);
                        addNodeToGraph(graph, pNext, "", false);
                        JSONObject cites = new JSONObject()
                                                .put("sourcePaper", p.get("id").toString())
                                                .put("targetPaper", pNext.get("id").toString());
                        graphInfo.putObjectToArray("cites", cites, false);
                        addEdgeToGraph(graph, p.get("id").toString(), pNext.get("id").toString(), TypeOfRelationship.CITES, "black", "", false);
//                        addEdgeToGraph(graph, pNext.get("id").toString(), t.get("id").toString(), TypeOfRelationship.RELATED_TO, "black", "", true);

                    }
                }           
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("graphInfo: " + graphInfo.getObject());
    }
    
    public static boolean reset(JFrame frame, Graph graph, StorageObject graphInfo) {
        if(!graphInfo.getObject().isNull("shown_nodes"))
        {
            if(graphInfo.getObject().getJSONArray("shown_nodes").length() > 0) {
                int output = JOptionPane.showConfirmDialog(frame
                   , "Reset Graph"
                   ,"Reset Graph"
                   ,JOptionPane.YES_NO_OPTION,
                   JOptionPane.INFORMATION_MESSAGE);

                if(output == JOptionPane.YES_OPTION){
                    graph.clear();
                    graphInfo.reset();
                    return true;
                }
                else if(output == JOptionPane.NO_OPTION){
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
     * @param limit 
     */
    public static void setGraph(int startYear, int endYear, String topic, Graph graph, StorageObject graphInfo, int limit) {
        String query;
        if(topic.equals("All")) {
//            query = "MATCH (p1:Paper)-[r:RELATED_TO]->(t:Topic), (p1)-[:CITES]->(p2:Paper) WHERE p1.Year >= " + startYear + " AND p1.Year <= " + endYear + " RETURN p1, p2, t, r.Proportion AS prop LIMIT " + limit;
            query = "MATCH (p1:Paper)-[r:RELATED_TO]->(t:Topic), (p1)-[:CITES]->(p2:Paper) WHERE p1.Year = " + startYear + " RETURN p1, p2, t, r.Proportion AS prop LIMIT " + limit;
            for(int year = startYear + 1; year <= endYear; year ++) {
                query += " UNION MATCH (p1:Paper)-[r:RELATED_TO]->(t:Topic), (p1)-[:CITES]->(p2:Paper) WHERE p1.Year = " + year + " RETURN p1, p2, t, r.Proportion AS prop LIMIT " + limit;
            }
        }
        else {
            query = "MATCH (p1:Paper)-[r:RELATED_TO]->(t:Topic), (p1)-[:CITES]->(p2:Paper) WHERE p1.Year = " + startYear + " AND t.TopicId = " + topic + " RETURN p1, p2, t, r.Proportion AS prop LIMIT " + limit;
            for(int year = startYear + 1; year <= endYear; year ++) {
                query += " UNION MATCH (p1:Paper)-[r:RELATED_TO]->(t:Topic), (p1)-[:CITES]->(p2:Paper) WHERE p1.Year = " + year + " AND t.TopicId = " + topic + " RETURN p1, p2, t, r.Proportion AS prop LIMIT " + limit;
            }
        }
        runGraphQuery(query, graph, graphInfo);
        System.out.println("[QUERY]: " + query);
    }
    
    public static void setTimeline(int startYear, int endYear, String topic, Graph graph, StorageObject graphInfo, int limit) {
        String query;
        if(topic == "All") {
            query = "MATCH (p: Paper)-[r:RELATED_TO]->(t: Topic), (p)-[:CITES]->(p1:Paper) WHERE p.Year = " + startYear + " RETURN t, p, p1, p.Year AS year, r.Proportion AS prop ORDER BY p LIMIT " + limit;
            for(int year = startYear + 1; year <= endYear; year ++) {
                query += " UNION MATCH (p: Paper)-[r:RELATED_TO]->(t: Topic), (p)-[:CITES]->(p1:Paper) WHERE p.Year = " + year + " RETURN t, p, p1, p.Year AS year, r.Proportion AS prop ORDER BY p LIMIT " + limit;
            }
        }
        else {
            query = "MATCH (p: Paper)-[:RELATED_TO]->(t: Topic), (p)-[:CITES]->(p1: Paper) WHERE p.Year = " + startYear + " AND t.TopicId = " + topic + " RETURN t, p, p.Year AS year, p1 ORDER BY p LIMIT " + limit;
            for(int year = startYear + 1; year <= endYear; year ++) {
                query += " UNION MATCH (p: Paper)-[:RELATED_TO]->(t: Topic), (p)-[:CITES]->(p1: Paper) WHERE p.Year = " + year + " AND t.TopicId = " + topic + " RETURN t, p, p.Year AS year, p1 ORDER BY p LIMIT " + limit;
            }
        }
        runTimelineQuery(query, graph, graphInfo);
        System.out.println("[QUERY]: " + query);
    }
    
    public static void setPaperFlow(String paperId, String topicId, int count, int limit, Graph graph, StorageObject graphInfo) {
        String query = "MATCH (p0:Paper)-[:RELATED_TO]->(t:Topic), ";
        for(int i = 1; i < count; i++) {
                query += "(p" + i + ":Paper)-[:RELATED_TO]->(t), (p" + (i - 1) + ")-[:CITES]->(p" + i + ") ";
                if(i != (count - 1)){
                        query += ", ";
                }
        }
        query += "WHERE ID(p0) = " + paperId + " AND ID(t) = " + topicId + " RETURN ";
        for(int j = 0; j < count; j++) {
                query += "p" + j + " ";
                if(j != (count - 1)){
                        query += ", ";
                }
        }
        query += ", t LIMIT " + limit;
        runPaperFlowQuery(query, graph, graphInfo);

        System.out.println("[QUERY]: " + query);
    }
    
    public static void showGraphOnPanel(Graph graph, StorageObject graphInfo, JPanel panel, InfiniteProgressPanel glassPane) {
       
        if(!graphInfo.getObject().isNull("papers_having_topics")) {

            /** 
             *  Với mỗi node Paper trong graph,
             *  thêm mảng bao gồm các proportion vào attribute "ui.pie-values".
             */
            for(Object _paper: graphInfo.getObject().getJSONArray("papers_having_topics")) {
                JSONObject paper = (JSONObject) _paper;
                ArrayList<String> proportion = new ArrayList<>();
                ArrayList<String> colors = new ArrayList<>();
                String paperId = paper.get("id").toString();
                for(Object _rls: graphInfo.getObject().getJSONArray("related_to")) {
                    JSONObject rls = (JSONObject) _rls;
                    rls.put("color", graphInfo.getObject().getJSONObject("topic_colors").get(rls.getString("topic")));

                    if(rls.getString("paper").equals(paperId)) {
                        proportion.add(rls.getString("proportion"));
                        colors.add(rls.getString("color"));
                    }
                }

                /** Màu trắng là dành cho % còn lại trong biểu đồ tròn. */
                colors.add("#cccccc"); 

                graph.getNode(paperId).addAttribute("ui.style", "fill-color: " + GraphUtils.printString(colors.toArray(new String[colors.size()]), ",") + "; shape: pie-chart;");
                graph.getNode(paperId).addAttribute("ui.pie-values", GraphUtils.printStringForDouble(proportion.toArray(new String[proportion.size()]), ","));
                    
            }
            
            if(!graphInfo.getObject().isNull("topics")) {
                for(Object _topic: graphInfo.getObject().getJSONArray("topics")) {
                    JSONObject topic = (JSONObject) _topic;
                    String color = graphInfo.getObject().getJSONObject("topic_colors").get(topic.get("id").toString()).toString();
                    addNodeToGraph(graph, topic, color, false);
                }
            }
        }
        
        /** Tạo View Panel để chứa Graph    */
        Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        viewer.enableAutoLayout(); // cho graph chuyển động       
        ViewPanel viewPanel = viewer.addDefaultView(false);    
//        ((DefaultView) viewPanel).getCamera().setViewCenter(0, 0, 0);
//        ((DefaultView) viewPanel).setForeLayoutRenderer(new LayerRenderer() {
//            @Override
//            public void render(Graphics2D graphics2D, GraphicGraph graphicGraph, double v, int i, int i1, double v1, double v2, double v3, double v4) {
//                
//                graphics2D.setColor(Color.BLACK);
//                graphics2D.drawString("Hello", 50, 50);
//            }
//        });
        
        panel.removeAll();
        panel.setLayout(new GridLayout());
        //Panel chứa graph
        panel.add(viewPanel);
        panel.revalidate();
        
        // Xử lí sự kiện về Mouse của View Panel
        ViewerPipe fromViewer = viewer.newViewerPipe();
        fromViewer.addSink(graph);
        fromViewer.addViewerListener(new MouseHandler(graph, viewPanel, fromViewer, graphInfo, panel, glassPane, "Graph"));
    }
    
    public static void showTimeLineOnPanel(Graph graph, StorageObject graphInfo, JPanel panel, InfiniteProgressPanel glassPane) {
        
        if(!graphInfo.getObject().isNull("years")) {  
            Random random;
            String colorCode;          
            Object[] yearKeys = invertUsingFor(graphInfo.getObject().getJSONObject("years").keySet().toArray());
            int index = 0;
            int numOfYears = graphInfo.getObject().getJSONObject("years").length();
            float width = panel.getWidth() / numOfYears;
            for(Object yearKey: yearKeys) {
                String year = yearKey.toString();
                /** In node với màu ngẫu nhiên */
                random = new Random();
                colorCode = String.format("#%06x", random.nextInt(256*256*256));
                int _length = graphInfo.getObject().getJSONObject("years").getJSONArray(year).length();
                for(int i = _length - 1; i >= 0; i--) {
//                    System.out.println("panel.getHeight(): " + panel.getHeight());
                    String nodeId = (String) graphInfo.getObject().getJSONObject("years").getJSONArray(year).getString(i);
                    float x = (float)Math.random() * ((width * (index + 1)) - (width * index)) + width * index;
                    float y = (float)Math.random() * (panel.getHeight() - 0.0f) + 0.0f;
                    graph.getNode(nodeId).setAttribute("xyz", x, y, 0);
                    graph.getNode(nodeId).addAttribute("layout.frozen");
//                    graph.getNode(nodeId).setAttribute("ui.style", "stroke-color: " + colorCode + "; stroke-width: 2");
                }
                index ++;
            }
        }
        if(!graphInfo.getObject().isNull("papers_having_topics")) {

            /** 
             *  Với mỗi node Paper trong graph,
             *  thêm mảng bao gồm các proportion vào attribute "ui.pie-values".
             */
            for(Object _paper: graphInfo.getObject().getJSONArray("papers_having_topics")) {
                JSONObject paper = (JSONObject) _paper;
                ArrayList<String> proportion = new ArrayList<>();
                ArrayList<String> colors = new ArrayList<>();
                String paperId = paper.get("id").toString();
                for(Object _rls: graphInfo.getObject().getJSONArray("related_to")) {
                    JSONObject rls = (JSONObject) _rls;
                    rls.put("color", graphInfo.getObject().getJSONObject("topic_colors").get(rls.getString("topic")));

                    if(rls.getString("paper").equals(paperId)) {
                        proportion.add(rls.getString("proportion"));
                        colors.add(rls.getString("color"));
                    }
                }

                /** Màu trắng là dành cho % còn lại trong biểu đồ tròn. */
                colors.add("#cccccc"); 

                graph.getNode(paperId).setAttribute("ui.style", "fill-color: " + GraphUtils.printString(colors.toArray(new String[colors.size()]), ",") + "; shape: pie-chart;");
                graph.getNode(paperId).setAttribute("ui.pie-values", GraphUtils.printStringForDouble(proportion.toArray(new String[proportion.size()]), ","));
                    
            }
        }
        
        if(!graphInfo.getObject().isNull("hidden_nodes")) {  
//            Random random;
            for(Object _node: graphInfo.getObject().getJSONArray("hidden_nodes")) {
                JSONObject node = (JSONObject) _node;
                setPositionToNode(graph.getNode(node.get("id").toString()));
//                float x = (float)Math.random() * (panel.getWidth() - 0.0f) + 0.0f;
//                float y = (float)Math.random() * (panel.getHeight() - 0.0f) + 0.0f;
//                graph.getNode(node.get("id").toString()).setAttribute("xyz", x, y, 0);
//                graph.getNode(node.get("id").toString()).addAttribute("layout.frozen");
            }
        }
            
        if(!graphInfo.getObject().isNull("topics")) {
            for(Object _topic: graphInfo.getObject().getJSONArray("topics")) {
                JSONObject topic = (JSONObject) _topic;
                String color = graphInfo.getObject().getJSONObject("topic_colors").get(topic.get("id").toString()).toString();
                addNodeToGraph(graph, topic, color, false);
            }
        }
        
        /** Tạo View Panel để chứa Graph */
        Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
//        viewer.enableAutoLayout(); // cho graph chuyển động       
//        viewer.disableAutoLayout();
        
        DefaultView view = (DefaultView) viewer.addDefaultView(false);
        view.resizeFrame(panel.getWidth(), panel.getHeight());
        
        columnBackgroundRender(graph, graphInfo, view);
        
        panel.removeAll();
        panel.setLayout(new GridLayout());
        /** Panel chứa graph */
        panel.add(view);
        panel.revalidate();
        
        /** Xử lí sự kiện về Mouse của View Panel */
        ViewerPipe fromViewer = viewer.newViewerPipe();
        fromViewer.addSink(graph);
        fromViewer.addViewerListener(new MouseHandler(graph, view, fromViewer, graphInfo, panel, glassPane, "Timeline"));

    }
    
    public static void showPaperFlowOnPanel(Graph graph, StorageObject graphInfo, JPanel panel, InfiniteProgressPanel glassPane) {
        /** Tạo View Panel để chứa Graph    */
        Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        viewer.enableAutoLayout(); // cho graph chuyển động       
        ViewPanel viewPanel = viewer.addDefaultView(false);    
        panel.removeAll();
        panel.setLayout(new GridLayout());
        //Panel chứa graph
        panel.add(viewPanel);
        panel.revalidate();
        
        // Xử lí sự kiện về Mouse của View Panel
        ViewerPipe fromViewer = viewer.newViewerPipe();
        fromViewer.addSink(graph);
        fromViewer.addViewerListener(new MouseHandler(graph, viewPanel, fromViewer, graphInfo, panel, glassPane, "Flow"));
    }
    
    public static void columnBackgroundRender(Graph graph, StorageObject graphInfo, DefaultView view) {
        
        view.setBackLayerRenderer(new LayerRenderer() {
            @Override
            public void render(Graphics2D graphics2D, GraphicGraph graphicGraph, double v, int i, int i1, double v1, double v2, double v3, double v4) {
                graphics2D.setColor(Color.BLACK);
                if(!graphInfo.getObject().isNull("years")) {
                    
//                    System.out.println("view.getHeight(): " + view.getHeight());
                    Object[] years = invertUsingFor(graphInfo.getObject().getJSONObject("years").keySet().toArray());
                    int numOfYears = graphInfo.getObject().getJSONObject("years").length();
                    float width_of_column = view.getWidth() / numOfYears;
                    int index = 0;
                    for(Object _year : years) {
                        String year = _year.toString();
                        float startX = width_of_column * (index + 1);
                        float startY = 0;
                        float endX = startX;
                        float endY = view.getHeight();
                        graphics2D.drawString(year, (width_of_column * index) + (width_of_column / 2), 30);
                        graphics2D.draw(new Line2D.Double(startX, startY, endX, endY));

                        index++;
                    }
                }
            }
        });
    }
    
    /**
     * Đảo mảng
     * @param array
     * @return 
     */
    private static Object[] invertUsingFor(Object[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            Object temp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = temp;
        }
        return array;
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
            sb.append(Math.abs(1 - sum));
        }
        else {
            // ERROR
        }
        return sb.toString();
    }
    
    /**
     * Thêm node vào graph đã có sau khi double click một node
     * @param graph
     * @param nodeId
     * @param graphInfo
     * @param limit 
     */
    public static void getMoreNodes(Graph graph, String nodeId, StorageObject graphInfo, int limit){  
        boolean flag = false;
        if(!graphInfo.getObject().isNull("topics")) {
            for(Object topic: graphInfo.getObject().getJSONArray("topics")) {
                if(((JSONObject) topic).get("id").toString().equals(nodeId)) {
                    flag = true;
                    String _query = "MATCH (p1:Paper)-[r:RELATED_TO]->(t:Topic), (p1)-[:CITES]->(p2:Paper) WHERE ID(t) = " + nodeId + " RETURN p1, p2, t, r.Proportion AS prop LIMIT " + limit;
                    runGraphQuery(_query, graph, graphInfo);
                    System.out.println("[QUERY]: " + _query);
                }
            }
        }
        if(!flag) {
            if(!graphInfo.getObject().isNull("hidden_nodes")) {
                /** Hiện những node ẩn có liên quan */
                JSONArray temp = graphInfo.getObject().getJSONArray("hidden_nodes");
                for(int i = 0; i < temp.length(); i ++) {
                    Node thisNode = graph.getNode(((JSONObject) temp.getJSONObject(i)).get("id").toString());
                    if(graph.getEdge(nodeId + "_" + thisNode.getId()) != null || graph.getEdge(thisNode.getId() + "_" + nodeId) != null) {
                        makeNodeVisible(graph, (JSONObject) temp.getJSONObject(i), graphInfo);
                        i --;
                    }
                }
            }
            
            String query = "MATCH (p1:Paper)-[r:RELATED_TO]->(t:Topic), (p1)-[:CITES]->(p2:Paper) WHERE ID(p1) = " + nodeId + " RETURN p1, p2, t, r.Proportion AS prop LIMIT " + limit;
            runGraphQuery(query, graph, graphInfo);
            System.out.println("[QUERY]: " + query);
        }
    }
    
    /** Hiển thị những node ẩn */
    public static void showHiddenNodes(Graph graph, String nodeId, StorageObject graphInfo) {
        Node thisNode = graph.getNode(nodeId);
        
        if(thisNode != null) {
            
            Object[] pos = thisNode.getAttribute("xyz");
            
            if(!graphInfo.getObject().isNull("hidden_nodes")) {
                
                for(Object hiddenObject: graphInfo.getObject().getJSONArray("hidden_nodes")) {
                    Node hiddenNode = graph.getNode(((JSONObject) hiddenObject).get("id").toString());
                    
                    if(graph.getEdge(nodeId + "_" + hiddenNode.getId()) != null) {
                        hiddenNode.removeAttribute("ui.hide");
                        for (Edge edgeOfHiddenNode: hiddenNode.getEachEdge()) {
                            if (edgeOfHiddenNode.hasAttribute("ui.hide")) {
                                edgeOfHiddenNode.removeAttribute("ui.hide");
                            }
                        }
                    }  
                }
            }
        }
    }
    
    /** Ẩn node */
    public static void hideHiddenNodes(Graph graph, String nodeId, StorageObject graphInfo) {
        Node thisNode = graph.getNode(nodeId);
        if(thisNode != null) {
            
            Object[] pos = thisNode.getAttribute("xyz");
            
            if(!graphInfo.getObject().isNull("hidden_nodes")) {
                for(Object hiddenObject: graphInfo.getObject().getJSONArray("hidden_nodes")) {
                    Node hiddenNode = (Node) graph.getNode(((JSONObject) hiddenObject).get("id").toString());
                    
                    if(graph.getEdge(nodeId + "_" + hiddenNode.getId()) != null) {
                        hiddenNode.setAttribute("ui.hide");
                        for (Edge edgeOfHiddenNode: hiddenNode.getEachEdge()) {
                            if (!edgeOfHiddenNode.hasAttribute("ui.hide")) {
                                edgeOfHiddenNode.setAttribute("ui.hide");
                            }
                        }
                    }
                }
            }
        }
    }
    
    /**  */
    public static void makeNodeVisible(Graph graph, JSONObject nodeInfo, StorageObject graphInfo) {
        String nodeId = nodeInfo.get("id").toString();
        if(!graphInfo.getObject().isNull("hidden_nodes") && graph.getNode(nodeId) != null) {
            graph.getNode(nodeId).removeAttribute("ui.hide");
            for (Object edge: graph.getNode(nodeId).getEachEdge()) {
                ((Edge) edge).removeAttribute("ui.hide");
            }
            graphInfo.putObjectToArray("shown_nodes", nodeInfo, false); 
            graphInfo.removeObjectFromArray("hidden_nodes", nodeInfo);
        }
    }
    
    /**
     * Assign the coordinates to the node in the graph.
     * @param node
     */
    public static void setPositionToNode(Node node) {
        double max = 100.0;
        double min = -100.0;
        double randomDoubleX = 0.0; 
        double randomDoubleY = 0.0; 
        do {
            randomDoubleX = (Math.random() * ((max - min) + 1)) + min;
            randomDoubleY = (Math.random() * ((max - min) + 1)) + min;
        }
        while(Math.hypot(randomDoubleX, randomDoubleY) < 50.0);
        double spacingX = randomDoubleX;
        double spacingY = randomDoubleY;
        
        Node parent = findParentWithHighestLevel(node);
        if(parent == null)
            return;

        double[] positionOfParent = Toolkit.nodePosition(parent);
        double x = positionOfParent[0] + spacingX;
        double y = positionOfParent[1] + spacingY;
        node.setAttribute("xyz", x, y, 0.0);
    }

    /**
     * Determine the parent of a node that has the highest level set.
     * @param node
     * @return parent node that has the highest level assigned
     */
    static Node findParentWithHighestLevel(Node node) {
        int inDegreeOfNode = node.getInDegree();
        Node parent = null;

        Iterator<Edge> nodeIterator = node.getEachEnteringEdge().iterator();
        if(inDegreeOfNode == 1)
            parent = nodeIterator.next().getOpposite(node);
        else if (inDegreeOfNode > 1) {
            parent = nodeIterator.next().getOpposite(node);
            while (nodeIterator.hasNext()) {
                Node temp = nodeIterator.next().getOpposite(node);
                if (temp.hasAttribute("layoutLayer") && (int) temp.getAttribute("layoutLayer") > (int) parent.getAttribute("layoutLayer")) {
                        parent = temp;
                }
            }
        }

        if(parent != null && !parent.hasAttribute("layouted")) {
            parent.setAttribute("layouted", "true");
            setPositionToNode(parent);
        }
        return parent;
    }

    public static TypeOfNode typeOfNode(Graph graph, StorageObject graphInfo, String nodeId) {
        if(graph.getNode(nodeId) != null) {
            if(graphInfo.hasKeyValueToObject("topics", "id", nodeId)) {
                return TypeOfNode.TOPIC;
            }
            else if(graphInfo.hasKeyValueToObject("papers", "id", nodeId)) {
                return TypeOfNode.PAPER;
            }
        }
        return TypeOfNode.NONE;
    }
    
    public static List<Component> getAllComponents(final Container c) {
        Component[] comps = c.getComponents();
        List<Component> compList = new ArrayList<Component>();
        for (Component comp : comps) {
          compList.add(comp);
          if (comp instanceof Container) {
            compList.addAll(getAllComponents((Container) comp));
          }
        }
    return compList;
  }
}