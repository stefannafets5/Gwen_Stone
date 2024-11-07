package card;

public final class Miraj extends Card {

    public Miraj() {
        super();
        this.setIsTank(0);
    }

    /**
     *
     * @param attacked
     */
    public void useAbility(final Card attacked) {
        int aux = getHealth();
        setHealth(attacked.getHealth());
        attacked.setHealth(aux);
        setHasAttacked(1);
    }

    /**
     *
     * @return
     */
    public Miraj cloneCard() {
        Miraj copy = new Miraj();
        copy.setMana(this.getMana());
        copy.setHealth(this.getHealth());
        copy.setName(this.getName());
        copy.setColors(this.getColors());
        copy.setIsFrozen(this.getIsFrozen());
        copy.setHasAttacked(this.getHasAttacked());
        copy.setDescription(this.getDescription());
        copy.setAttackDamage(this.getAttackDamage());
        copy.setIsTank(this.getIsTank());
        return copy;
    }
}
