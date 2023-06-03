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
    final int idLength = 12;

    @Autowired
    private DatabaseService databaseService;

    public <T> String generateNew(Class<T> tClass) {
        String id;
        do {
            id = RandomStringUtils.random(idLength, ID_CHARACTERS);
        } while (databaseService.getById(tClass, id) != null);

        return id;
    }
}