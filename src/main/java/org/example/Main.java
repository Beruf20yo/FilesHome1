package org.example;

import org.example.tasks.Task;
import org.example.tasks.TaskOne;
import org.example.tasks.TaskThree;
import org.example.tasks.TaskTwo;

public class Main {

    private static final String urlGames = "C://Games";

    public static void main(String[] args) {
        Task taskOne = new TaskOne(urlGames);
        System.out.println(taskOne.makeTask());
        Task taskTwo = new TaskTwo(urlGames);
        System.out.println(taskTwo.makeTask());
        Task taskThree = new TaskThree(urlGames);
        System.out.println(taskThree.makeTask());
    }

}