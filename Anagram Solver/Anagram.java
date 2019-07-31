import java.io.*;
import java.util.*;
public class Anagram{
	
	static TrieSTNew<String> dictionary = new TrieSTNew<>();
	static List<String> solutions = new ArrayList<>();
	static List<String> solutions2 = new ArrayList<>();
	
	
	public static void main(String[] args) throws FileNotFoundException{
		
		//Reading File names
		String inputFile = args[0];
		String outputFile = args[1];
		File dict = new File("dictionary.txt");
		File input = new File(inputFile);
		File output = new File(outputFile);
		Scanner sc = new Scanner(dict);
		
		//Loop through the dictionary to add all the values 
		while (sc.hasNext()) {
			String word = sc.nextLine();
			dictionary.put(word, word);
		}
		sc.close();
		
		//Use a scanner to find out size of input to create the anagram array just for size efficiency
		Scanner scTemp = new Scanner(input);
		int inputLines = 0;
		while(scTemp.hasNext()) {
			inputLines++;
			scTemp.nextLine();
		}
		scTemp.close();
		
		//Create and fill the array with the anagrams
		String anagrams[] = new String[inputLines];
		Scanner scInput = new Scanner(input);
		for(int i = 0; i < inputLines; i++) {
			anagrams[i] = scInput.nextLine();
		}
		scInput.close();
		
		int temp = 0;
		for(int i = 0; i < anagrams.length; i++) {
			//removing spaces
			if(anagrams[i].contains(" ")) anagrams[i].replaceAll("\\s", "");
			//Running main method
			search(anagrams[i].toCharArray(), new StringBuilder(), 0, anagrams[i]);
			
			
			
			
		}
		
		try {
			FileWriter fw = new FileWriter(outputFile);
			for(int j = 0; j < anagrams.length; j++) {
				fw.write("Here are the results for " + "'" + anagrams[j] + "'");
				for (int k = 0; k < solutions.size(); k++){
					if(solutions2.get(k).contains(anagrams[j])) {
						int len = anagrams[j].length();
						String ans = (String) solutions2.get(k).subSequence(0, solutions2.get(k).length() - len);
						fw.write(ans);
					}
				}
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
	}
	
	public static void search(char[] anagram, StringBuilder str, int begin, String word) {
			int val = dictionary.searchPrefix(str.toString());
			//If a prefix of a word
			if(val == 1 || val ==3) {
				//Loop through the char list
				for(int i = 0; i < anagram.length; i++) {
					//make new char[] of size one less than current iteration
					char chars[] = new char[anagram.length - 1];
					//Make new string with added char taken from []
					StringBuilder str2 = new StringBuilder(str.toString());
					str2.append(anagram[i]);
					//temp int as placeholder
					int temp = 0;
	
					for(int j = 0; j < anagram.length; j++) {
						if(j == i) continue;
						chars[temp] = anagram[j];
						temp++;
					}
					search(chars, str2, begin+1, word);
				}
			}
			//If sol'n add to Solutions List
			if((val == 2 || val == 3) && anagram.length == 0) {
				if(!solutions.contains(str.toString())) {
					//work around to store the anagram name with the solved anagram
					StringBuilder temp = new StringBuilder(str);
					temp.append(word);
					solutions2.add(temp.toString());
					//need both for testing if solution is contained logic to work
					solutions.add(str.toString());
					
					
				}
			}
			//Failed attempt at multiword anagrams
			/*if(val == 2 && anagram.length != 0){
				System.out.println(str.toString());
				for(int i = 0; i < anagram.length; i++) {
					char chars[] = new char[anagram.length - 1];
					StringBuilder str2 = new StringBuilder(str.toString());
					int temp = 0;
					
					str2.append(anagram[i]);
					for(int j = 0; j < anagram.length; j++) {
						if(j == i) continue;
						chars[temp] = anagram[j];
						temp++;
					}
					search(chars, str2.append(" "), begin+1);
				}
			}  */
			//if nothing just break
			if(val == 0) {
				
				return;
			}
			
			return;
	}
	
	
}
