package Test;

import mnkgame.MNKCell;

public class mainTest {
    public static void main(String[] args) {
        MNKCell vuoto[] = new MNKCell[0];
        MNKCell toInsert = new MNKCell(2, 4);
        arrayOperations.insert(toInsert, vuoto);
    }
}
