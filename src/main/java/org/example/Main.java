package org.example;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {

    private static final String urlGames = "C://Games";

    public static void main(String[] args) {
        task1();
        task2();
        task3();
    }

    public static void task1() {
        WorkWithFile workWithFile = new WorkWithFile();
        workWithFile.setUrlGames(urlGames);
        StringBuilder sb = new StringBuilder();
        List<String> dirUrl = new ArrayList<>(List.of(workWithFile.getNewUrl("src"), workWithFile.getNewUrl("res"),
                workWithFile.getNewUrl("savegames"), workWithFile.getNewUrl("temp")));
        dirUrl.add(dirUrl.get(0) + workWithFile.getNewUrl("main"));
        dirUrl.add(dirUrl.get(0) + workWithFile.getNewUrl("test"));

        dirUrl.add(dirUrl.get(1) + workWithFile.getNewUrl("drawables"));
        dirUrl.add(dirUrl.get(1) + workWithFile.getNewUrl("vectors"));
        dirUrl.add(dirUrl.get(1) + workWithFile.getNewUrl("icons"));

        List<String> fileUrl = new ArrayList<>();
        for (String dUrl : dirUrl) {
            sb.append(workWithFile.createDir(dUrl));
            if (dUrl.endsWith("main")) {
                fileUrl.add(dUrl + workWithFile.getNewUrl("Main.java"));
                fileUrl.add(dUrl + workWithFile.getNewUrl("Utils.java"));
            }
            if (dUrl.endsWith("temp")) {
                fileUrl.add(dUrl + workWithFile.getNewUrl("temp.txt"));
            }
        }
        for (String fUrl : fileUrl) {
            sb.append(workWithFile.createFile(fUrl));
        }
        System.out.println(workWithFile.createLogs(fileUrl.get(0), sb.toString()));
    }

    public static void task2() {
        List<GameProgress> gameProgresses = new ArrayList<>(
                List.of(new GameProgress(94, 10, 15, 254.32),
                        new GameProgress(10, 1, 1, 16.0),
                        new GameProgress(15, 2, 6, 55.1)));
        List<String> savesList = new ArrayList<>();
        for (int i = 0; i < gameProgresses.size(); i++) {
            savesList.add(urlGames + "/savegames/save" + i + ".dat");
            try (FileOutputStream fos = new FileOutputStream(savesList.get(i));
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {

                oos.writeObject(gameProgresses.get(i));
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        try (ZipOutputStream zout = new ZipOutputStream(new
                FileOutputStream(urlGames + "/savegames/Saves.zip"))) {
            for (int j = 0; j < savesList.size(); j++) {
                File file = new File(savesList.get(j));
                try (FileInputStream fis = new FileInputStream(file.getPath())) {
                    ZipEntry entry = new ZipEntry("save" + j + ".dat");
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
                if (file.delete()) {
                    System.out.println("Файл " + file.getName() + " удалён");
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void task3() {
        List<GameProgress> gameProgresses = new ArrayList<>();
        try (ZipInputStream zin = new ZipInputStream(
                new FileInputStream(urlGames + "/savegames/Saves.zip"))) {
            ZipEntry entry;
            while ((entry = zin.getNextEntry()) != null) {
                File file = new File(urlGames + "/savegames/" + entry.getName());
                FileOutputStream fout = new FileOutputStream(file.getPath());
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                fout.close();
                try (FileInputStream fis = new FileInputStream(file.getPath());
                     ObjectInputStream ois = new ObjectInputStream(fis)) {
                    gameProgresses.add((GameProgress) ois.readObject());
                } catch (Exception ex) {
                    System.out.println("Ошибка при сериализации: " + ex.getMessage());
                }
                zin.closeEntry();
                file.delete();
            }
            for (var save : gameProgresses) {
                System.out.println(save);
            }
        } catch (Exception ex) {
            System.out.println("Ошибка при распаковке zip: " + ex.getMessage());
        }
    }

}