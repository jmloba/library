/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generalRoutines;

import database.Common_Var;
import database.DatabaseHandler;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.TilePane;
import listbook.BookListController;


/**
 *
 * @author JLoba
 */
public class myfunction {
  static DatabaseHandler  handler;
  Connection connew = Common_Var.connew ;
  
  
  public static String convertDateToString(String date)
    {
        // Converts the string
        // format to date object
        DateFormat df = new SimpleDateFormat(date);
  
        // Get the date using calendar object
         java.util.Date today = Calendar.getInstance().getTime();
  
        // Convert the date into a
        // string using format() method
        String dateToString = df.format(today);
  
        // Return the result
        return (dateToString);
    }
  public static boolean toggleProgramName() {
      boolean  mvalue = Common_Var.showProgramName;

        if (mvalue) {
            Common_Var.showProgramName = false;
            mvalue = Common_Var.showProgramName;

        } else {
            Common_Var.showProgramName = true;
            mvalue = Common_Var.showProgramName;
        };
        return mvalue;
    }
  public static String changedateformat(String datefrom, String format1, String format2) {
        java.util.Date initDate1;
        String parsedDate1 = null;
        DateFormat sdf = new SimpleDateFormat(format1);
        sdf.setLenient(false);

        boolean proceed = true;
        try {
            sdf.parse(datefrom);
        } catch (ParseException e) {
            proceed = false;
        }

        if (proceed) {

            try {
                initDate1 = new SimpleDateFormat(format1).parse(datefrom);
//                        System.out.println("initDate1 :"+ initDate1);
                SimpleDateFormat formatter = new SimpleDateFormat(format2);
                parsedDate1 = formatter.format(initDate1);
            } catch (ParseException ex) {
                Logger.getLogger(myfunction.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            parsedDate1 = null;
        }

        return parsedDate1;

    }
  public static String formatSign(double number) {
          if (number > 0.0){
              return "+"+ number;
          } else if (number < 0) {
            return "-"+ number;
          } else {
            return String.valueOf(number);
          }   
      }
  public static void alert_now (String Alert_String, String Alert_Message) {
    
   // create a tile pane
		TilePane r = new TilePane();

		// create a alert
		Alert a = new Alert(Alert.AlertType.NONE);

		// action event
                if (Alert_String == "Warning alert") {
		
				// set alert type
				a.setAlertType(Alert.AlertType.WARNING);

				// set content text
				a.setContentText(Alert_Message);

				// show the dialog
				a.show();
			
                } else    
                if (Alert_String == "Record Saved") {
		
				// set alert type
				a.setAlertType(Alert.AlertType.INFORMATION);

				// set content text
				a.setContentText(Alert_Message);

				// show the dialog
				a.show();
			
                }
        
      }
  public static void numericOnly(final TextField tf) {

      tf.textProperty().addListener(new ChangeListener<String>() {
    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, 
        String newValue) {
        if (!newValue.matches("\\d*")) {
            tf.setText(newValue.replaceAll("[^\\d]", ""));
        }
    }
});
        };
  public static void numbersOnly(final TextField tf) {

        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*(\\.\\d*)?")) {
                    tf.setText(oldValue);
                }
            }
        });


/*
// force the field to be numeric only
tf.textProperty().addListener(new ChangeListener<String>() {
    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, 
        String newValue) {
        if (!newValue.matches("\\d*")) {
            tf.setText(newValue.replaceAll("[^\\d]", ""));
        }
    }
});
*/

    }
  public static void decimalNumberFormat(final TextField tf) {

        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*(\\.\\d*)?")) {
                    tf.setText(oldValue);
                }
            }
        });


    }    
  public static void numbersOnlyNoDecimal(final TextField tf) {

        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*(\\*)?")) {
                    tf.setText(oldValue);
                }
            }
        });



    }
  public static void numbersOnly1(final TextField tf) {

        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("-\\d*(\\.\\d*)?")) {
                    tf.setText(oldValue);
                }
            }
        });

    }
  public static int SubtractDates(String Datefrom, String Dateto) {

        LocalDate d = LocalDate.parse(Datefrom);

        int numberofDays = 0;
        java.util.Date initDate1;
        System.out.println("subtact dates function");
        System.out.println("before-datefrom : " + Datefrom);
        System.out.println("before-dateto : " + Dateto);

        String parsedDate1 = null;
        DateFormat sdf = new SimpleDateFormat(Datefrom);
        sdf.setLenient(false);
        boolean proceed = true;
        try {
            sdf.parse(Datefrom);
        } catch (ParseException e) {
            System.out.println(" ERROR DATE 1  :" + Datefrom);
            proceed = false;
        }

        DateFormat sdf2 = new SimpleDateFormat(Dateto);
        sdf2.setLenient(false);

        try {
            sdf2.parse(Dateto);
        } catch (ParseException e) {

            System.out.println(" ERROR DATE 2  :" + Dateto);
            proceed = false;
        }

        if (proceed) {
            System.out.println("PARSING OK ");
            System.out.println("datefrom  " + Datefrom);
            System.out.println("dateto  " + Dateto);

        } else {

            System.out.println("not proceed with error ");
            return 0;

        }

        return numberofDays;
    }
  public static boolean ValidDate(String passedDate, String mformat) {
        boolean valid_return = false;
        return valid_return;

    }
  public String leftpad(String text, int length) {
        return String.format("%" + length + "." + length + "s", text);
    }
  public static String rightpad(String text, int length) {
        return String.format("%-" + length + "." + length + "s", text);
    }
  public static Date addDays(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        return new Date(c.getTimeInMillis());
    }
  public static Date subtractDays(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, -days);
        return new Date(c.getTimeInMillis());
    }
  public static void RestrictNumbersOnly(final TextField tf){
    tf.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
        if (!newValue.matches("|[-\\+]?|[-\\+]?\\d+\\.?|[-\\+]?\\d+\\.?\\d+")){
            tf.setText(oldValue);
        }
    });
}
  
}
