/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doantest;

import static doantest.Utils.showMoreNodeInfo;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;
import org.graphstream.algorithm.Toolkit;
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
    private ViewerPipe pipe;
    private View view;
    private Node thisNode = null;
    
    public enum ToggleType {
        ON,
        OFF
    }
    
    public MouseHandler(Graph graph, View view, ViewerPipe pipe) {
        this.loop = true;
        this.graph = graph;
        this.view = view;
        this.pipe = pipe;
        // Add mouse listener.
        this.view.addMouseListener(this);
    }

    @Override
    public void mouseDragged(MouseEvent e){
    }

    @Override
    public void mouseMoved(MouseEvent e){
    }

    @Override
    public void mouseClicked(MouseEvent e){
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
        if(this.graph.getNode(id) != this.thisNode || this.thisNode == null) {
            this.thisNode = this.graph.getNode(id);
            showMoreNodeInfo(graph, id);
            toggleNode(this.thisNode, ToggleType.ON);
            for (Node otherNode : graph.getEachNode()) {
                if(otherNode != thisNode) {
                    if(null != otherNode.getAttribute("ui.style")){
                        otherNode.setAttribute("ui.style", thisNode.getAttribute("ui.style") + " size: 20px;");
                        otherNode.removeAttribute("ui.class");
                    }
                    else{
                        otherNode.addAttribute("ui.style", " size: 20px;");
                        otherNode.removeAttribute("ui.class");
                    }
                }
            }
        }
        else {
            toggleNode(this.thisNode, ToggleType.OFF);
            this.thisNode = null;
        }
//        for (Edge edge : graph.getEachEdge()) {
//            for (Edge e : thisNode.getEachEdge()){
//                e.addAttribute("ui.style", "fill-color: red; shape: line; arrow-size: 3px, 2px;");
//
//                if(edge != e)
//                    edge.addAttribute("ui.style", "fill-color: black; shape: line; arrow-size: 3px, 2px;");
//            }
//        }
    }
    
    private static void toggleNode(Node node, ToggleType toggle) {
        switch(toggle) {
            case ON:
                node.addAttribute("ui.style", " size: 40px;");
                node.addAttribute("ui.class", "clicked");
                break;
            case OFF:
                node.addAttribute("ui.style", " size: 20px;");
                node.removeAttribute("ui.class");
                break;
            default:
                break;
        }
    }
}
