package main;

import converter.ConvertJson;
import checker.Checker;
import game.Game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import checker.CheckerConstants;
import fileio.Input;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public final class Main {
    private Main() {
    }

    /**
     * DO NOT MODIFY MAIN METHOD
     * Call the checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(CheckerConstants.TESTS_PATH);
        Path path = Paths.get(CheckerConstants.RESULT_PATH);

        if (Files.exists(path)) {
            File resultFile = new File(String.valueOf(path));
            for (File file : Objects.requireNonNull(resultFile.listFiles())) {
                file.delete();
            }
            resultFile.delete();
        }
        Files.createDirectories(path);

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            String filepath = CheckerConstants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getName(), filepath);
            }
        }

        Checker.calculateScore();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Input inputData = objectMapper.readValue(new File(CheckerConstants.TESTS_PATH + filePath1),
                Input.class);

        ArrayNode output = objectMapper.createArrayNode();
        ConvertJson out = new ConvertJson(output);

        Game game = new Game(inputData.getPlayerOneDecks().getNrDecks(),
                inputData.getPlayerTwoDecks().getNrDecks());
        game.copyPlayerDecks(inputData);

        for (int i = 0; i < inputData.getGames().size(); i++) {
            game.startGame(inputData, i);
            game.setGameTurn(1);
            boolean gameEnd = false;

            for (int j = 0; j < game.getActions().size(); j++) {
                int index = game.getActions().get(j).getPlayerIdx() - 1;

                if (game.getActions().get(j).getCommand().equals("getPlayerDeck")) {
                    out.getPlayerDeck(game.getPlayer(index).getCurrentDeck(), index + 1);
                } else if (game.getActions().get(j).getCommand().equals("getPlayerHero")) {
                    out.getPlayerHero(game.getPlayer(index).getHero(), index + 1);
                } else if (game.getActions().get(j).getCommand().equals("getPlayerTurn")) {
                    out.getPlayerTurn(game.getPlayerTurn() + 1);
                } else if (game.getActions().get(j).getCommand().equals("getPlayerMana")) {
                    out.getPlayerMana(game.getPlayer(index).getTotalMana(), index + 1);
                } else if (game.getActions().get(j).getCommand().equals("getCardsInHand")) {
                    out.getCardsInHand(game.getPlayer(index).getCardsInHand(), index + 1);
                } else if (game.getActions().get(j).getCommand().equals("getCardsOnTable")) {
                    out.getCardsOnTable(game.getBoard());
                } else if (game.getActions().get(j).getCommand().equals("getFrozenCardsOnTable")) {
                    out.getFrozenCardsOnTable(game.getBoard());
                } else if (game.getActions().get(j).getCommand().equals("getCardAtPosition")) {
                    out.getCardAtPosition(game.getActions().get(j).getX(),
                            game.getActions().get(j).getY(), game.getBoard());
                } else if (game.getActions().get(j).getCommand().equals("getTotalGamesPlayed")) {
                    out.getTotalGamesPlayed(game.getPlayer(0));
                } else if (game.getActions().get(j).getCommand().equals("getPlayerOneWins")) {
                    out.getPlayerOneWins(game.getPlayer(0));
                } else if (game.getActions().get(j).getCommand().equals("getPlayerTwoWins")) {
                    out.getPlayerTwoWins(game.getPlayer(1));
                } else if (game.getActions().get(j).getCommand().equals("endPlayerTurn")
                        && !gameEnd) {
                    game.endTurn();
                } else if (game.getActions().get(j).getCommand().equals("placeCard")
                        && !gameEnd) {
                    game.placeCard(game.getActions().get(j).getHandIdx(), out);
                } else if (game.getActions().get(j).getCommand().equals("cardUsesAttack")
                        && !gameEnd) {
                    game.cardUsesAttack(game.getActions().get(j).getCardAttacker(),
                            game.getActions().get(j).getCardAttacked(), out);
                } else if (game.getActions().get(j).getCommand().equals("cardUsesAbility")
                        && !gameEnd) {
                    game.cardUsesAbility(game.getActions().get(j).getCardAttacker(),
                            game.getActions().get(j).getCardAttacked(), out);
                } else if (game.getActions().get(j).getCommand().equals("useAttackHero")
                        && !gameEnd) {
                    game.useAttackHero(game.getActions().get(j).getCardAttacker(), out);
                    if (game.getPlayer(0).getHero().getHealth() <= 0
                            || game.getPlayer(1).getHero().getHealth() <= 0) {
                        gameEnd = true; // someone lost
                    }
                } else if (game.getActions().get(j).getCommand().equals("useHeroAbility")
                        && !gameEnd) {
                    game.useHeroAbility(game.getActions().get(j).getAffectedRow(), out);
                }
            }
        }

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePath2), output);
    }
}
