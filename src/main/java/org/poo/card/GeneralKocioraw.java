package org.poo.card;

import java.util.ArrayList;

public final class GeneralKocioraw extends Card {
    /**
     *
     * @param row row affected byb ability
     * @param board current board with cards
     */
    @Override
    public void useAbility(final int row, final ArrayList<ArrayList<Card>> board) {
        for (int i = 0; i < board.get(row).size(); i++) {
            board.get(row).get(i).addAttack(1);
        }
        setHasAttacked(1);
    }
}
