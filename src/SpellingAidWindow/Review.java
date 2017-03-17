package SpellingAidWindow;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import Helper.DataRecorder;

public class Review extends JFrame {

	private SpellingAid MainMenu;
	private JFrame itself;
	private JPanel contentPane;
	
	private JTabbedPane tabbedPane;
	private JComboBox comboBox;
	private DataRecorder data;
	private String category;
	private JList list;
	
	private ArrayList<String> currentFailedList;
	/**
	 * Create the frame.
	 */
	public Review( DataRecorder data, SpellingAid MainMenu, String category) {
		this.MainMenu=MainMenu;
		this.data=data;
		this.category=category;
		this.currentFailedList=(ArrayList<String>) data.getFailedList().get(data.returnCurrentCategoryIndex(category)).clone();
		currentFailedList.remove(0);
		buildWindowListener();
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
		buildPanel();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	
	public void buildPanel(){
		JPanel NewWord = new JPanel();
		tabbedPane.addTab("Word Not Remembered", null, NewWord, null);
		NewWord.setLayout(null);

		JButton return2 = new JButton(new ImageIcon("Image/return96.png"));
		return2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				itself.dispose();
				MainMenu.setVisible(true);
			}
		});
		return2.setBorderPainted(false);
		return2.setOpaque(false);
		return2.setContentAreaFilled(false);
		return2.setBounds(644, 241, 96, 96);
		NewWord.add(return2);

		list = new JList(this.getViewList());
		list.setFont(new Font("Dialog", Font.BOLD, 15));
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBounds(12, 12, 605, 325);
		NewWord.add(scrollPane);

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"kal_diphone", "akl_nz_jdt_diphone"}));
		comboBox.setBounds(644, 12, 123, 41);
		NewWord.add(comboBox);

		JButton button = new JButton(new ImageIcon("Image/speak96.png"));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String word=(String)list.getSelectedValue();
				String whichVoice=(String) comboBox.getSelectedItem();
				data.festivalGenerator(word, whichVoice, "slow");
			}
		});
		button.setBounds(644, 93, 96, 96);
		button.setBorderPainted(false);
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		NewWord.add(button);
	}

	/**
	 * return string array of failed list for current category
	 * @return
	 */
	public String[] getViewList(){
		String[] output=new String[currentFailedList.size()];
		for(int i = 0; i < currentFailedList.size();i++){
			output[i]=currentFailedList.get(i);
		}
		return output;
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
