package com.bigdatapassion.kinesis;

import com.github.javafaker.Faker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessageFactory {

    private Faker faker = new Faker();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public Message generateNextMessage(int number) {

        Message person = new Message();
        person.setPartitionKey(String.format("partitionKey-%d", number));
        person.setCurrentDate(LocalDateTime.now().format(formatter));

        person.setName(faker.name().fullName());
        person.setPhoneNumber(faker.phoneNumber().phoneNumber());

        person.setCity(faker.address().city());
        person.setCountry(faker.address().country());
        person.setNumber(faker.address().buildingNumber());
        person.setStreetName(faker.address().streetName());

        person.setAnimal(faker.animal().name());

        return person;
    }

}
