public class WordleBoard
{
    private final int rows, cols;
    private final String word;
    private int playRow;

    private WordleCell[][] board;

    public static final String RED_BKG = "\u001B[41m";
    public static final String GREEN_BKG = "\u001B[42;1m";
    public static final String YELLOW_BKG = "\u001B[43;1m";
    public static final String PURPLE_BKG = "\033[95m";
    public static final String BLANK_BKG = "\u001b[0m";

    public WordleBoard(int rows, int cols, String word) {
        this.rows = rows;
        this.cols = cols;
        this.word = word;
        this.playRow = 0;
        assert word.length() == cols: "Word char count doesn't match number of columns in board";
        board = new WordleCell[rows][cols];
        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = new WordleCell();
            }
        }
    }

    public void show() throws InterruptedException {
        show(-1);
    }

    // shows current state of board and optionally animates a row
    private void show(int animateRow) throws InterruptedException {
        for(int i = 0; i < rows; i++) {
            printDivider();
            for (int j = 0; j < cols; j++) {
                if(i == animateRow) {
                    Thread.sleep(500);
                }
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
        printDivider();
    }

    // updates board with valid guess chars and colors
    public void guess(String guess) throws InterruptedException {
        assert hasSpace() : "Board is full, no more guesses!";
        guess = guess.toLowerCase();
        WordleCell[] guessRow = board[playRow];
        // update cells with char and bkg color
        for(int i = 0; i < guessRow.length; i++) {
            char val = guess.charAt(i);
            String bkgColor = cellBkgColor(val, i);
            guessRow[i] = new WordleCell(val, bkgColor);
        }
        show(playRow);
        playRow++;
    }

    // calculates the hint color of a char given its position in the guess word
    private String cellBkgColor(char userChar, int i) {
        if(userChar == word.charAt(i)) {
            return GREEN_BKG;
        }
        if(word.contains((String.valueOf(userChar)))) {
            return YELLOW_BKG;
        }
        return BLANK_BKG;
    }

    private void printDivider() {
        for(int i = 0; i < rows * 4; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    public boolean hasSpace() {
        return playRow < rows;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public String getWord() {
        return word;
    }
}
