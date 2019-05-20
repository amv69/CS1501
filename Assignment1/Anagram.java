import java.io.*;
import java.util.*;
public class Anagram{
	
	static TrieSTNew<String> dictionary = new TrieSTNew<>();
	
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
		List<String> solutions = new ArrayList<>();
		for(int i = 0; i < anagrams.length; i++) {
			//removing spaces
			if(anagrams[i].contains(" ")) anagrams[i].replaceAll("\\s", "");
			//Running main method
			solutions.addAll(search(anagrams[i].toCharArray(), new StringBuilder(), solutions, 0));
		}
		System.out.println(solutions.size());
	
	
	}
	
	public static List<String> search(char[] anagram, StringBuilder str, List<String> solutions, int begin) {
		for(int i = 0; i < anagram.length; i++) {
			str.append(anagram[begin]);
			System.out.println(str.toString());
			int val = dictionary.searchPrefix(str.toString());
			System.out.print(val);
			if(val == 1) search(anagram, str, solutions, begin + 1);
			if(val == 2) {
				if(!solutions.contains(str.toString()))solutions.add(str.toString());
			}
			if(val == 3) {
				if(!solutions.contains(str.toString())) {
					solutions.add(str.toString());
					search(anagram, str, solutions, begin + 1);
				}
				
			}
			if(val == 0) {
				search(anagram, str.deleteCharAt(str.length() - 1), solutions, begin++);
				break;
				
			}
		}
		return solutions;
	}
}
