package com.studying.io.file.fileanalyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamFileAnalyzer extends AbstractFileAnalyzer {

    @Override
    public List<String> splitIntoSentences(String content) {
        String[] sentences = SENTENCE_PATTERN.split(content);
        List<String> list = Stream.of(sentences)
                .map(String::trim)
                .collect(Collectors.toList());
        return list;
    }

    @Override
    public List<String> filter(List<String> sentences, String word) {
        String regex = "\\b%s\\b".formatted(word);
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        List<String> filteredList = sentences.stream()
                .filter(s -> {
                    matcher = pattern.matcher(s);
                    return matcher.find();
                }).collect(Collectors.toList());
        return filteredList;
    }

    @Override
    public int countWord(List<String> filteredSentences, String word) {
        String regex = "\\b%s\\b".formatted(word);
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        filteredSentences.parallelStream()
                .forEach(s -> {
                    matcher = pattern.matcher(s);
                    while (matcher.find()) {
                        counter++;
                    }
                });
        return counter;
    }
}
