package com.studying.io.file.fileanalyzer;



public class BasicFileAnalyzerTest extends FileAnalyzerTest{
    @Override
    protected FileAnalyzer getFileAnalyzer() {
        return new BasicFileAnalyzer();
    }

}

