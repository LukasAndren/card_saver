package com.card_saver.model;

public class Card {

    private int id;
    private String name;
    private String set;
    private String grade;
    private String altered;
    private String manaCost;
    private String type;
    private String description;
    private int userId;

    public Card() {
    }

    public Card(int id, String name, String set, String grade, String altered, String manaCost, String type, String description, int userId) {
        this.id = id;
        this.name = name;
        this.set = set;
        this.grade = grade;
        this.altered = altered;
        this.manaCost = manaCost;
        this.type = type;
        this.description = description;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getAltered() {
        return altered;
    }

    public void setAltered(String altered) {
        this.altered = altered;
    }

    public String getManaCost() {
        return manaCost;
    }

    public void setManaCost(String manaCost) {
        this.manaCost = manaCost;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUserId() { return userId; }

    public void setUserId(int userId) { this.userId = userId; }
}
