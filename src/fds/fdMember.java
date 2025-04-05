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
public class fdMember {
  public  int id = 0;
  public  String member_id  = "";
  public  String member_name = "";
  public  String member_mobile = "";
  public  String member_email = "";
    public fdMember( String member_id, String member_name, String member_mobile, String member_email){
    
    this.member_id = member_id;
    this.member_name = member_name;
    this.member_mobile = member_mobile;
    this.member_email = member_email;
    
  }
  public fdMember(int id, String member_id, String member_name, String member_mobile, String member_email){
    this.id =id;
    this.member_id = member_id;
    this.member_name = member_name;
    this.member_mobile = member_mobile;
    this.member_email = member_email;
    
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getMember_id() {
    return member_id;
  }

  public void setMember_id(String member_id) {
    this.member_id = member_id;
  }

  public String getMember_name() {
    return member_name;
  }

  public void setMember_name(String member_name) {
    this.member_name = member_name;
  }

  public String getMember_mobile() {
    return member_mobile;
  }

  public void setMember_mobile(String member_mobile) {
    this.member_mobile = member_mobile;
  }

  public String getMember_email() {
    return member_email;
  }

  public void setMember_email(String member_email) {
    this.member_email = member_email;
  }
  
  
  
  
  
}
