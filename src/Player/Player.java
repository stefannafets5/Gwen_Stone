package Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import Card.Card;

import fileio.CardInput;


public class Player {
    public static final int INITIAL_HERO_HEALTH = 30;
    private int decksNumber;
    private int cardsInDeckNumber;
    private int gamesWon = 0;
    private int gamesPlayed = 0;
    private Card hero = new Card();
    private ArrayList<Card> cardsInHand;
    private ArrayList<Card> currentDeck;
    private ArrayList<ArrayList<Card>> decks;

    public Player(int decksNumber, int cardsInDeckNumber) {
        this.decksNumber = decksNumber;
        this.cardsInDeckNumber = cardsInDeckNumber;
        this.cardsInHand = new ArrayList<Card>();
        this.currentDeck = new ArrayList<Card>();
        this.decks = new ArrayList<>();

        for (int i = 0; i < decksNumber; i++) {
            decks.add(new ArrayList<>());
        }
    }

    public Card getHero() {
        return hero;
    }

    public void setHero(CardInput heroInput) {
        hero.setColors(heroInput.getColors());
        hero.setDescription(heroInput.getDescription());
        hero.setMana(heroInput.getMana());
        hero.setHealth(INITIAL_HERO_HEALTH);
        hero.setName(heroInput.getName());
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getDecksNumber() {
        return decksNumber;
    }

    public void setDecksNumber(int decksNumber) {
        this.decksNumber = decksNumber;
    }

    public int getCardsInDeckNumber() {
        return cardsInDeckNumber;
    }

    public void setCardsInDeckNumber(int cardsInDeckNumber) {
        this.cardsInDeckNumber = cardsInDeckNumber;
    }

    public ArrayList<Card> getCurrentDeck() {
        return currentDeck;
    }

    public void setCurrentDeck(ArrayList<Card> currentDeck) {
        this.currentDeck = currentDeck;
    }

    public ArrayList<ArrayList<Card>> getDecks() {
        return decks;
    }

    public void setDecks(ArrayList<ArrayList<Card>> decks) {
        this.decks = decks;
    }

    public void shuffleDeck(int deckNumber, int seed) {
        currentDeck = new ArrayList<>(decks.get(deckNumber));
        Collections.shuffle(currentDeck, new Random(seed));
    }

    public void copyAllDecks(ArrayList<ArrayList<CardInput>> InputDecks) {
        for (int i = 0; i < InputDecks.size(); i++) {
            ArrayList<CardInput> inputCards = InputDecks.get(i);

            for (int j = 0; j < inputCards.size(); j++) {
                Card card = new Card();
                card.setAttackDamage(inputCards.get(j).getAttackDamage());
                card.setColors(inputCards.get(j).getColors());
                card.setDescription(inputCards.get(j).getDescription());
                card.setMana(inputCards.get(j).getMana());
                card.setHealth(inputCards.get(j).getHealth());
                card.setName(inputCards.get(j).getName());

                decks.get(i).add(card);
            }
        }

    }
}
