package main;

import java.util.ArrayList;
import kea.stemmers.PorterStemmer;
import wrapper.MyStopwords;

/**
 * Class to set all options for extraction of keyphrases 
 * 
 * Calls extraction method in main method on the specified file
 *
 */
public class ExtractKeywords {

	private KeyphraseExtractor ke;
	
	public void setOptionsTesting(String m_fileName) {
		
		ke = new KeyphraseExtractor();
		
		// 1. Name of the file -- give the path to the file
		ke.setFileName(m_fileName);
		
		// 2. Name of the model -- give the path to the model 
		ke.setModelName(Main.model.getPath());
		 
		// 3. Name of the vocabulary -- name of the file (without extension) that is stored in VOCABULARIES
		//    or "none" if no Vocabulary is used (free keyphrase extraction).
		ke.setVocabulary("");
		
		// 4. Format of the vocabulary in 3. Leave empty if vocabulary = "none", use "skos" or "txt" otherwise.
		ke.setVocabularyFormat("skos");
		
		// B. optional arguments if you want to change the defaults
		
		// 5. Encoding of the document
		ke.setEncoding("UTF-8");
		
		// 6. Language of the document -- use "es" for Spanish, "fr" for French
		//    or other languages as specified in your "skos" vocabulary 
		ke.setDocumentLanguage("en"); // es for Spanish, fr for French
		
		// 7. Stemmer -- adjust if you use a different language than English or want to alterate results
		// (We have obtained better results for Spanish and French with NoStemmer)
		ke.setStemmer(new PorterStemmer());
		
		// 8. Stopwords
		MyStopwords sw = new MyStopwords();
		ke.setStopwords(sw);
		
		// 9. Number of Keyphrases to extract
		ke.setNumPhrases(5);
		
		// 10. Set to true, if you want to compute global dictionaries from the test collection
		ke.setBuildGlobal(false);			
	}

	public ArrayList<String> extractKeyphrases() {
		ArrayList<String> list = new ArrayList<String>();
		try {
			// load model for extraction
			ke.loadModel();

			// extract keyphrases
			list = ke.extractKeyphrases(ke.collectStems(ke.m_fileName));
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
}

