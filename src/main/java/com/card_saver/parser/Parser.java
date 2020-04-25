package com.card_saver.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Handles parsing of text based resources. The class' content are all static
 * and its constructor private to prohibit instancing.
 */
public final class Parser {

    private static List<String> allCardNames;

    private Parser(){}

    /**
     * Parses all the comma separated card names present in "CardNames.txt" into Strings
     * and stores them in a List<String>.
     */
    public static void parseAllCardNames(){
        allCardNames = new ArrayList<>();

        try{
            File cardNamesFile = Paths.get(System.getProperty("user.dir") + "/src/main/resources/static/CardNames.txt").toFile();

            String cardNamesFileContent = readFile(cardNamesFile);

            while(cardNamesFileContent.length() != 0){
                String cardName = cardNamesFileContent.substring(0, cardNamesFileContent.indexOf(";"));
                allCardNames.add(cardName);
                cardNamesFileContent = cardNamesFileContent.substring(cardNamesFileContent.indexOf(";") + 1);
            }
        } catch (FileNotFoundException e){
            System.out.println("Could not parse card names. Exception: " + e.toString());
        }
    }

    /**
     * Reads the parameter File by using a Scanner with the parameter File
     * as the input stream and returns the entire file's content.
     * @param file - The file to be read.
     * @return The entire text content of the parameter File.
     * @throws FileNotFoundException
     */
    public static String readFile(File file) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(file);

        String fileContent = "";
        while (fileScanner.hasNextLine()) {
            fileContent += fileScanner.nextLine();
        }

        fileScanner.close();
        return fileContent;
    }

    public static List<String> getAllCardNames(){
        return allCardNames;
    }
}
