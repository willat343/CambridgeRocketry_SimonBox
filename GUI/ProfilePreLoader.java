/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Rocket;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FilenameFilter;
import java.util.Vector;

/**
 *
 * @author sb4p07
 */
public class ProfilePreLoader {

    File DataDir,
            AtmosDir,
            MotorDir,
            DesignDir;

    public ProfilePreLoader(File InstDir){
        try{
            DataDir = new File(InstDir.getPath() + File.separator +"data");
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
