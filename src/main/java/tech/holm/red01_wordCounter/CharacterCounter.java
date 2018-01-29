package tech.holm.red01_wordCounter;

public class CharacterCounter {
    private char character;
    private int count;

    public CharacterCounter(char character) {
        this.character = character;
        this.count = 0;
    }

    public void countOneUp(){
        count++;
    }

    public char getCharacter() {
        return character;
    }

    public int getCount() {
        return count;
    }

}
