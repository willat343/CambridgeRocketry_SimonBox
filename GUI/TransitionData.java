/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Rocket;
import org.w3c.dom.*;

/**
 *
 * @author simon
 */
public class TransitionData extends RockPartsData{
    //Members
    public double separationTime;
    public double ignitionDelay;
    boolean built;

    public TransitionData(){
    }
    public TransitionData(Node Nin){
        BuildFromXML(Nin);
    }

    public void PopulateTransition(double d1,double d2){
        separationTime = d1;
        ignitionDelay = d2;
        built = true;
    }

    @Override
    public String toString()
    {
        return("Stage Transition");
    }

    public void EditMe(){
        TransitionDialog TD = new TransitionDialog(null,true);
        if (built == true){
               TD.FillFields(separationTime,ignitionDelay,"Stage Transition");
        }
        TD.setVisible(true);
        if(TD.ReadOk == true){
            PopulateTransition(TD.separationTime,TD.ignitionDelay);
        }
    }



    public void WriteToXML(RWdesignXML design){
        Node PartNode = design.CreateNode("TransitionData");
        PartNode.appendChild(design.CreateDataNode("separationTime", Double.toString(separationTime)));
        PartNode.appendChild(design.CreateDataNode("ignitionDelay", Double.toString(ignitionDelay)));
        design.TransitionNode.appendChild(PartNode);
    }

    public void BuildFromXML(Node Nin){
        XMLnodeParser Temp = new XMLnodeParser(Nin);

       PopulateTransition(Temp.DbyName("separationTime"),Temp.DbyName("ignitionDelay"));
       built=true;
    }


}
