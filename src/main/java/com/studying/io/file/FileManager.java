package com.studying.io.file;

import java.io.*;

public class FileManager {
    public static int countDirs(String path) {
        File pathToDirectory = new File(path);
        checkPathToDirectory(pathToDirectory);
        int dirCounter = 0;
        File[] files = pathToDirectory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    dirCounter++;
                    dirCounter += countDirs(file.getPath());
                }
            }
        }
        return dirCounter;
    }


    public static int countFiles(String path) {
        File pathToDirectory = new File(path);
        checkPathToDirectory(pathToDirectory);
        int fileCounter = 0;
        File[] files = pathToDirectory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    fileCounter++;
                } else {
                    fileCounter += countFiles(file.getPath());
                }
            }
        }
        return fileCounter;
    }


    public static void move(String from, String to) {
        File sourceFile = new File(from);
        if (!sourceFile.exists()) {
            throw new IllegalStateException("Source file doesn't exist.");
        }
        sourceFile.renameTo(new File(to));
    }


    public static void copy(String from, String to) throws IOException {
        File sourceFile = new File(from);
        File targetFile = new File(to);
        if (!sourceFile.exists()) {
            throw new IllegalStateException("Source file doesn't exist.");
        }
        if (sourceFile.isDirectory()) {
            copyDirectory(sourceFile, targetFile);
        } else {
            if (!targetFile.exists() || !targetFile.isDirectory()) {
                targetFile.mkdir();
            }
            String fileName = sourceFile.getName();
            File target = new File(targetFile, fileName);
            copyFile(sourceFile, target);
        }
    }


    private static void copyDirectory(File from, File to) throws IOException {
        if (!to.exists() || !to.isDirectory()) {
            to.mkdir();
        }
        if ((from.list().length > 0)) {
            File[] files = from.listFiles();
            for (File file : files) {
                String fileName = file.getName();
                File target = new File(to, fileName);
                if (file.isFile()) {
                    copyFile(file, target);
                } else {
                    target.mkdir();
                    copyDirectory(file, target);
                }
            }
        }
    }


    private static void copyFile(File fileToCopy, File to) throws IOException {
        int fileLength = (int) fileToCopy.length();
        byte[] content = new byte[fileLength];
        try (InputStream inputStream = new FileInputStream(fileToCopy);
             OutputStream outputStream = new FileOutputStream(to)) {
            inputStream.read(content);
            outputStream.write(content);
        }
    }

    private static void checkPathToDirectory(File file) {
        if (!file.exists()) {
            throw new IllegalStateException("Directory not found");
        }
        if (!file.isDirectory()) {
            throw new IllegalArgumentException("File is not a directory");
        }
    }
}
