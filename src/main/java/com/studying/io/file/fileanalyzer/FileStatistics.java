package com.studying.io.file.fileanalyzer;

import java.util.List;

public class FileStatistics {
    private List<String> sentences;
    private int wordCount;

    public FileStatistics(List<String> sentences, int wordCount) {
        this.sentences = sentences;
        this.wordCount = wordCount;
    }

    public List<String> getSentences() {
        return sentences;
    }

    public int getWordCount() {
        return wordCount;
    }
}
