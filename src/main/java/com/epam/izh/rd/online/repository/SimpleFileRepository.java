package com.epam.izh.rd.online.repository;

import java.io.File;
import java.io.IOException;

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
        return;
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
            targetDirectory.mkdir();
            return createFile(path, name);
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
        return null;
    }
}
