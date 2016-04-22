/**
 * 
 */
package net.sf.openrocket.file.openrocket;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;



/**
 * @author will
 *
 */
public class CustomRocketMotor {
	private final double smallTime = 0.001;
	
	// class members
	private String fileName = ""; // filename stored as
	private String motorName = ""; // This is the common name of the motor, as you would like to see it listed in your simulator program.
	private double diameter = 50; // This is the casing diameter in millimeters (mm).
	private double length = 100; // This is the casing length, also in millimeters.
	private double delay = 0; // This is the list of available delays, separated by dashes. If the motor has an ejection charge but no delay use "0" and if it has no ejection charge at all use "P" (plugged).
	private double propellantWeight = 1; // The weight of all consumables in the motor. For solid motors this is simply the propellant itself, but for hybrids it is the fuel grain(s) plus the oxidizer (such as N2O). This weight is expressed in kilograms (Kg).
	private double totalWeight = 4; // The weight of the motor loaded and ready for flight, also in kilograms.
	private String manufacturer = "Custom"; // The motor manufacturer abbreviated to a few letters.
	// thrust curve time / thrust pair
	private Vector<Double> theseTime = new Vector<Double>(); // seconds
	private Vector<Double> theseThrust = new Vector<Double>(); // Newtons
	// functions
	
	public void setName(String motorName) {
		// 2 birds - 1 stone
		this.fileName = motorName;
		this.motorName = motorName;
	}
	
	public void setDiameter(double diameter) {
		// diameter of the motor
		this.diameter = diameter;
	}
	
	public void setLength(double length) {
		// length of the motor
		this.length = length;
	}
	
	public void setDelay(double delay) {
		// indicates the ejection charge
		this.delay = delay;
	}
	
	public void setPropellantWeight(double propellantWeight) {
		// weight of propellant only [kg]
		this.propellantWeight = propellantWeight;
	}
	
	public void setTotalWeight(double totalWeight) {
		// total weight of the motor at start [kg]
		this.totalWeight = totalWeight;
	}
	
	public void addPoint(double time, double thrust) {
		// time: add a time point
		// thrust: corresponding thrust-point
		
		// first point (0,0) is implied, don't add this
		if (!((theseTime.size() == 0) && (time < smallTime))) {
			this.theseTime.add(time);
			this.theseThrust.add(thrust);
		}
	}
	
	public Vector<Double> getTime() {
		// returns time
		this.checkThrustCurve(); // check validity
		return theseTime;
	}
	
	public Vector<Double> getThrust() {
		// return thrust
		this.checkThrustCurve();
		return theseThrust;
	}
	
	private void checkThrustCurve() {
		// check that final point has zero thrust, otherwise add it
		if (this.theseThrust.lastElement() != 0) {
			this.theseTime.add(theseTime.lastElement() + smallTime);
			this.theseThrust.addElement(0.0);
		}
		
		if (theseThrust.size() != theseTime.size()) {
			System.out.println("Warning: Houston, we have a problem.");
		}
	}
	
	
	public void printMotorToFile(String filePath) {
		// print information to a file as specified by the filepath
		
		String outputPath = filePath + "/" + this.fileName + ".eng";
		
		Vector<Double> thisVectorTime = this.getTime();
		Vector<Double> thisVectorThrust = this.getThrust();
		
		try {
			FileWriter thisFileWriter = new FileWriter(outputPath);
			
			// comments
			thisFileWriter.append("; Custom engine RASP ENG format\n");
			
			// first line
			thisFileWriter.append(this.motorName + " " + this.diameter + " " +
					this.length + " " + this.delay + " " + this.propellantWeight + " " +
					this.totalWeight + " " + this.manufacturer + "\n");
			
			for (int i = 0; i < theseTime.size(); i++) {
				// write thrust curve
				thisFileWriter.append(theseTime.get(i) + " " + theseThrust.get(i) + "\n");
				
			}
			// thrust curve - first point (0,0) is implied
			// final point of thrust must be zero
			
			thisFileWriter.flush();
			thisFileWriter.close();
		} catch (IOException e3) {
			e3.printStackTrace();
		}
		
	}
	
}
