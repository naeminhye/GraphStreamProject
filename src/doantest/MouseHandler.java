/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doantest;

import static doantest.GraphUtils.showLessNodeInfo;
import static doantest.GraphUtils.showMoreNodeInfo;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;

/**
 *
 *  Xử lí các xự kiện click chuột
 */
public class MouseHandler implements ViewerListener, MouseInputListener{
	
    protected boolean loop = true;
    private Graph graph;
    private JPanel panel;
    private ViewerPipe pipe;
    private View view;
    private String selectedId = "";
    private Node thisNode = null;
    private StorageObject graphInfo;
    private InfiniteProgressPanel glassPane;
    
    public enum ToggleType {
        ON,
        OFF
    }
    
    public MouseHandler(Graph graph, View view, ViewerPipe pipe, StorageObject array, JPanel pnl, InfiniteProgressPanel glassPane) {
        this.loop = true;
        this.graph = graph;
        this.panel = pnl;
        this.view = view;
        this.pipe = pipe;
        // Add mouse listener.
        this.view.addMouseListener(this);
        this.graphInfo = array;
        this.glassPane = glassPane;
    }

    @Override
    public void mouseDragged(MouseEvent e){
    }

    @Override
    public void mouseMoved(MouseEvent e){
    }

    @Override
    public void mouseClicked(MouseEvent e){
        if(this.selectedId.equals("") == false) {
            if (e.getClickCount() == 2 && !e.isConsumed()) {
                e.consume();
                /** Nhấp đôi vào một node bất kì để hiện thêm node */
                if(this.thisNode != null) {
                    System.out.println("Double click on node " + selectedId);
                    
                    glassPane.start();
                    new Thread(new Runnable() {
                        public void run() {
                                GraphUtils.getMoreNodes(graph, selectedId, graphInfo, 25);
                                GraphUtils.showGraphOnPanel(graph, graphInfo, panel, glassPane);
//                                GraphUtils.showTimeLineOnPanel(graph, graphInfo, panel, glassPane);

                                glassPane.stop();
                            }
                    }, "Performer").start();
                }
           }
            else {
                if(this.graph.getNode(this.selectedId) != this.thisNode || (this.selectedId.equals("") &&  this.thisNode == null)) {
                    this.thisNode = this.graph.getNode(this.selectedId);
                    showMoreNodeInfo(graph, this.selectedId, graphInfo);
                    toggleNode(this.thisNode, ToggleType.ON);
                    for (Node otherNode : graph.getEachNode()) {
                        if(otherNode != thisNode) {
                            showLessNodeInfo(graph, otherNode.getId());
                            toggleNode(otherNode, ToggleType.OFF);
                        }
                    }
                    for (Edge edge : graph.getEachEdge()) {
                        for (Edge _e : thisNode.getEachEdge()){
                            _e.addAttribute("ui.style", "size: 3px; fill-color: #ffff66; text-mode: normal; text-padding: 3px, 2px; text-background-mode: rounded-box; text-background-color: #e6e6e6e6;"); /*shadow-mode: plain; shadow-width: 3px; shadow-color: #ffff66; shadow-offset: 0px;*/
    
                            if(edge != _e)
                            edge.addAttribute("ui.style", "shadow-mode: none; size: 1px; fill-color: #000000; text-mode: hidden; ");
                        }
                    }
                }
                else {
                    showLessNodeInfo(graph, this.selectedId);
                    toggleNode(this.thisNode, ToggleType.OFF);
                    this.thisNode = null;
                }
                this.selectedId = "";
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e){
//        System.out.println("[MOUSE HANDLER] mousePressed");

    }

    @Override
    public void mouseReleased(MouseEvent e){
//        System.out.println("[MOUSE HANDLER] mouseReleased");
        this.pipe.pump();
    }

    @Override
    public void mouseEntered(MouseEvent e){
//        System.out.println("[MOUSE HANDLER] mouseEntered");
    }

    @Override
    public void mouseExited(MouseEvent e){
//        System.out.println("[MOUSE HANDLER] mouseExited");
    }
    
    @Override
    public void viewClosed(String id) {
        this.loop = false; 
        this.view.removeMouseListener(this);

        System.exit(0);
    }

    @Override
    /* (non-Javadoc)
     * @see org.graphstream.ui.view.ViewerListener#buttonReleased(java.lang.String)
     */
    public void buttonReleased(String id) {
//        System.out.println("[MOUSE HANDLER] buttonReleased");
    }

    @Override
    /**
     * Button push hook to label nodes/edges
     * @param id - string id of node
     */
    public void buttonPushed(String id) {
        
        this.selectedId = id;
    }
    
    private static void toggleNode(Node node, ToggleType toggle) {
        switch(toggle) {
            case ON:
                node.addAttribute("ui.class", "clicked");
                break;
            case OFF:
                node.removeAttribute("ui.class");
                break;
            default:
                break;
        }
    }
}
