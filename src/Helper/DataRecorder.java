package Helper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.SwingWorker;

import SpellingAidWindow.QuizGame;

/**
 * store all datas
 * @author kzy0618
 *
 */
public class DataRecorder {
	private String currentWorkingDirectory = System.getProperty("user.dir");

	private ArrayList<ArrayList<String>> wordlistCategory=new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<String[]>> history=new ArrayList<ArrayList<String[]>>();
	private ArrayList<ArrayList<String>> failedList=new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<String>> allWordlist=new ArrayList<ArrayList<String>>();
	private ArrayList<String> wordListName=new ArrayList<String>();
	private ArrayList<String> wordsTesting;
	private ArrayList<ArrayList<String>> wordDontKnow=new ArrayList<ArrayList<String>>();

	protected boolean processing = false;
	protected ProcessBuilder p;
	protected Process process;

	private String currentType="Level List";
	private int numberOfWordInGame=10;
	private int currentCategoryInTesting; //index of the category of the word list in game
	private int score;

	private VoiceSpeaker vc=VoiceSpeaker.getInstance();

	//	private boolean wordExistInStatistics=false;
	//	private int wordsCorrect=0;
	//	private int attempt=0;

	//save path of the word list imported from last time
	private ArrayList<String> path=new ArrayList<String>();


	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getCurrentCategoryInTesting() {
		return currentCategoryInTesting;
	}

	public String getCurrentType() {
		return currentType;
	}

	public void setCurrentType(String currentType) {
		this.currentType = currentType;
	}

	public ArrayList<String> getWordListName() {
		return wordListName;
	}

	public ArrayList<ArrayList<String>> getWordlistCategory() {
		return wordlistCategory;
	}

	public ArrayList<ArrayList<String[]>> getHistory() {
		return history;
	}

	public ArrayList<ArrayList<String>> getFailedList() {
		return failedList;
	}

	public ArrayList<ArrayList<String>> getAllWordlist() {
		return allWordlist;
	}

	public ArrayList<String> getWordsTesting() {
		return wordsTesting;
	}

	public ArrayList<String> getPath() {
		return path;
	}

	public void setPath(ArrayList<String> path) {
		this.path = path;
	}

	public int getNumberOfWordInGame(){
		return numberOfWordInGame;
	}

	public boolean isProcessing() {
		return processing;
	}

	public ArrayList<ArrayList<String>> getWordDontKnow() {
		return wordDontKnow;
	}

	public void setWordDontKnow(ArrayList<ArrayList<String>> wordDontKnow) {
		this.wordDontKnow = wordDontKnow;
	}

	public void setWordlistCategory(ArrayList<ArrayList<String>> wordlistCategory) {
		this.wordlistCategory = wordlistCategory;
	}

	public void setHistory(ArrayList<ArrayList<String[]>> history) {
		this.history = history;
	}

	public void setFailedList(ArrayList<ArrayList<String>> failedList) {
		this.failedList = failedList;
	}

	public void setAllWordlist(ArrayList<ArrayList<String>> allWordlist) {
		this.allWordlist = allWordlist;
	}

	public void setNumberOfWordInGame(int numberOfWordInGame){
		this.numberOfWordInGame=numberOfWordInGame;
	}

	public void setWordListName(ArrayList<String> wordListName) {
		this.wordListName = wordListName;
	}

	public void writeStatistic(){
		try {
			FileWriter writer=new FileWriter("Image/.Statistic.txt");
			BufferedWriter out =new BufferedWriter(writer);
			for(int currentLevelNumber =0;currentLevelNumber<history.size();currentLevelNumber++){
				String line=history.get(currentLevelNumber).get(0)[0];
				line="%"+line;
				out.write(line);
				out.newLine();
				ArrayList<String[]> currentLevel=history.get(currentLevelNumber);
				for(int i=1;i<currentLevel.size();i++){
					line="";
					String[] currentWordArray=currentLevel.get(i);
					line=line+currentWordArray[0];
					line=line+" "+currentWordArray[1];
					line=line+" "+currentWordArray[2];
					line=line+" "+currentWordArray[3];
					out.write(line);
					out.newLine();
				}
			}
			out.flush();
			out.close();

			FileWriter writer2=new FileWriter("Image/.score.txt");
			BufferedWriter out2 =new BufferedWriter(writer2);
			out2.write(score+"");
			out2.flush();
			out2.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeFailedList(){
		try {
			FileWriter writer=new FileWriter("Image/.failedList.txt");
			BufferedWriter out =new BufferedWriter(writer);

			for(ArrayList<String> currentLevel : failedList){
				String line = currentLevel.get(0);
				line="%"+line;
				out.write(line);
				out.newLine();
				for(int i = 1;i<currentLevel.size();i++){
					line=currentLevel.get(i);
					out.write(line);
					out.newLine();
				}
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void writePath(){
		try {
			FileWriter writer=new FileWriter("Image/.Path.txt");
			BufferedWriter out =new BufferedWriter(writer);
			for(String currentPath : path){
				out.write(currentPath);
				out.newLine();
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeWordDontKnow(){
		try {
			FileWriter writer=new FileWriter("Image/.WordDontKnow.txt");
			BufferedWriter out =new BufferedWriter(writer);

			for(ArrayList<String> currentLevel : wordDontKnow){
				String line = currentLevel.get(0);
				line="%"+line;
				out.write(line);
				out.newLine();
				for(int i = 1;i<currentLevel.size();i++){
					line=currentLevel.get(i);
					out.write(line);
					out.newLine();
				}
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * store the words to be tested in wordsTesting arraylist
	 * @param wordCategory
	 */
	public void newQuizGame(String wordCategory){

		currentCategoryInTesting=this.returnCurrentCategoryIndex(wordCategory);
		ArrayList<String> currentLevel=allWordlist.get(currentCategoryInTesting);

		wordsTesting=new ArrayList<String>();
		String word=currentLevel.get(new Random().nextInt(currentLevel.size()));
		wordsTesting.add(word);

		int number=0;
		if(numberOfWordInGame>currentLevel.size()){
			number=currentLevel.size();
		}else{
			number=numberOfWordInGame;
		}

		for(int i =0;i<number-1;i++){
			word=currentLevel.get(new Random().nextInt(currentLevel.size()));
			while(wordsTesting.contains(word)){
				word=currentLevel.get(new Random().nextInt(currentLevel.size()));
			}
			wordsTesting.add(word);
		};
	}

	/**
	 * store the words to be tested in wordsTesting arrayList and also return a boolean whether there is 
	 * any word in current level in failed list. 
	 * @param wordCategory
	 */
	public boolean ReviewGame(String wordCategory){
		currentCategoryInTesting=this.returnCurrentCategoryIndex(wordCategory);
		ArrayList<String> currentLevel=(ArrayList<String>) failedList.get(currentCategoryInTesting).clone();
		currentLevel.remove(0);
		if(currentLevel.size()==0){
			return false;
		}
		wordsTesting=new ArrayList<String>();
		String word=currentLevel.get(new Random().nextInt(currentLevel.size()));
		wordsTesting.add(word);

		int number=0;
		if(numberOfWordInGame>currentLevel.size()){
			number=currentLevel.size();
		}else{
			number=numberOfWordInGame;
		}

		for(int i =0;i<number-1;i++){
			word=currentLevel.get(new Random().nextInt(currentLevel.size()));
			while(wordsTesting.contains(word)){
				word=currentLevel.get(new Random().nextInt(currentLevel.size()));
			}
			wordsTesting.add(word);
		};

		return true;

	}

	/**
	 * return words user don't know in this category.
	 * @param category
	 * @return
	 */
	public ArrayList<String> learning(String category){
		return null;
	}

	/**
	 * be called in ClearStatistic window to clear a specific statistic.
	 */
	public void clearStatistics(String whichStatistics){
		if(whichStatistics.equals("All")){
			for(int i = 0; i<failedList.size();i++){
				ArrayList<String> currentFail = failedList.get(i);
				ArrayList<String> temp = new ArrayList<String>();
				temp=(ArrayList<String>) currentFail.clone();
				temp.remove(0);
				currentFail.removeAll(temp);

				ArrayList<String[]> currentHistory=history.get(i);
				ArrayList<String[]> tmp = new ArrayList<String[]>();
				tmp=(ArrayList<String[]>) currentHistory.clone();
				tmp.remove(0);
				currentHistory.removeAll(tmp); 

				int index=0;
				for(ArrayList<String> temp2: allWordlist){
					ArrayList<String> words=(ArrayList<String>) temp2.clone();
					words.add(0,failedList.get(index).get(0));
					wordDontKnow.remove(index);
					wordDontKnow.add(index, words);
					index++;
				}
			}
		}else{
			int index=this.returnCurrentCategoryIndex(whichStatistics);
			ArrayList<String> tmp=(ArrayList<String>) failedList.get(index).clone();
			tmp.remove(0);
			failedList.get(index).removeAll(tmp);

			ArrayList<String[]> temp=(ArrayList<String[]>) history.get(index).clone();
			temp.remove(0);
			history.get(index).removeAll(temp);

			ArrayList<String> temp2=(ArrayList<String>) allWordlist.get(index).clone();
			wordDontKnow.remove(index);
			temp2.add(0, whichStatistics);
			wordDontKnow.add(index, temp2);
		}
	}

	/**
	 * return a cloned DataRecorder.
	 * @return 
	 */
	public DataRecorder clone(){
		DataRecorder data=new DataRecorder();
		data.setAllWordlist((ArrayList<ArrayList<String>>) allWordlist.clone());
		data.setFailedList((ArrayList<ArrayList<String>>) failedList.clone());
		data.setHistory((ArrayList<ArrayList<String[]>>) history.clone());
		data.setWordlistCategory((ArrayList<ArrayList<String>>) wordlistCategory.clone());
		data.setWordListName((ArrayList<String>) wordListName.clone());
		data.setPath((ArrayList<String>) path.clone());
		data.setNumberOfWordInGame(numberOfWordInGame);
		data.setCurrentType(currentType);
		data.setWordDontKnow(wordDontKnow);
		return data;

	}

	/**
	 * get index of the first element need to be delete for this word list;
	 * @param nameOfWordList
	 * @return
	 */
	public int getFirstElement(String nameOfWordList){
		int index=wordListName.indexOf(nameOfWordList);
		int indexReturn=0;
		for(int i = 0;i<index;i++){
			indexReturn=indexReturn+wordlistCategory.get(i).size();
		}
		return indexReturn;
	}

	/**
	 * return the index of the category
	 * @param wordCategory
	 * @return
	 */
	public int returnCurrentCategoryIndex(String wordCategory){
		int index=0;

		for(int i=0;i<failedList.size();i++){
			ArrayList<String> tmp=failedList.get(i);
			if(tmp.get(0).equals(wordCategory)){
				index=i;
				break;
			}
		}
		return index;
	}

	/**
	 * speak out the sentence with certain volume
	 * @param input
	 * @param whichVoice
	 * @param speed
	 */
	public void festivalGenerator(String input, String whichVoice, String speed){
		vc.setSentence(input, whichVoice,speed);
		processing=true;
		String bashcmd = "festival -b "+currentWorkingDirectory+"/Image/sound.scm";		
		p = new ProcessBuilder("/bin/bash","-c", bashcmd);
		Processor processor=new Processor(this);
		processor.execute();
	}

	/**
	 * 	find out if the word already exsits in statistics
	 */
	public boolean checkIfExist(String word){
		for(String[] tmp:history.get(currentCategoryInTesting)){
			if(tmp[0].equals(word)){
				return true;
			}
		}
		return false;
	}

	/**
	 * make other quiz game class easier to get edit certain arraylist in failed list
	 * @return
	 */
	public ArrayList<String> getFailedListInTesting(){
		return failedList.get(currentCategoryInTesting);
	}

	/**
	 * similar to getFailedListInTesting but get history instead.
	 * @return
	 */
	public ArrayList<String[]> getStatisticsInTesting(){
		return history.get(currentCategoryInTesting);
	}


	/**
	 * load all words to wordDontKonwList for new word list added
	 * @param wordList
	 */
	public void loadWordDontKnow(String wordList){
		ArrayList<String> category = wordlistCategory.get(wordListName.indexOf(wordList));
		for(String tmp:category){
			ArrayList<String> current=wordDontKnow.get(this.returnCurrentCategoryIndex(tmp));
			current.addAll(allWordlist.get(this.returnCurrentCategoryIndex(tmp)));
		}
	}

}

class Processor extends SwingWorker<Void, Void>{
	private DataRecorder data;

	public Processor(DataRecorder data){
		this.data=data;
	}

	@Override
	protected Void doInBackground() throws Exception {
		data.p.start();
		Thread.sleep(1300);
		return null;
	}

	@Override
	protected void done(){
		data.processing=false;
	}

}


