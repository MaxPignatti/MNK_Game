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
		rand = new Random(System.currentTimeMillis());
		B = new MNKBoard(M, N, K);
		myWin = first ? MNKGameState.WINP1 : MNKGameState.WINP2;
		yourWin = first ? MNKGameState.WINP2 : MNKGameState.WINP1;
		TIMEOUT = timeout_in_secs;
	}

	public MNKCell selectCell(MNKCell[] FC, MNKCell[] MC) {
		Matrix mat = new Matrix(B, FC, MC);
		mat.initMatrix();
		mat.printMatrix();
		Sequence[] sequences = getAllSequences(mat, B);
		printSequences(sequences);
		return FC[rand.nextInt(FC.length)];
	}

	public void printSequences(Sequence[] sequences) {
		for (Sequence seq : sequences) {
			if (seq.length != 0) {
				System.out.println("C'è una sequenza che inizia a riga " + seq.firstCell.i + " colonna "
						+ seq.firstCell.j + " e finisce a riga " + seq.lastCell.i + " colonna " + seq.lastCell.j
						+ ". Lunghezza: " + seq.length + ". Player: " + seq.state);
			}
		}
	}

	public Sequence[] getAllSequences(Matrix Mat, MNKBoard B) {
		int index = 0;
		int counter = 0;
		boolean ok;
		Sequence[] allSequences = new Sequence[B.M * B.N];
		MNKCell defaultCell = new MNKCell(0, 0);

		for (int i=0; i<(B.M * B.N); i++) {
			allSequences[i] = new Sequence(defaultCell, defaultCell, 0, MNKCellState.FREE);
		}
		
		for (int row = 0; row<B.M; row++) {
			for (int column = 0; column<B.N; column++) {
				MNKCell toControl = Mat.mat[row][column];
				if (toControl.state != MNKCellState.FREE) {
					counter = 1;
					ok = false;
					if (row == 0) {
						ok = true;
					} else if (toControl.state != Mat.mat[row - 1][column].state) {
						ok = true;
					}
					if (ok) {
						//CONTROLLO VERTICALE
						int tempRow = row;
						while(tempRow < B.M - 1) {
							tempRow++;
							if (Mat.mat[tempRow][column].state == Mat.mat[tempRow - 1][column].state) {
								counter++;
							} else {
								tempRow--;
								break;
							}
						}
						if (counter > 1) {
							allSequences[index] = new Sequence(Mat.mat[tempRow - (counter - 1)][column],
                                    Mat.mat[tempRow][column], counter, Mat.mat[tempRow][column].state);
							index++; 
						}
						
					}
					counter = 1;
					ok = false;
					if (column == 0) {
						ok = true;
					} else if (toControl.state != Mat.mat[row][column - 1].state) {
						ok = true;
					}
					if (ok) {
						//CONTROLLO ORIZZONTALE
						int tempColumn = column;
						while(tempColumn < B.N - 1) {
							tempColumn++;
							if (Mat.mat[row][tempColumn].state == Mat.mat[row][tempColumn - 1].state) {
								counter++;
							} else {
								tempColumn--;
								break;
							}
						}
						if (counter > 1) {
							allSequences[index] = new Sequence(Mat.mat[row][tempColumn - (counter - 1)],
                                    Mat.mat[row][tempColumn], counter, Mat.mat[row][tempColumn].state);
							index++;
						} 
					}
					counter = 1;
					ok = false;
					if (row == 0 || column == 0) {
						ok = true;
					} else if (toControl.state != Mat.mat[row - 1][column - 1].state) {
						ok = true;
					}
					if (ok) {
						//CONTROLLO OBLIQUO BASSO-DESTRA
						int tempColumn = column;
						int tempRow = row;
						while(tempColumn < B.N - 1 && tempRow < B.M - 1) {
							tempColumn++;
							tempRow++;
							if (Mat.mat[tempRow][tempColumn].state == Mat.mat[tempRow - 1][tempColumn - 1].state) {
								counter++;
							} else {
								tempColumn--;
								tempRow--;
								break;
							}
						}
						if (counter > 1) {
							allSequences[index] = new Sequence(Mat.mat[tempRow - (counter - 1)][tempColumn - (counter - 1)],
                                    Mat.mat[tempRow][tempColumn], counter, Mat.mat[tempRow][tempColumn].state);
							index++;
						} 
					}
					counter = 1;
					ok = false;
					if (row == B.M - 1 || column == 0) {
						ok = true;
					} else if (toControl.state != Mat.mat[row + 1][column - 1].state) {
						ok = true;
					}
					if (ok) {
						//CONTROLLO OBLIQUO ALTO-DESTRA
						int tempColumn = column;
						int tempRow = row;
						while(tempColumn < B.N - 1 && tempRow > 0) {
							tempColumn++;
							tempRow--;
							if (Mat.mat[tempRow][tempColumn].state == Mat.mat[tempRow + 1][tempColumn - 1].state) {
								counter++;
							} else {
								tempColumn--;
								tempRow++;
								break;
							}
						}
						if (counter > 1) {
							allSequences[index] = new Sequence(Mat.mat[tempRow + (counter - 1)][tempColumn - (counter - 1)],
                                    Mat.mat[tempRow][tempColumn], counter, Mat.mat[tempRow][tempColumn].state);
							index++;
						} 
					}
				}
			}
		}
		return allSequences;
	}

	public MNKCell[] getExtremes(Sequence Seq, MNKBoard B) {

		MNKCell[] output = new MNKCell[2];
		MNKCell FC = Seq.getFirstCell();
		MNKCell LC = Seq.getLastCell();
		if (LC.i == FC.i) { // Se sono sulla stessa riga
			if (FC.j - 1 <= 0 && LC.j + 1 > B.M) {
			} else if (FC.j - 1 <= 0) {
				output[0] = new MNKCell(0, 0);
				output[1] = new MNKCell(LC.i, LC.j + 1, LC.state);
			} else if (LC.j + 1 > B.M) {
				output[0] = new MNKCell(FC.i, FC.j - 1, FC.state);
				output[1] = new MNKCell(0, 0);
			} else {
				output[0] = new MNKCell(FC.i, FC.j - 1, FC.state);
				output[1] = new MNKCell(LC.i, LC.j + 1, LC.state);
			}
		} else if (LC.j == FC.j) { // Se sono sulla stessa colonna
			if (FC.i - 1 <= 0 && LC.i + 1 > B.N) {
				output[0] = new MNKCell(0, 0);
				output[1] = new MNKCell(0, 0);
			} else if (FC.i - 1 <= 0) {
				output[0] = new MNKCell(0, 0);
				output[1] = new MNKCell(LC.i + 1, LC.j, LC.state);
			} else if (LC.i + 1 > B.N) {
				output[0] = new MNKCell(FC.i - 1, FC.j, FC.state);
				output[1] = new MNKCell(0, 0);
			} else {
				output[0] = new MNKCell(FC.i - 1, FC.j, FC.state);
				output[1] = new MNKCell(LC.i + 1, LC.j, LC.state);
			}
		} else {
			if (FC.i > LC.i) {
				if ((FC.i + 1 <= 0 || FC.j + 1 <= 0) && (LC.i - 1 > B.M || LC.j - 1 > B.N)) {

				} else if (FC.i + 1 <= 0 || FC.j + 1 <= 0) {
					output[0] = new MNKCell(0, 0);
					output[1] = new MNKCell(LC.i - 1, LC.j - 1, LC.state);
				} else if (LC.i - 1 > B.M || LC.j - 1 > B.N) {
					output[0] = new MNKCell(FC.i + 1, FC.j + 1, FC.state);
					output[1] = new MNKCell(0, 0);
				} else {
					output[0] = new MNKCell(FC.i + 1, FC.j + 1, FC.state);
					output[1] = new MNKCell(LC.i - 1, LC.j - 1, LC.state);
				}
			} else if (FC.i > LC.i) {
				if ((FC.i - 1 <= 0 || FC.j - 1 <= 0) && (LC.i + 1 > B.M || LC.j + 1 > B.N)) {
					output[0] = new MNKCell(0, 0);
					output[1] = new MNKCell(0, 0);
				} else if (FC.i - 1 <= 0 || FC.j - 1 <= 0) {
					output[0] = new MNKCell(0, 0);
					output[1] = new MNKCell(LC.i + 1, LC.j + 1, LC.state);
				} else if (LC.i + 1 > B.M || LC.j + 1 > B.N) {
					output[0] = new MNKCell(FC.i - 1, FC.j - 1, FC.state);
					output[1] = new MNKCell(0, 0);
				} else {
					output[0] = new MNKCell(FC.i - 1, FC.j - 1, FC.state);
					output[1] = new MNKCell(LC.i + 1, LC.j + 1, LC.state);
				}
			}
		}
		return output;
	}

	// Funzione che ritorna la cella libera più vicina al centro della matrice
	// FINIRE LA FUNZIONE CON CONTROLLO CHE NON VADA FUORI DAL BORDO
	public MNKCell freeCenterCell(MNKCell[][] mat, int n, int m) { // i è righe come m. j è colonne come n
		int nCenter = n / 2;
		int mCenter = m / 2;
		Direction going = Direction.right;
		int goDown = (nCenter) + 1;
		int goLeft = (mCenter) + 1;
		int goUp = (nCenter) - 1;
		int goRight = (mCenter) - 1;
		MNKCell centerCell;
		while (true) {
			centerCell = mat[mCenter][nCenter];
			if (centerCell.state == MNKCellState.FREE) {
				break;
			}
			switch (going) {
				case right:
					if (centerCell.j == goDown) {
						goDown++;
						nCenter++;
						going = Direction.down;
					} else {
						mCenter++;
					}
					break;
				case down:
					if (centerCell.i == goLeft) {
						goLeft++;
						mCenter--;
						;
						going = Direction.left;
					} else {
						nCenter++;
					}
					break;
				case left:
					if (centerCell.j == goUp) {
						goUp--;
						nCenter--;
						going = Direction.up;
					} else {
						mCenter--;
					}
					break;
				case up:
					if (centerCell.i == goRight) {
						goRight--;
						mCenter++;
						going = Direction.right;
					} else {
						nCenter--;
					}
					break;
			}
		}
		return centerCell;
	}

	public String playerName() {
		return "KUNG SLAO";
	}
}
