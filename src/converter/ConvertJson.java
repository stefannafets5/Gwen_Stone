package converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.util.ArrayList;
import fileio.Coordinates;
import player.Player;
import card.Card;
import game.Game;

public final class ConvertJson {
    private final ArrayNode out;

    public ConvertJson(final ArrayNode output) {
        this.out = output;

    }

    /**
     * @param player
     */
    public void getTotalGamesPlayed(final Player player) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "getTotalGamesPlayed");
        txt.put("output", player.getGamesPlayed());
        out.add(txt);
    }

    /**
     *
     * @param player
     */
    public void getPlayerOneWins(final Player player) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "getPlayerOneWins");
        txt.put("output", player.getGamesWon());
        out.add(txt);
    }

    /**
     *
     * @param player
     */
    public void getPlayerTwoWins(final Player player) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "getPlayerTwoWins");
        txt.put("output", player.getGamesWon());
        out.add(txt);
    }

    /**
     *
     * @param winnerIdx
     */
    public void gameEnded(final int winnerIdx) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        if (winnerIdx == 0) {
            txt.put("gameEnded", "Player one killed the enemy hero.");
        } else {
            txt.put("gameEnded", "Player two killed the enemy hero.");
        }
        out.add(txt);
    }

    /**
     *
     * @param affectedRow
     */
    public void heroHasAttacked(final int affectedRow) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "useHeroAbility");
        txt.put("affectedRow", affectedRow);
        txt.put("error", "Hero has already attacked this turn.");
        out.add(txt);
    }

    /**
     *
     * @param affectedRow
     * @param error
     */
    public void wrongRowAttacked(final int affectedRow, final String error) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "useHeroAbility");
        txt.put("affectedRow", affectedRow);
        txt.put("error", error);
        out.add(txt);
    }

    /**
     *
     * @param out2Int
     * @param outString
     * @param command
     * @param error
     */
    public void noMana(final int out2Int, final String outString,
                             final String command, final String error) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", command);
        txt.put(outString, out2Int);
        txt.put("error", error);
        out.add(txt);
    }

    /**
     *
     * @param cardIdx
     */
    public void noSpaceToPlace(final int cardIdx) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "placeCard");
        txt.put("handIdx", cardIdx);
        txt.put("error", "Cannot place card on table since row is full.");
        out.add(txt);
    }

    /**
     *
     * @param playerTurn
     */
    public void getPlayerTurn(final int playerTurn) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "getPlayerTurn");
        txt.put("output", playerTurn);
        out.add(txt);
    }

    /**
     *
     * @param mana
     * @param playerIdx
     */
    public void getPlayerMana(final int mana, final int playerIdx) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "getPlayerMana");
        txt.put("playerIdx", playerIdx);
        txt.put("output", mana);
        out.add(txt);
    }

    /**
     *
     * @param cardAttacker
     * @param error
     */
    public void cardAttackingHeroError(final Coordinates cardAttacker, final String error) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "useAttackHero");

        ObjectNode txt2 = mapper.createObjectNode();
        txt2.put("x", cardAttacker.getX());
        txt2.put("y", cardAttacker.getY());
        txt.set("cardAttacker", txt2);

        txt.put("error", error);
        out.add(txt);
    }

    /**
     *
     * @param cardAttacker
     * @param cardAttacked
     * @param command
     * @param error
     */
    public void cardAttackingError(final Coordinates cardAttacker,
                                         final Coordinates cardAttacked,
                                         final String command, final String error) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", command);

        ObjectNode txt2 = mapper.createObjectNode();
        txt2.put("x", cardAttacker.getX());
        txt2.put("y", cardAttacker.getY());
        txt.set("cardAttacker", txt2);

        ObjectNode txt3 = mapper.createObjectNode();
        txt3.put("x", cardAttacked.getX());
        txt3.put("y", cardAttacked.getY());
        txt.set("cardAttacked", txt3);

        txt.put("error", error);
        out.add(txt);
    }

    /**
     *
     * @param board
     */
    public void getCardsOnTable(final ArrayList<ArrayList<Card>> board) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "getCardsOnTable");

        ArrayNode allCards = mapper.createArrayNode();

        for (ArrayList<Card> cards : board) {
            ArrayNode txt1 = mapper.createArrayNode();
            for (Card card : cards) {
                ObjectNode txt2 = mapper.createObjectNode();
                txt2.put("mana", card.getMana());
                txt2.put("attackDamage", card.getAttackDamage());
                txt2.put("health", card.getHealth());
                txt2.put("description", card.getDescription());
                ArrayNode txt3 = mapper.createArrayNode();
                for (int k = 0; k < card.getColors().size(); k++) {
                    txt3.add(card.getColors().get(k));
                }
                txt2.set("colors", txt3);
                txt2.put("name", card.getName());
                txt1.add(txt2);
            }
            allCards.add(txt1);
        }
        txt.set("output", allCards);
        out.add(txt);
    }

    /**
     *
     * @param board
     */
    public void getFrozenCardsOnTable(final ArrayList<ArrayList<Card>> board) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "getFrozenCardsOnTable");

        ArrayNode allCards = mapper.createArrayNode();

        for (ArrayList<Card> cards : board) {
            int frozenCardExists = 0;

            for (Card card : cards) {
                if (card.getIsFrozen() == 1) {
                    frozenCardExists = 1;
                    break;
                }
            }

            if (frozenCardExists == 1) {
                for (Card card : cards) {
                    if (card.getIsFrozen() == 1) {
                        ObjectNode txt2 = mapper.createObjectNode();
                        txt2.put("mana", card.getMana());
                        txt2.put("attackDamage", card.getAttackDamage());
                        txt2.put("health", card.getHealth());
                        txt2.put("description", card.getDescription());
                        ArrayNode txt3 = mapper.createArrayNode();
                        for (int k = 0; k < card.getColors().size(); k++) {
                            txt3.add(card.getColors().get(k));
                        }
                        txt2.set("colors", txt3);
                        txt2.put("name", card.getName());
                        allCards.add(txt2);
                    }
                }
            }
        }
        txt.set("output", allCards);
        out.add(txt);
    }

    /**
     *
     * @param x
     * @param y
     * @param board
     */
    public void getCardAtPosition(final int x, final int y,
                                        final ArrayList<ArrayList<Card>> board) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "getCardAtPosition");
        txt.put("x", x);
        txt.put("y", y);

        if (y >= board.get(x).size()) {
            txt.put("output", "No card available at that position.");
        } else {
            ObjectNode txt2 = mapper.createObjectNode();

            txt2.put("mana", board.get(x).get(y).getMana());
            txt2.put("attackDamage", board.get(x).get(y).getAttackDamage());
            txt2.put("health", board.get(x).get(y).getHealth());
            txt2.put("description", board.get(x).get(y).getDescription());
            ArrayNode txt3 = mapper.createArrayNode();
            for (int i = 0; i < board.get(x).get(y).getColors().size(); i++) {
                txt3.add(board.get(x).get(y).getColors().get(i));
            }
            txt2.set("colors", txt3);
            txt2.put("name", board.get(x).get(y).getName());
            txt.set("output", txt2);
        }
        out.add(txt);
    }

    /**
     *
     * @param deckCards
     * @param playerIdx
     */
    public void getPlayerDeck(final ArrayList<Card> deckCards, final int playerIdx) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "getPlayerDeck");
        txt.put("playerIdx", playerIdx);

        ArrayNode deck = mapper.createArrayNode();

        for (Card deckCard : deckCards) {
            ObjectNode txt2 = mapper.createObjectNode();
            txt2.put("mana", deckCard.getMana());
            txt2.put("attackDamage", deckCard.getAttackDamage());
            txt2.put("health", deckCard.getHealth());
            txt2.put("description", deckCard.getDescription());
            ArrayNode txt3 = mapper.createArrayNode();
            for (int j = 0; j < deckCard.getColors().size(); j++) {
                txt3.add(deckCard.getColors().get(j));
            }
            txt2.set("colors", txt3);
            txt2.put("name", deckCard.getName());
            deck.add(txt2);
        }
        txt.set("output", deck);
        out.add(txt);
    }

    /**
     *
     * @param handCards
     * @param playerIdx
     */
    public void getCardsInHand(final ArrayList<Card> handCards, final int playerIdx) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "getCardsInHand");
        txt.put("playerIdx", playerIdx);

        ArrayNode card = mapper.createArrayNode();

        for (Card handCard : handCards) {
            ObjectNode txt2 = mapper.createObjectNode();
            txt2.put("mana", handCard.getMana());
            txt2.put("attackDamage", handCard.getAttackDamage());
            txt2.put("health", handCard.getHealth());
            txt2.put("description", handCard.getDescription());
            ArrayNode txt3 = mapper.createArrayNode();
            for (int j = 0; j < handCard.getColors().size(); j++) {
                txt3.add(handCard.getColors().get(j));
            }
            txt2.set("colors", txt3);
            txt2.put("name", handCard.getName());
            card.add(txt2);
        }
        txt.set("output", card);
        out.add(txt);
    }

    /**
     *
     * @param hero
     * @param playerIdx
     */
    public void getPlayerHero(final Card hero, final int playerIdx) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "getPlayerHero");
        txt.put("playerIdx", playerIdx);

        ObjectNode txt2 = mapper.createObjectNode();

        txt2.put("mana", hero.getMana());
        txt2.put("description", hero.getDescription());
        ArrayNode txt3 = mapper.createArrayNode();
        for (int i = 0; i < hero.getColors().size(); i++) {
            txt3.add(hero.getColors().get(i));
        }
        txt2.set("colors", txt3);
        txt2.put("name", hero.getName());
        txt2.put("health", hero.getHealth());

        txt.set("output", txt2);
        out.add(txt);
    }

    /**
     *
     * @param table
     */
    public void getPlayerTurn(final Game table) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "getPlayerTurn");
        txt.put("output", table.getPlayerTurn());

        out.add(txt);
    }
}
