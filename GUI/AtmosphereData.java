/*
%## Copyright (C) 2010 S.Box
%## 
%## This program is free software; you can redistribute it and/or modify
%## it under the terms of the GNU General Public License as published by
%## the Free Software Foundation; either version 2 of the License, or
%## (at your option) any later version.
%## 
%## This program is distributed in the hope that it will be useful,
%## but WITHOUT ANY WARRANTY; without even the implied warranty of
%## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
%## GNU General Public License for more details.
%## 
%## You should have received a copy of the GNU General Public License
%## along with this program; if not, write to the Free Software
%## Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA

%## AtmosphereData.java

%## Author: S.Box
%## Created: 2010-05-27
*/
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Rocket;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author sb4p07
 */
public class AtmosphereData {
    //*class members
    public String name;
    public Vector<Double>
            Altitude,
            Xwind,
            Ywind,
            Zwind,
            rho,
            Theta;
    boolean built;
    public boolean AddToList;
    
    //*class constuctor
    public AtmosphereData()
    {
        name = "Not Initialized";
        built = false;
        AddToList=false;
    }
    
    public void EditMeRaw()
    {
        RawAtmosphereDialog RAD = new  RawAtmosphereDialog(null,true);
        if(built == true)
        {
            RAD.FillFields(Altitude,Xwind,Ywind,Zwind,rho,Theta,name);
        }
        RAD.setVisible(true);
        if(RAD.ReadOk == true)
        {
            AddToList = true;
            name = RAD.name;
            Altitude = RAD.Altitude;
            Xwind = RAD.Xwind;
            Ywind = RAD.Ywind;
            Zwind = RAD.Zwind;
            rho = RAD.density;
            Theta = RAD.Temperature;
            built = true;

            try{
                RWatmosXML Wxml = new RWatmosXML(RAD.FileName);
                Wxml.WriteAtmosToXML(this);
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null,("Saving " + RAD.FileName +" failed. \nSystem msg: " + e));
            }

        }
        
    }

     @Override
    public String toString(){
        return(name);
    }


}
