package com.studying.io.file;

import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class FileManagerTest {
    private static final String SOURCE_FOLDER_WITH_FILES_AND_FOLDERS = "src/test/resources/fromWithFiles";
    private static final String SOURCE_FOLDER_WITH_FILES_ONLY = "src/test/resources/sourceFolderWithFilesOnly";
    private static final String EMPTY_SOURCE_FOLDER = "src/test/resources/emptySourceFolder";
    private static final String SOURCE_FILE_WITH_CONTENT_TXT = "src/test/resources/sourceFileWithContent.txt";
    private static final String EXISTING_EMPTY_DEST_FOLDER = "src/test/resources/existingEmptyDestFolder";
    private static final String SOURCE_FOLDER_NON_EXIST = "src/test/resources/sourceFolderNonExist";
    private static final String DEST_FOLDER_NON_EXIST = "src/test/resources/destFolderNonExist";
    private static final String EXISTING_DEST_FILE_TXT = "src/test/resources/existingFileTo.txt";


    @BeforeEach
    public void init() throws IOException {
        File pathFrom1 = new File(SOURCE_FOLDER_WITH_FILES_AND_FOLDERS);
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

        File pathFrom2 = new File(EMPTY_SOURCE_FOLDER);
        pathFrom2.mkdir();
        File pathFrom3 = new File(SOURCE_FILE_WITH_CONTENT_TXT);
        pathFrom3.createNewFile();
        try(FileOutputStream fileOutputStream = new FileOutputStream(pathFrom3)) {
            fileOutputStream.write("content".getBytes());
        }

        File pathFrom4 = new File(SOURCE_FOLDER_WITH_FILES_ONLY);
        pathFrom4.mkdir();
        File pathFile3 = new File(pathFrom4, "file3.txt");
        pathFile3.createNewFile();
        File pathFile4 = new File(pathFrom4, "file4.txt");
        pathFile4.createNewFile();

        File pathTo1 = new File(EXISTING_EMPTY_DEST_FOLDER);
        pathTo1.mkdir();
        File pathTo2 = new File(EXISTING_DEST_FILE_TXT);
        pathTo2.createNewFile();
    }

    @DisplayName("test countDirs for a directory with files and folders")
    @Test
    void testCountDirsForDirectoryWithFilesAndFolders() {
        assertEquals(2, FileManager.countDirs(SOURCE_FOLDER_WITH_FILES_AND_FOLDERS));
    }

    @DisplayName("test countDirs for a directory with files and folders including inner folders")
    @Test
    void testCountDirsForDirectoryWithFilesAndFoldersIncludingInnerFolders() {
        assertEquals(6, FileManager.countDirs("src/test/resources"));
    }

    @DisplayName("test countDirs for an empty directory")
    @Test
    void testCountDirsForEmptyDirectory() {
        assertEquals(0, FileManager.countDirs(EMPTY_SOURCE_FOLDER));
        assertEquals(0, FileManager.countDirs(EXISTING_EMPTY_DEST_FOLDER));
    }

    @DisplayName("test countDirs for directory consisting of files only")
    @Test
    void testCountDirsForDirectoryConsistingOfFilesOnly() {
        assertEquals(0, FileManager.countDirs(SOURCE_FOLDER_WITH_FILES_ONLY));
    }

    @DisplayName("test countDirs throw IllegalStateException for non existing directory")
    @Test
    void testCountDirsThrowIllegalStateExceptionForNonExistingDirectory() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            FileManager.countDirs(SOURCE_FOLDER_NON_EXIST);
        });
    }

    @DisplayName("test countDirs throw IllegalArgument exception if file is not a directory")
    @Test
    void testCountDirsThrowIllegalArgumentExceptionIfFileIsNotDirectory() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            FileManager.countDirs(SOURCE_FILE_WITH_CONTENT_TXT);
        });
    }


    @DisplayName("test countFiles for a directory with files and folders")
    @Test
    void testCountFilesForDirectoryWithFilesAndFolders() {
        assertEquals(4, FileManager.countFiles(SOURCE_FOLDER_WITH_FILES_AND_FOLDERS));
    }

    @DisplayName("test countFiles for a directory with files and folders including inner folders")
    @Test
    void testCountFilesForDirectoryWithFilesAndFoldersIncludingInnerFolders() {
        assertEquals(8, FileManager.countFiles("src/test/resources"));
    }

    @DisplayName("test countFiles for a directory consisting of files only")
    @Test
    void testCountFilesForDirectoryConsistingOfFilesOnly() {
        assertEquals(2, FileManager.countFiles(SOURCE_FOLDER_WITH_FILES_ONLY));
    }

    @DisplayName("test countFiles for an empty directory")
    @Test
    void testCountFilesForEmptyDirectory() {
        assertEquals(0, FileManager.countFiles(EMPTY_SOURCE_FOLDER));
        assertEquals(0, FileManager.countFiles(EXISTING_EMPTY_DEST_FOLDER));
    }

    @DisplayName("test countFiles throw IllegalStateException for non existing directory")
    @Test
    void testCountFilesThrowIllegalStateExceptionForNonExistingDirectory() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            FileManager.countFiles(DEST_FOLDER_NON_EXIST);
        });
    }

    @DisplayName("test countFiles throw IllegalArgument exception if file is not a directory")
    @Test
    void testCountFilesThrowIllegalArgumentExceptionIfFileIsNotDirectory() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            FileManager.countFiles(SOURCE_FILE_WITH_CONTENT_TXT);
        });
    }


    @DisplayName("test copy for a directory with files and folders")
    @Test
    void testCopyForDirectoryWithFilesAndFolders() throws IOException {
        FileManager.copy(SOURCE_FOLDER_WITH_FILES_AND_FOLDERS, EXISTING_EMPTY_DEST_FOLDER);
        assertTrue(compareDirectories(new File(SOURCE_FOLDER_WITH_FILES_AND_FOLDERS), new File(EXISTING_EMPTY_DEST_FOLDER)));
    }

    @DisplayName("test copy for a directory with files only")
    @Test
    void testCopyForDirectoryWithFilesOnly() throws IOException {
        FileManager.copy(SOURCE_FOLDER_WITH_FILES_ONLY, EXISTING_EMPTY_DEST_FOLDER);
        assertTrue(compareDirectories(new File(SOURCE_FOLDER_WITH_FILES_ONLY), new File(EXISTING_EMPTY_DEST_FOLDER)));
    }

    @DisplayName("test copy for one file only, with content")
    @Test
    void testCopyForOneFileOnlyWithContent() throws IOException {
        FileManager.copy(SOURCE_FILE_WITH_CONTENT_TXT, EXISTING_EMPTY_DEST_FOLDER);
        File file = new File(SOURCE_FILE_WITH_CONTENT_TXT);
        File folder = new File(EXISTING_EMPTY_DEST_FOLDER);
        File[] files = folder.listFiles();
        assertEquals(1, files.length);
        assertTrue(compareFilesByNameAndLength(files[0], file));
    }

    @DisplayName("test copy for an empty source directory")
    @Test
    void testCopyForEmptySourceDirectory() throws IOException {
        FileManager.copy(EMPTY_SOURCE_FOLDER, EXISTING_EMPTY_DEST_FOLDER);
        assertTrue(compareDirectories(new File(EMPTY_SOURCE_FOLDER), new File(EXISTING_EMPTY_DEST_FOLDER)));
    }

    @DisplayName("test copy throw IllegalStateException if file does not exist")
    @Test
    void testCopyThrowIllegalStateExceptionIfSourceFileDoesNotExist() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            FileManager.copy(SOURCE_FOLDER_NON_EXIST, EXISTING_EMPTY_DEST_FOLDER);
        });
    }


    @DisplayName("test move throw IllegalStateException if source file does not exist")
    @Test
    void testMoveThrowIllegalStateExceptionIfSourceFileDoesNotExist() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            FileManager.move(SOURCE_FOLDER_NON_EXIST, EXISTING_EMPTY_DEST_FOLDER);
        });
    }

    @DisplayName("test move for empty directory")
    @Test
    void testMoveEmptyDirectoryToNonExistingDirectory() throws IOException {
        FileManager.move(EMPTY_SOURCE_FOLDER, DEST_FOLDER_NON_EXIST);
        assertFalse(new File(EMPTY_SOURCE_FOLDER).exists());
        assertTrue(new File(DEST_FOLDER_NON_EXIST).exists());
        assertTrue(new File(DEST_FOLDER_NON_EXIST).listFiles().length == 0);
    }

    @DisplayName("test move for a directory with files and folders")
    @Test
    void testMoveForDirectoryWithFilesAndFolders() throws IOException {
        FileManager.copy(SOURCE_FOLDER_WITH_FILES_AND_FOLDERS, EXISTING_EMPTY_DEST_FOLDER);
        FileManager.move(SOURCE_FOLDER_WITH_FILES_AND_FOLDERS, DEST_FOLDER_NON_EXIST);
        assertFalse(new File(SOURCE_FOLDER_WITH_FILES_AND_FOLDERS).exists());
        assertTrue(compareDirectories(new File(DEST_FOLDER_NON_EXIST), new File(EXISTING_EMPTY_DEST_FOLDER)));
    }

    @DisplayName("test move for a directory with files only")
    @Test
    void testMoveForDirectoryWithFilesOnly() throws IOException {
        FileManager.copy(SOURCE_FOLDER_WITH_FILES_ONLY, EXISTING_EMPTY_DEST_FOLDER);
        FileManager.move(SOURCE_FOLDER_WITH_FILES_ONLY, DEST_FOLDER_NON_EXIST);
        assertFalse(new File(SOURCE_FOLDER_WITH_FILES_ONLY).exists());
        assertTrue(compareDirectories(new File(DEST_FOLDER_NON_EXIST), new File(EXISTING_EMPTY_DEST_FOLDER)));
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
