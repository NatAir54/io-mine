package com.studying.io.file.fileanalyzer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileAnalyzer {
    String pathToFile;
    private static final Pattern SENTENCE_PATTERN = Pattern.compile("(?<=[.!?])");


    public FileAnalyzer(String pathToFile) {
        this.pathToFile = pathToFile;
    }


    public FileStatistics analyze(String word) throws IOException {
        String content = readContent(pathToFile);
        List<String> sentences = splitIntoSentences(content);
        List<String> filteredSentences = filter(sentences, word);
        int count = countWord(filteredSentences, word);

        return new FileStatistics(filteredSentences, count);
    }

    static String readContent(String path) throws IOException {
        File pathToFile = new File(path);
        byte[] contentArray;
        try (InputStream inputStream = new FileInputStream(pathToFile)) {
            int fileLength = (int) pathToFile.length();
            contentArray = new byte[fileLength];
            inputStream.read(contentArray);
        }
        return new String(contentArray);
    }

    static List<String> splitIntoSentences(String content) {
        String[] sentences = SENTENCE_PATTERN.split(content);
        List<String> list = new ArrayList<>();
        for (String sentence : sentences) {
            list.add(sentence.trim());
        }
        return list;
    }


    static List<String> filter(List<String> sentences, String word) {
        List<String> filteredList = new ArrayList<>();
        String regex = "\\b" + word + "\\b";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher;
        for (String sentence : sentences) {
            matcher = pattern.matcher(sentence);
            if (matcher.find()) {
                filteredList.add(sentence);
            }
        }
        return filteredList;
    }


    static int countWord(List<String> filteredSentences, String word) {
        int counter = 0;
        String regex = "\\b" + word + "\\b";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher;
        for (String sentence : filteredSentences) {
            matcher = pattern.matcher(sentence);
            while (matcher.find()) {
                counter++;
            }
        }
        return counter;
    }
}