import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * This class represents a game of Hangman
 */
public class Hangman {
    private Dictionary dict;
    private boolean cheating = true;
    private boolean showWords = false;
    private boolean finished = false;
    private int guessAmount;
    private String finalWord = "";
    private String chosenWord;

    public Hangman(int length, int guess) throws FileNotFoundException {
        dict = new Dictionary(length);
        guessAmount = guess;

        while(length > 0){
            finalWord += "- ";
            length--;
        }
    }

    public int guessesRemaining() {
        return guessAmount;
    }

    public boolean getIsFinished(){
        return finished;
    }

    public void isCheating() {
        cheating = !cheating;
        chosenWord = dict.getWordList().get((int)(Math.random() * dict.getWordList().size()));
    }

    public void isShowWords() {
        showWords = !showWords;
    }

    public void guess(String letter) {
        String currentDisplay = finalWord;
        finalWord = "";
        if(cheating) {
            dict.shrinkList(letter);
            ArrayList<String> words = dict.getWordList();
            if (words.size() == 1) {
                System.out.println("You guessed the word: " + words.get(0));
                finished = true;

            } else {
                LinkedList<Integer> indexes = findIndexes(letter, words.get(0));
                if (!indexes.isEmpty()) {
                    System.out.println("You got a letter!");
                }
                for (int i = 0; i < words.get(0).length(); i++) {
                    if (indexes.contains(i)) {
                        finalWord += letter + " ";
                    } else if(currentDisplay.charAt(i * 2) != '-'){
                        finalWord += finalWord.charAt(i * 2) + " ";
                    }else{
                        finalWord += "- ";
                    }
                }
            }
        }else{
            setFinalWord(letter, chosenWord, currentDisplay);
            if(!finalWord.contains("-")){
                finished = true;
                System.out.println("You guessed the word: " + chosenWord);
            }
        }
        guessAmount--;
    }

    public String toString() {
        String output = finalWord + "\n";
        output += "Guesses remaining: " + guessesRemaining() + "\n";
        return output;
    }

    private void setFinalWord(String letter, String word, String currentDisplay) {
        LinkedList<Integer> indexes = findIndexes(letter, word);
        if (!indexes.isEmpty()) {
            System.out.println("You got a letter!");
        }
        for (int i = 0; i < word.length(); i++) {
            if (indexes.contains(i)) {
                finalWord += letter + " ";
            } else if(currentDisplay.charAt(i * 2) != '-'){
                finalWord += finalWord.charAt(i * 2) + " ";
            }else{
                finalWord += "- ";
            }
        }
    }



    private LinkedList<Integer> findIndexes(String letter, String word) {
        if (!word.contains(letter)) {
            return new LinkedList<>();
        }
        LinkedList<Integer> indexes = new LinkedList<>();
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letter.charAt(0)) {
                indexes.add(i);
            }
        }
        return indexes;
    }
}
