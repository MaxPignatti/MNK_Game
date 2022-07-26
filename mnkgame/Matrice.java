package mnkgame;

public class Matrice {

    protected MNKCell[][] mat;
    protected MNKBoard B;

    public Matrice(MNKBoard B) {
        this.B = B;
        mat = new MNKCell[B.M][B.N];
    }

    public MNKCell[][] getMat () {
        return mat;
    }

    public void initMat() {
        for (MNKCell cell : B.getMarkedCells()) {
            mat[cell.i][cell.j] = cell;
        }
        for (MNKCell cell : B.getFreeCells()) {
            mat[cell.i][cell.j] = cell;
        }
    }

    public void ReadMatrice() {
        for (int i = 0; i < B.N; i++) {
            for (int j = 0; j < B.M; j++) {
                System.out.print(mat[j][i].state + " ");
            }
            System.out.print("\n");
        }
    }
}
