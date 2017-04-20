package com.app.purchasegui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.GroupLayout;
import javax.swing.JTextArea;
import com.app.purchase.BuyItem;

public class PurchaseFrontEnd extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PurchaseFrontEnd() {
		initUI();
	}
	
	
	private void initUI() {
		
		JButton quitButton = new JButton("Quit");
		JButton purchaseItem = new JButton("Purchase");
		JTextArea jta = new JTextArea();
		
		createLayout(quitButton,purchaseItem,jta);
		
		//createLayout(quitButton);
		quitButton.addActionListener((ActionEvent event)-> {
			System.exit(0);
		});
		
		//createLayout(purchaseItem);
		purchaseItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				BuyItem bl = new BuyItem("Test Item", 2.99, 12345);
				System.out.println("Will purchase something.");
				//System.out.println(bl.parseItemMembers(bl));
				//jta.setText(bl.parseItemMembers(bl));
			}
		});
		
		
		setTitle("Purchase Item");
		setSize(300,200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
	private void createLayout(JComponent... arg) {
		/*Container pane = getContentPane();
		GroupLayout gl = new GroupLayout(pane);
		pane.setLayout(gl);
		gl.setAutoCreateContainerGaps(true);
		
		for(int i = 0; i < arg.length; i++) {
			gl.setHorizontalGroup(gl.createSequentialGroup().addComponent(arg[0]));
			gl.setVerticalGroup((gl.createSequentialGroup().addComponent(arg[0])));
		}*/
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JPanel subPanel = new JPanel();
		for(int i = 0; i < arg.length; i++) {
			subPanel.add(arg[i]);
		}
		
		panel.add(subPanel, BorderLayout.EAST);
		panel.setVisible(true);
		
		
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			PurchaseFrontEnd pe = new PurchaseFrontEnd();
			pe.setVisible(true);
		});
	}

}
