package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

import java.util.Arrays;
import java.util.Random;

/**
 * A class for a cubical object that occupies a position in the game world.
 * 
 * @author 	  Iwein Bau & Joppe Geluykens
 *
 * @note 	  A Unit is a basic type of in-game character with the ability to move around,
 * 			  interact with other such characters and manipulate the game world.
 * 
 * @invar 	  The state is always equals to a valid state.
 * 		  	| isValidAcivity(state)
 * @invar 	  The amount of hitpoints is always a valid amount
 * 		  	| isValidHitPoints(hitpoints)
 * @invar  	  The stamina of each Unit must be a valid stamina for any
 *         	  Unit.
 *        	| isValidStamina(getCurrentStaminaPoints())
 * @invar  	  The name of each Unit must be a valid name for any
 *         	  Unit.
 *        	| isValidName(getName())
 */
public class Unit {

	/**
	 * Initialize this new unit with a name, initial position,
	 * weight, agility, strength, toughness and whether default behavior
	 * is enabled or not.
	 *
	 * @param name                  The name of the unit.
	 * @param initialPosition       The initial position of the unit, as an array with 3 elements
	 *                              {unitX, unitY, unitZ}.
	 * @param weight                The initial weight of the unit
	 * @param agility               The initial agility of the unit
	 * @param strength              The initial strength of the unit
	 * @param toughness             The initial toughness of the unit
	 * @param enableDefaultBehavior Whether the default behavior of the unit is enabled
	 * @post The new unit is initialized with the given name if it is valid.
	 * | if (isValidName(name))
	 * |	new.getName() == name
	 * @post the unit it's weight, agility, strength,toughness has to be in between MIN_START_PARAM and MAX_START_PARAM
	 * @post The unit's toughness is set to the given toughness if it is in range,
	 * otherwise, toughness is
	 * either set to the given toughness modulo the range
	 * (when given toughness > this.getMaxInitialAttributeValue()),
	 * or set to the minimum initial attribute value
	 * (when given toughness < this.getMinInitialAttributeValue()).
	 * | if (toughness >= this.getMinInitialAttributeValue()) && (toughness <= this.getMaxInitialAttributeValue())
	 * | 	then new.getToughness() == toughness
	 * | else if (toughness < this.getMinInitialAttributeValue())
	 * | 	then new.getToughness() == this.getMinInitialAttributeValue()
	 * | else if (toughness > this.getMaxInitialAttributeValue())
	 * | 	then new.getToughness() == this.getMinInitialAttributeValue() + ((toughness - getMinInitialAttributeValue()) %
	 * |									(this.getMaxInitialAttributeValue() - this.getMinInitialAttributeValue))
	 * @effect ...
	 */
	public Unit(String name, int[] initialPosition, int weight, int agility, int strength, int toughness, boolean enableDefaultBehavior) {
		try {
			this.setName(name);
		} catch (IllegalArgumentException exc) {
			this.setName("\"Billy The Hill\"");
		}
		this.position = this.new Position(initialPosition);
		this.initializeAttribute("w", weight);
		this.initializeAttribute("a", agility);
		this.initializeAttribute("s", strength);
		this.initializeAttribute("t", toughness);
		this.setCurrentHitPoints(this.getMaxHitPoints());
		this.setStamina(this.getMaxStaminaPoints());
		this.setDefaultBehaviorEnabled(enableDefaultBehavior);
	}

	private void initializeAttribute(String attributeKind, int attributeValue) {
		if (attributeValue >= this.getMinInitialAttributeValue() &&
				attributeValue <= this.getMaxInitialAttributeValue()) {
			switch (attributeKind) {
				case "w":
					this.setWeight(attributeValue);
					break;
				case "a":
					this.setAgility(attributeValue);
					break;
				case "s":
					this.setStrength(attributeValue);
					break;
				case "t":
					this.setToughness(attributeValue);
					break;
			}
		} else if (attributeValue < this.getMinInitialAttributeValue()) {
			switch (attributeKind) {
				case "w":
					this.setWeight(this.getMinInitialAttributeValue());
					break;
				case "a":
					this.setAgility(this.getMinInitialAttributeValue());
					break;
				case "s":
					this.setStrength(this.getMinInitialAttributeValue());
					break;
				case "t":
					this.setToughness(this.getMinInitialAttributeValue());
					break;
			}
		} else if (attributeValue > this.getMaxInitialAttributeValue()) {
			switch (attributeKind) {
				case "w":
					this.setWeight(
							this.getMinInitialAttributeValue()
							+ ((attributeValue - this.getMinInitialAttributeValue())
							% (this.getMaxInitialAttributeValue() - this.getMinInitialAttributeValue()))
					);
					break;
				case "a":
					this.setAgility(
							this.getMinInitialAttributeValue()
							+ ((attributeValue - this.getMinInitialAttributeValue())
							% (this.getMaxInitialAttributeValue() - this.getMinInitialAttributeValue()))
					);
					break;
				case "s":
					this.setStrength(
							this.getMinInitialAttributeValue()
							+ ((attributeValue - this.getMinInitialAttributeValue())
							% (this.getMaxInitialAttributeValue() - this.getMinInitialAttributeValue()))
					);
					break;
				case "t":
					this.setToughness(
							this.getMinInitialAttributeValue()
							+ ((attributeValue - this.getMinInitialAttributeValue())
							% (this.getMaxInitialAttributeValue() - this.getMinInitialAttributeValue()))
					);
					break;
			}
		}
	}

	public int getMinInitialAttributeValue() {
		return Unit.MIN_INITIAL_ATTRIBUTE_VALUE;
	}

	private static final int MIN_INITIAL_ATTRIBUTE_VALUE = 25;

	public int getMaxInitialAttributeValue() {
		return Unit.MAX_INITIAL_ATTRIBUTE_VALUE;
	}

	private static final int MAX_INITIAL_ATTRIBUTE_VALUE = 100;

	/**
	 * A nested class in Unit for maintaining its position.
	 *
	 * @invar  	  The unit coordinates of each unit position must be valid
	 * 			  unit coordinates for any unit position.
	 *       	| isValidPosition(this.getUnitCoordinates())
	 *
	 * @note 	  All of these functions should be worked out defensively.
	 *
	 */
	public class Position {
		/**
		 * Initialize this new unit position with given cube coordinates.
		 *
		 * @param cubeCoordinates
		 *            The coordinates of the cube that this new unit position is occupying.
		 * @effect 	  Calls this.setUnitCoordinates and this.setOccupyingCubeCoordinates
		 * 			  with given cubeCoordinates.
		 *       	| this.setOccupyingCubeCoordinates(cubeCoordinates)
		 *       	| this.setUnitCoordinates(cubeCoordinates)
		 */
		public Position(int[] cubeCoordinates){
			try {
				this.setUnitCoordinates(cubeCoordinates);
			} catch (IllegalCoordinateException exc) {
				System.out.println("++++++++++ "+exc.getMessage()+" ++++++++++");
			}
		}

		/**
		 * Return the unit coordinates of this unit position.
		 */
		@Basic @Raw
		public double[] getUnitCoordinates() {
			return new double[] {this.unitX, this.unitY, this.unitZ};
		}

		/**
		 * Set the unit coordinates of this unit position to the sum of
		 * the given cube coordinates and 1/2 of a cube side.
		 *
		 * @param cubeCoordinates
		 *         	  The cube coordinates of this unit position.
		 * @post 	  The unit coordinates of this new unit position are equal to
		 *         	  the given cube coordinates + 1/2 of a cube side for unitX and unitY,
		 *         	  equal to the given cube coordinate for unitZ.
		 *        	| new.getUnitCoordinates() ==
		 *        	|	{
		 *        	|	  (cubeCoordinates[0] + 1/2 * cubeSideLength),
		 *        	| 	  (cubeCoordinates[1] + 1/2 * cubeSideLength),
		 *        	| 	  (cubeCoordinates[2])
		 *        	| 	}
		 * @throws IllegalCoordinateException
		 *         	  The given coordinates are not valid coordinates for any
		 *         	  position.
		 *       	| ! isValidPosition(cubeCoordinates)
		 */
		@Raw
		private void setUnitCoordinates(int[] cubeCoordinates) throws IllegalCoordinateException {
			if(!isValidPosition(cubeCoordinates))
				throw new IllegalCoordinateException(cubeCoordinates);
			double[] unitCoordinates = getDoubleArrayFromIntArray(cubeCoordinates);
			this.setOccupyingCubeCoordinates(cubeCoordinates);
			double halfCubeSideLength = cubeSideLength / 2;
			this.unitX = unitCoordinates[0] + halfCubeSideLength;
			this.unitY = unitCoordinates[1] + halfCubeSideLength;
			this.unitZ = unitCoordinates[2];
		}

		/**
		 * Variable registering the length of a cube side in meters.
		 */
		public final double cubeSideLength = 1;

		/**
		 * Variables registering the unit coordinates of this unit position.
		 */
		private double unitX, unitY, unitZ;

		/**
		 * Check whether the given coordinates are valid coordinates for
		 * any position.
		 *
		 * @param cubeCoordinates
		 *         	  The coordinates to check.
		 * @return 	  True if all coordinates are within range, false otherwise.
		 *       	| result ==
		 *       	|	(coordinates[0] >= 0) && (coordinates[0] < 50) &&
		 *       	|	(coordinates[1] >= 0) && (coordinates[1] < 50) &&
		 *       	| 	(coordinates[2] >= 0) && (coordinates[2] < 50)
		 */
		public boolean isValidPosition(int[] cubeCoordinates) {
			return (
					(cubeCoordinates[0] >= 0 && cubeCoordinates[0] < 50) &&
							(cubeCoordinates[1] >= 0 && cubeCoordinates[1] < 50) &&
							(cubeCoordinates[2] >= 0 && cubeCoordinates[2] < 50)
			);
		}

		public int[] getCubeCoordinates() {
			return new int[] {this.cubeX, this.cubeY, this.cubeZ};
		}

		private void setOccupyingCubeCoordinates(int[] cubeCoordinates) {
			this.cubeX = cubeCoordinates[0];
			this.cubeY = cubeCoordinates[1];
			this.cubeZ = cubeCoordinates[2];
		}

		private int cubeX, cubeY, cubeZ;

		/**
		 * Returns a copy of a given array of ints as an array of doubles.
		 *
		 * @param arrayOfInts
		 * 			  The int array to transform.
		 * @return	| (double[]) arrayOfInts
		 */
		public double[] getDoubleArrayFromIntArray(int[] arrayOfInts) {
			double[] doubleArrayFromIntArray = new double[arrayOfInts.length];
			for (int i = 0; i < arrayOfInts.length; i++) {
				doubleArrayFromIntArray[i] = (double) arrayOfInts[i];
			}
			return doubleArrayFromIntArray;
		}
	}

	/**
	 * Variable registering the current unit position of this unit.
	 */
	public Unit.Position position;

	private void updatePosition(double dt) {
		this.position.unitX += this.getUnitVelocity()[0] * dt;
		this.initialPosition[0] += this.getUnitVelocity()[0] * dt;
		this.position.unitY += this.getUnitVelocity()[1] * dt;
		this.initialPosition[1] += this.getUnitVelocity()[1] * dt;
		this.position.unitZ += this.getUnitVelocity()[2] * dt;
		this.initialPosition[2] += this.getUnitVelocity()[2] * dt;
		if (Math.abs(neighboringCubeToMoveTo[0]) - Math.abs(initialPosition[0]) <= 0 &&
				Math.abs(neighboringCubeToMoveTo[1]) - Math.abs(initialPosition[1]) <= 0 &&
				Math.abs(neighboringCubeToMoveTo[2]) - Math.abs(initialPosition[2]) <= 0) {
			this.position.setUnitCoordinates(new int[]{
					this.position.getCubeCoordinates()[0] + neighboringCubeToMoveTo[0],
					this.position.getCubeCoordinates()[1] + neighboringCubeToMoveTo[1],
					this.position.getCubeCoordinates()[2] + neighboringCubeToMoveTo[2]
			});
			if (Arrays.equals(this.position.getCubeCoordinates(), targetPosition)) {
				this.setState(State.NONE);
				this.stopSprinting();
			}
			if(newTargetPosition != null)
				targetPosition = newTargetPosition;
				newTargetPosition = null;
			initialPosition = new double[]{0, 0, 0};
		}
	}

	// Rommelhoop
	private int[] targetPosition = new int[] {0, 0, 0};
	/*
	 * Relative difference in current position vs target position.
	 *
	 */
	private double[] initialPosition = new double[]{0, 0, 0};
	private int[] neighboringCubeToMoveTo = new int[]{0, 0, 0};
	private int[] newTargetPosition = null;

	/**
	 * @param dx
	 * 			  X coordinate of neighbouring cube to move to.
	 * @param dy
	 * 			  Y coordinate of neighbouring cube to move to.
	 * @param dz
	 * 			  Z coordinate of neighbouring cube to move to.
	 * @post 	  the unit moves to an adjacent cube of the current one
	 * 			| new.position.getUnitCoordinates() == targetPosition
	 * @post 	  the orientation must be set to atan2(vy,vx)
	 * 			| new.getOrientation() == atan2(vy,vx)
	 * @throws IllegalArgumentException
	 * 			  When the given cube coordinates aren't from a neighbouring cube of this unit.
	 * 
	 */
	public void moveToAdjacent(int dx, int dy, int dz) throws IllegalArgumentException {
		if(!this.position.isValidPosition(new int[]{
				this.position.getCubeCoordinates()[0]+dx,
				this.position.getCubeCoordinates()[1]+dy,
				this.position.getCubeCoordinates()[2]+dz})) {
			throw new IllegalArgumentException();
		}

		if (dx == 0 && dy == 0 && dz == 0) {
			return;
		}
		if (this.getState()== State.MOVING)
			return;

		// Absolute target position
		this.targetPosition = new int[]{
				this.position.getCubeCoordinates()[0] + dx,
				this.position.getCubeCoordinates()[1] + dy,
				this.position.getCubeCoordinates()[2] + dz
		};
		this.setState(State.MOVING);
	}

	private double[] getUnitVelocity() {
		double distance =
				Math.sqrt(
						Math.pow(this.neighboringCubeToMoveTo[0], 2) +
						Math.pow(this.neighboringCubeToMoveTo[1], 2) +
						Math.pow(this.neighboringCubeToMoveTo[2], 2)
				);

		return new double[]
				{
					this.getUnitWalkSpeed() * (this.neighboringCubeToMoveTo[0])/ distance,
					this.getUnitWalkSpeed() * (this.neighboringCubeToMoveTo[1])/ distance,
					this.getUnitWalkSpeed() * (this.neighboringCubeToMoveTo[2])/ distance,
				};
	}

	/**
	 * @param 	  targetposition
	 *
	 * @post 	  the new position must be the target position if it's not moving and set the State on moving
	 * 			| this.Position.getPositon() == targetposition
	 * 			| new.setState(State.MOVING)
	 * @post      if the unit is moving set the newTargetPosition equals to targetposition
	 *
	 */
	public void moveTo(int[] targetposition) throws IllegalCoordinateException {
		if (! this.position.isValidPosition(targetposition)) {
			throw new IllegalCoordinateException(targetposition);
		}
		if(this.getState()== State.MOVING){
			this.newTargetPosition = targetposition;
			return;
		}
		this.targetPosition = targetposition;
		this.setState(State.MOVING);
	}

	/**
	 * @return 	  the base speed of a unit determined by the unit's weight, strength and agility
	 * 			| result == 1.5*((this.getStrength() + this.getAgility)/(200*(this.getWeight/100))
	 */
	public double getUnitBaseSpeed(){
		return 1.5*((this.getStrength()+this.getAgility())/(200*this.getWeight()/100));
	}
	/**
	 * 			  the position were the unit will go
	 * @return 	  if the unit is lower than the targetposition then the speed will be
	 * 			  0.5*getUnitBaseSpeed()
	 * 			  if the unit is higher then the targetposition the speed will be
	 * 			  1.2 * getUnitBaseSpeed()
	 * 			  if a unit is sprinting return the getUnitWalkSpeed *2
	 * 			  else is the speed equals as getUnitBaseSpeed()
	 * 			| private double totalSpeed == getUnitBaseSpeed;
	 * 			| if( position[2] - targetposition[2] = -1)
	 * 			| 	then totalSpeed == 0.5*getUnitBaseSpeed()
	 * 			| if( position[2] - targetposition[2] = 1)
	 * 			|	then totalSpeed == 1.2* getUnitBaseSpeed()
	 * 			| if( getSprinting())
	 * 			|	then totalSpeed *2
	 * 			| result == totalSpeed
	 */
	public double getUnitWalkSpeed(){
		double moveSpeed;
		if((targetPosition[2]) == -1)
			moveSpeed = 0.5*getUnitBaseSpeed();
		else if((targetPosition[2]) == 1)
			moveSpeed =1.2* getUnitBaseSpeed();
		else
			moveSpeed = getUnitBaseSpeed();
		if(isSprinting)
			moveSpeed = 2*moveSpeed;
		return moveSpeed;
	}

	/**
	 * @post 	  if the unit is not moving do nothing
	 * 
	 * @post 	  if the unit is moving set sprinting to true
	 * 			| if(this.getState()==State.MOVING)
	 * 			|	then new.isSprinting == true
	 * 
	 */
	
	public void startSprinting(){
		if(this.getState()==State.MOVING)
			this.isSprinting = true;
	}
	
	/**
	 * @post 	  if the unit isn't sprinting do nothing
	 * 
	 * @post 	  if the unit is sprinting set sprinting to false
	 * 			| new.isSprinting == false
	 *  
	 */
	
	public void stopSprinting(){
		this.isSprinting = false;
	}
	/**
	 * 
	 * @return	  true if the unit is sprinting else return false
	 * 			| result == this.isSprinting
	 * 
	 */
	
	public boolean isSprinting(){
		return this.isSprinting;
	}
	
	private boolean isSprinting = false;

	/**
	 * @post 	  the state of the unit is set to work
	 * 			| new.setState(State.WORKING)
	 * 
	 * @effect 	  set the state of the unit to work
	 * 			| this.setState(State.WORKING)
	 * 
	 * @throws IllegalStateException
	 * 			  The unit is attacking or defending
	 * 			| if(this.getState() == State.ATTACKING)
	 */	
	public void work() throws IllegalStateException {
		if(this.getState() != State.NONE)
			throw new IllegalStateException();
		WORK_COUNTER = this.getTimeForWork();
		this.setState(State.WORKING);
	}
	/**
	 * 
	 * @return 	  gives the time its takes for a working
	 * 			| result == 500/this.getStrength 		
	 * 
	 */
	public float getTimeForWork(){
		return (float) (500/this.getStrength());
	}
	
	/**
	 * @post 	  the state of the unit is set to rest
	 * 			| new.setState(State.RESTING)
	 * 
	 * @effect 	  set the state of the unit to work
	 * 			| this.setState(State.RESTING)
	 * 
	 * @throws IllegalStateException
	 * 			  When a unit has the maximum hitpoints and the maximum of stamina
	 * 			  the unit can't rest
	 * 			| if(this.getCurrentHitPoints() == this.maxHitPoints())
	 * 			| && ( this.getCurrentStaminaPoints == this.getMaxStaminaPoints())
	 * @throws IllegalStateException
	 *			  If the unit is currently executing an activity
	 *			| if(this.getState() != State.NONE)
	 *			  
	 */
	
	public void rest()throws IllegalStateException{
		if( this.getCurrentHitPoints() == this.getMaxHitPoints() && this.getCurrentStaminaPoints() == this.getMaxStaminaPoints())
			throw new IllegalStateException();
		if(this.getState() != State.NONE)
			throw new IllegalStateException();
		this.setState(State.RESTING);
	}
	
	/**
	 * variable time need the regen hp and stamina
	 * 
	 */
	
	public static final double REGEN_REST_TIME = 0.2;

	/**
	 * @return 	  gives the amount hitpoinst need to regenerate per time unit of REGEN_REST_TIME
	 * 			| result == (this.getToughness)/200
	 */
	
	public double getRegenHitPoints(){
		return (this.getToughness()/200.0);
	}
	
	/**
	 * @return 	  gives the amount Stamina points need to regenerate per time unit of REGEN_REST_TIME
	 * 			| restult == (this.getToughness)/100
	 */	
	
	public double getRegenStamina(){
		return this.getToughness()/100.0;
	}

	/**
	 * Returns the current orientation of this unit.
	 *
	 * @return 	  Current orientation of this unit.
	 * 			| result == this.orientation
	 */
	
	@Basic
	public float getOrientation() {
		return this.orientation;
	}

	/**
	 * @param 	  orientation
	 * 			  The new orientation for this unit.
	 * @post 	  If the given orientation is within the range of minimum and maximum
	 * 			  orientation values, the orientation of this unit is set to the given orientation.
	 * 			| if (orientation >= this.getMinOrientation()) && (orientation <= this.getMaxOrientation)
	 * 			| 	then new.getOrientation() == orientation
	 * @post	  If the given orientation is less than the minimum or greater than the
	 * 			  maximum orientation value, then the orientation of this unit is set to
	 * 			  the given orientation modulo the range established by the minimum and maximum
	 * 			  orientation values.
	 * 			| if (orientation < this.getMinOrientation) || (orientation > this.getMaxOrientation())
	 * 			| 	then new.getOrientation() ==
	 * 			|		((orientation-getMinOrientation()) % (getMaxOrientation()-getMinOrientation()+1)) + getMinOrientation()
	 */
	
	private void setOrientation(float orientation) {
		if( orientation < this.getMinOrientation() || orientation > this.getMaxOrientation())
			this.orientation = ((orientation -getMinOrientation()) % (getMaxOrientation()-getMinOrientation()+1))+getMinOrientation();
		this.orientation = orientation;
	}

	/**
	 * Returns the minimum orientation value of this unit.
	 *
	 * @return 	  Minimum orientation value of this unit.
	 *  		| result == this.MIN_ORIENTATION
	 */
	
	@Immutable
	private float getMinOrientation() {
		return Unit.MIN_ORIENTATION;
	}

	/**
	 * Constant reflecting the lowest possible value for the orientation of this unit.
	 */
	
	private static final float MIN_ORIENTATION = (float) (- Math.PI / 2);

	/**
	 * Returns the maximum orientation value of this unit.
	 *
	 * @return 	  Maximum orientation value of this unit.
	 *  		| result == this.MAX_ORIENTATION
	 */
	
	@Immutable
	private float getMaxOrientation() {
		return Unit.MAX_ORIENTATION;
	}

	/**
	 * Constant reflecting the highest possible value for the orientation of this unit.
	 */
	
	private static final float MAX_ORIENTATION = (float) (Math.PI / 2);

	/**
	 * Variable registering the orientation of this unit.
	 */
	
	private float orientation = (float) (Math.PI / 2);

	/**
	 * Returns the current strength of this unit.
	 *
	 * @return	  Current strength of this unit.
	 * 			| result == this.strength
	 */
	
	@Basic
	public double getStrength() {
		return this.strength;
	}

	/**
	 * Set the strength for this unit to the given strength.
	 *
	 * @param 	  strength
	 * 			  The new strength for this unit.
	 * @post 	  If the given strength is within the range established by
	 * 			  the minimum and maximum attribute value for this unit,
	 * 			  then the strength of this unit is equal to the given strength.
	 * 			| if (strength >= this.getMinAttributeValue()) && (strength <= this.getMaxAttributeValue())
	 * 			|	then new.getStrength() == strength
	 * @post	  If the given strength exceeds the maximum attribute value,
	 * 			  then the strength of this unit is equal to the maximum attribute value.
	 * 			| if (strength > this.getMaxAttributeValue())
	 * 			| 	then new.getStrength() == this.getMaxAttributeValue()
	 * @post	  If the given strength is lower than the minimum attribute value of this unit,
	 * 			  then the strength of this unit is equal to the minimum attribute value.
	 * 			| if (strength < this.getMinAttributeValue())
	 * 			|	then new.getStrength() == this.getMinAttributeValue()
	 */
	
	public void setStrength(double strength) {
		if (strength >= this.getMinAttributeValue()
				&& strength <= this.getMaxAttributeValue()) {
			this.strength = strength;
		} else if (strength < this.getMinAttributeValue()) {
			this.strength = this.getMinAttributeValue();
		} else if (strength > this.getMaxAttributeValue()) {
			this.strength = this.getMaxAttributeValue();
		}
	}
	
	/**
	 * @return	  the minimum number of weight, agility, strength,toughness
	 */
	
	private double getMinAttributeValue(){
		return 1;
	}
	
	/**
	 * @return	  the maximum number of weight, agility, strength,toughness
	 */
	
	private double getMaxAttributeValue(){
		return 200;
	}
	
	/**
	 * Variable registering the strength of this unit.
	 */
	
	private double strength;

	/**
	 * Returns the current agility of this unit.
	 *
	 * @return	  Current agility of this unit.
	 * 			| result == this.agility
	 */
	
	@Basic
	public double getAgility() {
		return this.agility;
	}

	/**
	 * Set the agility for this unit to the given agility.
	 *
	 * @param 	  agility
	 * 			  The new agility for this unit.
	 * @post 	  If the given agility is within the range established by
	 * 			  the minimum and maximum attribute value for this unit,
	 * 			  then the agility of this unit is equal to the given agility.
	 * 			| if (agility >= this.getMinAttributeValue()) && (agility <= this.getMaxAttributeValue())
	 * 			|	then new.getAgility() == agility
	 * @post	  If the given agility exceeds the maximum attribute value,
	 * 			  then the agility of this unit is equal to the maximum attribute value.
	 * 			| if (agility > this.getMaxAttributeValue())
	 * 			| 	then new.getAgility() == getMaxAttributeValue()
	 * @post	  If the given agility is lower than the minimum attribute value of this unit,
	 * 			  then the agility of this unit is equal to the minimum attribute value.
	 * 			| if (agility < this.getMinAttributeValue())
	 * 			|	then new.getAgility() == this.getMinAttributeValue()
	 */
	
	public void setAgility(double agility) {
		if (agility > this.getMaxAttributeValue())
			this.agility= this.getMaxAttributeValue();
		if (agility < this.getMinAttributeValue())
			this.agility= this.getMinAttributeValue();
		if(agility >= this.getMinAttributeValue() && (agility <= this.getMaxAttributeValue()))
				this.agility=agility;			
	}

	/**
	 * Variable registering the agility of this unit.
	 */
	private double agility;

	/**
	 * @return 	  Returns the current weight of the unit.
	 * 			| result == this.weight
	 */
	
	public double getWeight(){
		return this.weight;
	}

	/**
	 * @param 	  weight
	 *
	 * @post 	  if the given weight larger is then MAX_WEIGHT then weight
	 * 			  is equals to MAX_WEIGHT
	 * 			| if( weight > MAX_WEIGHT)
	 * 			|	then new.weight == MAX_WEIGHT
	 * @post 	  if the given weight smaller is then MIN_WEIGHT
	 * 			  then is new weight equals to MIN_WEIGHT
	 *
	 * @post 	  the weight of a unit must all times be at least minWeight()
	 * 			  if its smaller set the weight equals to minWeight()
	 * @post 	  the new weight is equals as the given weight
	 * 			| new.weight == weight
	 *
	 */
	
	public void setWeight(double weight){
		if(weight <= minWeight())
			weight = minWeight();
		this.weight = weight;
	}
	
	/**
	 * @return 	  the minWeight of a unit
	 * 			| (this.getStrength()+this.getAgility())/2
	 */
	
	public double minWeight(){
		return (this.getStrength()+this.getAgility())/2;
	}

	private double weight;

	/**
	 * Returns the current toughness of this unit.
	 *
	 * @return	  Current toughness of this unit.
	 * 			| result == this.toughness
	 */
	
	@Basic
	public double getToughness() {
		return this.toughness;
	}

	/**
	 * Set the toughness for this unit to the given toughness.
	 *
	 * @param 	  toughness
	 * 			  The new toughness for this unit.
	 * @post 	  If the given toughness is within the range established by
	 * 			  the minimum and maximum attribute value for this unit,
	 * 			  then the toughness of this unit is equal to the given toughness.
	 * 			| if (toughness >= this.getMinAttributeValue()) && (toughness <= this.getMaxAttributeValue())
	 * 			|	then new.getToughness() == toughness
	 * @post	  If the given toughness exceeds the maximum attribute value,
	 * 			  then the toughness of this unit is equal to the max attribute value.
	 * 			| if (toughness > this.getMaxAttributeValue())
	 * 			| 	then new.getToughness() == this.getMaxAttributeValue()
	 * @post	  If the given toughness is lower than the minimum attribute value of this unit,
	 * 			  then the toughness of this unit is equal to the minimum attribute value.
	 * 			| if (toughness < this.getMinAttributeValue())
	 * 			|	then new.getToughness() == this.getMinAttributeValue()
	 */
	
	public void setToughness(double toughness) {
		if (toughness >= this.getMinAttributeValue()
				&& toughness <= this.getMaxAttributeValue()) {
			this.toughness = toughness;
		} else if (toughness < this.getMinAttributeValue()) {
			this.toughness = this.getMinAttributeValue();
		} else if (toughness > this.getMaxAttributeValue()) {
			this.toughness = this.getMaxAttributeValue();
		}
	}

	/**
	 * Variable registering the toughness of this unit.
	 */
	
	private double toughness;

	/**
	 * @return 	  Returns the current state of this unit.
	 * 			| result == this.state
	 */
	
	public State getState(){
		return this.state;
	}

	/**
	 * @param 	  state
	 * 
	 * @post 	  The new state is equal to the given state.
	 * 			| new.getState() == state
	 *
	 */
	
	public void setState(State state) {
		this.state = state;
	}

	private State state = State.NONE;
	
	/**
	 * @post 	  choose a random state move, conduct a work task, rest unil it has full recovered hitpoints and stamina
	 * 			
	 * @post  	  if is moving sprinting till it's exhausted
	 * 
	 * @throws IllegalStateException
	 * 			  if the unit is doing a state
	 */
	
	public void startDefaultBehaviour() throws IllegalStateException{
		if(this.getState()!= State.NONE)
			throw new IllegalStateException();
		int randomBehaviourNumber =new Random().nextInt(5);
		if(randomBehaviourNumber== 0) {
			moveTo(new int[]{new Random().nextInt(50), new Random().nextInt(50), new Random().nextInt(50)});
			startSprinting();
		}
		else if(randomBehaviourNumber== 1||randomBehaviourNumber== 3) {
			work();
		}
		else
			try {
				rest();
			} catch (IllegalStateException exc) {
				startDefaultBehaviour();
			}
	}
	
	/**
	 * @post 	  Stop the Default state and set the current state on NONE
	 * 
	 */
	
	public void stopDefaultBehavior(){
		this.setState(State.NONE);
	}
	
	/**
	 * 
	 * @post 	  Set defaultBehaviorEnabled true if defaultBehaviorEnabled
	 * 			  is false, set to false otherwise.
	 * 			| if this.defaultBehaviorEnabled
	 * 			|	then new.defaultBehaviorEnabled == false
	 * 			| else 
	 * 			|	then new.defaultBehaviorEnabled == true
	 * 
	 */
	
	public void setDefaultBehaviorEnabled(Boolean toggle){
		this.defaultBehaviorEnabled= toggle;
	}
	public Boolean getDefaultBehaviorEnabled(){
		return this.defaultBehaviorEnabled;
	}
	
	private boolean defaultBehaviorEnabled;
	
	/**
	 * @return 	  return the current hitpoints of the unit
	 * 			| Result == this.hitPoints
	 */
	public double getCurrentHitPoints(){
		return this.hitPoints;
	}

	/**
	 * @param 	  hitpoints
	 * 			  The amount of hitpoints need to be set
	 * 
	 * @post 	  The hitpoints must be valid
	 * 			| isValidHitPoints(hitpoints)
	 * 
	 */
	
	public void setCurrentHitPoints(double hitpoints){
		assert isValidHitPoints(hitpoints);
		this.hitPoints = hitpoints;
	}

	/**
	 * @param 	  hitpoints
	 * 
	 * @return 	  True if the amounts of hitpoints larger or equals to zero
	 *            and is smaller or equals to the maximum hitpoints the unit can have
	 *         	| return if( (0 <= hitpoints) && ( hitpoints <= this.maxHitPoints())
	 */
	
	public boolean isValidHitPoints(double hitpoints){
		if(hitpoints <= this.getMaxHitPoints() && hitpoints >=getMinHitPoints())
			return true;
		return false;
	}

	/**
	 * @return 	  returns the maximum hitpoints the unit can have
	 * 			| result == 200.(this.getWeight()/100).(this.getTughness()/100)
	 */
	
	public double getMaxHitPoints(){
		return (200.0 * (this.getWeight()/100.0) * (this.getToughness()/100.0));
	}
	public double getMinHitPoints(){
		return 0;
	}
	
	private double hitPoints;
	
	/**
	 * Return the stamina of this Unit.
	 *
	 * @return	| result == this.stamina
	 */
	
	@Basic @Raw
	public double getCurrentStaminaPoints() {
	  return this.stamina;
	}
	
	/**
	 * Check whether the given stamina is a valid stamina for
	 * any Unit.
	 *  
	 * @param  	  stamina
	 *         	  The stamina to check.
	 * @return 
	 *       	| result == (stamina >= 0) && (stamina <= this.getMaxStaminaPoints())
	 */
	
	public boolean isValidStamina(double stamina) {
		if(stamina <= this.getMaxStaminaPoints() && stamina >= this.getMinStaminaPoints())
			return true;
		return false;
	}
	
	/**
	 * Set the stamina of this Unit to the given stamina.
	 * 
	 * @param  	  stamina
	 *         	  The new stamina for this Unit.
	 * @pre    	  The given stamina must be a valid stamina for any
	 *         	  Unit.
	 *       	| isValidStamina(stamina)
	 * @post   	  The stamina of this Unit is equal to the given
	 *         	  stamina.
	 *       	| new.getCurrentStaminaPoints() == stamina
	 */
	
	@Raw
	public void setStamina(double stamina) {
	  assert isValidStamina(stamina);
	  this.stamina = stamina;
	}

	/**
	 * Returns the maximum amount of stamina points for any Unit.
	 *
	 * @return 	| result == 200*(this.weight/100)*(this.toughness/100)
     */
	
	public double getMaxStaminaPoints() {
		return ( 200.0 * (this.getWeight()/100.0) * (this.getToughness()/100.0));
	}
	public double getMinStaminaPoints(){
		return 0.0;
	}
	
	/**
	 * Variable registering the stamina of this Unit.
	 */
	
	private double stamina;

	/**
	 * Return the name of this Unit.
	 */
	
	@Basic @Raw
	public String getName() {
	  return this.name;
	}

	/**
	 * Check whether the given name is a valid name for
	 * any Unit.
	 *
	 * @param  	  name
	 *         	  The name to check.
	 * @return
	 *       	| result == name.matches("\"?[A-Z]{1}[a-zA-Z'\\s]*\"?")
	 */
	
	public static boolean isValidName(String name) {
	  return name.matches("\"?[A-Z]{1}[a-zA-Z'\\s]+\"?");
	}

	/**
	 * Set the name of this Unit to the given name.
	 *
	 * @param  	  name
	 *         	  The new name for this Unit.
	 * @post   	  The name of this new Unit is equal to
	 *         	  the given name.
	 *       	| new.getName() == name
	 * @throws IllegalArgumentException
	 *         	  The given name is not a valid name for any
	 *         	  Unit.
	 *       	| ! isValidName(getName())
	 */
	
	@Raw
	public void setName(String name) throws IllegalArgumentException {
	  if (! isValidName(name))
	    throw new IllegalArgumentException();
	  this.name = name;
	}

	/**
	 * Variable registering the name of this Unit.
	 */
	
	private String name;

	private boolean isNeighboringCube(int[] cubeCoordinatesOfPossibleNeighbor) {
		if (Math.abs(cubeCoordinatesOfPossibleNeighbor[0] - this.position.getCubeCoordinates()[0]) == 1) {
			if (Arrays.
					stream(new int[]{-1, 0, 1}).
					anyMatch(i -> i == cubeCoordinatesOfPossibleNeighbor[1] - this.position.getCubeCoordinates()[1])) {
				return true;
			}
		}
		if (cubeCoordinatesOfPossibleNeighbor[0] == this.position.getCubeCoordinates()[0]) {
			if (Math.abs(cubeCoordinatesOfPossibleNeighbor[1] - this.position.getCubeCoordinates()[1]) == 1) {
				if (cubeCoordinatesOfPossibleNeighbor[2] == this.position.getCubeCoordinates()[2]) {
					return true;
				}
			} else if (cubeCoordinatesOfPossibleNeighbor[1] == this.position.getCubeCoordinates()[1]) {
				if (Math.abs(cubeCoordinatesOfPossibleNeighbor[2] - this.position.getCubeCoordinates()[2]) == 1) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Attack other unit that occupies the same or a neighbouring cube
	 * of the game world.
	 *
	 * @param 	  defender
	 * 			  The defending unit.
	 * @effect	  This unit fights defender.
	 * 			| fight(this, defender)
	 * @throws IllegalStateException
	 * 			  When the victim is not within reach.
	 * @note 	  Conducting an attack lasts 1s of game time.
	 */
	
	public void attack(Unit defender) throws IllegalStateException {
		if(!(this.isNeighboringCube(defender.position.getCubeCoordinates())))
			throw new IllegalStateException();
		if(defender.getCurrentHitPoints()<=0)
			this.setState(State.NONE);
		else{
		this.FIGHT_COUNTER = this.getFightTime();
		this.setState(State.ATTACKING);
		this.theDefender=defender;
		defender.defend(this.getAgility(), this.getStrength());
		}
	}
	
	/**
	 * 
	 *			  Variable that saves the unit who is defending
	 * 
	 */
	Unit theDefender;
	
	/**
	 * 
	 * 			  Variable that gives if a unit is defending
	 * 
	 */
	private boolean isDefending = false;

	@Basic @Immutable
	private double getFightTime() {
		return fightTime;
	}

	private static final double fightTime = 1;

	/**
	 * When being attacked, this unit defends itself by either
	 * dodging, blocking or taking damage, respectively.
	 *
	 * @param 	  attackerAgility
	 * 			  The agility of the attacking unit.
	 * @param 	  attackerStrength
	 * 			  The strength of the attacking unit.
	 * @post 	  When the agility of this unit is high enough,
	 * 			  relative to the agility of its attacker,
	 * 			  this unit dodges the attack and moves to a
	 * 			  random neighbouring cube.
	 * 			| 1 <= 0.20 * (this.getAgility() / attacker.getAgility())
	 * @post 	  When this unit fails to dodge the attack,
	 * 			  this unit blocks the attack when the sum of it's
	 * 			  strength and agility, relative to those of its attacker
	 * 			  is high enough.
	 * 			| 1 <= 0.25 * ( (this.getStrength() + this.getAgility())
	 * 			|	/ (attacker.getStrength() + attacker.getAgility()) )
	 * @post	  When this unit fails to dodge or block the attack,
	 * 			  this unit's hitpoints are lowered,
	 * 			  relative to the strength of the attacker.
	 * 			| new.getCurrentHitPoints() == this.getCurrentHitPoints() - (attacker.getStrength() / 10)
	 */
	
	public void defend(double attackerAgility, double attackerStrength) {
		this.isDefending = true;
		double chance = Math.random();
		if(chance < this.chanceForDodging(attackerAgility)){
			this.dodge();
		}else{
			chance = Math.random();
			if(chance< this.chanceForBlocking(attackerAgility,attackerStrength)){
				return;
			}else{
				this.setCurrentHitPoints(this.getCurrentHitPoints() - damage(attackerStrength));
			}
		}
		this.saveUnitSate();
		this.setState(State.NONE);
	}
	
	/**
	 * @param 	  strength
	 * 			  the strength of the attacking unit 
	 * @return	  the damage that has to be dealt to the defender
	 * 			| result == strength/10 
	 */
	
	private double damage(double strength){
		return strength/10;
	}
	
	/**
	 * @param	  attackerAgility
	 * 			  The attackers agility
	 * 
	 * @param 	  attackerStrength
	 * 			  attackers strength
	 * 
	 * @return    Gives the chance for a defending unit to block the attack
	 *			| result == 0.25*(this.getAgility() + this.getStrength())/(attackerAgility + attackerStrength)
	 * 			
	 */
	
	private double chanceForBlocking(double attackerAgility, double attackerStrength){
		return 0.25*((this.getAgility() + this.getStrength()) / (attackerAgility + attackerStrength));
	}
	
	/**
	 * @param	  attackerAgility
	 * 			  The attackers agility
	 * 
	 * @return	  Gives the chance for dodging an attack
	 * 			| return 0.2*(this.getAgility()/attackerAgility)
	 */
	
	private double chanceForDodging(double attackerAgility){
		return (0.20*(this.getAgility()/attackerAgility));
	}
	
	/**
	 * 			  This Method set the change the units position when dodging
	 * 
	 * @post 	  The new UnitCoordinates are the coordinates of a randomNeighboringCube
	 * 
	 * @post	  Set the position of the unit to those coordinates
	 * 
	 * 
	 */
	
	private void dodge() {
		int[] randomNeighboringCube = calculateRandomNeighboringCube();
		while (! this.position.isValidPosition(randomNeighboringCube)) {
			randomNeighboringCube = calculateRandomNeighboringCube();
		}
		this.position.setUnitCoordinates(randomNeighboringCube);
	}

	/**
	 * 
	 * 
	 * @return	  This method returns a random calculate a random neighboring cube of the unit
	 * 
	 * 
	 */
	
	private int[] calculateRandomNeighboringCube() {
		return new int[] {
				this.position.getCubeCoordinates()[0] + new int[]{-1, 1}[new Random().nextInt(2)] /*+/- 1*/,
				this.position.getCubeCoordinates()[1] + new int[]{-1, 1}[new Random().nextInt(2)] /*+/- 1*/,
				this.position.getCubeCoordinates()[2]
		};
	}
	
	/**
	 * @param	  previousState
	 * 			  The previous State of the unit
	 * @post	  Set previousState equals to the current State
	 * 			| new.previousState = previousState
	 */
	
	private void setPreviousState(State previousState){
		this.previousState = previousState;
	}
	
	/**
	 * @return	  Gives the units previous State
	 * 			| Result = this.previousState
	 */
	
	private State getPreviousState(){
		return this.previousState;
	}
	
	/**
	 * @param	  previousOrentation
	 * 			  The previous orientation of the unit
	 * @post	  Set previousOrentation equals to the current State
	 * 			| new.previousOrentation = previousOrentation
	 */
	
	private void setPreviousOrientation(float previousOrientation ){
		this.previousOrientation = previousOrientation;
	}
	
	/**
	 * @return	  Gives the units previous orientation
	 * 			| Result = this.previousOrientation
	 */
	
	private float getPreviousOrientation(){
		return this.previousOrientation;
	}
	
	/**
	 * 
	 * 			  Variable that hold the previous State of a unit
	 * 
	 */
	
	private State previousState;
	
	/**
	 * 
	 * 			  Variable that hold the previous orientation of a unit  
	 * 
	 */
	
	private float previousOrientation;
	
	/**
	 * 
	 * 			  Method that saves the State of a unit and saves the orientation of it
	 * 
	 * @post	  previous State is set to the current State
	 * 			| new.setPreviousState(this.getState())
	 * 
	 * @post	  previous Orientation is set to current orientation except when the unit is doing nothing
	 * 			| if(this.getState()!=State.NONE)
	 * 			|	then new.setPreviousOrientation(this.getOrientation())
	 * 
	 */
	
	public void saveUnitSate(){
		this.setPreviousState(this.getState());
		if(this.getState()!=State.NONE)
			this.setPreviousOrientation(this.getOrientation());
	}
	
	/**
	 * 			 
	 * 			 Method that update the units State whit it's previous and also update the orientation 
	 * 
	 * @post	  State is set to the previous State
	 * 			| new.setState(this.getPreviousState())
	 * 
	 * @post	  Orientation is set to previous orientation except when the unit is doing nothing
	 * 			| if(this.getState()!=State.NONE)
	 * 			|	then new.setOrientation(this.getPreviousOrientation())
	 * 
	 */
	
	public void updateUnitState(){
		this.setState(this.getPreviousState());
		if(this.getState()!=State.NONE)
			this.setOrientation(this.getPreviousOrientation());
	}
	
	private void resetCounter(String counter){
		switch (counter) {
		case "SPRINT_COUNTER":
			this.SPRINT_COUNTER = 0.1;
			break;
		case "RESR_COUNTER":
			this.REST_COUNTER = 0.2;
			break;
		case "WORK_COUNTER":
			this.WORK_COUNTER= this.getTimeForWork();
			break;
		case "FIGHT_COUNTER":
			this.FIGHT_COUNTER = this.getFightTime();
			break;
		case "NEEDTOREST_COUNTER":
			this.NEEDTOREST_COUNTER = 3000;
		}
	}
	private double SPRINT_COUNTER = 0.1;
	private double REST_COUNTER = 0.2;
	private double WORK_COUNTER;
	private double FIGHT_COUNTER;
	private double NEEDTOREST_COUNTER = 3000;

	public void advanceTime(double dt) {
		if (this.getState() == State.MOVING) {
			if (this.isSprinting()) {
				this.SPRINT_COUNTER -= dt;
				if (this.SPRINT_COUNTER <= 0) {
					if (this.getCurrentStaminaPoints() <= 0) {
						this.stopSprinting();
					} else {
						this.setStamina(this.getCurrentStaminaPoints() - 1);
						//Reset the SPRINT_COUNTER
						this.resetCounter("SPRINT_COUNTER");
					}
				}
			}
			int dx;
			int dy;
			int dz;
			if(position.getCubeCoordinates()[0]== this.targetPosition[0]){
				dx = 0;
			}else if(position.getCubeCoordinates()[0]< this.targetPosition[0]){
				dx = 1;
			}else{
				dx = -1;
			}

			if(position.getCubeCoordinates()[1]== this.targetPosition[1]){
				dy = 0;
			}else if(position.getCubeCoordinates()[1]< this.targetPosition[1]){
				dy = 1;
			}else{
				dy = -1;
			}

			if(position.getCubeCoordinates()[2]== this.targetPosition[2]){
				dz = 0;
			}else if(position.getCubeCoordinates()[2]< this.targetPosition[2]){
				dz = 1;
			}else{
				dz = -1;
			}
			if(!isDefending)
				this.setOrientation((float) Math.atan2(this.getUnitVelocity()[1], this.getUnitVelocity()[0]));
			this.neighboringCubeToMoveTo = new int[]{dx, dy, dz};
			this.updatePosition(dt);
		}
		if (this.getState() == State.RESTING) {	
			if (this.getCurrentHitPoints() != this.getMaxHitPoints()) {
				this.REST_COUNTER -= dt;
				if (this.REST_COUNTER <= 0) {
					this.setCurrentHitPoints(this.getCurrentHitPoints() + this.getRegenHitPoints());
					// reset the REST_COUNTER
					this.resetCounter("REST_COUNTER");
				}
			} else {
				this.REST_COUNTER -= dt;
				if (this.REST_COUNTER <= 0) {
					this.setStamina(this.getCurrentStaminaPoints() + this.getRegenStamina());
					// reset the REST_COUNTER
					this.resetCounter("REST_COUNTER");
				}
			}
			if (this.getCurrentStaminaPoints() == this.getMaxStaminaPoints() && this.getCurrentHitPoints() == this.getMaxHitPoints()) {
				this.setState(State.NONE);
			}
		}
		if (this.getState() == State.WORKING) {
			this.WORK_COUNTER -= dt;
			if (this.WORK_COUNTER <= 0) {
				this.setState(State.NONE);
				// reset the WORK_COUNTER
				this.resetCounter("WORK_COUNTER");
			}
		}
		if (this.getState() == State.ATTACKING) {
			this.setOrientation((float) Math.atan2(
					this.theDefender.position.getUnitCoordinates()[1] - this.position.getUnitCoordinates()[1],
					this.theDefender.position.getUnitCoordinates()[0] - this.position.getUnitCoordinates()[0]));
			this.theDefender.setOrientation((float) Math.atan2(
					this.position.getUnitCoordinates()[1] - this.theDefender.position.getUnitCoordinates()[1],
					this.position.getUnitCoordinates()[0] - this.theDefender.position.getUnitCoordinates()[0])
			);
			this.FIGHT_COUNTER -= dt;
			if (this.FIGHT_COUNTER <= 0) {
				this.setState(State.NONE);
				this.theDefender.updateUnitState();
				this.theDefender.isDefending= false;
				//reset the FIGHT_COUNTER
				this.resetCounter("FIGHT_COUNTER");
			}
		}
		NEEDTOREST_COUNTER -= dt;
		if (NEEDTOREST_COUNTER <= 0) {
			if (this.getState() != State.ATTACKING) {
				this.setState(State.RESTING);
				//reset the NEEDTOREST_COUNTER
				this.resetCounter("NEEDTOREST_COUNTER");
			}
		}
		if(this.getDefaultBehaviorEnabled()){
			try {
				this.startDefaultBehaviour();
			} catch (IllegalStateException exc) {
	
			}
		}
	}
}