import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.Socket;
import java.net.UnknownHostException;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.ButtonGroup;
import javax.swing.JMenuBar;
import javax.swing.KeyStroke;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class Main 
{	
    public static String s_host;
    public static String s_port;
    
    public static void main(String[] args)
    {  	
    	if(args.length == 2 && Integer.parseInt(args[1]) <= 65535){
    		
            MainFrame frame = new MainFrame();
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
    		System.out.println("Connecting to:" + args[0]+ " on port:" + args[1]);
        	Main.s_host = args[0];
        	Main.s_port = args[1];

    	} else if(args.length > 2) {
    		System.out.println("Only <host> <port> is needed");
    	} else if(args.length < 2) {
    		System.out.println("Need <host> <port> to continue");
    	} else if(Integer.parseInt(args[1]) > 65535) {
    		System.out.println("Invalid port range");
    	}
    }    
}

class MainFrame extends JFrame
{
   
	private static final long serialVersionUID = 1L;
	private Thread readerThread;
	private JTextField statusColor;
	private JPanel statusBar;
    private MyTableModel model;
    private JTable table;
    private TableColumn tcol;
    private String s;
    private String hash_status = null;
    private String hash_machine = null;
    private String hash_instance = null;
    private String hash_vm_number = null;
    private String statushash_key = null;
    private HashMap<String, String> statushash = null;
    public Socket socket, socket_reconnect = null;
    public BufferedReader in = null;
    public DataOutputStream dos = null;
    public String bxbhost = Main.s_host;
    public String bxbport = Main.s_port;
    public int bxbport_int = Integer.parseInt(bxbport);
    public PrintWriter printwriter = null;
    DefaultTableCellRenderer renderer = new ColoredTableCellRenderer();
    DefaultTableCellRenderer tooltiprenderer = new DefaultTableCellRenderer();
    //Logger log = Logger.getLogger("statusmonitor.log");

    public MainFrame()
    {
    	if(bxbhost.contains("dev")) {
    		setTitle("Omega DEV System");
    	} else {
    		setTitle("Omega PROD System");
    	}
        statushash = new HashMap<String,String>();
        readerThread = new Thread(new Reader());
        readerThread.start();
        
        Font displayFont = new Font("Arial", Font.PLAIN, 10);
        model = new MyTableModel(statushash);
        
        statusColor = new JTextField(2);
        statusColor.setBackground(Color.green);
        statusColor.setEditable(false);
        
        statusBar = new JPanel(new BorderLayout());
        statusBar.add(statusColor, BorderLayout.WEST);

        table = new JTable(model);
        table.setBackground(Color.black);
        table.setForeground(Color.green);
        table.setFont(displayFont);
        table.setShowHorizontalLines(false);
        table.setShowVerticalLines(false);
        table.setOpaque(true);
        table.setAutoResizeMode(5);
        table.setFont(displayFont);
        
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
        table.setRowSorter(sorter);
        sorter.modelStructureChanged();
        
        tcol = table.getColumnModel().getColumn(0);
        tcol.setCellRenderer(new ColoredTableCellRenderer()); 
        tcol = table.getColumnModel().getColumn(1);
        tcol.setCellRenderer(new ColoredTableCellRenderer());
        tcol.setResizable(true);
        tcol.sizeWidthToFit();
        renderer.setToolTipText("Status Monitor");
        
        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.red);
 
        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(370,630));
 
        JPanel mainPane = new JPanel(new BorderLayout());
        mainPane.add(scroll, BorderLayout.CENTER);
        mainPane.add(statusBar, BorderLayout.SOUTH);
 
        setContentPane(mainPane);
        pack();
    }
    
    public class ColoredTableCellRenderer extends DefaultTableCellRenderer{
    	/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public Component getTableCellRendererComponent (JTable table, Object obj, boolean isSelected, boolean hasFocus, int row, int column) {
    		Component cell = super.getTableCellRendererComponent(
                    table, obj, false, false, row, column);
    		int modelRow = table.convertRowIndexToModel(row);
    		String m = table.getModel().getValueAt(modelRow, column).toString();
    		
    		if (m.equals("D")){
    			cell.setBackground(Color.red);
    		}
    		else if (m.equals("W")){
    			cell.setBackground(Color.magenta);
    		}
    		else if (m.equals("L")){
    			cell.setBackground(Color.yellow);
    		}
    		else if (m.equals("A")){
    			cell.setForeground(Color.black);
    			cell.setBackground(Color.green);
    		}
    		else if (m.equals("X")){
    			cell.setBackground(Color.blue);
    		}
    		else if (m.equals("R")){
    			cell.setForeground(Color.green);
    			cell.setBackground(Color.black);
    		}
				return cell;
		}
    }
 
    public class MyTableModel extends AbstractTableModel { 	 	
    	/**
    	 * 
    	 */
    	private static final long serialVersionUID = 1L;
    	public String[] headers = {"Machine/Instance","Status"};
    	public HashMap<String, String> rows;
  	
    	public MyTableModel(HashMap<String, String> data){	
    		super();
    		this.rows = statushash;		
    	}
    	
    	public void update()
    	{   	  
          this.rows = statushash;
    	}

    	@Override
    	public int getColumnCount() {
    		return headers.length;
    	}

    	@Override
    	public int getRowCount() {
    		return statushash.size();
    	}
    	public String getColumnName(int columnIndex){
    		return headers[columnIndex];
    	}
   	
    	public Object getValueAt(int row, int col) {
   		
    		switch(col)
    		{
    			case 0:
    				return statushash.keySet().toArray()[row];
    			case 1:
    				return statushash.values().toArray()[row];
    		}		
    		return "";

    	}
    	
    	public void setValueAt(Object value, int row, int col){ 		
    		//nothing to do here    		   
    	}

    }
    
    private class Reader implements Runnable
    {
        public void run()
        {       	
			try {
				socket = new Socket(bxbhost,bxbport_int);
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                printwriter = new PrintWriter(socket.getOutputStream(),true);
				} catch (UnknownHostException e1) {
					//e1.printStackTrace();
					//log.log(Level.WARNING, "Unable to connect to host:port");
					System.out.println("Unable to connect to host:port");
				} catch (IOException e1) {
					//e1.printStackTrace();
					//log.log(Level.WARNING, "Unable to read data");
					System.out.println("Unable to read data");
				}
            while(true)
            {
            	 try {
					s = in.readLine();
					//System.out.println(s);
				} catch (IOException e1) {
					//e1.printStackTrace();
					//log.log(Level.WARNING, "Unable to read data");
					System.out.println("Unable to read data");
					
				}
            	if((s != null) && (s.length() != 0)){
            		
            		if(s.charAt(0)=='>')
                    {
                        try{Thread.sleep(5000);} catch(Exception e){}
                        printwriter.write("h\n");
                        printwriter.flush();
                        System.out.println(s);
                    }
                    else if(s.charAt(18)=='d'){
                      	String s1 = s.substring(20,s.length());
                        StringTokenizer st = new StringTokenizer(s1, ",", false);
                        hash_machine = st.nextToken().toString();
                        hash_instance = st.nextToken().toString();
                        //hash_vm_number = st.nextToken().toString();
                        statushash_key = ((hash_machine+"  "+hash_instance));
                    	statushash.remove(statushash_key);                      	
                        model.update();
                        model.fireTableDataChanged(); 
                    } 
                    else if(s.charAt(18)=='a'){
                    	String s1 = s.substring(20,s.length());
                        StringTokenizer st = new StringTokenizer(s1, ",", false);
                        hash_machine = st.nextToken().toString();
                        hash_instance = st.nextToken().toString();

                    }
                    else if(s.charAt(18)=='s')
                    {                   	
                    	String s1 = s.substring(20,s.length());
                        StringTokenizer st = new StringTokenizer(s1, ",", false);
                        hash_status = st.nextToken().toString();
                        hash_machine = st.nextToken().toString();
                        hash_instance = st.nextToken().toString();
                        hash_vm_number = st.nextToken().toString();
                        System.out.println(s1);
                        if(s1.charAt(0)=='R'||s1.charAt(0)=='D'||s1.charAt(0)=='W'||s1.charAt(0)=='X'||s1.charAt(0)=='A'||s1.charAt(0)=='L'){
                        statushash_key = ((hash_machine+"  "+hash_instance));
                        System.out.println(statushash_key);
                        statushash.put(statushash_key, hash_status);
                        model.update();
                        model.fireTableDataChanged(); 
                        
                        for (Map.Entry<String, String> entry: statushash.entrySet()) {              
                        	//System.out.println(entry.getValue()+" "+entry.getKey());
                        //Runtime.getRuntime().gc();
                        }                                               
                    }
            	}
                  
            	}
            	else{
            		statusColor.setBackground(Color.red);
            		try {
							socket.close();					
							} catch (IOException e) {
								//e.printStackTrace();
								//log.log(Level.WARNING, "Socket has been closed");
								System.out.println("Socket closed");
							}
							System.exit(0);
            			}
            		}
        		}
    		}
		}



