package com.example.demo.service.commandService;

import static com.example.demo.Utils.Utils.getBean;
import static java.util.Objects.nonNull;

import com.example.demo.exception.DemoException;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.type.TaskStatus;
import java.util.ServiceLoader;
import java.util.Set;

abstract class AbstractCommandService implements CommandService {

    protected TaskRepository taskRepository;
    protected UserRepository userRepository;
    protected String serviceName;

    public AbstractCommandService() {
        this.taskRepository = getBean(ServiceLoader.load(TaskRepository.class));
        this.userRepository = getBean(ServiceLoader.load(UserRepository.class));
    }

    @Override
    public String getName() {
        return serviceName;
    }

    protected void validateUserRights(String taskName, TaskStatus taskStatus, String userName) throws DemoException {
        if (!nonNull(taskStatus)) {
            throw new DemoException("Данной задачи не существует", "ERROR");
        }

        Set<String> tasks = userRepository.readTasksForUser(userName);
        if (!nonNull(tasks) || !tasks.contains(taskName)) {
            throw new DemoException("Вы не можете редактировать чужие задачи", "ACCESS_DENIED");
        }

    }
}
