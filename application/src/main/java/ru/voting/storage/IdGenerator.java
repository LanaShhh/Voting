package ru.voting.storage;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IdGenerator {
    final String ID_CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                    "abcdefghijklmnopqrstuvwxyz" +
                    "0123456789";
    @Autowired
    private DatabaseService databaseService;

    public <T> String generateNew(Class<T> tClass) {
        String id = RandomStringUtils.random(12, ID_CHARACTERS);
        while (databaseService.getById(tClass, id) != null) {

@Component
public class IdGenerator {
    @Autowired
    private DatabaseService database;
    final String ID_CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "abcdefghijklmnopqrstuvwxyz" +
                    "0123456789";

    public <T> String generateNew(Class<T> tClass) {
        String id = RandomStringUtils.random(12, ID_CHARACTERS);
        while (database.getById(tClass, id) != null) {
>>>>>>> d9eacfe (Add entities and db service, start to fix methods)
            id = RandomStringUtils.random(12, ID_CHARACTERS);
        }

        return id;
    }
}
