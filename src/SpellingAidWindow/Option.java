package SpellingAidWindow;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import Helper.DataRecorder;
import Helper.FileLoadingWorker;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class Option extends JFrame {

	private JFrame itself;
	private SpellingAid MainMenu;

	private JPanel contentPane;
	private JButton importWordList;
	private JComboBox currentWordList;
	private JComboBox wordCategory;
	
	private DataRecorder data;
	private DataRecorder originalData;


	/**
	 * Create the frame.
	 */
	public Option(DataRecorder data, SpellingAid MainMenu) {
		this.data=data.clone();
		originalData=data;
		itself=this;
		this.MainMenu=MainMenu;

		buildGUI();
	}

	public void buildGUI(){
		buildWindowListener();
		setBounds(100, 100, 389, 384);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNumberOfWords = new JLabel("How many words do you want to be tested ?");
		lblNumberOfWords.setBounds(30, 50, 320, 35);
		contentPane.add(lblNumberOfWords);

		wordCategory = new JComboBox();
		wordCategory.setModel(new DefaultComboBoxModel(new String[] {"5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"}));

		//make sure comboBox shows the correct setting saved by user from last time.
		wordCategory.setSelectedIndex(data.getNumberOfWordInGame()-5);
		wordCategory.setBounds(30, 90, 60, 35);
		contentPane.add(wordCategory);

		JButton back = new JButton("back");
		back.setFont(new Font("Arial", Font.BOLD, 20));
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				itself.setVisible(false);
				MainMenu.setVisible(true);
				MainMenu.update();
			}
		});
		back.setIcon(new ImageIcon("Back"));
		back.setBounds(30, 284, 130, 50);
		contentPane.add(back);

		String[] label=(String[]) data.getWordListName().toArray(new String[0]);
		currentWordList = new JComboBox(label);
		//combobox need to be fix
		currentWordList.setSelectedIndex(data.getWordListName().indexOf(data.getCurrentType()));
		currentWordList.setBounds(30, 175, 193, 48);
		contentPane.add(currentWordList);

		buildImportButton();
		JButton save = new JButton("save");
		save.setFont(new Font("Arial", Font.BOLD, 20));
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				data.setNumberOfWordInGame(Integer.parseInt((String) wordCategory.getSelectedItem()));
				data.setCurrentType((String) currentWordList.getSelectedItem());
				saveData();
				changeSavedMessage();
			}
		});
		save.setBounds(220, 284, 130, 50);
		contentPane.add(save);

		JButton deleteWordList = new JButton(new ImageIcon("Image/delete.png"));
		deleteWordList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!currentWordList.getSelectedItem().equals("Level List")){
					deleteMessage();
				}else{
					CanNotDeleteDefaultListMessage();
				}
			}
		});
		deleteWordList.setBounds(302, 175, 48, 48);
		deleteWordList.setBorderPainted(false);
		deleteWordList.setOpaque(false);
		deleteWordList.setContentAreaFilled(false);
		contentPane.add(deleteWordList);


		JLabel lblNewLabel = new JLabel("Choose your word list\n");
		lblNewLabel.setBounds(30, 140, 300, 15);
		contentPane.add(lblNewLabel);
		
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
	}

	public void buildImportButton(){
		importWordList = new JButton(new ImageIcon("Image/import.png"));
		importWordList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser=new JFileChooser();
				int choice=fileChooser.showOpenDialog(null);
				if (choice == JFileChooser.APPROVE_OPTION) {
					File newWordlist=fileChooser.getSelectedFile();
					String path=newWordlist.getPath();
					String name=newWordlist.getName();
					data.getWordListName().add(name);
					data.getWordlistCategory().add(new ArrayList<String>());
					FileLoadingWorker fw=new FileLoadingWorker(data);
					fw.loadOwnWordlist(path, name);
					data.loadWordDontKnow(name);
					itself.dispose();
					saveData();
					new Option(data,MainMenu);
				} else if (choice == JFileChooser.CANCEL_OPTION) {
					System.out.println("Cancel was selected");
				}

			}
		});
		importWordList.setBounds(242, 175, 48, 48);
		importWordList.setBorderPainted(false);
		importWordList.setOpaque(false);
		importWordList.setContentAreaFilled(false);
		contentPane.add(importWordList);
	}

	/**
	 * create a window showing change saved message
	 */
	public void changeSavedMessage(){
		final JFrame settingChanged = new JFrame();
		settingChanged.getContentPane().setLayout(new BorderLayout());
		JPanel np = new JPanel();
		np.setLayout(new BorderLayout());
		JLabel jlabel = new JLabel();
		jlabel.setText("Setting Change saved");
		jlabel.setFont(new Font("Arial", Font.BOLD, 20));
		jlabel.setSize(100, 100);
		jlabel.setVisible(true);
		np.add(jlabel,BorderLayout.CENTER);
		ActionListener l = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				settingChanged.dispose();
				itself.dispose();
				new Option(originalData, MainMenu);
			}

		};
		JButton jb = new JButton("OK");
		jb.addActionListener(l);
		np.add(jb, BorderLayout.SOUTH);
		settingChanged.getContentPane().add(np);
		settingChanged.setVisible(true);
		settingChanged.setLocationRelativeTo(null);
		settingChanged.pack();
	}

	/**
	 * save data from clone data.
	 */
	public void saveData(){
		originalData.setAllWordlist(data.getAllWordlist());
		originalData.setFailedList(data.getFailedList());
		originalData.setHistory(data.getHistory());
		originalData.setNumberOfWordInGame(data.getNumberOfWordInGame());
		originalData.setWordlistCategory(data.getWordlistCategory());
		originalData.setWordListName(data.getWordListName());
		originalData.setCurrentType(data.getCurrentType());
		originalData.setPath(data.getPath());
		originalData.setWordDontKnow(data.getWordDontKnow());
	}

	/**
	 * create a window confirming if user want to delete the word list
	 */
	public void deleteMessage(){
		final JFrame settingChanged = new JFrame();
		settingChanged.setBounds(100, 100, 327, 102);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		settingChanged.setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel jlabel = new JLabel("Are you sure you want to delete this word list ?");
		jlabel.setFont(new Font("Arial", Font.BOLD, 20));
		jlabel.setSize(100, 100);
		contentPane.add(jlabel,BorderLayout.CENTER);

		JButton yes = new JButton("yes");
		yes.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				int indexOfWordList = currentWordList.getSelectedIndex();
				String nameOfWordList=data.getWordListName().get(indexOfWordList);
				
				int index=data.getFirstElement(nameOfWordList);
				
				for(int i =0;i<data.getWordlistCategory().get(indexOfWordList).size();i++){
					data.getFailedList().remove(index);
					data.getAllWordlist().remove(index);
					data.getHistory().remove(index);
					data.getWordDontKnow().remove(index);
				}
				
				data.getWordListName().remove(indexOfWordList);
				data.getPath().remove(indexOfWordList-1);
				data.getWordlistCategory().remove(indexOfWordList);
				
				if(data.getCurrentType().equals(currentWordList.getSelectedItem())){
					data.setCurrentType("Level List");
				}
				
				
				saveData();
				settingChanged.dispose();
				itself.dispose();
				new Option(originalData,MainMenu);
			}
		});

		JButton no = new JButton("No");
		no.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				settingChanged.dispose();
			}

		});
		contentPane.add(yes);
		contentPane.add(no);
		settingChanged.setVisible(true);
		settingChanged.setLocationRelativeTo(null);
		settingChanged.pack();
	}

	/**
	 * showing a message default list cannot be deleted
	 */
	public void CanNotDeleteDefaultListMessage(){
		final JFrame settingChanged = new JFrame();
		settingChanged.setBounds(100, 100, 327, 102);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		settingChanged.setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel jlabel = new JLabel("Can not delete default word list");
		jlabel.setFont(new Font("Arial", Font.BOLD, 20));
		jlabel.setSize(100, 100);
		contentPane.add(jlabel,BorderLayout.CENTER);

		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				settingChanged.dispose();
			}
		});

		contentPane.add(ok);
		settingChanged.setVisible(true);
		settingChanged.setLocationRelativeTo(null);
		settingChanged.pack();
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