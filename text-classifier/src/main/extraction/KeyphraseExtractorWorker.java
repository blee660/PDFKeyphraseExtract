package main.extraction;

import java.io.File;
import java.io.IOException;

import javax.swing.SwingWorker;

public class KeyphraseExtractorWorker extends SwingWorker<Void, Void>{

	private String path;
	private File pdf;
	private File output;
	
	public KeyphraseExtractorWorker(String x, File file) {
		this.path = x;
		this.pdf = file;
		getOutput();
	}

	@Override
	protected Void doInBackground() throws Exception {

		ExtractKeywords extract = new ExtractKeywords();
		extract.setOptionsTesting(path);
		extract.extractKeyphrases();

		return null;
	}

	private void getOutput() {

		try {
			
			String outputPath = Main.outputFolder.getAbsolutePath() + File.separator + pdf.getName().split("\\.")[0] + ".txt";

			System.out.println(outputPath);
			output = new File(outputPath);
			if (!output.exists()) {
				System.out.println(output.createNewFile());
				
			} else {
				int i = 0;
				while (output.exists()) {
					i++;
					outputPath = Main.outputFolder.getAbsolutePath() + File.separator + pdf.getName().split("\\.")[0]
							+ "(" + i + ")" + ".txt";

					output = new File(outputPath);
				}

				output.createNewFile();
				
				output.deleteOnExit();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
