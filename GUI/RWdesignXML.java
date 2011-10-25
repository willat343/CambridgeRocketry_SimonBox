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
            MotorNode,
            TransitionNode;

    //construnctor function
    public RWdesignXML(String fn){
        super(fn);
        

    }

    public void WriteDesign(RocketDescription RDin){

        WriteInit();

        RootNode = Doc.createElement("RocketDesign");

        for(StageDescription stage : RDin.Stages){
            Node StageNode = RootNode.appendChild(CreateNode("Stage"));
            StageNode.appendChild(CreateDataNode("Name",stage.toString()));
            BodyNode = StageNode.appendChild(CreateNode("BodyParts"));
            MassNode = StageNode.appendChild(CreateNode("MassParts"));
            MotorNode = StageNode.appendChild(CreateNode("RocketMotor"));
            TransitionNode = StageNode.appendChild(CreateNode("Transition"));
            Vector<RockPartsData> BodyParts = stage.ReturnBodyParts();
            Vector<RockPartsData> MassParts = stage.ReturnMassParts();

            for(RockPartsData part : BodyParts){
               part.WriteToXML(this);
            }
            for(RockPartsData part : MassParts){
               part.WriteToXML(this);
            }

            if(stage.hasMotor()){
                RocketMotor MotorPart = stage.ReturnMotorData();
                MotorPart.WriteToXML(this);     
            }
            if(stage.isUpper()){
                TransitionData Tdat = stage.ReturnTransitionData();
                Tdat.WriteToXML(this);
            }

        }

        Doc.appendChild(RootNode);
        WriteOutput();
    }

    public RocketDescription ReadDesign(){
        RocketDescription RDnew = new RocketDescription();

        ReadInit();

        RootNode = Doc.getElementsByTagName("RocketDesign").item(0);

        NodeList StageList = Doc.getElementsByTagName("Stage");

        for(int j = 0; j<StageList.getLength(); j++){
            if(j!=0){RDnew.addNewStage();}

            Element stage = (Element)StageList.item(j);
            NodeList BMM = stage.getChildNodes();

            BodyNode = stage.getElementsByTagName("BodyParts").item(0);
            MassNode = stage.getElementsByTagName("MassParts").item(0);
            MotorNode = stage.getElementsByTagName("RocketMotor").item(0);
            TransitionNode = stage.getElementsByTagName("Transition").item(0);

            NodeList BNodes = BodyNode.getChildNodes();
            NodeList MNodes = MassNode.getChildNodes();
            NodeList MotNodes = MotorNode.getChildNodes();
            NodeList TranNodes = TransitionNode.getChildNodes();

            for(int i = 0; i < BNodes.getLength(); i++){
                Node Tnde = BNodes.item(i);

                String nName = Tnde.getNodeName();

                if(nName.equals("NoseCone")){
                    NoseConeData NC = new NoseConeData(Tnde);
                    RDnew.addBodyPart(NC, j);
                }
                else if(nName.equals("BodyTube")){
                    BodyTubeData BT = new BodyTubeData(Tnde);
                    RDnew.addBodyPart(BT, j);
                }
                else if(nName.equals("ConicTrans")){
                    ConicTransData CT = new ConicTransData(Tnde);
                    RDnew.addBodyPart(CT, j);
                }
                else if(nName.equals("Finset")){
                    FinsetData FS = new FinsetData(Tnde);
                    RDnew.addBodyPart(FS, j);
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
                    RDnew.addMassPart(TD, j);
                }
                else if(nName.equals("Cylinder")){
                    CylinderData Cy = new CylinderData(Tnde);
                    RDnew.addMassPart(Cy, j);
                }
                else if(nName.equals("Block")){
                    BlockData Bl = new BlockData(Tnde);
                    RDnew.addMassPart(Bl, j);
                }
                else if(nName.equals("ConeSec")){
                    ConeSecData CS = new ConeSecData(Tnde);
                    RDnew.addMassPart(CS, j);
                }
                else if(nName.equals("PointMass")){
                    PointMassData PM = new PointMassData(Tnde);
                    RDnew.addMassPart(PM, j);
                }
                else if(nName.equals("Parachute")){
                    ParachuteData Pc = new ParachuteData(Tnde);
                    RDnew.addMassPart(Pc, j);
                }
                else{
                    throw(new Error("The name of the mass part listed in the rocket design file is not recognised"));
                }


            }

            for(int i=0; i<MotNodes.getLength(); i++){
                Node Tnde = MotNodes.item(i);
                RocketMotor rMot = new RocketMotor(Tnde);
                RDnew.addMotor(rMot, j);
            }
            
            for(int i=0; i<TranNodes.getLength(); i++){
                Node Tnde = TranNodes.item(i);
                TransitionData tDat = new TransitionData(Tnde);
                RDnew.Stages.elementAt(j).addTransition(tDat);
            }



        }


        return(RDnew);
    }



}
