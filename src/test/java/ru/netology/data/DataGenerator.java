package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;

import java.util.Locale;

@UtilityClass
public class DataGenerator {

    private static final Faker faker = new Faker(new Locale("en"));

    public static String generateLogin() {
        return faker.name().username();
    }

    public static String generatePassword() {
        return faker.internet().password();
    }

    public static RegistrationDto generateActiveUser() {
        return RegistrationDto.builder()
                .login(generateLogin())
                .password(generatePassword())
                .status("active")
                .build();
    }

    public static RegistrationDto generateBlockedUser() {
        return RegistrationDto.builder()
                .login(generateLogin())
                .password(generatePassword())
                .status("blocked")
                .build();
    }
}