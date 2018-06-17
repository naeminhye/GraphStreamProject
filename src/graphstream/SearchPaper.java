/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphstream;

import static graphstream.GraphUtils.*;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.json.JSONArray; 
import javax.swing.ImageIcon;

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
    
    
    /**
     * Creates new form SearchPaper
     */
    public SearchPaper() {
        initComponents();
        // Set JFrame vào giữa màn hình
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        
//        Image appIcon = new ImageIcon(Constants.WORKING_DIRECTORY + "/src/main/resources/images/app-icon.png").getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
//        this.setIconImage(appIcon);
        
        // Set giá trị mặc định cho 2 number box startYear và endYear
        endYear.setValue(1981);        
        startYear.setValue(1980); 
        limit.setValue(5);
        count.setValue(5);
        showPnlBtn.setVisible(false);

//        Image iconInfo = (Image)new ImageIcon(Constants.WORKING_DIRECTORY + "/src/main/resources/images/icon-info.png").getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
//        infoBtn.setIcon(new ImageIcon((Image)new ImageIcon(Constants.WORKING_DIRECTORY + "/src/main/resources/images/icon-info.png").getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)));
//        infoBtn.setContentAreaFilled(false);
        
//        Image iconHelp = (Image) new ImageIcon(Constants.WORKING_DIRECTORY + "/src/main/resources/images/icon-help.png").getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
//        shortCutBtn.setIcon(new ImageIcon((Image) new ImageIcon(Constants.WORKING_DIRECTORY + "/src/main/resources/images/icon-help.png").getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)));
//        shortCutBtn.setContentAreaFilled(false);
        
//        Image iconSearchMore = (Image) new ImageIcon(Constants.WORKING_DIRECTORY + "/src/main/resources/images/icon-search.png").getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
//        searchMoreBtn.setIcon(new ImageIcon((Image) new ImageIcon(Constants.WORKING_DIRECTORY + "/src/main/resources/images/icon-search.png").getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)));
//        searchMoreBtn.setContentAreaFilled(false);
//        searchPaperBtn.setIcon(new ImageIcon(iconSearchMore));
//        searchPaperBtn.setContentAreaFilled(false);
        
//        Image iconSetting = (Image) new ImageIcon(Constants.WORKING_DIRECTORY + "/src/main/resources/images/icon-setting.png").getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
//        settingBtn.setIcon(new ImageIcon((Image) new ImageIcon(Constants.WORKING_DIRECTORY + "/src/main/resources/images/icon-setting.png").getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)));
//        settingBtn.setContentAreaFilled(false);
        
//        Image showPanel = (Image) new ImageIcon(Constants.WORKING_DIRECTORY + "/src/main/resources/images/show-panel.png").getImage().getScaledInstance(23, 23, java.awt.Image.SCALE_SMOOTH);
//        Image hidePanel = (Image) new ImageIcon(Constants.WORKING_DIRECTORY + "/src/main/resources/images/hide-panel.png").getImage().getScaledInstance(23, 23, java.awt.Image.SCALE_SMOOTH);
//        showPnlBtn.setIcon(new ImageIcon((Image) new ImageIcon(Constants.WORKING_DIRECTORY + "/src/main/resources/images/show-panel.png").getImage().getScaledInstance(23, 23, java.awt.Image.SCALE_SMOOTH)));
//        showPnlBtn.setContentAreaFilled(false);
//        hidePnlBtn.setIcon(new ImageIcon((Image) new ImageIcon(Constants.WORKING_DIRECTORY + "/src/main/resources/images/hide-panel.png").getImage().getScaledInstance(23, 23, java.awt.Image.SCALE_SMOOTH)));
//        hidePnlBtn.setContentAreaFilled(false);
        
        glassPane = new InfiniteProgressPanel("Loading ...", 8);
        this.setGlassPane(glassPane);
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

        showPnlBtn = new javax.swing.JButton();
        controlPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        controlPanel1 = new javax.swing.JPanel();
        lblFrom = new javax.swing.JLabel();
        startYear = new javax.swing.JSpinner();
        lblTo = new javax.swing.JLabel();
        endYear = new javax.swing.JSpinner();
        lblTopic = new javax.swing.JLabel();
        lblDisplay = new javax.swing.JLabel();
        topicSelection = new javax.swing.JComboBox<>();
        displaySeletion = new javax.swing.JComboBox<>();
        lblLimit = new javax.swing.JLabel();
        limit = new javax.swing.JSpinner();
        autoLayoutChkBox = new javax.swing.JCheckBox();
        searchPaperBtn = new javax.swing.JButton();
        controlPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        selectedPaper = new javax.swing.JLabel();
        selectedTopic = new javax.swing.JLabel();
        searchMoreBtn = new javax.swing.JButton();
        count = new javax.swing.JSpinner();
        lblLimit1 = new javax.swing.JLabel();
        hidePnlBtn = new javax.swing.JButton();
        shortCutBtn = new javax.swing.JButton();
        settingBtn = new javax.swing.JButton();
        infoBtn = new javax.swing.JButton();
        displayPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Search Paper");
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        setIconImage(new ImageIcon(Constants.WORKING_DIRECTORY + "/src/main/resources/images/app-icon.png").getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        showPnlBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/resources/images/show-panel.png"))); // NOI18N
        showPnlBtn.setToolTipText("Show Control Panel");
        showPnlBtn.setContentAreaFilled(false);
        showPnlBtn.setMaximumSize(new java.awt.Dimension(28, 23));
        showPnlBtn.setMinimumSize(new java.awt.Dimension(28, 23));
        showPnlBtn.setPreferredSize(new java.awt.Dimension(28, 23));
        showPnlBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showPnlBtnActionPerformed(evt);
            }
        });

        controlPanel.setBackground(new java.awt.Color(255, 255, 255));
        controlPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        controlPanel.setToolTipText("Control Panel");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setToolTipText("GraphStream");
        jPanel1.setLayout(null);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/resources/images/icon-gs.png"))); // NOI18N
        jLabel1.setMaximumSize(new java.awt.Dimension(80, 80));
        jLabel1.setMinimumSize(new java.awt.Dimension(80, 80));
        jLabel1.setPreferredSize(new java.awt.Dimension(80, 80));
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 20, 120, 60);

        controlPanel1.setBackground(new java.awt.Color(204, 204, 204));
        controlPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        controlPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        lblFrom.setText("From");

        startYear.setMinimumSize(new java.awt.Dimension(30, 20));

        lblTo.setText("To");

        endYear.setMinimumSize(new java.awt.Dimension(30, 20));

        lblTopic.setText("Topic");

        lblDisplay.setText("Display");

        topicSelection.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99" }));

        displaySeletion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Graph", "Timeline" }));

        lblLimit.setText("Limit");

        limit.setMinimumSize(new java.awt.Dimension(30, 20));

        autoLayoutChkBox.setBackground(new java.awt.Color(204, 204, 204));
        autoLayoutChkBox.setText("Enable Auto Layout");

        searchPaperBtn.setBackground(new java.awt.Color(255, 255, 255));
        searchPaperBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/resources/images/icon-search.png"))); // NOI18N
        searchPaperBtn.setToolTipText("Search");
        searchPaperBtn.setBorder(null);
        searchPaperBtn.setContentAreaFilled(false);
        searchPaperBtn.setIconTextGap(0);
        searchPaperBtn.setName("searchMoreBtn"); // NOI18N
        searchPaperBtn.setPreferredSize(new java.awt.Dimension(30, 30));
        searchPaperBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchPaperBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout controlPanel1Layout = new javax.swing.GroupLayout(controlPanel1);
        controlPanel1.setLayout(controlPanel1Layout);
        controlPanel1Layout.setHorizontalGroup(
            controlPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(controlPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(controlPanel1Layout.createSequentialGroup()
                        .addComponent(lblTo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(endYear, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(controlPanel1Layout.createSequentialGroup()
                        .addComponent(lblFrom)
                        .addGap(18, 18, 18)
                        .addComponent(startYear, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(controlPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTopic, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDisplay))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(controlPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(controlPanel1Layout.createSequentialGroup()
                        .addComponent(displaySeletion, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(autoLayoutChkBox)
                        .addGap(18, 18, 18)
                        .addComponent(searchPaperBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(controlPanel1Layout.createSequentialGroup()
                        .addComponent(topicSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)
                        .addComponent(lblLimit)
                        .addGap(18, 18, 18)
                        .addComponent(limit, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        controlPanel1Layout.setVerticalGroup(
            controlPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(controlPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(limit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(topicSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTopic)
                    .addComponent(startYear, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFrom)
                    .addComponent(lblLimit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(controlPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(endYear, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTo)
                    .addComponent(lblDisplay)
                    .addComponent(displaySeletion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(autoLayoutChkBox))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(controlPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(searchPaperBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        controlPanel2.setBackground(new java.awt.Color(204, 204, 204));
        controlPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel6.setText("Selected Paper: ");
        jLabel6.setName(""); // NOI18N

        jLabel7.setText("Selected Topic: ");
        jLabel7.setName(""); // NOI18N

        selectedPaper.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        selectedPaper.setName("selectedPaper"); // NOI18N

        selectedTopic.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        selectedTopic.setName("selectedTopic"); // NOI18N

        searchMoreBtn.setBackground(new java.awt.Color(255, 255, 255));
        searchMoreBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/resources/images/icon-search.png"))); // NOI18N
        searchMoreBtn.setToolTipText("Search");
        searchMoreBtn.setBorder(null);
        searchMoreBtn.setContentAreaFilled(false);
        searchMoreBtn.setIconTextGap(0);
        searchMoreBtn.setName("searchMoreBtn"); // NOI18N
        searchMoreBtn.setPreferredSize(new java.awt.Dimension(30, 30));
        searchMoreBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchMoreBtnActionPerformed(evt);
            }
        });

        count.setMinimumSize(new java.awt.Dimension(30, 20));

        lblLimit1.setText("Count");

        javax.swing.GroupLayout controlPanel2Layout = new javax.swing.GroupLayout(controlPanel2);
        controlPanel2.setLayout(controlPanel2Layout);
        controlPanel2Layout.setHorizontalGroup(
            controlPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(controlPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(controlPanel2Layout.createSequentialGroup()
                        .addGroup(controlPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6))
                        .addGroup(controlPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(controlPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(selectedPaper, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(controlPanel2Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(selectedTopic, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(searchMoreBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(controlPanel2Layout.createSequentialGroup()
                        .addComponent(lblLimit1)
                        .addGap(57, 57, 57)
                        .addComponent(count, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        controlPanel2Layout.setVerticalGroup(
            controlPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, controlPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(controlPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(selectedPaper, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(controlPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(controlPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(controlPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(selectedTopic, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(controlPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(count, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblLimit1)))
                    .addGroup(controlPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(searchMoreBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        hidePnlBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/resources/images/hide-panel.png"))); // NOI18N
        hidePnlBtn.setToolTipText("Hide Control Panel");
        hidePnlBtn.setContentAreaFilled(false);
        hidePnlBtn.setMaximumSize(new java.awt.Dimension(28, 23));
        hidePnlBtn.setMinimumSize(new java.awt.Dimension(28, 23));
        hidePnlBtn.setPreferredSize(new java.awt.Dimension(28, 23));
        hidePnlBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hidePnlBtnActionPerformed(evt);
            }
        });

        shortCutBtn.setBackground(new java.awt.Color(255, 255, 255));
        shortCutBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/resources/images/icon-help.png"))); // NOI18N
        shortCutBtn.setToolTipText("Help");
        shortCutBtn.setBorder(null);
        shortCutBtn.setContentAreaFilled(false);
        shortCutBtn.setIconTextGap(0);
        shortCutBtn.setName("infoBtn"); // NOI18N
        shortCutBtn.setPreferredSize(new java.awt.Dimension(30, 30));
        shortCutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shortCutBtnActionPerformed(evt);
            }
        });

        settingBtn.setBackground(new java.awt.Color(255, 255, 255));
        settingBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/resources/images/icon-setting.png"))); // NOI18N
        settingBtn.setToolTipText("Configuration");
        settingBtn.setBorder(null);
        settingBtn.setContentAreaFilled(false);
        settingBtn.setIconTextGap(0);
        settingBtn.setName("infoBtn"); // NOI18N
        settingBtn.setPreferredSize(new java.awt.Dimension(30, 30));
        settingBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingBtnActionPerformed(evt);
            }
        });

        infoBtn.setBackground(new java.awt.Color(255, 255, 255));
        infoBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/resources/images/icon-info.png"))); // NOI18N
        infoBtn.setToolTipText("About");
        infoBtn.setBorder(null);
        infoBtn.setContentAreaFilled(false);
        infoBtn.setIconTextGap(0);
        infoBtn.setName("infoBtn"); // NOI18N
        infoBtn.setPreferredSize(new java.awt.Dimension(30, 30));
        infoBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                infoBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout controlPanelLayout = new javax.swing.GroupLayout(controlPanel);
        controlPanel.setLayout(controlPanelLayout);
        controlPanelLayout.setHorizontalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, controlPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(controlPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(controlPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(controlPanelLayout.createSequentialGroup()
                        .addComponent(settingBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(shortCutBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(infoBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(hidePnlBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        controlPanelLayout.setVerticalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(controlPanelLayout.createSequentialGroup()
                        .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(infoBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(shortCutBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(settingBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(hidePnlBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(controlPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(controlPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        controlPanel1.getAccessibleContext().setAccessibleName("Search Filter");
        controlPanel1.getAccessibleContext().setAccessibleDescription("");

        displayPanel.setBackground(new java.awt.Color(255, 255, 255));
        displayPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout displayPanelLayout = new javax.swing.GroupLayout(displayPanel);
        displayPanel.setLayout(displayPanelLayout);
        displayPanelLayout.setHorizontalGroup(
            displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        displayPanelLayout.setVerticalGroup(
            displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 609, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(controlPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(displayPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(showPnlBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(controlPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(showPnlBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(displayPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchMoreBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchMoreBtnActionPerformed
        if(!selectedPaper.getText().equals("") && !selectedTopic.getText().equals("")) {
            int output = JOptionPane.showConfirmDialog(
                thisFrame,
                "You have chosen Node " + selectedPaper.getText() + " and Node " + selectedTopic.getText(),
                "Do you want to find the related road?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE);

            if(output == JOptionPane.YES_OPTION){
                glassPane.start();
                new Thread(new Runnable() {
                    public void run() {
                        GraphUtils.setPaperFlow(selectedPaper.getText(), selectedTopic.getText(), (int)count.getValue(), 1, graph, graphInfo, displayPanel, glassPane, false);
//                        showOnPanel(graph, graphInfo, displayPanel, glassPane, false);

                        glassPane.stop();
                    }
                }, "Performer").start();
            }
            else if(output == JOptionPane.NO_OPTION){
            }
        }
    }//GEN-LAST:event_searchMoreBtnActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        String ObjButtons[] = {"Yes","No"};
        int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to exit?","Search Paper",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
        if(PromptResult==JOptionPane.YES_OPTION)
        {
            System.exit(0);
        }
        
    }//GEN-LAST:event_formWindowClosing

    private void showPnlBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showPnlBtnActionPerformed
        if(!controlPanel.isVisible()) {
            controlPanel.setVisible(true);
            hidePnlBtn.setVisible(true);
            showPnlBtn.setVisible(false); 
        }
    }//GEN-LAST:event_showPnlBtnActionPerformed

    private void hidePnlBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hidePnlBtnActionPerformed
        controlPanel.setVisible(false);
        hidePnlBtn.setVisible(false);
        showPnlBtn.setVisible(true); 
    }//GEN-LAST:event_hidePnlBtnActionPerformed

    private void settingBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingBtnActionPerformed
        Configuration config = new Configuration();
        config.setVisible(true);
    }//GEN-LAST:event_settingBtnActionPerformed

    private void infoBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_infoBtnActionPerformed
        About about = new About();
        about.setVisible(true);
    }//GEN-LAST:event_infoBtnActionPerformed

    private void searchPaperBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchPaperBtnActionPerformed
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
    }//GEN-LAST:event_searchPaperBtnActionPerformed

    private void shortCutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shortCutBtnActionPerformed
        Shortcuts shortcuts = new Shortcuts();
        shortcuts.setVisible(true);
    }//GEN-LAST:event_shortCutBtnActionPerformed
   
    private void display() {
        switch(displaySeletion.getSelectedItem().toString()) {
            case "Graph":
                GraphUtils.setGraph((int)startYear.getValue(), (int)endYear.getValue(), (String)topicSelection.getSelectedItem(), graph, graphInfo, (int)limit.getValue(), displayPanel, glassPane, autoLayoutChkBox.isSelected());
                break;
            case "Timeline":
                GraphUtils.setTimeline((int)startYear.getValue(), (int)endYear.getValue(), (String)topicSelection.getSelectedItem(), graph, graphInfo, (int)limit.getValue(), displayPanel, glassPane, autoLayoutChkBox.isSelected());
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
    private javax.swing.JCheckBox autoLayoutChkBox;
    private javax.swing.JPanel controlPanel;
    private javax.swing.JPanel controlPanel1;
    private javax.swing.JPanel controlPanel2;
    private javax.swing.JSpinner count;
    private javax.swing.JPanel displayPanel;
    private javax.swing.JComboBox<String> displaySeletion;
    private javax.swing.JSpinner endYear;
    private javax.swing.JButton hidePnlBtn;
    private javax.swing.JButton infoBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblDisplay;
    private javax.swing.JLabel lblFrom;
    private javax.swing.JLabel lblLimit;
    private javax.swing.JLabel lblLimit1;
    private javax.swing.JLabel lblTo;
    private javax.swing.JLabel lblTopic;
    private javax.swing.JSpinner limit;
    private javax.swing.JButton searchMoreBtn;
    private javax.swing.JButton searchPaperBtn;
    private javax.swing.JLabel selectedPaper;
    private javax.swing.JLabel selectedTopic;
    private javax.swing.JButton settingBtn;
    private javax.swing.JButton shortCutBtn;
    private javax.swing.JButton showPnlBtn;
    private javax.swing.JSpinner startYear;
    private javax.swing.JComboBox<String> topicSelection;
    // End of variables declaration//GEN-END:variables
}
