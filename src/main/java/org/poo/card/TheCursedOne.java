package org.poo.card;

public final class TheCursedOne extends Card {

    public TheCursedOne() {
        super();
        this.setIsTank(0);
    }

    /**
     *
     * @param attacked card being affected by ability
     */
    @Override
    public void useAbility(final Card attacked) {
        int aux = attacked.getHealth();
        attacked.setHealth(attacked.getAttackDamage());
        attacked.setAttackDamage(aux);
        setHasAttacked(1);
    }

    /**
     *
     * @return copy of current card
     */
    @Override
    public TheCursedOne cloneCard() {
        TheCursedOne copy = new TheCursedOne();
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
