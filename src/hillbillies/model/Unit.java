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
 * @invar  	  The currentStaminaPoints of each Unit must be a valid currentStaminaPoints for any
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
		this.setCurrentStaminaPoints(this.getMaxStaminaPoints());
		this.setDefaultBehaviorEnabled(enableDefaultBehavior);
	}

	/**
	 * Variable registering the current name of this Unit.
	 */
	private String name;

	/**
	 * Variable registering the current unit position of this unit.
	 */
	public Unit.Position position;

	/**
	 * Variable registering the target cube this unit is
	 * moving towards when moving.
	 */
	private int[] targetPosition = new int[] {0, 0, 0};

	/*
	 * Variable registering the relative difference
	 * of the unit's current position and the target position.
	 */
	private double[] initialPosition = new double[]{0, 0, 0};

	/**
	 * Variable registering the next neighboring cube to move to.
	 */
	private int[] neighboringCubeToMoveTo = new int[]{0, 0, 0};

	/**
	 * Variable registering the target position when a new
	 * movement is initialized, while already moving.
	 */
	private int[] newTargetPosition = null;

	/**
	 * Variable registering whether this unit is
	 * conducting a sprint.
	 */
	private boolean isSprinting = false;

	/**
	 * Variable registering the current orientation of this unit.
	 */
	private float orientation = (float) (Math.PI / 2);

	/**
	 * Variable registering the current strength of this unit.
	 */
	private double strength;

	/**
	 * Variable registering the current agility of this unit.
	 */
	private double agility;

	/**
	 * Variable registering the current weight of this unit.
	 */
	private double weight;

	/**
	 * Variable registering the current toughness of this unit.
	 */
	private double toughness;

	/**
	 * Variable registering the current state of this unit.
	 */
	private State state = State.NONE;

	/**
	 * Variable registering whether or not this unit's
	 * default behavior is currently enabled.
	 */
	private boolean defaultBehaviorEnabled;

	/**
	 * Variable registering the current hitpoints of this unit.
	 */
	private double currentHitPoints;

	/**
	 * Variable registering the current currentStaminaPoints of this Unit.
	 */
	private double currentStaminaPoints;

	/**
	 * Variable registering the victim when this unit is conducting an attack.
	 */
	private Unit theDefender;

	/**
	 * Variable registering whether or not a unit is currently defending.
	 */
	private boolean isDefending = false;

	/**
	 * Variable registering the state of a unit,
	 * before being attacked.
	 */
	private State previousState;

	/**
	 * Variable registering the orientation of a unit,
	 * before being attacked.
	 */
	private float previousOrientation;

	/**
	 * Variable registering the number of seconds until
	 * this unit loses another stamina point due to sprinting.
	 */
	private double sprintCounter = SPRINT_TIME;

	/**
	 * Variable registering the number of seconds until
	 * this unit gains a new stamina or hit point from resting.
	 */
	private double restCounter = REST_TIME;

	/**
	 * Variable registering the number of seconds until
	 * this unit is done working.
	 */
	private double workCounter;

	/**
	 * Variable registering the number of seconds until
	 * this unit is done attacking another.
	 */
	private double fightCounter;

	/**
	 * Variable registering the number of seconds until
	 * this unit is forced to rest.
	 */
	private double needToRestCounter = NEED_TO_REST_TIME;

	/**
	 * Constant reflecting the lowest possible initial value for
	 * this unit's attributes, being:
	 * weight, agility, strength and toughness.
	 */
	private static final int MIN_INITIAL_ATTRIBUTE_VALUE = 25;

	/**
	 * Constant reflecting the highest possible initial value for
	 * this unit's attributes, being:
	 * weight, agility, strength and toughness.
	 */
	private static final int MAX_INITIAL_ATTRIBUTE_VALUE = 100;

	/**
	 * Constant reflecting the lowest possible value for
	 * this unit's hit points.
	 */
	private static final double MIN_HIT_POINTS =0.0;

	/**
	 * Constant reflecting the lowest possible value for
	 * this unit's stamina points.
	 */
	private static final double MIN_STAMINA_POINTS = 0.0;

	/**
	 * Constant reflecting the lowest possible value for
	 * this unit's orientation.
	 */
	private static final float MIN_ORIENTATION = (float) (- Math.PI / 2);

	/**
	 * Constant reflecting the highest possible value for
	 * this unit's orientation.
	 */
	private static final float MAX_ORIENTATION = (float) (Math.PI / 2);

	/**
	 * Constant reflecting the highest possible value for
	 * this unit's attributes, being:
	 * weight, agility, strength and toughness.
	 */
	private static final double MAX_ATTRIBUTE_VALUE = 200;

	/**
	 * Constant reflecting the lowest possible value for
	 * this unit's attributes, being:
	 * weight, agility, strength and toughness.
	 */
	private static final double MIN_ATTRIBUTE_VALUE = 1.0;

	/**
	 * Constant reflecting the time needed for this unit to
	 * conduct an attack.
	 */
	private static final double FIGHT_TIME = 1;

	/**
	 * Constant reflecting the time needed for this unit to
	 * loses a stamina point due to sprinting.
	 */
	private static final double SPRINT_TIME = 0.1;

	/**
	 * Constant reflecting the time needed for this unit to
	 * gain a stamina or hit point from resting.
	 */
	private static final double REST_TIME = 0.2;

	/**
	 * Constant reflecting the time needed for this unit to
	 * be forced to rest.
	 */
	private static final double NEED_TO_REST_TIME = 180;

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
	 * Check whether the given name is a valid name for
	 * any Unit.
	 *
	 * @param  	  name
	 *         	  The name to check.
	 * @return
	 *       	| result == name.matches("\"?[A-Z]{1}[a-zA-Z'\\s]*\"?")
	 */
	private static boolean isValidName(String name) {
		return name.matches("[A-Z][a-zA-Z\'\"\\s]+");
	}

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
		 *        	| 	  (cubeCoordinates[2] + 1/2 * cubeSideLength)
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
			this.setOccupyingCubeCoordinates(cubeCoordinates);
			double halfCubeSideLength = cubeSideLength / 2;
			this.unitX = cubeCoordinates[0] + halfCubeSideLength;
			this.unitY = cubeCoordinates[1] + halfCubeSideLength;
			this.unitZ = cubeCoordinates[2] + halfCubeSideLength;
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
		private boolean isValidPosition(int[] cubeCoordinates) {
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
	}

	/**
	 * Initalize the given attribute with the given value if its in range.
	 *
	 * @param attributeKind
	 * 			  The attribute of this unit to initialize.
	 * @param attributeValue
	 * 			  The value to initialize this unit's attribute with.
	 * @effect 	  If the given value is within the minimum and maximum
	 * 			  attribute value range, the attribute's value is set
	 * 			  to the given value.
	 * 			| this.setAttribute(attributeKind, attributeValue)
	 * @effect 	  If the given value is smaller than the minimum initial
	 * 			  attribute value, the attribute's value is set to the
	 * 			  minimum initial attribute value.
	 * 			| this.setAttribute(attributeKind, this.getMinInitialAttributeValue())
	 * @effect 	  If the given value is larger than the maximum initial
	 * 			  attribute value, the attribute's value is set to the
	 * 			  maximum initial attribute value.
	 * 			| this.setAttribute(attributeKind, this.getMaxInitialAttributeValue())
     */
	private void initializeAttribute(String attributeKind, int attributeValue) {
		if (this.isWithinInitialAttributeValueRange(attributeValue)) {
			this.setAttribute(attributeKind, attributeValue);
		} else if (attributeValue < this.getMinInitialAttributeValue()) {
			this.setAttribute(attributeKind, this.getMinInitialAttributeValue());
		} else if (attributeValue > this.getMaxInitialAttributeValue()) {
			this.setAttribute(attributeKind, this.getAttributeValueWithinInitialRangeFromTooLargeValue(attributeValue));
		}
	}

	/**
	 * @param attributeValue
	 * 			  Value to check
	 * @return	  Gives true if the value is in range of the minimum attribute value and maximum attribute value
	 * 			| Result == (attributeValue >= this.getMinInitialAttributeValue()) &&
				| (attributeValue <= this.getMaxInitialAttributeValue())
	 */
	private boolean isWithinInitialAttributeValueRange(int attributeValue) {
		return (attributeValue >= this.getMinInitialAttributeValue()) &&
				(attributeValue <= this.getMaxInitialAttributeValue());
	}

	/**
	 * 
	 * @return	  the minimum initial attribute value
	 * 			| Result == Unit.MIN_INITIAL_ATTRIBUTE_VALUE		
	 * 
	 */
	private int getMinInitialAttributeValue() {
		return Unit.MIN_INITIAL_ATTRIBUTE_VALUE;
	}
	
	/**
	 * 
	 * @return	  the maximum initial attribute value
	 * 			| Result == Unit.MAX_INITIAL_ATTRIBUTE_VALUE		
	 * 
	 */
	private int getMaxInitialAttributeValue() {
		return Unit.MAX_INITIAL_ATTRIBUTE_VALUE;
	}

	/**
	 * @param 		attributeKind
	 * 			  	  The kind of the attribute if (weight, agility, strength and toughness)
	 * 
	 * @param 		attributeValue
	 * 			  	  The value of that kind that need to be set.
	 * @post		  if the kind equals to w then set the weight to the attribute Value
	 * 				| if( attributeKind == "w")
	 * 				| 	Then this.setWeight(attributeValue)
	 * @post		  if the kind equals to a then set the agility to the attribute Value
	 * 				| if( attributeKind == "a")
	 * 				| 	Then this.setAgility(attributeValue) 
	 * @post		  if the kind equals to s then set the strength to the attribute Value
	 * 				| if( attributeKind == "s")
	 * 				| 	Then this.setStrength(attributeValue)	
	 * @post		  if the kind equals to t then set the toughness to the attribute Value
	 * 				| if( attributeKind == "t")
	 * 				| 	Then this.setToughness(attributeValue)
	 */
	private void setAttribute(String attributeKind, int attributeValue) {
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
			default:
				break;
		}
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
		if(weight <= getMinWeight())
			weight = getMinWeight();
		this.weight = weight;
	}

	/**
	 * @return 	  the minWeight of a unit
	 * 			| (this.getStrength()+this.getAgility())/2
	 */

	private double getMinWeight(){
		return (this.getStrength()+this.getAgility())/2;
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
	 * @return	  the minimum number of weight, agility, strength,toughness
	 */
	private double getMinAttributeValue(){
		return MIN_ATTRIBUTE_VALUE;
	}

	/**
	 * @return	  the maximum number of weight, agility, strength,toughness
	 */
	private double getMaxAttributeValue(){
		return MAX_ATTRIBUTE_VALUE;
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
	 * @param 		attributeValue
	 * 				  The attribute value to change
	 * 
	 * @return		  the  modulo of attribute value 
	 * 				| Result == this.getMinInitialAttributeValue()
	 *				| + (attributeValue - this.getMinInitialAttributeValue())
	 *				| % (this.getMaxInitialAttributeValue() - this.getMinInitialAttributeValue())
	 */
	private int getAttributeValueWithinInitialRangeFromTooLargeValue(int attributeValue) {
		return this.getMinInitialAttributeValue()
				+ (attributeValue - this.getMinInitialAttributeValue())
				% (this.getMaxInitialAttributeValue() - this.getMinInitialAttributeValue());
	}

	/**
	 * @param 	  hitpoints
	 * 			  The amount of hitpoints need to be set
	 *
	 * @post 	  The hitpoints must be valid
	 * 			| isValidHitPoints(hitpoints)
	 *
	 */

	private void setCurrentHitPoints(double hitpoints){
		assert isValidHitPoints(hitpoints);
		this.currentHitPoints = hitpoints;
	}

	/**
	 * @param 	  hitpoints
	 *
	 * @return 	  True if the amounts of hitpoints larger or equals to zero
	 *            and is smaller or equals to the maximum hitpoints the unit can have
	 *         	| Result == ( (0 <= hitpoints) && ( hitpoints <= this.maxHitPoints())
	 */
	private boolean isValidHitPoints(double hitpoints){
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

	/**
	 * @return	  the minimum hit points a unit can have
	 * 			| Result == MIN_HIT_POINTS
	 */
	private double getMinHitPoints(){
		return MIN_HIT_POINTS;
	}

	/**
	 * Set the currentStaminaPoints of this Unit to the given currentStaminaPoints.
	 *
	 * @param  	  stamina
	 *         	  The new currentStaminaPoints for this Unit.
	 * @pre    	  The given currentStaminaPoints must be a valid currentStaminaPoints for any
	 *         	  Unit.
	 *       	| isValidStamina(currentStaminaPoints)
	 * @post   	  The currentStaminaPoints of this Unit is equal to the given
	 *         	  currentStaminaPoints.
	 *       	| new.getCurrentStaminaPoints() == currentStaminaPoints
	 */
	@Raw
	private void setCurrentStaminaPoints(double stamina) {
		assert isValidStamina(stamina);
		this.currentStaminaPoints = stamina;
	}

	/**
	 * Check whether the given currentStaminaPoints is a valid currentStaminaPoints for
	 * any Unit.
	 *
	 * @param  	  stamina
	 *         	  The currentStaminaPoints to check.
	 * @return
	 *       	| result == (currentStaminaPoints >= 0) && (currentStaminaPoints <= this.getMaxStaminaPoints())
	 */
	private boolean isValidStamina(double stamina) {
		if(stamina <= this.getMaxStaminaPoints() && stamina >= this.getMinStaminaPoints())
			return true;
		return false;
	}

	/**
	 * Returns the maximum amount of currentStaminaPoints points for any Unit.
	 *
	 * @return 	| result == 200*(this.weight/100)*(this.toughness/100)
	 */
	public double getMaxStaminaPoints() {
		return ( 200.0 * (this.getWeight()/100.0) * (this.getToughness()/100.0));
	}

	/**
	 * @return	  the minimum stamina points a unit can have
	 * 			| Result == MIN_STAMINA_POINTS
	 */
	private double getMinStaminaPoints(){
		return MIN_STAMINA_POINTS;
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

	/**
	 * 				  A method that update the units state, orientation and position
	 *
	 * @param		  dt
	 * 				  Time interval
	 *
	 */
	public void advanceTime(double dt)throws IllegalArgumentException {
		if(dt <= 0 && dt >= 0.2)
			throw new IllegalArgumentException();
		//When the unit State is MOVING then to this
		if (this.getState() == State.MOVING)
			advanceWhileMoving(dt);
		//set the speed of the unit to 0 when it's not moving
		if(this.getState() != State.MOVING)
			this.setCurrentSpeed(0);

		//When the unit State is RESTING then to this
		if (this.getState() == State.RESTING)
			advanceWhileResting(dt);

		//When the unit State is WORKING then to this
		if (this.getState() == State.WORKING)
			advanceWhileWorking(dt);

		//When the unit State is ATTACKING then to this
		if (this.getState() == State.ATTACKING) {
			advanceWhileAttacking(dt);
		}

		//When NeedToRestCounter is smaller then 0 the unit needs to rest
		this.setNeedToRestCounter(this.getNeedToRestCounter()-dt);
		if (this.getNeedToRestCounter() <= 0) {
			if (this.getState() != State.ATTACKING) {
				this.rest();
				//reset the NEEDTOREST_COUNTER
				this.resetCounter("NEEDTOREST_COUNTER");
			}
		}

		//If the units default behavior is enabled start a new behavior
		if(this.getDefaultBehaviorEnabled()){
			try {
				this.startDefaultBehavior();
			} catch (IllegalStateException exc) {

			}
		}
	}

	/**
	 * @return 	  Returns the current state of this unit.
	 * 			| result == this.state
	 */
	@Basic
	public State getState(){
		return this.state;
	}

	/**
	 * @param		  dt	
	 * 				  Time interval
	 * 
	 * 				  
	 */
	private void advanceWhileMoving(double dt) {
		if (this.isSprinting()) {
			this.setCurrentSpeed(this.getUnitWalkSpeed());
			this.setSprintCounter(this.getSprintCounter()-dt);
			if (this.getSprintCounter() <= 0) {
				if (this.getCurrentStaminaPoints() <= 0) {
					this.stopSprinting();
				} else {
					this.setCurrentStaminaPoints(this.getCurrentStaminaPoints() - 1);
					//Reset the SPRINT_COUNTER
					this.resetCounter("SPRINT_COUNTER");
				}
			}
		}else{
			this.setCurrentSpeed(this.getUnitBaseSpeed());
		}
		if(!isDefending)
			this.setOrientation((float) Math.atan2(this.getUnitVelocity()[1], this.getUnitVelocity()[0]));
		this.setNeighboringCubeToMoveTo(getMovementChange());
		this.updatePosition(dt);
		
	}

	/**
	 *
	 * @return	  true if the unit is sprinting else return false
	 * 			| result == this.isSprinting
	 *
	 */
	@Basic
	public boolean isSprinting(){
		return this.isSprinting;
	}

	/**
	 * @param 	   	speed
	 * 				  The speed of the unit
	 * @post		  the currentSpeed is equals to speed
	 * 				| this.currentSpeed = speed 	
	 */
	private void setCurrentSpeed(double speed){
		this.currentSpeed = speed;
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
	private double getUnitWalkSpeed() {
		double moveSpeed;
		if((getNeighboringCubeToMoveTo()[2]) == 1)
			moveSpeed = 0.5*getUnitBaseSpeed();
		else if((getNeighboringCubeToMoveTo()[2]) == -1)
			moveSpeed =1.2* getUnitBaseSpeed();
		else
			moveSpeed = getUnitBaseSpeed();
		if(isSprinting)
			moveSpeed = 2*moveSpeed;
		return moveSpeed;
	}

	/**
	 * @return		  The neighboringCubeToMoveTo
	 * 				| Result == this.neighboringCubeToMoveTo
	 */
	@Basic
	private int[] getNeighboringCubeToMoveTo(){
		return this.neighboringCubeToMoveTo;
	}

	/**
	 * @return 	  the base speed of a unit determined by the unit's weight, strength and agility
	 * 			| Result == 1.5*((this.getStrength() + this.getAgility)/(200*(this.getWeight/100))
	 */
	private double getUnitBaseSpeed() {
		return 1.5*((this.getStrength()+this.getAgility())/(200*this.getWeight()/100));
	}

	/**
	 * @param		  time
	 *
	 * @post		  Set the sprintCounter equals to the given time
	 * 				| new.sprintCounter == time
	 *
	 */
	private void setSprintCounter(double time){
		this.sprintCounter = time;
	}

	/**
	 *
	 *
	 * @return		  Gives the current value of sprintCounter
	 * 				| Result == this.sprintCounter
	 *
	 */
	@Basic
	private double getSprintCounter() {
		return this.sprintCounter;
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
	 * 				  Return the currentStaminaPoints of this Unit.
	 * @return		| result == this.currentStaminaPoints
	 */
	@Basic @Raw
	public double getCurrentStaminaPoints() {
		return this.currentStaminaPoints;
	}

	/**
	 * @param 	  counter
	 * 			  the counter that need to be reseted
	 *
	 * @post	  The given counter is set to it's default value
	 * 			| if( counter== SPRINT_COUNTER)
	 * 			|	then new.setSprintCounter(SPRINT_TIME)
	 * 			| else if(counter == REST_COUNTER)
	 * 			|	then new.setRestCounter(REST_TIME)
	 * 			| else if(counter == WORK_COUNTER)
	 * 			|	then new.setWorkCounter(this.getTimeForWork())
	 * 			| else if(counter == FIGHT_COUNTER)
	 * 			|	then new.setFightCounter(this.getFightTime())
	 * 			| else if( counter == NEEDTOREST_COUNTER)
	 * 			|	then new.setNeedToRestCounter(NEED_TO_REST_TIME)
	 * 			| else
	 * 			|	then return
	 */
	private void resetCounter(String counter){
		switch (counter) {
			case "SPRINT_COUNTER":
				this.setSprintCounter(SPRINT_TIME);
				break;
			case "REST_COUNTER":
				this.setRestCounter(REST_TIME);
				break;
			case "WORK_COUNTER":
				this.setWorkCounter(this.getTimeForWork());
				break;
			case "FIGHT_COUNTER":
				this.setFightCounter(this.getFightTime());
				break;
			case "NEEDTOREST_COUNTER":
				this.setNeedToRestCounter(NEED_TO_REST_TIME);
		}
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
	 * @return		  a double array of the velocity in the x,y and z axis
	 * 				| Result == new double[]{
	 *				|	this.getUnitWalkSpeed() * (this.getNeighboringCubeToMoveTo()[0])/ distance,
	 *				|	this.getUnitWalkSpeed() * (this.getNeighboringCubeToMoveTo()[1])/ distance,
	 *				|	this.getUnitWalkSpeed() * (this.getNeighboringCubeToMoveTo()[2])/ distance,}
	 * 	
	 */
	private double[] getUnitVelocity() {
		double distance = getDistance();
		return new double[]
				{
						this.getUnitWalkSpeed() * (this.getNeighboringCubeToMoveTo()[0])/ distance,
						this.getUnitWalkSpeed() * (this.getNeighboringCubeToMoveTo()[1])/ distance,
						this.getUnitWalkSpeed() * (this.getNeighboringCubeToMoveTo()[2])/ distance,
				};
	}

	/**
	 * @return		  the distance
	 * 				| Result == Math.sqrt(
	 *				|	Math.pow(this.getNeighboringCubeToMoveTo()[0], 2) +
	 *				|	Math.pow(this.getNeighboringCubeToMoveTo()[1], 2) +
	 *				|	Math.pow(this.getNeighboringCubeToMoveTo()[2], 2))
	 */
	private double getDistance() {
		return Math.sqrt(
						Math.pow(this.getNeighboringCubeToMoveTo()[0], 2) +
								Math.pow(this.getNeighboringCubeToMoveTo()[1], 2) +
								Math.pow(this.getNeighboringCubeToMoveTo()[2], 2)
				);
	}

	/**
	 * @param 		  neighboringCubeToMoveTo
	 * 
	 * @post		  Set the neighboringCubeToMoveTo equals to neighboringCubeToMoveTo
	 * 				| new.neighboringCubeToMoveTo = neighboringCubeToMoveTo
	 */
	private void setNeighboringCubeToMoveTo(int[] neighboringCubeToMoveTo){
		this.neighboringCubeToMoveTo = neighboringCubeToMoveTo;
	}

	/**
	 * @return		  The the displacement a unit has to do
	 *
	 */
	private int[] getMovementChange() {
		int dx;
		int dy;
		int dz;
		if(position.getCubeCoordinates()[0]== this.getTargetPosition()[0]){
			dx = 0;
		}else if(position.getCubeCoordinates()[0]< this.getTargetPosition()[0]){
			dx = 1;
		}else{
			dx = -1;
		}

		if(position.getCubeCoordinates()[1]== this.getTargetPosition()[1]){
			dy = 0;
		}else if(position.getCubeCoordinates()[1]< this.getTargetPosition()[1]){
			dy = 1;
		}else{
			dy = -1;
		}

		if(position.getCubeCoordinates()[2]== this.getTargetPosition()[2]){
			dz = 0;
		}else if(position.getCubeCoordinates()[2]< this.getTargetPosition()[2]){
			dz = 1;
		}else{
			dz = -1;
		}
		return new int[]{dx,dy,dz};
	}

	/**
	 * 				Method that's calls in advanceTime and update the Units position
	 * 
	 * @param 		dt
	 * 				  Difference in time
	 */
	private void updatePosition(double dt) {
		this.position.unitX += this.getUnitVelocity()[0] * dt;
		this.getInitialPosition()[0] += this.getUnitVelocity()[0] * dt;
		this.position.unitY += this.getUnitVelocity()[1] * dt;
		this.getInitialPosition()[1] += this.getUnitVelocity()[1] * dt;
		this.position.unitZ += this.getUnitVelocity()[2] * dt;
		this.getInitialPosition()[2] += this.getUnitVelocity()[2] * dt;
		if (Math.abs(this.getNeighboringCubeToMoveTo()[0]) - Math.abs(getInitialPosition()[0]) <= 0 &&
				Math.abs(this.getNeighboringCubeToMoveTo()[1]) - Math.abs(getInitialPosition()[1]) <= 0 &&
				Math.abs(this.getNeighboringCubeToMoveTo()[2]) - Math.abs(getInitialPosition()[2]) <= 0) {
			this.position.setUnitCoordinates(new int[]{
					this.position.getCubeCoordinates()[0] + this.getNeighboringCubeToMoveTo()[0],
					this.position.getCubeCoordinates()[1] + this.getNeighboringCubeToMoveTo()[1],
					this.position.getCubeCoordinates()[2] + this.getNeighboringCubeToMoveTo()[2]
			});
			if (Arrays.equals(this.position.getCubeCoordinates(), this.getTargetPosition())) {
				this.setState(State.NONE);
				this.stopSprinting();
			}
			if(this.getNewTargetPosition() != null)
				this.setTargetPosition(this.getNewTargetPosition());
			this.setNewTargetPosition(null);
			this.setInitialPosition(new double[]{0, 0, 0});
		}
	}

	/**
	 * @return 		  the initialPosition of a unit
	 * 				| Result == this.initialPosition
	 */
	@Basic
	private double[] getInitialPosition() {
		return this.initialPosition;
	}

	/**
	 * @return 		  the targetPosition of a unit
	 * 				| Result == this.targetPosition
	 */
	@Basic
	private int[] getTargetPosition(){
		return this.targetPosition;
	}

	/**
	 * @param 	  state
	 *
	 * @post 	  The new state is equal to the given state.
	 * 			| new.getState() == state
	 *
	 */
	private void setState(State state) {
		this.state = state;
	}

	/**
	 * @return 		  the newTargetPosition of a unit( != targetPosition)
	 * 				| Result == this.newTargetPosition
	 */
	@Basic
	int[] getNewTargetPosition(){
		return this.newTargetPosition;
	}

	/**
	 * @param 	  targetPosition
	 *
	 * @post 	  The new targetPosition is equal to the given targetPosition.
	 * 			| new.targetPosition == targetPosition
	 *
	 */
	private void setTargetPosition(int[] targetPosition){
		this.targetPosition = targetPosition;
	}

	/**
	 * @param 	  newTargetPosition
	 *
	 * @post 	  The new newTargetPosition is equal to the given newTargetPosition.
	 * 			| new.newTargetPosition == newTargetPosition
	 *
	 */
	private void setNewTargetPosition(int[] newTargetPosition){
		this.newTargetPosition = newTargetPosition;
	}

	/**
	 * @param 	  initialPosition
	 *
	 * @post 	  The new newTargetPosition is equal to the given initialPosition.
	 * 			| new.initialPosition == initialPosition
	 *
	 */
	private void setInitialPosition(double[] initialPosition){
		this.initialPosition = initialPosition;
	}

	private double currentSpeed;

	/**
	 * @return 		  the currentSpeed of a unit
	 * 				| Result == this.currentSpeed
	 */	
	public double getCurrentSpeed(){
		return this.currentSpeed;
	}

	/**
	 * @param		  dt
	 * 				  Time interval
	 *
	 */
	private void advanceWhileResting(double dt) {
		if (this.getCurrentHitPoints() != this.getMaxHitPoints()) {
			this.setRestCounter(this.getRestCounter()-dt);
			if (this.getRestCounter() <= 0) {
				this.setCurrentHitPoints(this.getCurrentHitPoints() + this.getRegenHitPoints());
				// reset the REST_COUNTER
				this.resetCounter("REST_COUNTER");
			}
		} else {
			this.setRestCounter(this.getRestCounter()-dt);
			if (this.getRestCounter() <= 0) {
				this.setCurrentStaminaPoints(this.getCurrentStaminaPoints() + this.getRegenStamina());
				// reset the REST_COUNTER
				this.resetCounter("REST_COUNTER");
			}
		}
		if (this.getCurrentStaminaPoints() == this.getMaxStaminaPoints() && this.getCurrentHitPoints() == this.getMaxHitPoints()) {
			this.setState(State.NONE);
		}
	}

	/**
	 * @return 	  	  return the current hitpoints of the unit
	 * 				| Result == this.currentHitPoints
	 */
	@Basic
	public double getCurrentHitPoints(){
		return this.currentHitPoints;
	}

	/**
	 * @param		  time
	 *
	 * @post		  Set the restCounter equals to the given time
	 * 				| new.restCounter == time
	 *
	 */
	private void setRestCounter(double time){
		this.restCounter = time;
	}

	/**
	 *
	 *
	 * @return		  Gives the current value of restCounter
	 * 				| Result == this.restCounter
	 *
	 */
	@Basic
	private double getRestCounter(){
		return this.restCounter;
	}

	/**
	 * @return 	  gives the amount hitpoinst need to regenerate per time unit of REGEN_REST_TIME
	 * 			| result == (this.getToughness)/200
	 */
	private double getRegenHitPoints(){
		return (this.getToughness()/200.0);
	}

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
	 * @return 	  gives the amount Stamina points need to regenerate per time unit of REGEN_REST_TIME
	 * 			| restult == (this.getToughness)/100
	 */
	private double getRegenStamina(){
		return this.getToughness()/100.0;
	}

	/**
	 * @param		  dt
	 * 				  Time interval
	 */
	private void advanceWhileWorking(double dt) {
		this.setWorkCounter(this.getWorkCounter()-dt);
		if (this.getWorkCounter() <= 0) {
			this.setState(State.NONE);
			// reset the WORK_COUNTER
			this.resetCounter("WORK_COUNTER");
		}
	}

	/**
	 * @param		  time
	 *
	 * @post		  Set the workCounter equals to the given time
	 * 				| new.workCounter == time
	 *
	 */
	private void setWorkCounter(double time){
		this.workCounter = time;
	}

	/**
	 *
	 *
	 * @return		  Gives the current value of workCounter
	 * 				| Result == this.workCounter
	 *
	 */
	@Basic
	private double getWorkCounter() {
		return this.workCounter;
	}

	/**
	 *
	 * @param		  dt
	 * 				  Time interval
	 *
	 *
	 */
	private void advanceWhileAttacking(double dt) {
		this.setOrientation((float) Math.atan2(
				this.getDefender().position.getUnitCoordinates()[1] - this.position.getUnitCoordinates()[1],
				this.getDefender().position.getUnitCoordinates()[0] - this.position.getUnitCoordinates()[0]));
		this.getDefender().setOrientation((float) Math.atan2(
				this.position.getUnitCoordinates()[1] - this.getDefender().position.getUnitCoordinates()[1],
				this.position.getUnitCoordinates()[0] - this.getDefender().position.getUnitCoordinates()[0])
		);
		this.setFightCounter(this.getFightCounter() -dt);
		if (this.getFightCounter() <= 0) {
			this.setState(State.NONE);
			this.getDefender().updateUnitState();
			this.getDefender().isDefending= false;
			//reset the FIGHT_COUNTER
			this.resetCounter("FIGHT_COUNTER");
		}

	}

	/**
	 * @return		  The unit that's current defending against this unit
	 * 				| Result == this.defender
	 */
	@Basic
	private Unit getDefender(){
		return this.theDefender;
	}

	/**
	 * @param		  time
	 *
	 * @post		  Set the fightCounter equals to the given time
	 * 				| new.fightCounter == time
	 *
	 */
	private void setFightCounter(double time){
		this.fightCounter = time;
	}

	/**
	 *
	 *
	 * @return		  Gives the current value of fightCounter
	 * 				| Result == this.fightCounter
	 *
	 */
	@Basic
	private double getFightCounter() {
		return this.fightCounter;
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
	private void updateUnitState(){
		if(this.getPreviousState() == State.WORKING)
			this.setState(State.NONE);
		else
			this.setState(this.getPreviousState());
		if(this.getState()!=State.NONE)
			this.setOrientation(this.getPreviousOrientation());
	}

	/**
	 * @return	  Gives the units previous State
	 * 			| Result = this.previousState
	 */
	@Basic
	private State getPreviousState(){
		return this.previousState;
	}

	/**
	 * @return	  Gives the units previous orientation
	 * 			| Result = this.previousOrientation
	 */
	@Basic
	private float getPreviousOrientation(){
		return this.previousOrientation;
	}

	/**
	 * @param		  time
	 *
	 * @post		  Set the needToRestCounter equals to the given time
	 * 				| new.needToRestCounter == time
	 *
	 */
	private void setNeedToRestCounter(double time){
		this.needToRestCounter = time;
	}

	/**
	 *
	 *
	 * @return		  Gives the current value of needToRestCounter
	 * 				| Result == this.needToRestCounter
	 *
	 */
	@Basic
	private double getNeedToRestCounter() {
		return this.needToRestCounter;
	}

	/**
	 * @post 	  the state of the unit is set to rest
	 * 			| new.setState(State.RESTING)
	 *
	 * @effect 	  set the state of the unit to work
	 * 			| this.setState(State.RESTING)
	 *
	 * @throws IllegalStateException
	 * 			  When a unit has the maximum hitpoints and the maximum of currentStaminaPoints
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
	 *
	 * @return		  Gives the current value of defaultBehaviorEnabled
	 * 				| Result == this.defaultBehaviorEnabled
	 *
	 */
	@Basic
	public Boolean getDefaultBehaviorEnabled(){
		return this.defaultBehaviorEnabled;
	}

	/**
	 * @post 	  choose a random state move, conduct a work task, rest until it has full recovered currentHitPoints and currentStaminaPoints
	 *
	 * @post  	  if is moving sprinting till it's exhausted
	 *
	 * @throws IllegalStateException
	 * 			  if the unit is doing a state
	 */
	private void startDefaultBehavior() throws IllegalStateException{
		if(this.getState()!= State.NONE)
			throw new IllegalStateException();
		int randomBehaviorNumber =new Random().nextInt(5);
		if(randomBehaviorNumber== 0) {
			moveTo(new int[]{new Random().nextInt(50), new Random().nextInt(50), new Random().nextInt(50)});
			startSprinting();
		}
		else if(randomBehaviorNumber == 1) {
			work();
		}
		else
			try {
				rest();
			} catch (IllegalStateException exc) {
				startDefaultBehavior();
			}
	}

	// ======================
	// ==== Facade calls ====
	// ======================

	/**
	 *@return		  Return the name of this Unit.
	 *				| Result == this.name
	 */
	@Basic @Raw
	public String getName() {
		return this.name;
	}

	/**
	 * @return 	  Returns the current weight of the unit.
	 * 			| result == this.weight
	 */
	@Basic
	public double getWeight(){
		return this.weight;
	}

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
		this.setTargetPosition(new int[]{
				this.position.getCubeCoordinates()[0] + dx,
				this.position.getCubeCoordinates()[1] + dy,
				this.position.getCubeCoordinates()[2] + dz
		});
		this.setState(State.MOVING);
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
			this.setNewTargetPosition(targetposition);
		}else{
			this.setTargetPosition(targetposition);
			this.setState(State.MOVING);
		}
	}

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
		this.setWorkCounter(this.getTimeForWork());
		this.setState(State.WORKING);
	}

	/**
	 *
	 * @return 	  gives the time its takes for a working
	 * 			| result == 500/this.getStrength
	 *
	 */
	private float getTimeForWork(){
		return (float) (500/this.getStrength());
	}

	/**
	 * Attack other unit that occupies the same or a neighbouring cube
	 * of the game world.
	 *
	 * @param 	  defender
	 * 			  The defending unit.
	 * @effect	  This unit fights the defender.
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
			this.setFightCounter(this.getFightTime());
			this.setState(State.ATTACKING);
			this.setDefender(defender);
			this.getDefender().defend(this.getAgility(), this.getStrength());
		}
	}

	/**
	 * @param 	cubeCoordinatesOfPossibleNeighbor
	 * 			  The cube coordinates to check
	 * 	
	 * @return 	  True if the given coordinate is a neighboring cube
	 * 			  False if the given coordinate isn't a neighboring cube 
	 */
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
	 * @return	  The time needed for fighting
	 * 			| Result == FIGHT_TIME		
	 */
	@Basic @Immutable
	private double getFightTime() {
		return FIGHT_TIME;
	}

	/**
	 *
	 * @param 		  defender
	 * 				  Set the unit that will be defending against this unit
	 * 				| this.theDefender == defender
	 *
	 */
	private void setDefender(Unit defender){
		this.theDefender= defender;
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
	 * 			  random neighBouring cube.
	 * 			| 1 <= 0.20 * (this.getAgility() / attacker.getAgility())
	 * @post 	  When this unit fails to dodge the attack,
	 * 			  this unit blocks the attack when the sum of it's
	 * 			  strength and agility, relative to those of its attacker
	 * 			  is high enough.
	 * 			| 1 <= 0.25 * ( (this.getStrength() + this.getAgility())
	 * 			|	/ (attacker.getStrength() + attacker.getAgility()) )
	 * @post	  When this unit fails to dodge or block the attack,
	 * 			  this unit's hitPoints are lowered,
	 * 			  relative to the strength of the attacker.
	 * 			| new.getCurrentHitPoints() == this.getCurrentHitPoints() - (attacker.getStrength() / 10)
	 */
	private void defend(double attackerAgility, double attackerStrength) {
		this.isDefending = true;
		double chance = Math.random();
		if(chance < this.chanceForDodging(attackerAgility)){
			this.dodge();
			return;
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
	 * @param 	  strength
	 * 			  the strength of the attacking unit
	 * @return	  the damage that has to be dealt to the defender
	 * 			| result == strength/10
	 */
	private double damage(double strength){
		return strength/10;
	}

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
	private void saveUnitSate(){
		this.setPreviousState(this.getState());
		if(this.getState()!=State.NONE)
			this.setPreviousOrientation(this.getOrientation());
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
	 * @param	  previousOrientation
	 * 			  The previous orientation of the unit
	 * @post	  Set previousOrentation equals to the current State
	 * 			| new.previousOrentation = previousOrentation
	 */
	private void setPreviousOrientation(float previousOrientation){
		this.previousOrientation = previousOrientation;
	}
}