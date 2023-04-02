package com.studying.io.file.fileanalyzer;


public class StreamFileAnalyzerTest extends FileAnalyzerTest {
    @Override
    protected FileAnalyzer getFileAnalyzer() {
        return new StreamFileAnalyzer();
    }

}
