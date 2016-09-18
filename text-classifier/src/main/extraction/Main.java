package main.extraction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Main {

	private static HashMap<String, File> inputFiles = new HashMap<String, File>();
	public static File outputFolder;

	public static File stopwords;
	public static File model;
	public static File agro;

	public static void main(String[] args) {

		// receiving and processing the input arguments from the command line
		if (args.length < 2) {
			printHelp();
			return;
		}

		processArgs(args);
		if (outputFolder.isFile()) {
			printHelp();
			return;
		}

		generateResources();
		// retrieving the list paths
		Set<String> keys = inputFiles.keySet();

		// creating a list to store all the processes.
		ArrayList<KeyphraseExtractorWorker> kewList = new ArrayList<KeyphraseExtractorWorker>();

		// starting a worker for each input file
		for (String x : keys) {
			KeyphraseExtractorWorker kew = new KeyphraseExtractorWorker(x, inputFiles.get(x));
			kewList.add(kew);
			kew.execute();
		}

		// Waiting for all workers to complete
		while (!kewList.isEmpty()) {
			ArrayList<KeyphraseExtractorWorker> removeList = new ArrayList<KeyphraseExtractorWorker>();
			for (KeyphraseExtractorWorker kew : kewList) {
				if (kew.isDone()) {
					removeList.add(kew);
				}
			}

			for (KeyphraseExtractorWorker kew : removeList) {
				kewList.remove(kew);
			}
		}

	}

	private static void generateResources() {	
		stopwords = generateResource("/resources/stopwords.txt", outputFolder.getPath() +File.separator+"stopwords.txt");
		model = generateResource("/resources/model", outputFolder.getPath() +File.separator+"model");
		agro = generateResource("/resources/agro.rdf", outputFolder.getPath() +File.separator+"agro.rdf");

	}

	/**
	 * generating the default file to the target location.
	 */
	private static File generateResource(String input, String output) {
		File f = new File(output);

		// check to see if file already exists
		if (f.exists()) {
			return f;
		}

		try {
			f.createNewFile();

			// Copying the resource xsl to the new file
			InputStream is = Main.class.getResourceAsStream(input);
			OutputStream os = new FileOutputStream(f);

			int read = 0;

			byte[] bytes = new byte[1024];

			while ((read = is.read(bytes)) != -1) {
				os.write(bytes, 0, read);
			}

			os.close();
			is.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return f;
	}

	/**
	 * Processing the command line arguments provided. Checks to ensure the
	 * input files and output folder are valid.
	 * 
	 * @param args
	 */
	private static void processArgs(String[] args) {
		File outTemp = new File(args[args.length - 1]);
		setOutput(outTemp);

		for (int i = 0; i < args.length - 1; i++) {
			addInput(args[i]);
		}
	}

	private static void setOutput(File outFolder) {
		if (outFolder.isFile()) {
			System.out.println("Invalid output directory");
			printHelp();
			return;
		}
		outputFolder = outFolder;
		if (!outputFolder.exists()) {
			System.out.println(outputFolder.mkdir());
		}

	}

	private static void addInput(String path) {
		File y = new File(path);
		if (!y.isFile()) {
			System.out.println("Invalid input file: " + path);
			return;
		}

		inputFiles.put(path, y);
	}

	/**
	 * Method to display how to use this jar file. Displayed when wrong input
	 * provided
	 */
	private static void printHelp() {
		System.out.println("To execute this jar please follow following:");
		System.out.println("Run the jar with a list of input files separated by a space followed by an output folder");
		System.out.println(
				"Example: java -jar PDFExtractPrototype.jar example.pdf example1.pdf example2.pdf outputFolder");
		return;
	}
}
