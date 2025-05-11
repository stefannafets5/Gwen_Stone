package org.poo.card;

public final class Miraj extends Card {

    public Miraj() {
        super();
        this.setIsTank(0);
    }

    /**
     *
     * @param attacked card being affected by ability
     */
    @Override
    public void useAbility(final Card attacked) {
        int aux = getHealth();
        setHealth(attacked.getHealth());
        attacked.setHealth(aux);
        setHasAttacked(1);
    }

    /**
     *
     * @return copy of current card
     */
    @Override
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
