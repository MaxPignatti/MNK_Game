package mnkgame;

public class Matrice {

    public Matrice() {
    }

    public MNKCell[][] initMat(MNKBoard B) {
        MNKCell[][] mat = new MNKCell[B.N][B.M];
        for (MNKCell cell : B.getMarkedCells()) {
            mat[cell.j][cell.i] = cell;
        }
        for (MNKCell cell : B.getFreeCells()) {
            mat[cell.j][cell.i] = cell;
        }
        return mat;
    }
}
