package com.studying.io.file.fileanalyzer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class FileAnalyzerTest {
    private FileAnalyzer fileAnalyzer;


    @BeforeEach
    public void before() {
        fileAnalyzer = getFileAnalyzer();
    }

    protected abstract FileAnalyzer getFileAnalyzer();

    @DisplayName("test split String into sentences with separators (.!&)")
    @Test
    void testSplitStringIntoSentences() {
        String content = "Duck! One more duck. How many ducks here? Two ducks. Duck is beautiful.";
        List<String> listExpected = List.of("Duck!", "One more duck.", "How many ducks here?", "Two ducks.", "Duck is beautiful.");
        List<String> resultList = fileAnalyzer.splitIntoSentences(content);
        assertEquals(listExpected, resultList);
    }

    @DisplayName("test filter sentences with word")
    @Test
    void testFilter() {
        String content = "Hello! Wow, duck! One more duck. How many birds are here? Two birds. This duck is beautiful.";
        List<String> listExpected = List.of("Wow, duck!", "One more duck.", "This duck is beautiful.");
        List<String> splitSentences = fileAnalyzer.splitIntoSentences(content);
        List<String> resultList = fileAnalyzer.filter(splitSentences, "duck");
        assertEquals(listExpected, resultList);
    }

    @DisplayName("test filter sentences with word in different case")
    @Test
    void testFilterSentencesWithWordInDifferentCase() {
        String content = "Hello! Duck! One more duck. How many birds are here? Two birds. Duck is beautiful.";
        List<String> listExpected = List.of("Duck!", "One more duck.", "Duck is beautiful.");
        List<String> splitSentences = fileAnalyzer.splitIntoSentences(content);
        List<String> resultList = fileAnalyzer.filter(splitSentences, "duck");
        assertEquals(listExpected, resultList);
    }

    @DisplayName("test filter sentences with word in different case including word in plural")
    @Test
    void testFilterSentencesWithWordInDifferentCaseIncludingWordInPlural() {
        String content = "Hello! Duck! One more duck. How many ducks here? Two ducks. Duck is beautiful.";
        List<String> listExpected = List.of("Duck!", "One more duck.", "Duck is beautiful.");
        List<String> splitSentences = fileAnalyzer.splitIntoSentences(content);
        List<String> resultList = fileAnalyzer.filter(splitSentences, "duck");
        assertEquals(listExpected, resultList);
    }

    @DisplayName("test countWord")
    @Test
    void testCountWord() {
        String content = "Duck! One more duck. How many ducks here? Two ducks. Duck is beautiful.";
        List<String> splitSentences = fileAnalyzer.splitIntoSentences(content);
        List<String> filteredSentences = fileAnalyzer.filter(splitSentences, "duck");
        assertEquals(3, fileAnalyzer.countWord(filteredSentences, "duck"));
    }
}
