package com.example.demo;

import java.util.*;

public class Task {
	enum State {
		CREATED,
		CLOSED
	}
	
	static class NoAccessException extends Exception {};
	
	static final String answCREATED = "CREATED";
	static final String answDELETED = "DELETED";
	static final String answCLOSED = "CLOSED";
	static final String answREOPENED = "REOPENED";
	static final String answTASKS = "TASKS";
	static final String answWRONG = "WRONG_FORMAT";
	static final String answDENIED = "ACCESS_DENIED";
	static final String answERROR = "ERROR";
	static final String answOK = "OK";
	
	private String name;
	private String user;
	private State state;
	
	private static Map<String, Task> allTasks = new LinkedHashMap<>();	
	
	public Task(String name, String user) {
	    this.name = name;
	    this.user = user;
	    this.state = State.CREATED;
	    
	    allTasks.put(name, this);
	}
	
	private static String doDelAll() {
		allTasks.clear();
		return answOK;
	}
	
	private static Task getExistingTask(String user, String arg) throws NoAccessException {
		Task ret;
		try {
			ret = allTasks.get(arg);
			
			if ( !ret.user.equals(user) )
				throw new NoAccessException();
			
			return ret;
		}
		catch (NullPointerException e) {
			return null;
		}
	}
	
	private static String doCreate(String user, String arg) {
		Task existingTask;	
		
		try {
			existingTask = getExistingTask(user, arg);
	
			// если задания нет, создаем
			if (existingTask == null) {
				new Task(arg, user);
				return answCREATED;
			}
			// если есть, возвращаем ошибку
			else
				return answERROR;
		}
		catch (NoAccessException e) {
			// если нет прав, возвращаем отказ в доступе
			return answDENIED;
		}
	}
	
	private static String doClose(String user, String arg) {
		Task existingTask;	
		
		try {
			existingTask = getExistingTask(user, arg);
		
			// если задания нет, возвращаем ошибку
			if (existingTask == null)
				return answERROR;
			// если есть  
			// проверяем статус на CREATED и меняем статус на CLOSED
			else {
				if (existingTask.state == State.CREATED) {
					existingTask.state = State.CLOSED; 
					return answCLOSED;
				}	
				else	
					return answERROR;
			}
		}	
		// если задание есть и под другим пользователем, отказываем в доступе
		catch (NoAccessException e) {
			// если нет прав, возвращаем отказ в доступе
			return answDENIED;
		}
	}
	
	private static String doDelete(String user, String arg) {
		Task existingTask;	
		
		try {
			existingTask = getExistingTask(user, arg);
		
			// если задания нет, возвращаем ошибку
			if (existingTask == null)
				return answERROR;
			// 	если задание есть 
			else {
				// проверяем статус на CLOSED и удаляем задание из all_Tasks
				if (existingTask.state == State.CLOSED) {
					allTasks.remove(arg);
					return answDELETED;
				}	
				else
					return answERROR;	
			}	
		}	
		// если задание есть и под другим пользователем, отказываем в доступе
		catch (NoAccessException e) {
			// если нет прав, возвращаем отказ в доступе
			return answDENIED;
		}		
	}
	
	private static String doReopen(String user, String arg) {
		Task existingTask;	
		
		try {
			existingTask = getExistingTask(user, arg);
		

			// если задания нет, возвращаем ошибку
			if (existingTask == null)
				return answERROR;
			// если задание есть 
			else {
				// проверяем статус на CLOSED и меняем на CREATED
				if (existingTask.state == State.CLOSED) {
					existingTask.state = State.CREATED;
					return answREOPENED;
				}
				else
					return answERROR;
			}	
		}	
		// если задание есть и под другим пользователем, отказываем в доступе
		catch (NoAccessException e) {
			// если нет прав, возвращаем отказ в доступе
			return answDENIED;
		}		
	}
	
	private static String doList(String user, String arg) {
		String ret = "";
		
		for (Map.Entry<String, Task> entry: allTasks.entrySet()) {
		
			if (entry.getValue().user.equals(arg)) {
				if (ret != "") 
					ret = ret + ", ";
				ret = ret + entry.getValue().name;
			}
		}
		
		ret = "TASKS [" + ret + "]";
		return ret;
	}

	public static String doIt(String line) {
		String[] words;
		String command;
		String user;
		String arg;
		
		words = line.split(" ");
		
		// по формату команда может состоять из 1 или 3 частей
		// если из 1, это может быть только __DELETE_ALL 
		if (words.length == 1) {  
			if ( words[0].equals("__DELETE_ALL") ) {
				return doDelAll();
			}
			else
				return answWRONG;
		}
		//остальные команды состоят из 3 частей
		else if (words.length == 3) {
			user 	= words[0];
			command = words[1];
			arg 	= words[2];

			if ( command.equals("CREATE_TASK") )
				return doCreate(user, arg);
			else if ( command.equals("CLOSE_TASK") )
				return doClose(user, arg);
			else if ( command.equals("DELETE_TASK") )
				return doDelete(user, arg);
			else if ( command.equals("REOPEN_TASK") )
				return doReopen(user, arg);
			else if ( command.equals("LIST_TASK") )
				return doList(user, arg);			
			else 
				return answWRONG;
		
		}
		// команды не из 1 или 3 частей признаем некорректными по формату
		else
			return answWRONG;
	}
}
