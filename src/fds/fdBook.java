/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fds;

/**
 *
 * @author JovenLoba
 */
public class fdBook {
  int book_id =0;
  String book_code = "";
  String book_title = "";
  String book_category = "";
  int book_author_id = 0;
  String book_author_name = "";
  String book_publisher="";
  Boolean availability = true;
  int book_onShelf=0;
  int quantity = 0;
  
  public fdBook(String book_code, String book_title,
          String book_author_name, String book_publisher, Boolean availability, int book_onShelf){
    this.book_code = book_code;
    this.book_title = book_title;
    this.book_author_name = book_author_name;
    this.book_publisher =book_publisher;
    this.availability = availability;
    this.book_onShelf =book_onShelf;
      }
  
  public fdBook (int book_id, String book_code, 
     String book_title, String book_category, 
     int book_author_id,String book_author_name ){
    this.book_id = book_id;
    this.book_code = book_code;
    this.book_title = book_title;
    this.book_category = book_category;
    this.book_author_id = book_author_id;
    this.book_author_name = book_author_name;
  }

  public int getBook_onShelf() {
    return book_onShelf;
  }

  public void setBook_onShelf(int book_onShelf) {
    this.book_onShelf = book_onShelf;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public Boolean getAvailability() {
    return availability;
  }

  public void setAvailability(Boolean availability) {
    this.availability = availability;
  }

  public String getBook_publisher() {
    return book_publisher;
  }

  public void setBook_publisher(String book_publisher) {
    this.book_publisher = book_publisher;
  }

  public int getBook_id() {
    return book_id;
  }

  public void setBook_id(int book_id) {
    this.book_id = book_id;
  }

  public String getBook_code() {
    return book_code;
  }

  public void setBook_code(String book_code) {
    this.book_code = book_code;
  }

  public String getBook_title() {
    return book_title;
  }

  public void setBook_title(String book_title) {
    this.book_title = book_title;
  }

  public String getBook_category() {
    return book_category;
  }

  public void setBook_category(String book_category) {
    this.book_category = book_category;
  }

  public int getBook_author_id() {
    return book_author_id;
  }

  public void setBook_author_id(int book_author_id) {
    this.book_author_id = book_author_id;
  }

  public String getBook_author_name() {
    return book_author_name;
  }

  public void setBook_author_name(String book_author_name) {
    this.book_author_name = book_author_name;
  }
  
    

  
  
  
}
