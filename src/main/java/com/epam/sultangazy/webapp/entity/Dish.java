package com.epam.sultangazy.webapp.entity;

public class Dish {
    private int id;
    private String name;
    private String categories;
    private String ingredients;
    private int weight;
    private int cost;
    private String image;


    public Dish() {
    }

    public Dish(int id, String name, String ingredients, int weight, int cost, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.weight = weight;
        this.cost = cost;
        this.image = image;
    }

    public Dish(int id, String name, String categories, String ingredients, int weight, int cost, String image) {
        this.id = id;
        this.name = name;
        this.categories = categories;
        this.ingredients = ingredients;
        this.weight = weight;
        this.cost = cost;
        this.image = image;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return Dish.class.toString();
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

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
