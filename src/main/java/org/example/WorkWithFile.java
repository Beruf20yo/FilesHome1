package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WorkWithFile {
    private String urlGames = "C://Games";

    public void setUrlGames(String urlGames) {
        this.urlGames = urlGames;
    }

    public String createFile(String urlFile) {
        File file = new File(urlGames, urlFile);
        try {
            file.createNewFile();
            return "Файл " + file.getName() + " успешно создан\n";
        } catch (IOException e) {
            return "Ошибка загрузки файла " + e.getMessage();
        }

    }

    public String createDir(String dirUrl) {
        File dir = new File(urlGames, dirUrl);
        if (dir.mkdir()) {
            return "Директория " + dir.getName() + " успешно создана\n";
        } else {
            return "Ошибка при создании\n";
        }

    }

    public String getNewUrl(String url) {
        return "/" + url;
    }

    public String createLogs(String urlFile, String info) {
        try (FileWriter writer = new FileWriter(urlGames + urlFile, false)) {
            writer.write(info);
            return "Информация успешно записана в файл";
        } catch (IOException e) {
            return "Ошибка при работе с файлом " + e.getMessage();
        }
    }
}
