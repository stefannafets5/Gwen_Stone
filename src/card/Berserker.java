package card;

public final class Berserker extends Card {

    public Berserker() {
        super();
        this.setIsTank(0);
    }

    /**
     *
     * @return
     */
    public Berserker cloneCard() {
        Berserker copy = new Berserker();
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
