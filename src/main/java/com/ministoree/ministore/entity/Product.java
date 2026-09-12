package com.ministoree.ministore.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


@Entity 
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    double price; //<-руб
    String name;
    double stockcnt;
    String about;


    public Product(){}
    public int getId(){
        return id;
    }
    public double getPrice(){
        return price;
    }
    public String getName(){
        return name;
    }
    public double getStockcnt(){
        return stockcnt;
    }
    public String getAbout(){
        return about;
    }
    //////////
    public void setId(int id){
        this.id = id;
    }
    public void setPrice(double Price){
        this.price = Price;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setStockcnt(double stockcnt){
        this.stockcnt = stockcnt;
    }
    public void setAbout(String about){
        this.about = about;
    }
}

