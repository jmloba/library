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
public class fdCategory {
    int id = 0;
  String  code =  "";
  String  desc =  "";
  
    public fdCategory(int id, String code, String desc){
    this.id = id;
    this.code = code;
    this.desc = desc;
    
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }
    
    
}
