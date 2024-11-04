package Game;

import Card.Card;
import Player.Player;
import ConvertJson.ConvertJson;
import Player.Player;
import fileio.*;
import fileio.ActionsInput;

import java.util.ArrayList;

public class Game {
    private int gameTurn = 1;
    private Player[] players = new Player[2];
    private int playerTurn;
    private ArrayList<ActionsInput> actions;
    private ArrayList<ArrayList<Card>> board;

    public Game(int decksNumber1, int cardsInDeckNumber1, int decksNumber2, int cardsInDeckNumber2) {
        players[0] = new Player(decksNumber1, cardsInDeckNumber1);
        players[1] = new Player(decksNumber2, cardsInDeckNumber2);
        this.board = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            board.add(new ArrayList<Card>());
        }
    }

    public int getGameTurn() {
        return gameTurn;
    }

    public void setGameTurn(int gameTurn) {
        this.gameTurn = gameTurn;
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

    public ArrayList<ArrayList<Card>> getBoard() {
        return board;
    }

    public void setBoard(ArrayList<ArrayList<Card>> board) {
        this.board = board;
    }

    public void copyPlayerDecks(Input inputData) {
        getPlayer(0).copyAllDecks(inputData.getPlayerOneDecks().getDecks());
        getPlayer(1).copyAllDecks(inputData.getPlayerTwoDecks().getDecks());
    }

    public void endTurn () {
        int playerIdx = getPlayerTurn();
        int nextPlayer = (playerIdx + 1) % 2;
        getPlayer(playerIdx).setHasPlayed(1);

        if (getPlayer(0).getHasPlayed() == 1 && getPlayer(1).getHasPlayed() == 1) {
            getPlayer(0).setHasPlayed(0);
            getPlayer(1).setHasPlayed(0);
            int aux = getGameTurn();
            setGameTurn(aux + 1);

            if (getGameTurn() <= 10) {
                getPlayer(0).addMana(getGameTurn());
                getPlayer(1).addMana(getGameTurn());
            } else {
                getPlayer(0).addMana(10);
                getPlayer(1).addMana(10);
            }

            if (!getPlayer(0).getCurrentDeck().isEmpty()) {
                getPlayer(0).fromDeckToHand();
            }
            if (!getPlayer(1).getCurrentDeck().isEmpty()) {
                getPlayer(1).fromDeckToHand();
            }

            for (int i = 0; i < getBoard().size(); i++) {
                for (int j = 0; j < getBoard().get(i).size(); j++) {
                    getBoard().get(i).get(j).setHasAttacked(0);
                    getBoard().get(i).get(j).setIsFrozen(0);
                }
            }
        }
        setPlayerTurn(nextPlayer);
    }

    public void placeCard (int cardIdx, ConvertJson out) {
        int playerIdx = getPlayerTurn();
        Card placedCard = getPlayer(playerIdx).getCardsInHand().get(cardIdx);
        int manaCost = placedCard.getMana();

        if (getPlayer(playerIdx).getTotalMana() >= manaCost) {
            getPlayer(playerIdx).subtractMana(manaCost);
            int row;
            if (placedCard.getName().equals("Goliath") || placedCard.getName().equals("Warden")
                || placedCard.getName().equals("The Ripper") || placedCard.getName().equals("Miraj")) {
                row = 1;
                if (playerIdx == 0) {
                    row = 2;
                }
            } else {
                row = 0;
                if (playerIdx == 0) {
                    row = 3;
                }
            }
            if (getBoard().get(row).size() == 5) {
                out.noSpaceToPlace(cardIdx);
            } else {
                getBoard().get(row).add(placedCard);
                getPlayer(playerIdx).getCardsInHand().remove(cardIdx);
            }
        } else {
            out.noMana(cardIdx);
        }
    }

    public void cardUsesAbility(Coordinates cardAttacker, Coordinates cardAttacked, ConvertJson out) {
        int x1 = cardAttacker.getX();
        int y1 = cardAttacker.getY();
        int x2 = cardAttacked.getX();
        int y2 = cardAttacked.getY();

        if (y2 < getBoard().get(x2).size() && y1 < getBoard().get(x1).size()) {
            int playerIdx = getPlayerTurn();
            if (getBoard().get(x1).get(y1).getIsFrozen() == 1) {
                out.cardAttackingError(cardAttacker, cardAttacked, "cardUsesAbility","Attacker card is frozen.");
            } else if (getBoard().get(x1).get(y1).getHasAttacked() == 1) {
                out.cardAttackingError(cardAttacker, cardAttacked, "cardUsesAbility", "Attacker card has already attacked this turn.");
            } else if (getBoard().get(x1).get(y1).getName().equals("Disciple")){
                if ((playerIdx == 0 && (x2 == 2 || x2 == 3)) || (playerIdx == 1 && (x2 == 0 || x2 == 1))) {
                    getBoard().get(x2).get(y2).addHealth(2);
                    getBoard().get(x1).get(y1).setHasAttacked(1);
                } else {
                    out.cardAttackingError(cardAttacker, cardAttacked, "cardUsesAbility", "Attacked card does not belong to the current player.");
                }
            } else if (getBoard().get(x1).get(y1).getName().equals("The Ripper")
                       || getBoard().get(x1).get(y1).getName().equals("Miraj")
                       || getBoard().get(x1).get(y1).getName().equals("The Cursed One")) {
                if ((playerIdx == 0 && (x2 == 0 || x2 == 1)) || (playerIdx == 1 && (x2 == 2 || x2 == 3))){
                    int attackedCardIsTank = 0;
                    if (getBoard().get(x2).get(y2).getName().equals("Goliath")
                            || getBoard().get(x2).get(y2).getName().equals("Warden")) {
                        attackedCardIsTank = 1;
                    }
                    int enemyHasTank = 0;
                    if (playerIdx == 0) {
                        for (int i = 0; i < getBoard().get(1).size(); i++) {
                            if (getBoard().get(1).get(i).getName().equals("Goliath")
                                    || getBoard().get(1).get(i).getName().equals("Warden")) {
                                enemyHasTank = 1;
                                break;
                            }
                        }
                    } else {
                        for (int i = 0; i < getBoard().get(2).size(); i++) {
                            if (getBoard().get(2).get(i).getName().equals("Goliath")
                                    || getBoard().get(2).get(i).getName().equals("Warden")) {
                                enemyHasTank = 1;
                                break;
                            }
                        }
                    }
                    if (enemyHasTank == 1 && attackedCardIsTank == 0) {
                        out.cardAttackingError(cardAttacker, cardAttacked, "cardUsesAbility", "Attacked card is not of type 'Tank'.");
                    } else {
                        if (getBoard().get(x1).get(y1).getName().equals("The Ripper")) {
                            getBoard().get(x2).get(y2).subtractAttack(2);
                            getBoard().get(x1).get(y1).setHasAttacked(1);
                        } else if (getBoard().get(x1).get(y1).getName().equals("Miraj")) {
                            int aux = getBoard().get(x1).get(y1).getHealth();
                            getBoard().get(x1).get(y1).setHealth(getBoard().get(x2).get(y2).getHealth());
                            getBoard().get(x2).get(y2).setHealth(aux);
                            getBoard().get(x1).get(y1).setHasAttacked(1);
                        } else { /// The cursed one
                            if (getBoard().get(x2).get(y2).getAttackDamage() == 0) {
                                getBoard().get(x2).remove(y2);
                            } else {
                                int aux = getBoard().get(x2).get(y2).getAttackDamage();
                                getBoard().get(x2).get(y2).setAttackDamage(getBoard().get(x2).get(y2).getHealth());
                                getBoard().get(x2).get(y2).setHealth(aux);
                            }
                            getBoard().get(x1).get(y1).setHasAttacked(1);
                        }
                    }
                } else {
                    out.cardAttackingError(cardAttacker, cardAttacked, "cardUsesAbility", "Attacked card does not belong to the enemy.");
                }
            }
        }
    }

    public void cardUsesAttack(Coordinates cardAttacker, Coordinates cardAttacked, ConvertJson out) {
        int x1 = cardAttacker.getX();
        int y1 = cardAttacker.getY();
        int x2 = cardAttacked.getX();
        int y2 = cardAttacked.getY();

        if (y2 < getBoard().get(x2).size() && y1 < getBoard().get(x1).size()) {
            int playerIdx = getPlayerTurn();
            int attackedCardIsTank = 0;
            if (getBoard().get(x2).get(y2).getName().equals("Goliath")
                    || getBoard().get(x2).get(y2).getName().equals("Warden")) {
                attackedCardIsTank = 1;
            }
            int enemyHasTank = 0;
            if (playerIdx == 0) {
                for (int i = 0; i < getBoard().get(1).size(); i++) {
                    if (getBoard().get(1).get(i).getName().equals("Goliath")
                            || getBoard().get(1).get(i).getName().equals("Warden")) {
                        enemyHasTank = 1;
                        break;
                    }
                }
            } else {
                for (int i = 0; i < getBoard().get(2).size(); i++) {
                    if (getBoard().get(2).get(i).getName().equals("Goliath")
                            || getBoard().get(2).get(i).getName().equals("Warden")) {
                        enemyHasTank = 1;
                        break;
                    }
                }
            }

            if ((playerIdx == 0 && (x2 == 2 || x2 == 3)) || (playerIdx == 1 && (x2 == 0 || x2 == 1))) {
                out.cardAttackingError(cardAttacker, cardAttacked, "cardUsesAttack", "Attacked card does not belong to the enemy.");
            } else if (getBoard().get(x1).get(y1).getHasAttacked() == 1) {
                out.cardAttackingError(cardAttacker, cardAttacked, "cardUsesAttack", "Attacker card has already attacked this turn.");
            } else if (getBoard().get(x1).get(y1).getIsFrozen() == 1) {
                out.cardAttackingError(cardAttacker, cardAttacked, "cardUsesAttack", "Attacker card is frozen.");
            } else if (enemyHasTank == 1 && attackedCardIsTank == 0) {
                out.cardAttackingError(cardAttacker, cardAttacked, "cardUsesAttack", "Attacked card is not of type 'Tank'.");
            } else { // can attack
                getBoard().get(x2).get(y2).subtractHealth(getBoard().get(x1).get(y1).getAttackDamage());
                if (getBoard().get(x2).get(y2).getHealth() <= 0) {
                    getBoard().get(x2).remove(y2);
                }
                getBoard().get(x1).get(y1).setHasAttacked(1);
            }
        }
    }

    public void useAttackHero(Coordinates cardAttacker, ConvertJson out) {
        int x1 = cardAttacker.getX();
        int y1 = cardAttacker.getY();

        if (y1 < getBoard().get(x1).size()) {
            int playerIdx = getPlayerTurn();
            int enemyHasTank = 0;
            if (playerIdx == 0) {
                for (int i = 0; i < getBoard().get(1).size(); i++) {
                    if (getBoard().get(1).get(i).getName().equals("Goliath")
                            || getBoard().get(1).get(i).getName().equals("Warden")) {
                        enemyHasTank = 1;
                        break;
                    }
                }
            } else {
                for (int i = 0; i < getBoard().get(2).size(); i++) {
                    if (getBoard().get(2).get(i).getName().equals("Goliath")
                            || getBoard().get(2).get(i).getName().equals("Warden")) {
                        enemyHasTank = 1;
                        break;
                    }
                }
            }
            if (getBoard().get(x1).get(y1).getIsFrozen() == 1) {
                out.cardAttackingHeroError(cardAttacker, "Attacker card is frozen.");
            } else if (getBoard().get(x1).get(y1).getHasAttacked() == 1) {
                out.cardAttackingHeroError(cardAttacker, "Attacker card has already attacked this turn.");
            } else if (enemyHasTank == 1) {
                out.cardAttackingHeroError(cardAttacker, "Attacked card is not of type 'Tank'.");
            } else {
                int enemyIdx = (playerIdx + 1) % 2;
                getPlayer(enemyIdx).getHero().subtractHealth(getBoard().get(x1).get(y1).getAttackDamage());
                getBoard().get(x1).get(y1).setHasAttacked(1);
                if (getPlayer(0).getHero().getHealth() <= 0 || getPlayer(1).getHero().getHealth() <= 0) {
                    out.gameEnded(playerIdx);
                    getPlayer(playerIdx).addWin();
                    getPlayer(0).addGamePlayed();
                    getPlayer(1).addGamePlayed();
                }
            }
        }
    }

    public void startGame(Input inputData, int i) {
        setPlayerTurn(inputData.getGames().get(i).getStartGame().getStartingPlayer() - 1);
        for (int j = 0; j < 2; j++) {
            getPlayer(j).setTotalMana(1);
            getPlayer(j).setHasPlayed(0);
            getPlayer(j).getHero().setHealth(Player.INITIAL_HERO_HEALTH);
        }
        for (int j = 0 ; j < getBoard().size(); j++) {
            getBoard().get(j).clear();
        }
        setActions(inputData.getGames().get(i).getActions());

        getPlayer(0).setHero(inputData.getGames().get(i).getStartGame().getPlayerOneHero());
        getPlayer(1).setHero(inputData.getGames().get(i).getStartGame().getPlayerTwoHero());

        getPlayer(0).shuffleDeck(inputData.getGames().get(i).getStartGame().getPlayerOneDeckIdx()
                                   , inputData.getGames().get(i).getStartGame().getShuffleSeed());
        getPlayer(1).shuffleDeck(inputData.getGames().get(i).getStartGame().getPlayerTwoDeckIdx()
                                   , inputData.getGames().get(i).getStartGame().getShuffleSeed());
    }
}
