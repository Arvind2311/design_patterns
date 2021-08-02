package behavioral.template;

public class TemplateMethod {
    public static void main(String[] args) {
        new Chess().run();
    }
}

abstract class Game{
    protected int currentPlayer;
    protected final int numberOfPlayers;

    public Game(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public void run(){
        start();
        while(!haveWinner()){
            takeTurn();
            currentPlayer=(currentPlayer+1)%numberOfPlayers;
        }
        System.out.println("Player "+getWinningPlayer()+" wins!!");
    }

    protected abstract int getWinningPlayer();
    protected abstract void takeTurn();
    protected abstract boolean haveWinner();
    protected abstract void start();
}

class Chess extends Game{

    private int maxTurns = 10;
    private int turn = 1;

    public Chess() {
        super(2);
    }

    @Override
    public void run() {
        super.run();
    }

    @Override
    protected int getWinningPlayer() {
        return 0;
    }

    @Override
    protected void takeTurn() {
        System.out.println("Turn " + (turn++) + " taken by player " + currentPlayer);
    }

    @Override
    protected boolean haveWinner() {
        return turn==maxTurns;
    }

    @Override
    protected void start() {
        System.out.println("Starting a game of chess!!");
    }
}