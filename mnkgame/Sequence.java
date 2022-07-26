package mnkgame;

public class Sequence {
    protected MNKCell firstCell;
    protected MNKCell lastCell;
    protected int length;
    protected boolean state; //0=player1 1=player2

    public Sequence (MNKCell firstCell, MNKCell lastCell, int length, boolean state) {
        this.firstCell = firstCell;
        this.lastCell = lastCell;
        this.length = length;
        this.state = state;
    }

    public MNKCell getFirstCell () {
        return firstCell;
    }

    public MNKCell getLastCell () {
        return lastCell;
    }

    public int getlength () {
        return length;
    }

    public boolean getState () {
        return state;
    }
}

