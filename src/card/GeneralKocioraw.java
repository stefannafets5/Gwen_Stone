package card;

import java.util.ArrayList;

public final class GeneralKocioraw extends Card {
    /**
     *
     * @param row
     * @param board
     */
    public void useAbility(final int row, final ArrayList<ArrayList<Card>> board) {
        for (int i = 0; i < board.get(row).size(); i++) {
            board.get(row).get(i).addAttack(1);
        }
        setHasAttacked(1);
    }
}
