import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

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
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class Main 
{
    public static void main(String[] args)
    {
        MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
 
}

class MainFrame extends JFrame
{
    /**
	 * 
	 */
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
    private String statushash_key = null;
    private HashMap<String, String> statushash = null;
    public Socket socket = null;
    public BufferedReader in = null;
    public DataOutputStream dos = null;
	//public String bpfhost = "bpflaunch01.intdata.com";
	public String bpfhost = args[0];
    //public int bpfport = 12346;
    public int bpfport = args[1];
    public PrintWriter printwriter = null;
    DefaultTableCellRenderer renderer = new ColoredTableCellRenderer();
 
    public MainFrame()
    {
        setTitle("Status Monitor");
        
        statushash = new HashMap<String,String>();
        readerThread = new Thread(new Reader());
        readerThread.start();

        model = new MyTableModel(statushash);
        
        statusColor = new JTextField(2);
        statusColor.setBackground(Color.green);
        statusColor.setEditable(false);
        
        statusBar = new JPanel(new BorderLayout());
        statusBar.add(statusColor, BorderLayout.WEST);
 
        table = new JTable(model);
        table.setBackground(Color.black);
        table.setForeground(Color.green);
        table.setShowHorizontalLines(false);
        table.setShowVerticalLines(false);
        table.setOpaque(true);
        
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
        table.setRowSorter(sorter);
        sorter.modelStructureChanged();
        
        tcol = table.getColumnModel().getColumn(0);
        tcol.setCellRenderer(new ColoredTableCellRenderer()); 
        tcol = table.getColumnModel().getColumn(1);
        tcol.setCellRenderer(new ColoredTableCellRenderer());
        
        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.red);
 
        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(320,620));
 
        JPanel mainPane = new JPanel(new BorderLayout());
        mainPane.add(scroll, BorderLayout.CENTER);
        mainPane.add(statusBar, BorderLayout.SOUTH);
 
        setContentPane(mainPane);
        pack();
    }
    
    public static boolean isEventDispatchThread(){
		
    	if(true)
    	
    	System.out.println("Running on event dispatch Thread");
    	
    	return false;
    	
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
    			cell.setBackground(Color.yellow);
    		}
    		else if (m.equals("W")){
    			cell.setBackground(Color.magenta);
    		}
    		else if (m.equals("X")){
    			cell.setBackground(Color.blue);
    		}
    		else{
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
					s = in.readLine();
					//System.out.println(s);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            	if((s != null) && (s.length() != 0)){
            		
                    if(s.charAt(0)=='>')
                    {
                        try{Thread.sleep(5000);} catch(Exception e){}
                        printwriter.write("h\n");
                        printwriter.flush();
                        System.out.println(s);
                    }
                    
                    if(s.charAt(0)=='d'){
                      	String s1 = s.substring(1,s.length());
                        StringTokenizer st = new StringTokenizer(s1, ",", false);
                        hash_machine = st.nextToken().toString();
                        hash_instance = st.nextToken().toString();
                        statushash_key = ((hash_machine+"  "+hash_instance));
                    	statushash.remove(statushash_key);                      	
                        model.update();
                        model.fireTableDataChanged(); 
                    } 
                    if(s.charAt(0)=='s')
                    {                   	
                    	String s1 = s.substring(1,s.length());
                        StringTokenizer st = new StringTokenizer(s1, ",", false);
                        hash_status = st.nextToken().toString();
                        hash_machine = st.nextToken().toString();
                        hash_instance = st.nextToken().toString(); 
                        if(s.charAt(1)=='R'||s.charAt(1)=='D'||s.charAt(1)=='W'||s.charAt(1)=='X'){
                        statushash_key = ((hash_machine+"  "+hash_instance));
                        statushash.put(statushash_key, hash_status); 
                        model.update();
                        model.fireTableDataChanged(); 
                        
                        for (Map.Entry<String, String> entry: statushash.entrySet()) {              
                        	System.out.println(entry.getValue()+" "+entry.getKey());
                        }                                               
                    }
            	}
                  
            	}
            	else{
            		statusColor.setBackground(Color.red);
            	}
            }
        }
    }
}




