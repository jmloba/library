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
  String  bookid = "";
  String memberid= "";
  String membername ="";
  String issued_date ="";
  Button btn_del;
  public fdBookIssue(String bookid, String memberid  ){
    this.bookid=bookid;
    this.memberid = memberid;
  };

  public fdBookIssue(int id ,String memberid, String membername, 
          String issued_date, Button  btn_del){
    this.id = id;
    this.memberid=memberid;
    this.membername = membername;
    this.issued_date = issued_date;
    this.btn_del = btn_del;
  };

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

  
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getBookid() {
    return bookid;
  }

  public void setBookid(String bookid) {
    this.bookid = bookid;
  }

  public String getMemberid() {
    return memberid;
  }

  public void setMemberid(String memberid) {
    this.memberid = memberid;
  }
  
  
}
