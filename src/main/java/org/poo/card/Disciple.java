package org.poo.card;

public final class Disciple extends Card {

    public Disciple() {
        super();
        this.setIsTank(0);
    }

    /**
     *
     * @param attacked card being affected by ability
     */
    @Override
    public void useAbility(final Card attacked) {
        attacked.addHealth(2);
        setHasAttacked(1);
    }

    /**
     *
     * @return copy of current card
     */
    @Override
    public Disciple cloneCard() {
        Disciple copy = new Disciple();
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
