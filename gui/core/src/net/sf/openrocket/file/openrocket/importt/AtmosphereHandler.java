package net.sf.openrocket.file.openrocket.importt;

import java.util.HashMap;
import java.util.Vector;

import org.xml.sax.SAXException;

import net.sf.openrocket.aerodynamics.WarningSet;
import net.sf.openrocket.camrocksim.AtmosphereData;
import net.sf.openrocket.file.DocumentLoadingContext;
import net.sf.openrocket.file.simplesax.AbstractElementHandler;
import net.sf.openrocket.file.simplesax.ElementHandler;
import net.sf.openrocket.file.simplesax.PlainTextHandler;
import net.sf.openrocket.simulation.SimulationOptions;

class AtmosphereHandler extends AbstractElementHandler {
	@SuppressWarnings("unused")
	private final DocumentLoadingContext context;
	private final String model;
	
	
	
	public String name = null;
	public Vector<Double> Altitude,
			Xwind,
			Ywind,
			Zwind,
			rho,
			Theta;
	
	public AtmosphereHandler(String model, DocumentLoadingContext context) {
		this.model = model;
		this.context = context;
	}
	
	@Override
	public ElementHandler openElement(String element, HashMap<String, String> attributes,
			WarningSet warnings) {
		return PlainTextHandler.INSTANCE;
	}
	
	@Override
	public void closeElement(String element, HashMap<String, String> attributes,
			String content, WarningSet warnings) throws SAXException {
		
		if (element.equals("name")) {
			// name
			name = content;
		} else if (element.equals("altitude")) {
			// altitude
			Altitude = DstringToVector(content);
		} else if (element.equals("xwind")) {
			Xwind = DstringToVector(content);
		} else if (element.equals("ywind")) {
			Ywind = DstringToVector(content);
		} else if (element.equals("zwind")) {
			Zwind = DstringToVector(content);
		} else if (element.equals("rho")) {
			rho = DstringToVector(content);
		} else if (element.equals("theta")) {
			Theta = DstringToVector(content);
		} else {
			super.closeElement(element, attributes, content, warnings);
		}
	}
	
	
	public void storeSettings(SimulationOptions cond, WarningSet warnings) {
		
		AtmosphereData atmosphereData = new AtmosphereData(true);
		
		// TODO: implement test to see if valid
		
		atmosphereData.name = this.name;
		atmosphereData.Altitude = this.Altitude;
		atmosphereData.Xwind = this.Xwind;
		atmosphereData.Ywind = this.Ywind;
		atmosphereData.Zwind = this.Zwind;
		atmosphereData.rho = this.rho;
		atmosphereData.Theta = this.Theta;
		
		cond.atmosphereData = atmosphereData;
	}
	
	//*Function do split a data string into a vector of doubles
	private Vector<Double> DstringToVector(String Dstring) {
		String[] SplitString = Dstring.split(",");
		Vector<Double> Temp = new Vector<Double>();
		
		for (String S : SplitString) {
			Temp.add(Double.valueOf(S));
		}
		return (Temp);
	}
	
	//and back
	private String VectorToDstring(Vector<Double> Vec) {
		String Temp = "";
		for (double d : Vec) {
			Temp += (Double.toString(d) + ", ");
		}
		Temp = Temp.substring(0, (Temp.length() - 2)); // trim off  the last comma
		
		return (Temp);
	}
	
}