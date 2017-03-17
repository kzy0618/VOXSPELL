package Helper;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class VoiceSpeaker {

	private String voice="kal_diphone";
	private String word = "";
	private File scm=new File("Image/.sound.scm");
	private String sentenceToBeGenerated;

	private static VoiceSpeaker vc = null;

	private VoiceSpeaker(){}

	public static VoiceSpeaker getInstance(){
		if (vc == null){
			vc = new VoiceSpeaker();
			return vc;
		}else{
			return vc;
		}
	}

	/**
	 * set sentence with specific voice and certain speaking speed
	 * @param sentence
	 * @param whichVoice
	 * @param speed
	 */
	public void setSentence(String sentence,String whichVoice, String speed){
		voice="(voice_"+whichVoice+")";
		if(speed.equals("slow")){
			sentenceToBeGenerated=voice+"\n(Parameter.set 'Duration_Stretch 1.8)";
		}else if(speed.equals("normal")){
			sentenceToBeGenerated=voice+"\n(Parameter.set 'Duration_Stretch 1)";
		}else{
			sentenceToBeGenerated=voice+"\n(Parameter.set 'Duration_Stretch 0.8)";
		}

		sentenceToBeGenerated=sentenceToBeGenerated+"\n(SayText \""+sentence+"\")\n";
		updateSCM();
	}

	/**
	 * update scm file
	 */
	private void updateSCM(){
		FileWriter writer;
		try {
			writer = new FileWriter(scm, false);
			writer.write(sentenceToBeGenerated);
			writer.close();
		} catch (IOException e) {
			JFrame jf = new JFrame();
			JOptionPane op = new JOptionPane("Fatal error : IOException");
			jf.setLocationRelativeTo(null);
			jf.setLayout(new BorderLayout());
			jf.add(op, BorderLayout.SOUTH);
			op.setVisible(true);
			jf.setVisible(true);
		}
	}
}
