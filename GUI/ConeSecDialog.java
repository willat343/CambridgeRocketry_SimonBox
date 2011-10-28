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

%## ConeSecDialog.java

%## Author: S.Box
%## Created: 2010-05-27
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ConeSecDialog.java
 *
 * Created on 02-Feb-2010, 13:03:37
 */

package Rocket;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author sb4p07
 */
public class ConeSecDialog extends javax.swing.JDialog {

    //**Class Members
    double
            length,
            UD,
            DD,
            Thickness,
            Mass,
            Position,
            RadialPosition;
    boolean ReadOk;
    String Name;

    /** Creates new form ConeSecDialog */
    public ConeSecDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ConTranCancelButton = new javax.swing.JButton();
        ConTranThickUnit = new javax.swing.JLabel();
        ConTranAddButton = new javax.swing.JButton();
        ConTranMassUnit = new javax.swing.JLabel();
        ConTranPosUnit = new javax.swing.JLabel();
        ConTranUDUnit = new javax.swing.JLabel();
        ConTranDDUnit = new javax.swing.JLabel();
        ConTranRadPosLabel = new javax.swing.JLabel();
        ConTranRadPosText = new javax.swing.JTextField();
        ConTranLengthUnit = new javax.swing.JLabel();
        ConTranPosText = new javax.swing.JTextField();
        ConTranMassText = new javax.swing.JTextField();
        ConTranThickText = new javax.swing.JTextField();
        ConTranDDText = new javax.swing.JTextField();
        ConTranUDText = new javax.swing.JTextField();
        ConTranLengthText = new javax.swing.JTextField();
        ConTranNameText = new javax.swing.JTextField();
        ConTranThickLabel = new javax.swing.JLabel();
        ConTranPosLabel = new javax.swing.JLabel();
        ConTranMassLabel = new javax.swing.JLabel();
        ConTranDDLable = new javax.swing.JLabel();
        ConTranUDLabel = new javax.swing.JLabel();
        ConTranLengthLabel = new javax.swing.JLabel();
        ConTranNameLabel = new javax.swing.JLabel();
        ConTranSpecTitle = new javax.swing.JLabel();
        ConTranRefDUnit = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        ConTranCancelButton.setText("Cancel");
        ConTranCancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConTranCancelButtonActionPerformed(evt);
            }
        });

        ConTranThickUnit.setText("m");

        ConTranAddButton.setText("Add");
        ConTranAddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConTranAddButtonActionPerformed(evt);
            }
        });

        ConTranMassUnit.setText("kg");

        ConTranPosUnit.setText("m");

        ConTranUDUnit.setText("m");

        ConTranDDUnit.setText("m");

        ConTranRadPosLabel.setText("Radial Position");

        ConTranRadPosText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                ConTranRadPosTextFocusLost(evt);
            }
        });

        ConTranLengthUnit.setText("m");

        ConTranPosText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                ConTranPosTextFocusLost(evt);
            }
        });

        ConTranMassText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                ConTranMassTextFocusLost(evt);
            }
        });

        ConTranThickText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                ConTranThickTextFocusLost(evt);
            }
        });

        ConTranDDText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                ConTranDDTextFocusLost(evt);
            }
        });

        ConTranUDText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                ConTranUDTextFocusLost(evt);
            }
        });

        ConTranLengthText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                ConTranLengthTextFocusLost(evt);
            }
        });

        ConTranNameText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                ConTranNameTextFocusLost(evt);
            }
        });

        ConTranThickLabel.setText("Wall Thickness");

        ConTranPosLabel.setText("Position");

        ConTranMassLabel.setText("Mass");

        ConTranDDLable.setText("Downstream Diameter");

        ConTranUDLabel.setText("Upstream Diameter");

        ConTranLengthLabel.setText("Length");

        ConTranNameLabel.setText("Name");

        ConTranSpecTitle.setText("Set cone section specifications");

        ConTranRefDUnit.setText("m");

        jButton1.setText("?");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(ConTranSpecTitle, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ConTranNameLabel)
                                    .addComponent(ConTranLengthLabel)
                                    .addComponent(ConTranUDLabel)
                                    .addComponent(ConTranDDLable)
                                    .addComponent(ConTranThickLabel)
                                    .addComponent(ConTranMassLabel)
                                    .addComponent(ConTranPosLabel))
                                .addGap(49, 49, 49)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(ConTranPosText)
                                        .addComponent(ConTranNameText, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                                        .addComponent(ConTranLengthText)
                                        .addComponent(ConTranUDText)
                                        .addComponent(ConTranDDText)
                                        .addComponent(ConTranThickText)
                                        .addComponent(ConTranMassText))
                                    .addComponent(ConTranRadPosText, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ConTranThickUnit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ConTranMassUnit)
                                    .addComponent(ConTranPosUnit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(ConTranDDUnit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(ConTranUDUnit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(ConTranLengthUnit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(ConTranRefDUnit)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(ConTranAddButton, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ConTranCancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(ConTranRadPosLabel)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ConTranSpecTitle)
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ConTranNameLabel)
                    .addComponent(ConTranNameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ConTranLengthLabel)
                    .addComponent(ConTranLengthText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ConTranLengthUnit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ConTranUDLabel)
                    .addComponent(ConTranUDText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ConTranUDUnit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ConTranDDLable)
                    .addComponent(ConTranDDText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ConTranDDUnit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ConTranThickLabel)
                    .addComponent(ConTranThickText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ConTranThickUnit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ConTranMassLabel)
                    .addComponent(ConTranMassText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ConTranMassUnit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ConTranPosLabel)
                    .addComponent(ConTranPosText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ConTranPosUnit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ConTranRadPosLabel)
                    .addComponent(ConTranRefDUnit)
                    .addComponent(ConTranRadPosText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(79, 79, 79)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ConTranAddButton)
                    .addComponent(ConTranCancelButton)
                    .addComponent(jButton1))
                .addGap(29, 29, 29))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ConTranCancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConTranCancelButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
}//GEN-LAST:event_ConTranCancelButtonActionPerformed

    private void ConTranAddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConTranAddButtonActionPerformed
        // TODO add your handling code here:
        boolean Goer = CheckValidity();
        if(Goer == true){
            ReadOk = true;
            this.dispose();
        } else{
            JOptionPane.showMessageDialog(null,("Something is wrong with the data that you entered"));
        }
}//GEN-LAST:event_ConTranAddButtonActionPerformed

    private void ConTranRadPosTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ConTranRadPosTextFocusLost
        // TODO add your handling code here:
        TestUserTextInput RefDTest = new TestUserTextInput( ConTranRadPosText);
        RadialPosition = RefDTest.TestDouble();
        ConTranPosText = RefDTest.InputField;
}//GEN-LAST:event_ConTranRadPosTextFocusLost

    private void ConTranPosTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ConTranPosTextFocusLost
        //Function to test the validity of the user input
        TestUserTextInput PosTest = new TestUserTextInput( ConTranPosText);
        Position = PosTest.TestDouble();
        ConTranPosText = PosTest.InputField;
}//GEN-LAST:event_ConTranPosTextFocusLost

    private void ConTranMassTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ConTranMassTextFocusLost
        ///Function to test the validity of the user input
        TestUserTextInput MassTest = new TestUserTextInput( ConTranMassText);
        Mass = MassTest.TestDouble();
        ConTranMassText = MassTest.InputField;
}//GEN-LAST:event_ConTranMassTextFocusLost

    private void ConTranThickTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ConTranThickTextFocusLost
        ///Function to test the validity of the user input
        TestUserTextInput ThickTest = new TestUserTextInput( ConTranThickText);
        Thickness = ThickTest.TestDouble();
        ConTranThickText = ThickTest.InputField;
}//GEN-LAST:event_ConTranThickTextFocusLost

    private void ConTranDDTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ConTranDDTextFocusLost
        ///Function to test the validity of the user input
        TestUserTextInput DDTest = new TestUserTextInput( ConTranDDText);
        DD = DDTest.TestDouble();
        ConTranDDText = DDTest.InputField;
}//GEN-LAST:event_ConTranDDTextFocusLost

    private void ConTranUDTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ConTranUDTextFocusLost
        ///Function to test the validity of the user input
        TestUserTextInput UDTest = new TestUserTextInput( ConTranUDText);
        UD = UDTest.TestDouble();
        ConTranUDText = UDTest.InputField;
}//GEN-LAST:event_ConTranUDTextFocusLost

    private void ConTranLengthTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ConTranLengthTextFocusLost
        //Function to test the validity of the user input
        TestUserTextInput LengthTest = new TestUserTextInput( ConTranLengthText);
        length = LengthTest.TestDouble();
        ConTranLengthText = LengthTest.InputField;
}//GEN-LAST:event_ConTranLengthTextFocusLost

    private void ConTranNameTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ConTranNameTextFocusLost
        
        Name = ConTranNameText.getText();
}//GEN-LAST:event_ConTranNameTextFocusLost

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Tips tDiag = new Tips(null,true);
        tDiag.setTipTxt("Cone Section Settings \n\n Use this window to specify the parameters of the Cone Section part. Position is the distance, along the rocket's axis, from the nose tip to the foremost edge of the part. Radial position is the offset between the centre of the part and the rocket's axis. ");
        tDiag.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private boolean CheckValidity(){
    boolean answer;
    Vector<javax.swing.JTextField> FieldsList = new Vector<javax.swing.JTextField>();
    FieldsList.add(ConTranLengthText);
    FieldsList.add(ConTranUDText);
    FieldsList.add(ConTranDDText);
    FieldsList.add(ConTranThickText);
    FieldsList.add(ConTranMassText);
    FieldsList.add(ConTranPosText);
    FieldsList.add(ConTranRadPosText);

    TestUserTextInput TUI1 = new TestUserTextInput(FieldsList);
    answer = TUI1.TestDoubleList();
    return(answer);
}
    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ConeSecDialog dialog = new ConeSecDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    public void FillFields(double d1,double d2,double d3,double d4,double d5,double d6,double d7, String S1){
        length = d1;
        UD = d2;
        DD = d3;
        RadialPosition = d4;
        Thickness = d5;
        Mass = d6;
        Position = d7;
        Name = S1;

        ConTranLengthText.setText(Double.toString(length));
        ConTranUDText.setText(Double.toString(UD));
        ConTranDDText.setText(Double.toString(DD));
        ConTranThickText.setText(Double.toString(Thickness));
        ConTranMassText.setText(Double.toString(Mass));
        ConTranPosText.setText(Double.toString(Position));
        ConTranNameText.setText(Name);
        ConTranRadPosText.setText(Double.toString(RadialPosition));

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ConTranAddButton;
    private javax.swing.JButton ConTranCancelButton;
    private javax.swing.JLabel ConTranDDLable;
    private javax.swing.JTextField ConTranDDText;
    private javax.swing.JLabel ConTranDDUnit;
    private javax.swing.JLabel ConTranLengthLabel;
    private javax.swing.JTextField ConTranLengthText;
    private javax.swing.JLabel ConTranLengthUnit;
    private javax.swing.JLabel ConTranMassLabel;
    private javax.swing.JTextField ConTranMassText;
    private javax.swing.JLabel ConTranMassUnit;
    private javax.swing.JLabel ConTranNameLabel;
    private javax.swing.JTextField ConTranNameText;
    private javax.swing.JLabel ConTranPosLabel;
    private javax.swing.JTextField ConTranPosText;
    private javax.swing.JLabel ConTranPosUnit;
    private javax.swing.JLabel ConTranRadPosLabel;
    private javax.swing.JTextField ConTranRadPosText;
    private javax.swing.JLabel ConTranRefDUnit;
    private javax.swing.JLabel ConTranSpecTitle;
    private javax.swing.JLabel ConTranThickLabel;
    private javax.swing.JTextField ConTranThickText;
    private javax.swing.JLabel ConTranThickUnit;
    private javax.swing.JLabel ConTranUDLabel;
    private javax.swing.JTextField ConTranUDText;
    private javax.swing.JLabel ConTranUDUnit;
    private javax.swing.JButton jButton1;
    // End of variables declaration//GEN-END:variables

}
