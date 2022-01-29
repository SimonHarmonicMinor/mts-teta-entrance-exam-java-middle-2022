package com.example.demo;

import java.util.HashMap;
import java.util.Map;

public class Manager {
	final static String
		create_task = "CREATE_TASK",
		delete_task = "DELETE_TASK",
		close_task = "CLOSE_TASK",
		reopen_task = "REOPEN_TASK",
		list_task = "LIST_TASK",
		
		created = "CREATED",
		deleted = "DELETED",
		closed = "CLOSED",
		reopened = "REOPENED",
		tasks_arr = "TASKS",
		wrong_format = "WRONG_FORMAT",
		access_denied = "ACCESS_DENIED",
		other_error = "ERROR";
	
	final private Map<String, User> users = new HashMap<>();
	final private Map<String, Task> tasks = new HashMap<>();
	
	public String process(String cmd) {
		String[] args = cmd.split("\\s+", 4);
		if (args.length != 3)
			return wrong_format;
		
		String caller = args[0], argument = args[2];

		try {
			switch (args[1]) {
			case create_task:
				User user = User.getOrCreate(users, caller);
				Task.create(tasks, user, argument);
				return created;
			case delete_task:
				Task.delete(tasks, caller, argument);
				return deleted;
			case close_task:
				Task.close(tasks, caller, argument);
				return closed;
			case reopen_task:
				Task.reopen(tasks, caller, argument);
				return reopened;
			case list_task:
				return tasks_arr + " " + User.listTasks(users, argument);
			}
		} catch (ErrAccess err) {
			return access_denied;
		} catch (ErrOther err) {
			return other_error;
		}
		
		return wrong_format;
	}

}
