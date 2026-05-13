import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * This class creates a dictionary for the Hangman game.
 * It reads in data from dictionary.txt and manages a data structure of words for Hangman
 *
 * @author
 */
public class Dictionary {
    private ArrayList<String> words;
    private int length;
    public Dictionary(int l) throws FileNotFoundException {
        length = l;
        words = new ArrayList<>();
        Scanner sc = new Scanner(new File("dictionary.txt"));
        while (sc.hasNext()) {
            String word = sc.next();
            if (word.length() == length) {
                words.add(word);
            }
        }

    }

    public String getWord(int index) {
        return words.get(index);
    }

    public ArrayList<String> getWordList() {
        return words;
    }

    public void shrinkList(String letter) {
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        int index = 0;
        while (!words.isEmpty()){
            list.add(new ArrayList<>());
            list.get(index).add(words.remove(0));
            LinkedList<Integer> comps = findIndexes(letter ,list.get(index).get(0));
            for (int i = words.size() - 1; i >= 0; i--) {
                LinkedList<Integer> temp = findIndexes(letter, words.get(i));
                if (temp.isEmpty() && comps.isEmpty()) {
                    list.get(index).add(words.remove(i));
                }else if (temp.size() == comps.size()) {
                    int count = 0;
                    for (int j = 0; j < temp.size(); j++) {
                        if (temp.get(j).equals(comps.get(j))) {
                            count++;
                        }
                    }
                    if (count == comps.size()) {
                        list.get(index).add(words.remove(i));
                    }
                }
            }
            index++;
        }
        int biggestGroup = 0;
        for (int i = 0; i < list.size(); i++) {
            if  (list.get(i).size() > list.get(biggestGroup).size()) {
                biggestGroup = i;
            }
        }
        words = list.get(biggestGroup);
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
