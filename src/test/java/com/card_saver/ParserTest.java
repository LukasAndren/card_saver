package com.card_saver;

import com.card_saver.parser.Parser;

import static org.junit.Assert.*;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ParserTest {

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
