package SpellingAidWindow;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Helper.DataRecorder;
import Helper.FileLoadingWorker;
import Helper.MyButton;
import Helper.VoiceSpeaker;

import java.awt.event.ActionEvent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;

public class SpellingAid extends JFrame {

	private SpellingAid itself;
	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private JPanel statisticView;
	private JScrollPane scrollPane;
	
	private DataRecorder data;
	private JTable table;
	private DefaultTableModel model;
	
	private JList view;
	private JLabel score;
	private JLabel score1;
	
	public SpellingAid() {
		data=new DataRecorder();
		itself=this;
		
		//starting files setup
		FileLoadingWorker fw = new FileLoadingWorker(data);
		fw.execute();
		buildGUI();
		
		
	}

	public void buildGUI(){
		
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		
		buildWindowListener();
		
		setBounds(100, 100, 720, 514);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 720, 514);
		contentPane.add(tabbedPane);
		
		buildQuizGamePanel();
		buildLearningPanel();
		setScore();
		buildStatisticPanel();
		
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}

	public void buildQuizGamePanel(){
		JPanel panel = new JPanel();
		tabbedPane.addTab("Quiz Game", null, panel, null);
		panel.setLayout(null);

		JButton turnOff = new JButton(new ImageIcon("Image/turn-off.png"));
		turnOff.setBorderPainted(false);
		turnOff.setOpaque(false);
		turnOff.setContentAreaFilled(false);
		turnOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buildTurnOff();
			}
		});
		turnOff.setBounds(602, 365, 64, 64);
		panel.add(turnOff);


		JButton setting = new JButton(new ImageIcon("Image/set.png"));
		setting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				itself.setVisible(false);
				new Option(data, itself);
			}
		});
		setting.setBorderPainted(false);
		setting.setOpaque(false);
		setting.setContentAreaFilled(false);
		setting.setBounds(602, 291, 64, 64);
		panel.add(setting);

		JButton play=new MyButton("PLAY");
		play.setFont(new Font("L M Roman Slant10", Font.BOLD, 20));
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new LevelSelection(data,itself,"NewQuizGame");
				
			}
		});
		play.setBounds(214, 82, 150, 150);
		play.setBorderPainted(false);
		play.setOpaque(false);
		play.setContentAreaFilled(false);
		panel.add(play);

		MyButton myButton = new MyButton("REVISION");
		myButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new LevelSelection(data,itself,"ReviewMistakeQuiz");
			}
		});
		myButton.setOpaque(false);
		myButton.setContentAreaFilled(false);
		myButton.setBorderPainted(false);
		myButton.setFont(new Font("L M Roman Slant10", Font.BOLD, 20));
		myButton.setBounds(421, 125, 150, 150);
		panel.add(myButton);

		MyButton clear = new MyButton("CLEAR");
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				itself.setVisible(false);
				new ClearStatistics(itself, data);
			}
		});
		clear.setFont(new Font("L M Roman Slant10", Font.BOLD, 20));
		clear.setOpaque(false);
		clear.setContentAreaFilled(false);
		clear.setBorderPainted(false);
		clear.setBounds(287, 268, 150, 150);
		panel.add(clear);
		
		JLabel lblScore = new JLabel("Score");
		lblScore.setFont(new Font("Dialog", Font.BOLD, 35));
		lblScore.setBounds(589, 129, 114, 42);
		panel.add(lblScore);
		
		score = new JLabel("");
		score.setFont(new Font("Dialog", Font.BOLD, 40));
		score.setBounds(626, 183, 58, 50);
		panel.add(score);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("Image/image.png"));
		label.setBounds(0, 0, 715, 487);
		panel.add(label);
	}

	public void buildLearningPanel(){
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Learning", null, panel_2, null);
		panel_2.setLayout(null);

		JButton settingInLearning = new JButton(new ImageIcon("Image/set.png"));
		settingInLearning.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				itself.setVisible(false);
				new Option(data, itself);
			}
		});
		settingInLearning.setBounds(602, 291, 64, 64);
		settingInLearning.setBorderPainted(false);
		settingInLearning.setOpaque(false);
		settingInLearning.setContentAreaFilled(false);
		panel_2.add(settingInLearning);

		JButton turnOffInLearning = new JButton(new ImageIcon("Image/turn-off.png"));
		turnOffInLearning.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buildTurnOff();
			}
		});
		turnOffInLearning.setBounds(602, 365, 64, 64);
		turnOffInLearning.setBorderPainted(false);
		turnOffInLearning.setOpaque(false);
		turnOffInLearning.setContentAreaFilled(false);
		panel_2.add(turnOffInLearning);
		
		JButton learning = new MyButton("START");
		learning.setFont(new Font("L M Roman Slant10", Font.BOLD, 20));
		learning.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new LevelSelection(data,itself,"Learning");
			}
		});
		learning.setBounds(223, 97, 150, 150);
		learning.setBorderPainted(false);
		learning.setOpaque(false);
		learning.setContentAreaFilled(false);
		panel_2.add(learning);
		
		MyButton myButton = new MyButton("REVIEW");
		myButton.setFont(new Font("L M Roman Slant10", Font.BOLD, 20));
		myButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new LevelSelection(data,itself,"Review");
			}
		});
		myButton.setOpaque(false);
		myButton.setContentAreaFilled(false);
		myButton.setBorderPainted(false);
		myButton.setBounds(389, 218, 150, 150);
		panel_2.add(myButton);
		
		JLabel label = new JLabel("Score");
		label.setFont(new Font("Dialog", Font.BOLD, 35));
		label.setBounds(589, 129, 114, 42);
		panel_2.add(label);
		
		score1 = new JLabel("");
		score1.setFont(new Font("Dialog", Font.BOLD, 40));
		score1.setBounds(626, 183, 58, 50);
		panel_2.add(score1);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("Image/image.png"));
		lblNewLabel.setBounds(0, 0, 715, 487);
		panel_2.add(lblNewLabel);
	}

	public void buildStatisticPanel(){
		statisticView = new JPanel();
		tabbedPane.addTab("Statistics", null, statisticView, null);
		statisticView.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 196, 487);
		statisticView.add(panel);
		panel.setLayout(null);
		
		String[] output=this.getWordCategory();
		view=new JList(output);
		
		scrollPane = new JScrollPane(view);
		scrollPane.setBounds(0, 0, 196, 487);
		panel.add(scrollPane);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(196, 0, 519, 487);
		statisticView.add(panel_2);
		panel_2.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 0, 519, 487);
		panel_2.add(scrollPane_1);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Word", "Mastered", "Faulted", "Failed"
			}
		));
		
		model=(DefaultTableModel) table.getModel();
		addContentToTable("Level 1");
		table.setEnabled(false);
		table.getTableHeader().setReorderingAllowed(false);
	
		view.addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				model.setRowCount(0);
				String wordCategory=(String) view.getSelectedValue();
				addContentToTable(wordCategory);
			}
		});
		
		scrollPane_1.setViewportView(table);
	}

	/**
	 * build turn off button--all datas are written into text file
	 */
	public void buildTurnOff(){
		data.writeFailedList();
		data.writePath();
		data.writeStatistic();
		data.writeWordDontKnow();
		System.exit(0);
	}
	
	private String[] getWordCategory(){
		ArrayList<ArrayList<String>> failedList = (ArrayList<ArrayList<String>>) data.getFailedList().clone();
		int count=data.getFailedList().size();
		String[] output=new String[count];
		for(int i = 0; i < count; i++){
			output[i]=failedList.get(i).get(0);
		}
		return output;
	}
	
	/**
	 * add content to table in statistics
	 * @param wordCategory
	 */
	private void addContentToTable(String wordCategory){
		int index=data.returnCurrentCategoryIndex(wordCategory);
		ArrayList<String[]> statistic=data.getHistory().get(index);
		for(int i=1;i<statistic.size();i++){
			String[] tmp =statistic.get(i);
			model.addRow(tmp);
		}
	}
	
	/**
	 * set score, can be called to update score.
	 */
	public void setScore(){
		score.setText(data.getScore()+"");
		score1.setText(data.getScore()+"");
	}
	
	public static void main(String[] agrs){
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				SpellingAid frame = new SpellingAid();
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
			}
		});
	}

	/**
	 * update statistics panel
	 */
	public void update(){
		tabbedPane.remove(2);
		buildStatisticPanel();
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
				data.writeFailedList();
				data.writePath();
				data.writeStatistic();
				data.writeWordDontKnow();
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



