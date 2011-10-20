/*
%## Copyright (C) 2008 S.Box
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

%## ascent_variables.m

%## Author: S.Box
%## Created: 2010-05-27
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Rocket;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author simon
 */
public class RocketDescription {
    //**Class Members
    //Vector<RockPartsData> RPartsList = new Vector<RockPartsData>();
    public TreeModel RocketTree;
    private DefaultMutableTreeNode Body;
    private DefaultMutableTreeNode Mass;
    private DefaultMutableTreeNode Motor;
    private DefaultMutableTreeNode RootNode;

    //** Constructor
    public RocketDescription(){
        RootNode = new DefaultMutableTreeNode("Rocket");
        Body = new DefaultMutableTreeNode("Body Parts");
        Mass = new DefaultMutableTreeNode("Mass Parts");
        Motor = new DefaultMutableTreeNode("Motor");
        RootNode.add(Body);
        RootNode.add(Mass);
        RootNode.add(Motor);

        RocketTree = new DefaultTreeModel(RootNode);
    }

    //**Function to add part to body
    public void addBodyPart(RockPartsData Part){
        DefaultMutableTreeNode PartN  = new DefaultMutableTreeNode(Part);
        Body.add(PartN);
        TreeRefresh();
    }

    //**Function to add part to mass
    public void addMassPart(RockPartsData Part){
        DefaultMutableTreeNode PartN  = new DefaultMutableTreeNode(Part);
        Mass.add(PartN);
        TreeRefresh();
    }

    //**Function to add part to mass
    public void addMotor(RocketMotor Part){
        DefaultMutableTreeNode PartN  = new DefaultMutableTreeNode(Part);
        Motor.add(PartN);
        TreeRefresh();
    }

    //**Function to remove part
    public void RemovePart(DefaultMutableTreeNode Node){
        Node.removeFromParent();
        TreeRefresh();
    }

    public Vector<RockPartsData> ReturnBodyParts()
    {
        Vector<RockPartsData> Temp = new Vector<RockPartsData>();
        int Count = Body.getChildCount();
        for(int i =0; i<Count; i++)
        {
            DefaultMutableTreeNode T1 = (DefaultMutableTreeNode)Body.getChildAt(i);
            if(T1.isLeaf())
            {
                Temp.add((RockPartsData)T1.getUserObject());
            }
        }
        return(Temp);

    }

     public Vector<RockPartsData> ReturnMassParts()
    {
        Vector<RockPartsData> Temp = new Vector<RockPartsData>();
        int Count = Mass.getChildCount();
        for(int i =0; i<Count; i++)
        {
            DefaultMutableTreeNode T1 = (DefaultMutableTreeNode)Mass.getChildAt(i);
            if(T1.isLeaf())
            {
                Temp.add((RockPartsData)T1.getUserObject());
            }
        }
        return(Temp);

    }

    public RocketMotor ReturnMotorData()
    {
        RocketMotor Temp = new RocketMotor();   
        try{
            DefaultMutableTreeNode T1 = (DefaultMutableTreeNode)Motor.getChildAt(0);
            if(T1.isLeaf())
            {
                Temp = (RocketMotor)T1.getUserObject();
            }
            else{
                throw new Exception("Rocket Motor was not found");
            }

        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        return(Temp);
    }

    public Vector<ParachuteData> ReturnParachutes()
    {
        Vector<RockPartsData> Temp = ReturnMassParts();
        Vector<ParachuteData> Temp2 = new Vector<ParachuteData>();
        for(RockPartsData part:Temp){
            if(part.toString().contains("[Parachute]")){
                Temp2.add((ParachuteData)part);
            }
        }
        return(Temp2);

    }

    private void TreeRefresh(){
        Body.removeFromParent();
        Mass.removeFromParent();
        Motor.removeFromParent();
        RootNode.add(Body);
        RootNode.add(Mass);
        RootNode.add(Motor);
        RocketTree = new DefaultTreeModel(RootNode);
    }

    
}
