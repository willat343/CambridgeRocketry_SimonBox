package net.sf.openrocket.gui.simulation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.miginfocom.swing.MigLayout;
import net.sf.openrocket.camrocksim.AtmosphereData;
import net.sf.openrocket.camrocksim.MotorData;
import net.sf.openrocket.camrocksim.RWatmosXML;
import net.sf.openrocket.camrocksim.RWmotorXML;
import net.sf.openrocket.document.Simulation;
import net.sf.openrocket.gui.SpinnerEditor;
import net.sf.openrocket.gui.adaptors.BooleanModel;
import net.sf.openrocket.gui.adaptors.DoubleModel;
import net.sf.openrocket.gui.components.BasicSlider;
import net.sf.openrocket.gui.components.UnitSelector;
import net.sf.openrocket.l10n.Translator;
import net.sf.openrocket.models.atmosphere.ExtendedISAModel;
import net.sf.openrocket.simulation.DefaultSimulationOptionFactory;
import net.sf.openrocket.simulation.SimulationOptions;
import net.sf.openrocket.startup.Application;
import net.sf.openrocket.unit.UnitGroup;
import net.sf.openrocket.util.Chars;

public class SimulationConditionsPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6026484093043119005L;
	private static final Translator trans = Application.getTranslator();
	
	JTextField text = new JTextField("Not initialised");
	
	SimulationConditionsPanel(final Simulation simulation) {
		super(new MigLayout("fill"));
		
		final SimulationOptions conditions = simulation.getOptions();
		
		JPanel sub;
		String tip;
		UnitSelector unit;
		BasicSlider slider;
		DoubleModel m;
		JSpinner spin;
		JButton button;
		JLabel label;
		
		//// Monte-carlo settings:  boolean and number (int)
		sub = new JPanel(new MigLayout("fill, gap rel unrel",
				"[grow][65lp!][30lp!][75lp!]", ""));
		//// Monte-carlo
		sub.setBorder(BorderFactory.createTitledBorder("Monte-carlo settings"));
		this.add(sub, "growx, split 2, aligny 0, flowy, gapright para");
		
		BooleanModel doMonteCarlo = new BooleanModel(conditions, "MonteCarloBool");
		JCheckBox check = new JCheckBox(doMonteCarlo);
		//// Use International Standard Atmosphere
		check.setText("do Monte-Carlo runs");
		check.setToolTipText("When checked, do multiple runs with uncertainty.");
		sub.add(check, "spanx, wrap unrel");
		
		// Number of Monte carlo runs:
		label = new JLabel("Number of Monte-Carlo runs");
		//// 
		tip = "The number of Monte-Carlo runs executed";
		label.setToolTipText(tip);
		doMonteCarlo.addEnableComponent(label, true);
		sub.add(label);
		
		m = new DoubleModel(conditions, "MonteCarloInteger", UnitGroup.UNITS_NONE, 0);
		
		spin = new JSpinner(m.getSpinnerModel());
		spin.setEditor(new SpinnerEditor(spin));
		spin.setToolTipText(tip);
		doMonteCarlo.addEnableComponent(spin, true);
		sub.add(spin, "w 65lp!");
		
		
		
		//// Atmosphere settings settings: 
		sub = new JPanel(new MigLayout("fill, gap rel unrel",
				"", "")); // "[25lp!][25lp!][25lp!][25lp!]"
		//// Wind
		sub.setBorder(BorderFactory.createTitledBorder("Atmosphere Settings"));
		this.add(sub,"growx"); // , "growx, split 4, aligny 0, flowy, gapright para"
		
		
		label = new JLabel("Selected profile:");
		sub.add(label);
		
		// text field with the selected atmosphere profile
		// text = new JTextField("-");
		// updateAtmosphere(simulation);
		text.setEditable(false);
		sub.add(text,"spanx, wrap"); //  "growx, gapright para"
		
		// new atmosphere button
		button = new JButton("New");
		//// new atmosphere
		button.setToolTipText("New atmosphere");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// do action
				newAtmosphere(simulation);
				// update
				updateAtmosphere(simulation);
			}
		});
		sub.add(button); // "w 75lp, wrap"
		
		// edit atmosphere button
		button = new JButton("Edit");
		//// new atmosphere
		button.setToolTipText("Edit atmosphere");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// do action
				editAtmosphere(simulation);
				// update
				updateAtmosphere(simulation);
			}
		});
		sub.add(button); // "w 75lp, wrap"
		
		button = new JButton("Save");
		//// load atmosphere
		button.setToolTipText("Save atmosphere");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// do action
				saveAtmosphere(simulation);
				// update
				updateAtmosphere(simulation);
				/*
				String thisFileName = selectAtmosphere();
				
				if (thisFileName != null) {
					// set new condition
					conditions.setAtmosphereString(thisFileName);
				}
				
				// update text
				text.setText(conditions.getAtmosphereString());
				*/
			}
		});
		sub.add(button);
		
		button = new JButton("Load");
		//// load atmosphere
		button.setToolTipText("Load atmosphere");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// do action
				// TODO load
				loadAtmosphere(simulation);
				// update
				updateAtmosphere(simulation);
				/*
				String thisFileName = selectAtmosphere();
				
				if (thisFileName != null) {
					// set new condition
					conditions.setAtmosphereString(thisFileName);
				}
				
				// update text
				text.setText(conditions.getAtmosphereString());
				*/
			}
		});
		sub.add(button); // , "growx"
		
		// selectWindProfile();
		
		/*
		
		// Wind average
		//// Average windspeed:
		JLabel label = new JLabel(trans.get("simedtdlg.lbl.Averwindspeed"));
		//// The average windspeed relative to the ground.
		tip = trans.get("simedtdlg.lbl.ttip.Averwindspeed");
		label.setToolTipText(tip);
		sub.add(label);
		
		m = new DoubleModel(conditions, "WindSpeedAverage", UnitGroup.UNITS_WINDSPEED, 0);
		
		spin = new JSpinner(m.getSpinnerModel());
		spin.setEditor(new SpinnerEditor(spin));
		spin.setToolTipText(tip);
		sub.add(spin, "w 65lp!");
		
		unit = new UnitSelector(m);
		unit.setToolTipText(tip);
		sub.add(unit, "growx");
		slider = new BasicSlider(m.getSliderModel(0, 10.0));
		slider.setToolTipText(tip);
		sub.add(slider, "w 75lp, wrap");
		
		
		
		// Wind std. deviation
		//// Standard deviation:
		label = new JLabel(trans.get("simedtdlg.lbl.Stddeviation"));
		//// <html>The standard deviation of the windspeed.<br>
		//// The windspeed is within twice the standard deviation from the average for 95% of the time.
		tip = trans.get("simedtdlg.lbl.ttip.Stddeviation");
		label.setToolTipText(tip);
		sub.add(label);
		
		m = new DoubleModel(conditions, "WindSpeedDeviation", UnitGroup.UNITS_WINDSPEED, 0);
		DoubleModel m2 = new DoubleModel(conditions, "WindSpeedAverage", 0.25,
				UnitGroup.UNITS_COEFFICIENT, 0);
		
		spin = new JSpinner(m.getSpinnerModel());
		spin.setEditor(new SpinnerEditor(spin));
		spin.setToolTipText(tip);
		sub.add(spin, "w 65lp!");
		
		unit = new UnitSelector(m);
		unit.setToolTipText(tip);
		sub.add(unit, "growx");
		slider = new BasicSlider(m.getSliderModel(new DoubleModel(0), m2));
		slider.setToolTipText(tip);
		sub.add(slider, "w 75lp, wrap");
		
		// Wind turbulence intensity
		//// Turbulence intensity:
		label = new JLabel(trans.get("simedtdlg.lbl.Turbulenceintensity"));
		//// <html>The turbulence intensity is the standard deviation divided by the average windspeed.<br>
		//// Typical values range from 
		//// to
		tip = trans.get("simedtdlg.lbl.ttip.Turbulenceintensity1") +
				trans.get("simedtdlg.lbl.ttip.Turbulenceintensity2") + " " +
				UnitGroup.UNITS_RELATIVE.getDefaultUnit().toStringUnit(0.05) +
				" " + trans.get("simedtdlg.lbl.ttip.Turbulenceintensity3") + " " +
				UnitGroup.UNITS_RELATIVE.getDefaultUnit().toStringUnit(0.20) + ".";
		label.setToolTipText(tip);
		sub.add(label);
		
		m = new DoubleModel(conditions, "WindTurbulenceIntensity", UnitGroup.UNITS_RELATIVE, 0);
		
		spin = new JSpinner(m.getSpinnerModel());
		spin.setEditor(new SpinnerEditor(spin));
		spin.setToolTipText(tip);
		sub.add(spin, "w 65lp!");
		
		unit = new UnitSelector(m);
		unit.setToolTipText(tip);
		sub.add(unit, "growx");
		
		final JLabel intensityLabel = new JLabel(
				getIntensityDescription(conditions.getWindTurbulenceIntensity()));
		intensityLabel.setToolTipText(tip);
		sub.add(intensityLabel, "w 75lp, wrap");
		m.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				intensityLabel.setText(
						getIntensityDescription(conditions.getWindTurbulenceIntensity()));
			}
		});
		
		// Wind Direction:
		label = new JLabel(trans.get("simedtdlg.lbl.Winddirection"));
		//// Direction of the wind. 0 is north
		tip = trans.get("simedtdlg.lbl.ttip.Winddirection");
		label.setToolTipText(tip);
		sub.add(label);
		
		m = new DoubleModel(conditions, "WindDirection", 1.0, UnitGroup.UNITS_ANGLE,
				0, 2*Math.PI);
					
		spin = new JSpinner(m.getSpinnerModel());
		spin.setEditor(new SpinnerEditor(spin));
		spin.setToolTipText(tip);
		sub.add(spin, "w 65lp!");
		
		unit = new UnitSelector(m);
		unit.setToolTipText(tip);
		sub.add(unit, "growx");
		slider = new BasicSlider(m.getSliderModel(0, 2*Math.PI));
		slider.setToolTipText(tip);
		sub.add(slider, "w 75lp, wrap");
		
		*/
		
		/*
		
		//// Temperature and pressure
		sub = new JPanel(new MigLayout("fill, gap rel unrel",
				"[grow][65lp!][30lp!][75lp!]", ""));
		//// Atmospheric conditions
		sub.setBorder(BorderFactory.createTitledBorder(trans.get("simedtdlg.border.Atmoscond")));
		this.add(sub, "growx, aligny 0, gapright para");
		
		
		BooleanModel isa = new BooleanModel(conditions, "ISAAtmosphere");
		JCheckBox check = new JCheckBox(isa);
		//// Use International Standard Atmosphere
		check.setText(trans.get("simedtdlg.checkbox.InterStdAtmosphere"));
		//// <html>Select to use the International Standard Atmosphere model.
		//// <br>This model has a temperature of
		//// and a pressure of
		//// at sea level.
		check.setToolTipText(trans.get("simedtdlg.checkbox.ttip.InterStdAtmosphere1") + " " +
				UnitGroup.UNITS_TEMPERATURE.toStringUnit(ExtendedISAModel.STANDARD_TEMPERATURE) +
				" " + trans.get("simedtdlg.checkbox.ttip.InterStdAtmosphere2") + " " +
				UnitGroup.UNITS_PRESSURE.toStringUnit(ExtendedISAModel.STANDARD_PRESSURE) +
				" " + trans.get("simedtdlg.checkbox.ttip.InterStdAtmosphere3"));
		sub.add(check, "spanx, wrap unrel");
		
		// Temperature:
		label = new JLabel(trans.get("simedtdlg.lbl.Temperature"));
		//// The temperature at the launch site.
		tip = trans.get("simedtdlg.lbl.ttip.Temperature");
		label.setToolTipText(tip);
		isa.addEnableComponent(label, false);
		sub.add(label);
		
		m = new DoubleModel(conditions, "LaunchTemperature", UnitGroup.UNITS_TEMPERATURE, 0);
		
		spin = new JSpinner(m.getSpinnerModel());
		spin.setEditor(new SpinnerEditor(spin));
		spin.setToolTipText(tip);
		isa.addEnableComponent(spin, false);
		sub.add(spin, "w 65lp!");
		
		unit = new UnitSelector(m);
		unit.setToolTipText(tip);
		isa.addEnableComponent(unit, false);
		sub.add(unit, "growx");
		slider = new BasicSlider(m.getSliderModel(253.15, 308.15)); // -20 ... 35
		slider.setToolTipText(tip);
		isa.addEnableComponent(slider, false);
		sub.add(slider, "w 75lp, wrap");
		
		
		
		// Pressure:
		label = new JLabel(trans.get("simedtdlg.lbl.Pressure"));
		//// The atmospheric pressure at the launch site.
		tip = trans.get("simedtdlg.lbl.ttip.Pressure");
		label.setToolTipText(tip);
		isa.addEnableComponent(label, false);
		sub.add(label);
		
		m = new DoubleModel(conditions, "LaunchPressure", UnitGroup.UNITS_PRESSURE, 0);
		
		spin = new JSpinner(m.getSpinnerModel());
		spin.setEditor(new SpinnerEditor(spin));
		spin.setToolTipText(tip);
		isa.addEnableComponent(spin, false);
		sub.add(spin, "w 65lp!");
		
		unit = new UnitSelector(m);
		unit.setToolTipText(tip);
		isa.addEnableComponent(unit, false);
		sub.add(unit, "growx");
		slider = new BasicSlider(m.getSliderModel(0.950e5, 1.050e5));
		slider.setToolTipText(tip);
		isa.addEnableComponent(slider, false);
		sub.add(slider, "w 75lp, wrap");
		
		*/
		
		
		
		
		//// Launch site conditions
		sub = new JPanel(new MigLayout("fill, gap rel unrel",
				"[grow][65lp!][30lp!][75lp!]", ""));
		//// Launch site
		sub.setBorder(BorderFactory.createTitledBorder(trans.get("simedtdlg.lbl.Launchsite")));
		this.add(sub, "growx, split 2, aligny 0, flowy");
		
		
		/*
		// Latitude:
		label = new JLabel(trans.get("simedtdlg.lbl.Latitude"));
		//// <html>The launch site latitude affects the gravitational pull of Earth.<br>
		//// Positive values are on the Northern hemisphere, negative values on the Southern hemisphere.
		tip = trans.get("simedtdlg.lbl.ttip.Latitude");
		label.setToolTipText(tip);
		sub.add(label);
		
		m = new DoubleModel(conditions, "LaunchLatitude", UnitGroup.UNITS_NONE, -90, 90);
		
		spin = new JSpinner(m.getSpinnerModel());
		spin.setEditor(new SpinnerEditor(spin));
		spin.setToolTipText(tip);
		sub.add(spin, "w 65lp!");
		
		label = new JLabel(Chars.DEGREE + " N");
		label.setToolTipText(tip);
		sub.add(label, "growx");
		slider = new BasicSlider(m.getSliderModel(-90, 90));
		slider.setToolTipText(tip);
		sub.add(slider, "w 75lp, wrap");
		
		
		// Longitude:
		label = new JLabel(trans.get("simedtdlg.lbl.Longitude"));
		tip = trans.get("simedtdlg.lbl.ttip.Longitude");
		label.setToolTipText(tip);
		sub.add(label);
		
		m = new DoubleModel(conditions, "LaunchLongitude", UnitGroup.UNITS_NONE, -180, 180);
		
		spin = new JSpinner(m.getSpinnerModel());
		spin.setEditor(new SpinnerEditor(spin));
		spin.setToolTipText(tip);
		sub.add(spin, "w 65lp!");
		
		label = new JLabel(Chars.DEGREE + " E");
		label.setToolTipText(tip);
		sub.add(label, "growx");
		slider = new BasicSlider(m.getSliderModel(-180, 180));
		slider.setToolTipText(tip);
		sub.add(slider, "w 75lp, wrap");
		
		*/
		
		// Altitude:
		label = new JLabel(trans.get("simedtdlg.lbl.Altitude"));
		//// <html>The launch altitude above mean sea level.<br> 
		//// This affects the position of the rocket in the atmospheric model.
		tip = trans.get("simedtdlg.lbl.ttip.Altitude");
		label.setToolTipText(tip);
		sub.add(label);
		
		m = new DoubleModel(conditions, "LaunchAltitude", UnitGroup.UNITS_DISTANCE, 0);
		
		spin = new JSpinner(m.getSpinnerModel());
		spin.setEditor(new SpinnerEditor(spin));
		spin.setToolTipText(tip);
		sub.add(spin, "w 65lp!");
		
		unit = new UnitSelector(m);
		unit.setToolTipText(tip);
		sub.add(unit, "growx");
		slider = new BasicSlider(m.getSliderModel(0, 250, 1000));
		slider.setToolTipText(tip);
		sub.add(slider, "w 75lp, wrap");
		
		//// Launch rod
		sub = new JPanel(new MigLayout("fill, gap rel unrel",
				"[grow][65lp!][30lp!][75lp!]", ""));
		//// Launch rod
		sub.setBorder(BorderFactory.createTitledBorder("Launch"));
		this.add(sub, "growx, aligny 0, wrap");
		
		
		// Length:
		label = new JLabel(trans.get("simedtdlg.lbl.Length"));
		//// The length of the launch rod.
		tip = trans.get("simedtdlg.lbl.ttip.Length");
		label.setToolTipText(tip);
		sub.add(label);
		
		m = new DoubleModel(conditions, "LaunchRodLength", UnitGroup.UNITS_LENGTH, 0);
		
		spin = new JSpinner(m.getSpinnerModel());
		spin.setEditor(new SpinnerEditor(spin));
		spin.setToolTipText(tip);
		sub.add(spin, "w 65lp!");
		
		unit = new UnitSelector(m);
		unit.setToolTipText(tip);
		sub.add(unit, "growx");
		slider = new BasicSlider(m.getSliderModel(0, 1, 5));
		slider.setToolTipText(tip);
		sub.add(slider, "w 75lp, wrap");
		
		
		// Keep launch rod parallel to the wind.
		
		/*
		
		BooleanModel intoWind = new BooleanModel(conditions, "LaunchIntoWind");
		JCheckBox checkWind = new JCheckBox(intoWind);
		//// Use International Standard Atmosphere
		checkWind.setText(trans.get("simedtdlg.checkbox.Intowind"));
		checkWind.setToolTipText(
				trans.get("simedtdlg.checkbox.ttip.Intowind1") +
				trans.get("simedtdlg.checkbox.ttip.Intowind2") +
				trans.get("simedtdlg.checkbox.ttip.Intowind3") +
				trans.get("simedtdlg.checkbox.ttip.Intowind4"));
		sub.add(checkWind, "spanx, wrap unrel");
		
		*/
		
		// Angle:
		label = new JLabel(trans.get("simedtdlg.lbl.Angle"));
		//// The angle of the launch rod from vertical.
		/*
		tip = trans.get("simedtdlg.lbl.ttip.Angle");
		label.setToolTipText(tip);
		*/
		label.setToolTipText("Angle from the top, e.g. 0 [deg] pointing up, 90 [deg] pointing side-ways");
		sub.add(label);
		
		m = new DoubleModel(conditions, "LaunchRodAngle", UnitGroup.UNITS_ANGLE,
				SimulationOptions.MIN_LAUNCH_ROD_ANGLE, SimulationOptions.MAX_LAUNCH_ROD_ANGLE );
		
		spin = new JSpinner(m.getSpinnerModel());
		spin.setEditor(new SpinnerEditor(spin));
		spin.setToolTipText(tip);
		sub.add(spin, "w 65lp!");
		
		unit = new UnitSelector(m);
		unit.setToolTipText(tip);
		sub.add(unit, "growx");
		slider = new BasicSlider(m.getSliderModel(SimulationOptions.MIN_LAUNCH_ROD_ANGLE, 
				SimulationOptions.AVG_LAUNCH_ROD_ANGLE,
				SimulationOptions.MAX_LAUNCH_ROD_ANGLE));
		slider.setToolTipText(tip);
		sub.add(slider, "w 75lp, wrap");
		
		
		// Angle sigma 
		label = new JLabel("+-angle");
		//// The angle of the launch rod from vertical.
		tip = "Angle standard deviation";
		label.setToolTipText(tip);
		sub.add(label);
		
		m = new DoubleModel(conditions, "SigmaLaunchDeclination", UnitGroup.UNITS_ANGLE,
				0, 90);
		
		spin = new JSpinner(m.getSpinnerModel());
		spin.setEditor(new SpinnerEditor(spin));
		spin.setToolTipText(tip);
		sub.add(spin, "w 65lp!");
		
		unit = new UnitSelector(m);
		unit.setToolTipText(tip);
		sub.add(unit, "growx, wrap");
		
		
		
		// Direction:
		JLabel directionLabel = new JLabel("Azimuth");
		//// <html>Direction of the launch rod.
		/*
		tip = trans.get("simedtdlg.lbl.ttip.Direction1") +
				UnitGroup.UNITS_ANGLE.toStringUnit(0) +
				" " + trans.get("simedtdlg.lbl.ttip.Direction2") + " " +
				UnitGroup.UNITS_ANGLE.toStringUnit(2*Math.PI) +
				" " + trans.get("simedtdlg.lbl.ttip.Direction3");
		directionLabel.setToolTipText(tip);
		*/
		directionLabel.setToolTipText("Azimuth angle relative to the North, i.e. 90 [deg] is East");
		sub.add(directionLabel);
		
		m = new DoubleModel(conditions, "LaunchRodDirection", 1.0, UnitGroup.UNITS_ANGLE,
				0, 2*Math.PI);
		
		JSpinner directionSpin = new JSpinner(m.getSpinnerModel());
		directionSpin.setEditor(new SpinnerEditor(directionSpin));
		directionSpin.setToolTipText(tip);
		sub.add(directionSpin, "w 65lp!");
		
		unit = new UnitSelector(m);
		unit.setToolTipText(tip);
		sub.add(unit, "growx");
		BasicSlider directionSlider = new BasicSlider(m.getSliderModel(0, 2*Math.PI));
		directionSlider.setToolTipText(tip);
		sub.add(directionSlider, "w 75lp, wrap");
		
		// +- thrust
		label = new JLabel("+-Thrust");
		//// The angle of the launch rod from vertical.
		tip = "Thrust standard deviation";
		label.setToolTipText(tip);
		sub.add(label);
		
		m = new DoubleModel(conditions, "SigmaThrust", UnitGroup.UNITS_FORCE,
				0, 1000);
		
		spin = new JSpinner(m.getSpinnerModel());
		spin.setEditor(new SpinnerEditor(spin));
		spin.setToolTipText(tip);
		sub.add(spin, "w 65lp!");
		
		unit = new UnitSelector(m);
		unit.setToolTipText(tip);
		sub.add(unit, "growx, wrap");
		
		/*
		intoWind.addEnableComponent(directionLabel, false);
		intoWind.addEnableComponent(directionSpin, false);
		intoWind.addEnableComponent(unit, false);
		intoWind.addEnableComponent(directionSlider, false);
		*/
		
		JButton restoreDefaults = new JButton(trans.get("simedtdlg.but.resettodefault"));
		restoreDefaults.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				DefaultSimulationOptionFactory f = Application.getInjector().getInstance(DefaultSimulationOptionFactory.class);
				SimulationOptions defaults = f.getDefault();
				conditions.copyConditionsFrom(defaults);
				
			}
			
		});
		this.add(restoreDefaults, "span, split 3, skip, gapbottom para, gapright para, right");
		
		JButton saveDefaults = new JButton(trans.get("simedtdlg.but.savedefault"));
		saveDefaults.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				DefaultSimulationOptionFactory f = Application.getInjector().getInstance(DefaultSimulationOptionFactory.class);
				f.saveDefault(conditions);
				
			}
			
		});
		
		this.add(saveDefaults, "gapbottom para, gapright para, right");
		
		updateAtmosphere(simulation);
		
	}
	
	private String getIntensityDescription(double i) {
		if (i < 0.001)
			//// None
			return trans.get("simedtdlg.IntensityDesc.None");
		if (i < 0.05)
			//// Very low
			return trans.get("simedtdlg.IntensityDesc.Verylow");
		if (i < 0.10)
			//// Low
			return trans.get("simedtdlg.IntensityDesc.Low");
		if (i < 0.15)
			//// Medium
			return trans.get("simedtdlg.IntensityDesc.Medium");
		if (i < 0.20)
			//// High
			return trans.get("simedtdlg.IntensityDesc.High");
		if (i < 0.25)
			//// Very high
			return trans.get("simedtdlg.IntensityDesc.Veryhigh");
		//// Extreme
		return trans.get("simedtdlg.IntensityDesc.Extreme");
	}
	
	private String selectAtmosphere() {
		/*
		 * select a windprofile
		 * 
		 */
		String FileName = null;
		
		AtmosphereData thisAtmosphereData = new AtmosphereData();
		
		JFileChooser fc = new JFileChooser();
		File thisFile = new File("./Atmospheres"); // TODO fix hardcoded folder location
		fc.setCurrentDirectory(thisFile);
		int RetVal = fc.showSaveDialog(null);
		if (RetVal == fc.APPROVE_OPTION) {
			FileName = fc.getSelectedFile().getPath();
		}
		
		if (FileName != null)
		{
			
			try {
				
				RWatmosXML thisAtmosXML = new RWatmosXML( FileName );
				
				thisAtmosXML.ReadXMLtoAtmos();
				
				thisAtmosphereData = thisAtmosXML.getAtmosphereData();
				
			}
			catch (Exception e1) {
				JOptionPane.showMessageDialog(null, ("Something is wrong with the data that you entered"));
				FileName = null; // set back to null, don't want to use it.
			}
		}
		
		return FileName;
		
	}
	
	private void newAtmosphere(Simulation simulation) {
		
		// create a new atmosphere
		AtmosphereData newAtmosphereData = new AtmosphereData();
		newAtmosphereData.EditMeRaw();
		
		// overwrite if built
		if (newAtmosphereData.built) {
			//simulation.setAtmosphereData(newAtmosphereData);
			simulation.getOptions().atmosphereData = newAtmosphereData;
		}
		
	}
	
	private void editAtmosphere(Simulation simulation) {
		// TODO
		
		// obtain set atmosphere
		AtmosphereData thisAtmosphereData = simulation.getOptions().atmosphereData;
		
		thisAtmosphereData.EditMeRaw();
		
		if (thisAtmosphereData != null) {
			simulation.getOptions().atmosphereData = (thisAtmosphereData);
		}
		
	}
	
	private void saveAtmosphere(Simulation simulation) {
		// TODO
		
		String FileName = null;
		
		JFileChooser fc = new JFileChooser();
		File thisFile = new File("../../Data/Atmospheres"); 
		fc.setCurrentDirectory(thisFile);
		int RetVal = fc.showSaveDialog(null);
		if (RetVal == fc.APPROVE_OPTION) {
			FileName = fc.getSelectedFile().getPath();
		}
		
		AtmosphereData thisAtmosphereData = simulation.getOptions().atmosphereData;
		
		if (FileName != null)
		{
			
			try {
				
				RWatmosXML thisAtmosXML = new RWatmosXML( FileName );
				thisAtmosXML.WriteAtmosToXML(thisAtmosphereData);
				
			}
			catch (Exception e1) {
				JOptionPane.showMessageDialog(null, ("Something is wrong with the data that you entered"));
				FileName = null; // set back to null, don't want to use it.
			}
		}
		
	}
	
	private void loadAtmosphere(Simulation simulation) {
		
		String FileName = null;
		
		JFileChooser fc = new JFileChooser();
		File thisFile = new File("../../Data/Atmospheres"); // TODO fix hardcoded folder location
		fc.setCurrentDirectory(thisFile);
		int RetVal = fc.showSaveDialog(null);
		if (RetVal == fc.APPROVE_OPTION) {
			FileName = fc.getSelectedFile().getPath();
		}
		
		AtmosphereData thisAtmosphereData = null; // simulation.getAtmosphereData();
		
		if (FileName != null)
		{
			
			try {
				
				RWatmosXML thisAtmosXML = new RWatmosXML( FileName );
				thisAtmosXML.ReadXMLtoAtmos();
				thisAtmosphereData = thisAtmosXML.getAtmosphereData();
				
			}
			catch (Exception e1) {
				JOptionPane.showMessageDialog(null, ("Something is wrong with the data that you entered"));
				FileName = null; // set back to null, don't want to use it.
			}
		}
		
		if (thisAtmosphereData != null) {
			simulation.getOptions().atmosphereData = (thisAtmosphereData);
		}
		
	}
	
	private void updateAtmosphere(Simulation simulation) {
		
		// String newName = simulation.getOptions().atmosphereData.name;
		
		if (simulation.getOptions().atmosphereData.built) {
			text.setText(simulation.getOptions().atmosphereData.name);
		}
		else {
			text.setText("Unspecified atmosphere");
		}
		
	}
	
}
