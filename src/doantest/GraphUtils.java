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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.geom.Line2D;
import javax.swing.JOptionPane;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
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
    public static void showMoreNodeInfo(Graph graph, String NodeId, StorageObject graphInfo, View view) {
        if(!graphInfo.getObject().isNull("nodes")) {
            String label = "Node's Information:\n";
            int _length = graphInfo.getObject().getJSONArray("nodes").length();
            for(int i = 0; i < _length; i ++) {
                JSONObject obj = (JSONObject) graphInfo.getObject().getJSONArray("nodes").getJSONObject(i);
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
            
        
            if(!graphInfo.getObject().isNull("hidden_nodes")) {
                JSONArray hidden_nodes = (JSONArray) graphInfo.getObject().getJSONArray("hidden_nodes");
                for(Object _node: hidden_nodes) {
                    JSONObject node = (JSONObject) _node;
                    graph.getNode(node.get("id").toString()).removeAttribute("ui.hide");
                    graph.getNode(node.get("id").toString()).getEnteringEdgeSet().forEach(edge -> {
                        edge.removeAttribute("ui.hide");
                    });
                    graph.getNode(node.get("id").toString()).getLeavingEdgeSet().forEach(edge -> {
                        edge.removeAttribute("ui.hide");
                    });
                }
            }
            DefaultView _view = (DefaultView) view;
            drawTextOnView(_view, label, 20f, 20f);
        }
    }
    
    private static void drawTextOnView(DefaultView view, String text, float x, float y) {
        
        
            view.setForeLayoutRenderer(new LayerRenderer() {
                @Override
                public void render(Graphics2D graphics2D, GraphicGraph graphicGraph, double v, int i, int i1, double v1, double v2, double v3, double v4) {
                    float heightOfText = graphics2D.getFontMetrics().getHeight() * 1.5f;
                    float newY = y;
                    Font myFont = new Font ("Arial", Font.BOLD, 16);
                    graphics2D.setColor(Color.BLACK);
                    graphics2D.setFont(myFont);
                    for (String line : text.split("\n")) {
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
            graph.getNode(NodeId).addAttribute("ui.label", "Node ID: " + NodeId);
        }
        
        if(!graphInfo.getObject().isNull("hidden_nodes")) {
            JSONArray hidden_nodes = (JSONArray) graphInfo.getObject().getJSONArray("hidden_nodes");
                for(Object _node: hidden_nodes) {
                    JSONObject node = (JSONObject) _node;
                    graph.getNode(node.get("id").toString()).addAttribute("ui.hide");
                    graph.getNode(node.get("id").toString()).getEnteringEdgeSet().forEach(edge -> {
                        edge.addAttribute("ui.hide");
                    });
                    graph.getNode(node.get("id").toString()).getLeavingEdgeSet().forEach(edge -> {
                        edge.addAttribute("ui.hide");
                    });
                }
        }
    }
    
    public static void addNodeToGraph(Graph graph, JSONObject nodeObj, String color, boolean isHidden) {
        
        String NodeId = String.valueOf(nodeObj.getInt("id"));

        String NodeLabel = nodeObj.getJSONArray("labels").getString(0);
        Random random = new Random();
        String workingDir = System.getProperty("user.dir");
        
        if(graph.getNode(NodeId) == null) {
            graph.addNode(NodeId);        
//            System.out.println("Node: " + nodeObj);

            graph.getNode(NodeId).setAttribute("xyz", random.nextInt(5), random.nextInt(5), 0);
            if(graph.getNode(NodeId) != null) { 
                graph.getNode(NodeId).addAttribute("ui.label", "Node ID: " + NodeId);
            }
//            graph.getNode(NodeId).addAttribute("ui.label", "ID: " + NodeId);
//            graph.getNode(NodeId).addAttribute("ui.label", "Label: " + NodeLabel + ", ID: " + NodeId);
            switch(NodeLabel) {
                case "Paper":
                    //graph.getNode(NodeId).addAttribute("ui.style", " fill-mode: image-scaled; shadow-mode: none; shape: box; fill-image: url('" + workingDir + "/src/images/icon-news-32.png');");
                    graph.getNode(NodeId).addAttribute("ui.style", "fill-color: #cccccc;");
                    break;
                case "Topic":
                    graph.getNode(NodeId).addAttribute("ui.style", "fill-color: " + color + "; shape: box; ");
                    break;
                default:
                    break;
            }
        }
        if(isHidden) {
            graph.getNode(NodeId).addAttribute("ui.hide");
        }
    }
    
//    public static void addNodeToGraph(Graph graph, JSONObject nodeObj, String color, float x, float y) {
//    }
    
    public static void addEdgeToGraph(Graph graph, String SourceNodeId, String TargetNodeId, TypeOfRelationship type, String color, String sprite) {
        String edgeName = SourceNodeId + TargetNodeId;
        if(graph.getEdge(edgeName) == null) {
            graph.addEdge(edgeName, SourceNodeId, TargetNodeId, true);
            switch(type) {
                case RELATED_TO:
                    graph.getEdge(edgeName).addAttribute("ui.style", "fill-color: " + color + ";");
                    graph.getEdge(edgeName).setAttribute("ui.label", "Proportion: " + String.format("%.2f", (Float.parseFloat(sprite) * 100)) + "%");
                    break;
                case CITES:
                    graph.getEdge(edgeName).addAttribute("ui.style", "fill-color: " + color + " ;");
                    break;
                default:
                    graph.getEdge(edgeName).addAttribute("ui.style", "fill-color: " + color + ";");
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
    private static void runGraphQuery(String query, Graph graph, StorageObject graphInfo) {
        /** TODO: Kiểm tra nếu Topic có màu rồi thì lấy màu có sẳn */
        Random random;
        String colorCode;
        
        /** 
         *  Tạo một biến JSONObject 
         *  lưu lại cái node Paper, Topic
         *  và các relationship giữa các node 
         */
        JSONObject jsonObj = new JSONObject();
        if(!graphInfo.getObject().isNull("nodes")) {
            jsonObj.put("nodes", graphInfo.getObject().getJSONArray("nodes"));
        }
        if(!graphInfo.getObject().isNull("papers")) {
            jsonObj.put("papers", graphInfo.getObject().getJSONArray("papers"));
        }
        if(!graphInfo.getObject().isNull("cites")) {
            jsonObj.put("cites", graphInfo.getObject().getJSONArray("cites"));
        }
        if(!graphInfo.getObject().isNull("related_to")) {
            jsonObj.put("related_to", graphInfo.getObject().getJSONArray("related_to"));
        }
        if(!graphInfo.getObject().isNull("topics")) {
            jsonObj.put("topics", graphInfo.getObject().getJSONArray("topics"));
        }
        if(!graphInfo.getObject().isNull("topic_colors")) {
            jsonObj.put("topic_colors", graphInfo.getObject().getJSONObject("topic_colors"));
        }
        if(!graphInfo.getObject().isNull("years")) {
            jsonObj.put("years", graphInfo.getObject().getJSONObject("years"));
        }
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
                if(jsonObj.isNull("nodes")) {
                    jsonObj.put("nodes", new JSONArray().put(p1));
                    
                } else {
                    if(!arrayContainsObject(jsonObj.getJSONArray("nodes"), p1)) {
                        jsonObj.getJSONArray("nodes").put(p1);
                    }
                }
                addNodeToGraph(graph, p1, "", false);
                
//                if(jsonObj.isNull("papers")) {
//                    jsonObj.put("papers", new JSONArray().put(p2));
//                    
//                } else {
//                    if(!arrayContainsObject(jsonObj.getJSONArray("papers"), p2)) {
//                        jsonObj.getJSONArray("papers").put(p2);
//                    }
//                }
                if(jsonObj.isNull("nodes")) {
                    jsonObj.put("nodes", new JSONArray().put(p2));
                    
                } else {
                    if(!arrayContainsObject(jsonObj.getJSONArray("nodes"), p2)) {
                        jsonObj.getJSONArray("nodes").put(p2);
                    }
                }
                addNodeToGraph(graph, p2, "", false);
                if(jsonObj.isNull("cites")) {
                    jsonObj.put("cites", new JSONArray().put(cites));
                } else {
                    if(!arrayContainsObject(jsonObj.getJSONArray("cites"), cites)) {
                        jsonObj.getJSONArray("cites").put(cites);
                    }
                }
                addEdgeToGraph(graph, p1.get("id").toString(), p2.get("id").toString(), TypeOfRelationship.CITES, "black", "");
                
                if(jsonObj.isNull("topics")) {
                    jsonObj.put("topics", new JSONArray().put(t));
                } else {
                    if(!arrayContainsObject(jsonObj.getJSONArray("topics"), t)) {
                        jsonObj.getJSONArray("topics").put(t);
                    }
                    else {
                        colorCode = jsonObj.getJSONObject("topic_colors").get(t.get("id").toString()).toString();
                    }
                }
                addNodeToGraph(graph, t, colorCode, false);
                if(jsonObj.isNull("topic_colors")) {
                    jsonObj.put("topic_colors", new JSONObject().put(t.get("id").toString(), colorCode));
                }
                else {
                    jsonObj.getJSONObject("topic_colors").put(t.get("id").toString(), colorCode);
                }

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
                addEdgeToGraph(graph, p1.get("id").toString(), t.get("id").toString(), TypeOfRelationship.RELATED_TO, "black", prop);
                
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(!jsonObj.isNull("papers")) {

            /** 
             *  Với mỗi node Paper trong graph,
             *  thêm mảng bao gồm các proportion vào attribute "ui.pie-values".
             */
            for(Object _paper: jsonObj.getJSONArray("papers")) {
                JSONObject paper = (JSONObject) _paper;
                ArrayList<String> proportion = new ArrayList<>();
                ArrayList<String> colors = new ArrayList<>();

                String paperId = paper.get("id").toString();
                int _length = jsonObj.getJSONArray("related_to").length();
                for(int i = 0; i < _length; i++) {
//                    for(Object rls: jsonObj.getJSONArray("related_to")) {
                    JSONObject jsonRls = (JSONObject)jsonObj.getJSONArray("related_to").getJSONObject(i);
                    jsonRls.put("color", jsonObj.getJSONObject("topic_colors").get(jsonRls.getString("topic")));

                    if(jsonRls.getString("paper").equals(paperId)) {
                        proportion.add(jsonRls.getString("proportion"));
                        colors.add(jsonRls.getString("color"));
                    }
                }

                /** Màu trắng là dành cho % còn lại trong biểu đồ tròn. */
                colors.add("#cccccc"); 

                graph.getNode(paperId).addAttribute("ui.style", "fill-color: " + GraphUtils.printString(colors.toArray(new String[colors.size()]), ",") + "; shape: pie-chart;");
                graph.getNode(paperId).addAttribute("ui.pie-values", GraphUtils.printStringForDouble(proportion.toArray(new String[proportion.size()]), ","));
                    
            }
        }
        /** Lưu danh sách thông tin các node hiển thị trên màn hình */
        graphInfo.setObject(jsonObj);
    }
    
    private static void runTimelineQuery(String query, Graph graph, StorageObject graphInfo) {
        
        /** 
         *  Tạo một biến JSONObject 
         *  lưu lại cái node Paper, Topic
         *  và các relationship giữa các node 
         */
        JSONObject jsonObj = new JSONObject();
        if(!graphInfo.getObject().isNull("nodes")) {
            jsonObj.put("nodes", graphInfo.getObject().getJSONArray("nodes"));
        }
//        if(!graphInfo.getObject().isNull("papers")) {
//            jsonObj.put("papers", graphInfo.getObject().getJSONArray("papers"));
//        }
        if(!graphInfo.getObject().isNull("years")) {
            jsonObj.put("years", graphInfo.getObject().getJSONObject("years"));
        }
        if(!graphInfo.getObject().isNull("cites")) {
            jsonObj.put("cites", graphInfo.getObject().getJSONArray("cites"));
        }
        if(!graphInfo.getObject().isNull("related_to")) {
            jsonObj.put("related_to", graphInfo.getObject().getJSONArray("related_to"));
        }
        if(!graphInfo.getObject().isNull("topics")) {
            jsonObj.put("topics", graphInfo.getObject().getJSONArray("topics"));
        }
        if(!graphInfo.getObject().isNull("topic_colors")) {
            jsonObj.put("topic_colors", graphInfo.getObject().getJSONObject("topic_colors"));
        }
        if(!graphInfo.getObject().isNull("hidden_nodes")) {
            jsonObj.put("hidden_nodes", graphInfo.getObject().getJSONArray("hidden_nodes"));
        }
    
        ResultSet rs;
        PreparedStatement stmt = null;
        
        // Kết nối
        Connection con;
        try {
            con = DriverManager.getConnection(Constants.CONNECTION_URL, Constants.USERNAME, Constants.PASSWORD);
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
            
            while (rs.next()) {                
                // Import style từ file css thông qua lớp StyleImporter
                String style = getStyle("style.css");
                // Add style vào graph
                graph.addAttribute("ui.stylesheet", style);
                
                JSONObject p = new JSONObject(rs.getString("p"));  
                JSONObject p1 = new JSONObject(rs.getString("p1"));
                String year = String.valueOf(rs.getInt("year"));
                /** 
                 *  Kiểm tra xem trong jsonObj đã có mảng (JSONArray) "papers" hay chưa,  
                 *  nếu chưa thì tạo một mảng "papers" mới, 
                 *  nếu đã có rồi thì kiểm tra đã có p trong "papers" chưa,
                 *  nếu chưa có thì thêm p vào "papers".
                 */
//                if(jsonObj.isNull("papers")) {
//                    jsonObj.put("papers", new JSONArray().put(p));
//                    
//                } else {
//                    if(!arrayContainsObject(jsonObj.getJSONArray("papers"), p)) {
//                        jsonObj.getJSONArray("papers").put(p);
//                    }
//                }
                if(jsonObj.isNull("nodes")) {
                    jsonObj.put("nodes", new JSONArray().put(p));
                    
                } else {
                    if(!arrayContainsObject(jsonObj.getJSONArray("nodes"), p)) {
                        jsonObj.getJSONArray("nodes").put(p);
                    }
                }
                addNodeToGraph(graph, p, "", false);
                if(jsonObj.isNull("hidden_nodes")) {
                    jsonObj.put("hidden_nodes", new JSONArray().put(p1));
                    addNodeToGraph(graph, p1, "", true);
                } else {
                    if(!arrayContainsObject(jsonObj.getJSONArray("hidden_nodes"), p1)) {
                        jsonObj.getJSONArray("hidden_nodes").put(p1);
                        addNodeToGraph(graph, p1, "", true);
                    }
                    else {
                        addNodeToGraph(graph, p1, "", false);
                    }
                }
                addEdgeToGraph(graph, p.get("id").toString(), p1.get("id").toString(), TypeOfRelationship.CITES, "black", "");
                
                
                if(jsonObj.isNull("years")) {
                    jsonObj.put("years", new JSONObject().put(year, new JSONArray().put(p.get("id").toString())));
                    
                } else {
                    if(jsonObj.getJSONObject("years").has(year)) {
                        if(!jsonObj.getJSONObject("years").getJSONArray(year).toString().contains(p.get("id").toString())) {
                            jsonObj.getJSONObject("years").getJSONArray(year).put(p.get("id").toString());
                        }
                    }
                    else {
                        jsonObj.getJSONObject("years").put(year, new JSONArray().put(p.get("id").toString()));
                    }
                }
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
//        
//        if(!jsonObj.isNull("years")) {  
//            Random random;
//            String colorCode;          
//            Object[] yearKeys = jsonObj.getJSONObject("years").keySet().toArray();
//            int index = 0;
//            for(Object yearKey: yearKeys) {
//                String year = yearKey.toString();
//                /** In node với màu ngẫu nhiên */
//                random = new Random();
//                colorCode = String.format("#%06x", random.nextInt(256*256*256));
//                int _length = jsonObj.getJSONObject("years").getJSONArray(year).length();
//                for(int i = 0; i < _length; i++) {
////                for(Object node: jsonObj.getJSONObject("years").getJSONArray(year)) {
//                    String nodeId = (String) jsonObj.getJSONObject("years").getJSONArray(year).getString(i);
//                    float x = (float)Math.random() * ((20f * (index + 1)) - (20f * index)) + 20f * index;
//                    float y = (float)Math.random() * (50f - 0.0f) + 0.0f;
//                    graph.getNode(nodeId).addAttribute("ui.frozen");
//                    graph.getNode(nodeId).setAttribute("xyz", x, y, 0);
////                    graph.getNode(nodeId).addAttribute("x", x);
////                    graph.getNode(nodeId).addAttribute("y", y);
//                    graph.getNode(nodeId).setAttribute("ui.style", "fill-color: " + colorCode + ";");
//                }
//                index ++;
//            }
//        }
        
        /** Lưu danh sách thông tin các node hiển thị trên màn hình */
        graphInfo.setObject(jsonObj);
    }
    
    public static boolean reset(JFrame frame, Graph graph, StorageObject graphInfo/*JSONArray shownNodes*/) {
        if(!graphInfo.getObject().isNull("nodes"))
        {
            if(graphInfo.getObject().getJSONArray("nodes").length() > 0) {
            int output = JOptionPane.showConfirmDialog(frame
                   , "Reset Graph"
                   ,"Reset Graph"
                   ,JOptionPane.YES_NO_OPTION,
                   JOptionPane.INFORMATION_MESSAGE);

                if(output == JOptionPane.YES_OPTION){
                    graph.clear();
                    graphInfo.reset();// = new JSONObject();
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
    public static void setGraph(int startYear, int endYear, String topic, Graph graph, StorageObject graphInfo, int limit) {
        String query;
        if(topic == "All") {
//            query = "MATCH (p1:Paper)-[r:RELATED_TO]->(t:Topic), (p1)-[:CITES]->(p2:Paper) WHERE p1.Year >= " + startYear + " AND p1.Year <= " + endYear + " RETURN p1, p2, t, r.Proportion AS prop LIMIT " + limit;
            query = "MATCH (p1:Paper)-[r:RELATED_TO]->(t:Topic), (p1)-[:CITES]->(p2:Paper) WHERE p1.Year >= " + startYear + " AND p1.Year <= " + endYear + " AND p2.Year >= " + startYear + " AND p2.Year <= " + endYear + " RETURN p1, p2, t, r.Proportion AS prop LIMIT " + limit;
        }
        else {
//            query = "MATCH (p1:Paper)-[r:RELATED_TO]->(t:Topic), (p1)-[:CITES]->(p2:Paper) WHERE p1.Year >= " + startYear + " AND p1.Year <= " + endYear + " AND t.TopicId = " + topic + " RETURN p1, p2, t, r.Proportion AS prop LIMIT " + limit;
            query = "MATCH (p1:Paper)-[r:RELATED_TO]->(t:Topic), (p1)-[:CITES]->(p2:Paper) WHERE p1.Year >= " + startYear + " AND p1.Year <= " + endYear + " AND p2.Year >= " + startYear + " AND p2.Year <= " + endYear + " AND t.TopicId = " + topic + " RETURN p1, p2, t, r.Proportion AS prop LIMIT " + limit;
        }
        runGraphQuery(query, graph, graphInfo);
        System.out.println("[QUERY]: " + query);
    }
    
    public static void setTimeline(int startYear, int endYear, String topic, Graph graph, StorageObject graphInfo, int limit) {
        String query;
        if(topic == "All") {
            query = "MATCH (p: Paper)-[:CITES]->(p1: Paper) WHERE p.Year = " + startYear + " RETURN p, p.Year AS year, p1 LIMIT " + limit;
            for(int year = startYear + 1; year <= endYear; year ++) {
                query += " UNION MATCH (p: Paper)-[:CITES]->(p1: Paper) WHERE p.Year = " + year + " RETURN p, p.Year AS year, p1 LIMIT " + limit;
            }
        }
        else {
            query = "MATCH (p: Paper)-[:RELATED_TO]->(t: Topic), (p)-[:CITES]->(p1: Paper) WHERE p.Year = " + startYear + " AND t.TopicId = " + topic + " RETURN p, p.Year AS year, p1 LIMIT " + limit;
            for(int year = startYear + 1; year <= endYear; year ++) {
                query += " UNION MATCH (p: Paper)-[:RELATED_TO]->(t: Topic), (p)-[:CITES]->(p1: Paper) WHERE p.Year = " + year + " AND t.TopicId = " + topic + " RETURN p, p.Year AS year, p1 LIMIT " + limit;
            }
        }
        runTimelineQuery(query, graph, graphInfo);
        System.out.println("[QUERY]: " + query);
    }
    
    public static void showGraphOnPanel(Graph graph, StorageObject graphInfo, JPanel panel, InfiniteProgressPanel glassPane) {
        //Tạo View Panel để chứa Graph
        Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        viewer.enableAutoLayout(); // cho graph chuyển động       
        ViewPanel viewPanel = viewer.addDefaultView(false);    
        double zoom = ((DefaultView) viewPanel).getCamera().getViewPercent();
        System.out.println("zoomzoomzoom: " + zoom);
        ((DefaultView) viewPanel).setForeLayoutRenderer(new LayerRenderer() {
            @Override
            public void render(Graphics2D graphics2D, GraphicGraph graphicGraph, double v, int i, int i1, double v1, double v2, double v3, double v4) {
                
                graphics2D.setColor(Color.BLACK);
                graphics2D.drawString("Hello", 50, 50);
            }
        });
        
        panel.removeAll();
        panel.setLayout(new GridLayout());
        //Panel chứa graph
        panel.add(viewPanel);
        panel.revalidate();
        
        // Xử lí sự kiện về Mouse của View Panel
        ViewerPipe fromViewer = viewer.newViewerPipe();
        fromViewer.addSink(graph);
        fromViewer.addViewerListener(new MouseHandler(graph, viewPanel, fromViewer, graphInfo, panel, glassPane));
    }
    
    public static void showTimeLineOnPanel(Graph graph, StorageObject graphInfo, JPanel panel, InfiniteProgressPanel glassPane) {
        //Tạo View Panel để chứa Graph
        Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        viewer.disableAutoLayout();
        
        DefaultView view = (DefaultView) viewer.addDefaultView(false);
        view.resizeFrame(panel.getWidth(), panel.getHeight());
        view.getCamera().getViewCenter();  
//        view.getCamera().setViewPercent(0.5);
        double zoom = view.getCamera().getViewPercent();
        System.out.println("zoomzoomzoom: " + zoom);
        
        if(!graphInfo.getObject().isNull("years")) {  
            Random random;
            String colorCode;    
                    int numOfYears = graphInfo.getObject().getJSONObject("years").length();
                    float width_of_column = view.getWidth() / numOfYears;
                    Object[] years = invertUsingFor(graphInfo.getObject().getJSONObject("years").keySet().toArray());
                    int index = 0;
                    for(Object _year : years) {
                    String year = _year.toString();
                    /** In node với màu ngẫu nhiên */
                    random = new Random();
                    colorCode = String.format("#%06x", random.nextInt(256*256*256));
                    int _length = graphInfo.getObject().getJSONObject("years").getJSONArray(year).length();
                    for(int ind = 0; ind < _length; ind++) {
        //                for(Object node: jsonObj.getJSONObject("years").getJSONArray(year)) {
                        String nodeId = (String) graphInfo.getObject().getJSONObject("years").getJSONArray(year).getString(ind);
                        float x = (float)Math.random() * ((width_of_column * (index + 1)) - (width_of_column * index)) + width_of_column * index;
                        float y = (float)Math.random() * (view.getHeight() - 0.0f) + 0.0f;
                        graph.getNode(nodeId).addAttribute("ui.frozen");
                        graph.getNode(nodeId).setAttribute("xyz", x, y, 0);
        //                    graph.getNode(nodeId).addAttribute("x", x);
        //                    graph.getNode(nodeId).addAttribute("y", y);
                        graph.getNode(nodeId).setAttribute("ui.style", "fill-color: " + colorCode + ";");
                    }
                        index++;
                    }
                }
        
        view.setBackLayerRenderer(new LayerRenderer() {
            @Override
            public void render(Graphics2D graphics2D, GraphicGraph graphicGraph, double v, int i, int i1, double v1, double v2, double v3, double v4) {
                graphics2D.setColor(Color.BLACK);
//                graphics2D.drawString("1990", 10, 30);
                if(!graphInfo.getObject().isNull("years")) {
                    
                    Object[] years = invertUsingFor(graphInfo.getObject().getJSONObject("years").keySet().toArray());
                    int numOfYears = graphInfo.getObject().getJSONObject("years").length();
                    float width_of_column = view.getWidth() / numOfYears;
                    if(width_of_column < 200) {
                        width_of_column = 200;
                        view.resizeFrame(numOfYears * 200, view.getHeight());
                    }
                    int index = 0;
                    for(Object _year : years) {
                    String year = _year.toString();
                    int _length = graphInfo.getObject().getJSONObject("years").getJSONArray(year).length();
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
        
        
        panel.removeAll();
        panel.setLayout(new GridLayout());
        //Panel chứa graph
        panel.add(view);
        panel.revalidate();
        
        // Xử lí sự kiện về Mouse của View Panel
        ViewerPipe fromViewer = viewer.newViewerPipe();
        fromViewer.addSink(graph);
        fromViewer.addViewerListener(new MouseHandler(graph, view, fromViewer, graphInfo, panel, glassPane));
        
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
    public static void getMoreNodes(Graph graph, String nodeId, StorageObject graphInfo, int limit){        
        String query = "MATCH (p1:Paper)-[r:RELATED_TO]->(t:Topic), (p1)-[:CITES]->(p2:Paper) WHERE ID(p1) = " + nodeId + " RETURN p1, p2, t, r.Proportion AS prop LIMIT " + limit;
        
//        if(!graphInfo.getObject().isNull("topics")) {
//            graphInfo.getObject().getJSONArray("topics").forEach((topic) -> {
//                JSONObject _topic = (JSONObject) topic;
//                if(_topic.get("id").toString().equals(nodeId)) {
//                    query = "MATCH (p1:Paper)-[r:RELATED_TO]->(t:Topic), (p2:Paper)-[r:RELATED_TO]->(t:Topic) WHERE ID(t) = " + nodeId + " RETURN p1, p2, t, r.Proportion AS prop LIMIT " + limit;
//                }
//            });
//        }
        runGraphQuery(query, graph, graphInfo);

    }
    
    public static void showAllHiddenNodes(Graph graph, StorageObject graphInfo, String nodeId) {
    }
}
