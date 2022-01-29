package com.example.demo;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


public class User {
	final private Set<String> tasks;
	final private String name;
	
	private User(String name) {
		tasks = new TreeSet<>();
		this.name = name;
	}
	
	public static User getOrCreate(Map<String, User> users, String user_name) {
		User user = users.get(user_name);
		if (user == null) {
			user = new User(user_name);
			users.put(user_name, user);
		}
		return user;
	}
	
	public String getName() {
		return name;
	}
	
	public void addTask(String task_name) {
		tasks.add(task_name);
	}
	
	public void delTask(String task_name) {
		tasks.remove(task_name);
	}
	
	public static String listTasks(Map<String, User> users, String user_name) {
		User user = users.get(user_name);
		if (user == null)
			return "[]";
		return "[" + String.join(", ", user.tasks) + "]";
	}
}
