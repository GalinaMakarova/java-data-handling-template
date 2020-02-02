package com.epam.izh.rd.online.repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Scanner;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class SimpleFileRepository implements FileRepository {

    /**
     * Метод рекурсивно подсчитывает количество файлов в директории
     *
     * @param path путь до директори
     * @return файлов, в том числе скрытых
     */
    @Override
    public long countFilesInDirectory(String path) {
        long resultCountInDirectory = 0;
        File targetObjectInDirectory = new File("src/main/resources/" + path);
        if (targetObjectInDirectory.isDirectory()) {
            File[] internalDirectoryObjectsArray = targetObjectInDirectory.listFiles();
            assert internalDirectoryObjectsArray != null;
            for (File internalDirectoryObject : internalDirectoryObjectsArray) {
                resultCountInDirectory = resultCountInDirectory +
                        countFilesInDirectory(path + "/" + internalDirectoryObject.getName());
            }
        } else {
            resultCountInDirectory = resultCountInDirectory + 1;
        }

        return resultCountInDirectory;
    }

    /**
     * Метод рекурсивно подсчитывает количество папок в директории, считая корень
     *
     * @param path путь до директории
     * @return число папок
     */
    @Override
    public long countDirsInDirectory(String path) {
        long resultFoldersCount = 0;
        File targetObjectInDirectory = new File("src/main/resources/" + path);
        if (targetObjectInDirectory.isDirectory()) {
            resultFoldersCount = resultFoldersCount + 1;
            File[] internalDirectoryObjectsArray = targetObjectInDirectory.listFiles();
            assert internalDirectoryObjectsArray != null;
            for (File internalDirectoryObject : internalDirectoryObjectsArray) {
                resultFoldersCount = resultFoldersCount +
                        countDirsInDirectory(path + "/" + internalDirectoryObject.getName());
            }
        }
        return resultFoldersCount;
    }

    /**
     * Метод копирует все файлы с расширением .txt
     *
     * @param from путь откуда
     * @param to   путь куда
     */

    @Override
    public void copyTXTFiles(String from, String to) {
        File fromDirectory = new File("src/main/resources/" + from);
        File toDirectory = new File("src/main/resources/" + to);
        if (toDirectory.exists() && fromDirectory.exists() && fromDirectory.isDirectory()
                && toDirectory.isDirectory()) {
            File[] fromDirectoryFilesList = fromDirectory.listFiles();
            assert fromDirectoryFilesList != null;
            for (File bufferedFile : fromDirectoryFilesList) {
                if (bufferedFile.isFile() && bufferedFile.getName().endsWith(".txt")) {
                    File bufferedFileNewPath = new File(toDirectory.toPath() + "\\" + bufferedFile.getName());
                    try {
                        Files.copy(bufferedFile.toPath(), bufferedFileNewPath.toPath(), REPLACE_EXISTING);
                    } catch (Exception e) {
                        break;
                    }
                }
            }
        } else if (!toDirectory.exists() && fromDirectory.exists() && fromDirectory.isDirectory()) {
            try {
                Files.createDirectory(toDirectory.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            copyTXTFiles(from, to);
        }
    }

    /**
     * Метод создает файл на диске с расширением txt
     *
     * @param path путь до нового файла
     * @param name имя файла
     * @return был ли создан файл
     */
    @Override
    public boolean createFile(String path, String name) {
        File targetDirectory = new File("src/main/resources/" + path);
        File targetFile = new File("src/main/resources/" + path, name);
        if (!targetDirectory.exists()) {
            if (targetDirectory.mkdir()) {
                return createFile(path, name);
            }
            return false;
        } else {
            if (targetDirectory.isDirectory()) {
                try {
                    return targetFile.createNewFile();
                } catch (IOException e) {
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    /**
     * Метод считывает тело файла .txt из папки src/main/resources
     *
     * @param fileName имя файла
     * @return контент
     */
    @Override
    public String readFileFromResources(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            File targetFile = new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
            StringBuilder stringBuffer = new StringBuilder();
            Scanner scannerReader = new Scanner(targetFile);
            while (scannerReader.hasNextLine()) {
                String fileString = scannerReader.nextLine();
                stringBuffer.append(fileString);
            }
            scannerReader.close();
            return stringBuffer.toString();
        } catch (Exception e) {
            return null;
        }
    }
}
