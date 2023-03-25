package com.studying.io.file;

import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class FileManagerTest {
    private static final String sourceFolderWithFilesAndFolders = "src/test/resources/fromWithFiles";
    private static final String sourceFolderWithFilesOnly = "src/test/resources/fromConsistingOfFilesOnly";
    private static final String emptySourceFolder = "src/test/resources/emptyFolderFrom";
    private static final String sourceFile = "src/test/resources/fileFrom.txt";
    private static final String existingEmptyDestFolder = "src/test/resources/existingFolderTo";
    private static final String sourceFolderNonExist = "src/test/resources/fromNotExist";
    private static final String destFolderNonExist = "src/test/resources/toNotExist";


    @BeforeEach
    public void init() throws IOException {
        File pathFrom1 = new File(sourceFolderWithFilesAndFolders);
        pathFrom1.mkdir();
        File pathFile1 = new File(pathFrom1, "file1.txt");
        pathFile1.createNewFile();
        File pathFile2 = new File(pathFrom1, "file2.txt");
        pathFile2.createNewFile();

        File pathInnerFolder1 = new File(pathFrom1, "dir1");
        pathInnerFolder1.mkdir();
        File pathInnerFile1 = new File(pathInnerFolder1, "innerFile1.txt");
        pathInnerFile1.createNewFile();
        File pathInnerFolder2 = new File(pathFrom1, "dir2");
        pathInnerFolder2.mkdir();
        File pathInnerFile2 = new File(pathInnerFolder2, "innerFile2.txt");
        pathInnerFile2.createNewFile();

        File pathFrom2 = new File(emptySourceFolder);
        pathFrom2.mkdir();
        File pathFrom3 = new File(sourceFile);
        pathFrom3.createNewFile();
        try (FileOutputStream fileOutputStream = new FileOutputStream(pathFrom3)) {
            fileOutputStream.write("content".getBytes());
        }

        File pathFrom4 = new File(sourceFolderWithFilesOnly);
        pathFrom4.mkdir();
        File pathFile3 = new File(pathFrom4, "file3.txt");
        pathFile3.createNewFile();
        File pathFile4 = new File(pathFrom4, "file4.txt");
        pathFile4.createNewFile();

        File pathTo1 = new File(existingEmptyDestFolder);
        pathTo1.mkdir();
        String existingFileTo = "src/test/resources/existingFileTo.txt";
        File pathTo2 = new File(existingFileTo);
        pathTo2.createNewFile();
    }

    @DisplayName("test countDirs for a directory with files and folders")
    @Test
    void testCountDirsForDirectoryWithFilesAndFolders() {
        assertEquals(2, FileManager.countDirs(sourceFolderWithFilesAndFolders));
    }

    @DisplayName("test countDirs for a directory with files and folders including inner folders")
    @Test
    void testCountDirsForDirectoryWithFilesAndFoldersIncludingInnerFolders() {
        assertEquals(6, FileManager.countDirs("src/test/resources"));
    }

    @DisplayName("test countDirs for an empty directory")
    @Test
    void testCountDirsForEmptyDirectory() {
        assertEquals(0, FileManager.countDirs(emptySourceFolder));
        assertEquals(0, FileManager.countDirs(existingEmptyDestFolder));
    }

    @DisplayName("test countDirs for directory consisting of files only")
    @Test
    void testCountDirsForDirectoryConsistingOfFilesOnly() {
        assertEquals(0, FileManager.countDirs(sourceFolderWithFilesOnly));
    }

    @DisplayName("test countDirs throw NullPointer exception for non existing directory")
    @Test
    void testCountDirsThrowNullPointerExceptionForNonExistingDirectory() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            FileManager.countDirs(sourceFolderNonExist);
        });
    }

    @DisplayName("test countDirs throw IllegalArgument exception if file is not a directory")
    @Test
    void testCountDirsThrowIllegalArgumentExceptionIfFileIsNotDirectory() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            FileManager.countDirs(sourceFile);
        });
    }


    @DisplayName("test countFiles for a directory with files and folders")
    @Test
    void testCountFilesForDirectoryWithFilesAndFolders() {
        assertEquals(4, FileManager.countFiles(sourceFolderWithFilesAndFolders));
    }

    @DisplayName("test countFiles for a directory with files and folders including inner folders")
    @Test
    void testCountFilesForDirectoryWithFilesAndFoldersIncludingInnerFolders() {
        assertEquals(8, FileManager.countFiles("src/test/resources"));
    }

    @DisplayName("test countFiles for a directory consisting of files only")
    @Test
    void testCountFilesForDirectoryConsistingOfFilesOnly() {
        assertEquals(2, FileManager.countFiles(sourceFolderWithFilesOnly));
    }

    @DisplayName("test countFiles for an empty directory")
    @Test
    void testCountFilesForEmptyDirectory() {
        assertEquals(0, FileManager.countFiles(emptySourceFolder));
        assertEquals(0, FileManager.countFiles(existingEmptyDestFolder));
    }

    @DisplayName("test countFiles throw NullPointer exception for non existing directory")
    @Test
    void testCountFilesThrowNullPointerExceptionForNonExistingDirectory() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            FileManager.countFiles(destFolderNonExist);
        });
    }

    @DisplayName("test countFiles throw IllegalArgument exception if file is not a directory")
    @Test
    void testCountFilesThrowIllegalArgumentExceptionIfFileIsNotDirectory() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            FileManager.countFiles(sourceFile);
        });
    }



    @DisplayName("test copy for a directory with files and folders")
    @Test
    void testCopyForDirectoryWithFilesAndFolders() throws IOException {
        FileManager.copy(sourceFolderWithFilesAndFolders, existingEmptyDestFolder);
        assertTrue(compareDirectories(new File(sourceFolderWithFilesAndFolders), new File(existingEmptyDestFolder)));
    }

    @DisplayName("test copy for a directory with files only")
    @Test
    void testCopyForDirectoryWithFilesOnly() throws IOException {
        FileManager.copy(sourceFolderWithFilesOnly, existingEmptyDestFolder);
        assertTrue(compareDirectories(new File(sourceFolderWithFilesOnly), new File(existingEmptyDestFolder)));
    }

    @DisplayName("test copy for one file only, with content")
    @Test
    void testCopyForOneFileOnlyWithContent() throws IOException {
        FileManager.copy(sourceFile, existingEmptyDestFolder);
        File file = new File(sourceFile);
        File folder = new File(existingEmptyDestFolder);
        File[] files = folder.listFiles();
        assertEquals(1, files.length);
        assertTrue(compareFilesByNameAndLength(files[0], file));
    }

    @DisplayName("test copy for an empty source directory")
    @Test
    void testCopyForEmptySourceDirectory() throws IOException {
        FileManager.copy(emptySourceFolder, existingEmptyDestFolder);
        assertTrue(compareDirectories(new File(emptySourceFolder), new File(existingEmptyDestFolder)));
    }

    @DisplayName("test copy throw NullPointerException if file does not exist")
    @Test
    void testCopyThrowNullPointerExceptionIfSourceFileDoesNotExist() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            FileManager.copy(sourceFolderNonExist, existingEmptyDestFolder);
        });
    }


    @DisplayName("test move throw NullPointerException if source file does not exist")
    @Test
    void testMoveThrowNullPointerExceptionIfSourceFileDoesNotExist() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            FileManager.move(sourceFolderNonExist, existingEmptyDestFolder);
        });
    }

    @DisplayName("test move for empty directory")
    @Test
    void testMoveEmptyDirectoryToNonExistingDirectory() throws IOException {
        FileManager.move(emptySourceFolder, destFolderNonExist);
        assertFalse(new File(emptySourceFolder).exists());
        assertTrue(new File(destFolderNonExist).exists());
        assertTrue(new File(destFolderNonExist).listFiles().length == 0);
    }

    @DisplayName("test move for a directory with files and folders")
    @Test
    void testMoveForDirectoryWithFilesAndFolders() throws IOException {
        FileManager.copy(sourceFolderWithFilesAndFolders, existingEmptyDestFolder);
        FileManager.move(sourceFolderWithFilesAndFolders, destFolderNonExist);
        assertFalse(new File(sourceFolderWithFilesAndFolders).exists());
        assertTrue(compareDirectories(new File(destFolderNonExist), new File(existingEmptyDestFolder)));
    }

    @DisplayName("test move for a directory with files only")
    @Test
    void testMoveForDirectoryWithFilesOnly() throws IOException {
        FileManager.copy(sourceFolderWithFilesOnly, existingEmptyDestFolder);
        FileManager.move(sourceFolderWithFilesOnly, destFolderNonExist);
        assertFalse(new File(sourceFolderWithFilesOnly).exists());
        assertTrue(compareDirectories(new File(destFolderNonExist), new File(existingEmptyDestFolder)));
    }


    @AfterEach
    void deleteFiles() {
        File file = new File("src/test/resources");
        for (File f : file.listFiles()) {
            recursiveDelete(f);
        }
    }

    private boolean compareDirectories(File dirFirst, File dirSecond) throws FileNotFoundException {
        if (!dirFirst.exists()) {
            throw new FileNotFoundException(dirFirst.getAbsolutePath());
        }
        if (!dirSecond.exists()) {
            throw new FileNotFoundException(dirSecond.getAbsolutePath());
        }
        if (dirFirst.isDirectory() != dirSecond.isDirectory()) {
            return false;
        }
        if (dirFirst.isFile()) {
            return compareFilesByNameAndLength(dirFirst, dirSecond);
        }
        File[] listFirst = dirFirst.listFiles();
        File[] listSecond = dirSecond.listFiles();
        if (listFirst.length != listSecond.length) {
            return false;
        }

        for (int i = 0; i < listFirst.length; i++) {
            File fileFirst = listFirst[i];
            File fileSecond = listSecond[i];
            if (!compareDirectories(fileFirst, fileSecond)) {
                return false;
            }
        }
        return true;
    }

    private boolean compareFilesByNameAndLength(File fileFirst, File fileSecond) {
        if (!fileFirst.getName().equals(fileSecond.getName())) {
            return false;
        }
        return fileFirst.length() == fileSecond.length();
    }

    private void recursiveDelete(File file) {
        if (!file.exists())
            return;
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                recursiveDelete(f);
            }
        }
        file.delete();
    }
}
