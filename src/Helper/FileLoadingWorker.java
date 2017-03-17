package Helper;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

public class FileLoadingWorker extends SwingWorker<Void, Void> {

	private ArrayList<ArrayList<String>> wordlistCategory;
	private ArrayList<ArrayList<String[]>> history;
	private ArrayList<ArrayList<String>> failedList;
	private ArrayList<ArrayList<String>> allWordlist;
	private ArrayList<ArrayList<String>> wordDontKnow;
	private ArrayList<String> wordListName;
	private ArrayList<String> path;
	private DataRecorder data;

	public FileLoadingWorker(DataRecorder data){
		this.data=data;
		this.wordlistCategory=data.getWordlistCategory();
		this.allWordlist=data.getAllWordlist();
		this.history=data.getHistory();
		this.failedList=data.getFailedList();
		this.wordListName=data.getWordListName();
		this.wordDontKnow=data.getWordDontKnow();
		this.path=data.getPath();
	}

	@Override
	protected Void doInBackground(){

		System.out.println("Project files loading starts......");

		loadWordlist();
		loadImportedData();
		loadHistory();
		loadFailedList();
		loadWordDontKnow();
		videoSetup();
		return null;
	}

	private void loadWordlist(){
		try{
			Scanner scanner = new Scanner(new FileReader("Image/NZCER-spelling-lists.txt"));
			int count=-1;
			wordlistCategory.add(new ArrayList<String>());
			while(scanner.hasNextLine()){
				String line=scanner.nextLine();
				if(line.contains("%")){
					ArrayList<String> currentLevel=new ArrayList<String>();
					allWordlist.add(currentLevel);
					line=line.trim();
					wordlistCategory.get(0).add(line.substring(1));
					count++;
					
					ArrayList<String> currentLevelFail =new ArrayList<String>();
					currentLevelFail.add(line.substring(1));
					failedList.add(currentLevelFail);
					
					ArrayList<String[]> currentLevelHistory =new ArrayList<String[]>();
					String[] categoryToArray = {line.substring(1)};
					currentLevelHistory.add(categoryToArray);
					history.add(currentLevelHistory);
					
					ArrayList<String> tmp =new ArrayList<String>();
					tmp.add(line.substring(1));
					wordDontKnow.add(tmp);
				}else{
					allWordlist.get(count).add(line);
				}
			}
			wordListName.add("Level List");
			scanner.close();
		}catch (FileNotFoundException e) {
			final JFrame newFrame = new JFrame();
			newFrame.setLayout(new BorderLayout());
			JPanel np = new JPanel();
			np.setLayout(new BorderLayout());
			JLabel jlabel = new JLabel();
			jlabel.setText("Error! Required file(s) cannot be found! "
					+ "Please check if NZCER-spelling-lists.txt file have been modified or deleted.");
			jlabel.setSize(100, 100);
			jlabel.setVisible(true);
			np.add(jlabel,BorderLayout.CENTER);

			JButton jb = new JButton("OK");
			jb.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					newFrame.setVisible(false);
					System.exit(1);
				}
			});
			np.add(jb, BorderLayout.SOUTH);
			newFrame.add(np);
			newFrame.setVisible(true);
			newFrame.setLocationRelativeTo(null);
			newFrame.pack();
		}
	}

	private void loadHistory(){
		try{

			Scanner scanner = new Scanner(new FileReader("Image/.Statistic.txt"));
			int count=-1;
			while(scanner.hasNextLine()){
				String line=scanner.nextLine();
				if(line.contains("%")){
					count = data.returnCurrentCategoryIndex(line.substring(1));
				}else{
					String[] stats=line.split(" ");
					history.get(count).add(stats);
				}
			}
			scanner.close();
			Scanner scanner2 = new Scanner(new FileReader("Image/.score.txt"));
			data.setScore(Integer.parseInt(scanner2.nextLine()));
			scanner2.close();
		}catch (FileNotFoundException e) {
			final JFrame newFrame = new JFrame();
			newFrame.setLayout(new BorderLayout());
			JPanel np = new JPanel();
			np.setLayout(new BorderLayout());
			JLabel jlabel = new JLabel();
			jlabel.setText("Error! Required file(s) cannot be found! "
					+ "Please check if Statistic.txt file have been modified or deleted.");
			jlabel.setSize(100, 100);
			jlabel.setVisible(true);
			np.add(jlabel,BorderLayout.CENTER);
			ActionListener l = new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					newFrame.setVisible(false);
					System.exit(1);
				}

			};
			JButton jb = new JButton("OK");
			jb.addActionListener(l);
			np.add(jb, BorderLayout.SOUTH);
			newFrame.add(np);
			newFrame.setVisible(true);
			newFrame.setLocationRelativeTo(null);
			newFrame.pack();
		}
	}

	public void loadFailedList(){
		try{

			Scanner scanner = new Scanner(new FileReader("Image/.failedList.txt"));
			int count=-1;
			while(scanner.hasNextLine()){
				String line=scanner.nextLine();
				if(line.contains("%")){
					count = data.returnCurrentCategoryIndex(line.substring(1));
				}else{
					failedList.get(count).add(line);
				}
			}
			scanner.close();
		}catch (FileNotFoundException e) {
			final JFrame newFrame = new JFrame();
			newFrame.setLayout(new BorderLayout());
			JPanel np = new JPanel();
			np.setLayout(new BorderLayout());
			JLabel jlabel = new JLabel();
			jlabel.setText("Error! Required file(s) cannot be found! "
					+ "Please check if failedList.txt file have been modified or deleted.");
			jlabel.setSize(100, 100);
			jlabel.setVisible(true);
			np.add(jlabel,BorderLayout.CENTER);
			ActionListener l = new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					newFrame.setVisible(false);
					System.exit(1);
				}

			};
			JButton jb = new JButton("OK");
			jb.addActionListener(l);
			np.add(jb, BorderLayout.SOUTH);
			newFrame.add(np);
			newFrame.setVisible(true);
			newFrame.setLocationRelativeTo(null);
			newFrame.pack();
		}
	}

	private void videoSetup(){
		ProcessBuilder p = null;
		try {
			File bunny = new File("Image/big_buck_bunny_1_minute.avi");
			String path = bunny.getCanonicalPath();
			path = path.substring(0, path.length()-bunny.getName().length());
			File bunny_negative = new File("Image/big_buck_bunny_1_minute_converted.avi");
			if (!(bunny_negative.exists())){ //dead code LOL
				p = new ProcessBuilder("/bin/bash","-c", "ffmpeg -i "+bunny.getCanonicalPath()+" -vf negate "+
						path+"big_buck_bunny_1_minute_converted.avi");
			}
		} catch (IOException e2) {
			final JFrame newFrame = new JFrame();
			newFrame.setLayout(new BorderLayout());
			JPanel np = new JPanel();
			np.setLayout(new BorderLayout());
			JLabel jlabel = new JLabel();
			jlabel.setText("Unresolvable error occurs!! "
					+ "Required video file does not exist.");
			jlabel.setSize(100, 100);
			jlabel.setVisible(true);
			np.add(jlabel,BorderLayout.CENTER);
			ActionListener l = new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					newFrame.setVisible(false);
					System.exit(1);
				}

			};
			JButton jb = new JButton("OK");
			jb.addActionListener(l);
			np.add(jb, BorderLayout.SOUTH);
			newFrame.add(np);
			newFrame.setVisible(true);
			newFrame.setLocationRelativeTo(null);
			newFrame.pack();
		}
		Process process = null;
		try {
			process = p.start();
			int exit = process.waitFor();
			if (exit==0){
				System.out.println("successfully converted video file.");
			}
		} catch (IOException | InterruptedException e1) {
			final JFrame newFrame = new JFrame();
			newFrame.setLayout(new BorderLayout());
			JPanel np = new JPanel();
			np.setLayout(new BorderLayout());
			JLabel jlabel = new JLabel();
			jlabel.setText("Unresolvable error occurs!! "
					+ "Underlying commands are possibly not functioning.");
			jlabel.setSize(100, 100);
			jlabel.setVisible(true);
			np.add(jlabel,BorderLayout.CENTER);
			ActionListener l = new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					newFrame.setVisible(false);
					System.exit(1);
				}

			};
			JButton jb = new JButton("OK");
			jb.addActionListener(l);
			np.add(jb, BorderLayout.SOUTH);
			newFrame.add(np);
			newFrame.setVisible(true);
			newFrame.setLocationRelativeTo(null);
			newFrame.pack();
		}
	}

	@Override
	protected void done(){
		System.out.println("Project files loading done.");
	}

	public void loadOwnWordlist(String path, String name){
		try {
			Scanner scanner = new Scanner(new FileReader(path));
			if(!this.path.contains(path)){
				this.path.add(path);
			}
			int index=wordListName.indexOf(name);
			int count=allWordlist.size()-1;
			while(scanner.hasNext()){
				String line=scanner.nextLine();
				if(line.contains("%")){
					wordlistCategory.get(index).add(line.substring(1));
					ArrayList<String> currentCategory=new ArrayList<String>();
					allWordlist.add(currentCategory);
					count++;

					ArrayList<String[]> currentLevel =new ArrayList<String[]>();
					String[] categoryToArray = {line.substring(1)};
					currentLevel.add(categoryToArray);
					history.add(currentLevel);

					ArrayList<String> currentLevelFailedList=new ArrayList<String>();
					currentLevelFailedList.add(line.substring(1));
					failedList.add(currentLevelFailedList);
					
					ArrayList<String> tmp =new ArrayList<String>();
					tmp.add(line.substring(1));
					wordDontKnow.add(tmp);
				}else{
					allWordlist.get(count).add(line);
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * When user retart voxspell, reload the word list already imported from user before from an TXT document which saves the
	 * word list path.
	 */
	public void loadImportedData(){
		try{
			Scanner scanner = new Scanner(new FileReader("Image/.Path.txt"));
			while(scanner.hasNextLine()){
				String path=scanner.nextLine();
				String name = new File(path).getName();
				wordListName.add(name);
				wordlistCategory.add(new ArrayList<String>());
				loadOwnWordlist(path,name);
			}
			scanner.close();
		}catch (FileNotFoundException e) {
			final JFrame newFrame = new JFrame();
			newFrame.setLayout(new BorderLayout());
			JPanel np = new JPanel();
			np.setLayout(new BorderLayout());
			JLabel jlabel = new JLabel();
			jlabel.setText("Error! Required file(s) cannot be found! "
					+ "Please check if NZCER-spelling-lists.txt file have been modified or deleted.");
			jlabel.setSize(100, 100);
			jlabel.setVisible(true);
			np.add(jlabel,BorderLayout.CENTER);

			JButton jb = new JButton("OK");
			jb.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					newFrame.setVisible(false);
					System.exit(1);
				}
			});
			np.add(jb, BorderLayout.SOUTH);
			newFrame.add(np);
			newFrame.setVisible(true);
			newFrame.setLocationRelativeTo(null);
			newFrame.pack();
		}
	}
	
	/**
	 * load data of word dont know list
	 */
	public void loadWordDontKnow(){
		try{
			Scanner scanner = new Scanner(new FileReader("Image/.WordDontKnow.txt"));
			int count=-1;
			while(scanner.hasNextLine()){
				String line=scanner.nextLine();
				if(line.contains("%")){
					count = data.returnCurrentCategoryIndex(line.substring(1));
				}else{
					wordDontKnow.get(count).add(line);
				}
			}
			scanner.close();
		}catch (FileNotFoundException e) {
			final JFrame newFrame = new JFrame();
			newFrame.setLayout(new BorderLayout());
			JPanel np = new JPanel();
			np.setLayout(new BorderLayout());
			JLabel jlabel = new JLabel();
			jlabel.setText("Error! Required file(s) cannot be found! "
					+ "Please check if failedList.txt file have been modified or deleted.");
			jlabel.setSize(100, 100);
			jlabel.setVisible(true);
			np.add(jlabel,BorderLayout.CENTER);
			ActionListener l = new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					newFrame.setVisible(false);
					System.exit(1);
				}

			};
			JButton jb = new JButton("OK");
			jb.addActionListener(l);
			np.add(jb, BorderLayout.SOUTH);
			newFrame.add(np);
			newFrame.setVisible(true);
			newFrame.setLocationRelativeTo(null);
			newFrame.pack();
		}
	}
	
}
