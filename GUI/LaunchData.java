/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Rocket;
import javax.swing.JTextField;

/**
 *
 * @author simon
 */
public class LaunchData {
    //*Class Members
    public double RailLength,
            Azimuth,
            Declination;
    public boolean Built;

    public LaunchData(){}

    public void SetData(JTextField F1,JTextField F2,JTextField F3){
        
        TestUserTextInput TU1 = new TestUserTextInput(F1);
        RailLength = TU1.TestDouble();
        TestUserTextInput TU2 = new TestUserTextInput(F2);
        Azimuth = TU2.TestDouble();
        TestUserTextInput TU3 = new TestUserTextInput(F3);
        Declination = TU3.TestDouble();
        
        if(TU1.Valid && TU2.Valid && TU3.Valid)
        {
            Built = true;
        }
        else{
            Built = false;
        }
    }

}
