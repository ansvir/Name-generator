import java.util.Random;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NameGeneration {
	
	private char[] vowels= {'a', 'e', 'i','o','u','y'};		
	private char[] consonants= {'b','c','d','f','g','h','j','k','l','m','n','p','q','r','s','t','v','w','x','z'};
	private static char[] letters= {'a', 'b', 'c', 'd', 'e', 'f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	private static double[] probabilities = {8.0, 8.0, 5.0, 5.0, 6.0, 3.0, 2.0, 3.0, 3.0, 1.0, 3.0, 5.0, 6.0, 6.0, 7.0, 5.0, 1.0, 4.0, 5.0, 6.0, 1.0, 3.0, 1.0, 1.0, 1.0, 1.0}; 
	static final double[] DEFAULT_PROBABILITIES = {8.0, 8.0, 5.0, 5.0, 6.0, 3.0, 2.0, 3.0, 3.0, 1.0, 3.0, 5.0, 6.0, 6.0, 7.0, 5.0, 1.0, 4.0, 5.0, 6.0, 1.0, 3.0, 1.0, 1.0, 1.0, 1.0};
	static final int PROBABILITIES_LENGTH=26;
	static final double SUM_OF_PROBABILTIES=100.0;
	
	//next variables are used to control the amount of nearby double vowels and consonants
	//current amount of vowels and consonants in the name/surename, respectively
	private int amount_of_vowels, amount_of_consonants;
	//maximal value of vowels and consonants in the name or surename
	private final int MAX_VOWELS=2, MAX_CONSONANTS=2;
	
	NameGeneration() {
		
		amount_of_vowels=0;
		amount_of_consonants=0;

	}
	
	public static int returnIntRand(int start, int end) {
		
		int diff=end-start;
		Random r=new Random();
		int result=r.nextInt(diff+1);
		return result+=start;
		
	}
	
	public char returnRandCharOnTheBasisOfProbabilities() {
		
		List<Character> letters_list=new ArrayList<>();
		int integer_buffer, a=0;
		char char_buffer;
		
		for(int i=0;i<probabilities.length;i++) {
			for(double j=0.0; j<probabilities[i]*10.0; j+=1.0) { letters_list.add(letters[i]); if(letters[i]=='a') a++; }
		}
		
		System.out.print(a);
		System.out.println(letters_list);
		
		integer_buffer=returnIntRand(0, letters_list.size()-1);
		
		char_buffer=letters_list.get(integer_buffer);
		
		return char_buffer;
		
	}
	
	static public void setProbability(int i, double d) {
		probabilities[i]=d;
	}
	
	static public double getProbability(int i) {
		return probabilities[i];
	}
	
	public String generateNameAndSurename(int name_length, int surename_length, boolean length_is_selected) {
		
		//name and surename's variables
		String name="", surename="";
		
		//buffers for char and its code in ASCII table as integer
		char char_buffer;
		int integer_buffer;

		//if generate name and surename using random is selected generate lengths of random value in the range of max and min name and surename lengths
		if(length_is_selected==false) {
			integer_buffer=returnIntRand(2, name_length);
			name_length=integer_buffer;
			integer_buffer=returnIntRand(2, surename_length);
			surename_length=integer_buffer;
		}
		
		//start with filling name string with determining its first upper case letter
		char_buffer=Character.toUpperCase(returnRandCharOnTheBasisOfProbabilities());
		name+=char_buffer;
		
		for(int k=0;k<vowels.length;k++) {
			if(char_buffer==Character.toUpperCase(vowels[k])) {
				amount_of_vowels++;
				break;
			}
		}
		
		for(int k=0;k<consonants.length;k++) {
			if(char_buffer==Character.toUpperCase(consonants[k])) {
				amount_of_consonants++;
				break;
			}
		}
		
		//filling rest of the name
		for(int i=1;i<name_length;i++) {
				
			char_buffer=returnRandCharOnTheBasisOfProbabilities();
			
			for(int k=0;k<vowels.length;k++) {
				if(char_buffer==vowels[k]) {
					amount_of_vowels++;
					amount_of_consonants=0;
					break;
				}
			}
			
			for(int k=0;k<consonants.length;k++) {
				if(char_buffer==consonants[k]) {
					amount_of_consonants++;
					amount_of_vowels=0;
					break;
				}
			}
			
			//add current char to then name
			name+=char_buffer;
			
			//Exceptions
			//"if" to avoid double consonants in the surename with the length two
			if(name_length==2&&amount_of_consonants==2) {
				name=name.substring(0,name.length()-1);
				i--;
				amount_of_consonants-=1;
			}
			
			//"if" to avoid double similar vowels in the name with the length two
			if(name_length==2&&i==1&&name.charAt(i)==Character.toLowerCase(name.charAt(i-1))) {
					name=name.substring(0,name.length()-1);
					i--;
					amount_of_vowels-=1;
			}
			
			//"ifs" to avoid triple consonants or vowels in the name
			if(amount_of_vowels>MAX_VOWELS) {
				name=name.substring(0,name.length()-1);
				i--;
				amount_of_vowels-=1;
			}
			if(amount_of_consonants>MAX_CONSONANTS) {
				name=name.substring(0,name.length()-1);
				i--;
				amount_of_consonants-=1;
			}
			
			}
		
		//restoring amounts of vowels and consonants in the word variables
		amount_of_vowels=amount_of_consonants=0;
		
		//start with filling surename string with determining its first upper case letter
		char_buffer=Character.toUpperCase(returnRandCharOnTheBasisOfProbabilities());
		surename+=char_buffer;
				
		for(int k=0;k<vowels.length;k++) {
			if(char_buffer==Character.toUpperCase(vowels[k])) {
				amount_of_vowels++;
				break;
			}
		}
				
		for(int k=0;k<consonants.length;k++) {
			if(char_buffer==Character.toUpperCase(consonants[k])) {
				amount_of_consonants++;
				break;
			}
		}

		//filling rest of the surename
		for(int i=1;i<surename_length;i++) {
						
			char_buffer=returnRandCharOnTheBasisOfProbabilities();
					
			for(int k=0;k<vowels.length;k++) {
				if(char_buffer==vowels[k]) {
					amount_of_vowels++;
					amount_of_consonants=0;
					break;
				}
			}
					
			for(int k=0;k<consonants.length;k++) {
				if(char_buffer==consonants[k]) {
					amount_of_consonants++;
					amount_of_vowels=0;
					break;
				}
			}
				
			//add current char to the surename
			surename+=char_buffer;
			
			//"if" to avoid double consonants in the surename with the length two
			if(surename_length==2&&amount_of_consonants==2) {
				surename=surename.substring(0,surename.length()-1);
				i--;
				amount_of_consonants-=1;
			}
			
			//"if" to avoid double similar vowels in the surename with the length two
			if(surename_length==2&&i==1&&surename.charAt(i)==name.charAt(i-1)+32) {
					surename=surename.substring(0,surename.length()-1);
					i--;
					amount_of_vowels-=1;
			}

			//"ifs" to avoid triple consonants or vowels in the name
			if(amount_of_vowels>MAX_VOWELS) {
				surename=surename.substring(0,surename.length()-1);
				i--;
				amount_of_vowels-=1;
			}
			if(amount_of_consonants>MAX_CONSONANTS) {
				surename=surename.substring(0,surename.length()-1);
				i--;
				amount_of_consonants-=1;
			}
					
			}
		
		return name+" "+surename;
	}
}
