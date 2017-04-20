package adrmonitortree;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.Socket;
import java.net.UnknownHostException;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class Main 
{
    public static void main(String[] args)
    {
        MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);      
    }
 
}

class MainFrame extends JFrame
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Thread readerThread, reloadThread;
	private JTextField statusColor,statusTime;
	private JPanel statusBar;
    private JTree m_tree;
    private DefaultTreeModel m_model;
    private DefaultMutableTreeNode rootNode, groupNode, statusNode = null;
    private DefaultTreeCellRenderer renderer = new ColoredTreeCellRenderer();
    private String statusstring;
    private String hash_status = null;
    private String hash_machine = null;
    private String hash_instance = null;
    private String statushash_key = null;
    private String machinehash_key = null;
    private Hashtable<String, String> machinehash = null;
    private Hashtable<String, String> statushash = null;
    private Vector<String> machinevector = null;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    public Socket socket = null;
    public BufferedReader in = null;
    public DataOutputStream dos = null;
	public String bpfhost = "bxb-dev-launch01.intdata.com";
    public int bpfport = 12346;
    public PrintWriter printwriter = null;
 
    public MainFrame()
    {
        setTitle("Omega DEV Monitor");
        
        readerThread = new Thread(new Reader());
        readerThread.start(); 
        if(!SwingUtilities.isEventDispatchThread()){
        reloadThread = new Thread(new Reload());
        SwingUtilities.invokeLater(reloadThread);
        }
   
        statushash = new Hashtable<String,String>();
        machinehash = new Hashtable<String,String>();
        machinevector = new Vector<String>();
        
        statusColor = new JTextField(2);
        statusColor.setBackground(Color.green);
        statusColor.setEditable(false);
        
        statusBar = new JPanel(new BorderLayout());
        statusBar.add(statusColor, BorderLayout.WEST);
        
        rootNode = new DefaultMutableTreeNode("Omega System");
        m_model = new DefaultTreeModel(rootNode);
        m_model.addTreeModelListener(new OmegaTreeModelListener());
        m_tree = new JTree(m_model);
        m_tree.setEditable(false);
        m_tree.setShowsRootHandles(false);
        m_tree.setCellRenderer(renderer);
        renderer.setOpaque(true);
        renderer.setClosedIcon(new ImageIcon("images/smallomega.gif"));
        renderer.setOpenIcon(new ImageIcon("images/smallomega.gif"));
        renderer.setLeafIcon(new ImageIcon("images/smallomega.gif"));  
        renderer.setFont(new Font("Dialog", Font.PLAIN | Font.PLAIN, 12));
        //implement the scroll 
        JScrollPane scroll = new JScrollPane(m_tree);
        scroll.setPreferredSize(new Dimension(300,200));
        //put scroll (JScrollPane) into mainPane
        JPanel mainPane = new JPanel(new BorderLayout());
        mainPane.add(scroll, BorderLayout.CENTER);
        mainPane.add(statusBar, BorderLayout.SOUTH);
        
        //adds machines as nodes, adds machine names into tree
        addParentNodes();
        //end code for adding machine names into tree
        //expands the tree at start. scroll will appear if expanded more than pane
        expandAll(m_tree);     

        setContentPane(mainPane);
        pack();
    }
    
    class OmegaTreeModelListener implements TreeModelListener {
        public void treeNodesChanged(TreeModelEvent e) {
        }
        public void treeNodesInserted(TreeModelEvent e) {
        }
        public void treeNodesRemoved(TreeModelEvent e) {
        }
        public void treeStructureChanged(TreeModelEvent e) {
        }
        public void fireIntervalRemoval(TreeModelEvent e){
        }
        public void fireTreeNodesInserted(TreeModelEvent e){
        }
    }

    public class ColoredTreeCellRenderer extends DefaultTreeCellRenderer{
    	/**
		 * 
		 */
		private static final long serialVersionUID = 1L;				
		public Component getTreeCellRendererComponent (JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
    		Component cell = super.getTreeCellRendererComponent(
                    tree, value, false, false, true, row, false);

    		if (value.toString().endsWith("=D")) {
    			cell.setBackground(Color.red);
    			toolkit.beep();
    		}
    		else if (value.toString().endsWith("=R")) {
    			cell.setBackground(Color.white);
    		}
    		else if (value.toString().endsWith("=W")){
    			cell.setBackground(Color.magenta);
    		}
    		else if (value.toString().endsWith("=L")){
    			cell.setBackground(Color.yellow);
    		}
    		else if (value.toString().endsWith("=X")){
    			toolkit.beep();
    			cell.setBackground(Color.blue);
    		}
    		else if (value.toString().endsWith("=A")){
    			cell.setBackground(Color.green);
    		}
    		else {
    			cell.setBackground(Color.white);
    		}
			return cell;
   		} 
    }
    
    private class Reload implements Runnable

    {
    	@SuppressWarnings("finally")
		public void run(){
    		try {
    			Thread.sleep(3000);
    			m_model.reload(groupNode);
    			m_model.reload(statusNode);
    			m_model.reload(rootNode);
    			m_model.nodeChanged(rootNode);
    			expandAll(m_tree);
    		} catch (InterruptedException e) {
				e.printStackTrace();
			}finally{
    			return;
    		}
    	}
    }

    public static void printDescendants(TreeNode root) {
        System.out.println(root);
        Enumeration<?> children = root.children();
        if (children != null) {
          while (children.hasMoreElements()) {
            printDescendants((TreeNode) children.nextElement());
          }
        }
      }    
    
    private void expandAll (JTree tree)	{
    	int row = 0;
    	while (row < tree.getRowCount()){
    		tree.expandRow(row);
    		row++;
    	}
    }
    
    //code to add nodes(leafs) in sorted order. Not changeable after this.
    private void addNodeInSortedOrder(DefaultMutableTreeNode parent,
    	DefaultMutableTreeNode child){
    		int n = parent.getChildCount();
    		if(n==0){
    			parent.add(child);
    			return;
    		}
    			DefaultMutableTreeNode node=null;
    			for(int i=0;i<n;i++){
    				node = (DefaultMutableTreeNode)parent.getChildAt(i);
    				if(node.toString().compareTo(child.toString())>0){
    					parent.insert(child, i);
    					return;
    				}
    			}
    				parent.add(child);
    				return;
    	}
    
    private void addParentNodes() {
      for (String omegamachine : machinevector){
    	DefaultMutableTreeNode groupNode = new DefaultMutableTreeNode(omegamachine);
    	rootNode.add(groupNode);
    	addNodeInSortedOrder(rootNode,groupNode);
    	m_model.nodeChanged(rootNode);
    	for (Map.Entry<String,String> machine: statushash.entrySet()){
    		DefaultMutableTreeNode statusNode = new DefaultMutableTreeNode(machine);
    		if(statusNode.toString().contains(omegamachine.toString())){
    			groupNode.add(statusNode);
    			printDescendants(groupNode);
    			m_model.nodeChanged(rootNode);
    			expandAll(m_tree);     
    		}
    	}
    }
    }
    
    public void removeCurrentNode() {
        TreePath currentSelection = m_tree.getSelectionPath();
        if (currentSelection != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
                         (currentSelection.getLastPathComponent());
            MutableTreeNode parent = (MutableTreeNode)(currentNode.getParent());
            if (parent != null) {
                m_model.removeNodeFromParent(currentNode);
                return;
            }
        } 

    }
    
    private class Reader implements Runnable
    {
        public void run()
        {       	
			try {
				socket = new Socket(bpfhost,bpfport);
				in = new BufferedReader(new
                InputStreamReader(socket.getInputStream()));
                printwriter = new PrintWriter(socket.getOutputStream(),true);
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            while(true)
            {
            	 try {
					statusstring = in.readLine();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            	if((statusstring != null) && (statusstring.length() != 0)){
            		m_model.reload(statusNode);
                    if(statusstring.charAt(0)=='>')
                    {
                        try{Thread.sleep(3000);}
                        catch(Exception e){}
                        printwriter.write("h\n");
                        printwriter.flush();
                    }    
                    
                    
                    else if(statusstring.charAt(18)=='d'){
                      	String s1 = statusstring.substring(20,statusstring.length());
                        StringTokenizer st = new StringTokenizer(s1, ",", false);
                        hash_machine = st.nextToken().toString();
                        hash_instance = st.nextToken().toString();
                        statushash_key = ((hash_machine+"  "+hash_instance));
                    	statushash.remove(statushash_key);                      	
                    	machinevector.remove(hash_machine);
                    } 
                    else if(statusstring.charAt(18)=='a'){
                    	String s1 = statusstring.substring(20,statusstring.length());
                        StringTokenizer st = new StringTokenizer(s1, ",", false);
                        hash_machine = st.nextToken().toString();
                        hash_instance = st.nextToken().toString();
                    }
                    else if(statusstring.charAt(18)=='s')
                    {                   	
                    	String s1 = statusstring.substring(20,statusstring.length());
                        StringTokenizer st = new StringTokenizer(s1, ",", false);
                        hash_status = st.nextToken().toString();
                        hash_machine = st.nextToken().toString();
                        hash_instance = st.nextToken().toString();
                        if(s1.charAt(0)=='R'||s1.charAt(0)=='D'||s1.charAt(0)=='W'||s1.charAt(0)=='X'||s1.charAt(0)=='A'||s1.charAt(0)=='L'){
                        statushash_key = ((hash_machine+"  "+hash_instance));
                        System.out.println(statushash_key);
                        statushash.put(statushash_key, hash_status);
                        if(!machinevector.contains(hash_machine)){
                        	machinevector.add(hash_machine);
                        }
                        for (Map.Entry<String, String> entry: statushash.entrySet()) {              
                        }                                               
                    }
            	}
                  
            	}

            	else{
            		statusColor.setBackground(Color.red);
            		try {
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
            	}
            }
        }
    }
}
