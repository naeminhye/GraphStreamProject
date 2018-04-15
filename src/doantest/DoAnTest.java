/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doantest;

import java.awt.*;
import javax.swing.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.ui.swingViewer.*;
import org.graphstream.ui.view.*;

import java.sql.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.json.JSONArray;
import org.json.JSONObject;

public class DoAnTest {

    public static void main(String args[]) {
        EventQueue.invokeLater(new DoAnTest()::display);
    }

    private void display() {
        //Tạo các component cho giao diện (vd: button)
        Button btn1 = new Button();
        btn1.setLabel("Nut 1");
        btn1.setPreferredSize(new Dimension(200, 50));

        Button btn2 = new Button();
        btn2.setLabel("Nut 2");
        btn2.setPreferredSize(new Dimension(200, 50));

        //Tạo Graph từ GraphStream
        Graph graph = new SingleGraph("Tutorial", false, true);

        // Kết nối
        Connection con;
        try {
            con = DriverManager.getConnection("jdbc:neo4j:bolt://localhost:11005", "neo4j", "123");
            PreparedStatement stmt = null;
            // Querying
//            String querry = "MATCH p=()-[r:CITES]->() RETURN p LIMIT 3";
            // Chọn ra 10 bài báo xuất bản năm 1984
            String querry = "MATCH (p:Paper) WHERE p.Year = 1984 RETURN p LIMIT 10";
//            String querry = "MATCH (p) RETURN p LIMIT 3";
            
            stmt = con.prepareStatement(querry);
            ResultSet rs = stmt.executeQuery();
            
            Random rand = new Random();
            Random random;
            String colorCode;
            //Lấy dữ liệu về 
            while (rs.next()) {
                //Chuyển thành JSON
//                JSONArray ja = new JSONArray(rs.getString("p"));
                JSONObject jo = new JSONObject(rs.getString("p"));
                //System.out.println(rs.getString("p"));

                String nodeId = String.valueOf(jo.getInt("id"));
                graph.addNode(nodeId);
//                //Lấy node đầu, node cuối - phân biệt nhau bởi ID
//                String startId = String.valueOf(ja.getJSONObject(0).getInt("id"));
//                String endId = String.valueOf(ja.getJSONObject(2).getInt("id"));
//
//                //Add các node vào graph đã tạo
//                graph.addNode(startId);
//                graph.addNode(endId);
//                graph.addEdge(startId + endId, startId, endId);
//                
//                //Style
//                graph.addAttribute("ui.stylesheet", "node.red { fill-color: pink; size: 40px, 40px; stroke-mode: plain; stroke-color: blue;}");
//                
                random = new Random();
//                //In node với màu ngẫu nhiên 
                colorCode = String.format("#%06x", random.nextInt(256*256*256));
                graph.addAttribute("ui.stylesheet", "node { fill-color: " + colorCode + "; size: 20px, 20px; stroke-mode: plain; stroke-color: blue;}");
//                graph.getNode(nodeId).addAttribute("ui.class", "color");
                graph.getNode(nodeId).setAttribute("xyz", random.nextInt(5), random.nextInt(5), 0);
//                //Thêm thuộc tính cho các node - dùng random để rand vị trí khác nhau cho các node
//                //Nếu không cho vị trí khác nhau, tất cả các node sẽ nằm trùng lên nhau, phải kéo mới thấy được 
//                Node a = graph.getNode(startId);
//                a.setAttribute("xyz", rand.nextInt(50), 2, 0);
//                Node b = graph.getNode(endId);
//                b.setAttribute("xyz", 2, 3, 0);
////                b.addAttribute("ui.class", "color");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DoAnTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Tạo View Panel để chứa Graph
        Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        ViewPanel viewPanel = viewer.addDefaultView(false);

        //Tạo Panel chứa viewpanel
        JPanel panel = new JPanel(new GridLayout()) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(800, 600); 
            }
        };
        panel.setBorder(BorderFactory.createLineBorder(Color.blue, 5));
        panel.add(viewPanel);

        //Tạo GridBagLayout cho cả ứng dụng
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;

        //Tạo frame để chứa tất cả những component trên
        JFrame frame = new JFrame();
        frame.setLayout(gridBagLayout);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Đặt vị trí cho các component trong frame bằng cách chỉnh thuộc tính Layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(btn1, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        frame.add(btn2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 3;
        frame.add(panel, gbc);

        //Chỉnh các thuộc tính hiển thị khác của frame
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
