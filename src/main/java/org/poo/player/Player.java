package org.poo.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import org.poo.fileio.CardInput;
import org.poo.card.Card;
import org.poo.card.Sentinel;
import org.poo.card.Berserker;
import org.poo.card.Goliath;
import org.poo.card.Warden;
import org.poo.card.TheRipper;
import org.poo.card.Miraj;
import org.poo.card.TheCursedOne;
import org.poo.card.Disciple;
import org.poo.card.LordRoyce;
import org.poo.card.EmpressThorina;
import org.poo.card.KingMudface;
import org.poo.card.GeneralKocioraw;

public class Player {
    public static final int INITIAL_HERO_HEALTH = 30;
    private int hasPlayed;
    private int totalMana = 0;
    private int gamesWon = 0;
    private int gamesPlayed = 0;
    private Card hero;
    private final ArrayList<Card> cardsInHand;
    private ArrayList<Card> currentDeck;
    private ArrayList<ArrayList<Card>> decks;

    public Player(final int decksNumber) {
        this.hasPlayed = 0;
        this.cardsInHand = new ArrayList<>();
        this.currentDeck = new ArrayList<>();
        this.decks = new ArrayList<>();

        for (int i = 0; i < decksNumber; i++) {
            decks.add(new ArrayList<>());
        }
    }

    /**
     *
     * @return current hero
     */
    public final Card getHero() {
        return hero;
    }

    /**
     *  Creates a specific hero for the player based on the name given at input.
     * @param heroInput input given
     */
    public final void setHero(final CardInput heroInput) {
        if ("Lord Royce".equals(heroInput.getName())) {
            this.hero = new LordRoyce();
        } else if ("Empress Thorina".equals(heroInput.getName())) {
            this.hero = new EmpressThorina();
        } else if ("King Mudface".equals(heroInput.getName())) {
            this.hero = new KingMudface();
        } else if ("General Kocioraw".equals(heroInput.getName())) {
            this.hero = new GeneralKocioraw();
        }
        this.hero.setColors(heroInput.getColors());
        this.hero.setDescription(heroInput.getDescription());
        this.hero.setMana(heroInput.getMana());
        this.hero.setHealth(INITIAL_HERO_HEALTH);
        this.hero.setName(heroInput.getName());
        this.hero.setHasAttacked(0);
    }

    public final int getHasPlayed() {
        return hasPlayed;
    }

    public final void setHasPlayed(final int hasPlayed) {
        this.hasPlayed = hasPlayed;
    }

    public final int getTotalMana() {
        return totalMana;
    }

    public final void setTotalMana(final int totalMana) {
        this.totalMana = totalMana;
    }

    public final int getGamesWon() {
        return gamesWon;
    }

    public final void setGamesWon(final int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public final int getGamesPlayed() {
        return gamesPlayed;
    }

    public final void setGamesPlayed(final int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public final ArrayList<Card> getCardsInHand() {
        return cardsInHand;
    }

    public final ArrayList<Card> getCurrentDeck() {
        return currentDeck;
    }

    public final ArrayList<ArrayList<Card>> getDecks() {
        return decks;
    }

    public final void setDecks(final ArrayList<ArrayList<Card>> decks) {
        this.decks = decks;
    }

    /**
     * increments wins of a player
     */
    public final void addWin() {
        int currentWins = getGamesWon();
        setGamesWon(currentWins + 1);
    }

    /**
     * increments games played by a player
     */
    public final void addGamePlayed() {
        int currentGamesPlayed = getGamesPlayed();
        setGamesPlayed(currentGamesPlayed + 1);
    }

    /**
     *
     * @param mana mana to be added
     */
    public final void addMana(final int mana) {
        int currentMana = getTotalMana();
        setTotalMana(currentMana + mana);
    }

    /**
     *
     * @param mana mana to be subtracted
     */
    public final void subtractMana(final int mana) {
        int currentMana = getTotalMana();
        setTotalMana(currentMana - mana);
    }

    /**
     *  Randomizes the current deck using the seed given and adds the first card
     *  from the deck to the hand of the player.
     * @param deckNumber deck to be randomized
     * @param seed random seed given
     */
    public final void shuffleDeck(final int deckNumber, final int seed) {
        currentDeck = new ArrayList<>(decks.get(deckNumber));
        Collections.shuffle(currentDeck, new Random(seed));
        cardsInHand.clear();

        Card firstCard = currentDeck.remove(0);
        Card specificCard = getCard(firstCard);
        if (specificCard != null) {
            specificCard.setColors(firstCard.getColors());
            specificCard.setDescription(firstCard.getDescription());
            specificCard.setAttackDamage(firstCard.getAttackDamage());
            specificCard.setMana(firstCard.getMana());
            specificCard.setHealth(firstCard.getHealth());
            specificCard.setName(firstCard.getName());
        }
        cardsInHand.add(specificCard);
    }

    /**
     *  The input is a card of type "Card" and the method returns the specific type
     *  of the card given.
     * @param firstCard the card we want to know the type of
     * @return the type of card "firstCard" is
     */
    private static Card getCard(final Card firstCard) {
        Card specificCard = null;
        if ("Sentinel".equals(firstCard.getName())) {
            specificCard = new Sentinel();
        } else if ("Berserker".equals(firstCard.getName())) {
            specificCard = new Berserker();
        } else if ("Goliath".equals(firstCard.getName())) {
            specificCard = new Goliath();
        } else if ("Warden".equals(firstCard.getName())) {
            specificCard = new Warden();
        } else if ("The Ripper".equals(firstCard.getName())) {
            specificCard = new TheRipper();
        } else if ("Miraj".equals(firstCard.getName())) {
            specificCard = new Miraj();
        } else if ("The Cursed One".equals(firstCard.getName())) {
            specificCard = new TheCursedOne();
        } else if ("Disciple".equals(firstCard.getName())) {
            specificCard = new Disciple();
        }
        return specificCard;
    }

    /**
     *  The player draws a card: the card is extracted from his current deck
     *  and goes in his hand.
     */
    public final void fromDeckToHand() {
        Card firstCard = currentDeck.remove(0);
        Card specificCard = getCard(firstCard);
        if (specificCard != null) {
            specificCard.setColors(firstCard.getColors());
            specificCard.setDescription(firstCard.getDescription());
            specificCard.setAttackDamage(firstCard.getAttackDamage());
            specificCard.setMana(firstCard.getMana());
            specificCard.setHealth(firstCard.getHealth());
            specificCard.setName(firstCard.getName());
        }
        cardsInHand.add(specificCard);
    }

    /**
     *  Copies decks from input to players.
     * @param inputDecks decks given from input
     */
    public final void copyAllDecks(final ArrayList<ArrayList<CardInput>> inputDecks) {
        for (int i = 0; i < inputDecks.size(); i++) {
            ArrayList<CardInput> inputCards = inputDecks.get(i);

            for (CardInput inputCard : inputCards) {
                Card card = new Card();
                card.setAttackDamage(inputCard.getAttackDamage());
                card.setColors(inputCard.getColors());
                card.setDescription(inputCard.getDescription());
                card.setMana(inputCard.getMana());
                card.setHealth(inputCard.getHealth());
                card.setName(inputCard.getName());

                decks.get(i).add(card);
            }
        }
    }
}
