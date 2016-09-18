package wrapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;

import kea.stopwords.Stopwords;
import main.Main;

public class MyStopwords extends Stopwords{

	private static final long serialVersionUID = 1L;
	
	private static Hashtable m_Stopwords = null;

	static {
		
		if (m_Stopwords == null) {
			m_Stopwords = new Hashtable();
			Double dummy = new Double(0);
			File txt = Main.stopwords;	
			InputStreamReader is;
			String sw = null;
			try {
				is = new InputStreamReader(new FileInputStream(txt), "UTF-8");
				BufferedReader br = new BufferedReader(is);				
				while ((sw=br.readLine()) != null)  {
					m_Stopwords.put(sw, dummy); 
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	@Override
	public boolean isStopword(String str) {
		return m_Stopwords.containsKey(str.toLowerCase());
	}

	
}
