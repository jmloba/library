
package Settings;

import static AlertMaker.AlertMaker.showSimpleAlert;
import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import org.apache.commons.codec.digest.DigestUtils;

public class Preference {
  
  public static final String CONFIG_file ="config.txt";
  
  int ndaysWOFine;
  float finePerDay;
  String username ;
  String password;
  public Preference(){
    ndaysWOFine =14;
    finePerDay = 50 ; 
    username = "admin";
    setPassword("admin");
    
  }

  public int getNdaysWOFine() {
    return ndaysWOFine;
  }

  public void setNdaysWOFine(int ndaysWOFine) {
    this.ndaysWOFine = ndaysWOFine;
  }

  public float getFinePerDay() {
    return finePerDay;
  }

  public void setFinePerDay(float finePerDay) {
    this.finePerDay = finePerDay;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public  void setPassword(String password) {
    if (password.length()<16){
       this.password = DigestUtils.shaHex(password);
    }else{
    this.password = password;
    }
    
    
    
  }
  public static void initConfig(){
    Writer writer = null;
    try {
      Preference preference = new Preference();    
      Gson gson = new Gson();
      writer = new FileWriter( CONFIG_file);
      gson.toJson(preference,writer);
      
    } catch (IOException ex) {
      Logger.getLogger(Preference.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      try {
        writer.close();
      } catch (IOException ex) {

        Logger.getLogger(Preference.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }
  public static Preference getpreference(){
    
    Gson gson = new Gson();
    Preference pref = new Preference();
    try {
      pref = gson.fromJson(new FileReader(CONFIG_file),Preference.class );
    } catch (FileNotFoundException ex) {
      initConfig();
      Logger.getLogger(Preference.class.getName()).log(Level.SEVERE, null, ex);
    }
      System.out.println("get");
      

      
    return pref;
  }
  public static void writePreferenceToFile(Preference preference){
    Writer writer = null;
    try {
      
      Gson gson = new Gson();
      writer = new FileWriter( CONFIG_file);
      gson.toJson(preference,writer);
      AlertMaker.AlertMaker.showSimpleAlert("Success","saved");
      
      
    } catch (IOException ex) {
      Logger.getLogger(Preference.class.getName()).log(Level.SEVERE, null, ex);
      AlertMaker.AlertMaker.showErrorMessage(ex,"Failed","Cant Save Configuration File");
    } finally {
      try {
        writer.close();
      } catch (IOException ex) {

        Logger.getLogger(Preference.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }
  
}
