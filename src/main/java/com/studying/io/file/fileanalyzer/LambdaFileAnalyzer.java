package com.studying.io.file.fileanalyzer;

import java.io.IOException;
import java.util.List;

public class LambdaFileAnalyzer implements FileAnalyzer {
    private String pathToFile;

    public LambdaFileAnalyzer(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    @Override
    public FileStatistics analyze(String word) throws IOException {
        return null;
    }

    @Override
    public String readContent(String path) throws IOException {
        return null;
    }

    @Override
    public List<String> splitIntoSentences(String content) {
        return null;
    }

    @Override
    public List<String> filter(List<String> sentences, String word) {
        return null;
    }

    @Override
    public int countWord(List<String> filteredSentences, String word) {
        return 0;
    }
}
