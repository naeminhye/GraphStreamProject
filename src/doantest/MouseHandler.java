/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doantest;

import static doantest.GraphUtils.*;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_MINUS;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.graphicGraph.stylesheet.Selector;
import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants;
import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants.Units;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.swingViewer.DefaultView;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;
import org.graphstream.ui.view.util.DefaultMouseManager;
import org.graphstream.ui.view.util.DefaultShortcutManager;

/**
 *
 *  Xử lí các xự kiện click chuột
 */
public class MouseHandler implements ViewerListener {
	
    protected boolean loop = true;
    private Graph graph;
    private JPanel panel;
    private ViewerPipe pipe;
    private View view;
    private String selectedId = ""; // ID của node đang được click
    private String oldSelectedId = "";
    private String selectedPaper = "";
    private String selectedTopic = "";
    private Node thisNode = null; // Node đang được trỏ đến
    private boolean isClicked = false;
    private StorageObject graphInfo;
    private InfiniteProgressPanel glassPane;
    private String displayType;
    private SpriteManager sman;// = new SpriteManager(graph);
    
    private DefaultMouseManager mouseManager = new DefaultMouseManager() {
        
        public void elementMoving(GraphicElement element, MouseEvent event) {
            view.moveElementAtPx(element, event.getX(), event.getY());
        }
        
        public void mouseMoved(MouseEvent event) {
            /** Lấy Node hoặc Sprite tại vị trí chuột trỏ đến */
            curElement = view.findNodeOrSpriteAt(event.getX(), event.getY());
            if(curElement != null) {
                if(curElement.getSelectorType() == Selector.Type.NODE) {

                    thisNode = (Node) curElement;
                    String nodeId = curElement.getId();
                    graph.getNode(nodeId).setAttribute("ui.label", "Node ID: " + nodeId);

                    thisNode.setAttribute("ui.clicked");
                    thisNode.setAttribute("layout.frozen");
                    showMoreNodeInfo(graph, nodeId, graphInfo, view);

                    for (Node otherNode : graph.getEachNode()) {
                        if(otherNode != thisNode && otherNode != this.graph.getNode(selectedId)) {
                            if(otherNode.hasAttribute("ui.clicked")) {
                                otherNode.removeAttribute("layout.frozen");
                                otherNode.removeAttribute("ui.clicked");
                            }
                            showLessNodeInfo(graph, otherNode.getId(), graphInfo);
                        }
                    }
                }
            }
            else {
                thisNode = null;
                for (Node node : graph.getEachNode()) {
                    if(!node.getId().equals(selectedId)){
                        showLessNodeInfo(graph, node.getId(), graphInfo);
                        if(node.hasAttribute("ui.clicked")) {
                            node.removeAttribute("layout.frozen");
                            node.removeAttribute("ui.clicked");
                        }
                    }
                }
            }
        }

        public void mouseButtonPressOnElement(GraphicElement element, MouseEvent e) {
            view.freezeElement(element, true);
            curElement = view.findNodeOrSpriteAt(e.getX(), e.getY());
            if(curElement != null) {
                if(curElement.getSelectorType() == Selector.Type.NODE) {
                    thisNode = (Node) curElement;
                    oldSelectedId = selectedId;
                    selectedId = thisNode.getId();
                    /** Nhấp đôi vào một node bất kì để hiện thêm node */
                    if (e.getClickCount() >= 2 && !e.isConsumed()) {
                        e.consume();
                        doubleClickOnNode();
                    }
                    /** Click vào node ---> toggle hiển thị thêm/bớt node */
                    else {
                        singleClickOnNode();
                    }
                }
                /** Nếu click vào Sprite ... */
                else if(curElement.getSelectorType() == Selector.Type.SPRITE) {
                    singleClickOnSprite();
                }
            }
        }
    };
    
    private DefaultShortcutManager shortcutManager = new DefaultShortcutManager() {
      	public void keyPressed(KeyEvent event) {
            /** CTRL + ... */
            if(event.isControlDown()) {
                switch(event.getKeyCode()) {
                    /** CTRL + "+" */
                    case KeyEvent.VK_EQUALS:
                    case KeyEvent.VK_ADD:
                        view.getCamera().setViewPercent(view.getCamera().getViewPercent() - 0.1);
                        break;
                    /** CTRL + "-" */
                    case KeyEvent.VK_MINUS:
                    case KeyEvent.VK_SUBTRACT:
                        view.getCamera().setViewPercent(view.getCamera().getViewPercent() + 0.1);
                        break;
                }
            }
            else {
                switch(event.getKeyCode()) {       
                    /** CTRL + <-- */
                    case KeyEvent.VK_KP_LEFT:
                    case KeyEvent.VK_LEFT:
                        view.getCamera().setViewCenter(view.getCamera().getViewCenter().x - 1, view.getCamera().getViewCenter().y, 0);
                        break;
                    /** CTRL + --> */
                    case KeyEvent.VK_KP_RIGHT:
                    case KeyEvent.VK_RIGHT:
                        view.getCamera().setViewCenter(view.getCamera().getViewCenter().x + 1, view.getCamera().getViewCenter().y, 0);
                        break;

                    /** CTRL + <-- */
                    case KeyEvent.VK_KP_UP:
                    case KeyEvent.VK_UP:
                        view.getCamera().setViewCenter(view.getCamera().getViewCenter().x, view.getCamera().getViewCenter().y + 1, 0);
                        break;
                    /** CTRL + --> */
                    case KeyEvent.VK_KP_DOWN:
                    case KeyEvent.VK_DOWN:
                        view.getCamera().setViewCenter(view.getCamera().getViewCenter().x, view.getCamera().getViewCenter().y - 1, 0);
                        break;
                }
            }
      	}

      	public void keyReleased(KeyEvent event) {
      	}

      	public void keyTyped(KeyEvent event) {
      	}
    };
    
    public MouseHandler(Graph graph, View view, ViewerPipe pipe, StorageObject array, JPanel pnl, InfiniteProgressPanel glassPane, String displayType) {
        this.loop = true;
        this.graph = graph;
        this.panel = pnl;
        this.view = view;
        this.pipe = pipe;
        this.graphInfo = array;
        this.glassPane = glassPane;
        this.displayType = displayType;
        this.view.setMouseManager(mouseManager);
        this.view.setShortcutManager(shortcutManager);
        this.sman = new SpriteManager(graph);
    }
    
    @Override
    public void viewClosed(String id) {
        System.exit(0);
    }

    @Override
    /* (non-Javadoc)
     * @see org.graphstream.ui.view.ViewerListener#buttonReleased(java.lang.String)
     */
    public void buttonReleased(String id) {}

    @Override
    /**
     * Button push hook to label nodes/edges
     * @param id - string id of node
     */
    public void buttonPushed(String id) {}
    
    /**
     * Xử lý sự kiện khi click đôi vào node
     */
    private void doubleClickOnNode() {
        if(thisNode != null) {
            System.out.println("Double click on node " + selectedId);

            glassPane.start();
            new Thread(new Runnable() {
                public void run() {
//                        graph.getNode(selectedId).setAttribute("ui.frozen");
                        GraphUtils.getMoreNodes(graph, selectedId, graphInfo, 25);
                        switch(displayType) {
                            case "Graph":
                                GraphUtils.showGraphOnPanel(graph, graphInfo, panel, glassPane);
                                break;
                            case "Timeline":
                                GraphUtils.showTimeLineOnPanel(graph, graphInfo, panel, glassPane);
                                break;
                            case "Flow":
                                GraphUtils.showPaperFlowOnPanel(graph, graphInfo, panel, glassPane);
                                break;
                            default:
                                break;
                        }

                        glassPane.stop();
                    }
            }, "Performer").start();
        }
    }
    
    /**
     * Xử lý sự kiện khi click đơn vào node
     */
    private void singleClickOnNode() {
        
        /** Nếu node hiện tại không được click trước đó */
        if(!oldSelectedId.equals(selectedId) || oldSelectedId.equals("")) {
            isClicked = true;
            showHiddenNodes(graph, selectedId, graphInfo);
            thisNode.setAttribute("ui.clicked");
            
            if(!sman.hasSprite(selectedId)) {
                Sprite s = sman.addSprite(selectedId);
                s.attachToNode(selectedId);
                s.setPosition(StyleConstants.Units.PX, 30, 100, 0);
                System.out.println("New Sprite: " + selectedId);
            }
            
            for (Node otherNode : graph.getEachNode()) {
                if(!otherNode.getId().equals(thisNode.getId()) && !otherNode.getId().equals(selectedPaper) && !otherNode.getId().equals(selectedTopic)) {
                    if(sman.hasSprite(otherNode.getId())) {
                        sman.getSprite(otherNode.getId()).detach();
                        sman.removeSprite(otherNode.getId());
                        System.out.println("Remove Sprite: " + otherNode.getId());
                    }
                    showLessNodeInfo(graph, otherNode.getId(), graphInfo);
                    if(otherNode.hasAttribute("ui.clicked")) {
                        otherNode.removeAttribute("ui.clicked");
                        if(sman.hasSprite(otherNode.getId())) {
                            sman.removeSprite(otherNode.getId());
                        }
                    }
                }
            }
            for (Edge edge : graph.getEachEdge()) {
                for (Edge edgeOfThisNode : thisNode.getEachEdge()){
                    edgeOfThisNode.setAttribute("ui.style", "size: 3px; fill-color: #ffff66; text-mode: normal; text-padding: 3px, 2px; text-background-mode: rounded-box; text-background-color: #e6e6e6e6;");

                    if(!edge.getId().equals(edgeOfThisNode.getId())) {
                        edge.setAttribute("ui.style", "shadow-mode: none; size: 1px; fill-color: #000000; text-mode: hidden; ");
                    }
                }
            }
        }
        else 
        {
            isClicked = !isClicked;
            if(isClicked) {
                thisNode.setAttribute("ui.clicked");
                showHiddenNodes(graph, selectedId, graphInfo);
            }
            else {
                if(thisNode.hasAttribute("ui.clicked")) {
                    thisNode.removeAttribute("ui.clicked");
                }
                hideHiddenNodes(graph, selectedId, graphInfo);
            }
        }
    }
    
    private void singleClickOnSprite() {
        Sprite spr = sman.getSprite(selectedId);
//                    Node node = (Node) spr.getAttachment();
        TypeOfNode thisType = GraphUtils.typeOfNode(graph, graphInfo, selectedId);
        switch(thisType) {
            case PAPER: 
                if(!selectedId.equals(selectedPaper)) {
                    selectedPaper = selectedId;
                    System.out.println("This sprite is attached to PAPER " + spr.getAttachment().getId());
                    sman.getSprite(selectedId).setAttribute("ui.style", "fill-image: url('src/images/icon-ok.png');");
                }
                else {
                    selectedPaper = "";
                    sman.getSprite(selectedId).setAttribute("ui.style", "fill-image: url('src/images/icon-plus.png');");
                }
                break;
            case TOPIC: 
                if(!selectedId.equals(selectedTopic)) {
                    selectedTopic = selectedId;
                    System.out.println("This sprite is attached to TOPIC " + spr.getAttachment().getId());
                    sman.getSprite(selectedId).setAttribute("ui.style", "fill-image: url('src/images/icon-ok.png');");
                }
                else {
                    selectedTopic = "";
                    sman.getSprite(selectedId).setAttribute("ui.style", "fill-image: url('src/images/icon-plus.png');");
                }
                break;
            default: 
                break;
        }
    }
}
