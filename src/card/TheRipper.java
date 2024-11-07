package card;

public final class TheRipper extends Card {

    public TheRipper() {
        super();
        this.setIsTank(0);
    }

    /**
     *
     * @param attacked
     */
    public void useAbility(final Card attacked) {
        attacked.subtractAttack(2);
        setHasAttacked(1);
    }

    /**
     *
     * @return
     */
    public TheRipper cloneCard() {
        TheRipper copy = new TheRipper();
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
