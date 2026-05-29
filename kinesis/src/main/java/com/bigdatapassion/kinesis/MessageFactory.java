package com.bigdatapassion.kinesis;

import com.github.javafaker.Faker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessageFactory {

    private Faker faker = new Faker();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public Message generateNextMessage(int number) {

        Message person = new Message();
        person.setPartitionkey(String.format("partitionkey-%d", number));
        person.setCurrentdate(LocalDateTime.now().format(formatter));

        person.setName(faker.name().fullName());
        person.setPhonenumber(faker.phoneNumber().phoneNumber());

        person.setCountry(faker.address().country());
        person.setCity(faker.address().city());
        person.setStreetname(faker.address().streetName());
        person.setNumber(faker.address().buildingNumber());

        person.setAnimal(faker.animal().name());

        return person;
    }

}
