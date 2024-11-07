package card;

import java.util.ArrayList;

public final class EmpressThorina extends Card {
    /**
     *
     * @param row row affected byb ability
     * @param board current board with cards
     */
    @Override
    public void useAbility(final int row, final ArrayList<ArrayList<Card>> board) {
        int maxHealthIdx = -1;
        int maxHealth = 0;
        for (int i = 0; i < board.get(row).size(); i++) {
            if (maxHealth < board.get(row).get(i).getHealth()) {
                maxHealth = board.get(row).get(i).getHealth();
                maxHealthIdx = i;
            }
        }
        if (maxHealthIdx != -1) { // Row not empty
            board.get(row).remove(maxHealthIdx);
        }
        setHasAttacked(1);
    }
}
