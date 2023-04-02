package com.studying.io.file.fileanalyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class BasicFileAnalyzer extends AbstractFileAnalyzer {

    public List<String> splitIntoSentences(String content) {
        String[] sentences = SENTENCE_PATTERN.split(content);
        List<String> list = new ArrayList<>();
        for (String sentence : sentences) {
            list.add(sentence.trim());
        }
        return list;
    }


    public List<String> filter(List<String> sentences, String word) {
        String regex = "\\b%s\\b".formatted(word);
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        List<String> filteredList = new ArrayList<>();
        for (String sentence : sentences) {
            matcher = pattern.matcher(sentence);
            if (matcher.find()) {
                filteredList.add(sentence);
            }
        }
        return filteredList;
    }


    public int countWord(List<String> filteredSentences, String word) {
        String regex = "\\b%s\\b".formatted(word);
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        for (String sentence : filteredSentences) {
            matcher = pattern.matcher(sentence);
            while (matcher.find()) {
                counter++;
            }
        }
        return counter;
    }
}