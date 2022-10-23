package org.itique.generator.core;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

import static org.itique.generator.core.Constant.consonants;
import static org.itique.generator.core.Constant.letters;
import static org.itique.generator.core.Constant.vowels;

/**
 * Main name generator class that encapsulates logic of generating random names nad surnames
 */
public class NameGenerator {

    private static double[] probabilities = {8.0, 8.0, 5.0, 5.0, 6.0, 3.0, 2.0, 3.0, 3.0, 1.0, 3.0, 5.0, 6.0, 6.0, 7.0, 5.0, 1.0, 4.0, 5.0, 6.0, 1.0, 3.0, 1.0, 1.0, 1.0, 1.0};
    private static String[] parts = {"", "", "", ""};
    public static final double[] DEFAULT_PROBABILITIES = {8.0, 8.0, 5.0, 5.0, 6.0, 3.0, 2.0, 3.0, 3.0, 1.0, 3.0, 5.0, 6.0, 6.0, 7.0, 5.0, 1.0, 4.0, 5.0, 6.0, 1.0, 3.0, 1.0, 1.0, 1.0, 1.0};


    //next variables are used to control the amount of nearby double vowels and consonants
    //current amount of vowels and consonants in the name/surname, respectively
    private int amount_of_vowels, amount_of_consonants;
    //maximal value of vowels and consonants in the name or surname
    private final int MAX_VOWELS = 2, MAX_CONSONANTS = 2;

    public NameGenerator() {

        amount_of_vowels = 0;
        amount_of_consonants = 0;

    }

    public static int returnIntRand(int start, int end) {

        int diff = end - start;
        Random r = new Random();
        int result = r.nextInt(diff + 1);
        return result += start;

    }

    public char returnRandCharOnTheBasisOfProbabilities() {

        List<Character> letters_list = new ArrayList<>();
        int integer_buffer, a = 0;
        char char_buffer;

        for (int i = 0; i < probabilities.length; i++) {
            for (double j = 0.0; j < probabilities[i] * 10.0; j += 1.0) {
                letters_list.add(letters[i]);
                if (letters[i] == 'a') a++;
            }
        }

        integer_buffer = returnIntRand(0, letters_list.size() - 1);

        char_buffer = letters_list.get(integer_buffer);

        return char_buffer;

    }

    static public void setProbability(int i, double d) {
        probabilities[i] = d;
    }

    static public double getProbability(int i) {
        return probabilities[i];
    }

    static public void setPart(int i, String s) {
        parts[i] = s;
    }

    static public String getPart(int i) {
        return parts[i];
    }

    public String generateNameAndSurname(int name_length, int surname_length, boolean length_is_selected, boolean parts_is_selected) {

        //name and surname's variables
        String name = "", surname = "";
        //variables that define the minimum length of the name and surname
        int name_start_length = 2, surname_start_length = 2;

        if (parts_is_selected) {
            name = parts[0];
            name_start_length = parts[0].length();
            surname = parts[1];
            surname_start_length = parts[1].length();
        }
		/*
		//if generating using parts of the name and surname is selected, create variables that contain that parts
		if(parts_is_selected==true) {
			String name_start=parts[0], surname_start=parts[1], name_end=parts[2], surname_end=parts[3];
		}
		*/

        //buffers for char and its code in ASCII table as integer
        char char_buffer;
        int integer_buffer;

        //if generate name and surname using random is selected generate lengths of random value in the range of max and min name and surname lengths
        if (!length_is_selected) {
            integer_buffer = returnIntRand(2, name_length);
            name_length = integer_buffer;
            integer_buffer = returnIntRand(2, surname_length);
            surname_length = integer_buffer;
        }

        //filling the name
        for (int i = 0; i < name_length; i++) {

            char_buffer = returnRandCharOnTheBasisOfProbabilities();

            for (int k = 0; k < vowels.length; k++) {
                if (char_buffer == vowels[k]) {
                    amount_of_vowels++;
                    amount_of_consonants = 0;
                    break;
                }
            }

            for (int k = 0; k < consonants.length; k++) {
                if (char_buffer == consonants[k]) {
                    amount_of_consonants++;
                    amount_of_vowels = 0;
                    break;
                }
            }

            //add current char to then name
            if (i == 0) {
                name += Character.toUpperCase(char_buffer);
            } else name += char_buffer;


            //Exceptions
            //"if" to avoid double consonants in the surname with the length two
            if (name_length == 2 && amount_of_consonants == 2) {
                name = name.substring(0, name.length() - 1);
                i--;
                amount_of_consonants -= 1;
            }

            //"if" to avoid double similar vowels in the name with the length two
            if (name_length == 2 && i == 1 && name.charAt(i) == Character.toLowerCase(name.charAt(i - 1))) {
                name = name.substring(0, name.length() - 1);
                i--;
                amount_of_vowels -= 1;
            }

            //"ifs" to avoid triple consonants or vowels in the name
            if (amount_of_vowels > MAX_VOWELS) {
                name = name.substring(0, name.length() - 1);
                i--;
                amount_of_vowels -= 1;
            }
            if (amount_of_consonants > MAX_CONSONANTS) {
                name = name.substring(0, name.length() - 1);
                i--;
                amount_of_consonants -= 1;
            }
        }

        //restoring amounts of vowels and consonants in the word to zero
        amount_of_vowels = amount_of_consonants = 0;

        //filling the surname
        for (int i = 0; i < surname_length; i++) {

            char_buffer = returnRandCharOnTheBasisOfProbabilities();

            for (int k = 0; k < vowels.length; k++) {
                if (char_buffer == vowels[k]) {
                    amount_of_vowels++;
                    amount_of_consonants = 0;
                    break;
                }
            }

            for (int k = 0; k < consonants.length; k++) {
                if (char_buffer == consonants[k]) {
                    amount_of_consonants++;
                    amount_of_vowels = 0;
                    break;
                }
            }

            //add current char to the surname
            if (i == 0) {
                surname += Character.toUpperCase(char_buffer);
            } else surname += char_buffer;

            //"if" to avoid double consonants in the surname with the length two
            if (surname_length == 2 && amount_of_consonants == 2) {
                surname = surname.substring(0, surname.length() - 1);
                i--;
                amount_of_consonants -= 1;
            }

            //"if" to avoid double similar vowels in the surname with the length two
            if (surname_length == 2 && i == 1 && surname.charAt(i) == Character.toLowerCase(name.charAt(i - 1))) {
                surname = surname.substring(0, surname.length() - 1);
                i--;
                amount_of_vowels -= 1;
            }

            //"ifs" to avoid triple consonants or vowels in the name
            if (amount_of_vowels > MAX_VOWELS) {
                surname = surname.substring(0, surname.length() - 1);
                i--;
                amount_of_vowels -= 1;
            }
            if (amount_of_consonants > MAX_CONSONANTS) {
                surname = surname.substring(0, surname.length() - 1);
                i--;
                amount_of_consonants -= 1;
            }

        }

        return name + " " + surname;
    }
}
