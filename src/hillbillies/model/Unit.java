package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

import java.util.Arrays;

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
 * @invar  	  The workActivity of each Unit must be a valid workActivity for any
 *         	  Unit.
 *     	    | isValidWorkActivity(getWorkActivity())
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
		this.setName(name);
		this.position = this.new Position(initialPosition);
		this.initializeAttribute("w", weight);
		this.initializeAttribute("a", agility);
		this.initializeAttribute("s", strength);
		this.initializeAttribute("t", toughness);
		this.setCurrentHitPoints(this.getMaxHitPoints());
		this.setStamina(this.getMaxStaminaPoints());
		
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
		public Position(int[] cubeCoordinates) throws IllegalCoordinateException {
			this.setUnitCoordinates(cubeCoordinates);
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
			double[] unitCoordinates = getDoubleArrayFromIntArray(cubeCoordinates);
			if (! isValidPosition(unitCoordinates))
				throw new IllegalCoordinateException(unitCoordinates);
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
		 * @param coordinates
		 *         	  The coordinates to check.
		 * @return 	  True if all coordinates are within range, false otherwise.
		 *       	| result ==
		 *       	|	(coordinates[0] >= 0) && (coordinates[0] < 50) &&
		 *       	|	(coordinates[1] >= 0) && (coordinates[1] < 50) &&
		 *       	| 	(coordinates[2] >= 0) && (coordinates[2] < 50)
		 */
		public boolean isValidPosition(double[] coordinates) {
			return (
					(coordinates[0] >= 0 && coordinates[0] < 50) &&
							(coordinates[1] >= 0 && coordinates[1] < 50) &&
							(coordinates[2] >= 0 && coordinates[2] < 50)
			);
		}

		public int[] getCubeCoordinates() {
			return new int[] {this.cubeX, this.cubeY, this.cubeZ};
		}

		public void setOccupyingCubeCoordinates(int[] cubeCoordinates) {
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
		if (Math.abs(targetPosition[0]) - Math.abs(initialPosition[0]) <= 0 &&
				Math.abs(targetPosition[1]) - Math.abs(initialPosition[1]) <= 0 &&
				Math.abs(targetPosition[2]) - Math.abs(initialPosition[2]) <= 0) {
			this.setState(State.NONE);
			this.position.setUnitCoordinates(new int[]{initialCube[0] + targetPosition[0],
					initialCube[1] + targetPosition[1],
					initialCube[2] + targetPosition[2]}
			);
			initialPosition = new double[]{0, 0, 0};
			this.stopSprinting();
		}
	}

	private int[] targetPosition = new int[] {0, 0, 0};
	private double[] initialPosition = new double[]{0, 0, 0};
	private int[] initialCube;

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
	 * @throws IllegalStateException
	 * 			  when the unit can't move when the it is attacked
	 * @throws IllegalArgumentException
	 * 			  When the given cube coordinates aren't from a neighbouring cube of this unit.
	 * 
	 */
	public void moveToAdjacent(int dx, int dy, int dz) throws IllegalArgumentException {
		if (this.getState() != State.MOVING) {
			initialCube = this.position.getCubeCoordinates();
			if (this.getState() == State.DEFENDING)
				return;
			targetPosition = new int[]{dx, dy, dz};
			this.setOrientation((float) Math.atan2(this.getUnitVelocity()[1], this.getUnitVelocity()[0]));
			this.setState(State.MOVING);
		}
	}

	private double[] getUnitVelocity() {
		double distance =
				Math.sqrt(
						Math.pow(this.targetPosition[0], 2) +
						Math.pow(this.targetPosition[1], 2) +
						Math.pow(this.targetPosition[2], 2)
				);

		return new double[]
				{
					this.getUnitWalkSpeed() * (this.targetPosition[0])/ distance,
					this.getUnitWalkSpeed() * (this.targetPosition[1])/ distance,
					this.getUnitWalkSpeed() * (this.targetPosition[2])/ distance,
				};
	}

	/**
	 * @param 	  targetposition
	 *
	 * @post 	  the new position must be the target positon
	 * 			| this.Position.getPositon() == targetposition
	 *
	 */
	public void moveTo(int[] targetposition) throws IllegalCoordinateException {
		if (! this.position.isValidPosition(this.position.getDoubleArrayFromIntArray(targetposition))) {
			throw new IllegalCoordinateException(this.position.getDoubleArrayFromIntArray(targetposition));
		}
//		if (this.getState() == State.DEFENDING) {
//			return;
//		}

		int cubeX;
		int cubeY;
		int cubeZ;

		while(! Arrays.equals(position.getCubeCoordinates(), targetposition)) {
//			if(this.state != State.MOVING)
//				break;
			if(position.getCubeCoordinates()[0]== targetposition[0]){
				cubeX = 0;
			}else if(position.getCubeCoordinates()[0]< targetposition[0]){
				cubeX = 1;
			}else{
				cubeX = -1;
			}

			if(position.getCubeCoordinates()[1]== targetposition[1]){
				cubeY = 0;
			}else if(position.getCubeCoordinates()[1]< targetposition[1]){
				cubeY = 1;
			}else{
				cubeY = -1;
			}

			if(position.getCubeCoordinates()[2]== targetposition[2]){
				cubeZ = 0;
			}else if(position.getCubeCoordinates()[2]< targetposition[2]){
				cubeZ = 1;
			}else{
				cubeZ = -1;
			}

			moveToAdjacent(cubeX, cubeY, cubeZ);
			while (this.getState() == State.MOVING) {

			}
		}
	}

	/**
	 * @return 	  the base speed of a unit determined by the unit's weight, strength and agility
	 * 			| result == 1.5*((strength + agility)/(200*(weight/100))
	 */
	public double getUnitBaseSpeed(){
		return (1.5*(this.getStrength()+this.getAgility())/200*this.getWeight()/100);
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
			moveSpeed = 1.2* getUnitBaseSpeed();
		else
			moveSpeed = getUnitBaseSpeed();
		if(isSprinting)
			moveSpeed = 2*moveSpeed;
		return moveSpeed;
	}

	/**
	 * @post 	  if the unit is sprinting do nothing
	 * 
	 * @post 	  if the unit isn't sprinting set sprinting to true
	 * 			| if(! isSprinting)
	 * 			|	then new.isSprinting == true
	 * @throws IllegalStateException
	 * 			  if the unit's stamina is lower or equals to zero
	 * 			| if (!(this.getCurrentStaminaPoints >= 0))
	 * 
	 */
	public void startSprinting() throws IllegalStateException{
		if(this.getState()==State.MOVING)
			this.isSprinting = true;
	}
	/**
	 * @post 	  if the unit isn't sprinting do nothing
	 * 
	 * @post 	  if the unit is sprinting set sprinting to true
	 * 			| if(! isSprinting)
	 * 			|	then new.isSprinting == false
	 *  
	 */
	public void stopSprinting(){
		this.isSprinting = false;
	}
	public boolean isSprinting(){
		return this.isSprinting;
	}
	private boolean isSprinting = false;

	/**
	 * @post 	  the state of the unit is set to work
	 * 			| new.setState(work) == work
	 * 
	 * @effect 	  set the state of the unit to work
	 * 			| this.setState(work)
	 * 
	 * @throws IllegalStateException
	 * 			  The unit is attacking or defending
	 * 			| if(this.getState() == State.ATTACKING || this.getState() == State.DEFENDING)
	 */	
	public void work() throws IllegalStateException {
		if(this.getState() == State.ATTACKING || this.getState() == State.DEFENDING)
			throw new IllegalStateException();
		workCounter = this.getTimeForWork();
		this.setState(State.WORKING);
	}
	/**
	 * 
	 * @return 	  gives the time its takes for a work state
	 * 			| result == 500/this.strength 		
	 * 
	 */
	public float getTimeForWork(){
		return 500/this.getStrength();
	}
	
	/**
	 * @post 	  the state of the unit is set to work
	 * 			| new.setState(rest) == rest
	 * @post 	  while the hitpoints not larger or equals the maxHitpoints then
	 * 		 	  add every REGEN_REST_TIME second toughness/200 hitpoints
	 * 		 	  else check the stamina points if they are not the
	 * 		 	  maximun add every REGEN_REST_TIME tougness/100 stanmina points
	 * 			| while (this.getCurrentHitPoints() <= this.maxHitPoints)
	 * 			| 		then advanceTime(REGEN_REST_TIME)
	 * 			| while (this.getCurrentStaminaPoints <= this.maxStaminaPoints())
	 * 			|		then advaceTime(REGEN_REST_TIME)
	 * 			|
	 * 
	 * @effect 	  set the state of the unit to work
	 * 			| this.setState(rest)
	 * 
	 * @throws IllegalStateException
	 * 			  When a unit has the maximum hitpoints and the maximum of stamina
	 * 			  the unit can't rest
	 * 			| if(this.getCurrentHitPoints() == this.maxHitPoints())
	 * 			| && ( this.getCurrentStaminaPoints == this.getMaxStaminaPoints())
	 */
	public void rest()throws IllegalStateException{
		if( this.getCurrentHitPoints() == this.getMaxHitPoints() && this.getCurrentStaminaPoints() == this.getMaxStaminaPoints())
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
	 * 			| result == (this.toughness)/200
	 */
	public double getRegenHitPoints(){
		return (this.getToughness()/200.0);
	}
	/**
	 * @return 	  gives the amount Stamina points need to regenerate per time unit of REGEN_REST_TIME
	 * 			| restult == (this.toughness)/100
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
	public int getStrength() {
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
	public void setStrength(int strength) {
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
	public int getMinAttributeValue(){
		return 1;
	}
	
	/**
	 * @return	  the maximum number of weight, agility, strength,toughness
	 */
	public int getMaxAttributeValue(){
		return 200;
	}
	
	/**
	 * Variable registering the strength of this unit.
	 */
	private int strength;

	/**
	 * Returns the current agility of this unit.
	 *
	 * @return	  Current agility of this unit.
	 * 			| result == this.agility
	 */
	@Basic
	public int getAgility() {
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
	public void setAgility(int agility) {
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
	private int agility;

	/**
	 * @return 	  Returns the current weight of the unit.
	 * 			| result == this.weight
	 */
	public int getWeight(){
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
	public void setWeight(int weight){
		if(weight <= minWeight())
			weight = minWeight();
		this.weight = weight;
	}
	
	/**
	 * @return 	  the minWeight of a unit
	 * 			| (strength+agility)/2
	 */
	public int minWeight(){
		return (this.strength+this.agility)/2;
	}

	private int weight;

	/**
	 * Returns the current toughness of this unit.
	 *
	 * @return	  Current toughness of this unit.
	 * 			| result == this.toughness
	 */
	@Basic
	public int getToughness() {
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
	public void setToughness(int toughness) {
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
	private int toughness;

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
	 * @pre 	  The given state has to be a valid state.
	 * 			| isvalidAcivity(state)
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
	 * @post  	  if it is sprinting till it's exhausted
	 * 
	 * @throws IllegalStateException
	 * 			  if the unit is doing a state
	 * @throws IllegalStateException
	 * 			  if setDefaultBehaviorEnabled is false
	 */
	public void startDefaultBehaviour() throws IllegalStateException{

	}
	/**
	 * @post 	  Stop the Default state and set the current state on null
	 * 
	 * @throws IllegalStateException
	 * 			  if a unit doesn't conduct an state
	 */
	public void stopDefaultBehavior() throws IllegalStateException{
		
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
	 */
	public int getCurrentHitPoints(){
		return this.hitPoints;
	}

	/**
	 * @param 	  hitpoints
	 * 			  the amount of hitpoints need to be set
	 * @post 	  the hitpoints must be valid
	 * 			| isValidHitPoints(hitpoints)
	 * 
	 */
	public void setCurrentHitPoints(int hitpoints){
		assert isValidHitPoints(hitpoints);
		this.hitPoints = hitpoints;
	}

	/**
	 * @param 	  hitpoints
	 * 
	 * @return 	  true if the amounts of hitpoints larger or equals to zero
	 *            and is smaller or equals to the maximum hitpoints the unit can have
	 *         	| return if( (0 <= hitpoints) && ( hitpoints <= this.maxHitPoints())
	 */
	public boolean isValidHitPoints(int hitpoints){
		if(hitpoints <= this.getMaxHitPoints() && hitpoints >=getMinHitPoints())
			return true;
		return false;
	}

	/**
	 * @return 	  returns the maximum hitpoints the unit can have
	 * 			| result == 200.(this.weight/100).(this.toughness/100)
	 */
	public int getMaxHitPoints(){
		return ((int) Math.ceil(200.0 * (this.getWeight()/100.0) * (this.getToughness()/100.0)));
	}
	public int getMinHitPoints(){
		return 0;
	}
	
	private int hitPoints;
	
	/**
	 * Return the stamina of this Unit.
	 *
	 * @return	| result == this.stamina
	 */
	@Basic @Raw
	public int getCurrentStaminaPoints() {
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
	public boolean isValidStamina(int stamina) {
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
	public void setStamina(int stamina) {
	  assert isValidStamina(stamina);
	  this.stamina = stamina;
	}

	/**
	 * Returns the maximum amount of stamina points for any Unit.
	 *
	 * @return 	| result == 200*(this.weight/100)*(this.toughness/100)
     */
	public int getMaxStaminaPoints() {
		return ((int) Math.ceil(200.0 * (this.getWeight()/100.0) * (this.getToughness()/100.0)));
	}
	public int getMinStaminaPoints(){
		return 0;
	}
	


	
	/**
	 * Variable registering the stamina of this Unit.
	 */
	private int stamina;

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
	 * @note 	  To-do: add orientation update.
	 */
	public void attack(Unit defender) throws IllegalStateException {
		this.setState(State.ATTACKING);
		defender.defend(this.getAgility(), this.getStrength());
	}

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
	 * @note 	  To-do: add orientation update.
	 */
	public void defend(int attackerAgility, int attackerStrength) {
		this.setState(State.DEFENDING);
		// TODO: Write complete implementation
	}

	private double sprintCounter = 0.1;
	private double restCounter = 0.2;
	private double workCounter;

	public void advanceTime(double dt) {
		if (this.getState() == State.MOVING) {
			if (this.isSprinting()) {
				this.sprintCounter -= dt;
				if (this.sprintCounter <= 0) {
					if (this.getCurrentStaminaPoints() <= 0) {
						this.stopSprinting();
					} else {
						this.setStamina(this.getCurrentStaminaPoints() - 1);
						this.sprintCounter = 0.1;
					}
				}
			}
			this.updatePosition(dt);
		}
		if (this.getState() == State.RESTING) {
			if (this.getCurrentHitPoints() == this.getMaxHitPoints()) {
				this.restCounter -= dt;
				if (this.restCounter <= 0) {
					this.setStamina(this.getCurrentStaminaPoints() + (int) Math.ceil(this.getRegenStamina()));
					this.restCounter = 0.2;
				}
			} else {
				this.restCounter -= dt;
				if (this.restCounter <= 0) {
					this.setCurrentHitPoints(this.getCurrentHitPoints() + (int) Math.ceil(this.getRegenHitPoints()));
					this.restCounter = 0.2;
				}
				if (this.getCurrentHitPoints() == this.getMaxHitPoints()) {
					if (this.restCounter <= 0) {
						this.setStamina(this.getCurrentStaminaPoints() + (int) Math.ceil(this.getRegenStamina()));
						this.restCounter = 0.2;
					}
				}
			}
			if (this.getCurrentStaminaPoints() == this.getMaxStaminaPoints()) {
				this.setState(State.NONE);
			}
		}
		if (this.getState() == State.WORKING) {
			this.workCounter -= dt;
			if (this.workCounter <= 0) {
				this.setState(State.NONE);
			}
		}
	}
}