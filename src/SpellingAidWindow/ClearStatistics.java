package SpellingAidWindow;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.DataRecorder;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Color;

public class ClearStatistics extends JFrame {

	private SpellingAid MainMenu;
	private JFrame itself;
	private JPanel contentPane;
	
	private JComboBox comboBox;
	
	private DataRecorder data;
	
	/**
	 * Create the frame.
	 */
	public ClearStatistics(SpellingAid MainMenu, DataRecorder data) {
		this.MainMenu=MainMenu;
		this.data=data;
		itself=this;
		
		buildGUI();
	}
	
	private void buildGUI(){
		buildWindowListener();
		
		setBounds(100, 100, 513, 255);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblWhatStatisticsDo = new JLabel("what statistics do you want to clear ?");
		lblWhatStatisticsDo.setForeground(Color.BLACK);
		lblWhatStatisticsDo.setBackground(Color.BLACK);
		lblWhatStatisticsDo.setFont(new Font("Arial", Font.BOLD, 20));
		lblWhatStatisticsDo.setBounds(37, 25, 443, 24);
		contentPane.add(lblWhatStatisticsDo);

		String currentType=data.getCurrentType();
		int index=data.getWordListName().indexOf(currentType);
		ArrayList<String> label=(ArrayList<String>) data.getWordlistCategory().get(index).clone();
		label.add("All");
		comboBox = new JComboBox((String [])label.toArray(new String[0]));
		comboBox.setBounds(37, 91, 185, 34);
		contentPane.add(comboBox);
		
		JButton confirm = new JButton("Confrim");
		confirm.setFont(new Font("Arial", Font.BOLD, 20));
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buildConfirmGUI();
			}
		});
		confirm.setBounds(317, 161, 163, 53);
		contentPane.add(confirm);
		
		JButton back = new JButton("Back");
		back.setFont(new Font("Arial", Font.BOLD, 20));
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainMenu.setVisible(true);
				itself.dispose();
			}
		});
		back.setBounds(37, 161, 163, 53);
		contentPane.add(back);
		
		JLabel lblNewLabel = new JLabel(new ImageIcon("Image/clear.jpg"));
		lblNewLabel.setBounds(0, 0, 513, 255);
		contentPane.add(lblNewLabel);
		
		this.setVisible(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
	
	public void buildConfirmGUI(){
		final JFrame confirm=new JFrame();
		
		confirm.setBounds(100, 100, 420, 125);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		confirm.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAreYouSure = new JLabel("<html>Are you sure you want to clear the statistics ?<html>");
		lblAreYouSure.setFont(new Font("Arial", Font.BOLD, 15));
		lblAreYouSure.setBounds(12, 12, 393, 27);
		contentPane.add(lblAreYouSure);
		
		JButton yes = new JButton("Yes");
		yes.setFont(new Font("Times New Roman", Font.BOLD, 20));
		yes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String whichStatistics= (String) comboBox.getSelectedItem();
				data.clearStatistics(whichStatistics);
				confirm.dispose();
			}
		});
		yes.setBounds(60, 51, 130, 38);
		contentPane.add(yes);
		
		JButton no = new JButton("No");
		no.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirm.dispose();
			}
		});
		no.setFont(new Font("Times New Roman", Font.BOLD, 20));
		no.setBounds(215, 51, 130, 38);
		contentPane.add(no);
		confirm.setVisible(true);
		confirm.setLocationRelativeTo(null);
		confirm.setResizable(false);
	}

	private void buildWindowListener(){
		this.addWindowListener(new WindowListener(){

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent e) {		
				itself.dispose();
				MainMenu.setVisible(true);
			}

			@Override
			public void windowClosed(WindowEvent e) {
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
}
