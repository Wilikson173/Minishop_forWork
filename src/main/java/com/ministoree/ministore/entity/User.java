package com.ministoree.ministore.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uid;
    private String login;
    private String password;
    private String name;
    private String email;
    private double balance;
    private boolean isAdmin;

    public User(){}
    ///
    public int getUid(){
        return uid;
    }
    public String getPassword(){
        return password;
    }
    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
    public String getLogin(){
        return login;
    }
    public double getBalance(){
        return balance;
    }
    public boolean isAdmin(){
        return isAdmin;
    }
    ///
    public void setUid(int uid){
        this.uid = uid;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setLogin(String login){
        this.login = login;
    }
    public void setBalance(double balance){
        this.balance = balance;
    }
    public void setAdmin(boolean isAdmin){
        this.isAdmin = isAdmin;
    }
}
