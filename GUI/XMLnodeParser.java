/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Rocket;
import org.w3c.dom.*;
import java.util.Vector;

/**
 *
 * @author simon
 */
public class XMLnodeParser {
    Node MainNode;
    Element MainElement;

    public XMLnodeParser(Node Nin){
        MainNode = Nin;
        MainElement = (Element)MainNode;
    }

    //function to return double under MainElement given a name
    protected double DbyName(String name)
    {
        return(Double.valueOf(MainElement.getElementsByTagName(name).item(0).getTextContent()));
    }

    //function to returnstring under MainElement given a name
    protected String SbyName(String name)
    {
        return(MainElement.getElementsByTagName(name).item(0).getTextContent());
    }

    //function to return int under MainElement by given name
    protected int IbyName(String name)
    {
        return(Integer.valueOf(MainElement.getElementsByTagName(name).item(0).getTextContent()));
    }

    //function to return int under MainElement by given name
    protected boolean BbyName(String name)
    {
        return(Boolean.parseBoolean(MainElement.getElementsByTagName(name).item(0).getTextContent()));
    }
    //function to return Vector<double> under Doc given a name
    protected Vector<Double> VDbyName(String name)
    {
        return(DstringToVector(MainElement.getElementsByTagName(name).item(0).getTextContent()));
    }
    protected Vector<Double> DstringToVector(String Dstring)
    {
        String[] SplitString = Dstring.split(",");
        Vector<Double> Temp = new Vector<Double>();

        for(String S : SplitString)
        {
            Temp.add(Double.valueOf(S));
        }
        return(Temp);
    }

}
