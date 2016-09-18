package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.SwingWorker;

public class KeyphraseExtractorWorker extends SwingWorker<Void, Void>{

	private String path;
	private File pdf;
	private File output;
	private ArrayList<String> keywords = new ArrayList<String>();
	
	public KeyphraseExtractorWorker(String x, File file) {
		this.path = x;
		this.pdf = file;
		getOutput();
	}

	@Override
	protected Void doInBackground() throws Exception {

		ExtractKeywords extract = new ExtractKeywords();
		extract.setOptionsTesting(path);
		keywords = extract.extractKeyphrases();
		
		FileWriter fw = new FileWriter(output);

		for(String x: keywords){
			System.out.println(x);
			fw.write(x + "\n");
		}

		fw.close();
		
		return null;
	}

	private void getOutput() {

		try {
			
			String outputPath = Main.outputFolder.getAbsolutePath() + File.separator + pdf.getName().split("\\.")[0] + ".txt";

			output = new File(outputPath);
			if (!output.exists()) {
				output.createNewFile();
				
			} else {
				int i = 0;
				while (output.exists()) {
					i++;
					outputPath = Main.outputFolder.getAbsolutePath() + File.separator + pdf.getName().split("\\.")[0]
							+ "(" + i + ")" + ".txt";

					output = new File(outputPath);
				}
				output.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
