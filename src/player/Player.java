package player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import fileio.CardInput;
import card.Card;

public class Player {
    public static final int INITIAL_HERO_HEALTH = 30;
    private int hasPlayed;
    private int totalMana = 0;
    private int gamesWon = 0;
    private int gamesPlayed = 0;
    private final Card hero = new Card();
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
     * @return
     */
    public final Card getHero() {
        return hero;
    }

    /**
     *
     * @param heroInput
     */
    public final void setHero(final CardInput heroInput) {
        hero.setColors(heroInput.getColors());
        hero.setDescription(heroInput.getDescription());
        hero.setMana(heroInput.getMana());
        hero.setHealth(INITIAL_HERO_HEALTH);
        hero.setName(heroInput.getName());
        hero.setHasAttacked(0);
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
     *
     */
    public final void addWin() {
        int currentWins = getGamesWon();
        setGamesWon(currentWins + 1);
    }

    /**
     *
     */
    public final void addGamePlayed() {
        int currentGamesPlayed = getGamesPlayed();
        setGamesPlayed(currentGamesPlayed + 1);
    }

    /**
     *
     * @param mana
     */
    public final void addMana(final int mana) {
        int currentMana = getTotalMana();
        setTotalMana(currentMana + mana);
    }

    /**
     *
     * @param mana
     */
    public final void subtractMana(final int mana) {
        int currentMana = getTotalMana();
        setTotalMana(currentMana - mana);
    }

    /**
     *
     * @param deckNumber
     * @param seed
     */
    public final void shuffleDeck(final int deckNumber, final int seed) {
        currentDeck = new ArrayList<>(decks.get(deckNumber));
        Collections.shuffle(currentDeck, new Random(seed));
        cardsInHand.clear();
        cardsInHand.add(currentDeck.remove(0));
    }

    /**
     *
     */
    public final void fromDeckToHand() {
        cardsInHand.add(currentDeck.remove(0));
    }

    /**
     *
     * @param inputDecks
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
