package card;

public final class Sentinel extends Card {

    public Sentinel() {
        super();
        this.setIsTank(0);
    }

    /**
     *
     * @return
     */
    public Sentinel cloneCard() {
        Sentinel copy = new Sentinel();
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
