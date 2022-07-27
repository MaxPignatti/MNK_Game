package mnkgame;

public class Matrix {

    protected MNKCell[][] mat;
    protected MNKBoard B;

    public Matrix(MNKBoard B) {
        this.B = B;
        mat = new MNKCell[B.M][B.N];
    }

    public MNKCell[][] getMatrix () {
        return mat;
    }

    public void initMatrix() {
        System.out.println("sono qua");
        for (MNKCell cell : B.getMarkedCells()) {
            System.out.println(cell.i + " " + cell.j);
            mat[cell.i][cell.j] = cell;
            System.out.println(mat[cell.i][cell.j].state);
        }
        for (MNKCell cell : B.getFreeCells()) {
            System.out.println("cella libera");
            mat[cell.i][cell.j] = cell;
        }
    }

    public void printMatrix() {
        for (int i = 0; i < B.N; i++) {
            for (int j = 0; j < B.M; j++) {
                System.out.print(mat[j][i].state + " ");
            }
            System.out.print("\n");
        }
    }
}
