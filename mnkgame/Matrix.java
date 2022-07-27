package mnkgame;

public class Matrix {

    protected MNKCell[][] mat;
    protected MNKBoard B;
    protected MNKCell[] FC;
    protected MNKCell[] MC;

    public Matrix(MNKBoard B, MNKCell[] FC, MNKCell[] MC) {
        this.B = B;
        this.FC = FC;
        this.MC = MC;
        mat = new MNKCell[B.M][B.N];
    }

    public MNKCell[][] getMatrix () {
        return mat;
    }

    public void initMatrix() {
        for (MNKCell cell : MC) {
            mat[cell.i][cell.j] = cell;
        }
        for (MNKCell cell : FC) {
            mat[cell.i][cell.j] = cell;
        }
    }

    public void printMatrix() {
        for (int i = 0; i < B.M; i++) {
            for (int j = 0; j < B.N; j++) {
                System.out.print(mat[i][j].state + " ");
            }
            System.out.print("\n");
        }
    }
}
