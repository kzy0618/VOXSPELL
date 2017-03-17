package SpellingAidWindow;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.DataRecorder;
import Helper.VideoPlayer;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

public class EndWindow extends JFrame {
	private SpellingAid MainMenu;
	private JFrame itself;
	private JPanel contentPane;

	private JButton btnNext;
	private DataRecorder data;

	private String currentLevel;
	private boolean ifPassed;
	private String nextLevel;
	private String wordCorrect;
	/**
	 * Create the frame.
	 */
	public EndWindow(SpellingAid MainMenu,DataRecorder data,String currentLevel,boolean ifPassed, String wordCorrect) {
		this.MainMenu=MainMenu;
		this.data=data;
		this.currentLevel=currentLevel;
		this.ifPassed=ifPassed;
		this.wordCorrect=wordCorrect;
		itself=this;

		buildGUI();
	}

	public void buildGUI(){
		setBounds(100, 100, 800, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton main = new JButton(new ImageIcon("Image/home.png"));
		main.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				itself.dispose();
				MainMenu.setVisible(true);
			}
		});
		main.setContentAreaFilled(false);
		main.setBorderPainted(false);
		main.setBounds(269, 259, 64, 64);
		contentPane.add(main);

		JButton restart = new JButton("restart");
		restart.setFont(new Font("Dialog", Font.BOLD, 17));
		restart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				data.newQuizGame(currentLevel);
				itself.dispose();
				data.newQuizGame(currentLevel);
				new QuizGame(MainMenu,data,"QuizGame",currentLevel);
			}
		});
		restart.setBounds(195, 346, 138, 64);
		contentPane.add(restart);

		JButton reward = new JButton("");
		reward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				itself.dispose();
				new VideoPlayer(MainMenu,itself);
			}
		});
		btnNext = new JButton("next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				itself.dispose();
				data.newQuizGame(nextLevel);
				new QuizGame(MainMenu,data,"QuizGame",nextLevel);
			}
		});
		btnNext.setFont(new Font("Dialog", Font.BOLD, 17));
		btnNext.setBounds(463, 346, 138, 64);
		this.ifExistNextLevel();
		contentPane.add(btnNext);
		reward.setIcon(new ImageIcon("Image/reward.png"));
		reward.setContentAreaFilled(false);
		reward.setBorderPainted(false);
		reward.setBounds(463, 259, 64, 64);
		contentPane.add(reward);

		JLabel background = new JLabel("");
		if(ifPassed){
			background.setIcon(new ImageIcon("Image/congradulation.jpg"));
		}else{
			background.setIcon(new ImageIcon("Image/fail.jpg"));
		}
		
		String text="Accuracy Rate: ";
		text=text+wordCorrect+"/"+data.getNumberOfWordInGame();
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setForeground(Color.YELLOW);
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 27));
		lblNewLabel.setText(text);
		lblNewLabel.setBounds(253, 213, 429, 35);
		contentPane.add(lblNewLabel);
		
		JLabel label = new JLabel("");
		label.setText(text);
		label.setForeground(Color.BLACK);
		label.setFont(new Font("Dialog", Font.BOLD, 27));
		label.setBounds(255, 215, 429, 35);
		contentPane.add(label);
		background.setBounds(0, 0, 800, 450);
		contentPane.add(background);

		buildWindowListener();
		if(!ifPassed){
			btnNext.setEnabled(false);
		}
		this.setLocationRelativeTo(null);
		this.setVisible(true);
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

	/**
	 * check current category is the last word category in the word list, if it is the last word category, disable the next level button
	 * otherwise save the next word category in nextLevel.
	 */
	public void ifExistNextLevel(){
		int index=0;
		int indexR=0;
		for(int i=0;i<data.getWordlistCategory().size();i++){
			if(data.getWordlistCategory().get(i).contains(currentLevel)){
				indexR=i;
			}
		}
		ArrayList<String> categoryList=data.getWordlistCategory().get(indexR);
		for(int i =0;i<categoryList.size();i++){
			if(categoryList.get(i).equals(currentLevel)){
				index=i;
			}
		}
		if(index+1<categoryList.size()){
			nextLevel=categoryList.get(index+1);
		}else{
			btnNext.setEnabled(false);
		}
		
	}
	
}
