package com.mycompany.csc311hw3;

import com.google.gson.annotations.SerializedName;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Nick Gutierrez
 */
public class Book {
   
    @SerializedName("Id")
    public int Id;
    @SerializedName("Title")
    public String Title;
    @SerializedName("Price")
    public double Price;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }
    public Book(int Id, String Title, double Price){
        this.Id = Id;
        this.Title = Title;
        this.Price = Price;
    }
    
    @Override
    public String toString(){
        return this.Id + ", " + this.Title + ", " + this.Price;
    }
}


