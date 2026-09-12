package com.ministoree.ministore.entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int oid;
    private boolean isPayed;
    
    @ManyToOne
    private User user;
    @ManyToOne
    private Product product;
    
    public Order(){}
    
    public boolean isPayed(){
        return isPayed;
    }
    public int getOid(){
        return oid;
    }
    public User getUser(){
        return user;
    }
    public Product getProduct(){
        return product;
    }
    
    /// 
    public void setOid(int oid){
        this.oid = oid;
    }
    public void setPayed(boolean isPayed){
        this.isPayed = isPayed;
    }
    public void setUser(User user){
        this.user = user;
    }
    public void setProduct(Product product){
        this.product = product;
    }
}
