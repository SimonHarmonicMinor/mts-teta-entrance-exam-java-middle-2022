package com.example.demo;

import com.example.demo.entity.AbstractTask;
import com.example.demo.entity.Storable;
import com.example.demo.exceptions.StoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Реализация хранения элементов в памяти.
 * Все задачи, кроме удаленных, хранятся в основном хранилище.
 * Удаленные, не хранятся, т.к. согласно единственного условия по
 * получению списка задания: "Пользователь может получить список всех
 * задач любого другого пользователя, кроме удаленных.". Это подразумевает
 * что получение и работа с удаленными задачами не предусмотрена и их
 * хранение не имеет смысла
 * @author Eugenu Modenov
 */
public class TaskStore implements Storable<AbstractTask> {
    /**
     * Структура для хранения основных данных.
     * В качестве ключа основной мапы служит имя пользователя.
     * В качестве ключа вложеной мапы - название таска.
     */
    private final Map<String, Map<String, AbstractTask>> mainStore = new HashMap<>();

    /**
     * Сет для хранения имен тасков для соблюдения условия уникальности
     * В стек добавляются новые имена, а имена удаленных тасков удаляются из сета
     * Также по сету можно быстро проверить существование тасков
     */
    private final Set<String> taskNames = new HashSet<>();

    private static final Logger LOG = LoggerFactory.getLogger(TaskStore.class);
    /**
     * Добавление/обновление таска
     * @param task задание
     * @throws StoreException ошибка при добавлении, в случае если имя уже зарезервировано
     */
    @Override
    public void save(AbstractTask task) throws StoreException {
        if(mainStore.containsKey(task.getUserName())){
            Map<String, AbstractTask> tasks = mainStore.get(task.getUserName());
            if(tasks.containsKey(task.getName()) || this.taskNames.add(task.getName())){
                tasks.put(task.getName(), task);
                LOG.debug("Task {} added/updated  for user {}", task.getName(), task.getUserName());
            } else {
                LOG.error("Create task with not unique name {} forbidden", task.getName());
                throw new StoreException("Name is already taken");
            }

        } else{
            if (this.taskNames.add(task.getName())) {
                Map<String, AbstractTask> map = new HashMap<>();
                map.put(task.getName(), task);
                mainStore.put(task.getUserName(), map);
                LOG.debug("Task {} added for new user {}", task.getName(), task.getUserName());
            } else{
                LOG.error("Create task with not unique name {} forbidden", task.getName());
                throw new StoreException("Name is already taken");
            }

        }
    }

    /**
     * Удаление задания из хранилища.
     * При удалении задания дополнительно удаляет и имя из зарезервированных имен
     * @param task задание
     * @throws StoreException ошибка при удалении, возникает при отсутствии задачи у пользователя
     * или отсутствии самого пользователя
     */
    @Override
    public void delete(AbstractTask task) throws StoreException {
        if(mainStore.containsKey(task.getUserName()) &&
                mainStore.get(task.getUserName()).containsKey(task.getName())){
            mainStore.get(task.getUserName()).remove(task.getName());
            this.taskNames.remove(task.getName());
            LOG.debug("Task {} deleted at user {}", task.getName(), task.getUserName());
        } else {
            LOG.error("Task {} not found at user {}", task.getName(), task.getUserName());
            throw new StoreException("Task not found");
        }
    }

    /**
     * Возвращает список всех неудаленных тасков пользователя.
     * Если пользователь не найден вернет Null
     * @param userName имя пользователя
     * @return коллекция из заданий
     */
    @Override
    public Collection<AbstractTask> get(String userName) {
        if(this.mainStore.containsKey(userName))
            return this.mainStore.get(userName).values();
        else
            return Collections.EMPTY_LIST;
    }

    /**
     * Функция поиска таска по имени пользователя и названию таска
     * @param userName пользователь
     * @param taskName имя таска
     * @return найденный таск или null, если таск принадлежит другому пользователю
     * @throws StoreException таска не существует
     */
    @Override
    public AbstractTask findByUserAndTask(String userName, String taskName) throws StoreException {
        if (!this.taskNames.contains(taskName)) {
            LOG.error("Task {} deleted or not exist in store", taskName);
            throw new StoreException("Task was deleted");
        }
        if (this.mainStore.containsKey(userName)) {
            return this.mainStore.get(userName).get(taskName);
        }
        LOG.debug("Task {} not exist at user {}", taskName, userName);
        return null;
    }
}
