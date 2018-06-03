/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doantest;

import static doantest.GraphUtils.*;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.json.JSONArray; 
import org.graphstream.algorithm.generator.BaseGenerator;
import org.graphstream.algorithm.generator.LobsterGenerator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.stream.ProxyPipe;
import org.graphstream.stream.SinkAdapter;
import org.graphstream.ui.view.Viewer;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import java.util.Iterator;
import javax.swing.JOptionPane;

/**
 *
 * @author Hieu Nguyen
 */
public class SearchPaper extends javax.swing.JFrame {
    protected InfiniteProgressPanel glassPane;
        //Tạo Graph từ GraphStream
    protected Graph graph = new SingleGraph("Citation");
    protected StorageObject graphInfo = new StorageObject();
    protected JSONArray shownNodes = new JSONArray();
    private JFrame thisFrame = this;
    private String workingDir = System.getProperty("user.dir");
    
    /**
     * Creates new form SearchPaper
     */
    public SearchPaper() {
        initComponents();
        // Set JFrame vào giữa màn hình
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        
        // Set giá trị mặc định cho 2 number box startYear và endYear
        endYear.setValue(1981);        
        startYear.setValue(1980); 
        limit.setValue(10);

        Image questionMark = new ImageIcon(workingDir + "/src/images/question-mark.png").getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
        infoBtn.setIcon(new ImageIcon(questionMark));
        infoBtn.setContentAreaFilled(false);
        
        glassPane = new InfiniteProgressPanel("Loading ...", 8);
        this.setGlassPane(glassPane);
        
        searchBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if(!selectedPaper.getText().equals("") && !selectedTopic.getText().equals("")) {
                    int output = JOptionPane.showConfirmDialog(
                        thisFrame,
                        "You have chosen Node " + selectedPaper.getText() + " and Node " + selectedTopic.getText(),
                        "Do you want to find the related road?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE);

                    if(output == JOptionPane.YES_OPTION){
                        graph.clear();
                        graphInfo.reset();
                        glassPane.start();
                        new Thread(new Runnable() {
                            public void run() {
                                GraphUtils.setPaperFlow(selectedPaper.getText(), selectedTopic.getText(), 5, 1, graph, graphInfo);
                                showPaperFlowOnPanel(graph, graphInfo, displayPanel, glassPane);

                                glassPane.stop();
                                }
                        }, "Performer").start();
                    }
                    else if(output == JOptionPane.NO_OPTION){
                    }
                }
                else {
                    if((int)endYear.getValue() - (int)startYear.getValue() <= 4) {
                        if(GraphUtils.reset(thisFrame, graph, graphInfo))
                            loading();
                    }
                    else {
                        Object[] options = {"OK"};
                        int n = JOptionPane.showOptionDialog(thisFrame,
                            "You just can search within 5 years.","Note",
                            JOptionPane.PLAIN_MESSAGE,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[0]);
                    }
                }
            }
        });
        
        infoBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                // Mở Màn hình tìm kiếm bài báo
                Shortcuts shortcuts = new Shortcuts();
                shortcuts.setVisible(true);
            }
        });
    }
    
    public void loading() {
        glassPane.start();
        new Thread(new Runnable() {
            public void run() {
                // Hiển thị các Node và Edge sau khi click vào button "Search"
                display();

                glassPane.stop();
            }
        }, "Performer").start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        controlPanel = new javax.swing.JPanel();
        startYear = new javax.swing.JSpinner();
        endYear = new javax.swing.JSpinner();
        topicSelection = new javax.swing.JComboBox<>();
        displaySeletion = new javax.swing.JComboBox<>();
        searchBtn = new java.awt.Button();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        limit = new javax.swing.JSpinner();
        infoBtn = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        selectedPaper = new javax.swing.JLabel();
        selectedTopic = new javax.swing.JLabel();
        displayPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        controlPanel.setBackground(new java.awt.Color(204, 204, 204));
        controlPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        controlPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        startYear.setMinimumSize(new java.awt.Dimension(30, 20));

        endYear.setMinimumSize(new java.awt.Dimension(30, 20));

        topicSelection.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99" }));

        displaySeletion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Graph", "Timeline" }));

        searchBtn.setLabel("Search");
        searchBtn.setName("searchBtn"); // NOI18N

        jLabel1.setText("From");

        jLabel2.setText("To");

        jLabel3.setText("Topic");

        jLabel4.setText("Display");

        jLabel5.setText("Limit");

        limit.setMinimumSize(new java.awt.Dimension(30, 20));

        infoBtn.setBackground(new java.awt.Color(255, 255, 255));
        infoBtn.setBorder(null);
        infoBtn.setIconTextGap(0);
        infoBtn.setName("infoBtn"); // NOI18N
        infoBtn.setPreferredSize(new java.awt.Dimension(30, 30));

        jLabel6.setText("Selected Paper: ");
        jLabel6.setName(""); // NOI18N

        jLabel7.setText("Selected Topic: ");
        jLabel7.setName(""); // NOI18N

        selectedPaper.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        selectedPaper.setName("selectedPaper"); // NOI18N

        selectedTopic.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        selectedTopic.setName("selectedTopic"); // NOI18N

        javax.swing.GroupLayout controlPanelLayout = new javax.swing.GroupLayout(controlPanel);
        controlPanel.setLayout(controlPanelLayout);
        controlPanelLayout.setHorizontalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(startYear, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(endYear, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(topicSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(displaySeletion, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(limit, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(infoBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(controlPanelLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(selectedPaper, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(controlPanelLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(selectedTopic, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                .addComponent(searchBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        controlPanelLayout.setVerticalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(controlPanelLayout.createSequentialGroup()
                        .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(selectedPaper))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(selectedTopic)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(infoBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(limit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(topicSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(endYear, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(startYear, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel5)
                            .addComponent(displaySeletion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(searchBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        displayPanel.setBackground(new java.awt.Color(255, 255, 255));
        displayPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        javax.swing.GroupLayout displayPanelLayout = new javax.swing.GroupLayout(displayPanel);
        displayPanel.setLayout(displayPanelLayout);
        displayPanelLayout.setHorizontalGroup(
            displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        displayPanelLayout.setVerticalGroup(
            displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 719, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(displayPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(controlPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(controlPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(displayPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        controlPanel.getAccessibleContext().setAccessibleName("Search Filter");
        controlPanel.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents
   
    private void display() {
        switch(displaySeletion.getSelectedItem().toString()) {
            case "Graph":
                GraphUtils.setGraph((int)startYear.getValue(), (int)endYear.getValue(), (String)topicSelection.getSelectedItem(), graph, graphInfo, (int)limit.getValue());
                showGraphOnPanel(graph, graphInfo, displayPanel, glassPane);
                break;
            case "Timeline":
                GraphUtils.setTimeline((int)startYear.getValue(), (int)endYear.getValue(), (String)topicSelection.getSelectedItem(), graph, graphInfo, (int)limit.getValue());
                showTimeLineOnPanel(graph, graphInfo, displayPanel, glassPane);   
                break;
            default:
                break;
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SearchPaper.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SearchPaper.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SearchPaper.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SearchPaper.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SearchPaper().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel controlPanel;
    private javax.swing.JPanel displayPanel;
    private javax.swing.JComboBox<String> displaySeletion;
    private javax.swing.JSpinner endYear;
    private javax.swing.JButton infoBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JSpinner limit;
    private java.awt.Button searchBtn;
    private javax.swing.JLabel selectedPaper;
    private javax.swing.JLabel selectedTopic;
    private javax.swing.JSpinner startYear;
    private javax.swing.JComboBox<String> topicSelection;
    // End of variables declaration//GEN-END:variables
}
