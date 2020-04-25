package com.card_saver;

import com.card_saver.parser.Parser;

import static org.junit.Assert.*;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ParserTest {

    @Test
    public void testParseAllCardNames() throws IOException {
        File cardNamesFile = Paths.get(System.getProperty("user.dir") + "/src/main/resources/static/CardNames.txt").toFile();

        String cardNamesFileContent = Parser.readFile(cardNamesFile);

        List<String> allCardNames = new ArrayList<>();

        while(cardNamesFileContent.length() != 0){
            String cardName = cardNamesFileContent.substring(0, cardNamesFileContent.indexOf(";"));
            allCardNames.add(cardName);
            cardNamesFileContent = cardNamesFileContent.substring(cardNamesFileContent.indexOf(";") + 1);
        }

        Parser.parseAllCardNames();

        assertEquals(allCardNames, Parser.getAllCardNames());
    }

    @Test
    public void testReadFile() throws IOException {
        File cardNamesFile = File.createTempFile("cardNames", ".txt");
        String cardNames = "Ancestrall Recall;Lightning Bolt;Scryb Sprites;";
        BufferedWriter writer = new BufferedWriter(new FileWriter(cardNamesFile, true));
        writer.append(cardNames);
        writer.close();

        String cardNamesFileContent = Parser.readFile(cardNamesFile);

        assertTrue(cardNamesFileContent.equals(cardNames));
    }
}
