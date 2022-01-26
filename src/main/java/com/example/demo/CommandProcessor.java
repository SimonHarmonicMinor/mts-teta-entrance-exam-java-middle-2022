package com.example.demo;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommandProcessor {

    public ArrayList<Tasklist> tasklist = new ArrayList<>();
    //  перечисление и метод для него возращающий соответствие
    public enum CommandList {
        CREATE_TASK(), //MyTask- создать задачу с названием MyTask
        DELETE_TASK(), // DELETE_TASK MyTask - удалить задачу MyTask
        CLOSE_TASK(), //CLOSE_TASK MyTask - закрыть задачу MyTask
        REOPEN_TASK(), //  REOPEN_TASK MyTask - заново открыть задачу MyTask
        LIST_TASK(); //   LIST_TASK USER

        CommandList() {
        }

        public static boolean contains(String tekst) {

            for (CommandList c : CommandList.values()) {
                if (c.name().equals(tekst)) {
                    return true;
                }
            }

            return false;
        }

    }

// обьект команды после парсинга
    public static class parcedCommand {

        public String user;
        public CommandList comand;
        public String arg;

        public parcedCommand(String command) {
            String[] commandStructure = command.split(" ");
            this.user = commandStructure[0];
            this.comand = CommandList.valueOf(commandStructure[1]);
            this.arg = commandStructure[2];

        }

    }



    // основной процессор команд
    public String processCommand(String command, ArrayList<Tasklist> tlist) {
        String res;
        //проверяем на формат
        if (!ifCommandWrongFormat(command)) {
            res = "WRONG_FORMAT";
        } else {

//если с форматом все ок то парсим команду в обьект и дальше работаем с ним
            parcedCommand newCommand = new parcedCommand(command);

            // вывод списка без проверки на права пользователя т.к. доступен всем
            if (newCommand.comand.equals(CommandList.LIST_TASK)) {
                res = comLIST_TASK(newCommand, tlist);
                return res;
            }
            // Создание задачи без проверки на права пользователя т.к. создание просто базовая функция
            if (newCommand.comand.equals(CommandList.CREATE_TASK)) {
                res = comCREATE_TASK(newCommand, tlist);
                return res;
            }

            // Проверяем на существование задачи если это не создание и не вывод списка, обработанные выше, если несушествует такой задачи то ошибка
            if (!ifEXIST(newCommand, tlist)) {
                res = "ERROR";
                return res;
            }


            // Для всех остальных команд проверяем права доступа на запрашиваемую задачу
            if (!ifACESS(newCommand, tlist)) {
                res = "ACCESS_DENIED";
                return res;
            }

            /// проверяем оставшиеся задачи

            switch (newCommand.comand) {
                case CLOSE_TASK:
                    res = comCLOSE_TASK(newCommand, tlist);
                    break;
                case DELETE_TASK:
                    res = comDELETE_TASK(newCommand, tlist);
                    break;
                case REOPEN_TASK:
                    res = comREOPEN_TASK(newCommand, tlist);
                    break;


                default:
                    res = "ERROR ";
                    break;
            }


        }

        return res;
    }

    // функция проеряет доступ к задаче
    private Boolean ifACESS(parcedCommand newCommand, ArrayList<Tasklist> tlist) {
        List<Tasklist> forACCES = tlist.stream()
                .filter(p -> p.user.name.equals(newCommand.user))
                .filter(p -> p.task.taskname.equals(newCommand.arg))
                .collect(Collectors.toList());
        boolean st = false;
        if (!forACCES.isEmpty()) {

            st = true;
        }

        return st;

    }
    // проверяем на существование задачи с таким же именем
    private Boolean ifEXIST(parcedCommand newCommand, ArrayList<Tasklist> tlist) {
        List<Tasklist> forEXIST = tlist.stream()
                .filter(p -> p.task.taskname.equals(newCommand.arg))
                .collect(Collectors.toList());
        boolean st = false;
        if (!forEXIST.isEmpty()) {

            st = true;
        }

        return st;

    }
// Созадние задачи, дублируем фильтр  смотрим чо осталось после него и в случае если ничего нет то создаем новую задачу, в противном случае ошибка т.к. такое имя уже есть
    private String comCREATE_TASK(parcedCommand newCommand, ArrayList<Tasklist> tlist) {
        List<Tasklist> nonUnical = tlist.stream()
                .filter(p -> p.task.status != Task.taskstatus.DELETED)
                .filter(p -> p.task.taskname.equals(newCommand.arg))
                .collect(Collectors.toList());


        if (nonUnical.isEmpty()) {

            User nuser = new User().GetWithName(newCommand.user);
            Task ntask = new Task().GetWithName(newCommand.arg);
            Tasklist nTL = new Tasklist();
            nTL.setTask(ntask);
            nTL.setUser(nuser);
            tlist.add(nTL);
            return "CREATED";

        } else {
            return "ERROR";
        }


    }
// удаление задачи, дублируем фильтр  смотрим чо осталось после него и в случае если есть что то удаляем задачу, в противном случае ошибка т.к. задачи нет либо ее статус не позволет закрыть

    private String comDELETE_TASK(parcedCommand newCommand, ArrayList<Tasklist> tlist) {
        List<Tasklist> forDelete = tlist.stream()
                .filter(p -> p.user.name.equals(newCommand.user))
                .filter(p -> p.task.status == Task.taskstatus.CLOSED)
                .filter(p -> p.task.taskname.equals(newCommand.arg))
                .collect(Collectors.toList());
        String st = "ERROR";
        if (!forDelete.isEmpty()) {
            Tasklist tClose = forDelete.get(0);
            tClose.task.status = Task.taskstatus.DELETED;
            st = "DELETED";
        }
        return st;

    }
// переоткрытие задачи, дублируем фильтр  смотрим что осталось после него и в случае если есть что то переоткрываем задачу, в противном случае ошибка т.к. задачи нет либо ее статус не позволет переоткрыть

    private String comREOPEN_TASK(parcedCommand newCommand, ArrayList<Tasklist> tlist) {
        List<Tasklist> forReopen = tlist.stream()
                .filter(p -> p.user.name.equals(newCommand.user))
                .filter(p -> p.task.status == Task.taskstatus.CLOSED)
                .filter(p -> p.task.taskname.equals(newCommand.arg))
                .collect(Collectors.toList());
        String st = "ERROR";
        if (!forReopen.isEmpty()) {
            Tasklist tClose = forReopen.get(0);
            tClose.task.status = Task.taskstatus.CREATED;
            st = "REOPEN";
        }
        return st;

    }
//список всех неудаленных задач  для пользователя

    private String comLIST_TASK(parcedCommand newCommand, ArrayList<Tasklist> tlist) {
        List<Tasklist> usrFiltered = tlist.stream()
                .filter(p -> p.task.status != Task.taskstatus.DELETED)
                .filter(p -> p.user.name.equals(newCommand.arg))
                .collect(Collectors.toList());


        return "TASKS " + usrFiltered.toString();

    }

    //Закрытие задачи с проверкой на неудаление и принадлежность пользователя

    private String comCLOSE_TASK(parcedCommand newCommand, ArrayList<Tasklist> tlist) {
        List<Tasklist> nonDelete = tlist.stream()
                .filter(p -> p.task.taskname.equals(newCommand.arg))
                .filter(p -> p.user.name.equals(newCommand.user))
                .filter(p -> p.task.status != Task.taskstatus.DELETED)
                .collect(Collectors.toList());
        String st = "ERROR";
        if (!nonDelete.isEmpty()) {
            Tasklist tClose = nonDelete.get(0);
            tClose.task.status = Task.taskstatus.CLOSED;
            st = "CLOSED";
        }
        return st;

    }
// функция проверки формата Делаем сплит и смотрим есть ли три параметра, в противном случае ошибка
    Boolean ifCommandWrongFormat(String command) {

        String[] commandStructure = command.split(" ");

        if (commandStructure.length <= 2) {
            //System.out.println("WRONG_FORMAT  -  мало параметров");
            return false;
        }


        if (commandStructure.length > 3) {

            // System.out.println("WRONG_FORMAT  -  больше 3 параметров");
            return false;
        }

        if (!CommandList.contains(commandStructure[1])) {

            // System.out.println("WRONG_FORMAT  -  команда не известна");
            return false;
        }

        return true;
    }


}
