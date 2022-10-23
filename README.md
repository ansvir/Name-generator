# Name-generator
Simple name generator that uses random numbers, based on system nano time, and probabilities of each latin letter.

Java SE with JavaFX integration

## Desktop version
### Instruction:
1. Go to "settings" tab;
2. Fill next two fields after "Enter maximum lengths of the name and surname." with the lengths of the name and surname, respectively (integer number);
3. (Optional) Check the checkbox "Generate using certain lengths" to generate names and surnames using certain lengths that were set in the previous step;
4. (Optional) Set preferred probabilities of chosen letters in percent (fractional number) and push "Confirm";
5. Go to "generate" tab;
6. Push "Generate". If checkbox "Generate using certain lengths" is unchecked, names and surnames with the length from 2 to set lengths will be generated.
To set probabilities' values to default, go to "settings" tab, push "Default" and then "Confirm".

## Note
To avoid not exact result, when length of the name or surname is set to three or more, set probabilities for not more than 75%. Program creates names and surenames without triple consonants and vowels nearby, so it's impossible to generate such name as "Aaaa".

Feature generating names and surnames using parts currently is not implemented.

**For "org.itique.generator.core.NameGenerator" class users**:
to tell program take into account more or less signs after points of input values of probabilities, in the class find  method "returnRandCharOnTheBasisOfProbabilities" and set double value in the loop to preferred. At the default it's 10.0, so 100.0 means that exactness will be increased to 0.01 from 0.1 and two or fewer signs after point will be taken into account while generating.
