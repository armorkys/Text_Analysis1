package com.example.Text_Analysis;

import com.example.Text_Analysis.model.MatchingString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@SpringBootTest
public class MatchingStringServiceTest {


    MatchingStringService service = new MatchingStringService();

    @Test
    public void testSplitStringNoSpaces() {
        String noSpace = "ASDCAWAWDCA";

        String[] noSpaceAns = service.splitString(noSpace);


        Assertions.assertEquals(1, noSpaceAns.length);
        Assertions.assertEquals(noSpace, noSpaceAns[0]);

    }

    @Test
    public void testSplitStringSpacesOnly() {
        String spaceOnly = "      ";
        String[] spaceOnlyAns = service.splitString(spaceOnly);


        Assertions.assertEquals(0, spaceOnlyAns.length);

    }

    @Test
    public void testSplitStringWordsWithSpaces() {
        String wordsWithSpaces = " abba erree tdtdt ";
        String[] wordsWithSpacesAns = service.splitString(wordsWithSpaces);

        Assertions.assertEquals(4, wordsWithSpacesAns.length);

    }

    @Test
    public void testSplitBigSpaces() {
        String bigSpaces = "   AMMA AIJLA       IE";
        String[] bigSpacesAns = service.splitString(bigSpaces);

        Assertions.assertEquals(12, bigSpacesAns.length);

    }


    @Test
    public void loadFromNULLFile(@TempDir Path tempDir) throws FileNotFoundException {
        Path numbers = tempDir.resolve("numbers.txt");

        File temp = new File(numbers.toString());

        FileNotFoundException thrown =
                Assertions.assertThrows(FileNotFoundException.class,

                        () -> service.loadFromFile(temp),

                        "Expected loadFromFile() to throw, but it didn't");

        Assertions.assertTrue(thrown.getMessage().contains("Stuff"));


    }

    @Test
    public void loadFromEmptyFile(@TempDir Path tempDir)
            throws IOException {

        Path numbers = tempDir.resolve("numbers.txt");
        List<String> lines = Arrays.asList("");
        Files.write(numbers, lines);
        File temp = new File(numbers.toString());
        String data = service.loadFromFile(temp);

        Assertions.assertTrue(data.isBlank());

    }

    @Test
    public void writeToFileNullList() {
        List<MatchingString> emptyList = new ArrayList<MatchingString>();
        String startingString = null;

        IOException thrown =
                Assertions.assertThrows(IOException.class,

                        () -> service.writeToFile(emptyList, startingString),

                        "Expected writeToFile() to throw, but it didn't");

        Assertions.assertTrue(thrown.getMessage().contains("Arguments are null"));

    }


    @Test
    public void writeToFileNoMatchesList() {
        List<MatchingString> emptyList = new ArrayList<MatchingString>();
        String startingString = "AL#ASDJK LAS$DJKLAIJ L2WIJ LJA4WIL";

        IOException thrown =
                Assertions.assertThrows(IOException.class,

                        () -> service.writeToFile(emptyList, startingString),

                        "Expected writeToFile() to throw, but it didn't");

        Assertions.assertTrue(thrown.getMessage().contains("Arguments are null"));

    }

    @Test
    public void analyzeNullString() {
        String[] nullInput = null;


        List<MatchingString> emptyList = new ArrayList<MatchingString>();
        List<MatchingString> emptyListAns = service.analyzeString(nullInput);




        NullPointerException thrown =
                Assertions.assertThrows(NullPointerException.class,

                        () -> service.analyzeString(nullInput),

                        "Expected analyzeString() to throw, but it didn't");

        Assertions.assertTrue(thrown.getMessage().contains("NullPointerException"));


    }


    @Test
    public void analyzeEmptyString() {
        String[] emptyInput = new String[1];
        emptyInput[0] = "";

        List<MatchingString> emptyList = new ArrayList<MatchingString>();
        List<MatchingString> emptyListAns = service.analyzeString(emptyInput);

        Assertions.assertEquals(emptyList, emptyListAns);

    }

    @Test
    public void analyzeBadString() {
        String[] emptyInput = new String[1];
        emptyInput[0] = "33215131";

        List<MatchingString> emptyList = new ArrayList<MatchingString>();
        List<MatchingString> emptyListAns = service.analyzeString(emptyInput);

        Assertions.assertEquals(emptyList, emptyListAns);
    }


    @Test
    public void sortResultEmptyList() {

        List<MatchingString> emptyList = new ArrayList<MatchingString>();

        List<MatchingString> emptyListAns = service.sortResult(emptyList);


        Assertions.assertEquals(emptyList, emptyListAns);
    }

    @Test
    public void sortResultSingleObjectList() {
        List<MatchingString> singleEntryList = new ArrayList<MatchingString>();

        singleEntryList.add(new MatchingString('a', 5, "asa ada aea area atra"));

        List<MatchingString> singleEntryListAns = service.sortResult(singleEntryList);


        Assertions.assertEquals(singleEntryList, singleEntryListAns);
    }

    @Test
    public void sortResultDuplicateEntryList() {
        List<MatchingString> duplicateEntryList = new ArrayList<MatchingString>();

        duplicateEntryList.add(new MatchingString('b', 2, "asa ada aea area atra"));
        duplicateEntryList.add(new MatchingString('b', 2, "asa ada aea area atra"));

        List<MatchingString> duplicateEntryListAns = service.sortResult(duplicateEntryList);

        Assertions.assertEquals(duplicateEntryList, duplicateEntryListAns);
    }


}


