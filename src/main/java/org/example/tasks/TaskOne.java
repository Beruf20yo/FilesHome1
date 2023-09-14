package org.example.tasks;

import org.example.WorkWithFile;

import java.util.ArrayList;
import java.util.List;

public class TaskOne implements Task{
    private final WorkWithFile workWithFile;
    private final StringBuilder logs = new StringBuilder();

    public TaskOne(String urlGames) {
        this.workWithFile = new WorkWithFile(urlGames);
    }
    @Override
    public String makeTask(){
        return createAllFiles(createFilesUrl(createDirectoriesUrl()));
    }
    private List<String> createDirectoriesUrl(){
        List<String> dirUrl = new ArrayList<>(List.of(workWithFile.getNewUrl("src"), workWithFile.getNewUrl("res"),
                workWithFile.getNewUrl("savegames"), workWithFile.getNewUrl("temp")));
        dirUrl.add(dirUrl.get(0) + workWithFile.getNewUrl("main"));
        dirUrl.add(dirUrl.get(0) + workWithFile.getNewUrl("test"));

        dirUrl.add(dirUrl.get(1) + workWithFile.getNewUrl("drawables"));
        dirUrl.add(dirUrl.get(1) + workWithFile.getNewUrl("vectors"));
        dirUrl.add(dirUrl.get(1) + workWithFile.getNewUrl("icons"));
        return dirUrl;
    }
    private List<String> createFilesUrl(List<String> dirUrl){
        List<String> fileUrl = new ArrayList<>();
        for (String dUrl : dirUrl) {
            logs.append(workWithFile.createDir(dUrl));
            if (dUrl.endsWith("main")) {
                fileUrl.add(dUrl + workWithFile.getNewUrl("Main.java"));
                fileUrl.add(dUrl + workWithFile.getNewUrl("Utils.java"));
            }
            if (dUrl.endsWith("temp")) {
                fileUrl.add(dUrl + workWithFile.getNewUrl("temp.txt"));
            }
        }
        return fileUrl;
    }
    private String createAllFiles(List<String> fileUrl){
        for (String fUrl : fileUrl) {
            logs.append(workWithFile.createFile(fUrl));
        }
        return workWithFile.createLogs(fileUrl.get(0), logs.toString());
    }

}
