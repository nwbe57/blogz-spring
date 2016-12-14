package org.launchcode.blogz.controllers;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;

public class deleteGui {

	private JFrame frame;
	private final Action actionYes = new SwingAction();
	private final Action actionNo = new SwingAction();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					deleteGui window = new deleteGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public deleteGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 176);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblDeleteConf = new JLabel("Are you sure you want to delete this post?");
		lblDeleteConf.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDeleteConf.setBounds(53, 22, 334, 22);
		frame.getContentPane().add(lblDeleteConf);
		
		JButton btnYes = new JButton("YES");
		btnYes.setAction(actionYes);
		btnYes.setBounds(53, 78, 89, 23);
		frame.getContentPane().add(btnYes);
		
		JButton btnNo = new JButton("NO");
		btnNo.setAction(actionNo);
		btnNo.setBounds(250, 78, 89, 23);
		frame.getContentPane().add(btnNo);
	}
	
	@SuppressWarnings("serial")
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}