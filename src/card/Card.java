package card;

import java.util.ArrayList;

public class Card {
    private int isTank;
    private int mana;
    private int attackDamage;
    private int health;
    private int isFrozen;
    private int hasAttacked;
    private String description;
    private ArrayList<String> colors;
    private String name;

    public Card() {
        this.isFrozen = 0;
        this.hasAttacked = 0;
    }

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

    public final int getIsTank() {
        return isTank;
    }

    public final void setIsTank(final int isTank) {
        this.isTank = isTank;
    }

    /**
     * @param minusHealth health to be subtracted
     */
    public final void subtractHealth(final int minusHealth) {
        setHealth(getHealth() - minusHealth);
    }

    /**
     * @param plusHealth health to be added
     */
    public final void addHealth(final int plusHealth) {
        setHealth(getHealth() + plusHealth);
    }

    /**
     * @param minusAttack attack to be subtracted
     */
    public final void subtractAttack(final int minusAttack) {
        setAttackDamage(getAttackDamage() - minusAttack);
        if (getAttackDamage() < 0) {
            setAttackDamage(0);
        }
    }

    /**
     * @param plusAttack attack to be added
     */
    public final void addAttack(final int plusAttack) {
        setAttackDamage(getAttackDamage() + plusAttack);
    }

    /**
     *  Attacked card loses health equal to the attackers damage.
     * @param attacked card being affected by attack
     */
    public final void useAttack(final Card attacked) {
        attacked.subtractHealth(this.getAttackDamage());
        setHasAttacked(1);
    }

    /**
     *
     * @param attacked card being affected by ability
     */
    public void useAbility(final Card attacked) {
    }

    /**
     *
     * @param row row being affected by ability
     * @param board current board with cards
     */
    public void useAbility(final int row, final ArrayList<ArrayList<Card>> board) {

    }

    /**
     * @return never returns, method is here just to be overridden
     */
    public Card cloneCard() {
        return new Card();
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
                + name
                + '\''
                + '}';
    }
}
