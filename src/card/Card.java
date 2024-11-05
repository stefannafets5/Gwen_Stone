package card;

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

    public Card() { }

    public final int getMana() {
        return mana;
    }

    public final void setMana(final int mana) {
        this.mana = mana;
    }

    public final int getAttackDamage() {
        return attackDamage;
    }

    public final void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public final int getHealth() {
        return health;
    }

    public final void setHealth(final int health) {
        this.health = health;
    }

    public final int getHasAttacked() {
        return hasAttacked;
    }

    public final void setHasAttacked(final int hasAttacked) {
        this.hasAttacked = hasAttacked;
    }

    public final int getIsFrozen() {
        return isFrozen;
    }

    public final void setIsFrozen(final int isFrozen) {
        this.isFrozen = isFrozen;
    }

    public final String getDescription() {
        return description;
    }

    public final void setDescription(final String description) {
        this.description = description;
    }

    public final ArrayList<String> getColors() {
        return colors;
    }

    public final void setColors(final ArrayList<String> colors) {
        this.colors = colors;
    }

    public final String getName() {
        return name;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    /**
     * @param minusHealth
     */
    public final void subtractHealth(final int minusHealth) {
        setHealth(getHealth() - minusHealth);
    }

    /**
     * @param plusHealth
     */
    public final void addHealth(final int plusHealth) {
        setHealth(getHealth() + plusHealth);
    }

    /**
     * @param minusAttack
     */
    public final void subtractAttack(final int minusAttack) {
        setAttackDamage(getAttackDamage() - minusAttack);
        if (getAttackDamage() < 0) {
            setAttackDamage(0);
        }
    }

    /**
     * @param plusAttack
     */
    public final void addAttack(final int plusAttack) {
        setAttackDamage(getAttackDamage() + plusAttack);
    }

    /**
     * @return
     */
    public final Card cloneCard() {
        Card copy = new Card();
        copy.setMana(this.mana);
        copy.setHealth(this.health);
        copy.setName(this.name);
        copy.setColors(this.colors);
        copy.setIsFrozen(this.isFrozen);
        copy.setHasAttacked(this.hasAttacked);
        copy.setDescription(this.description);
        copy.setAttackDamage(this.attackDamage);
        return copy;
    }

    @Override
    public final String toString() {
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
}
