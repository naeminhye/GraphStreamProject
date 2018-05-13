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
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.geom.Line2D;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerPipe;

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
    public static void showMoreNodeInfo(Graph graph, String NodeId, StorageObject graphInfo) {
        if(!graphInfo.getObject().isNull("nodes")) {
            String label = "[Node's Information]: ";
            for(Object object: graphInfo.getObject().getJSONArray("nodes")) {
                JSONObject obj = (JSONObject) object;
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
    
    public static void addNodeToGraph(Graph graph, JSONObject nodeObj, String color) {
        
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
                addNodeToGraph(graph, p1, "");
                
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
                addNodeToGraph(graph, p2, "");
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
                addNodeToGraph(graph, t, colorCode);
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
                        JSONObject jsonRls = (JSONObject)rls;
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
        if(!graphInfo.getObject().isNull("papers")) {
            jsonObj.put("papers", graphInfo.getObject().getJSONArray("papers"));
        }
        if(!graphInfo.getObject().isNull("years")) {
            jsonObj.put("years", graphInfo.getObject().getJSONObject("years"));
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
                addNodeToGraph(graph, p, "");
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
        
        if(!jsonObj.isNull("years")) {  
            Random random;
            String colorCode;          
            Iterator<String> years = jsonObj.getJSONObject("years").keys();
            int index = 0;
            while (years.hasNext()) {
                String year = years.next();
                /** In node với màu ngẫu nhiên */
                random = new Random();
                colorCode = String.format("#%06x", random.nextInt(256*256*256));
                for(Object node: jsonObj.getJSONObject("years").getJSONArray(year)) {
                    String nodeId = (String) node;
                    float x = (float)Math.random() * ((200f * (index + 1)) - (200f * index)) + 200f * index;
                    float y = (float)Math.random() * (1000f - 0.0f) + 0.0f;
//                    graph.getNode(nodeId).addAttribute("ui.frozen");
//                    graph.getNode(nodeId).addAttribute("x", x);
//                    graph.getNode(nodeId).addAttribute("y", y);
                    graph.getNode(nodeId).setAttribute("ui.style", "fill-color: " + colorCode + ";");
                }
                index ++;
            }
        }
        
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
        System.out.println("After running graphInfo: " + graphInfo.getObject());
    }
    
    public static void setTimeline(int startYear, int endYear, String topic, Graph graph, StorageObject graphInfo, int limit) {
        String query;
        if(topic == "All") {
            query = "MATCH (p: Paper) WHERE p.Year = " + startYear + " RETURN p, p.Year AS year LIMIT " + limit;
            for(int year = startYear + 1; year <= endYear; year ++) {
                query += " UNION MATCH (p: Paper) WHERE p.Year = " + year + " RETURN p, p.Year AS year LIMIT " + limit;
            }
        }
        else {
            query = "MATCH (p: Paper)-[:RELATED_TO]->(t: Topic) WHERE p.Year = " + startYear + " AND t.TopicId = " + topic + " RETURN p, p.Year AS year LIMIT " + limit;
            for(int year = startYear + 1; year <= endYear; year ++) {
                query += " UNION MATCH (p: Paper)-[:RELATED_TO]->(t: Topic) WHERE p.Year = " + year + " AND t.TopicId = " + topic + " RETURN p, p.Year AS year LIMIT " + limit;
            }
        }
        System.out.println("QUERY: " + query);
        runTimelineQuery(query, graph, graphInfo);
        System.out.println("After running graphInfo: " + graphInfo.getObject());
    }
    
    public static void showGraphOnPanel(Graph graph, StorageObject graphInfo, JPanel panel, InfiniteProgressPanel glassPane) {
        //Tạo View Panel để chứa Graph
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
        fromViewer.addViewerListener(new MouseHandler(graph, viewPanel, fromViewer, graphInfo, panel, glassPane));
    }
    
    public static void showTimeLineOnPanel(Graph graph, StorageObject graphInfo, JPanel panel, InfiniteProgressPanel glassPane) {
        //Tạo View Panel để chứa Graph
        Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        
        ViewPanel viewPanel = viewer.addDefaultView(false);
        
        panel.removeAll();
        panel.setLayout(new GridLayout());
        //Panel chứa graph
        panel.add(viewPanel);
        panel.revalidate();
        
        // Xử lí sự kiện về Mouse của View Panel
        ViewerPipe fromViewer = viewer.newViewerPipe();
        fromViewer.addSink(graph);
        fromViewer.addViewerListener(new MouseHandler(graph, viewPanel, fromViewer, graphInfo, panel, glassPane));
        
        for(int i = 0; i < 3; i ++) {
               
        }
    }
    
    private static JComponent createVerticalSeparator() {
        JSeparator x = new JSeparator(SwingConstants.VERTICAL);
        x.setPreferredSize(new Dimension(3,50));
        return x;
    }
  
    private static JComponent createHorizontalSeparator() {
        JSeparator x = new JSeparator(SwingConstants.HORIZONTAL);
        x.setPreferredSize(new Dimension(50,3));
        return x;
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
    
}
