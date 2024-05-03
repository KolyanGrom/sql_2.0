package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.Locale;

public class DataHelper {
    private static final Faker FAKER = new Faker(new Locale("en"));

    private DataHelper() {
    }

    public static UserData getUserDataWinTestData() {
        return new UserData("vasya", "qwerty123");
    }


    @Value
    public static class UserData {
        private final String login;
        private final String password;
    }


    public static String getRandomLogin() {
        return FAKER.name().username();
    }

    public static String getRandomPassword() {
        return FAKER.internet().password();
    }

    public static UserData generateRandomUser() {
        return new UserData(getRandomLogin(), getRandomPassword());
    }

    public static VerificationCode generateRandomVerificationCode() {
        return new VerificationCode(FAKER.numerify("######"));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VerificationCode {
        String code;
    }
}