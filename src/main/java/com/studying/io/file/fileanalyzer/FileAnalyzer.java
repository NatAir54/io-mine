package com.studying.io.file.fileanalyzer;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public interface FileAnalyzer {
    Pattern SENTENCE_PATTERN = Pattern.compile("(?<=[.!?])");
    String FILE_NAME = "src/test/resources/DuckBook.txt";

    FileStatistics analyze(String word) throws IOException;

    String readContent(String path) throws IOException;

    List<String> splitIntoSentences(String content);

    List<String> filter(List<String> sentences, String word);

    int countWord(List<String> filteredSentences, String word);
}
