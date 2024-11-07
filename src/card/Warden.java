package card;

public final class Warden extends Card {

    public Warden() {
        super();
        this.setIsTank(1);
    }

    /**
     *
     * @return
     */
    public Warden cloneCard() {
        Warden copy = new Warden();
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
