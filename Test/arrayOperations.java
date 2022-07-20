package Test;

import mnkgame.MNKCell;

public class arrayOperations {

    public static void insert (MNKCell toInsert, MNKCell array[]) {
        int pos = array.length + 1;
        array[pos] = toInsert;
    }

    public static void delete (MNKCell toDelete, MNKCell array[]) {
        boolean done = false;
        int counter = -1;
        while (!done) {
            counter++;
            if (array[counter].i == toDelete.i && array[counter].j == toDelete.j) 
                done = true;           
        }
        while (counter != array.length - 1) {
            array[counter] = array[counter + 1];
            counter++;
        }
        array[counter] = null;
    }
}
