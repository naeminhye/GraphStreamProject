/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doantest;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;
import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.graphicGraph.GraphicNode;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants.Units;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;

/**
 *
 * @author Hieu Nguyen
 */
public class MouseHandler implements ViewerListener, MouseInputListener{
	
    protected boolean loop = true;
    private Graph graph;
    private ViewerPipe pipe;
    private View view;
    
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
            System.out.println("[MOUSE HANDLER] mousePressed");

    }

    @Override
    public void mouseReleased(MouseEvent e){
        System.out.println("[MOUSE HANDLER] mouseReleased");
        this.pipe.pump();
    }

    @Override
    public void mouseEntered(MouseEvent e){
        System.out.println("[MOUSE HANDLER] mouseEntered");
    }

    @Override
    public void mouseExited(MouseEvent e){
        System.out.println("[MOUSE HANDLER] mouseExited");
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
        System.out.println("[MOUSE HANDLER] buttonReleased");
    }

    @Override
    /**
     * Button push hook to label nodes/edges
     * @param id - string id of node
     */
    public void buttonPushed(String id) {
        System.out.println("[MOUSE HANDLER] buttonPushed");
        System.out.println("Button pushed on node "+id);
        Node n = this.graph.getNode(id);
        String point = Toolkit.nodePointPosition(graph, id).toString();
        System.out.println("[NODE'S POSITION] " + point);

        n.addAttribute("ui.style", " size: 60px;");
        for (Node node : graph.getEachNode()) {
            if(node != n) {
                if(null != node.getAttribute("ui.style")){
                    node.setAttribute("ui.style", n.getAttribute("ui.style") + " size: 20px;");
                }
                else{
                    node.addAttribute("ui.style", " size: 20px;");
                }
            }
        }

        for (Edge edge : graph.getEachEdge()) {
            for (Edge e : n.getEachEdge()){
                e.addAttribute("ui.style", "fill-color: red; shape: line; arrow-size: 3px, 2px;");

                if(edge != e)
                    edge.addAttribute("ui.style", "fill-color: black; shape: line; arrow-size: 3px, 2px;");
            }
        }
//        String _ui_label = n.getAttribute("_ui.label");
//        String ui_label = n.getAttribute("ui.label"); 

//        // if not set toggle on node and adj edges
//        if (ui_label==null || ui_label.equals(""))
//        {
//                n.setAttribute("ui.label", _ui_label);
//
//                // Label adjacent edges
//                labelAdjacentEdges(n);
//
//        }
//        else // Toggle node off and adj unbound edges
//        {
//                n.setAttribute("ui.label", "");
//                unlabelAdjacentEdges(n);
//        }
    }
    
    /**
    * labels adjacent edges of the given node 
    * @param n - node to label adjacent edges of
    */
    private void labelAdjacentEdges(Node n)
    {
        for (Edge e : n.getEdgeSet()){
            String _ui_label = e.getAttribute("_ui.label");
            String ui_label = e.getAttribute("ui.label"); 

            if (ui_label==null || ui_label.equals(""))
            {
                    e.setAttribute("ui.label", _ui_label);

            }
        }
    }

    /**
     * unlabels all edges connected to node which are no longer
     * connected to any other labeled node
     * @param n - the node to apply to adjacent edges
     */
    private void unlabelAdjacentEdges(Node n)
    {
            for (Edge e : n.getEdgeSet()){
                    Node v1 = e.getNode0();
                    Node v2 = e.getNode1();

                    String ui_label_v1 = v1.getAttribute("ui.label");
                    String ui_label_v2 = v2.getAttribute("ui.label");


                    if ((ui_label_v1==null || ui_label_v1.equals(""))
                                    && (ui_label_v2==null || ui_label_v2.equals("")))
                    {
                            e.setAttribute("ui.label", "");
                    }
            }

    }
}
