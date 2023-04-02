package com.studying.io.file.fileanalyzer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Matcher;

public class AbstractFileAnalyzer implements FileAnalyzer {
    protected Matcher matcher;
    protected int counter = 0;

    @Override
    public FileStatistics analyze(String pathToFile, String word) throws IOException {
        String content = readContent(pathToFile);
        List<String> sentences = splitIntoSentences(content);
        List<String> filteredSentences = filter(sentences, word);
        int count = countWord(filteredSentences, word);

        return new FileStatistics(filteredSentences, count);
    }

    @Override
    public String readContent(String path) throws IOException {
        File pathToFile = new File(path);
        byte[] contentArray;
        try (InputStream inputStream = new FileInputStream(pathToFile)) {
            int fileLength = (int) pathToFile.length();
            contentArray = new byte[fileLength];
            inputStream.read(contentArray);
        }
        return new String(contentArray);
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
