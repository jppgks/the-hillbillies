package hillbillies.model;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class for a cubical object that occupies a position in the game world.
 * 
 * @author 	Iwein Bau & Joppe Geluykens
 *
 * @note 	A Unit is a basic type of in-game character with the ability to move around,
 * 			interact with other such characters and manipulate the game world.
 * 
 * @invar 	The activity is always equals to a valid activity.
 * 			| isValidAcivity(activity)
 * @invar 	The amount of hitpoints is always a valid amount
 * 			| isValidHitPoints(hitpoints)
 * @invar  	The stamina of each Unit must be a valid stamina for any
 *         	Unit.
 *       	| isValidStamina(getStamina())
 * @invar  	The workActivity of each Unit must be a valid workActivity for any
 *         	Unit.
 *       	| isValidWorkActivity(getWorkActivity())
 * @invar  	The name of each Unit must be a valid name for any
 *         	Unit.
 *       	| isValidName(getName())
 */

public class Unit {

	/**
	 * A nested class in Unit for maintaining its position.
	 *
	 * @invar  	The coordinates of each Position must be a valid coordinates for any
	 *         	Position.
	 *       	| isValidPosition(this.getCoordinates())
	 *
	 * @note 	All of these functions should be worked out defensively.
	 *
	 */
	private class Position {

		/**
		 * Initialize this new Position to default coordinates.
		 *
		 * @effect 	The coordinates of this new Position are set to 0.
		 *       	| this.setCoordinates(Coordinate)
		 */
		public Position() {
			this.x = 0;
			this.y = 0;
			this.z = 0;
		}

		/**
		 * Initialize this new Position with given coordinates.
		 *
		 * @param  coordinates
		 *         The coordinates for this new Position.
		 * @effect The coordinates of this new Position are set to
		 *         the given coordinates.
		 *       | this.setCoordinates(coordinates)
		 */
		public Position(double... coordinates) throws IllegalCoordinateException {
			this.setCoordinates(coordinates);
		}


		/**
		 * Return the coordinates of this Position.
		 */
		@Basic @Raw
		public double[] getCoordinates() {
			return new double[] {this.x, this.y, this.z};
		}

		/**
		 * Check whether the given coordinates are valid coordinates for
		 * any Position.
		 *
		 * @param  	coordinates
		 *         	The coordinates to check.
		 * @return 	True if all coordinates are within range, false otherwise.
		 *       	| result ==
		 *       	|	(coordinates[0] >= 0) && (coordinates[0] < 50) &&
		 *       	|	(coordinates[1] >= 0) && (coordinates[1] < 50) &&
		 *       	| 	(coordinates[2] >= 0) && (coordinates[2] < 50)
		 */
		public boolean isValidPosition(double[] coordinates) {
			return false;
		}

		/**
		 * Set the coordinates of this Position to the given coordinates.
		 *
		 * @param	coordinates
		 *         	The new coordinates for this Position.
		 * @post 	The coordinates of this new Position are equal to
		 *         	the given coordinates.
		 *       	| new.getCoordinates() == coordinates
		 * @throws IllegalCoordinateException
		 *         	The given coordinates are not valid coordinates for any
		 *         	Position.
		 *       	| ! isValidPosition(getCoordinates())
		 */
		@Raw
		public void setCoordinates(double[] coordinates) throws IllegalCoordinateException {
			if (! isValidPosition(coordinates))
				throw new IllegalCoordinateException(coordinates);
			this.x = coordinates[0];
			this.y = coordinates[1];
			this.z = coordinates[2];
		}
		/**
		 * Variables registering the coordinates of this Position.
		 */
		private double x, y, z;
	}
	/**
	 * @param targetposition
	 * 			the adjacent field were the unit has to move
	 * @post the unit moves to an adjacent position of the current one
	 * 			|new.posistion.getCoordinates() == targetposition
	 * @post the orientation must set by atan2(vy,vx)
	 * 			| new.getOrentation() == atan2(vy,vx)
	 * @throws IllegalStateException
	 * 			when the unit can't move when the it is attacked
	 * 
	 */
	public void moveToAdjacent(Position targetposition) throws IllegalStateException {

	}
	/**
	 * @param targetposition
	 * 
	 * @post the new position must be the target positon
	 * 			|this.Position.getPositon() == targetposition
	 * 
	 */
	public void moveTo(Position targetposition){
		
	}
	/**
	 * @return the base speed of a unit determined by the unit's weight, strength and agility
	 * 			|result == 1.5*((strength + agility)/(200*(weight/100))
	 */
	public double getUnitBaseSpeed(){
		return -1;
	}
	/**
	 * @param targetposition
	 * 			the position were the unit will go
	 * @return if the unit is lower than the targetposition then the speed will be 
	 * 			0.5*getUnitBaseSpeed()
	 * 			if the unit is higher then the targetposition the speed will be 
	 * 			1.2 * getUnitBaseSpeed()
	 * 			if a unit is sprinting return the getUnitWalkSpeed *2 			
	 * 			else is the speed equals as getUnitBaseSpeed()
	 * 			|private double totalSpeed == getUnitBaseSpeed;
	 * 			|if( position[2] - targetposition[2] = -1)
	 * 			|	then totalSpeed == 0.5*getUnitBaseSpeed()
	 * 			|if( position[2] - targetposition[2] = 1)
	 * 			|	then totalSpeed == 1.2* getUnitBaseSpeed()
	 * 			|if( getSprinting())
	 * 			|	then totalSpeed *2
	 * 			|result == totalSpeed
	 */
	public double getUnitWalkSpeed(Position targetposition){
		return -1;
	}
	/**
	 * @post if the unit is sprinting do nothing 
	 * 
	 * @post if the unit isn't sprinting set sprinting to true
	 * 		|if(! isSprinting)
	 * 		|	then new.isSprinting == true
	 * @throws IllegalStateException
	 * 		if the unit's stamina is lower or equals to zero
	 * 		|if (!(this.getStamina >= 0))
	 * 
	 */
	public void startSprinting() throws IllegalStateException{
		
	}
	/**
	 * @post if the unit isn't sprinting do nothing 
	 * 
	 * @post if the unit is sprinting set sprinting to true
	 * 		|if(! isSprinting)
	 * 		|	then new.isSprinting == false
	 *  
	 */
	public void stopSprinting(){
		
	}
	public boolean isSprinting(){
		return this.isSprinting;
	}
	public boolean isSprinting = false;
	
	public static final double TIME_EXHAUSTSSPRINTNG = 0.1 ;
	/**
	 * @param workActivity
	 * 
	 * @post the activity of the unit is set to work
	 * 			|new.setActivity(work) == work
	 * 
	 * @effect set the activity of the unit to work
	 * 			|this.setActivity(work)
	 * 
	 * @throws IllegalStateException
	 * 			The given workActivity is not a valid workActivity for work
	 * 			| !isValidWorkActivity(workActivity)
	 *
	 * @throws IllegalArgumentException
	 * 			The given workActivity is not a valid workActivity for work
	 * 			| !isValidWorkActivity(workActivity)
	 */			
	public void work(String workActivity) throws IllegalArgumentException{
		
	}
	/**
	 * @param workActivity
	 * 			the workActivity that we want to check
	 * @return true if workActivity is in VALID_WORKACTIVITIES else we return false
	 * 			|for each i in VALID_WORKACTIVITIES
	 * 			| 	if workActivity == VALID_WORKACTIVITIES[j]
	 * 			|		then return true
	 * 			|return false
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
	 * @return gives the time its takes for a work activity
	 * 			|result == 500/this.strength 
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
	 * @post the activity of the unit is set to work
	 * 			|new.setActivity(rest) == rest
	 * @post while the hitpoints not larger or equals the maxHitpoints then
	 * 		 add every REGEN_REST_TIME second toughness/200 hitpoints
	 * 		 else check the stamina points if they are not the
	 * 		 maximun add every REGEN_REST_TIME tougness/100 stanmina points
	 * 			| while (this.getHitPoints() <= this.maxHitPoints)
	 * 			| 		then advanceTime(REGEN_REST_TIME)
	 * 			|while (this.getStamina <= this.maxStaminaPoints())
	 * 			|		then advaceTime(REGEN_REST_TIME)
	 * 			|
	 * 
	 * @effect set the activity of the unit to work
	 * 			|this.setActivity(rest)
	 * 
	 * @throws IllegalStateException
	 * 			When a unit has the maximum hitpoints and the maximum of stamina
	 * 			the unit can't rest
	 * 			|if(this.getHitPoints() == this.maxHitPoints()) 
	 * 			|&& ( this.getStamina == this.getMaxStaminaPoints())	
	 */
	public void rest()throws IllegalStateException{
		
	}
	/**
	 * variable time need the regen hp and stamina
	 * 
	 */
	public static final double REGEN_REST_TIME = 0.2;

	/**
	 * @return gives the amount hitpoinst need to regenerate per time unit of REGEN_REST_TIME
	 * 			|result == (this.toughness)/200
	 */
	public int getRegenHitPoints(){
		return -1;
	}
	/**
	 * @return gives the amount Stamina points need to regenerate per time unit of REGEN_REST_TIME
	 * 			| restult == (this.toughness)/100
	 */	
	public int getRegenStamina(){
		return -1;
	}
	/**
	 * Variable registering the current Position of this Unit.
	 */
	private Unit.Position position = new Unit.Position();

	/**
	 * Returns the current orientation of this unit.
	 *
	 * @return 	Current orientation of this unit.
	 * 			| result == this.orientation
	 */
	@Basic
	public float getOrientation() {
		return -1;
	}

	/**
	 * @param 	orientation
	 * 			The new orientation for this unit.
	 * @post 	If the given orientation is within the range of minimum and maximum
	 * 			orientation values, the orientation of this unit is set to the given orientation.
	 * 			| if (orientation >= this.getMinOrientation()) && (orientation <= this.getMaxOrientation)
	 * 			| 	then new.getOrientation() == orientation
	 * @post	If the given orientation is less than the minimum or greater than the
	 * 			maximum orientation value, then the orientation of this unit is set to
	 * 			the given orientation modulo the range established by the minimum and maximum
	 * 			orientation values.
	 * 			| if (orientation < this.getMinOrientation) || (orientation > this.getMaxOrientation())
	 * 			| 	then new.getOrientation() ==
	 * 			|		((orientation-getMinOrientation()) % (getMaxOrientation()-getMinOrientation()+1)) + getMinOrientation()
	 */
	private void setOrientation(float orientation) {

	}

	/**
	 * Returns the minimum orientation value of this unit.
	 *
	 * @return 	Minimum orientation value of this unit.
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
	 * @return 	Maximum orientation value of this unit.
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
	 * @return	Current strength of this unit.
	 * 			| result == this.strength
	 */
	@Basic
	public int getStrength() {
		return -1;
	}

	/**
	 * Set the strength for this unit to the given strength.
	 *
	 * @param 	strength
	 * 			The new strength for this unit.
	 * @post 	If the given strength is within the range established by
	 * 			the minimum and maximum attribute value for this unit,
	 * 			then the strength of this unit is equal to the given strength.
	 * 			| if (strength >= this.getMinAttributeValue()) && (strength <= this.getMaxAttributeValue())
	 * 			|	then new.getStrength() == strength
	 * @post	If the given strength exceeds the maximum attribute value,
	 * 			then the strength of this unit is equal to the given strength modulo
	 * 			the range established by the minimum and maximum attribute values of this unit.
	 * 			| if (strength > this.getMaxAttributeValue())
	 * 			| 	then new.getStrength() ==
	 * 			|		((strength-getMinAttributeValue()) % (getMaxAttributeValue()-getMinAttributeValue()+1)) + getMinAttributeValue()
	 * @post	If the given strength is lower than the minimum attribute value of this unit,
	 * 			then the strength of this unit is equal to the minimum attribute value.
	 * 			| if (strength < this.getMinAttributeValue())
	 * 			|	then new.getStrength() == this.getMinAttributeValue()
	 */
	public void setStrength(int strength) {

	}

	/**
	 * Variable registering the strength of this unit.
	 */
	private int strength;

	/**
	 * Returns the current agility of this unit.
	 *
	 * @return	Current agility of this unit.
	 * 			| result == this.agility
	 */
	@Basic
	public int getAgility() {
		return -1;
	}

	/**
	 * Set the agility for this unit to the given agility.
	 *
	 * @param 	agility
	 * 			The new agility for this unit.
	 * @post 	If the given agility is within the range established by
	 * 			the minimum and maximum attribute value for this unit,
	 * 			then the agility of this unit is equal to the given agility.
	 * 			| if (agility >= this.getMinAttributeValue()) && (agility <= this.getMaxAttributeValue())
	 * 			|	then new.getAgility() == agility
	 * @post	If the given agility exceeds the maximum attribute value,
	 * 			then the agility of this unit is equal to the given agility modulo
	 * 			the range established by the minimum and maximum attribute values of this unit.
	 * 			| if (agility > this.getMaxAttributeValue())
	 * 			| 	then new.getAgility() ==
	 * 			|		((agility-getMinAttributeValue()) % (getMaxAttributeValue()-getMinAttributeValue()+1)) + getMinAttributeValue()
	 * @post	If the given agility is lower than the minimum attribute value of this unit,
	 * 			then the agility of this unit is equal to the minimum attribute value.
	 * 			| if (agility < this.getMinAttributeValue())
	 * 			|	then new.getAgility() == this.getMinAttributeValue()
	 */
	public void setAgility(int agility) {

	}

	/**
	 * Variable registering the agility of this unit.
	 */
	private int agility;

	/**
	 * @return 	Returns the current weight of the unit.
	 * 			| result == this.weight
	 */
	public int getWeight(){
		return this.weight;
	}

	/**
	 * @param weight
	 *
	 * @post if the given weight larger is then MAX_WEIGHT then weight
	 * 		is equals to MAX_WEIGHT
	 * 		| if( weight > MAX_WEIGHT)
	 * 		|	then new.weight == MAX_WEIGHT
	 * @post if the given weight smaller is then MIN_WEIGHT
	 * 			then is new weight equals to MIN_WEIGHT
	 *
	 * @post the weight of a unit must all times be at least minWeight()
	 * 			if its smaller set the weight equals to minWeight()

	 * @post the new weight is equals as the given weight
	 * 		|new.weight == weight
	 *
	 */
	public void setWeight(int weight){


	}
	/**
	 * @return the minWeight of a unit
	 * 		|(strengt+agility)/2
	 */
	public static final int minWeight(){
		return -1;
	}
	public static final int MAX_WEIGHT = 200;
	public static final int	MIN_WEIGHT = 1;

	private int weight;

	/**
	 * Returns the current toughness of this unit.
	 *
	 * @return	Current toughness of this unit.
	 * 			| result == this.toughness
	 */
	@Basic
	public int getToughness() {
		return -1;
	}

	/**
	 * Set the toughness for this unit to the given toughness.
	 *
	 * @param 	toughness
	 * 			The new toughness for this unit.
	 * @post 	If the given toughness is within the range established by
	 * 			the minimum and maximum attribute value for this unit,
	 * 			then the toughness of this unit is equal to the given toughness.
	 * 			| if (toughness >= this.getMinAttributeValue()) && (toughness <= this.getMaxAttributeValue())
	 * 			|	then new.getToughness() == toughness
	 * @post	If the given toughness exceeds the maximum attribute value,
	 * 			then the toughness of this unit is equal to the given toughness modulo
	 * 			the range established by the minimum and maximum attribute values of this unit.
	 * 			| if (toughness > this.getMaxAttributeValue())
	 * 			| 	then new.getToughness() ==
	 * 			|		((toughness-getMinAttributeValue()) % (getMaxAttributeValue()-getMinAttributeValue()+1)) + getMinAttributeValue()
	 * @post	If the given toughness is lower than the minimum attribute value of this unit,
	 * 			then the toughness of this unit is equal to the minimum attribute value.
	 * 			| if (toughness < this.getMinAttributeValue())
	 * 			|	then new.getToughness() == this.getMinAttributeValue()
	 */
	public void setToughness(int toughness) {

	}

	/**
	 * Variable registering the toughness of this unit.
	 */
	private int toughness;

	/**
	 * @param 	activity
	 *
	 * @return 	Returns the current activity of this unit.
	 * 			| result == this.activity
	 */
	public String getActivity(String activity){
		return this.activity;
	}

	/**
	 * @param 	activity
	 *
	 * @pre 	The given activity has to be a valid activity.
	 * 			| isvalidAcivity(activity)
	 * @post 	The new activity is equal to the given activity.
	 * 			| new.getActivity() == activity
	 *
	 */
	public void setActivity(String activity){
		assert isValidActivity(activity);
		this.activity = activity;
	}
	/**
	 * @param 	activity
	 *
	 *
	 * @return 	Returns true if the activity is equal to a valid activity.
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
	 * @return 	All the activities a unit can perform
	 */
	public static String[] validActivities(){
		String[] activities = {"work,attack","defend","rest","move"};
		return activities;
	}

	private String activity;
	
	/**
	 * @return return the current hitpoints of the unit
	 */
	public int getHitPoints(){
		return this.hitPoints;
	}

	/**
	 * @param hitpoints
	 * 			the amount of hitpoints need to be set
	 * @post the hitpoints must be valid
	 * 		|isValidHitPoints(hitpoints)
	 * 
	 */
	public void setHitPoints(int hitpoints){
		assert isValidHitPoints(hitpoints);
		this.hitPoints = hitpoints;
	}

	/**
	 * @param hitpoints
	 * 
	 * @return true if the amounts of hitpoints larger or equals to zero
	 *          and is smaller or equals to the maximum hitpoints the unit can have
	 *         |return if( (0 <= hitpoints) && ( hitpoints <= this.maxHitPoints())
	 */
	public static boolean isValidHitPoints(int hitpoints){
		return true;
	}

	/**
	 * @return returns the maximum hitpoints the unit can have
	 * 			|200.(weight/100).(toughness/100)
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
	 * @param  stamina
	 *         The stamina to check.
	 * @return 
	 *       | result == (stamina >= 0) && (stamina < this.getMaxStaminaPoints())
	 */
	public static boolean isValidStamina(int stamina) {
	  return false;
	}
	
	/**
	 * Set the stamina of this Unit to the given stamina.
	 * 
	 * @param  stamina
	 *         The new stamina for this Unit.
	 * @pre    The given stamina must be a valid stamina for any
	 *         Unit.
	 *       | isValidStamina(stamina)
	 * @post   The stamina of this Unit is equal to the given
	 *         stamina.
	 *       | new.getStamina() == stamina
	 */
	@Raw
	public void setStamina(int stamina) {
	  assert isValidStamina(stamina);
	  this.stamina = stamina;
	}

	/**
	 * Returns the maximum amount of stamina points for any Unit.
	 *
	 * @return	| result == this.maxStamina
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
	 * @param  name
	 *         The name to check.
	 * @return
	 *       | result == name.matches("\"?[A-Z]{1}[a-zA-Z'\\s]*\"?")
	 */
	public static boolean isValidName(String name) {
	  return name.matches("\"?[A-Z]{1}[a-zA-Z'\\s]*\"?");
	}

	/**
	 * Set the name of this Unit to the given name.
	 *
	 * @param  name
	 *         The new name for this Unit.
	 * @post   The name of this new Unit is equal to
	 *         the given name.
	 *       | new.getName() == name
	 * @throws IllegalArgumentException
	 *         The given name is not a valid name for any
	 *         Unit.
	 *       | ! isValidName(getName())
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
	 * @param 	attacker
	 * 			The unit attacking defender.
	 * @param 	defender
	 * 			The defending unit.
	 * @effect 	The defending unit defends.
	 * 			| defender.defend(attacker)
     */
	public static void fight(Unit attacker, Unit defender) {
		defender.defend(attacker);
	}

	/**
	 * Attack other unit that occupies the same or a neighbouring cube
	 * of the game world.
	 *
	 * @param 	defender
	 * 			The defending unit.
	 * @effect	This unit fights defender.
	 * 			| fight(this, defender)
	 * @throws IllegalStateException
	 * 			When the victim is not within reach.
	 * @note 	Conducting an attack lasts 1s of game time.
	 */
	public void attack(Unit defender) throws IllegalStateException {
		fight(this, defender);
	}

	/**
	 * When being attacked, this unit defends itself by either
	 * dodging, blocking or taking damage, respectively.
	 *
	 * @param 	attacker
	 * 			The attacking unit.
	 * @post 	When the agility of this unit is high enough,
	 * 			relative to the agility of its attacker,
	 * 			this unit dodges the attack and moves to a
	 * 			random neighbouring cube.
	 * 			| 1 <= 0.25 * (defender.getAgility() / attacker.getAgility())
	 * @post 	When this unit fails to dodge the attack,
	 * 			this unit blocks the attack when the sum of it's
	 * 			strength and agility, relative to those of its attacker
	 * 			is high enough.
	 * 			|
	 * @post	When this unit fails to dodge or block the attack,
	 * 			this unit's hitpoints are lowered,
	 * 			relative to the strength of the attacker.
	 * 			|
	 *
	 */
	public void defend(Unit attacker) {

	}
}
