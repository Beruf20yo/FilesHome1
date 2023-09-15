import org.example.GameProgress;
import org.example.tasks.Task;
import org.example.tasks.TaskOne;
import org.example.tasks.TaskThree;
import org.example.tasks.TaskTwo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.CleanupMode;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class TasksTest {
    @TempDir(cleanup = CleanupMode.DEFAULT)
    static
    Path tempDir;
    static String urlGames;
    @BeforeAll
    static void createTempDirUrl(){
        urlGames = tempDir.toFile().getPath();
    }
    @Test
    void taskOne(){
        TaskOne taskOne = new TaskOne(urlGames);
        Assertions.assertEquals("Информация успешно записана в файл",taskOne.makeTask());
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

    @Test
    void taskTwo(){
        Task taskTwo = new TaskTwo(urlGames);
        String answers = """
                Файл save0.dat удалён
                Файл save1.dat удалён
                Файл save2.dat удалён
                """;
        Assertions.assertEquals(answers,taskTwo.makeTask());
    }

    @Test
    void taskThree(){
        TaskThree taskThree = new TaskThree(urlGames);
        List<GameProgress> gameProgresses = new ArrayList<>(
                List.of(new GameProgress(94, 10, 15, 254.32),
                        new GameProgress(10, 1, 1, 16.0),
                        new GameProgress(15, 2, 6, 55.1)));
        Set<GameProgress> setToCheck = new TreeSet<>(Comparator.comparingInt(GameProgress::getLvl));
        Set<GameProgress> setFromTask = new TreeSet<>(Comparator.comparingInt(GameProgress::getLvl));
        setToCheck.addAll(gameProgresses);
        setFromTask.addAll(taskThree.getProgressFromZip());
        Assertions.assertEquals(setToCheck,setFromTask);
    }
}
