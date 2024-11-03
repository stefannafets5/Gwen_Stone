package main;

import ConvertJson.ConvertJson;
import checker.Checker;

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

import Game.Game;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
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

        Game game = new Game(inputData.getPlayerOneDecks().getNrDecks()
                             , inputData.getPlayerOneDecks().getNrCardsInDeck()
                             , inputData.getPlayerTwoDecks().getNrDecks()
                             , inputData.getPlayerTwoDecks().getNrCardsInDeck());

        game.getPlayer(0).copyAllDecks(inputData.getPlayerOneDecks().getDecks());
        game.getPlayer(1).copyAllDecks(inputData.getPlayerTwoDecks().getDecks());

        for(int i = 0; i < inputData.getGames().size(); i++) {
            game.setPlayerTurn(inputData.getGames().get(i).getStartGame().getStartingPlayer());
            game.setActions(inputData.getGames().get(i).getActions());

            game.getPlayer(0).setHero(inputData.getGames().get(i).getStartGame().getPlayerOneHero());
            game.getPlayer(1).setHero(inputData.getGames().get(i).getStartGame().getPlayerTwoHero());

            game.getPlayer(0).shuffleDeck(inputData.getGames().get(i).getStartGame().getPlayerOneDeckIdx()
                                            , inputData.getGames().get(i).getStartGame().getShuffleSeed());
            game.getPlayer(1).shuffleDeck(inputData.getGames().get(i).getStartGame().getPlayerTwoDeckIdx()
                                            , inputData.getGames().get(i).getStartGame().getShuffleSeed());

            for (int j = 0; j < game.getActions().size(); j++) {
                if (game.getActions().get(j).getCommand().equals("getPlayerDeck")) {
                    out.getPlayerDeck(game.getPlayer(game.getActions().get(j).getPlayerIdx() - 1).getCurrentDeck()
                                      , game.getActions().get(j).getPlayerIdx());
                } else if (game.getActions().get(j).getCommand().equals("getPlayerHero")) {
                    out.getPlayerHero(game.getPlayer(game.getActions().get(j).getPlayerIdx() - 1).getHero()
                                      , game.getActions().get(j).getPlayerIdx());
                } else if (game.getActions().get(j).getCommand().equals("getPlayerTurn")) {
                    out.getPlayerTurn(game.getPlayerTurn());
                } else if (game.getActions().get(j).getCommand().equals("getCardsInHand")) {

                }
            }
        }

        /*
         * TODO Implement your function here
         *
         * How to add output to the output array?
         * There are multiple ways to do this, here is one example:
         *
         * ObjectMapper mapper = new ObjectMapper();
         *
         * ObjectNode objectNode = mapper.createObjectNode();
         * objectNode.put("field_name", "field_value");
         *
         * ArrayNode arrayNode = mapper.createArrayNode();
         * arrayNode.add(objectNode);
         *
         * output.add(arrayNode);
         * output.add(objectNode);
         *
         */

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePath2), output);
    }
}
