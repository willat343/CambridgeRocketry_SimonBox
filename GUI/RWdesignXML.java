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
public class RWdesignXML extends RWXML{
    Node
            RootNode,
            BodyNode,
            MassNode,
            MotorNode;

    //construnctor function
    public RWdesignXML(String fn){
        super(fn);
        

    }

    public void WriteDesign(RocketDescription RDin){

        WriteInit();

        RootNode = Doc.createElement("RocketDesign");
        BodyNode = RootNode.appendChild(CreateNode("BodyParts"));
        MassNode = RootNode.appendChild(CreateNode("MassParts"));
        MotorNode = RootNode.appendChild(CreateNode("RocketMotor"));
        Vector<RockPartsData> BodyParts = RDin.ReturnBodyParts();
        Vector<RockPartsData> MassParts = RDin.ReturnMassParts();

        for(RockPartsData part : BodyParts){
           part.WriteToXML(this);
        }
        for(RockPartsData part : MassParts){
           part.WriteToXML(this);
        }

        try{
            RocketMotor MotorPart = RDin.ReturnMotorData();
            MotorPart.WriteToXML(this);
        }
        catch(Exception e){
            //put something here to if you need to deal with the no motor situation
        }

        Doc.appendChild(RootNode);
        WriteOutput();
    }

    public RocketDescription ReadDesign(){
        RocketDescription RDnew = new RocketDescription();

        ReadInit();

        RootNode = Doc.getElementsByTagName("RocketDesign").item(0);
        BodyNode = Doc.getElementsByTagName("BodyParts").item(0);
        MassNode = Doc.getElementsByTagName("MassParts").item(0);
        MotorNode = Doc.getElementsByTagName("RocketMotor").item(0);

        NodeList BNodes = BodyNode.getChildNodes();
        NodeList MNodes = MassNode.getChildNodes();
        NodeList MotNodes = MotorNode.getChildNodes();

        for(int i = 0; i < BNodes.getLength(); i++){
            Node Tnde = BNodes.item(i);

            String nName = Tnde.getNodeName();

            if(nName.equals("NoseCone")){
                NoseConeData NC = new NoseConeData(Tnde);
                RDnew.addBodyPart(NC);
            }
            else if(nName.equals("BodyTube")){
                BodyTubeData BT = new BodyTubeData(Tnde);
                RDnew.addBodyPart(BT);
            }
            else if(nName.equals("ConicTrans")){
                ConicTransData CT = new ConicTransData(Tnde);
                RDnew.addBodyPart(CT);
            }
            else if(nName.equals("Finset")){
                FinsetData FS = new FinsetData(Tnde);
                RDnew.addBodyPart(FS);
            }
            else{
                throw(new Error("The name of the body part listed in the rocket design file is not recognised"));
            }

        }

        for(int i=0; i< MNodes.getLength(); i++){
            Node Tnde = MNodes.item(i);

            String nName = Tnde.getNodeName();


            if(nName.equals("Tube")){
                TubeData TD = new TubeData(Tnde);
                RDnew.addMassPart(TD);
            }
            else if(nName.equals("Cylinder")){
                CylinderData Cy = new CylinderData(Tnde);
                RDnew.addMassPart(Cy);
            }
            else if(nName.equals("Block")){
                BlockData Bl = new BlockData(Tnde);
                RDnew.addMassPart(Bl);
            }
            else if(nName.equals("ConeSec")){
                ConeSecData CS = new ConeSecData(Tnde);
                RDnew.addMassPart(CS);
            }
            else if(nName.equals("PointMass")){
                PointMassData PM = new PointMassData(Tnde);
                RDnew.addMassPart(PM);
            }
            else if(nName.equals("Parachute")){
                ParachuteData Pc = new ParachuteData(Tnde);
                RDnew.addMassPart(Pc);
            }
            else{
                throw(new Error("The name of the mass part listed in the rocket design file is not recognised"));
            }

            
        }

        for(int i=0; i<MotNodes.getLength(); i++){
            Node Tnde = MotNodes.item(i);
            RocketMotor rMot = new RocketMotor(Tnde);
            RDnew.addMotor(rMot);
        }


        return(RDnew);
    }



}
