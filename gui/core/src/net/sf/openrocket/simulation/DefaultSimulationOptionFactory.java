package net.sf.openrocket.simulation;


import com.google.inject.Inject;

import net.sf.openrocket.startup.Preferences;

public class DefaultSimulationOptionFactory {
	
	@Inject
	private Preferences prefs;
	
	public static final String SIMCONDITION_WIND_SPEED = "SimConditionWindSpeed";
	public static final String SIMCONDITION_WIND_STDDEV = "SimConditionWindStdDev";
	public static final String SIMCONDITION_WIND_TURB = "SimConditionWindTurb";
	public static final String SIMCONDITION_SITE_LAT = "SimConditionSiteLat";
	public static final String SIMCONDITION_SITE_LON = "SimConditionSiteLon";
	public static final String SIMCONDITION_SITE_ALT = "SimConditionSiteAlt";
	public static final String SIMCONDITION_ATMOS_STD = "SimConditionsAtmosStd";
	public static final String SIMCONDITION_ATMOS_TEMP = "SimConditionsAtmosTemp";
	public static final String SIMCONDITION_ATMOS_PRESSURE = "SimConditionsAtmosPres";
	public static final String SIMCONDITION_ROD_INTO_WIND = "SimConditionsRodIntoWind";
	public static final String SIMCONDITION_ROD_LENGTH = "SimConditionsRodLength";
	public static final String SIMCONDITION_ROD_ANGLE = "SimConditionsRodAngle";
	public static final String SIMCONDITION_ROD_DIRECTION = "SimConditionsRodDirection";
	
	// camrocksim
	public static final String SIMCONDITION_MONTE_BOOL = "MonteCarloBool";
	public static final String SIMCONDITION_MONTE_INT = "MonteCarloInteger";
	
	public static final String SIMCONDITION_ATMOSPHERE_STR = "AtmosphereString";
	
	public DefaultSimulationOptionFactory(Preferences prefs) {
		this.prefs = prefs;
	}
	
	public DefaultSimulationOptionFactory() {
		prefs = null;
	}
	
	public SimulationOptions getDefault() {
		SimulationOptions defaults = new SimulationOptions(null);
		if (prefs != null) {
			
			defaults.setWindSpeedAverage(prefs.getDouble(SIMCONDITION_WIND_SPEED, defaults.getWindSpeedAverage()));
			defaults.setWindSpeedDeviation(prefs.getDouble(SIMCONDITION_WIND_STDDEV, defaults.getWindSpeedDeviation()));
			defaults.setWindTurbulenceIntensity(prefs.getDouble(SIMCONDITION_WIND_TURB, defaults.getWindTurbulenceIntensity()));
			
			defaults.setLaunchLatitude(prefs.getDouble(SIMCONDITION_SITE_LAT, defaults.getLaunchLatitude()));
			defaults.setLaunchLongitude(prefs.getDouble(SIMCONDITION_SITE_LON, defaults.getLaunchLongitude()));
			defaults.setLaunchAltitude(prefs.getDouble(SIMCONDITION_SITE_ALT, defaults.getLaunchAltitude()));
			
			defaults.setISAAtmosphere(prefs.getBoolean(SIMCONDITION_ATMOS_STD, defaults.isISAAtmosphere()));
			defaults.setLaunchTemperature(prefs.getDouble(SIMCONDITION_ATMOS_TEMP, defaults.getLaunchTemperature()));
			defaults.setLaunchPressure(prefs.getDouble(SIMCONDITION_ATMOS_PRESSURE, defaults.getLaunchTemperature()));
			
			defaults.setLaunchIntoWind(prefs.getBoolean(SIMCONDITION_ROD_INTO_WIND, defaults.getLaunchIntoWind()));
			defaults.setLaunchRodLength(prefs.getDouble(SIMCONDITION_ROD_LENGTH, defaults.getLaunchRodLength()));
			defaults.setLaunchRodAngle(prefs.getDouble(SIMCONDITION_ROD_ANGLE, defaults.getLaunchRodAngle()));
			defaults.setLaunchRodDirection(prefs.getDouble(SIMCONDITION_ROD_DIRECTION, defaults.getLaunchRodDirection()));
			
			// camrocksim defaults
			defaults.setMonteCarloBool(prefs.getBoolean(SIMCONDITION_MONTE_BOOL, defaults.getMonteCarloBool()));
			defaults.setMonteCarloInteger(prefs.getDouble(SIMCONDITION_MONTE_INT, defaults.getMonteCarloInteger()));
			
			defaults.setAtmosphereString(prefs.getString(SIMCONDITION_ATMOSPHERE_STR, defaults.getAtmosphereString()));
			
		}
		return defaults;
	}
	
	public void saveDefault(SimulationOptions newDefaults) {
		
		prefs.putDouble(SIMCONDITION_WIND_SPEED, newDefaults.getWindSpeedAverage());
		prefs.putDouble(SIMCONDITION_WIND_STDDEV, newDefaults.getWindSpeedDeviation());
		prefs.putDouble(SIMCONDITION_WIND_TURB, newDefaults.getWindTurbulenceIntensity());
		
		prefs.putDouble(SIMCONDITION_SITE_LAT, newDefaults.getLaunchLatitude());
		prefs.putDouble(SIMCONDITION_SITE_LON, newDefaults.getLaunchLongitude());
		prefs.putDouble(SIMCONDITION_SITE_ALT, newDefaults.getLaunchAltitude());
		prefs.putBoolean(SIMCONDITION_ATMOS_STD, newDefaults.isISAAtmosphere());
		prefs.putDouble(SIMCONDITION_ATMOS_TEMP, newDefaults.getLaunchTemperature());
		prefs.putDouble(SIMCONDITION_ATMOS_PRESSURE, newDefaults.getLaunchPressure());
		
		prefs.putBoolean(SIMCONDITION_ROD_INTO_WIND, newDefaults.getLaunchIntoWind());
		prefs.putDouble(SIMCONDITION_ROD_LENGTH, newDefaults.getLaunchRodLength());
		prefs.putDouble(SIMCONDITION_ROD_ANGLE, newDefaults.getLaunchRodAngle());
		prefs.putDouble(SIMCONDITION_ROD_DIRECTION, newDefaults.getLaunchRodDirection());
		
		prefs.putBoolean(SIMCONDITION_MONTE_BOOL, newDefaults.getMonteCarloBool());
		prefs.putDouble(SIMCONDITION_MONTE_INT, newDefaults.getMonteCarloInteger());
		
		prefs.putString(SIMCONDITION_ATMOSPHERE_STR, newDefaults.getAtmosphereString());
		
		
	}
}
