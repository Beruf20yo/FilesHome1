package tasks.junit;

import org.example.tasks.Task;
import org.example.tasks.TaskOne;
import org.example.tasks.TaskTwo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.CleanupMode;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;

public class TaskTwoTest {
    @TempDir(cleanup = CleanupMode.ALWAYS)
    static
    Path tempDir;
    static String urlGames;
    @BeforeAll
    static void createTempDirUrl(){
        urlGames = tempDir.toFile().getPath();
    }
    @Test
    void taskTwo(){
        Task taskOne = new TaskOne(urlGames);
        taskOne.makeTask();
        Task taskTwo = new TaskTwo(urlGames);
        taskTwo.makeTask();
        Assertions.assertFalse(new File(urlGames + "/savegames/save" + 0 + ".dat").exists());
        Assertions.assertFalse(new File(urlGames + "/savegames/save" + 1 + ".dat").exists());
        Assertions.assertFalse(new File(urlGames + "/savegames/save" + 2 + ".dat").exists());
    }
}