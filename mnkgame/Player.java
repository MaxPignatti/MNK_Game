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
		Matrice mat = new Matrice(B);
		mat.initMat();

	}

	public Sequence[] getAllSequences (MNKCell[][] mat, MNKBoard B) {
		int index=0;
		int counter;
		Sequence[] allSequences = new Sequence[B.M*B.N];

		//	IL PROSSIMO CICLO AGGIUNGE ALL'ARRAY DI SEQUENZE QUELLE ORIZZONTALI
		for (Sequence lengthZero : allSequences) {
			lengthZero.setLength(0);
		}
		for (int row = 1; row <= B.M; row ++) {
			counter = 0;
			for (int column = 1; column <= B.N; column ++) {
				if (column == 1 && mat[row][1].state != MNKCellState.FREE) {
					counter++;
				} else {
					if (mat[row][column].state != mat[row][column - 1].state && mat[row][column - 1].state != MNKCellState.FREE) {
						if (counter >= 1) {
							boolean state;
							if (mat[row][column - 1].state==MNKCellState.P1) {
								state = false;
							} else {
								state = true;
							}
							allSequences[index] = new Sequence(mat[row][column-counter-1], mat[row][column-1], counter, state);
							index++;
							counter = 0;
						} 
					} else {
						if (mat[row][column-1].state == MNKCellState.FREE) {
							counter = 0;
						} else {
							counter++;
						}
					}
				}
			}
		}

		counter = 0;

		//	IL PROSSIMO CICLO AGGIUNGE ALL'ARRAY DI SEQUENZE QUELLE VERTICALI
		for (int column = 1; column <= B.M; column ++) {
			counter = 0;
			for (int row = 1; row <= B.N; row ++) {
				if (row == 1 && mat[1][column].state != MNKCellState.FREE) {
					counter++;
				} else {
					if (mat[row][column].state != mat[row - 1][column].state && mat[row - 1][column].state != MNKCellState.FREE) {
						if (counter >= 1) {
							boolean state;
							if (mat[row - 1][column].state==MNKCellState.P1) {
								state = false;
							} else {
								state = true;
							}
							allSequences[index] = new Sequence(mat[row - counter - 1][column], mat[row - 1][column], counter, state);
							index++;
							counter = 0;
						} 
					} else {
						if (mat[row - 1][column].state == MNKCellState.FREE) {
							counter = 0;
						} else {
							counter++;
						}
					}
				}
			}
		}

		counter = 0;
		int column = B.N - B.K;
		int row = 1;

		//	IL PROSSIMO CICLO AGGIUNGE ALL'ARRAY DI SEQUENZE QUELLE OBLIQUE DAL LATO ALTO AL LATO DESTRO
		while (column >= 1) {
			int tempColumn = column;
			int tempRow = row;
			while (tempColumn <= B.N && tempRow <= B.M) {
				if (tempRow == 1 && mat[1][tempColumn].state != MNKCellState.FREE) {
					counter++;
				} else {
					if (mat[tempRow][tempColumn].state != mat[tempRow - 1][tempColumn - 1].state && mat[tempRow - 1][tempColumn - 1].state != MNKCellState.FREE) {
						if (counter >= 1) {
							boolean state;
							if (mat[tempRow - 1][tempColumn - 1].state==MNKCellState.P1) {
								state = false;
							} else {
								state = true;
							}
							allSequences[index] = new Sequence(mat[tempRow - counter - 1][tempColumn - counter - 1], mat[tempRow - 1][tempColumn - 1], counter, state);
							index++;
							counter = 0;
						} 
					} else {
						if (mat[tempRow - 1][tempColumn - 1].state == MNKCellState.FREE) {
							counter = 0;
						} else {
							counter++;
						}
					}
				}
				tempColumn++;
				tempRow++;
			}
			column--;
			counter = 0;
		}

		counter = 0;
		column = 1;
		row = B.M - B.K;

		//	IL PROSSIMO CICLO AGGIUNGE ALL'ARRAY DI SEQUENZE QUELLE OBLIQUE DAL LATO SINISTRO AL LATO BASSO
		while (row >= 1) {
			int tempColumn = column;
			int tempRow = row;
			while (tempColumn <= B.N && tempRow <= B.M) {
				if (tempColumn == 1 && mat[tempRow][1].state != MNKCellState.FREE) {
					counter++;
				} else {
					if (mat[tempRow][tempColumn].state != mat[tempRow - 1][tempColumn - 1].state && mat[tempRow - 1][tempColumn - 1].state != MNKCellState.FREE) {
						if (counter >= 1) {
							boolean state;
							if (mat[tempRow - 1][tempColumn - 1].state==MNKCellState.P1) {
								state = false;
							} else {
								state = true;
							}
							allSequences[index] = new Sequence(mat[tempRow - counter - 1][tempColumn - counter - 1], mat[tempRow - 1][tempColumn - 1], counter, state);
							index++;
							counter = 0;
						} 
					} else {
						if (mat[tempRow - 1][tempColumn - 1].state == MNKCellState.FREE) {
							counter = 0;
						} else {
							counter++;
						}
					}
				}
				tempColumn++;
				tempRow++;
			}
			row--;
			counter = 0;
		}

		counter = 0;
		column = 1;
		row = B.K;

		//	IL PROSSIMO CICLO AGGIUNGE ALL'ARRAY DI SEQUENZE QUELLE OBLIQUE DAL LATO SINISTRO AL LATO ALTO
		while (row <= B.M) {
			int tempColumn = column;
			int tempRow = row;
			while (tempColumn <= B.N && tempRow >= 1) {
				if (tempColumn == 1 && mat[tempRow][1].state != MNKCellState.FREE) {
					counter++;
				} else {
					if (mat[tempRow][tempColumn].state != mat[tempRow + 1][tempColumn - 1].state && mat[tempRow + 1][tempColumn - 1].state != MNKCellState.FREE) {
						if (counter >= 1) {
							boolean state;
							if (mat[tempRow + 1][tempColumn - 1].state==MNKCellState.P1) {
								state = false;
							} else {
								state = true;
							}
							allSequences[index] = new Sequence(mat[tempRow + counter + 1][tempColumn - counter - 1], mat[tempRow + 1][tempColumn - 1], counter, state);
							index++;
							counter = 0;
						} 
					} else {
						if (mat[tempRow + 1][tempColumn - 1].state == MNKCellState.FREE) {
							counter = 0;
						} else {
							counter++;
						}
					}
				}
				tempColumn++;
				tempRow--;
			}
			row++;
			counter = 0;
		}

		counter = 0;
		column = B.N - B.K;
		row = B.M;

		//	IL PROSSIMO CICLO AGGIUNGE ALL'ARRAY DI SEQUENZE QUELLE OBLIQUE DAL LATO BASSO AL LATO DESTRO
		while (column >= 1) {
			int tempColumn = column;
			int tempRow = row;
			while (tempColumn <= B.N && tempRow >= 1) {
				if (tempRow == B.N && mat[B.N][tempColumn].state != MNKCellState.FREE) {
					counter++;
				} else {
					if (mat[tempRow][tempColumn].state != mat[tempRow + 1][tempColumn - 1].state && mat[tempRow + 1][tempColumn - 1].state != MNKCellState.FREE) {
						if (counter >= 1) {
							boolean state;
							if (mat[tempRow + 1][tempColumn - 1].state==MNKCellState.P1) {
								state = false;
							} else {
								state = true;
							}
							allSequences[index] = new Sequence(mat[tempRow + counter + 1][tempColumn - counter - 1], mat[tempRow + 1][tempColumn - 1], counter, state);
							index++;
							counter = 0;
						} 
					} else {
						if (mat[tempRow + 1][tempColumn - 1].state == MNKCellState.FREE) {
							counter = 0;
						} else {
							counter++;
						}
					}
				}
				tempColumn++;
				tempRow--;
			}
			column--;
			counter = 0;
		}

		return allSequences;
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
