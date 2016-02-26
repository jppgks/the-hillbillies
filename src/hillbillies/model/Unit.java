package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class for a cubical object that occupies a position in the game world.
 * 
 * @author 	  Iwein Bau & Joppe Geluykens
 *
 * @note 	  A Unit is a basic type of in-game character with the ability to move around,
 * 			  interact with other such characters and manipulate the game world.
 * 
 * @invar 	  The activity is always equals to a valid activity.
 * 		  	| isValidAcivity(activity)
 * @invar 	  The amount of hitpoints is always a valid amount
 * 		  	| isValidHitPoints(hitpoints)
 * @invar  	  The stamina of each Unit must be a valid stamina for any
 *         	  Unit.
 *        	| isValidStamina(getStamina())
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
	 *                              {x, y, z}.
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

		this.position.setUnitCoordinates(initialPosition);

		/*
		 * The implementation below of weight, agility and strength has to be total instead of defensive.
		 */

		if (toughness >= this.getMinInitialAttributeValue() &&
				toughness <= this.getMaxInitialAttributeValue()) {
			this.setToughness(toughness);
		} else if (toughness < this.getMinInitialAttributeValue()) {
			this.setToughness(this.getMinInitialAttributeValue());
		} else if (toughness > this.getMaxInitialAttributeValue()) {
			this.setToughness(
					this.getMinInitialAttributeValue()
							+ ((toughness - this.getMinInitialAttributeValue())
							% (this.getMaxInitialAttributeValue() - this.getMinInitialAttributeValue()))
			);
		}

//		//set the weight of the unit
//		if (weight < MIN_START_PARAM)
//			throw new IllegalArgumentException();
//		if (weight > MAX_START_PARAM)
//			throw new IllegalArgumentException();
//		this.setWeight(weight);
//
//		//set the agility of a unit
//		if (agility < MIN_START_PARAM)
//			throw new IllegalArgumentException();
//		if (agility > MAX_START_PARAM)
//			throw new IllegalArgumentException();
//		this.setAgility(agility);
//
//		//set the strength of a unit
//		if (strength < MIN_START_PARAM)
//			throw new IllegalArgumentException();
//		if (strength > MAX_START_PARAM)
//			throw new IllegalArgumentException();
//		this.setStrength(strength);


	}

	public int getMinInitialAttributeValue() {
		return this.MIN_INITIAL_ATTRIBUTE_VALUE;
	}

	private static final int MIN_INITIAL_ATTRIBUTE_VALUE = 25;

	public int getMaxInitialAttributeValue() {
		return this.MAX_INITIAL_ATTRIBUTE_VALUE;
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
		 * Initialize this new unit position to default unit coordinates.
		 *
		 * @post 	  The unit coordinates of this new position are set to 0.5
		 * 			| new.getUnitCoordinates() == {.5, .5, .5}
		 */
		public Position() {
			this.x = cubeSideLength / 2;
			this.y = cubeSideLength / 2;
			this.z = cubeSideLength / 2;
		}

		/**
		 * Initialize this new unit position with given cube coordinates.
		 *
		 * @param cubeCoordinates
		 *            The cube coordinates of this new unit position.
		 * @effect 	  Converts given array of ints to array of doubles
		 * 			  before passing it on to the setter.
		 * 			  The unit coordinates of this new unit position are set to
		 *         	  the given cube coordinate + 1/2 of the cube side length
		 *         	  for each coordinate.
		 *       	| this.setUnitCoordinates(cubeCoordinates)
		 */
		public Position(int[] cubeCoordinates) throws IllegalCoordinateException {
			this.setUnitCoordinates(cubeCoordinates);
		}

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

		/**
		 * Return the unit coordinates of this unit position.
		 */
		@Basic @Raw
		public double[] getUnitCoordinates() {
			return new double[] {this.x, this.y, this.z};
		}

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

		/**
		 * Set the unit coordinates of this unit position to the sum of
		 * the given cube coordinates and 1/2 of a cube side.
		 *
		 * @param cubeCoordinates
		 *         	  The cube coordinates of this unit position.
		 * @post 	  The unit coordinates of this new unit position are equal to
		 *         	  the given cube coordinates + 1/2 of a cube side.
		 *        	| new.getUnitCoordinates() ==
		 *        	|	{
		 *        	|	  (cubeCoordinates[0] + 1/2 * cubeSideLength),
		 *        	| 	  (cubeCoordinates[1] + 1/2 * cubeSideLength),
		 *        	| 	  (cubeCoordinates[2] + 1/2 * cubeSideLength)
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
			double halfCubeSideLength = cubeSideLength / 2;
			this.x = unitCoordinates[0] + halfCubeSideLength;
			this.y = unitCoordinates[1] + halfCubeSideLength;
			this.z = unitCoordinates[2] + halfCubeSideLength;
		}

		/**
		 * Variable registering the length of a cube side in meters.
		 */
		public final double cubeSideLength = 1;

		/**
		 * Variables registering the unit coordinates of this unit position.
		 */
		private double x, y, z;
	}

	/**
	 * Variable registering the current unit position of this unit.
	 */
	public Unit.Position position = new Unit.Position();

	/**
	 * @param 	  targetposition
	 * 			  the adjacent field were the unit has to move
	 * @post 	  the unit moves to an adjacent position of the current one
	 * 			| new.posistion.getUnitCoordinates() == targetposition
	 * @post 	  the orientation must set by atan2(vy,vx)
	 * 			| new.getOrentation() == atan2(vy,vx)
	 * @throws IllegalStateException
	 * 			  when the unit can't move when the it is attacked
	 * 
	 */
	public void moveToAdjacent(Position targetposition) throws IllegalStateException {

	}
	/**
	 * @param 	  targetposition
	 * 
	 * @post 	  the new position must be the target positon
	 * 			| this.Position.getPositon() == targetposition
	 * 
	 */
	public void moveTo(Position targetposition){
		
	}
	/**
	 * @return 	  the base speed of a unit determined by the unit's weight, strength and agility
	 * 			| result == 1.5*((strength + agility)/(200*(weight/100))
	 */
	public double getUnitBaseSpeed(){
		return -1;
	}
	/**
	 * @param 	  targetposition
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
	public double getUnitWalkSpeed(Position targetposition){
		return -1;
	}
	/**
	 * @post 	  if the unit is sprinting do nothing
	 * 
	 * @post 	  if the unit isn't sprinting set sprinting to true
	 * 			| if(! isSprinting)
	 * 			|	then new.isSprinting == true
	 * @throws IllegalStateException
	 * 			  if the unit's stamina is lower or equals to zero
	 * 			| if (!(this.getStamina >= 0))
	 * 
	 */
	public void startSprinting() throws IllegalStateException{
		
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
		
	}
	public boolean isSprinting(){
		return this.isSprinting;
	}
	private boolean isSprinting = false;
	
	public static final double TIME_EXHAUSTSSPRINTNG = 0.1 ;
	/**
	 * @param 	  workActivity
	 * 
	 * @post 	  the activity of the unit is set to work
	 * 			| new.setActivity(work) == work
	 * 
	 * @effect 	  set the activity of the unit to work
	 * 			| this.setActivity(work)
	 * 
	 * @throws IllegalStateException
	 * 			  The given workActivity is not a valid workActivity for work
	 * 			| !isValidWorkActivity(workActivity)
	 *
	 * @throws IllegalArgumentException
	 * 			  The given workActivity is not a valid workActivity for work
	 * 			| !isValidWorkActivity(workActivity)
	 */			
	public void work(String workActivity) throws IllegalArgumentException{
		
	}
	/**
	 * @param 	  workActivity
	 * 			  the workActivity that we want to check
	 * @return 	  true if workActivity is in VALID_WORKACTIVITIES else we return false
	 * 			| for each i in VALID_WORKACTIVITIES
	 * 			| 	if workActivity == VALID_WORKACTIVITIES[j]
	 * 			|		then return true
	 * 			| return false
	 */
	public boolean isValidWorkActivity(String workActivity){
		return true;
	}
	/**
	 *  variable list with all valid workActivities a unit can do
	 *  
	 */
	private static String VALID_WORKACTIVITIES[]= {"write all available work activities"};
	
	/**
	 * 
	 * @return 	  gives the time its takes for a work activity
	 * 			| result == 500/this.strength
	 * 
	 * 		
	 * 
	 */
	public float getTimeForWork(){
		return this.timeForWork;
	}
	
	/**
	 * variable time that it takes for a work activity
	 */
	public float timeForWork;
	
	
	/**
	 * @post 	  the activity of the unit is set to work
	 * 			| new.setActivity(rest) == rest
	 * @post 	  while the hitpoints not larger or equals the maxHitpoints then
	 * 		 	  add every REGEN_REST_TIME second toughness/200 hitpoints
	 * 		 	  else check the stamina points if they are not the
	 * 		 	  maximun add every REGEN_REST_TIME tougness/100 stanmina points
	 * 			| while (this.getHitPoints() <= this.maxHitPoints)
	 * 			| 		then advanceTime(REGEN_REST_TIME)
	 * 			| while (this.getStamina <= this.maxStaminaPoints())
	 * 			|		then advaceTime(REGEN_REST_TIME)
	 * 			|
	 * 
	 * @effect 	  set the activity of the unit to work
	 * 			| this.setActivity(rest)
	 * 
	 * @throws IllegalStateException
	 * 			  When a unit has the maximum hitpoints and the maximum of stamina
	 * 			  the unit can't rest
	 * 			| if(this.getHitPoints() == this.maxHitPoints())
	 * 			| && ( this.getStamina == this.getMaxStaminaPoints())
	 */
	public void rest()throws IllegalStateException{
		
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
	public int getRegenHitPoints(){
		return -1;
	}
	/**
	 * @return 	  gives the amount Stamina points need to regenerate per time unit of REGEN_REST_TIME
	 * 			| restult == (this.toughness)/100
	 */	
	public int getRegenStamina(){
		return -1;
	}

	/**
	 * Returns the current orientation of this unit.
	 *
	 * @return 	  Current orientation of this unit.
	 * 			| result == this.orientation
	 */
	@Basic
	public float getOrientation() {
		return -1;
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

	}

	/**
	 * Returns the minimum orientation value of this unit.
	 *
	 * @return 	  Minimum orientation value of this unit.
	 *  		| result == this.MIN_ORIENTATION
	 */
	@Immutable
	private float getMinOrientation() {
		return -1;
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
		return -1;
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
	 * 			  then the strength of this unit is equal to the given strength modulo
	 * 			  the range established by the minimum and maximum attribute values of this unit.
	 * 			| if (strength > this.getMaxAttributeValue())
	 * 			| 	then new.getStrength() ==
	 * 			|		((strength-getMinAttributeValue()) % (getMaxAttributeValue()-getMinAttributeValue()+1)) + getMinAttributeValue()
	 * @post	  If the given strength is lower than the minimum attribute value of this unit,
	 * 			  then the strength of this unit is equal to the minimum attribute value.
	 * 			| if (strength < this.getMinAttributeValue())
	 * 			|	then new.getStrength() == this.getMinAttributeValue()
	 */
	public void setStrength(int strength) {
			if(strength <= getMinAttributeValue())
				strength = getMinAttributeValue();
			if(strength >= getMaxAttributeValue())
				strength =getMaxAttributeValue();
			this.strength = strength;
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
	 * 			  then the agility of this unit is equal to the given agility modulo
	 * 			  the range established by the minimum and maximum attribute values of this unit.
	 * 			| if (agility > this.getMaxAttributeValue())
	 * 			| 	then new.getAgility() ==
	 * 			|		((agility-getMinAttributeValue()) % (getMaxAttributeValue()-getMinAttributeValue()+1)) + getMinAttributeValue()
	 * @post	  If the given agility is lower than the minimum attribute value of this unit,
	 * 			  then the agility of this unit is equal to the minimum attribute value.
	 * 			| if (agility < this.getMinAttributeValue())
	 * 			|	then new.getAgility() == this.getMinAttributeValue()
	 */
	public void setAgility(int agility) {
		if (agility > this.getMaxAttributeValue())
			this.agility=((agility-this.getMinAttributeValue()) 
					% (this.getMaxAttributeValue()-this.getMinAttributeValue()+1)) + this.getMinAttributeValue();
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
		if(weight >= getMaxAttributeValue())
			weight = getMaxAttributeValue();
		if(weight <= getMinAttributeValue())
			weight = getMinAttributeValue();
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
	 * 			  then the toughness of this unit is equal to the given toughness modulo
	 * 			  the range established by the minimum and maximum attribute values of this unit.
	 * 			| if (toughness > this.getMaxAttributeValue())
	 * 			| 	then new.getToughness() ==
	 * 			|			((toughness - this.getMinAttributeValue()) %
	 * 			|			(this.getMaxAttributeValue()-this.getMinAttributeValue()))
	 * 			|			+ this.getMinAttributeValue()
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
			this.toughness = ((toughness - this.getMinAttributeValue()) % (this.getMaxAttributeValue() - this.getMinAttributeValue())) +
					this.getMinAttributeValue();
		}
	}

	/**
	 * Variable registering the toughness of this unit.
	 */
	private int toughness;

	/**
	 * @param 	  activity
	 *
	 * @return 	  Returns the current activity of this unit.
	 * 			| result == this.activity
	 */
	public String getActivity(String activity){
		return this.activity;
	}

	/**
	 * @param 	  activity
	 *
	 * @pre 	  The given activity has to be a valid activity.
	 * 			| isvalidAcivity(activity)
	 * @post 	  The new activity is equal to the given activity.
	 * 			| new.getActivity() == activity
	 *
	 */
	public void setActivity(String activity){
		assert isValidActivity(activity);
		this.activity = activity;
	}
	/**
	 * @param 	  activity
	 *
	 *
	 * @return 	  Returns true if the activity is equal to a valid activity.
	 * 			| for each i in validActivities()
	 * 			|	if( activity == j in validAcitivties())
	 * 			|		then return true
	 * 			| return false
	 */
	public static boolean isValidActivity(String activity){
		return true;
	}

	/**
	 *
	 * @return 	  All the activities a unit can perform
	 */
	public static String[] validActivities(){
		String[] activities = {"work,attack","defend","rest","move",null};
		return activities;
	}

	private String activity = null;
	
	/**
	 * @post 	  choose a random activity move, conduct a work task, rest unil it has full recovered hitpoints and stamina
	 * 			
	 * @post  	  if it is sprinting till it's exhausted
	 * 
	 * @throws IllegalStateException
	 * 			  if the unit is doing an activity
	 */
	public void startDefaultBehaviour() throws IllegalStateException{
		
	}
	/**
	 * @post 	  Stop the Default activity and set the current activity on null
	 * 
	 * @throws IllegalStateException
	 * 			  if a unit doesn't conduct an activity
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
	public void toggleDefaultBehavior(){
		
	}
	
	private boolean defaultBehaviorEnabled;
	
	/**
	 * @return 	  return the current hitpoints of the unit
	 */
	public int getHitPoints(){
		return this.hitPoints;
	}

	/**
	 * @param 	  hitpoints
	 * 			  the amount of hitpoints need to be set
	 * @post 	  the hitpoints must be valid
	 * 			| isValidHitPoints(hitpoints)
	 * 
	 */
	public void setHitPoints(int hitpoints){
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
	public static boolean isValidHitPoints(int hitpoints){
		return true;
	}

	/**
	 * @return 	  returns the maximum hitpoints the unit can have
	 * 			| 200.(weight/100).(toughness/100)
	 */
	public int maxHitPoints(){
		return -1;
	}

	private int hitPoints;
	
	/**
	 * Return the stamina of this Unit.
	 *
	 * @return	| result == this.stamina
	 */
	@Basic @Raw
	public int getStamina() {
	  return this.stamina;
	}
	
	/**
	 * Check whether the given stamina is a valid stamina for
	 * any Unit.
	 *  
	 * @param  	  stamina
	 *         	  The stamina to check.
	 * @return 
	 *       	| result == (stamina >= 0) && (stamina < this.getMaxStaminaPoints())
	 */
	public static boolean isValidStamina(int stamina) {
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
	 *       	| new.getStamina() == stamina
	 */
	@Raw
	public void setStamina(int stamina) {
	  assert isValidStamina(stamina);
	  this.stamina = stamina;
	}

	/**
	 * Returns the maximum amount of stamina points for any Unit.
	 *
	 * @return 	| result == this.maxStamina
     */
	public int getMaxStaminaPoints() {
		return -1;
	}

	private int maxStamina;
	
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
	public void setName(String name)
	      throws IllegalArgumentException {
	  if (! isValidName(name))
	    throw new IllegalArgumentException();
	  this.name = name;
	}

	/**
	 * Variable registering the name of this Unit.
	 */
	private String name;

	/**
	 * Make two units fight each other.
	 * 
	 * @param 	  attacker
	 * 			  The unit attacking defender.
	 * @param 	  defender
	 * 			  The defending unit.
	 * @effect 	  The defending unit defends.
	 * 			| defender.defend(attacker)
	 * @note 	  To-do: add orientation update.
     */
	public static void fight(Unit attacker, Unit defender) {
		defender.defend(attacker);
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
	 * @note 	  To-do: add orientation update.
	 */
	public void attack(Unit defender) throws IllegalStateException {
		fight(this, defender);
	}

	/**
	 * When being attacked, this unit defends itself by either
	 * dodging, blocking or taking damage, respectively.
	 *
	 * @param 	  attacker
	 * 			  The attacking unit.
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
	public void defend(Unit attacker) {

	}

	public void advanceTime(double dt) {

	}
}


