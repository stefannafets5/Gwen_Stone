package game;

import card.Card;
import player.Player;
import converter.ConvertJson;
import fileio.ActionsInput;
import fileio.Coordinates;
import fileio.Input;


import java.util.ArrayList;

public class Game {
    public static final int BOARD_ROWS = 4;
    public static final int MAX_MANA_PER_TURN = 10;
    public static final int MAX_ROW_SIZE = 5;
    public static final int PLAYER_0_FRONT_ROW = 3;
    private int gameTurn = 1;
    private final Player[] players = new Player[2];
    private int playerTurn;
    private ArrayList<ActionsInput> actions;
    private final ArrayList<ArrayList<Card>> board;

    public Game(final int decksNumber1, final int decksNumber2) {
        players[0] = new Player(decksNumber1);
        players[1] = new Player(decksNumber2);
        this.board = new ArrayList<>();

        for (int i = 0; i < BOARD_ROWS; i++) {
            board.add(new ArrayList<>());
        }
    }

    public final int getGameTurn() {
        return gameTurn;
    }

    public final void setGameTurn(final int gameTurn) {
        this.gameTurn = gameTurn;
    }

    /**
     *
     * @param i
     * @return
     */
    public final Player getPlayer(final int i) {
        return players[i];
    }

    public final ArrayList<ActionsInput> getActions() {
        return actions;
    }

    public final void setActions(final ArrayList<ActionsInput> actions) {
        this.actions = actions;
    }

    public final int getPlayerTurn() {
        return playerTurn;
    }

    public final void setPlayerTurn(final int playerTurn) {
        this.playerTurn = playerTurn;
    }

    public final ArrayList<ArrayList<Card>> getBoard() {
        return board;
    }

    /**
     *
     * @param inputData
     */
    public final void copyPlayerDecks(final Input inputData) {
        getPlayer(0).copyAllDecks(inputData.getPlayerOneDecks().getDecks());
        getPlayer(1).copyAllDecks(inputData.getPlayerTwoDecks().getDecks());
    }

    /**
     *
     */
    public final void endTurn() {
        int playerIdx = getPlayerTurn();
        int nextPlayer = (playerIdx + 1) % 2;
        getPlayer(playerIdx).setHasPlayed(1);

        if (getPlayer(0).getHasPlayed() == 1 && getPlayer(1).getHasPlayed() == 1) {
            getPlayer(0).setHasPlayed(0);
            getPlayer(1).setHasPlayed(0);
            int aux = getGameTurn();
            setGameTurn(aux + 1);

            if (getGameTurn() <= MAX_MANA_PER_TURN) {
                getPlayer(0).addMana(getGameTurn());
                getPlayer(1).addMana(getGameTurn());
            } else {
                getPlayer(0).addMana(MAX_MANA_PER_TURN);
                getPlayer(1).addMana(MAX_MANA_PER_TURN);
            }

            if (!getPlayer(0).getCurrentDeck().isEmpty()) {
                getPlayer(0).fromDeckToHand();
            }
            if (!getPlayer(1).getCurrentDeck().isEmpty()) {
                getPlayer(1).fromDeckToHand();
            }
        }
        if (playerIdx == 0) {
            for (int i = 2; i < BOARD_ROWS; i++) {
                for (int j = 0; j < getBoard().get(i).size(); j++) {
                    getBoard().get(i).get(j).setIsFrozen(0);
                    getBoard().get(i).get(j).setHasAttacked(0);
                    getPlayer(0).getHero().setHasAttacked(0);
                }
            }
        } else { // playerIdx = 1
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < getBoard().get(i).size(); j++) {
                    getBoard().get(i).get(j).setIsFrozen(0);
                    getBoard().get(i).get(j).setHasAttacked(0);
                    getPlayer(1).getHero().setHasAttacked(0);
                }
            }
        }
        setPlayerTurn(nextPlayer);
    }

    /**
     *
     * @param cardIdx
     * @param out
     */
    public final void placeCard(final int cardIdx, final ConvertJson out) {
        int playerIdx = getPlayerTurn();
        Card placedCard = getPlayer(playerIdx).getCardsInHand().get(cardIdx);
        int manaCost = placedCard.getMana();

        if (getPlayer(playerIdx).getTotalMana() >= manaCost) {
            int row = getRow(placedCard, playerIdx);
            if (getBoard().get(row).size() == MAX_ROW_SIZE) {
                out.noSpaceToPlace(cardIdx);
            } else {
                getPlayer(playerIdx).subtractMana(manaCost);
                getBoard().get(row).add(placedCard.cloneCard());
                getPlayer(playerIdx).getCardsInHand().remove(cardIdx);
            }
        } else {
            out.noMana(cardIdx, "handIdx", "placeCard",
                    "Not enough mana to place card on table.");
        }
    }

    private static int getRow(final Card placedCard, final int playerIdx) {
        int row;
        if (placedCard.getName().equals("Goliath")
                || placedCard.getName().equals("Warden")
                || placedCard.getName().equals("The Ripper")
                || placedCard.getName().equals("Miraj")) {
            row = 1;
            if (playerIdx == 0) {
                row = 2;
            }
        } else {
            row = 0;
            if (playerIdx == 0) {
                row = PLAYER_0_FRONT_ROW;
            }
        }
        return row;
    }

    /**
     *
     * @param affectedRow
     * @param out
     */
    public final void useHeroAbility(final int affectedRow, final ConvertJson out) {
        Card currentHero = getPlayer(getPlayerTurn()).getHero();
        int manaCost = currentHero.getMana();
        if (getPlayer(getPlayerTurn()).getTotalMana() >= manaCost) {
            if (currentHero.getHasAttacked() == 0) {
                if (currentHero.getName().equals("Lord Royce")
                        || currentHero.getName().equals("Empress Thorina")) {
                    if ((getPlayerTurn() == 0
                            && (affectedRow == 2 || affectedRow == PLAYER_0_FRONT_ROW))
                            || (getPlayerTurn() == 1 && (affectedRow == 0 || affectedRow == 1))) {
                        out.wrongRowAttacked(affectedRow,
                                "Selected row does not belong to the enemy.");
                    } else {
                        getPlayer(getPlayerTurn()).subtractMana(manaCost);
                        currentHero.useAbility(affectedRow, getBoard());
                    }
                } else {
                    if ((getPlayerTurn() == 1 && (affectedRow == 2
                            || affectedRow == PLAYER_0_FRONT_ROW))
                            || (getPlayerTurn() == 0
                            && (affectedRow == 0 || affectedRow == 1))) {
                        out.wrongRowAttacked(affectedRow,
                                "Selected row does not belong to the current player.");
                    } else {
                        getPlayer(getPlayerTurn()).subtractMana(manaCost);
                        currentHero.useAbility(affectedRow, getBoard());
                    }
                }
            } else {
                out.heroHasAttacked(affectedRow);
            }
        } else {
            out.noMana(affectedRow, "affectedRow", "useHeroAbility",
                    "Not enough mana to use hero's ability.");
        }
    }

    /**
     *
     * @param cardAttacker
     * @param cardAttacked
     * @param out
     */
    public final void cardUsesAbility(final Coordinates cardAttacker,
                                      final Coordinates cardAttacked,
                                      final ConvertJson out) {
        int x1 = cardAttacker.getX();
        int y1 = cardAttacker.getY();
        int x2 = cardAttacked.getX();
        int y2 = cardAttacked.getY();

        if (y2 < getBoard().get(x2).size() && y1 < getBoard().get(x1).size()) {
            int playerIdx = getPlayerTurn();
            int attackedCardIsTank = getBoard().get(x2).get(y2).getIsTank();
            int enemyHasTank = getEnemyHasTank(playerIdx);

            if (getBoard().get(x1).get(y1).getIsFrozen() == 1) {
                out.cardAttackingError(cardAttacker, cardAttacked,
                        "cardUsesAbility", "Attacker card is frozen.");
            } else if (getBoard().get(x1).get(y1).getHasAttacked() == 1) {
                out.cardAttackingError(cardAttacker, cardAttacked,
                        "cardUsesAbility", "Attacker card has already attacked this turn.");
            } else if (getBoard().get(x1).get(y1).getName().equals("Disciple")) {
                if ((playerIdx == 0 && (x2 == 2 || x2 == PLAYER_0_FRONT_ROW))
                        || (playerIdx == 1 && (x2 == 0 || x2 == 1))) {
                    getBoard().get(x1).get(y1).useAbility(getBoard().get(x2).get(y2));
                } else {
                    out.cardAttackingError(cardAttacker, cardAttacked,
                            "cardUsesAbility",
                            "Attacked card does not belong to the current player.");
                }
            } else if (getBoard().get(x1).get(y1).getName().equals("The Ripper")
                       || getBoard().get(x1).get(y1).getName().equals("Miraj")
                       || getBoard().get(x1).get(y1).getName().equals("The Cursed One")) {
                if ((playerIdx == 0 && (x2 == 0 || x2 == 1))
                        || (playerIdx == 1 && (x2 == 2 || x2 == PLAYER_0_FRONT_ROW))) {
                    if ((enemyHasTank == 1) && (attackedCardIsTank == 0)) {
                        out.cardAttackingError(cardAttacker, cardAttacked,
                                "cardUsesAbility", "Attacked card is not of type 'Tank'.");
                    } else {
                        getBoard().get(x1).get(y1).useAbility(getBoard().get(x2).get(y2));
                        if (getBoard().get(x2).get(y2).getHealth() == 0) {
                            getBoard().get(x2).remove(y2);
                        }
                    }
                } else {
                    out.cardAttackingError(cardAttacker, cardAttacked, "cardUsesAbility",
                            "Attacked card does not belong to the enemy.");
                }
            }
        }
    }

    /**
     *
     * @param cardAttacker
     * @param cardAttacked
     * @param out
     */
    public final void cardUsesAttack(final Coordinates cardAttacker,
                                     final Coordinates cardAttacked,
                                     final ConvertJson out) {
        int x1 = cardAttacker.getX();
        int y1 = cardAttacker.getY();
        int x2 = cardAttacked.getX();
        int y2 = cardAttacked.getY();

        if (y2 < getBoard().get(x2).size() && y1 < getBoard().get(x1).size()) {
            int playerIdx = getPlayerTurn();
            int attackedCardIsTank = getBoard().get(x2).get(y2).getIsTank();
            int enemyHasTank = getEnemyHasTank(playerIdx);

            if ((playerIdx == 0 && (x2 == 2 || x2 == PLAYER_0_FRONT_ROW))
                    || (playerIdx == 1 && (x2 == 0 || x2 == 1))) {
                out.cardAttackingError(cardAttacker, cardAttacked,
                        "cardUsesAttack", "Attacked card does not belong to the enemy.");
            } else if (getBoard().get(x1).get(y1).getHasAttacked() == 1) {
                out.cardAttackingError(cardAttacker, cardAttacked,
                        "cardUsesAttack", "Attacker card has already attacked this turn.");
            } else if (getBoard().get(x1).get(y1).getIsFrozen() == 1) {
                out.cardAttackingError(cardAttacker, cardAttacked,
                        "cardUsesAttack", "Attacker card is frozen.");
            } else if ((enemyHasTank == 1) && (attackedCardIsTank == 0)) {
                out.cardAttackingError(cardAttacker, cardAttacked,
                        "cardUsesAttack", "Attacked card is not of type 'Tank'.");
            } else { // can attack
                getBoard().get(x1).get(y1).useAttack(getBoard().get(x2).get(y2));
                if (getBoard().get(x2).get(y2).getHealth() <= 0) {
                    getBoard().get(x2).remove(y2);
                }
            }
        }
    }

    /**
     *
     * @param cardAttacker
     * @param out
     */
    public final void useAttackHero(final Coordinates cardAttacker, final ConvertJson out) {
        int x1 = cardAttacker.getX();
        int y1 = cardAttacker.getY();

        if (y1 < getBoard().get(x1).size()) {
            int playerIdx = getPlayerTurn();
            int enemyHasTank = getEnemyHasTank(playerIdx);

            if (getBoard().get(x1).get(y1).getIsFrozen() == 1) {
                out.cardAttackingHeroError(cardAttacker, "Attacker card is frozen.");
            } else if (getBoard().get(x1).get(y1).getHasAttacked() == 1) {
                out.cardAttackingHeroError(cardAttacker,
                        "Attacker card has already attacked this turn.");
            } else if (enemyHasTank == 1) {
                out.cardAttackingHeroError(cardAttacker,
                        "Attacked card is not of type 'Tank'.");
            } else {
                int enemyIdx = (playerIdx + 1) % 2;
                getBoard().get(x1).get(y1).useAttack(getPlayer(enemyIdx).getHero());
                if (getPlayer(0).getHero().getHealth() <= 0
                        || getPlayer(1).getHero().getHealth() <= 0) {
                    out.gameEnded(playerIdx);
                    getPlayer(playerIdx).addWin();
                    getPlayer(0).addGamePlayed();
                    getPlayer(1).addGamePlayed();
                }
            }
        }
    }

    /**
     *
     * @param playerIdx
     * @return
     */
    private int getEnemyHasTank(final int playerIdx) {
        int enemyHasTank = 0;
        if (playerIdx == 0) {
            for (int i = 0; i < getBoard().get(1).size(); i++) {
                if (getBoard().get(1).get(i).getIsTank() == 1) {
                    enemyHasTank = 1;
                    break;
                }
            }
        } else {
            for (int i = 0; i < getBoard().get(2).size(); i++) {
                if (getBoard().get(2).get(i).getIsTank() == 1) {
                    enemyHasTank = 1;
                    break;
                }
            }
        }
        return enemyHasTank;
    }

    /**
     *
     * @param inputData
     * @param i
     */
    public final void startGame(final Input inputData, final int i) {
        setPlayerTurn(inputData.getGames().get(i).getStartGame().getStartingPlayer() - 1);
        for (int j = 0; j < 2; j++) {
            getPlayer(j).setTotalMana(1);
            getPlayer(j).setHasPlayed(0);
        }
        for (int j = 0; j < getBoard().size(); j++) {
            getBoard().get(j).clear();
        }
        setGameTurn(1);
        setActions(inputData.getGames().get(i).getActions());

        getPlayer(0).setHero(inputData.getGames().get(i).getStartGame().getPlayerOneHero());
        getPlayer(1).setHero(inputData.getGames().get(i).getStartGame().getPlayerTwoHero());

        getPlayer(0).shuffleDeck(inputData.getGames().get(i).getStartGame().getPlayerOneDeckIdx(),
                inputData.getGames().get(i).getStartGame().getShuffleSeed());
        getPlayer(1).shuffleDeck(inputData.getGames().get(i).getStartGame().getPlayerTwoDeckIdx(),
                inputData.getGames().get(i).getStartGame().getShuffleSeed());
    }
}
