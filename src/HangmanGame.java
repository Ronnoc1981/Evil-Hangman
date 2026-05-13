import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This class runs a game of Hangman using the Hangman class.
 * It is responsible for the console program that players use to interact with the game.
 *
 * @author
 */
public class HangmanGame {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Hangman! \nPlay? (y/n)");
        String answer = sc.nextLine();
        if(answer.equals("y")){
            while (true){
                System.out.println("How long you want the word to be: ");
                int len = sc.nextInt();
                System.out.println("How many guesses do you want?: ");
                int guesses = sc.nextInt();
                Hangman game = new Hangman(len, guesses);
                System.out.print("Do you want a fair game? (y/n): ");
                answer = sc.next();
                if(answer.equals("y")){
                    game.isCheating();
                }
                if(answer.equals("n")){
                    System.out.println("Would you like to see the word list? (y/n): ");
                    answer = sc.next();
                    if(answer.equals("y")){
                        game.isShowWords();
                    }
                }
                System.out.println("Lets Start!");
                while(true){
                    System.out.println(game);
                    System.out.print("Guess: ");
                    String guess = sc.next();
                    game.guess(guess);
                    if(game.getIsFinished() || game.guessesRemaining() == 0){
                        break;
                    }
                }
                System.out.println("Play again? (y/n): ");
                answer = sc.next();
                if(answer.equals("n")){
                    break;
                }
            }
        }
        sc.close();
        System.exit(0);

    }
}