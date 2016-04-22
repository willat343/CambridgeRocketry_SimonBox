package net.sf.openrocket.gui.dialogs.motor;


import java.awt.Dialog;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import net.miginfocom.swing.MigLayout;
import net.sf.openrocket.camrocksim.MotorData;
import net.sf.openrocket.database.MotorDatabaseLoader;
import net.sf.openrocket.file.motor.GeneralMotorLoader;
import net.sf.openrocket.file.openrocket.CustomRocketMotor;
import net.sf.openrocket.gui.dialogs.motor.thrustcurve.ThrustCurveMotorSelectionPanel;
import net.sf.openrocket.gui.util.GUIUtil;
import net.sf.openrocket.gui.util.SwingPreferences;
import net.sf.openrocket.l10n.Translator;
import net.sf.openrocket.motor.Motor;
import net.sf.openrocket.rocketcomponent.MotorMount;
import net.sf.openrocket.startup.Application;

public class MotorChooserDialog extends JDialog implements CloseableDialog {
	
	private final ThrustCurveMotorSelectionPanel selectionPanel;
	
	private boolean okClicked = false;
	private static final Translator trans = Application.getTranslator();
	
	public MotorChooserDialog(MotorMount mount, String currentConfig, Window owner) {
		this(owner);
		setMotorMountAndConfig(mount, currentConfig);
	}
	
	public MotorChooserDialog(Window owner) {
		super(owner, trans.get("MotorChooserDialog.title"), Dialog.ModalityType.APPLICATION_MODAL);
		
		// We're going to reuse this dialog so only hide it when it's closed.
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		
		JPanel panel = new JPanel(new MigLayout("fill"));
		
		selectionPanel = new ThrustCurveMotorSelectionPanel();
		
		panel.add(selectionPanel, "grow, wrap");
		
		
		// OK / Cancel buttons
		JButton okButton = new JButton(trans.get("dlg.but.ok"));
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				close(true);
			}
		});
		panel.add(okButton, "tag ok, spanx, split");
		
		//// Cancel button
		JButton cancelButton = new JButton(trans.get("dlg.but.cancel"));
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				close(false);
			}
		});
		panel.add(cancelButton, "tag cancel");
		
		//// New motor button
		JButton newButton = new JButton("New motor");
		newButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: create new motor
				newMotor();
			}
		});
		panel.add(newButton, "");
		
		this.add(panel);
		
		this.setModal(true);
		this.pack();
		this.setLocationByPlatform(true);
		GUIUtil.installEscapeCloseOperation(this);
		
		JComponent focus = selectionPanel.getDefaultFocus();
		if (focus != null) {
			focus.grabFocus();
		}
		
		// Set the closeable dialog after all initialization
		selectionPanel.setCloseableDialog(this);
	}
	
	public void setMotorMountAndConfig( MotorMount mount, String currentConfig ) {
		selectionPanel.setMotorMountAndConfig(mount, currentConfig);
	}
	
	/**
	 * Return the motor selected by this chooser dialog, or <code>null</code> if the selection has been aborted.
	 * 
	 * @return	the selected motor, or <code>null</code> if no motor has been selected or the selection was canceled.
	 */
	public Motor getSelectedMotor() {
		if (!okClicked)
			return null;
		return selectionPanel.getSelectedMotor();
	}
	
	/**
	 * Return the selected ejection charge delay.
	 * 
	 * @return	the selected ejection charge delay.
	 */
	public double getSelectedDelay() {
		return selectionPanel.getSelectedDelay();
	}
	
	
	
	@Override
	public void close(boolean ok) {
		okClicked = ok;
		this.setVisible(false);
		
		Motor selected = getSelectedMotor();
		if (okClicked && selected != null) {
			selectionPanel.selectedMotor(selected);
		}
	}
	
	private void newMotor() {
		// TODO: finish function
		
		// get default folder to save file
		SwingPreferences preferences = (SwingPreferences) Application.getPreferences();
		File defaultThrustCurveFile = preferences.getDefaultUserThrustCurveFile();
		String defaultThrustCurveString = defaultThrustCurveFile.getAbsolutePath();
		
		//System.out.println(defaultThrustCurveFile);
		
		// use existing GUI
		MotorData thisMotorData = new MotorData();
		
		// populate with GUI
		thisMotorData.EditMe();
		
		// test thisMotorData (is it built?)
		
		if (thisMotorData.isBuilt()) {
		
			// new class, fill from thisMotorData
			CustomRocketMotor thisCustomRocketMotor = new CustomRocketMotor();
			
			thisCustomRocketMotor.setName( thisMotorData.Name );
			thisCustomRocketMotor.setDiameter (thisMotorData.Diameter );
			thisCustomRocketMotor.setLength( thisMotorData.Length );
			thisCustomRocketMotor.setDelay( 0.0 ); // default no delay
			
			thisCustomRocketMotor.setPropellantWeight( thisMotorData.LoadedMass - thisMotorData.DryMass );
			thisCustomRocketMotor.setTotalWeight(thisMotorData.LoadedMass);
			
			for (int i = 0; i < thisMotorData.Time.size(); i++)
			{
				double thisTime = thisMotorData.Time.get(i);
				double thisThrust = thisMotorData.Thrust.get(i);
				
				thisCustomRocketMotor.addPoint(thisTime, thisThrust);
			}
			
			// create file
			thisCustomRocketMotor.printMotorToFile(defaultThrustCurveString);
			
			// TODO: update database
		}
		
	}
}
