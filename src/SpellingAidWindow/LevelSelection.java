package SpellingAidWindow;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.DataRecorder;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;

public class LevelSelection extends JFrame {

	private SpellingAid MainMenu;
	private JFrame itself;
	private JPanel contentPane;
	private JComboBox<String> comboBox;
	private JButton start;

	private DataRecorder data;

	private String whichChoice;
	/**
	 * Create the frame.
	 */
	public LevelSelection(DataRecorder data, SpellingAid MainMenu, String whichChoice) {
		this.MainMenu=MainMenu;
		this.data=data;
		this.whichChoice=whichChoice;
		itself=this;

		buildGUI();
	}

	public void buildGUI(){
		setBounds(100, 100, 307, 265);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		String currentWordList=data.getCurrentType();
		int index=data.getWordListName().indexOf(currentWordList);
		//change back to Design.
		comboBox = new JComboBox<String>((String [])data.getWordlistCategory().get(index).toArray(new String[0]));  
		comboBox.setBounds(59, 93, 197, 44);
		contentPane.add(comboBox);

		JLabel lblNewLabel = new JLabel("<html>What category do you want to start with ?<html>");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel.setBounds(59, 37, 197, 44);
		contentPane.add(lblNewLabel);

		start = new JButton("start!");
		start.setFont(new Font("Dialog", Font.BOLD, 20));

		if(whichChoice.equals("NewQuizGame")){
			buildNewQuizGame();
		}else if(whichChoice.equals("ReviewMistakeQuiz")){
			buildReviewMistakeQuiz();
		}else if(whichChoice.equals("Learning")){
			buildLearn();
		}else if(whichChoice.equals("Review")){
			buildReview();
		}

		start.setBounds(59, 159, 197, 54);
		contentPane.add(start);
		
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
	}

	public void buildNewQuizGame(){
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				data.newQuizGame((String)comboBox.getSelectedItem());
				itself.dispose();
				MainMenu.setVisible(false);
				new QuizGame(MainMenu,data,"QuizGame",(String)comboBox.getSelectedItem());
			}
		});
	}

	public void buildReviewMistakeQuiz(){
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean ifAnyMistakeExist=data.ReviewGame((String)comboBox.getSelectedItem());
				if(ifAnyMistakeExist){
					itself.dispose();
					MainMenu.setVisible(false);
					new QuizGame(MainMenu,data,"Review",(String)comboBox.getSelectedItem());
				}else{
					noMistake();
				}
			}
		});
	}

	public void buildLearn(){
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				itself.dispose();
				MainMenu.setVisible(false);
				new Learning(data,MainMenu,(String)comboBox.getSelectedItem());
			}
		});
	}
	
	public void buildReview(){
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				itself.dispose();
				MainMenu.setVisible(false);
				new Review(data,MainMenu,(String)comboBox.getSelectedItem());
			}
		});
	}

	/**
	 * create a new window to show a message saying no mistakes in current category.
	 */
	private void noMistake(){
		final JFrame noMistake = new JFrame();
		noMistake.getContentPane().setLayout(new BorderLayout());
		JPanel np = new JPanel();
		np.setLayout(new BorderLayout());
		JLabel jlabel = new JLabel();
		jlabel.setText("<html>Congradulation!!<br>No mistake to be reviewd<html>");
		jlabel.setFont(new Font("Arial", Font.BOLD, 20));
		jlabel.setSize(100, 100);
		jlabel.setVisible(true);
		np.add(jlabel,BorderLayout.CENTER);
		ActionListener l = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				noMistake.dispose();
				itself.dispose();
				MainMenu.setVisible(true);
			}
		};
		JButton jb = new JButton("OK");
		jb.addActionListener(l);
		np.add(jb, BorderLayout.SOUTH);
		noMistake.getContentPane().add(np);
		noMistake.setVisible(true);
		noMistake.setLocationRelativeTo(null);
		noMistake.pack();
	}

	private void buildWindowListener(){
		this.addWindowListener(new WindowListener(){

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent e) {		
				MainMenu.setVisible(true);
				itself.dispose();
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
