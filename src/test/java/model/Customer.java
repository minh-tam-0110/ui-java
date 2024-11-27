package model;

import java.util.Objects;

public class Customer {
    private String name;
    private int age;
    private String address;
    private String tags;

    public Customer() {

    }

    public Customer(String name, int age, String address, String tags) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return age == customer.age && Objects.equals(name, customer.name) && Objects.equals(address, customer.address) && Objects.equals(tags, customer.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, address, tags);
    }
}
