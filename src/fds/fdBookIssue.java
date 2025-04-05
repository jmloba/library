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
public class fdBookIssue {
  int id = 0;
  String  bookcode = "";
  String bookname = "";
  String membercode= "";
  String membername ="";
  String issued_date ="";
  Button btn_del;
  public fdBookIssue(String bookcode, String membercode  ){
    this.bookcode=bookcode;
    this.membercode = membercode;
  };
  public fdBookIssue(int id ,String issued_date,
          String membercode,  String membername, 
          String bookcode,  
          Button  btn_del){
     this.id = id;
    this.issued_date = issued_date;
    
    this.membercode=membercode;
    this.membername = membername;
    
    this.bookcode = bookcode;
    
    
    this.btn_del = btn_del;
  }
  

  public fdBookIssue(int id ,String issued_date,
          String membercode,  String membername, 
          String bookcode,  String bookname,  
          Button  btn_del){
    
    this.id = id;
    this.issued_date = issued_date;
    
    this.membercode=membercode;
    this.membername = membername;
    
    this.bookcode = bookcode;
    this.bookname =bookname;
    
    this.btn_del = btn_del;
  };

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getBookcode() {
    return bookcode;
  }

  public void setBookcode(String bookcode) {
    this.bookcode = bookcode;
  }

  public String getBookname() {
    return bookname;
  }

  public void setBookname(String bookname) {
    this.bookname = bookname;
  }

  public String getMembercode() {
    return membercode;
  }

  public void setMembercode(String membercode) {
    this.membercode = membercode;
  }

  public String getMembername() {
    return membername;
  }

  public void setMembername(String membername) {
    this.membername = membername;
  }

  public String getIssued_date() {
    return issued_date;
  }

  public void setIssued_date(String issued_date) {
    this.issued_date = issued_date;
  }

  public Button getBtn_del() {
    return btn_del;
  }

  public void setBtn_del(Button btn_del) {
    this.btn_del = btn_del;
  }

  
  
}
