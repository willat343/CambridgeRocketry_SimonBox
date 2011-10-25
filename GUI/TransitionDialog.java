/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TransitionDialog.java
 *
 * Created on 28-Sep-2011, 18:14:18
 */

package Rocket;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author simon
 */
public class TransitionDialog extends javax.swing.JDialog {
    //members
    double
        separationTime,
        ignitionDelay;
    boolean ReadOk;
    String Name;

    /** Creates new form TransitionDialog */
    public TransitionDialog(java.awt.Frame parent, boolean modal) {
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

        jLabel1 = new javax.swing.JLabel();
        SeparationLabel = new javax.swing.JLabel();
        separationField = new javax.swing.JTextField();
        igDelayLabel = new javax.swing.JLabel();
        IgDelayField = new javax.swing.JTextField();
        SetConditionsButton = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Set the stage transition conditions");

        SeparationLabel.setText("Stage separation time");

        separationField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                separationFieldActionPerformed(evt);
            }
        });
        separationField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                separationFieldFocusLost(evt);
            }
        });

        igDelayLabel.setText("Motor ignition delay");

        IgDelayField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IgDelayFieldActionPerformed(evt);
            }
        });
        IgDelayField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                IgDelayFieldFocusLost(evt);
            }
        });

        SetConditionsButton.setText("Set");
        SetConditionsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SetConditionsButtonActionPerformed(evt);
            }
        });

        jLabel4.setText("s");

        jLabel5.setText("s");

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
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SeparationLabel)
                            .addComponent(igDelayLabel))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(separationField)
                            .addComponent(IgDelayField, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(167, 167, 167)
                        .addComponent(SetConditionsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(80, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(371, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(60, 60, 60)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SeparationLabel)
                    .addComponent(separationField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(igDelayLabel)
                    .addComponent(IgDelayField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(63, 63, 63)
                .addComponent(SetConditionsButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void separationFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_separationFieldActionPerformed
        
    }//GEN-LAST:event_separationFieldActionPerformed

    private void IgDelayFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IgDelayFieldActionPerformed
        
    }//GEN-LAST:event_IgDelayFieldActionPerformed

    private void SetConditionsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SetConditionsButtonActionPerformed
        boolean Goer = CheckValidity();
        if(Goer == true){
            ReadOk = true;
            this.dispose();
        } else{
            JOptionPane.showMessageDialog(null,("Something is wrong with the data that you entered"));
        }
    }//GEN-LAST:event_SetConditionsButtonActionPerformed

    private void separationFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_separationFieldFocusLost
        TestUserTextInput separationTest = new TestUserTextInput(separationField);
        separationTime= separationTest.TestDouble();
        separationField = separationTest.InputField;
    }//GEN-LAST:event_separationFieldFocusLost

    private void IgDelayFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_IgDelayFieldFocusLost
        TestUserTextInput ignitionTest = new TestUserTextInput(IgDelayField);
        ignitionDelay= ignitionTest.TestDouble();
        IgDelayField = ignitionTest.InputField;
    }//GEN-LAST:event_IgDelayFieldFocusLost

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Tips tDiag = new Tips(null,true);
        tDiag.setTipTxt("Stage Transition\n\nWhen you add a booster you must specify some data about stage separation. The separation time is the number of seconds after launch that the booster stage separates. The ignition delay is the number of seconds after separation that the upper stage ignites its motor. In the case of no motor (i.e. for a boosted dart) set this to 0.");
        tDiag.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TransitionDialog dialog = new TransitionDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    private boolean CheckValidity(){
    boolean answer;
    Vector<javax.swing.JTextField> FieldsList = new Vector<javax.swing.JTextField>();
    FieldsList.add(separationField);
    FieldsList.add(IgDelayField);

    TestUserTextInput TUI1 = new TestUserTextInput(FieldsList);
    answer = TUI1.TestDoubleList();
    return(answer);
    }

     public void FillFields(double d1,double d2,String s1){
        separationTime = d1;
        ignitionDelay = d2;
        Name = s1;

        separationField.setText(Double.toString(separationTime));
        IgDelayField.setText(Double.toString(ignitionDelay));

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField IgDelayField;
    private javax.swing.JLabel SeparationLabel;
    private javax.swing.JButton SetConditionsButton;
    private javax.swing.JLabel igDelayLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField separationField;
    // End of variables declaration//GEN-END:variables

}
