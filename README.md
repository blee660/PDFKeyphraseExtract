# PDFKeyphraseExtract

##Requirements
You will require the following conditions to run the program:
- JVM installed on the machine
- CLI tool to execute the jar

##For End Users
The built standalone jar file is able to extract bibliography items from a specified PDF document or a specified list. You can perform this task by typing the following command into a CLI program:

    java -jar [name].jar [input files] [output folder]

where you replace
- name with the name of the standalone jar file
- input files with the paths to the list of files you wish to extract keywords from, each separated with a space. Such as "C:/users/file1.pdf C:/users/file2.pdf"
- output folder with the path to the directory you wish to store the output

It will store the extracted keywords in a file with the name of the corresponding document.

##For Developers
This code functions by using the KEA Keyphrase Extraction Algorithm tool.
It has modified the KEA tool to remove the model building component, and to change the type of input it works with.
We have also changed the SKOS file to use ACMs digital computing classification SKOS file, and this can be interchanged with any other correctly formatted SKOS file from different domains depending on the type of text content is being processed.
This tool works by reading each word from the text content from the input files, compares it against a provided SKOS file, and extracts the most relevant keyphrases.

The source code is explained below:

###Package - main
Responsible for starting and running the keyphrase extractor classes.

**Main class:**
- entry point to the program
- parses the parameters provided
- provides help messages if input parameters are incorrect
- creates a local version of the resources required (stopwords, model, SKOS file)
- starts and handles all of the tasks running in background

**KeyphraseExtractorWorker class:**
- processes each pdf input
- extracts keyphrases from input
- prints extracted keyphrases into an output file, and also to console (which is read by GUI application)

**KeyphraseExtractor class:**
- handles keyphrase extraction logic
- extracts keyphrases based on provided model and SKOS file, ignoring any words in the stopword file
- takes user specified fields as input (model name, file name, etc.) for extraction process
- prints extracted keyphrases into an output file, and also to console (which is read by GUI application)

###Package - wrapper
Contains modification of KEA's Stopwords class to instead use the provided stopwords file in the resources folder.

###Package - resources
Contains all the external resources required for keyphrase extraction

**compvoc.rdf**
- SKOS file containing hierarchy of keywords from the ACM Digital Computing Classification system
- keyphrases are extracted by comparing words from the text with keywords in the SKOS file
- This file can be replaced with other SKOS files as long as they have the correct format

**model**
- this tool extracts keyphrases based on this model which contains trained data from a large number of publications

**stopwords.txt**
- file containing all the words to be ignored during extraction (e.g. and, the, they, etc.)

