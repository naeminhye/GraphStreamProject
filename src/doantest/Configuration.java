/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doantest;

import doantest.GlobalVariables.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
        
/**
 *
 * @author Hieu Nguyen
 */
public class Configuration extends javax.swing.JFrame {

    /**
     * Creates new form Configuration
     */
    public Configuration() {
        initComponents();
    }
    
    private void formValidation() {
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        portTypeComboBox = new javax.swing.JComboBox<>();
        portLbl = new javax.swing.JLabel();
        portTxtField = new javax.swing.JTextField();
        userNameTxtBox = new javax.swing.JTextField();
        userNameLbl = new javax.swing.JLabel();
        passWordTxtBox = new javax.swing.JPasswordField();
        passWordLbl = new javax.swing.JLabel();
        saveBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(559, 560));
        setPreferredSize(new java.awt.Dimension(559, 560));
        setResizable(false);

        jPanel1.setLayout(null);

        portTypeComboBox.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        portTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "HTTP", "HTTPS", "Bolt" }));
        portTypeComboBox.setSelectedIndex(2);
        jPanel1.add(portTypeComboBox);
        portTypeComboBox.setBounds(250, 260, 270, 30);

        portLbl.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        portLbl.setText("Port:");
        jPanel1.add(portLbl);
        portLbl.setBounds(250, 310, 52, 30);

        portTxtField.setText("7687");
        portTxtField.setToolTipText("");
        jPanel1.add(portTxtField);
        portTxtField.setBounds(370, 310, 150, 30);

        userNameTxtBox.setText("reader");
        userNameTxtBox.setToolTipText("");
        jPanel1.add(userNameTxtBox);
        userNameTxtBox.setBounds(370, 360, 150, 30);

        userNameLbl.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        userNameLbl.setText("Username:");
        jPanel1.add(userNameLbl);
        userNameLbl.setBounds(250, 360, 97, 30);

        passWordTxtBox.setText("1234");
        jPanel1.add(passWordTxtBox);
        passWordTxtBox.setBounds(370, 410, 150, 30);

        passWordLbl.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        passWordLbl.setText("Password:");
        jPanel1.add(passWordLbl);
        passWordLbl.setBounds(250, 410, 97, 30);

        saveBtn.setBackground(new java.awt.Color(153, 153, 153));
        saveBtn.setText("Save");
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });
        jPanel1.add(saveBtn);
        saveBtn.setBounds(400, 460, 100, 30);

        cancelBtn.setBackground(new java.awt.Color(153, 153, 153));
        cancelBtn.setText("Cancel");
        jPanel1.add(cancelBtn);
        cancelBtn.setBounds(270, 460, 100, 30);

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/background01.png"))); // NOI18N
        jPanel1.add(background);
        background.setBounds(0, 0, 560, 559);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 564, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
        // TODO add your handling code here:
        Connection con = null;
        String connection = "";
        String port = portTxtField.getText();
        String username = userNameTxtBox.getText();
        String password = "1234";
        try {
            //passWordTxtBox.getPassword().toString()
            //  "jdbc:neo4j:bolt://localhost:7687"
            switch(portTypeComboBox.getSelectedIndex()) {
                case 0:
                    connection = "jdbc:neo4j:http://localhost:" + port;
                    break;
                case 1:
                    connection = "jdbc:neo4j:https://localhost:" + port;
                    break;
                case 2:
                    connection = "jdbc:neo4j:bolt://localhost:" + port;
                    break;
                default: 
                    break;
            }
            con = DriverManager.getConnection(connection, username, password);
            if(con == null) {
                System.out.println("null connection: " + connection);
            }
            System.out.println("connection: " + connection);
        }
        catch (SQLException ex) {
            System.out.println(ex);
            Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_saveBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(Configuration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Configuration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Configuration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Configuration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Configuration().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel passWordLbl;
    private javax.swing.JPasswordField passWordTxtBox;
    private javax.swing.JLabel portLbl;
    private javax.swing.JTextField portTxtField;
    private javax.swing.JComboBox<String> portTypeComboBox;
    private javax.swing.JButton saveBtn;
    private javax.swing.JLabel userNameLbl;
    private javax.swing.JTextField userNameTxtBox;
    // End of variables declaration//GEN-END:variables
}
