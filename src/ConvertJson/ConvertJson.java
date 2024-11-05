package ConvertJson;

import Player.Player;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.util.ArrayList;
import Card.Card;
import Game.Game;
import fileio.Coordinates;

public class ConvertJson {
    private ArrayNode out;

    public ConvertJson(ArrayNode output) {
        this.out = output;

    }

    public void getTotalGamesPlayed(Player player) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "getTotalGamesPlayed");
        txt.put("output", player.getGamesPlayed());
        out.add(txt);
    }

    public void getPlayerOneWins(Player player) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "getPlayerOneWins");
        txt.put("output", player.getGamesWon());
        out.add(txt);
    }

    public void getPlayerTwoWins(Player player) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "getPlayerTwoWins");
        txt.put("output", player.getGamesWon());
        out.add(txt);
    }

    public void gameEnded(int winnerIdx) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        if (winnerIdx == 0) {
            txt.put("gameEnded", "Player one killed the enemy hero.");
        } else {
            txt.put("gameEnded", "Player two killed the enemy hero.");
        }
        out.add(txt);
    }

    public void heroHasAttacked(int affectedRow) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "useHeroAbility");
        txt.put("affectedRow", affectedRow);
        txt.put("error", "Hero has already attacked this turn.");
        out.add(txt);
    }

    public void wrongRowAttacked(int affectedRow, String error) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "useHeroAbility");
        txt.put("affectedRow", affectedRow);
        txt.put("error", error);
        out.add(txt);
    }

    public void noMana(int secondLineInt, String secondLine, String command, String error) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", command);
        txt.put(secondLine, secondLineInt);
        txt.put("error", error);
        out.add(txt);
    }

    public void noSpaceToPlace(int cardIdx) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "placeCard");
        txt.put("handIdx", cardIdx);
        txt.put("error", "Cannot place card on table since row is full.");
        out.add(txt);
    }

    public void getPlayerTurn(int playerTurn) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "getPlayerTurn");
        txt.put("output", playerTurn);
        out.add(txt);
    }

    public void getPlayerMana(int mana, int playerIdx) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "getPlayerMana");
        txt.put("playerIdx", playerIdx);
        txt.put("output", mana);
        out.add(txt);
    }

    public void cardAttackingHeroError(Coordinates cardAttacker, String error) {
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

    public void cardAttackingError(Coordinates cardAttacker, Coordinates cardAttacked, String command, String error) {
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

    public void getCardsOnTable(ArrayList<ArrayList<Card>> board) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "getCardsOnTable");

        ArrayNode allCards = mapper.createArrayNode();

        for (int i = 0; i < board.size(); i++) {
            ArrayNode txt1 = mapper.createArrayNode();
            for (int j = 0; j < board.get(i).size(); j++) {
                ObjectNode txt2 = mapper.createObjectNode();
                txt2.put("mana", board.get(i).get(j).getMana());
                txt2.put("attackDamage", board.get(i).get(j).getAttackDamage());
                txt2.put("health", board.get(i).get(j).getHealth());
                txt2.put("description", board.get(i).get(j).getDescription());
                ArrayNode txt3 = mapper.createArrayNode();
                for (int k = 0; k < board.get(i).get(j).getColors().size(); k++) {
                    txt3.add(board.get(i).get(j).getColors().get(k));
                }
                txt2.put("colors", txt3);
                txt2.put("name", board.get(i).get(j).getName());
                txt1.add(txt2);
            }
            allCards.add(txt1);
        }
        txt.set("output", allCards);
        out.add(txt);
    }

    public void getFrozenCardsOnTable(ArrayList<ArrayList<Card>> board) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "getFrozenCardsOnTable");

        ArrayNode allCards = mapper.createArrayNode();

        for (int i = 0; i < board.size(); i++) {
            int frozenCardExists = 0;

            for (int j = 0; j < board.get(i).size(); j++) {
                if (board.get(i).get(j).getIsFrozen() == 1) {
                    frozenCardExists = 1;
                    break;
                }
            }

            if (frozenCardExists == 1) {
                for (int j = 0; j < board.get(i).size(); j++) {
                    if (board.get(i).get(j).getIsFrozen() == 1) {
                        ObjectNode txt2 = mapper.createObjectNode();
                        txt2.put("mana", board.get(i).get(j).getMana());
                        txt2.put("attackDamage", board.get(i).get(j).getAttackDamage());
                        txt2.put("health", board.get(i).get(j).getHealth());
                        txt2.put("description", board.get(i).get(j).getDescription());
                        ArrayNode txt3 = mapper.createArrayNode();
                        for (int k = 0; k < board.get(i).get(j).getColors().size(); k++) {
                            txt3.add(board.get(i).get(j).getColors().get(k));
                        }
                        txt2.put("colors", txt3);
                        txt2.put("name", board.get(i).get(j).getName());
                        allCards.add(txt2);
                    }
                }
            }
        }
        txt.set("output", allCards);
        out.add(txt);
    }

    public void getCardAtPosition(int x, int y, ArrayList<ArrayList<Card>> board) {
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
            txt2.put("colors", txt3);
            txt2.put("name", board.get(x).get(y).getName());
            txt.set("output", txt2);
        }
        out.add(txt);
    }

    public void getPlayerDeck(ArrayList<Card> deckCards, int playerIdx) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "getPlayerDeck");
        txt.put("playerIdx", playerIdx);

        ArrayNode deck = mapper.createArrayNode();

        for (int i = 0; i < deckCards.size(); i++) {
            ObjectNode txt2 = mapper.createObjectNode();
            txt2.put("mana", deckCards.get(i).getMana());
            txt2.put("attackDamage", deckCards.get(i).getAttackDamage());
            txt2.put("health", deckCards.get(i).getHealth());
            txt2.put("description", deckCards.get(i).getDescription());
            ArrayNode txt3 = mapper.createArrayNode();
                for (int j = 0; j < deckCards.get(i).getColors().size(); j++) {
                    txt3.add(deckCards.get(i).getColors().get(j));
                }
            txt2.put("colors", txt3);
            txt2.put("name", deckCards.get(i).getName());
            deck.add(txt2);
        }
        txt.set("output", deck);
        out.add(txt);
    }

    public void getCardsInHand(ArrayList<Card> handCards, int playerIdx) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "getCardsInHand");
        txt.put("playerIdx", playerIdx);

        ArrayNode card = mapper.createArrayNode();

        for (int i = 0; i < handCards.size(); i++) {
            ObjectNode txt2 = mapper.createObjectNode();
            txt2.put("mana", handCards.get(i).getMana());
            txt2.put("attackDamage", handCards.get(i).getAttackDamage());
            txt2.put("health", handCards.get(i).getHealth());
            txt2.put("description", handCards.get(i).getDescription());
            ArrayNode txt3 = mapper.createArrayNode();
            for (int j = 0; j < handCards.get(i).getColors().size(); j++) {
                txt3.add(handCards.get(i).getColors().get(j));
            }
            txt2.put("colors", txt3);
            txt2.put("name", handCards.get(i).getName());
            card.add(txt2);
        }
        txt.set("output", card);
        out.add(txt);
    }

    public void getPlayerHero(Card hero, int playerIdx) {
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
        txt2.put("colors", txt3);
        txt2.put("name", hero.getName());
        txt2.put("health", hero.getHealth());

        txt.set("output", txt2);
        out.add(txt);
    }

    public void getPlayerTurn(Game table) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "getPlayerTurn");
        txt.put("output", table.getPlayerTurn());

        out.add(txt);
    }
}
