package Card;

import java.util.ArrayList;

public class Card {
    private int mana;
    private int attackDamage;
    private int health;
    private int isFrozen = 0;
    private int hasAttacked = 0;
    private String description;
    private ArrayList<String> colors;
    private String name;

    public Card() {}

    public int getMana() {
        return mana;
    }

    public void setMana(final int mana) {
        this.mana = mana;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(final int health) {
        this.health = health;
    }

    public int getHasAttacked() { return hasAttacked; }

    public void setHasAttacked(int hasAttacked) { this.hasAttacked = hasAttacked; }

    public int getIsFrozen() {
        return isFrozen;
    }

    public void setIsFrozen(int isFrozen) {
        this.isFrozen = isFrozen;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public void setColors(final ArrayList<String> colors) {
        this.colors = colors;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void subtractHealth(int minusHealth) {
        setHealth(getHealth() - minusHealth);
    }

    @Override
    public String toString() {
        return "CardInput{"
                +  "mana="
                + mana
                +  ", attackDamage="
                + attackDamage
                + ", health="
                + health
                +  ", description='"
                + description
                + '\''
                + ", colors="
                + colors
                + ", name='"
                +  ""
                + name
                + '\''
                + '}';
    }

    public void useAbility() {
        if (this.getName().equals("The Ripper")) {

        } else if (this.getName().equals("Miraj")) {

        }else if (this.getName().equals("The Cursed One")) {

        }else if (this.getName().equals("Disciple")) {

        } else if (this.getName().equals("Lord Royce")) {

        } else if (this.getName().equals("Empress Thorina")) {

        }else if (this.getName().equals("King Mudface")) {

        }else if (this.getName().equals("General Kocioraw")) {

        }
    }
}
