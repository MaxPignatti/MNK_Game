package mnkgame;

public class Matrice {

    public Matrice() {
    }

    public MNKCell[][] initMat(MNKBoard B) {
        MNKCell[][] mat = new MNKCell[B.M][B.N];
        for (MNKCell cell : B.getMarkedCells()) {
            mat[cell.i][cell.j] = cell;
        }
        for (MNKCell cell : B.getFreeCells()) {
            mat[cell.i][cell.j] = cell;
        }
        return mat;
    }

    public void ReadMatrice(MNKBoard B, MNKCell mat[][]) {
        for (int i = 0; i < B.N; i++) {
            for (int j = 0; j < B.M; j++) {
                System.out.print(mat[j][i].state + " ");
            }
            System.out.print("\n");
        }
    }
}
