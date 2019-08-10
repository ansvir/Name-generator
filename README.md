# Name-generator
Simple name generator that uses random numbers, based on system nano time, and probabilities of each latin letter.

## Instruction:
1. Go to "settings" tab;
2. Fill next two fields after "Enter maximum lengths of the name and surename." with the lengths of the name and surename, respecively (integer number);
3. (Optional) Check the checkbox "Generate using certain lengths" to generate names and surenames using certain lengths that were set in the previous step;
4. (Optional) Set preferred probabilities of choosen letters in percent (fractional number) and push "Confirm";
5. Go to "generate" tab;
6. Push "Generate". If checkbox "Generate using certain lengths" is unchecked, names and surenames with the length from 2 to set lengths will be generated.
To set values of probabilities' values to default, go to "settings" tab, push "Default" and then "Confirm".

## Note
To avoid not exact result, when length of the name or surename is set to three or more, set probabilities for not more than 75%. Program creates names and surenames without triple consonants and vowels nearby, so it's impossible to generate such name as "Aaaa".

**For "NameGeneration" class users**:
to tell program take into account more or less signs after points of input values of probabilities, in the class find  method "returnRandCharOnTheBasisOfProbabilities" and set double value in the loop to preferred. At the default it's 100.0, so 10.0 means that exactness will be decreased to 0.1 from 0.01 and numbers after 0.0999... will not be taken into account.
