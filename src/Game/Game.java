package Game;

import Card.Card;
import Player.Player;
import fileio.ActionsInput;

import java.util.ArrayList;

public class Game {
    private Player[] players = new Player[2];
    private int playerTurn;
    private ArrayList<ActionsInput> actions;
    private ArrayList<ArrayList<Card>> board;

    public Game(int decksNumber1, int cardsInDeckNumber1, int decksNumber2, int cardsInDeckNumber2) {
        players[0] = new Player(decksNumber1, cardsInDeckNumber1);
        players[1] = new Player(decksNumber2, cardsInDeckNumber2);
        this.board = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            board.add(new ArrayList<>());
        }
    }

    public Player getPlayer(int i) {
        return players[i];
    }

    public ArrayList<ActionsInput> getActions() {
        return actions;
    }

    public void setActions(ArrayList<ActionsInput> actions) {
        this.actions = actions;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
    }
}
