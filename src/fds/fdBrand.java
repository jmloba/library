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
public class fdBrand {
  int id = 0;
  String  brandcode =  "";
  String  brandname =  "";
  
    public fdBrand( String brandcode, String brandname){
    this.brandcode = brandcode;
    this.brandname = brandname;
  }
  public fdBrand(int id, String brandcode, String brandname){
    this.id = id;
    this.brandcode = brandcode;
    this.brandname = brandname;
    
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getBrandcode() {
    return brandcode;
  }

  public void setBrandcode(String brandcode) {
    this.brandcode = brandcode;
  }

  public String getBrandname() {
    return brandname;
  }

  public void setBrandname(String brandname) {
    this.brandname = brandname;
  }
  
  
}
