package org.example;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
@NoArgsConstructor
@AllArgsConstructor
public class WorkWithFile {
    @Setter
    private String urlGames = "C://Games";

    public String createFile(String urlFile) {
        File file = new File(urlGames, urlFile);
        try {
            if (file.createNewFile()){
                return "Файл " + file.getName() + " успешно создан\n";
            }
            return "Файл " + file.getName() + "не удалось сохранить";
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
