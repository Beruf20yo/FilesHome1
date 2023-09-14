package org.example.tasks;

import lombok.AllArgsConstructor;
import org.example.GameProgress;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@AllArgsConstructor
public class TaskTwo implements Task {
    private final String urlGames;
    @Override
    public String makeTask() {
        return saveToZip(saveProgress(createProgresses()));
    }

    private List<GameProgress> createProgresses() {
        return new ArrayList<>(
                List.of(new GameProgress(94, 10, 15, 254.32),
                        new GameProgress(10, 1, 1, 16.0),
                        new GameProgress(15, 2, 6, 55.1)));

    }
    private List<String> saveProgress(List<GameProgress> gameProgresses){
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
        return savesList;
    }
    private String saveToZip(List<String> savesList){
        StringBuilder deleteLogs = new StringBuilder();
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
                    deleteLogs.append("Файл ").append(file.getName()).append(" удалён\n");
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return deleteLogs.toString();
    }
}
