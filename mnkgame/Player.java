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
		Matrice mat = new Matrice();
		mat.initMat(B);

	}

	public String playerName() {
		return "KUNG SLAO";
	}
}
