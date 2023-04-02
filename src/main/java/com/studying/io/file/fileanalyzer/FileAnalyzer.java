package com.studying.io.file.fileanalyzer;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public interface FileAnalyzer {
    Pattern SENTENCE_PATTERN = Pattern.compile("(?<=[.!?])");


    FileStatistics analyze(String pathToFile, String word) throws IOException;

    String readContent(String path) throws IOException;

    List<String> splitIntoSentences(String content);

    List<String> filter(List<String> sentences, String word);

    int countWord(List<String> filteredSentences, String word);
}
