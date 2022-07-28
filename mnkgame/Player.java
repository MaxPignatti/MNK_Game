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
		for (MNKCell cell : B.getMarkedCells()) {
			System.out.println(cell.i + " " + cell.j);
		}
		Matrix mat = new Matrix(B, FC, MC);
		mat.initMatrix();
		mat.printMatrix();
		// System.out.println("arriva qui?");
		Sequence[] sequences = getAllSequences(mat, B);
		// System.out.println("arriva qui? 2");
		// printSequences(sequences);
		return FC[rand.nextInt(FC.length)];
	}

	public void printSequences(Sequence[] sequences) {
		for (Sequence seq : sequences) {
			if (seq.length != 0) {
				System.out.println("C'è una sequenza che inizia a riga " + seq.firstCell.i + " colonna "
						+ seq.firstCell.j + " e finisce a riga " + seq.lastCell.i + " colonna " + seq.lastCell.j
						+ ". Lunghezza: " + seq.length);
			}
		}
	}

	public Sequence[] getAllSequences(Matrix Mat, MNKBoard B) {
		int index = 0;
		int counter;
		Sequence[] allSequences = new Sequence[B.M * B.N];

		// IL PROSSIMO CICLO AGGIUNGE ALL'ARRAY DI SEQUENZE QUELLE ORIZZONTALI
		System.out.println("Prova");
		// for (Sequence lengthZero : allSequences) {
		// lengthZero.setLength(0); //ERRORE 1
		// }
		for (int row = 0; row < B.M; row++) {
			System.out.println("Prova");
			counter = 0;
			for (int column = 0; column < B.N; column++) {
				if (column == 0 && Mat.mat[row][0].state != MNKCellState.FREE) {
					counter++;
				} else if (column == 0 && Mat.mat[row][0].state == MNKCellState.FREE) {
					counter = 0;
				} else {
					System.out.println("Prova");
					if (Mat.mat[row][column].state != Mat.mat[row][column - 1].state
							&& Mat.mat[row][column - 1].state != MNKCellState.FREE) {
						if (counter >= 1) {
							boolean state;
							if (Mat.mat[row][column - 1].state == MNKCellState.P1) {
								state = false;
							} else {
								state = true;
							}
							System.out.println("Prova");
							allSequences[index] = new Sequence(Mat.mat[row][column - counter - 1],
									Mat.mat[row][column - 1],
									counter, state);
							index++;
							System.out.println("Prova");
							counter = 0;
						}
					} else {
						if (Mat.mat[row][column - 1].state == MNKCellState.FREE) {
							counter = 0;
						} else {
							counter++;
						}
					}
				}
			}
		}

		counter = 0;

		// IL PROSSIMO CICLO AGGIUNGE ALL'ARRAY DI SEQUENZE QUELLE VERTICALI
		for (int column = 0; column < B.M; column++) {
			counter = 0;
			for (int row = 0; row < B.N; row++) {
				if (row == 0 && Mat.mat[0][column].state != MNKCellState.FREE) {
					counter++;
				} else if (row == 0 && Mat.mat[0][column].state == MNKCellState.FREE) {
					counter = 0;
				} else {
					if (Mat.mat[row][column].state != Mat.mat[row - 1][column].state
							&& Mat.mat[row - 1][column].state != MNKCellState.FREE) {
						if (counter >= 1) {
							boolean state;
							if (Mat.mat[row - 1][column].state == MNKCellState.P1) {
								state = false;
							} else {
								state = true;
							}
							allSequences[index] = new Sequence(Mat.mat[row - counter - 1][column],
									Mat.mat[row - 1][column],
									counter, state);
							index++;
							counter = 0;
						}
					} else {
						if (Mat.mat[row - 1][column].state == MNKCellState.FREE) {
							counter = 0;
						} else {
							counter++;
						}
					}
				}
			}
		}
		System.out.println("Prova Finale");

		counter = 0;
		int column = B.N - B.K;
		int row = 0;

		// IL PROSSIMO CICLO AGGIUNGE ALL'ARRAY DI SEQUENZE QUELLE OBLIQUE DAL LATO ALTO
		// AL LATO DESTRO
		while (column >= 0) {
			int tempColumn = column;
			int tempRow = row;
			while (tempColumn < B.N && tempRow < B.M) {
				System.out.println("Prova Ancora");

				if (tempRow == 0 && Mat.mat[0][tempColumn].state != MNKCellState.FREE) {
					counter++;
				} else if (tempRow == 0 && Mat.mat[0][tempColumn].state == MNKCellState.FREE) {
					counter = 0;
				} else {
					System.out.println("Prova Ancora");
					if (Mat.mat[tempRow][tempColumn].state != Mat.mat[tempRow - 1][tempColumn - 1].state
							&& Mat.mat[tempRow - 1][tempColumn - 1].state != MNKCellState.FREE) {
						if (counter >= 1) {
							boolean state;
							if (Mat.mat[tempRow - 1][tempColumn - 1].state == MNKCellState.P1) {
								state = false;
							} else {
								state = true;
							}
							allSequences[index] = new Sequence(Mat.mat[tempRow - counter - 1][tempColumn - counter - 1],
									Mat.mat[tempRow - 1][tempColumn - 1], counter, state);
							index++;
							counter = 0;
						}
					} else {
						if (Mat.mat[tempRow - 1][tempColumn - 1].state == MNKCellState.FREE) {
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
		System.out.println("Altra prova");
		counter = 0;
		column = 0;
		row = B.M - B.K;

		// IL PROSSIMO CICLO AGGIUNGE ALL'ARRAY DI SEQUENZE QUELLE OBLIQUE DAL LATO
		// SINISTRO AL LATO BASSO
		while (row >= 0) {
			int tempColumn = column;
			int tempRow = row;
			while (tempColumn < B.N && tempRow < B.M) {
				if (tempColumn == 0 && Mat.mat[tempRow][0].state != MNKCellState.FREE) {
					counter++;
				} else if (tempColumn == 0 && Mat.mat[tempRow][0].state == MNKCellState.FREE) {
					counter = 0;
				} else {
					if (Mat.mat[tempRow][tempColumn].state != Mat.mat[tempRow - 1][tempColumn - 1].state
							&& Mat.mat[tempRow - 1][tempColumn - 1].state != MNKCellState.FREE) {
						if (counter >= 1) {
							boolean state;
							if (Mat.mat[tempRow - 1][tempColumn - 1].state == MNKCellState.P1) {
								state = false;
							} else {
								state = true;
							}
							allSequences[index] = new Sequence(Mat.mat[tempRow - counter - 1][tempColumn - counter - 1],
									Mat.mat[tempRow - 1][tempColumn - 1], counter, state);
							index++;
							counter = 0;
						}
					} else {
						if (Mat.mat[tempRow - 1][tempColumn - 1].state == MNKCellState.FREE) {
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
		column = 0;
		row = B.K;

		System.out.println("Cazzo");
		// IL PROSSIMO CICLO AGGIUNGE ALL'ARRAY DI SEQUENZE QUELLE OBLIQUE DAL LATO
		// SINISTRO AL LATO ALTO
		while (row < B.M) {
			int tempColumn = column;
			int tempRow = row;
			while (tempColumn < B.N && tempRow >= 0) {
				if (tempColumn == 0 && Mat.mat[tempRow][0].state != MNKCellState.FREE) {
					counter++;
				} else if (tempColumn == 0 && Mat.mat[tempRow][0].state != MNKCellState.FREE) {
					counter = 0;
				} else {
					System.out.println("Cazzo");
					if (Mat.mat[tempRow][tempColumn].state != Mat.mat[tempRow + 1][tempColumn - 1].state
							&& Mat.mat[tempRow + 1][tempColumn - 1].state != MNKCellState.FREE) {
						if (counter >= 1) {
							boolean state;
							if (Mat.mat[tempRow + 1][tempColumn - 1].state == MNKCellState.P1) {
								state = false;
							} else {
								state = true;
							}
							allSequences[index] = new Sequence(Mat.mat[tempRow + counter + 1][tempColumn - counter - 1],
									Mat.mat[tempRow + 1][tempColumn - 1], counter, state);
							index++;
							counter = 0;
						}
					} else {
						if (Mat.mat[tempRow + 1][tempColumn - 1].state == MNKCellState.FREE) {
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

		// IL PROSSIMO CICLO AGGIUNGE ALL'ARRAY DI SEQUENZE QUELLE OBLIQUE DAL LATO
		// BASSO AL LATO DESTRO
		while (column >= 0) {
			int tempColumn = column;
			int tempRow = row;
			while (tempColumn < B.N && tempRow >= 0) {
				if (tempRow == B.N && Mat.mat[B.N][tempColumn].state != MNKCellState.FREE) {
					counter++;
				} else if (tempRow == B.N && Mat.mat[B.N][tempColumn].state == MNKCellState.FREE) {
					counter = 0;
				} else {
					if (Mat.mat[tempRow][tempColumn].state != Mat.mat[tempRow + 1][tempColumn - 1].state
							&& Mat.mat[tempRow + 1][tempColumn - 1].state != MNKCellState.FREE) {
						if (counter >= 1) {
							boolean state;
							if (Mat.mat[tempRow + 1][tempColumn - 1].state == MNKCellState.P1) {
								state = false;
							} else {
								state = true;
							}
							allSequences[index] = new Sequence(Mat.mat[tempRow + counter + 1][tempColumn - counter - 1],
									Mat.mat[tempRow + 1][tempColumn - 1], counter, state);
							index++;
							counter = 0;
						}
					} else {
						if (Mat.mat[tempRow + 1][tempColumn - 1].state == MNKCellState.FREE) {
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
		System.out.println("cazzone");
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
