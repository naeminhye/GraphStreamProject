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
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.graphicGraph.GraphicNode;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants.Units;

/**
 *
 * @author Hieu Nguyen
 */
public class MouseHandler implements MouseInputListener{
    
    private Graph thisGraph;
    private Viewer thisViewer;
    
    public MouseHandler(Graph graph, Viewer viewer) {
        thisGraph = graph;
        thisViewer = viewer;
    }

    @Override
    public void mouseDragged(MouseEvent e){
    }

    @Override
    public void mouseMoved(MouseEvent e){
    }

    @Override
    public void mouseClicked(MouseEvent e){
        Point p = MouseInfo.getPointerInfo().getLocation();
        for( Node o : thisGraph.getEachNode() )
        {            
//            GraphicNode n = thisViewer.getGraphicGraph().getNode(o.getId());
//            double nodeX = n.getX();              // Graph Unit
//            double nodeY = n.getY();              // Graph Unit
//            int x = p.x;                          // Pixel
//            int y = p.y;                          // Pixel
//            System.out.println("Node's Location: " + nodeX + ", " + nodeY);
//            System.out.println("Mouse's Location: " + x + ", " + y);
//            if(Math.abs(x - nodeX) < 3 && Math.abs(y - nodeY) < 3) {
//                System.out.println("Node's Location: " + nodeX + ", " + nodeY);
//            }
        }
//        Point p = MouseInfo.getPointerInfo().getLocation();
//        int x = p.x;
//        int y = p.y;
//        System.out.println("Mouse's Location: " + x + ", " + y);
    }

    @Override
    public void mousePressed(MouseEvent e){

    }

    @Override
    public void mouseReleased(MouseEvent e){
    }

    @Override
    public void mouseEntered(MouseEvent e){
    }

    @Override
    public void mouseExited(MouseEvent e){

    }
}
