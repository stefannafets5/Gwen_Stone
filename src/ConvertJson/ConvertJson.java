package ConvertJson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.util.ArrayList;
import Card.Card;
import Game.Game;

public class ConvertJson {
    private ArrayNode out;

    public ConvertJson(ArrayNode output) {
        this.out = output;

    }

    public void getPlayerTurn(int playerTurn) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "getPlayerTurn");
        txt.put("output", playerTurn);
        out.add(txt);
    }

    public void getPlayerDeck(ArrayList<Card> deckCards, int playerNumber) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "getPlayerDeck");
        txt.put("playerIdx", playerNumber);

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

    public void getPlayerHero(Card hero, int playerNumber) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "getPlayerHero");
        txt.put("playerIdx", playerNumber);

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

    public void getCardsInHand(ArrayList<Card> handCards, int playerNumber) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "getCardsInHand");
        txt.put("playerIdx", playerNumber);

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
}
