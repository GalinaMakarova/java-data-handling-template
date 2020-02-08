package com.epam.izh.rd.online.repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
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
        long count = 0;
        File directory = new File("src/main/resources/" + path);
        if (directory.isDirectory()) {
            File[] directoryFiles = directory.listFiles();

            for (File file : directoryFiles) {
                count = count +
                        countFilesInDirectory(path + "/" + file.getName());
            }
        } else {
            count = count + 1;
        }
        return count;
    }

    /**
     * Метод рекурсивно подсчитывает количество папок в директории, считая корень
     *
     * @param path путь до директории
     * @return число папок
     */
    @Override
    public long countDirsInDirectory(String path) {
        long count = 0;
        File directory = new File("src/main/resources/" + path);
        if (directory.isDirectory()) {
            count = count + 1;
            File[] directoryFolders = directory.listFiles();

            for (File folder : directoryFolders) {
                count = count +
                        countDirsInDirectory(path + "/" + folder.getName());
            }
        }
        return count;
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
            if (Objects.requireNonNull(fromDirectory.listFiles()).length > 0) {
                File[] fromDirFiles = fromDirectory.listFiles();
                File bufFileFrom = Objects.requireNonNull(fromDirFiles[0]);
                for (int i = 0; i < fromDirFiles.length; i++) {
                    if (bufFileFrom.isFile() && bufFileFrom.getName().endsWith(".txt")) {
                        File bufFileTo = new File(toDirectory.toPath() + "\\" + bufFileFrom.getName());
                        try {
                            Files.copy(bufFileFrom.toPath(), bufFileTo.toPath(), REPLACE_EXISTING);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
        try {
            File targetDirectory = new File("src/main/resources/" + path);
            File targetFile = new File("src/main/resources/" + path, name);
            if (!targetDirectory.exists()) {
                if (targetDirectory.mkdir()) {
                    return createFile(path, name);
                }
            } else {
                if (targetDirectory.isDirectory()) {
                    try {
                        return targetFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        return false;
    }

    /**
     * Метод считывает тело файла .txt из папки src/main/resources
     *
     * @param fileName имя файла
     * @return контент
     */
    @Override
    public String readFileFromResources(String fileName) {
        try {
            File targetFile = new File(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(fileName)).getFile());
            StringBuilder stringBuffer = new StringBuilder();
            Scanner scannerReader = new Scanner(targetFile);
            while (scannerReader.hasNextLine()) {
                String fileString = scannerReader.nextLine();
                stringBuffer.append(fileString);
            }
            scannerReader.close();
            return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}