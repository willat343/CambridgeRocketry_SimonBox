package net.sf.openrocket.gui.main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.DefaultTableCellRenderer;

import net.miginfocom.swing.MigLayout;
import net.sf.openrocket.aerodynamics.Warning;
import net.sf.openrocket.aerodynamics.WarningSet;
import net.sf.openrocket.arch.SystemInfo;
import net.sf.openrocket.document.OpenRocketDocument;
import net.sf.openrocket.document.Simulation;
import net.sf.openrocket.document.Simulation.Status;
import net.sf.openrocket.document.events.DocumentChangeEvent;
import net.sf.openrocket.document.events.DocumentChangeListener;
import net.sf.openrocket.document.events.SimulationChangeEvent;
import net.sf.openrocket.formatting.RocketDescriptor;
import net.sf.openrocket.gui.adaptors.Column;
import net.sf.openrocket.gui.adaptors.ColumnTable;
import net.sf.openrocket.gui.adaptors.ColumnTableModel;
import net.sf.openrocket.gui.adaptors.ColumnTableRowSorter;
import net.sf.openrocket.gui.adaptors.ValueColumn;
import net.sf.openrocket.gui.components.StyledLabel;
import net.sf.openrocket.gui.simulation.SimulationEditDialog;
import net.sf.openrocket.gui.simulation.SimulationRunDialog;
import net.sf.openrocket.gui.simulation.SimulationWarningDialog;
import net.sf.openrocket.gui.util.GUIUtil;
import net.sf.openrocket.gui.util.Icons;
// import net.sf.openrocket.gui.util.SwingPreferences;
import net.sf.openrocket.l10n.Translator;
import net.sf.openrocket.simulation.FlightData;
import net.sf.openrocket.simulation.SimulationConditions;
import net.sf.openrocket.simulation.SimulationOptions;
// import net.sf.openrocket.simulation.FlightEvent;
import net.sf.openrocket.startup.Application;
import net.sf.openrocket.startup.Preferences;
import net.sf.openrocket.unit.UnitGroup;
import net.sf.openrocket.util.AlphanumComparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import net.sf.openrocket.rocketcomponent.*;
import net.sf.openrocket.rocketcomponent.DeploymentConfiguration.DeployEvent;
import net.sf.openrocket.camrocksim.*;

public class panelCamRockSim extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4815038225984990190L;
	private static final Logger log = LoggerFactory.getLogger(panelCamRockSim.class);
	private static final Translator trans = Application.getTranslator();


	private static final Color WARNING_COLOR = Color.RED;
	private static final String WARNING_TEXT = "\uFF01"; // Fullwidth exclamation mark

	private static final Color OK_COLOR = new Color(60, 150, 0);
	private static final String OK_TEXT = "\u2714"; // Heavy check mark


	private RocketDescriptor descriptor = Application.getInjector().getInstance(RocketDescriptor.class);


	private final OpenRocketDocument document;
	
	private final ColumnTableModel exportTableModel;
	private final JTable simulationTable;
	
	private final JButton editButton;
	private final JButton runButton;
	private final JButton deleteButton;
	private final JButton plotButton;
	
	private final JButton exportButton;
	

	public panelCamRockSim(OpenRocketDocument doc) {
		super(new MigLayout("fill", "[grow][][][][][][grow]"));

		this.document = doc;
		
		////////  The simulation action buttons

		//// New simulation button
		{
			JButton button = new JButton(trans.get("simpanel.but.newsimulation"));
			//// Add a new simulation
			button.setToolTipText(trans.get("simpanel.but.ttip.newsimulation"));
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Simulation sim = new Simulation(document.getRocket());
					sim.setName(document.getNextSimulationName());

					int n = document.getSimulationCount();
					document.addSimulation(sim);
					exportTableModel.fireTableDataChanged();
					simulationTable.clearSelection();
					simulationTable.addRowSelectionInterval(n, n);

					openDialog(false, sim);
				}
			});
			this.add(button, "skip 1, gapright para");
		}

		//// Edit simulation button
		editButton = new JButton(trans.get("simpanel.but.editsimulation"));
		//// Edit the selected simulation
		editButton.setToolTipText(trans.get("simpanel.but.ttip.editsim"));
		editButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int[] selection = simulationTable.getSelectedRows();
				if (selection.length == 0) {
					return;
				}
				Simulation[] sims = new Simulation[selection.length];
				for (int i = 0; i < selection.length; i++) {
					selection[i] = simulationTable.convertRowIndexToModel(selection[i]);
					sims[i] = document.getSimulation(selection[i]);
				}
				openDialog(false, sims);
			}
		});
		this.add(editButton, "gapright para");

		//// Run simulations
		runButton = new JButton(trans.get("simpanel.but.runsimulations"));
		//// Re-run the selected simulations
		runButton.setToolTipText(trans.get("simpanel.but.ttip.runsimu"));
		runButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int[] selection = simulationTable.getSelectedRows();
				if (selection.length == 0) {
					return;
				}
				Simulation[] sims = new Simulation[selection.length];
				for (int i = 0; i < selection.length; i++) {
					selection[i] = simulationTable.convertRowIndexToModel(selection[i]);
					sims[i] = document.getSimulation(selection[i]);
				}

				long t = System.currentTimeMillis();
				new SimulationRunDialog(SwingUtilities.getWindowAncestor(
						panelCamRockSim.this), document, sims).setVisible(true);
				log.info("Running simulations took " + (System.currentTimeMillis() - t) + " ms");
				fireMaintainSelection();
			}
		});
		// this.add(runButton, "gapright para");

		//// Delete simulations button
		deleteButton = new JButton(trans.get("simpanel.but.deletesimulations"));
		//// Delete the selected simulations
		deleteButton.setToolTipText(trans.get("simpanel.but.ttip.deletesim"));
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int[] selection = simulationTable.getSelectedRows();
				if (selection.length == 0) {
					return;
				}
				// Verify deletion
				boolean verify = Application.getPreferences().getBoolean(Preferences.CONFIRM_DELETE_SIMULATION, true);
				if (verify) {

					JPanel panel = new JPanel(new MigLayout());
					//// Do not ask me again
					JCheckBox dontAsk = new JCheckBox(trans.get("simpanel.checkbox.donotask"));
					panel.add(dontAsk, "wrap");
					//// You can change the default operation in the preferences.
					panel.add(new StyledLabel(trans.get("simpanel.lbl.defpref"), -2));

					int ret = JOptionPane.showConfirmDialog(panelCamRockSim.this,
							new Object[] {
							//// Delete the selected simulations?
							trans.get("simpanel.dlg.lbl.DeleteSim1"),
							//// <html><i>This operation cannot be undone.</i>
							trans.get("simpanel.dlg.lbl.DeleteSim2"),
							"",
							panel },
							//// Delete simulations
							trans.get("simpanel.dlg.lbl.DeleteSim3"),
							JOptionPane.OK_CANCEL_OPTION,
							JOptionPane.WARNING_MESSAGE);
					if (ret != JOptionPane.OK_OPTION)
						return;

					if (dontAsk.isSelected()) {
						Application.getPreferences().putBoolean(Preferences.CONFIRM_DELETE_SIMULATION, false);
					}
				}

				// Delete simulations
				for (int i = 0; i < selection.length; i++) {
					selection[i] = simulationTable.convertRowIndexToModel(selection[i]);
				}
				Arrays.sort(selection);
				for (int i = selection.length - 1; i >= 0; i--) {
					document.removeSimulation(selection[i]);
				}
				exportTableModel.fireTableDataChanged();
			}
		});
		this.add(deleteButton, "gapright para");

		
		//// Plot / export button
		plotButton = new JButton(trans.get("simpanel.but.plotexport"));
		//		button = new JButton("Plot flight");
		plotButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selected = simulationTable.getSelectedRow();
				if (selected < 0) {
					return; 
				}
				selected = simulationTable.convertRowIndexToModel(selected);
				simulationTable.clearSelection();
				simulationTable.addRowSelectionInterval(selected, selected);


				Simulation sim = document.getSimulations().get(selected);
				
				if (!sim.hasSimulationData()) {
					new SimulationRunDialog(SwingUtilities.getWindowAncestor(
							panelCamRockSim.this), document, sim).setVisible(true);
				}

				fireMaintainSelection();

				openDialog(true, sim);

			}
		});
		// this.add(plotButton, "gapright para");
		

		//  Run simulation using camrocksim backend
		exportButton = new JButton("Run+Plot simulation");
		//  button = new JButton("Plot flight");
		exportButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selected = simulationTable.getSelectedRow();
				if (selected < 0) {
					return; 
				}
				selected = simulationTable.convertRowIndexToModel( selected );
				simulationTable.clearSelection();
				simulationTable.addRowSelectionInterval(selected, selected);

				// this simulation
				Simulation thisSimulation = document.getSimulations().get( selected );
				
				SimulationOptions thisSimulationOptions = thisSimulation.getOptions();
				
				// convert OpenRocketDocument (openrocket) to RocketDescription (camrocksim)
				RocketDescription thisRocketDescription = document.getRocketDescription( thisSimulation );
				
				
				/*
					// (temporary) export XML of design, for testing
					RWdesignXML thisDesign = new RWdesignXML("rocketDesign.xml");
					// fill design with rocket description (easy comparison)
					thisDesign.WriteDesign(thisRocketDescription);
				*/
				
				// setup atmosphere
				AtmosphereData thisAtmosphereData = thisSimulationOptions.atmosphereData;
				
				double thisRailLength = thisSimulationOptions.getLaunchRodLength(); // [m]
				double thisAzimuthRad = thisSimulationOptions.getLaunchRodDirection(); // [rad]
				double thisDeclinationRad = thisSimulationOptions.getLaunchRodAngle(); // [rad]
				
				// convert from rad to deg
				double thisAzimuthDeg = thisAzimuthRad / (2*Math.PI) * 360;
				double thisDeclinationDeg = thisDeclinationRad / (2*Math.PI) * 360;
				
				double thisAltitude = thisSimulationOptions.getLaunchAltitude();
				
				LaunchData thisLaunchData = new LaunchData(
						thisRailLength,thisAzimuthDeg,thisDeclinationDeg,
						thisAltitude);
				
				// use relative paths
				Path jarPath = SystemInfo.getJarLocation();
				
				// System.out.println("jar path: " + jarPath.toString());
				
				File fileSimulationInput = new File(jarPath.toFile(), ("Data" + File.separator + "SimulationInput.xml"));
				
				// System.out.println(fileSimulationInput.toString());
				
				RWsiminXML thisSimulationInput = new RWsiminXML(fileSimulationInput.toString());
				
				// RWsiminXML thisSimulationInput = new RWsiminXML("Data/SimulationInput.xml");
				
				boolean isMonteCarlo = thisSimulationOptions.getMonteCarloBool();
				double numberOfMonteCarloDouble = thisSimulationOptions.getMonteCarloInteger();
				int numberOfMonteCarloInt = (int) numberOfMonteCarloDouble;
				
				boolean isBallisticFailure = false;
				
				// writes all information to SimulationInput.xml
				thisSimulationInput.WriteSimDataToXML(thisRocketDescription, 
						thisAtmosphereData, thisLaunchData,
						isMonteCarlo, numberOfMonteCarloInt, isBallisticFailure);
				
				// edit information on uncertainty for monte carlo
				File fileUncertainty = new File(jarPath.toFile(), "Data" + File.separator + "Uncertainty.xml");
				
				// System.out.println(fileUncertainty.toString());
				
				RWuncertainty thisUncertainty = new RWuncertainty(fileUncertainty.toString());
				
				// get new values values
				double sigmaLaunchDeclinationRad = thisSimulationOptions.getSigmaLaunchDeclination();
				double sigmaLaunchDeclinationDeg = sigmaLaunchDeclinationRad / (2*Math.PI) * 360;
				double sigmaThrust = thisSimulationOptions.getSigmaThrust();
				// set new values values
				thisUncertainty.setSigmaLaunchDeclination(sigmaLaunchDeclinationDeg);
				thisUncertainty.setSigmaThrust(sigmaThrust);
				
				// update xml (update with previous set values)
				thisUncertainty.UpdateXML();
				
				
				
				// run program
				try{

					// create dialog
					final RunningDialog dialog = new RunningDialog();
					
					File fileRocketc = new File(jarPath.toFile(),  "Data" + File.separator + "rocketc");
					
					// System.out.println(fileRocketc.toString());
					
					String thisCommand= fileRocketc.toString() + " " + fileSimulationInput.toString(); // location binary root/Data
					
					// System.out.println(thisCommand);
					
					final Process thisProcess = Runtime.getRuntime().exec(thisCommand, null);
					
					Timer timer = new Timer(100, new ActionListener() {
						
						private int count = 0;
						

						BufferedReader thisBufferedReader = 
								new BufferedReader(new InputStreamReader(thisProcess.getInputStream()));
						String thisPrintLine = null;
						
						boolean isDone = false;
						
						int intCurrent = 0, intTotal = 100;
						
						@Override
						public void actionPerformed(ActionEvent e) {
							count++;
							
							// check
							try {
								if ((thisPrintLine = thisBufferedReader.readLine()) != null) {
									// thisPrintLine, (current,total)
									
									if (thisPrintLine.contains("(")){
										
										String strCurrent = thisPrintLine.substring(thisPrintLine.indexOf("(") + 1, 
												thisPrintLine.indexOf(",") );
										String strTotal = thisPrintLine.substring(thisPrintLine.indexOf(",") + 1, 
												thisPrintLine.indexOf(")") );
										// transform to values
										intCurrent = Integer.parseInt(strCurrent);
										intTotal = Integer.parseInt(strTotal);
										// check if it's a valid number
										if (intCurrent == (int) intCurrent) {
											// adjust maximum
											if (intCurrent < intTotal) {
												// running
												isDone = false;
											}
											else {
												// done
												isDone = true;
											}
										}
									}
									
									// print to console
									System.out.println(thisPrintLine);
								}
							} catch (Exception e1) {
								// do nothing
								System.out.println("Failed to read the output string");
							}
							
							
							if (isDone) {
								// done
								//log.debug("Database loaded, closing dialog");
								dialog.adjustProgress(intCurrent, intTotal);
								dialog.setVisible(false);
							} else if (count % 10 == 0) {
								// not yet done
								dialog.adjustProgress(intCurrent, intTotal);
								//log.debug("Database not loaded, count=" + count);
							}
						}
					});
					
					timer.start();
					dialog.setVisible(true);
					timer.stop();
					
					// plot output
					if (dialog.isDone()) {
						File filePlotter = new File(jarPath.toFile(),  "Data" + File.separator + "Plotter" + File.separator);
						// System.out.println(filePlotter.toString());
						PlotLauncher thisPlotLauncher = new PlotLauncher(filePlotter.toString());
						File thisFile = new File(jarPath.toFile(), "Data" + File.separator + "SimulationOutput.xml");
						// System.out.println(thisFile.toString());
						thisPlotLauncher.MakePlots(thisFile);
					} else {
						// kill remaining process!
						thisProcess.destroy();
					}
					
					/*
					while ((thisPrintLine = thisBufferedReader.readLine()) != null) {
						// thisPrintLine, (current,total)
						
						if (thisPrintLine.contains("(")){
							
							String strCurrent = thisPrintLine.substring(thisPrintLine.indexOf("(") + 1, 
									thisPrintLine.indexOf(",") );
							String strTotal = thisPrintLine.substring(thisPrintLine.indexOf(",") + 1, 
									thisPrintLine.indexOf(")") );
							// transform to values
							int intCurrent = Integer.parseInt(strCurrent);
							int intTotal = Integer.parseInt(strTotal);
							// check if it's a valid number
							if (intCurrent == (int) intCurrent) {
								// adjust maximum
								if (intCurrent < intTotal) {
									// running
									dialog.setVisible(true);
									dialog.adjustProgress(intCurrent, intTotal);
								}
								else {
									// done
									dialog.setVisible(false);
								}
							}
						}
						
						// print to console
						System.out.println(thisPrintLine);
					} */
					
					// dialog.setVisible(false);
					
				} catch (Exception e1) {
					// do nothing
					System.out.println("Failed to run the program");
				}
				


				
				// code to export to csv
				/*
				
				RWsimOutXML thisSimulationOutput = new RWsimOutXML("SimulationOutput.xml");
				
				thisSimulationOutput.ReadXMLtoSimOdata();
				
				Vector<SimulationOutputData> DataList = thisSimulationOutput.getDataList();
				
				try
				{
					FileWriter thisFileWriter = new FileWriter("output.csv");
					
					for (SimulationOutputData thisData : DataList)
					{
						// extract x y z
						Vector<Vector<Double>> thesePositions = thisData.mPosition;
						
						thisFileWriter.append( thesePositions.toString() );
						
						// next line
						thisFileWriter.append( "\n" );
						
					}
					
					thisFileWriter.flush();
					thisFileWriter.close();
				}
				catch(IOException e3)
				{
					e3.printStackTrace();
				}
				
				*/
				
				
				fireMaintainSelection();
				
			}
		});
		this.add(exportButton, "wrap para");
		
		
		
		
		////////  The simulation table

		exportTableModel = new ColumnTableModel(
				
				/*
				////  Status and warning column
				new Column("") {
					private JLabel label = null;

					@Override
					public Object getValueAt(int row) {
						if (row < 0 || row >= document.getSimulationCount())
							return null;

						// Initialize the label
						if (label == null) {
							label = new StyledLabel(2f);
							label.setIconTextGap(1);
							//							label.setFont(label.getFont().deriveFont(Font.BOLD));
						}

						// Set simulation status icon
						Simulation.Status status = document.getSimulation(row).getStatus();
						label.setIcon(Icons.SIMULATION_STATUS_ICON_MAP.get(status));


						// Set warning marker
						if (status == Simulation.Status.NOT_SIMULATED ||
								status == Simulation.Status.EXTERNAL) {

							label.setText("");

						} else {

							WarningSet w = document.getSimulation(row).getSimulatedWarnings();
							if (w == null) {
								label.setText("");
							} else if (w.isEmpty()) {
								label.setForeground(OK_COLOR);
								label.setText(OK_TEXT);
							} else {
								label.setForeground(WARNING_COLOR);
								label.setText(WARNING_TEXT);
							}
						}

						return label;
					}

					@Override
					public int getExactWidth() {
						return 36;
					}

					@Override
					public Class<?> getColumnClass() {
						return JLabel.class;
					}
				},
				
				*/

				//// Simulation name
				//// Name
				new Column(trans.get("simpanel.col.Name")) {
					@Override
					public Object getValueAt(int row) {
						if (row < 0 || row >= document.getSimulationCount())
							return null;
						return document.getSimulation(row).getName();
					}

					@Override
					public int getDefaultWidth() {
						return 250;
					}

					@Override
					public Comparator getComparator() {
						return new AlphanumComparator();
					}
				},

				//// Simulation configuration
				new Column(trans.get("simpanel.col.Configuration")) {
					@Override
					public Object getValueAt(int row) {
						if (row < 0 || row >= document.getSimulationCount())
							return null;
						Configuration c = document.getSimulation(row).getConfiguration();
						return descriptor.format(c.getRocket(), c.getFlightConfigurationID());
					}

					@Override
					public int getDefaultWidth() {
						return 250;
					}
				}
				
				/*

				//// Launch rod velocity
				new ValueColumn(trans.get("simpanel.col.Velocityoffrod"), UnitGroup.UNITS_VELOCITY) {
					@Override
					public Double valueAt(int row) {
						if (row < 0 || row >= document.getSimulationCount())
							return null;

						FlightData data = document.getSimulation(row).getSimulatedData();
						if (data == null)
							return null;

						return data.getLaunchRodVelocity();
					}

				},

				//// Apogee
				new ValueColumn(trans.get("simpanel.col.Apogee"), UnitGroup.UNITS_DISTANCE) {
					@Override
					public Double valueAt(int row) {
						if (row < 0 || row >= document.getSimulationCount())
							return null;

						FlightData data = document.getSimulation(row).getSimulatedData();
						if (data == null)
							return null;

						return data.getMaxAltitude();
					}
				},

				//// Velocity at deployment
				new ValueColumn(trans.get("simpanel.col.Velocityatdeploy"), UnitGroup.UNITS_VELOCITY) {
					@Override
					public Double valueAt(int row) {
						if (row < 0 || row >= document.getSimulationCount())
							return null;

						FlightData data = document.getSimulation(row).getSimulatedData();
						if (data == null)
							return null;

						return data.getDeploymentVelocity();
					}
				},

				//// Deployment Time from Apogee
				new ValueColumn(trans.get("simpanel.col.OptimumCoastTime"),
						trans.get("simpanel.col.OptimumCoastTime.ttip"),
						UnitGroup.UNITS_SHORT_TIME) {
					@Override
					public Double valueAt(int row) {
						if (row < 0 || row >= document.getSimulationCount())
							return null;

						FlightData data = document.getSimulation(row).getSimulatedData();
						if (data == null || data.getBranchCount() == 0)
							return null;

						double val = data.getBranch(0).getOptimumDelay();
						if ( Double.isNaN(val) ) {
							return null;
						}
						return val;
					}
				},

				//// Maximum velocity
				new ValueColumn(trans.get("simpanel.col.Maxvelocity"), UnitGroup.UNITS_VELOCITY) {
					@Override
					public Double valueAt(int row) {
						if (row < 0 || row >= document.getSimulationCount())
							return null;

						FlightData data = document.getSimulation(row).getSimulatedData();
						if (data == null)
							return null;

						return data.getMaxVelocity();
					}
				},

				//// Maximum acceleration
				new ValueColumn(trans.get("simpanel.col.Maxacceleration"), UnitGroup.UNITS_ACCELERATION) {
					@Override
					public Double valueAt(int row) {
						if (row < 0 || row >= document.getSimulationCount())
							return null;

						FlightData data = document.getSimulation(row).getSimulatedData();
						if (data == null)
							return null;

						return data.getMaxAcceleration();
					}
				},

				//// Time to apogee
				new ValueColumn(trans.get("simpanel.col.Timetoapogee"), UnitGroup.UNITS_FLIGHT_TIME) {
					@Override
					public Double valueAt(int row) {
						if (row < 0 || row >= document.getSimulationCount())
							return null;

						FlightData data = document.getSimulation(row).getSimulatedData();
						if (data == null)
							return null;

						return data.getTimeToApogee();
					}
				},

				//// Flight time
				new ValueColumn(trans.get("simpanel.col.Flighttime"), UnitGroup.UNITS_FLIGHT_TIME) {
					@Override
					public Double valueAt(int row) {
						if (row < 0 || row >= document.getSimulationCount())
							return null;

						FlightData data = document.getSimulation(row).getSimulatedData();
						if (data == null)
							return null;

						return data.getFlightTime();
					}
				},

				//// Ground hit velocity
				new ValueColumn(trans.get("simpanel.col.Groundhitvelocity"), UnitGroup.UNITS_VELOCITY) {
					@Override
					public Double valueAt(int row) {
						if (row < 0 || row >= document.getSimulationCount())
							return null;

						FlightData data = document.getSimulation(row).getSimulatedData();
						if (data == null)
							return null;

						return data.getGroundHitVelocity();
					}
				}
				
				*/

				) {
			/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

			@Override
			public int getRowCount() {
				return document.getSimulationCount();
			}
		};

		// Override processKeyBinding so that the JTable does not catch
		// key bindings used in menu accelerators
		simulationTable = new ColumnTable(exportTableModel) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected boolean processKeyBinding(KeyStroke ks,
					KeyEvent e,
					int condition,
					boolean pressed) {
				return false;
			}
		};
		ColumnTableRowSorter simulationTableSorter = new ColumnTableRowSorter(exportTableModel);
		simulationTable.setRowSorter(simulationTableSorter);
		simulationTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		simulationTable.setDefaultRenderer(Object.class, new JLabelRenderer());
		exportTableModel.setColumnWidths(simulationTable.getColumnModel());


		// Mouse listener to act on double-clicks
		simulationTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
					int selectedRow = simulationTable.getSelectedRow();
					if (selectedRow < 0) {
						return;
					}
					int selected = simulationTable.convertRowIndexToModel(selectedRow);

					int column = simulationTable.columnAtPoint(e.getPoint());
					if (column == 0) {
						SimulationWarningDialog.showWarningDialog(panelCamRockSim.this, document.getSimulations().get(selected));
					} else {

						simulationTable.clearSelection();
						simulationTable.addRowSelectionInterval(selectedRow, selectedRow);

						openDialog(document.getSimulations().get(selected));
					}
				} else {
					updateButtonStates();
				}
			}
		});
		
		
		document.addDocumentChangeListener(new DocumentChangeListener() {
			@Override
			public void documentChanged(DocumentChangeEvent event) {
				if (!(event instanceof SimulationChangeEvent))
					return;
				exportTableModel.fireTableDataChanged();
			}
		});
		



		// Fire table change event when the rocket changes
		document.getRocket().addComponentChangeListener(new ComponentChangeListener() {
			@Override
			public void componentChanged(ComponentChangeEvent e) {
				fireMaintainSelection();
			}
		});


		JScrollPane scrollpane = new JScrollPane(simulationTable);
		this.add(scrollpane, "spanx, grow, wrap rel");

		updateButtonStates();
	}
	
	private class RunningDialog extends JDialog {
		/**
		 *  TODO: cancel button
		 */
		private static final long serialVersionUID = 1L;
		private JProgressBar progress = new JProgressBar();
		private boolean isDone = false;

		private RunningDialog() {
			super(null, "Running Simulation", ModalityType.APPLICATION_MODAL);
			
			JPanel panel = new JPanel(new MigLayout("fill"));
			panel.add(new JLabel("Running .."), "wrap para");
			
			progress.setIndeterminate(false);
			progress.setMinimum(0);
			
			panel.add(progress, "growx");
			
			this.add(panel);
			this.pack();
			// this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
			this.setLocationByPlatform(true);
			GUIUtil.setWindowIcons(this);
		}
		
		public void adjustProgress(int currentValue, int totalValue) {
			
			progress.setMaximum(totalValue);
			progress.setValue(currentValue);
			
			if (totalValue == currentValue) {
				isDone = true;
			}
			
		}
		
		public boolean isDone() {
			return this.isDone;
		}
		
	}

	
	private void updateButtonStates() {
		int[] selection = simulationTable.getSelectedRows();
		if (selection.length == 0) {
			editButton.setEnabled(false);
			runButton.setEnabled(false);
			deleteButton.setEnabled(false);
			plotButton.setEnabled(false);
			exportButton.setEnabled(false);
		} else {
			if (selection.length > 1) {
				plotButton.setEnabled(false);
			} else {
				plotButton.setEnabled(true);
			}
			editButton.setEnabled(true);
			runButton.setEnabled(true);
			deleteButton.setEnabled(true);
			exportButton.setEnabled(true);
		}

	}
	
	
	/// when the simulation tab is selected this run outdated simulated if appropriate.
	public void activating(){
		if( ((Preferences) Application.getPreferences()).getAutoRunSimulations()){
			int nSims = simulationTable.getRowCount();
			int outdated = 0;
			if (nSims == 0) {
				return;
			}
			
			for (int i = 0; i < nSims; i++) {
				Simulation.Status s = document.getSimulation(simulationTable.convertRowIndexToModel(i)).getStatus();
				if((s==Simulation.Status.NOT_SIMULATED) ||
						(s==Simulation.Status.OUTDATED)){
					outdated++;
				}	
			}
			if(outdated>0){
				Simulation[] sims = new Simulation[outdated];
				
				int index=0;
				for (int i = 0; i < nSims; i++) {
					int t = simulationTable.convertRowIndexToModel(i);
					Simulation s = document.getSimulation(t);
					if((s.getStatus()==Status.NOT_SIMULATED)||(s.getStatus()==Status.OUTDATED)){
						sims[index] = s;
						index++;
					}
				}
	
				long t = System.currentTimeMillis();
				new SimulationRunDialog(SwingUtilities.getWindowAncestor(
						panelCamRockSim.this), document, sims).setVisible(true);
				log.info("Running simulations took " + (System.currentTimeMillis() - t) + " ms");
				fireMaintainSelection();
			}
		}
	}

	public ListSelectionModel getSimulationListSelectionModel() {
		return simulationTable.getSelectionModel();
	}

	private void openDialog(boolean plotMode, final Simulation... sims) {
		SimulationEditDialog d = new SimulationEditDialog(SwingUtilities.getWindowAncestor(this), document, sims);
		if (plotMode) {
			d.setPlotMode();
		}
		d.setVisible(true);
		fireMaintainSelection();
	}

	private void openDialog(final Simulation sim) {
		boolean plotMode = false;
		if (sim.hasSimulationData() && (sim.getStatus() == Status.UPTODATE || sim.getStatus() == Status.EXTERNAL)) {
			plotMode = true;
		}
		openDialog(plotMode, sim);
	}

	private void fireMaintainSelection() {
		int[] selection = simulationTable.getSelectedRows();
		exportTableModel.fireTableDataChanged();
		for (int row : selection) {
			if (row >= exportTableModel.getRowCount())
				break;
			simulationTable.addRowSelectionInterval(row, row);
		}
	}

	private enum SimulationTableColumns {

	}

	private class JLabelRenderer extends DefaultTableCellRenderer {

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {

			if (row < 0 || row >= document.getSimulationCount())
				return super.getTableCellRendererComponent(table, value,
						isSelected, hasFocus, row, column);

			row = table.getRowSorter().convertRowIndexToModel(row);

			// A JLabel is self-contained and has set its own tool tip
			if (value instanceof JLabel) {
				JLabel label = (JLabel) value;
				if (isSelected)
					label.setBackground(table.getSelectionBackground());
				else
					label.setBackground(table.getBackground());
				label.setOpaque(true);

				label.setToolTipText(getSimulationToolTip(document.getSimulation(row)));
				return label;
			}

			Component component = super.getTableCellRendererComponent(table, value,
					isSelected, hasFocus, row, column);

			if (component instanceof JComponent) {
				((JComponent) component).setToolTipText(getSimulationToolTip(
						document.getSimulation(row)));
			}
			return component;
		}

		private String getSimulationToolTip(Simulation sim) {
			String tip;
			FlightData data = sim.getSimulatedData();

			tip = "<html><b>" + sim.getName() + "</b><br>";
			switch (sim.getStatus()) {
			case UPTODATE:
				tip += trans.get("simpanel.ttip.uptodate") + "<br>";
				break;

			case LOADED:
				tip += trans.get("simpanel.ttip.loaded") + "<br>";
				break;

			case OUTDATED:
				tip += trans.get("simpanel.ttip.outdated") + "<br>";
				break;

			case EXTERNAL:
				tip += trans.get("simpanel.ttip.external") + "<br>";
				return tip;

			case NOT_SIMULATED:
				tip += trans.get("simpanel.ttip.notSimulated");
				return tip;
			}

			if (data == null) {
				tip += trans.get("simpanel.ttip.noData");
				return tip;
			}
			WarningSet warnings = data.getWarningSet();

			if (warnings.isEmpty()) {
				tip += trans.get("simpanel.ttip.noWarnings");
				return tip;
			}

			tip += trans.get("simpanel.ttip.warnings");
			for (Warning w : warnings) {
				tip += "<br>" + w.toString();
			}

			return tip;
		}
		
	}
	
	// return new File(MyClass.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());



	
}
