/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fds;

import javafx.scene.control.Button;

/**
 *
 * @author JovenLoba
 */
public class fdInvoiceTemp {
  public int id =0;
  public String invdate = "";
  public String itemno = "";
  public String description = "";
  public String brand_code="";
  public String brand ="";
  public String category_code = "";
  public String category = "";
  public int quantity =0;
  public double price = 0.0;
  public double amount = 0.0;
  public Button del_button;
  
  

  public fdInvoiceTemp(
      int id,  String invdate, String itemno, 
     String description, String brand_code, String category_code,
      int quantity, double price, double amount, Button del_button
  ){
    this.id = id;    
    this.invdate = invdate;
    this.itemno = itemno;  
    
    this.description = description;
    this.brand_code = brand_code;
    this.category_code= category_code;  
    this.quantity = quantity; 
    this.price = price; 
    this.amount = amount;
    this.del_button = del_button;
    
  }
  
    public fdInvoiceTemp(
    String invdate, String itemno, String description, 
          String brand_code, String category_code,
      int quantity, double price, double amount
  ){
    
    this.invdate = invdate;
    this.itemno = itemno;  
    
    this.description = description;
    this.brand_code = brand_code;
    this.category_code= category_code;  
    this.quantity = quantity; 
    this.price = price; 
    this.amount = amount;
    
  }

  public Button getDel_button() {
    return del_button;
  }

  public void setDel_button(Button del_button) {
    this.del_button = del_button;
  }
  

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getInvdate() {
    return invdate;
  }

  public void setInvdate(String invdate) {
    this.invdate = invdate;
  }

  public String getItemno() {
    return itemno;
  }

  public void setItemno(String itemno) {
    this.itemno = itemno;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getBrand_code() {
    return brand_code;
  }

  public void setBrand_code(String brand_code) {
    this.brand_code = brand_code;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getCategory_code() {
    return category_code;
  }

  public void setCategory_code(String category_code) {
    this.category_code = category_code;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  
  
  
  
  
}
