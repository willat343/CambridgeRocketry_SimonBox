/*
%## Copyright (C) 2011 S.Box
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

%## ProfilePreLoader.java

%## Author: S.Box
%## Created: 2011-10-27
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Rocket;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.URLDecoder;

/**
 *
 * @author sb4p07
 */
public class ProfilePreLoader {

    File InstallationDir,
            DataDir,
            AtmosDir,
            MotorDir,
            DesignDir;

    public ProfilePreLoader(){
        String path = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        String decodedPath ="";
        try {
            decodedPath = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        InstallationDir = new File(decodedPath);
        InstallationDir = InstallationDir.getParentFile().getParentFile();

        try{
            DataDir = new File(InstallationDir.getPath() + File.separator +"data");
            AtmosDir = new File(DataDir.getPath() + File.separator +"Atmospheres");
            MotorDir = new File(DataDir.getPath() + File.separator + "Motors");
            DesignDir = new File(DataDir.getPath() + File.separator + "Designs");
        }
        catch(Exception e){
            //throw(new Error("Could not determine the struncture  of the profile data files. System Message:" + e.getMessage()));
        }
    }

    public AtmosphereList getAmospheres(){
        AtmosphereList Atmospheres = new AtmosphereList();

        try{
            File[] Fnames = AtmosDir.listFiles();

            for(File fn:Fnames){
                try{
                    RWatmosXML atmosdata = new RWatmosXML(fn.getAbsolutePath());
                    atmosdata.ReadXMLtoAtmos();
                    Atmospheres.AddtoList(atmosdata.Atmosdat);
                }
                catch(Exception e){
                    //do nothing
                }
                }
            return(Atmospheres);
        }
        catch(Exception e){
            return(Atmospheres);
        }
    }

    public MotorsList getMotors(){
        
        MotorsList Motors = new MotorsList();
        try{
            File[] Fnames = MotorDir.listFiles();

            for(File fn:Fnames){
                try{
                    RWmotorXML motordata = new RWmotorXML(fn.getAbsolutePath());
                    motordata.ReadXMLtoMotor();
                    Motors.AddtoList(motordata.MotDat);
                }
                catch(Exception e){
                    //do nothing
                }
                }
            return(Motors);
        }
        catch(Exception e){
            return(Motors);
        }
    }



}
