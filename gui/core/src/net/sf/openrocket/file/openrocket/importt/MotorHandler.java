package net.sf.openrocket.file.openrocket.importt;

import java.util.HashMap;
import java.util.Locale;
import java.util.Vector;

import org.xml.sax.SAXException;

import net.sf.openrocket.aerodynamics.Warning;
import net.sf.openrocket.aerodynamics.WarningSet;
import net.sf.openrocket.file.DocumentLoadingContext;
import net.sf.openrocket.file.simplesax.AbstractElementHandler;
import net.sf.openrocket.file.simplesax.ElementHandler;
import net.sf.openrocket.file.simplesax.PlainTextHandler;
import net.sf.openrocket.motor.Manufacturer;
import net.sf.openrocket.motor.Motor;
import net.sf.openrocket.motor.ThrustCurveMotor;
import net.sf.openrocket.util.Coordinate;

class MotorHandler extends AbstractElementHandler {
	/** File version where latest digest format was introduced */
	private static final int MOTOR_DIGEST_VERSION = 104;
	
	private final DocumentLoadingContext context;
	private Motor.Type type = null;
	private String manufacturer = null;
	private String designation = null;
	private String digest = null;
	private double diameter = Double.NaN;
	private double length = Double.NaN;
	private double delay = Double.NaN;
	private double[] time;
	private double[] thrust;
	
	private double loadedMass;
	private double dryMass;
	
	public MotorHandler(DocumentLoadingContext context) {
		this.context = context;
	}
	
	
	@Override
	public ElementHandler openElement(String element, HashMap<String, String> attributes,
			WarningSet warnings) {
		return PlainTextHandler.INSTANCE;
	}
	
	
	/**
	 * Return the motor to use, or null.
	 */
	public Motor getMotor(WarningSet warnings) {
		// TODO HIGH add motor if not found!
		
		Manufacturer thisManufacturer = new Manufacturer(this.manufacturer,
				this.manufacturer, Motor.Type.UNKNOWN);
		
		Motor.Type thisType = Motor.Type.UNKNOWN;
		
		double[] delays = { 0 }; // no delays
		
		double weightLaunch = this.loadedMass;
		double weightEmpty = this.dryMass;
		
		Coordinate[] cg = new Coordinate[this.time.length];
		
		for (int i = 0; i < time.length; i++) {
			Coordinate thisCoordinate = null;
			if (i == 0) {
				// start
				thisCoordinate = new Coordinate(0, 0, 0, weightLaunch);
			} else if (i == (time.length - 1)) {
				// end
				thisCoordinate = new Coordinate(0, 0, 0, weightEmpty);
			} else {
				// irrelevant
				thisCoordinate = new Coordinate(0, 0, 0, weightLaunch);
			}
			cg[i] = thisCoordinate;
		}
		
		
		ThrustCurveMotor thisThrustCurveMotor = new ThrustCurveMotor(
				thisManufacturer, designation, "", thisType,
				delays, diameter, length, time, thrust, cg,
				digest);
		
		Motor thisMotor = (Motor) thisThrustCurveMotor;
		
		return thisMotor;
	}
	
	/**
	 * Return the delay to use for the motor.
	 */
	public double getDelay(WarningSet warnings) {
		if (Double.isNaN(delay)) {
			warnings.add(Warning.fromString("Motor delay not specified, assuming no ejection charge."));
			return Motor.PLUGGED;
		}
		return delay;
	}
	
	@Override
	public void closeElement(String element, HashMap<String, String> attributes,
			String content, WarningSet warnings) throws SAXException {
		
		content = content.trim();
		
		if (element.equals("type")) {
			
			// Motor type
			type = null;
			for (Motor.Type t : Motor.Type.values()) {
				if (t.name().toLowerCase(Locale.ENGLISH).equals(content.trim())) {
					type = t;
					break;
				}
			}
			if (type == null) {
				warnings.add(Warning.fromString("Unknown motor type '" + content + "', ignoring."));
			}
			
		} else if (element.equals("manufacturer")) {
			
			// Manufacturer
			manufacturer = content.trim();
			
		} else if (element.equals("designation")) {
			
			// Designation
			designation = content.trim();
			
		} else if (element.equals("digest")) {
			
			// Digest is used only for file versions saved using the same digest algorithm
			if (context.getFileVersion() >= MOTOR_DIGEST_VERSION) {
				digest = content.trim();
			}
			
		} else if (element.equals("loadedMass")) {
			loadedMass = Double.NaN;
			try {
				loadedMass = Double.parseDouble(content.trim());
			} catch (NumberFormatException e) {
				// Ignore
			}
		} else if (element.equals("dryMass")) {
			dryMass = Double.NaN;
			try {
				dryMass = Double.parseDouble(content.trim());
			} catch (NumberFormatException e) {
				// Ignore
			}
		} else if (element.equals("diameter")) {
			
			
			// Diameter
			diameter = Double.NaN;
			try {
				diameter = Double.parseDouble(content.trim());
			} catch (NumberFormatException e) {
				// Ignore
			}
			if (Double.isNaN(diameter)) {
				warnings.add(Warning.fromString("Illegal motor diameter specified, ignoring."));
			}
			
		} else if (element.equals("length")) {
			
			// Length
			length = Double.NaN;
			try {
				length = Double.parseDouble(content.trim());
			} catch (NumberFormatException ignore) {
			}
			
			if (Double.isNaN(length)) {
				warnings.add(Warning.fromString("Illegal motor diameter specified, ignoring."));
			}
			
		} else if (element.equals("delay")) {
			
			// Delay
			delay = Double.NaN;
			if (content.equals("none")) {
				delay = Motor.PLUGGED;
			} else {
				try {
					delay = Double.parseDouble(content.trim());
				} catch (NumberFormatException ignore) {
				}
				
				if (Double.isNaN(delay)) {
					warnings.add(Warning.fromString("Illegal motor delay specified, ignoring."));
				}
				
			}
			
		} else if (element.equals("time")) {
			try {
				this.time = DstringToArray(content);
			} catch (NumberFormatException ignore) {
			}
		} else if (element.equals("thrust")) {
			try {
				this.thrust = DstringToArray(content);
			} catch (NumberFormatException ignore) {
			}
		} else {
			super.closeElement(element, attributes, content, warnings);
		}
	}
	
	private double[] DstringToArray(String Dstring) {
		return VectorToArray(DstringToVector(Dstring));
	}
	
	private Vector<Double> DstringToVector(String Dstring) {
		String[] SplitString = Dstring.split(",");
		Vector<Double> Temp = new Vector<Double>();
		
		for (String S : SplitString) {
			Temp.add(Double.valueOf(S));
		}
		return (Temp);
	}
	
	private double[] VectorToArray(Vector<Double> inputVector) {
		
		int total = inputVector.size();
		
		double[] outputArray = new double[total];
		
		int i = 0;
		for (double d : inputVector) {
			
			outputArray[i] = d;
			
			i++;
		}
		
		return outputArray;
	}
	
}