/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Rocket;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author sb4p07
 */
public class TimeStamper {
    Date TheDate;
    SimpleDateFormat parser;

    public TimeStamper(){
        parser = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    }

    String DateTimeNow()
    {
        return(parser.format(new Date()));
    }

}
