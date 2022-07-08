package mgr;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.File;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static tasks.Task.*;


public class FileBackedTasksManager extends InMemoryTaskManager {

    String filename;

    FileBackedTasksManager() {
        super();
    }

    FileBackedTasksManager(String filename) {
        this();

        this.filename = filename;

        try (BufferedReader fileReader = new BufferedReader(new FileReader(filename, StandardCharsets.UTF_8))) {

            if (fileReader.ready() && fileReader.readLine().length() != 0) {


                while (fileReader.ready()) {

                    String s = fileReader.readLine();

                    if (s.equals("")) {
                        break;
                    }

                    String c[] = s.split(",");

                    switch (c[1]) {
                        case ("TASK"):
                            super.Tasks.put(Integer.parseInt(c[0]), fromString(s));
                            super.taskCount++;
                            break;
                        case ("EPIC"):
                            super.Epics.put(Integer.parseInt(c[0]), (Epic) fromString(s));
                            super.Epics.get(Integer.parseInt(c[0])).examinationEpicStatus();
                            super.taskCount++;
                            break;
                        case ("SUBTASK"):
                            Epics.get(Integer.parseInt(c[5])).addSubtask(Integer.parseInt(c[0]), (Subtask) fromString(s));
                            super.Epics.get(Integer.parseInt(c[5])).examinationEpicStatus();
                            //Epic ep = Epics.get(c[5]);
                            //ep.addSubtask(Integer.parseInt(c[0]), (Subtask) fromString(s));
                            //Epics.put()
                            super.taskCount++;
                            break;
                        default:
                            System.out.println("Ошибка структуры файла");
                    }
                }

                //System.out.println("Восстанавливаем HistoryManager");

                ArrayList hist = null;
                String temp = fileReader.readLine();
                hist = (ArrayList) HistoryManager.fromString(temp);


                //System.out.println("hist: " + hist);

                for (int i = 0; i < hist.size(); i++) {
                    if (Tasks.containsKey(hist.get(i))) {
                        historyManager.add(Tasks.get(hist.get(i)));
                    } else if (Epics.containsKey(hist.get(i))) {
                        historyManager.add(Epics.get(hist.get(i)));
                    } else {
                        for (Integer e : Epics.keySet()) {
                            for (Subtask s : Epics.get(e).getSubtaskList()) {
                                if (s.getId() == hist.get(i)) {
                                    //System.out.println("Найденный субтаск: " + s);
                                    historyManager.add(s);
                                }
                            }
                        }
                    }
                }
                //System.out.println("/Восстанавливаем HistoryManager");
            } else {
                System.out.println("Файл пустой!");
            }

        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден, создаём новый.");

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }


    @Override
    public void addNewTask(String name, String description) {
        super.addNewTask(name, description);
        save();
    }

    @Override
    public void addNewEpic(String name, String description) {
        super.addNewEpic(name, description);
        save();
    }

    @Override
    public void addNewSubtask(int epicId, String name, String description) {
        super.addNewSubtask(epicId, name, description);
        save();
    }

    @Override
    public void editTask(Task task) {
        super.editTask(task);
        save();
    }

    @Override
    public void editEpic(Epic epic) {
        super.editEpic(epic);
        save();
    }

    @Override
    public void editSubtask(Subtask subtask) {
        super.editSubtask(subtask);
        save();
    }

    @Override
    public void setStatus(int id, TaskStatus status) {
        super.setStatus(id, status);
        save();
    }

    @Override
    public void delAllTasks() {
        super.delAllTasks();
        save();
    }

    @Override
    public void deleteTask(int d) {
        super.deleteTask(d);
        save();
    }

    @Override
    public Task getTask(int d) {
        Task t = super.getTask(d);
        save();
        return t;
    }

    @Override
    public Epic getEpic(int d) {
        Epic e = super.getEpic(d);
        save();
        return e;
    }

    @Override
    public Subtask getSubtask(int d) {
        Subtask s = super.getSubtask(d);
        save();
        return s;
    }

    static FileBackedTasksManager loadFromFile(String filename) {
        FileBackedTasksManager tmgr = new FileBackedTasksManager(filename);
        return tmgr;
    }


    void save() {

        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filename, StandardCharsets.UTF_8))) {

            fileWriter.write("id,type,name,status,description,epic\n");

            for (Task t : super.getAllTasks()) {
                fileWriter.write(t.toString() + "\n");
            }

            for (Epic t : super.getAllEpics()) {
                fileWriter.write(t.toString() + "\n");
                for (Subtask s : t.getSubtaskList()) {
                    fileWriter.write(s.toString() + "\n");
                }
            }

            fileWriter.write("\n");
            fileWriter.write(HistoryManager.toString(getHistoryManager()) + "\n");


        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден.");
        } catch (IOException e) {
            System.out.println("Произошла ошибка во время записи файла.");
        }
    }

    Task fromString(String value) {

        String[] l = value.split(",");

        int id = Integer.parseInt(l[0]);

        switch (l[1]) {
            case ("TASK"):
                Task t = new Task(id, l[2], l[4]);
                t.setStatus(TaskStatus.valueOf(l[3]));
                return t;
            case ("EPIC"):
                return new Epic(id, l[2], l[4]);
            case ("SUBTASK"):
                return new Subtask(id, Integer.parseInt(l[5]), l[2], l[4], TaskStatus.valueOf(l[3]));
            default:
                System.out.println("Ошибка структуры файла");
                return null;
        }
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
