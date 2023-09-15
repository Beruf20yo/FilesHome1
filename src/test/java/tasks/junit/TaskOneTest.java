package tasks.junit;

import org.example.tasks.TaskOne;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.CleanupMode;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

public class TaskOneTest {
    @TempDir(cleanup = CleanupMode.ALWAYS)
    static
    Path tempDir;
    static String urlGames;
    @BeforeAll
    static void createTempDirUrl(){
        urlGames = tempDir.toFile().getPath();
    }
    @Test
    void makeTaskOneTest(){
        TaskOne taskOne = new TaskOne(urlGames);
        Assertions.assertEquals("Информация успешно записана в файл", taskOne.makeTask());
        StringBuilder sb = new StringBuilder();
        String answers = """
                Директория src успешно создана
                Директория res успешно создана
                Директория savegames успешно создана
                Директория temp успешно создана
                Директория main успешно создана
                Директория test успешно создана
                Директория drawables успешно создана
                Директория vectors успешно создана
                Директория icons успешно создана
                Файл temp.txt успешно создан
                Файл Main.java успешно создан
                Файл Utils.java успешно создан
                """;
        try (BufferedReader reader = new BufferedReader(new FileReader(tempDir.toFile().getPath()+"/temp/temp.txt"))) {
            String line = reader.readLine();
            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(answers, sb.toString());
    }
}
