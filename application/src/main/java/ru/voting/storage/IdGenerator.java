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
            id = RandomStringUtils.random(12, ID_CHARACTERS);
        }

        return id;
    }
}
