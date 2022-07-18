import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Wordle {
    private static final String EXIT = ".exit";
    private static final String A_Z_REGEX = "[a-zA-Z]+";
    static WordleBoard board;
    static Set<String> words;
    public static void main(String[] args) throws Exception {
        Random picker = new Random();
        Scanner kbReader = new Scanner(System.in);
        boolean guessed = false;

        List<String> fileWords = readFile();
        words = new HashSet<>(fileWords);
        board = new WordleBoard(6, 5, fileWords.get(picker.nextInt(words.size())));
        System.out.println(board.getWord());
        System.out.println("Welcome to Wordle! \n Yellow means the letter is in the word, but in the wrong spot. \n Green means the letter is in the word and in the correct spot. \n Type .exit to quit");
        board.show();

        // play game while board has space and the user hasn't guessed the word
        while(board.hasSpace() && !guessed) {
            String validGuess = getValidGuess(kbReader);
            if(validGuess.equals(EXIT)) {
                break;
            }
            board.guess(validGuess.toUpperCase());
            guessed = validGuess.equalsIgnoreCase(board.getWord());
        }

        if(guessed) {
            System.out.println("Nice job, you won!");
        } else {
            System.out.println("Game over. The word was: " + board.getWord());
        }
    }

    public static List<String> readFile() throws FileNotFoundException {
        List<String> result = new ArrayList<>();
        Scanner fileReader = new Scanner(new File("words.txt"));
        while(fileReader.hasNext()) {
            result.add(fileReader.nextLine());
        }
        return result;
    }

    // Calls itself repeatedly until a valid guess is provided by the user
    private static String getValidGuess(Scanner kbReader) {
        System.out.println("Guess: ");
        String guess = kbReader.nextLine();
        if(guess.equals(EXIT)) {
            return EXIT;
        }
        if(!guess.matches(A_Z_REGEX)) {
            System.out.println("Invalid input character(s)");
            return getValidGuess(kbReader);
        }
        if(guess.length() < board.getCols()) {
            System.out.println("Too few letters, try again");
            return getValidGuess(kbReader);
        }
        if(guess.length() > board.getCols()) {
            System.out.println("Too many letters, try again");
            return getValidGuess(kbReader);
        }
        if(!words.contains(guess)) {
            System.out.println("Invalid word, try again");
            return getValidGuess(kbReader);
        }
        return guess;
    }
}