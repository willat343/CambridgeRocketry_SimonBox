/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Rocket;
import java.io.*;
import javax.swing.JOptionPane;

/**
 *
 * @author simon
 */
public class PlotLauncher {
    //class memberts
    public File FilePath;
    private File DataFile;

    //class constructor
    public PlotLauncher(String path){
        FilePath = new File(path);
    }

    public void MakePlots(File dF){
        DataFile = dF;
        try{
            Thread T = new Thread(new LaunchFigures());
            T.start();

        }
        catch (Exception e){

        }
    }

    private class LaunchFigures implements Runnable{

        public void run() {
            String OutputRecord = "";
            try{
                Runtime rtime = Runtime.getRuntime();
                String[] Command = new String[]{"python", FilePath.getPath(), "-f", DataFile.getPath()};
                Process Pr2 = rtime.exec(Command);
                BufferedReader errors2 = new BufferedReader(new InputStreamReader(Pr2.getErrorStream()));
                String line = null;
                while((line = errors2.readLine()) != null) {
                        OutputRecord += line;

                    }
            }
            catch(Exception e){
                 JOptionPane.showMessageDialog(null,("Figure generation failed\nJava Message: " + e.getMessage() + "\nPython Message: " + OutputRecord));
            }
        }

    }

}
