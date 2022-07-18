public class WordleCell {
    String bkgColor;
    char val;

    public WordleCell(){
        this.bkgColor = "\u001B[0m";
        this.val = ' ';
    }

    public WordleCell(char val, String bkgColor) {
        this.val = val;
        this.bkgColor = bkgColor;
    }

    @Override
    public String toString() {
        char divider = '|';
        return bkgColor + "  " + Character.toUpperCase(val) + "  " + "\u001B[0m";
    }
}
