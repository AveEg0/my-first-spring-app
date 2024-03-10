package org.lesson.models;

import jakarta.validation.constraints.*;

import java.util.Objects;


public class Person {
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should have between 2 and 30 characters")
    private String name;

    @Min(value = 1, message = "Age should not be less than 1")
    @Max(value = 150, message = "Age should not be greater than 150")
    private int age;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;

    //Country, City, post index (6 numbers)
    @NotEmpty( message = "Address should not be empty")
    @Pattern(regexp = "[A-Z]+[a-z]*, [A-Z]+[a-z]*, \\d{6}", message = "Address should be valid")
    private String address;

    public Person() {
    }

    public Person(int id, String name, int age, String email, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && age == person.age && Objects.equals(name, person.name) && Objects.equals(email, person.email) && Objects.equals(address, person.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, email, address);
    }
}
