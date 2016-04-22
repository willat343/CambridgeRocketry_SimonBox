package net.sf.openrocket.rocketcomponent;

/**
 * A component internal to the rocket.  Internal components have no effect on the
 * the aerodynamics of a rocket, only its mass properties (though the location of the
 * components is not enforced to be within external components).  Internal components 
 * are always attached relative to the parent component, which can be internal or
 * external, or absolutely.
 * 
 * @author Sampo Niskanen <sampo.niskanen@iki.fi>
 */
public abstract class InternalComponent extends RocketComponent {
	
	public InternalComponent() {
		super(RocketComponent.Position.BOTTOM);
	}
	
	
	@Override
	public final void setRelativePosition(RocketComponent.Position position) {
		super.setRelativePosition(position);
		fireComponentChangeEvent(ComponentChangeEvent.MASS_CHANGE);
	}
	
	
	@Override
	public final void setPositionValue(double value) {
		super.setPositionValue(value);
		fireComponentChangeEvent(ComponentChangeEvent.MASS_CHANGE);
	}
	
	@Override
	public final double getAbsolutePosition() {
		// return absolute position..
		// change relative position (is ok, for internal components)
		this.setRelativePosition(RocketComponent.Position.ABSOLUTE);
		
		// return the position (now set relative)
		return this.getPositionValue();
	}
	
	/**
	 * Non-aerodynamic components.
	 * @return <code>false</code>
	 */
	@Override
	public final boolean isAerodynamic() {
		return false;
	}
	
	/**
	 * Is massive.
	 * @return <code>true</code>
	 */
	@Override
	public final boolean isMassive() {
		return true;
	}
	
	/*
	
	@Override
	public RockPartsData getRockPartsData() {
		
		// extract data
		double thisMass = this.getMass();
		double thisPosition = this.getAbsolutePosition();
		double thisRadialPosition = 0.0;
		String thisName = this.getName();
		
		// create camrock component (model as mass)
		PointMassData thisPointMassData = new PointMassData(
				thisMass, thisPosition, thisRadialPosition, thisName);
		
		// add component to main stage
		return (RockPartsData) thisPointMassData;
	}
	
	*/
}
