package com.studying.io.file.fileanalyzer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileAnalyzerTest {

    @DisplayName("test split String into sentences with separators (.!&)")
    @Test
    void testSplitStringIntoSentences() {
        String content = "Duck! One more duck. How many ducks here? Two ducks. Duck is beautiful.";
        List<String> listExpected = List.of("Duck!", "One more duck.", "How many ducks here?", "Two ducks.", "Duck is beautiful.");
        List<String> resultList = FileAnalyzer.splitIntoSentences(content);
        assertEquals(listExpected, resultList);
    }

    @DisplayName("test split file text into sentences with separators (.!&)")
    @Test
    void testSplitFileTextIntoSentences() throws IOException {
        FileAnalyzerRunner.writeDuckBook(FileAnalyzerRunner.FILE_NAME);
        String content = FileAnalyzer.readContent(FileAnalyzerRunner.FILE_NAME);
        List<String> splitSentences = FileAnalyzer.splitIntoSentences(content);
        assertEquals(43, splitSentences.size());
    }

    @DisplayName("test filter sentences with word")
    @Test
    void testFilter() {
        String content = "Hello! Wow, duck! One more duck. How many birds are here? Two birds. This duck is beautiful.";
        List<String> listExpected = List.of("Wow, duck!", "One more duck.", "This duck is beautiful.");
        List<String> splitSentences = FileAnalyzer.splitIntoSentences(content);
        List<String> resultList = FileAnalyzer.filter(splitSentences, "duck");
        assertEquals(listExpected, resultList);
    }

    @DisplayName("test filter sentences with word in different case")
    @Test
    void testFilterSentencesWithWordInDifferentCase() {
        String content = "Hello! Duck! One more duck. How many birds are here? Two birds. Duck is beautiful.";
        List<String> listExpected = List.of("Duck!", "One more duck.", "Duck is beautiful.");
        List<String> splitSentences = FileAnalyzer.splitIntoSentences(content);
        List<String> resultList = FileAnalyzer.filter(splitSentences, "duck");
        assertEquals(listExpected, resultList);
    }

    @DisplayName("test filter sentences with word in different case including word in plural")
    @Test
    void testFilterSentencesWithWordInDifferentCaseIncludingWordInPlural() {
        String content = "Hello! Duck! One more duck. How many ducks here? Two ducks. Duck is beautiful.";
        List<String> listExpected = List.of("Duck!", "One more duck.", "Duck is beautiful.");
        List<String> splitSentences = FileAnalyzer.splitIntoSentences(content);
        List<String> resultList = FileAnalyzer.filter(splitSentences, "duck");
        assertEquals(listExpected, resultList);
    }

    @DisplayName("test filter sentences with word in file")
    @Test
    void testFilterSentencesWithWordInFile() throws IOException {
        FileAnalyzerRunner.writeDuckBook(FileAnalyzerRunner.FILE_NAME);
        String content = FileAnalyzer.readContent(FileAnalyzerRunner.FILE_NAME);
        List<String> splitSentences = FileAnalyzer.splitIntoSentences(content);
        List<String> filteredSentences = FileAnalyzer.filter(splitSentences, "duck");
        assertEquals(23, filteredSentences.size());
    }

    @DisplayName("test countWord")
    @Test
    void testCountWord() {
        String content = "Duck! One more duck. How many ducks here? Two ducks. Duck is beautiful.";
        List<String> splitSentences = FileAnalyzer.splitIntoSentences(content);
        List<String> filteredSentences = FileAnalyzer.filter(splitSentences, "duck");
        assertEquals(3, FileAnalyzer.countWord(filteredSentences, "duck"));
    }

    @DisplayName("test count word in file")
    @Test
    void testCountWordInFile() throws IOException {
        FileAnalyzerRunner.writeDuckBook(FileAnalyzerRunner.FILE_NAME);
        String content = FileAnalyzer.readContent(FileAnalyzerRunner.FILE_NAME);
        List<String> splitSentences = FileAnalyzer.splitIntoSentences(content);
        List<String> filteredSentences = FileAnalyzer.filter(splitSentences, "duck");
        assertEquals(25, FileAnalyzer.countWord(filteredSentences, "duck"));
    }
}

