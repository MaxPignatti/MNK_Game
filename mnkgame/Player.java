package mnkgame;

import java.util.Random;

public class Player implements MNKPlayer {

    private Random rand;
	private MNKBoard B;
	private MNKGameState myWin;
	private MNKGameState yourWin;
	private int TIMEOUT;

	public Player() {
	}

	public void initPlayer(int M, int N, int K, boolean first, int timeout_in_secs) {
		rand    = new Random(System.currentTimeMillis()); 
		B       = new MNKBoard(M,N,K);
		myWin   = first ? MNKGameState.WINP1 : MNKGameState.WINP2; 
		yourWin = first ? MNKGameState.WINP2 : MNKGameState.WINP1;
		TIMEOUT = timeout_in_secs;	
	}

	public MNKCell selectCell(MNKCell[] FC, MNKCell[] MC) {
		Matrice mat = new Matrice();
		mat.initMat(B);

	}

	//Funzione che ritorna la cella libera più vicina al centro della matrice
	//FINIRE LA FUNZIONE CON CONTROLLO CHE NON VADA FUORI DAL BORDO
	public MNKCell freeCenterCell (MNKCell[][] mat, int n, int m) { //i è righe come m. j è colonne come n
		int nCenter = n/2;
		int mCenter = m/2;
		Direction going = Direction.right;
		int goDown = (nCenter)+1;
		int goLeft = (mCenter)+1;
		int goUp = (nCenter)-1;
		int goRight = (mCenter)-1;
		while (true) {
			MNKCell centerCell = mat[nCenter][mCenter];
			if (centerCell.state == MNKCellState.FREE) {
				return centerCell;
			}
			switch (going) {
				case right: 
					if (centerCell.j == goDown) {
						goDown++;
						mCenter++;
						going = Direction.down;
					} else {
						nCenter++;
					}
				break;
				case down:
					if (centerCell.i == goLeft) {
						goLeft++;
						nCenter--;;
						going = Direction.left;
					} else {
						mCenter++;
					}
				break;
				case left:
					if (centerCell.j == goUp) {
						goUp--;
						mCenter--;
						going = Direction.up;
					} else {
						nCenter--;
					}
				break;
				case up:
					if (centerCell.i == goRight) {
						goRight--;
						nCenter++;
						going = Direction.right;
					} else {
						mCenter--;
					}
				break;
			}
		}
	}

    public String playerName() {
		return "KUNG SLAO";
	}
}
