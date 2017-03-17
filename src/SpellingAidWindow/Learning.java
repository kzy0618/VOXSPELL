package SpellingAidWindow;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.DataRecorder;
import Helper.MyButton;

import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JComboBox;

public class Learning extends JFrame {

	private SpellingAid MainMenu;
	private Learning itself;

	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private JComboBox voiceChoice;
	private JComboBox comboBox;
	private JComboBox comboBox_1;
	private JList list;
	private JList wordDontKnow;
	private JLabel lblNewLabel;

	private JButton DontKnow;
	private JButton iKnow;

	private DataRecorder data;
	private String category;
	private ArrayList<String> currentCategory;
	private int count=0;
	private ArrayList<String> remove=new ArrayList<String>();

	/**
	 * Create the frame.
	 */
	public Learning(DataRecorder data, SpellingAid MainMenu,String category) {
		this.category=category;
		this.data=data;
		this.MainMenu=MainMenu;
		this.currentCategory=(ArrayList<String>) data.getWordDontKnow().get(data.returnCurrentCategoryIndex(category)).clone();
		currentCategory.remove(0);
		Collections.shuffle(currentCategory);
		itself=this;
		buildGUI();
	}

	public void buildGUI(){
		setBounds(100, 100, 798, 403);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 798, 419);
		contentPane.add(tabbedPane);

		buildStudying();
		buildWordListPanel();
		buildWordDontKnowPanel();

		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}

	public void buildStudying(){
		JPanel learn = new JPanel();
		tabbedPane.addTab("Learning", null, learn, null);
		learn.setLayout(null);

		String txt="<html>Congratulations!! <br>You learned all words in this level<html>";
		if(currentCategory.size()!=0){
			txt=currentCategory.get(count);
		}
		lblNewLabel = new JLabel( txt,SwingConstants.CENTER);
		lblNewLabel.setBounds(44, 62, 593, 128);
		learn.add(lblNewLabel);

		comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"kal_diphone", "akl_nz_jdt_diphone"}));
		comboBox_1.setBounds(527, 245, 135, 60);
		learn.add(comboBox_1);

		JButton hear = new JButton("");
		hear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String word=currentCategory.get(count);
				String whichVoice=(String) comboBox_1.getSelectedItem();
				data.festivalGenerator(word, whichVoice, "slow");
			}
		});
		hear.setIcon(new ImageIcon("Image/speak2.png"));
		hear.setBorderPainted(false);
		hear.setOpaque(false);
		hear.setContentAreaFilled(false);

		hear.setBounds(637, 62, 128, 128);
		learn.add(hear);

		iKnow = new JButton("I KNOW");
		iKnow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remove.add(currentCategory.get(count));
				count++;
				itself.update();
				if(count<currentCategory.size()){
					lblNewLabel.setText(currentCategory.get(count));
				}else{
					lblNewLabel.setText("<html>Congratulations!! <br>You learned all words in this level<html>");
					lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 35));
					iKnow.setEnabled(false);
					DontKnow.setEnabled(false);
				}

			}
		});
		iKnow.setFont(new Font("Dialog", Font.BOLD, 25));
		iKnow.setBounds(44, 245, 190, 60);
		learn.add(iKnow);

		DontKnow = new JButton("I DON'T KNOW");
		DontKnow.setFont(new Font("Dialog", Font.BOLD, 25));
		DontKnow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				count++;
				if(count<currentCategory.size()){
					lblNewLabel.setText(currentCategory.get(count));
				}
			}
		});
		DontKnow.setBounds(257, 245, 251, 60);
		learn.add(DontKnow);

		if(currentCategory.size()!=0){
			lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 55));
		}else{
			lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 35));
			iKnow.setEnabled(false);
			DontKnow.setEnabled(false);
		}
		
		JButton returnMain = new JButton("");
		returnMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				itself.dispose();
				MainMenu.setVisible(true);
				data.getWordDontKnow().get(data.returnCurrentCategoryIndex(category)).removeAll(remove);
			}
		});
		returnMain.setIcon(new ImageIcon("Image/return.png"));
		returnMain.setBorderPainted(false);
		returnMain.setOpaque(false);
		returnMain.setContentAreaFilled(false);
		returnMain.setBounds(701, 236, 64, 64);
		learn.add(returnMain);
		
		JLabel background = new JLabel("");
		background.setIcon(new ImageIcon("Image/learningBK.jpg"));
		background.setBounds(0, 0, 798, 419);
		learn.add(background);
	}

	public void buildWordListPanel(){
		voiceChoice = new JComboBox();
		voiceChoice.setModel(new DefaultComboBoxModel(new String[] {"kal_diphone", "akl_nz_jdt_diphone"}));
		voiceChoice.setBounds(644, 12, 123, 41);

		JPanel wordList = new JPanel();
		tabbedPane.addTab("Word List", null, wordList, null);
		wordList.setLayout(null);

		list = new JList(this.getAllWord());
		list.setFont(new Font("Dialog", Font.BOLD, 15));
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBounds(12, 12, 605, 325);
		wordList.add(scrollPane);

		JButton return1 = new JButton(new ImageIcon("Image/return96.png"));
		return1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				itself.dispose();
				MainMenu.setVisible(true);
				data.getWordDontKnow().get(data.returnCurrentCategoryIndex(category)).removeAll(remove);
			}
		});
		return1.setBorderPainted(false);
		return1.setOpaque(false);
		return1.setContentAreaFilled(false);
		return1.setBounds(644, 241, 96, 96);
		wordList.add(return1);

		JButton speak1 = new JButton("");
		speak1.setIcon(new ImageIcon("Image/speak96.png"));
		speak1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String word=(String)list.getSelectedValue();
				String whichVoice=(String) voiceChoice.getSelectedItem();
				data.festivalGenerator(word, whichVoice, "slow");
			}
		});
		speak1.setBorderPainted(false);
		speak1.setOpaque(false);
		speak1.setContentAreaFilled(false);
		speak1.setBounds(644, 93, 96, 96);
		wordList.add(speak1);

		wordList.add(voiceChoice);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("Image/learningBK.jpg"));
		lblNewLabel_1.setBounds(0, 0, 798, 419);
		wordList.add(lblNewLabel_1);
	}

	public void buildWordDontKnowPanel(){
		JPanel NewWord = new JPanel();
		tabbedPane.addTab("Word Not Remembered", null, NewWord, null);
		NewWord.setLayout(null);

		JButton return2 = new JButton(new ImageIcon("Image/return96.png"));
		return2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				itself.dispose();
				MainMenu.setVisible(true);
				data.getWordDontKnow().get(data.returnCurrentCategoryIndex(category)).removeAll(remove);
			}
		});
		return2.setBorderPainted(false);
		return2.setOpaque(false);
		return2.setContentAreaFilled(false);
		return2.setBounds(644, 241, 96, 96);
		NewWord.add(return2);

		String[] tmp= this.getAllDont();
		wordDontKnow = new JList(this.getAllDont());
		wordDontKnow.setFont(new Font("Dialog", Font.BOLD, 15));
		JScrollPane scrollPane = new JScrollPane(wordDontKnow);
		scrollPane.setBounds(12, 12, 605, 325);
		NewWord.add(scrollPane);

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"kal_diphone", "akl_nz_jdt_diphone"}));
		comboBox.setBounds(644, 12, 123, 41);
		NewWord.add(comboBox);

		JButton button = new JButton(new ImageIcon("Image/speak96.png"));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String word=(String)wordDontKnow.getSelectedValue();
				String whichVoice=(String) comboBox.getSelectedItem();
				data.festivalGenerator(word, whichVoice, "slow");
			}
		});
		button.setBounds(644, 93, 96, 96);
		button.setBorderPainted(false);
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		NewWord.add(button);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBounds(0, 0, 798, 419);
		lblNewLabel_2.setIcon(new ImageIcon("Image/learningBK.jpg"));
		NewWord.add(lblNewLabel_2);

	}

	/**
	 * return all all words in current category in string array
	 * @return
	 */
	public String[] getAllWord(){
		ArrayList<ArrayList<String>> allWord=data.getAllWordlist();
		ArrayList<String> current=allWord.get(data.returnCurrentCategoryIndex(category));
		String[] output=new String[current.size()];
		for(int i = 0; i < current.size();i++){
			output[i]=current.get(i);
		}
		return output;
	}

	/**
	 * get all words user does know.
	 * @return
	 */
	public String[] getAllDont(){
		ArrayList<String> tmp=(ArrayList<String>) currentCategory.clone();
		tmp.removeAll(remove);
		String[] output=new String[tmp.size()];
		for(int i = 0; i < tmp.size();i++){
			output[i]=tmp.get(i);
		}
		return output;
	}

	/**
	 * update word dont know panel
	 */
	public void update(){
		tabbedPane.remove(2);
		buildWordDontKnowPanel();
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
				data.getWordDontKnow().get(data.returnCurrentCategoryIndex(category)).removeAll(remove);
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