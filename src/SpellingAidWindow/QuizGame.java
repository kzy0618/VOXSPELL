package SpellingAidWindow;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.DataRecorder;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;

public class QuizGame extends JFrame {

	private SpellingAid MainMenu;
	private JFrame itself;

	private JPanel contentPane;
	private JTextField textField;
	private JComboBox voiceChoice;
	private JComboBox voiceSpeed;
	private JTextArea textArea;

	private DataRecorder data;

	private ArrayList<String[]> currentLevelTesting=new ArrayList<String[]>();
	private ArrayList<String> currentLevelFailedlistTesting=new ArrayList<String>();

	private String word;
	private ArrayList<String> wordsInTesting;

	private boolean lastAttemptFailed=false;
	protected boolean processing=false;
	private int numberOfWordsToBetested;
	private int wordCount=0;
	private int wordsFailed=0;
	private int attempt=0;
	private String whatQuiz;
	private String currentLevel;

	private ArrayList<String> failedList;
	private int score;
	private JLabel scoreView;
	private int howManyScore=0;
	private int wordCorrect=0;
	/**
	 * Create the frame.
	 */
	public QuizGame(SpellingAid MainMenu, DataRecorder data, String whatQuiz, String currentLevel) {
		this.MainMenu=MainMenu;
		this.data=data;
		this.whatQuiz=whatQuiz;
		this.currentLevel=currentLevel;
		this.failedList=data.getFailedList().get(data.returnCurrentCategoryIndex(currentLevel));
		this.score=data.getScore();
		if(whatQuiz.equals("QuizGame")){
			getLevel();
		}
		itself=this;
		currentLevelTesting=data.getStatisticsInTesting();
		currentLevelFailedlistTesting=data.getFailedListInTesting();
		wordsInTesting=data.getWordsTesting();
		word=wordsInTesting.get(0);
		numberOfWordsToBetested=wordsInTesting.size();

		buildGUI();
	}

	public void buildGUI(){
		setBounds(100, 100, 765, 459);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		buildWindowListener();
		JButton rehear = new JButton("");
		rehear.setIcon(new ImageIcon("Image/speak.png"));
		rehear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				processing=data.isProcessing();
				if(processing==false){
					String whichVoice=(String) voiceChoice.getSelectedItem();
					String speed=(String) voiceSpeed.getSelectedItem();
					data.festivalGenerator(word, whichVoice, speed);
				}
			}
		});
		rehear.setBounds(552, 327, 64, 64);
		rehear.setBorderPainted(false);
		rehear.setOpaque(false);
		rehear.setContentAreaFilled(false);
		contentPane.add(rehear);

		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				processing=data.isProcessing();
				if(processing==false){
					itself.dispose();
					MainMenu.update();
					MainMenu.setVisible(true);
					data.setScore(score);
					MainMenu.setScore();
				}
			}
		});

		JLabel lblNewLabel_2 = new JLabel("SCORE");
		lblNewLabel_2.setFont(new Font("Dialog", Font.BOLD, 35));
		lblNewLabel_2.setBounds(604, 95, 132, 42);
		contentPane.add(lblNewLabel_2);

		scoreView = new JLabel("");
		scoreView.setText(score+"");
		scoreView.setFont(new Font("Dialog", Font.BOLD, 40));
		scoreView.setBounds(648, 149, 70, 42);
		contentPane.add(scoreView);
		button.setIcon(new ImageIcon("Image/return.png"));
		button.setBounds(648, 327, 64, 64);
		button.setBorderPainted(false);
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		contentPane.add(button);

		voiceChoice = new JComboBox();
		voiceChoice.setModel(new DefaultComboBoxModel(new String[] {"kal_diphone", "akl_nz_jdt_diphone"}));
		voiceChoice.setBounds(410, 327, 110, 28);
		contentPane.add(voiceChoice);

		voiceSpeed = new JComboBox(new DefaultComboBoxModel(new String[] {"normal", "fast", "slow"}));
		voiceSpeed.setBounds(410, 367, 110, 28);
		contentPane.add(voiceSpeed);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(148, 25, 437, 241);
		contentPane.add(scrollPane);

		textField = new JTextField();
		textField.setBounds(148, 327, 229, 64);

		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(148, 25, 564, 242);
		scrollPane.setViewportView(textArea);
		buildTextInput();

		textField.setColumns(10);
		contentPane.add(textField);

		JLabel lblNewLabel = new JLabel("Please press enter to submit your answer");
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 20));
		lblNewLabel.setBounds(148, 290, 564, 21);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("Image/image.png"));
		lblNewLabel_1.setBounds(0, 0, 765, 459);
		contentPane.add(lblNewLabel_1);

		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
	}

	public void buildTextInput(){
		String whichVoice=(String) voiceChoice.getSelectedItem();
		String speed=(String) voiceSpeed.getSelectedItem();
		String sentence = "Please spell the word "+word;
		data.festivalGenerator(sentence, whichVoice, speed);

		int tmp = wordCount+1;
		textArea.append("Please spell the word "+tmp+"/"+numberOfWordsToBetested+": ");
		printDash();
		textField.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent arg0) {
				processing=data.isProcessing();
				if(arg0.getKeyCode()==KeyEvent.VK_ENTER){
					if(!processing){
						processing=true;

						buildLogic();
						scoreView.setText(score+"");
						textField.setText("");
						if(wordCount==numberOfWordsToBetested){
							MainMenu.update();
							if(whatQuiz.equals("QuizGame")){
								boolean ifPassed=false;
								itself.dispose();
								if(wordsFailed<=numberOfWordsToBetested*0.2){
									ifPassed=true;
								}
								String tmp=wordCorrect+"";
								new EndWindow(MainMenu,data,currentLevel,ifPassed,tmp);
							}else if (whatQuiz.equals("Review")){
								itself.dispose();
								MainMenu.setVisible(true);
							}

						}
					}
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub	
			}
		});
	}
	/**
	 * build input logic to check word's correctness
	 */
	public void buildLogic(){
		String whichVoice=(String) voiceChoice.getSelectedItem();
		String speed=(String) voiceSpeed.getSelectedItem();
		String input=textField.getText();
		input=input.toLowerCase();

		String speak="";
		//ensure no empty input

		if(input.equals("")){

		}else if(input.equals(word.toLowerCase())){
			speak=speak+"Correct ";
			textArea.append("Correct\n");
			attempt=0;
			if (!(lastAttemptFailed)){
				wordCorrect++;
				score=score+howManyScore*2;
				if(!data.checkIfExist(word)){
					String[] currentWord={word,"1","0","0"};
					currentLevelTesting.add(currentWord);
				}else{
					int index=0;
					for(int i = 0;i<currentLevelTesting.size();i++){
						if(currentLevelTesting.get(i)[0].equals(word)){
							index=i;
							break;
						}
					}
					String[] currentWord=currentLevelTesting.get(index);
					int tmp=Integer.parseInt(currentWord[1]);
					tmp++;
					currentWord[1]=tmp+"";
				}

				if(failedList.contains(word)){
					failedList.remove(word);
				}

			}else if (lastAttemptFailed){
				score=score+howManyScore;
				if(!data.checkIfExist(word)){
					String[] currentWord={word,"0","1","0"};
					currentLevelTesting.add(currentWord);
				}else{
					int index=0;
					for(int i = 0;i<currentLevelTesting.size();i++){
						if(currentLevelTesting.get(i)[0].equals(word)){
							index=i;
							break;
						}
					}
					String[] currentWord=currentLevelTesting.get(index);
					int tmp=Integer.parseInt(currentWord[2]);
					tmp++;
					currentWord[2]=tmp+"";
				}
				lastAttemptFailed=false;
			}
			wordCount++;
			if(wordCount<numberOfWordsToBetested){
				word=wordsInTesting.get(wordCount);
				int tmp=wordCount+1;
				textArea.append("\nPlease spell the word: "+tmp+"/"+numberOfWordsToBetested+": ");
				printDash();
			}
			if(wordCount!=numberOfWordsToBetested){
				speak=speak+",Please spell the word "+word;
			}

		}else{
			speak=speak+"Incorrect ";
			textArea.append("Incorrect\n");
			if (attempt==1){
				wordsFailed++;
				textArea.append(word+"\n");
				boolean exist=false;
				for(String tmp:currentLevelFailedlistTesting){
					if(tmp.equals(word)){
						exist=true;
					}
				}

				if(!data.checkIfExist(word)){
					String[] currentWord={word,"0","0","1"};
					currentLevelTesting.add(currentWord);
				}else{
					int index=0;
					for(int i = 0;i<currentLevelTesting.size();i++){
						if(currentLevelTesting.get(i)[0].equals(word)){
							index=i;
							break;
						}
					}
					String[] currentWord=currentLevelTesting.get(index);
					int tmp=Integer.parseInt(currentWord[3]);
					tmp++;
					currentWord[1]=tmp+"";
				}

				if(!exist){
					currentLevelFailedlistTesting.add(word);
				}
				wordCount++;
				if(wordCount<numberOfWordsToBetested){
					int tmp = wordCount+1;
					textArea.append("\nPlease spell the word: "+tmp+"/"+numberOfWordsToBetested+": ");
					printDash();
					word=wordsInTesting.get(wordCount);
				}
				attempt=0;
				lastAttemptFailed=false;
				if(wordCount!=numberOfWordsToBetested){
					speak=speak+",     Please spell the word "+word;
				}
			}else if (!(lastAttemptFailed)){
				speak=speak+",try once more "+word+", "+word;
				lastAttemptFailed = true;
				data.festivalGenerator(speak,whichVoice,speed);
				attempt++;
				return;
			}
		}

		data.festivalGenerator(speak, whichVoice,speed);
	}
	
	/**
	 * print dashes with number of char in word as hint for user
	 */
	public void printDash(){
		for(int i =0;i<word.length();i++){
			textArea.append("_ ");
		}
		textArea.append("\n");
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
				data.setScore(score);
				MainMenu.setScore();
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

	private void getLevel(){
		if(currentLevel.equals("Level 1")){
			howManyScore=1;
		}else if(currentLevel.equals("Level 2")){
			howManyScore=2;
		}else if(currentLevel.equals("Level 3")){
			howManyScore=3;
		}else if(currentLevel.equals("Level 4")){
			howManyScore=4;
		}else if(currentLevel.equals("Level 5")){
			howManyScore=5;
		}else if(currentLevel.equals("Level 6")){
			howManyScore=6;
		}else if(currentLevel.equals("Level 7")){
			howManyScore=7;
		}else if(currentLevel.equals("Level 8")){
			howManyScore=8;
		}else if(currentLevel.equals("Level 9")){
			howManyScore=9;
		}else if(currentLevel.equals("Level 10")){
			howManyScore=10;
		}else if(currentLevel.equals("Level 11")){
			howManyScore=11;
		}


	}

}
