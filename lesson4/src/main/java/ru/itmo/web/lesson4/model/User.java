package ru.itmo.web.lesson4.model;

public class User {
    private final long id;
    private final String handle;
    private final String name;
    private final User.UserColors color;

    public User(long id, String handle, String name, User.UserColors color) {
        this.id = id;
        this.handle = handle;
        this.name = name;
        this.color = color;
    }

    public long getId() {
        return id;
    }

    public String getHandle() {
        return handle;
    }

    public String getName() {
        return name;
    }

    public UserColors getColor() {
        return color;
    }

    public enum UserColors {
        RED, GREEN, BLUE
    }
}
