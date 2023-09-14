package org.example.tasks;

import lombok.AllArgsConstructor;
import org.example.GameProgress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
@AllArgsConstructor
public class TaskThree implements Task{
    private final String urlGames;

    @Override
    public String makeTask() {
        StringBuilder sb = new StringBuilder();
        for(var game: getProgressFromZip()){
            sb.append(game).append("\n");
        }
        return sb.toString();
    }
    public List<GameProgress> getProgressFromZip(){
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
        } catch (Exception ex) {
            System.out.println("Ошибка при распаковке zip: " + ex.getMessage());
        }
        return gameProgresses;
    }

}
