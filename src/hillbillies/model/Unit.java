package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.gameobject.Boulder;
import hillbillies.model.gameobject.Faction;
import hillbillies.model.gameobject.Log;
import hillbillies.model.terrain.Rock;
import hillbillies.model.terrain.Tree;
import hillbillies.model.terrain.Workshop;
import sun.security.jca.GetInstance.Instance;

import java.util.Arrays;
import java.util.Random;

import org.hamcrest.core.IsInstanceOf;

/**
 * A class for a 'cubical object that occupies a position in the game world'.
 * 
 * @author  Iwein Bau & Joppe Geluykens
 * 
 * @note  A Unit is a basic type of in-game character with the ability to move around,
 * interact with other such characters and manipulate the game world.
 * 
 * @invar  The amount of current hit points must be valid
 * hit points for any unit.
 * | isValidHitPoints(this.getCurrentHitPoints())
 * @invar    The current stamina points of each unit must be valid
 * stamina points for any unit.
 * | isValidStamina(this.getCurrentStaminaPoints())
 * @invar    The name of each unit must be a valid
 * name for any unit.
 * | isValidName(this.getName())
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
		this.setPosition(new Position(initialPosition));
		this.initializeAttribute(0, toughness);
		this.initializeAttribute(1, agility);
		this.initializeAttribute(2, strength);
		this.initializeAttribute(3, weight);
		this.setCurrentHitPoints(this.getMaxHitPoints());
		this.setCurrentStaminaPoints(this.getMaxStaminaPoints());
		this.setDefaultBehaviorEnabled(enableDefaultBehavior);
		this.setState(State.NONE);
	}

	/**
	 * Variable registering the current name of this Unit.
	 */
	private String name;

	/**
	 * Variable registering the target cube this unit is
	 * moving towards when moving.
	 */
	private int[] targetPosition = new int[] {0, 0, 0};

	/**
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
	private boolean sprinting = false;

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
	private State state;

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
	
	private double currentSpeed;

	private boolean restRequestedWhileMoving = false;

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
	Faction faction;

	World world;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position position;
	private int currentExperiencePoints;
	private int randomAttributePointCounter;
	private boolean falling;
	Log log = null;
	Boulder boulder = null;
    boolean alive = true;
    private int[] cubeWorkOn;
    workActivity workActivity;

	/**
	 * Return the workActivity of this Unit.
	 */
	public workActivity getWorkActivity() {
		return workActivity;
	}

	/**
	 * Set the workActivity of this Unit to the given workActivity.
	 *
	 * @param  workActivity
	 *         The workActivity to set.
	 * @post   The workActivity of this of this Unit is equal to the given workActivity.
	 *       | new.getworkActivity() == workActivity
	 */
	public void setWorkActivity(workActivity workActivity) {
		this.workActivity = workActivity;
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
		if (! isValidName(name) || name == null)
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
	 * Initalize the given attribute with the given value if its in range.
	 *
	 * @param attributeKind
	 * 			  The attribute of this unit to initialize.
	 * @param attributeValue
	 * 			  The value to initialize this unit's attribute with.
	 * @effect 	  If the given value is within the minimum and maximum
	 * 			  initial attribute value range, the attribute's value is set
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
	private void initializeAttribute(int attributeKind, int attributeValue) {
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
	private void setAttribute(int attributeKind, int attributeValue) {
		switch (attributeKind) {
			case 3:
				this.setWeight(attributeValue);
				break;
			case 1:
				this.setAgility(attributeValue);
				break;
			case 2:
				this.setStrength(attributeValue);
				break;
			case 0:
				this.setToughness(attributeValue);
				break;
			default:
				break;
		}
	}

	/**
	 * Set the weight of this unit to the given weight.
	 *
	 * @param 	  weight
	 *			  The new weight for this unit.
	 * @post 	  If the given weight is within the range established by
	 * 			  the minimum weight and maximum attribute value for this unit,
	 * 			  then the weight of this unit is equal to the given weight.
	 * 			| if (weight >= this.getMinWeight()) && (weight <= this.getMaxAttributeValue())
	 * 			|	then new.getWeight() == weight
	 * @post	  If the given weight exceeds the maximum attribute value,
	 * 			  then the weight of this unit is equal to the max attribute value.
	 * 			| if (weight > this.getMaxAttributeValue())
	 * 			| 	then new.getWeight() == this.getMaxAttributeValue()
	 * @post	  If the given weight is lower than the minimum weight value of this unit,
	 * 			  then the weight of this unit is equal to the minimum weight value.
	 * 			| if (weight < this.getMinWeight())
	 * 			|	then new.getWeight() == this.getMinWeight()
	 */
	public void setWeight(double weight) {
		if (weight > this.getMaxAttributeValue()) {
			this.weight = this.getMaxAttributeValue();
		}
		if (weight < this.getMinWeight()) {
			this.weight = this.getMinWeight();
		}
		if (weight >= this.getMinWeight() && weight <= this.getMaxAttributeValue())
			this.weight = weight;
	}

	/**
	 * @return 	  The minimum weight for any unit.
	 * 			| result == (this.getStrength() + this.getAgility()) / 2
	 */

	private double getMinWeight() {
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
	 * @return	  The minimum value for the agility, strength and toughness
	 * 			  for any unit.
	 */
	private double getMinAttributeValue() {
		return MIN_ATTRIBUTE_VALUE;
	}

	/**
	 * @return	  The maximum value for the weight, agility, strength and toughness
	 * 			  for any unit.
	 */
	private double getMaxAttributeValue() {
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
	 * Return a value, based on the given one, within range for initialization of an attribute.
	 *
	 * @param attributeValue
	 * 			  The attribute value to get a proper initial value for.
	 * 
	 * @return	  The  modulo of attribute value
	 * 			| Result == this.getMinInitialAttributeValue()
	 *			| + (attributeValue - this.getMinInitialAttributeValue())
	 *			| % (this.getMaxInitialAttributeValue() - this.getMinInitialAttributeValue())
	 */
	private int getAttributeValueWithinInitialRangeFromTooLargeValue(int attributeValue) {
		return this.getMinInitialAttributeValue()
				+ (attributeValue - this.getMinInitialAttributeValue())
				% (this.getMaxInitialAttributeValue() - this.getMinInitialAttributeValue());
	}

	/**
	 * Set the hit points of this unit to the given hit points.
	 *
	 * @param hitPoints
	 * 			  The new hit points for this unit.
	 *
	 * @pre 	  The hitPoints must be valid.
	 * 			| isValidHitPoints(hitPoints)
	 * @post 	  If the given hit points are valid,
	 * 			  the hit points of this unit are set to them.
	 *			| new.getCurrentHitPoints() == hitPoints
     * TODO: 16/03/16 Die when 0 hitpoints left.
	 */
	private void setCurrentHitPoints(double hitPoints) {
		this.currentHitPoints = hitPoints;
	}

	/**
	 * @param 	  hitPoints
	 *
	 * @return 	  True if the given hit points are smaller than the maximum hit points for any unit
	 * 			  and larger than the minimum hit points for any unit.
	 *         	| result == ( (this.getMinHitPoints() <= hitPoints) && ( hitPoints <= this.getMaxHitPoints())
	 */
	private boolean isValidHitPoints(double hitPoints) {
		if(hitPoints <= this.getMaxHitPoints() && hitPoints >=getMinHitPoints())
			return true;
		return false;
	}

	/**
	 * @return 	  Returns the maximum hit points this unit can have.
	 * 			| result == 200 * (this.getWeight()/100) * (this.getToughness()/100)
	 */
	public double getMaxHitPoints() {
		return (200.0 * (this.getWeight()/100.0) * (this.getToughness()/100.0));
	}

	/**
	 * @return	  The minimum amount of hit points for any unit.
	 * 			| Result == Unit.MIN_HIT_POINTS
	 */
	private double getMinHitPoints() {
		return Unit.MIN_HIT_POINTS;
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
	 */
	public void setDefaultBehaviorEnabled(Boolean toggle) {
		this.defaultBehaviorEnabled= toggle;
	}

	/**
	 * A method that updates the unit's state, orientation and position.
	 *
	 * @param dt
	 * 			  Time interval
     * TODO: 16/03/16 Update hitpoints when falling.
     */
	public void advanceTime(double dt) throws IllegalArgumentException {
		if(dt <= 0 && dt >= 0.2)
			throw new IllegalArgumentException();
		if(this.hasEnoughExperiencePoints()){
			this.incrementRandomAtrributeValue();
		}
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
				try {
					this.rest();
				} catch (Exception exc) {
					// Do Nothing
				}
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
	 * Returns the current state of this unit.
	 *
	 * @return 	  The current state of this unit.
	 * 			| result == this.state
	 */
	@Basic
	public State getState(){
		return this.state;
	}

	/**
	 * @param dt
	 * 			  Time interval
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
			this.setCurrentSpeed(this.getUnitWalkSpeed());
		}
		if(!isDefending)
			this.setOrientation((float) Math.atan2(this.getUnitVelocity()[1], this.getUnitVelocity()[0]));
		this.setNeighboringCubeToMoveTo(getMovementChange());
		this.updatePosition(dt);
		
	}

	/**
	 * Returns whether or not this unit is currently sprinting.
	 *
	 * @return	  true if the unit is sprinting, false otherwise.
	 * 			| result == this.sprinting
	 */
	@Basic
	public boolean isSprinting(){
		return this.sprinting;
	}

	/**
	 * Set the current speed of this unit to the given speed.
	 *
	 * @param speed
	 * 			  The speed of the unit
	 * @post	  The current speed is equal to the given speed.
	 * 			| new.currentSpeed == speed
	 */
	private void setCurrentSpeed(double speed){
		this.currentSpeed = speed;
	}

	/**
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
	 * TODO: 16/03/16 Update speed when falling.
	 */
	private double getUnitWalkSpeed() {
		double moveSpeed;
		if((getNeighboringCubeToMoveTo()[2]) == 1)
			moveSpeed = 0.5*getUnitBaseSpeed();
		else if((getNeighboringCubeToMoveTo()[2]) == -1)
			moveSpeed =1.2* getUnitBaseSpeed();
		else
			moveSpeed = getUnitBaseSpeed();
		if(isSprinting())
			moveSpeed = 2*moveSpeed;
		return moveSpeed;
	}

	/**
	 * @return	  The neighboringCubeToMoveTo
	 * 			| result == this.neighboringCubeToMoveTo
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
	 * @param time
	 *
	 * @post	  Set the sprintCounter equals to the given time
	 * 			| new.sprintCounter == time
	 */
	private void setSprintCounter(double time){
		this.sprintCounter = time;
	}

	/**
	 *
	 * @return	  The current value of sprintCounter
	 * 			| result == this.sprintCounter
	 */
	@Basic
	private double getSprintCounter() {
		return this.sprintCounter;
	}

	/**
	 * Stop the sprint of this unit.
	 *
	 * @post 	  if the unit is sprinting set sprinting to false
	 * 			| new.sprinting == false
	 */
	public void stopSprinting(){
		this.sprinting = false;
	}

	/**
	 * Return the current stamina points of this unit.
	 *
	 * @return	The current stamina points.
	 * 	 	  | result == this.currentStaminaPoints
	 */
	@Basic @Raw
	public double getCurrentStaminaPoints() {
		return this.currentStaminaPoints;
	}

	/**
	 * @param counter
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
	 * @param orientation
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
	 * @return	  a double array of the velocity in the x,y and z axis
	 * 			| result == new double[]{
	 *			|	this.getUnitWalkSpeed() * (this.getNeighboringCubeToMoveTo()[0])/ distance,
	 *			|	this.getUnitWalkSpeed() * (this.getNeighboringCubeToMoveTo()[1])/ distance,
	 *			|	this.getUnitWalkSpeed() * (this.getNeighboringCubeToMoveTo()[2])/ distance,}
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
	 * @return	  The distance to the next cube to move to.
	 * 			| result == Math.sqrt(
	 *			|	Math.pow(this.getNeighboringCubeToMoveTo()[0], 2) +
	 *			|	Math.pow(this.getNeighboringCubeToMoveTo()[1], 2) +
	 *			|	Math.pow(this.getNeighboringCubeToMoveTo()[2], 2))
	 */
	private double getDistance() {
		return Math.sqrt(
						Math.pow(this.getNeighboringCubeToMoveTo()[0], 2) +
								Math.pow(this.getNeighboringCubeToMoveTo()[1], 2) +
								Math.pow(this.getNeighboringCubeToMoveTo()[2], 2)
				);
	}

	/**
	 * @param neighboringCubeToMoveTo
	 * 
	 * @post	  Set the neighboringCubeToMoveTo equals to neighboringCubeToMoveTo
	 * 			| new.neighboringCubeToMoveTo = neighboringCubeToMoveTo
	 */
	private void setNeighboringCubeToMoveTo(int[] neighboringCubeToMoveTo){
		this.neighboringCubeToMoveTo = neighboringCubeToMoveTo;
	}

	/**
	 * @return	  The displacement of this unit when moving.
	 *
	 */
	private int[] getMovementChange() {
		int dx;
		int dy;
		int dz;
		if(getPosition().getCubeCoordinates()[0]== this.getTargetPosition()[0]){
			dx = 0;
		}else if(getPosition().getCubeCoordinates()[0]< this.getTargetPosition()[0]){
			dx = 1;
		}else{
			dx = -1;
		}

		if(getPosition().getCubeCoordinates()[1]== this.getTargetPosition()[1]){
			dy = 0;
		}else if(getPosition().getCubeCoordinates()[1]< this.getTargetPosition()[1]){
			dy = 1;
		}else{
			dy = -1;
		}

		if(getPosition().getCubeCoordinates()[2]== this.getTargetPosition()[2]){
			dz = 0;
		}else if(getPosition().getCubeCoordinates()[2]< this.getTargetPosition()[2]){
			dz = 1;
		}else{
			dz = -1;
		}
		return new int[]{dx,dy,dz};
	}

	/**
	 * Method that's calls in advanceTime and update the Units position
	 * 
	 * @param dt
	 * 		  Difference in time
	 */
	private void updatePosition(double dt) {
		this.setPosition(
                new Position(
                        new double[]{
                        		this.getPosition().getDoubleCoordinates()[0] + this.getUnitVelocity()[0] * dt,
                                this.getPosition().getDoubleCoordinates()[1] + this.getUnitVelocity()[1] * dt,
                                this.getPosition().getDoubleCoordinates()[2] + this.getUnitVelocity()[2] * dt
                        }
                )
        );
		this.getInitialPosition()[0] += this.getUnitVelocity()[0] * dt;
		this.getInitialPosition()[1] += this.getUnitVelocity()[1] * dt;
		this.getInitialPosition()[2] += this.getUnitVelocity()[2] * dt;
		if (Math.abs(this.getNeighboringCubeToMoveTo()[0]) - Math.abs(getInitialPosition()[0]) <= 0 &&
				Math.abs(this.getNeighboringCubeToMoveTo()[1]) - Math.abs(getInitialPosition()[1]) <= 0 &&
				Math.abs(this.getNeighboringCubeToMoveTo()[2]) - Math.abs(getInitialPosition()[2]) <= 0) {
			this.setPosition(
                    new Position(
                            new int[]{
                                    this.getPosition().getCubeCoordinates()[0] + this.getNeighboringCubeToMoveTo()[0],
                                    this.getPosition().getCubeCoordinates()[1] + this.getNeighboringCubeToMoveTo()[1],
                                    this.getPosition().getCubeCoordinates()[2] + this.getNeighboringCubeToMoveTo()[2]
                            }
                    )
            );
            // TODO: 16/03/16 Increment experience points, except when interrupted.
			if (Arrays.equals(this.getPosition().getCubeCoordinates(), this.getTargetPosition())) {
				this.setState(State.NONE);
				this.stopSprinting();
			}
			if(this.getNewTargetPosition() != null)
				this.setTargetPosition(this.getNewTargetPosition());
			// check if there is a rest request
			if(this.getRestRequestedWhileMoving())
				try {
					this.saveUnitSate();
					this.setState(State.NONE);
					this.stopSprinting();
					this.rest();
				} catch (IllegalStateException exc) {
					
				}		
			this.setNewTargetPosition(null);
			this.setInitialPosition(new double[]{0, 0, 0});
		}
	}

	/**
	 * @return
	 */
	private boolean getRestRequestedWhileMoving() {
		return this.restRequestedWhileMoving;
	}

	/**
	 * @return 	  The initial position of a unit
	 * 			| result == this.initialPosition
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
	 * @param state
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
	 * @param targetPosition
	 *
	 * @post 	  The new targetPosition is equal to the given targetPosition.
	 * 			| new.targetPosition == targetPosition
	 *
	 */
	private void setTargetPosition(int[] targetPosition){
		this.targetPosition = targetPosition;
	}

	/**
	 * @param newTargetPosition
	 *
	 * @post 	  The new newTargetPosition is equal to the given newTargetPosition.
	 * 			| new.newTargetPosition == newTargetPosition
	 *
	 */
	private void setNewTargetPosition(int[] newTargetPosition){
		this.newTargetPosition = newTargetPosition;
	}

	/**
	 * @param initialPosition
	 *
	 * @post 	  The new newTargetPosition is equal to the given initialPosition.
	 * 			| new.initialPosition == initialPosition
	 *
	 */
	private void setInitialPosition(double[] initialPosition){
		this.initialPosition = initialPosition;
	}

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
		if (this.getCurrentHitPoints() < this.getMaxHitPoints()) {
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
		if (this.getCurrentStaminaPoints() >= this.getMaxStaminaPoints() && this.getCurrentHitPoints() >= this.getMaxHitPoints()) {
			if(this.getPreviousState()== State.MOVING){
				this.updateUnitState();
				this.setRestRequestedWhileMoving(false);
			}else{
				this.setState(State.NONE);
			}
		}
	}

	/**
	 * @return 	  	  return the current hitPoints of the unit
	 * 				| Result == this.currentHitPoints
	 */
	@Basic
	public double getCurrentHitPoints(){
		return this.currentHitPoints;
	}

	/**
	 * @param time
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
	 * 			| Result == (this.getToughness)/100
	 */
	private double getRegenStamina(){
		return this.getToughness()/100.0;
	}

	/**
	 * @param dt
	 * 		  Time interval
     *
     * TODO: 16/03/16 Specify work activity.
	 */
	private void advanceWhileWorking(double dt) {
		this.setWorkCounter(this.getWorkCounter()-dt);
		this.setOrientation((float) Math.atan2(
				this.getCubeToWorkOn()[1]+0.5 - this.getPosition().getDoubleCoordinates()[1],
				this.getCubeToWorkOn()[0]+0.5 - this.getPosition().getDoubleCoordinates()[0]));
		if (this.getWorkActivity() == workActivity.DROPINGBOULDER){
			this.boulder.setPosition(world.getCube(getCubeToWorkOn()[0], getCubeToWorkOn()[1], getCubeToWorkOn()[2]).getPosition());
			world.getCube(getCubeToWorkOn()[0], getCubeToWorkOn()[1], getCubeToWorkOn()[2]).boulder = boulder;
			this.setWeight(this.getWeight()- boulder.getWeight());
			world.getBoulders().add(boulder);
			this.boulder = null;
			this.setState(State.NONE);
		}if (this.getWorkActivity() == workActivity.DROPINGLOG){
			this.log.setPosition(world.getCube(getCubeToWorkOn()[0], getCubeToWorkOn()[1], getCubeToWorkOn()[2]).getPosition());
			world.getCube(getCubeToWorkOn()[0], getCubeToWorkOn()[1], getCubeToWorkOn()[2]).log = log;
			this.setWeight(this.getWeight()- log.getWeight());
			world.getLogs().add(log);
			this.log = null;
			this.setState(State.NONE);
		}
		if(this.getWorkActivity()== workActivity.DIGING){
			world.getCube(getCubeToWorkOn()[0], getCubeToWorkOn()[1], getCubeToWorkOn()[2]).caveIn();
			this.setState(State.NONE);
		}
		if (this.getWorkCounter() <= 0) {
			this.setState(State.NONE);
			// reset the WORK_COUNTER
			if (this.getWorkActivity() == workActivity.PICKINGUPLOG) {
				this.log=(Log) world.getCube(getCubeToWorkOn()[0], getCubeToWorkOn()[1], getCubeToWorkOn()[2]).log;
				this.setWeight(this.getWeight()+ log.getWeight());
				world.getCube(getCubeToWorkOn()[0], getCubeToWorkOn()[1], getCubeToWorkOn()[2]).log=null;
				world.getLogs().remove(this.log);
			}if (this.getWorkActivity() == workActivity.PICKINGUPBOULDER) {
				this.boulder=(Boulder) world.getCube(getCubeToWorkOn()[0], getCubeToWorkOn()[1], getCubeToWorkOn()[2]).boulder;
				this.setWeight(this.getWeight()+ boulder.getWeight());
				world.getCube(getCubeToWorkOn()[0], getCubeToWorkOn()[1], getCubeToWorkOn()[2]).boulder=null;
				world.getBoulders().remove(boulder);
			}if (this.getWorkActivity()== workActivity.WORKING){
				//Increase Toughens
				this.AttributeValueIncrease(2);
				//Increase Weight
				this.AttributeValueIncrease(3);
			}
			this.setCurrentExperiencePoints(this.getCurrentExperiencePoints()+10);
			this.setWorkActivity(workActivity.NONE);
			this.resetCounter("WORK_COUNTER");
		}
	}

	/**
	 * @param time
	 *
	 * @post	  Set the workCounter equals to the given time
	 * 			| new.workCounter == time
	 *
	 */
	private void setWorkCounter(double time){
		this.workCounter = time;
	}

	/**
	 *
	 * @return	  Gives the current value of workCounter
	 * 			| result == this.workCounter
	 */
	@Basic
	private double getWorkCounter() {
		return this.workCounter;
	}

	/**
	 *
	 * @param dt
	 * 		  Time interval
	 */
	private void advanceWhileAttacking(double dt) {
		this.setOrientation((float) Math.atan2(
				this.getDefender().getPosition().getDoubleCoordinates()[1] - this.getPosition().getDoubleCoordinates()[1],
				this.getDefender().getPosition().getDoubleCoordinates()[0] - this.getPosition().getDoubleCoordinates()[0]));
		this.getDefender().setOrientation((float) Math.atan2(
				this.getPosition().getDoubleCoordinates()[1] - this.getDefender().getPosition().getDoubleCoordinates()[1],
				this.getPosition().getDoubleCoordinates()[0] - this.getDefender().getPosition().getDoubleCoordinates()[0])
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
	 * @return	  The unit that's current defending against this unit
	 * 			| result == this.defender
	 */
	@Basic
	private Unit getDefender(){
		return this.theDefender;
	}

	/**
	 * @param time
	 *
	 * @post	  Set the fightCounter equals to the given time
	 * 			| new.fightCounter == time
	 *
	 */
	private void setFightCounter(double time){
		this.fightCounter = time;
	}

	/**
	 *
	 *
	 * @return	  Gives the current value of fightCounter
	 * 			| Result == this.fightCounter
	 *
	 */
	@Basic
	private double getFightCounter() {
		return this.fightCounter;
	}

	/**
	 * Method that update the units State whit it's previous and also update the orientation
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
	 * @param time
	 *
	 * @post	  Set the needToRestCounter equals to the given time
	 * 			| new.needToRestCounter == time
	 *
	 */
	private void setNeedToRestCounter(double time){
		this.needToRestCounter = time;
	}

	/**
	 *
	 * @return	  Gives the current value of needToRestCounter
	 * 			| Result == this.needToRestCounter
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
	 * 			  When a unit has the maximum hitPoints and the maximum of currentStaminaPoints
	 * 			  the unit can't rest
	 * 			| if(this.getCurrentHitPoints() == this.maxHitPoints())
	 * 			| && ( this.getCurrentStaminaPoints == this.getMaxStaminaPoints())
	 * @throws IllegalStateException
	 *			  If the unit is currently executing an activity
	 *			| if(this.getState() != State.NONE)
	 * TODO: 16/03/16 If unit is falling, abort.
	 */
	public void rest()throws IllegalStateException{
		if( this.getCurrentHitPoints() == this.getMaxHitPoints() && this.getCurrentStaminaPoints() == this.getMaxStaminaPoints())
			throw new IllegalStateException();
		if (this.getState()== State.MOVING)
			this.setRestRequestedWhileMoving(true);
		if(this.getState() != State.NONE)
			throw new IllegalStateException();
		this.setState(State.RESTING);
	}

	/**
	 * 
	 */
	private void setRestRequestedWhileMoving(boolean haveToRest) {
		this.restRequestedWhileMoving = haveToRest;
	}

	/**
	 *
	 * @return	  Gives the current value of defaultBehaviorEnabled
	 * 			| result == this.defaultBehaviorEnabled
	 */
	@Basic
	public Boolean getDefaultBehaviorEnabled(){
		return this.defaultBehaviorEnabled;
	}

	/**
	 * @post 	  choose a random state move, conduct a work task, rest until it has full recovered currentHitPoints and currentStaminaPoints
	 *
	 * @post  	  if is moving sprinting till it's exhausted
	 * 			|if(randomBehaviorNumber== 0)
	 * 			|	then this.startSprinting()
	 * @throws IllegalStateException
	 * 			  if the unit is doing a state
	 *			| if this.getState() != NONE
	 */
	private void startDefaultBehavior() throws IllegalStateException{
		if(this.getState()!= State.NONE)
			throw new IllegalStateException();
		int randomBehaviorNumber =new Random().nextInt(5);
		if(randomBehaviorNumber== 0) {
			moveTo(new int[]{new Random().nextInt(world.getNbCubesX()), new Random().nextInt(world.getNbCubesY()), new Random().nextInt(world.getNbCubesZ())});
			this.startSprinting();
		}
		else if(randomBehaviorNumber == 1) {
			int[] cubeToWorkOn = calculateRandomNeighboringCube();
			try {
				work(cubeToWorkOn[0],cubeToWorkOn[1],cubeToWorkOn[2]);
			} catch (IllegalArgumentException exc) {
				this.startDefaultBehavior();
			}
			
		}
		else
			try {
				rest();
			} catch (IllegalStateException exc) {
				this.startDefaultBehavior();
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
	public double getWeight() {
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
	 * 			  X coordinate of neighboring cube to move to.
	 * @param dy
	 * 			  Y coordinate of neighboring cube to move to.
	 * @param dz
	 * 			  Z coordinate of neighboring cube to move to.
	 * @post 	  the unit moves to an adjacent cube of the current one
	 * 			| new.position.getUnitCoordinates() == targetPosition
	 * @throws IllegalArgumentException
	 * 			  When the given cube coordinates aren't from a neighboring cube of this unit.
	 * TODO: 16/03/16 If unit is falling, abort.
	 */
	public void moveToAdjacent(int dx, int dy, int dz) throws IllegalArgumentException {
		if(!this.isValidPosition(new int[]{
				this.getPosition().getCubeCoordinates()[0]+dx,
				this.getPosition().getCubeCoordinates()[1]+dy,
				this.getPosition().getCubeCoordinates()[2]+dz})) {
			throw new IllegalArgumentException();
		}
		if (dx == 0 && dy == 0 && dz == 0) {
			return;
		}
		if (this.getState()== State.MOVING)
			return;

		// Absolute target position
		this.setTargetPosition(new int[]{
				this.getPosition().getCubeCoordinates()[0] + dx,
				this.getPosition().getCubeCoordinates()[1] + dy,
				this.getPosition().getCubeCoordinates()[2] + dz
		});
		this.setState(State.MOVING);
	}

	/**
	 * @post 	  if the unit is not moving do nothing
	 *
	 * @post 	  if the unit is moving set sprinting to true
	 * 			| if(this.getState()==State.MOVING)
	 * 			|	then new.sprinting == true
	 *
	 */
	public void startSprinting(){
		if(this.getState()==State.MOVING)
			this.sprinting = true;
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
	 * @param 	  targetPosition
	 * 
	 * @post 	  the new position must be the target position if it's not moving and set the State on moving
	 * 			| this.Position.getPositon() == targetPosition
	 * 			| new.setState(State.MOVING)
	 * @post      if the unit is moving set the newTargetPosition equals to targetPosition
	 * TODO: 16/03/16 If unit is falling, abort.
     * TODO: 16/03/16 Integr
	 */
	public void moveTo(int[] targetPosition) throws IllegalCoordinateException {
		if (! this.isValidPosition(targetPosition) || targetPosition == null) {
			throw new IllegalCoordinateException(targetPosition);
		}
		if(this.getState()== State.MOVING){
			this.setNewTargetPosition(targetPosition);
		}else{
			this.setTargetPosition(targetPosition);
			this.setState(State.MOVING);
			this.setRestRequestedWhileMoving(false);
		}
	}

	/**
	 * @param z 
	 * @param y 
	 * @param x 
	 * @post 	  the state of the unit is set to work
	 * 			| new.setState(State.WORKING)
	 *
	 * @effect 	  set the state of the unit to work
	 * 			| this.setState(State.WORKING)
	 *
	 * @throws IllegalStateException
	 * 			  The unit is attacking or defending or working or moving
	 * 			| if(this.getState() != State.NONE)
	 * TODO: 16/03/16 If unit is falling, abort.
     */
	public void work(int x, int y, int z) throws IllegalStateException,IllegalArgumentException {
		if(this.getState() != State.NONE)
			throw new IllegalStateException();
//		if(!this.isNeighboringCube(new int[]{x,y,z})){
//			throw new IllegalArgumentException();
//		}
		this.setWorkCounter(this.getTimeForWork());
		if(this.isCarryingLog() && !world.getCube(x,y,z).isSolid()){
			this.setWorkActivity(workActivity.DROPINGLOG);
		}
		else if(this.isCarryingBoulder()&& !world.getCube(x,y,z).isSolid()){
			this.setWorkActivity(workActivity.DROPINGBOULDER);
		}
		// terrain type is workshop
		else if (world.getCube(x, y, z).getTerrain() instanceof Workshop) {
			if(world.getCube(x, y, z).hasBoulder() && world.getCube(x, y, z).hasLog())
				this.setWorkActivity(workActivity.WORKING);
			else
				return;
			
		}
		else if(world.getCube(x, y, z).hasLog()) {
			if(!isCarryingBoulder() && !isCarryingLog())
				this.setWorkActivity(workActivity.PICKINGUPLOG);
			else
				return;
		}
		else if(world.getCube(x, y, z).hasBoulder()){
			if(!isCarryingBoulder() && !isCarryingLog())
				this.setWorkActivity(workActivity.PICKINGUPBOULDER);
			else
				return;
		}
		// terrain type is wood
		else if (world.getCube(x, y, z).getTerrain() instanceof Tree){
			if(!isCarryingBoulder() && !isCarryingLog())
				setWorkActivity(workActivity.DIGING);
			else 
				return;
		}
		// terrain type is rock
		else if (world.getCube(x, y, z).getTerrain() instanceof Rock){
			if(!isCarryingBoulder() && !isCarryingLog())
				this.setWorkActivity(workActivity.DIGING);
			else
				return;
		}
		else
			return;
		this.setCubeToWorkOn(x, y, z);
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
	 * @post	  if the defender is located on a neighboring cube of the attacker then 
	 * 			  set the state of this unit to ATTACKING and set the defender as defender
	 * 			| if(this.isNeighboringCube(defender.position.getCubeCoordinates()))
	 * 			|	then new.setState(State.ATTACKING)
	 * 			|	new.setDefender(defender)
	 * 			|	this.getDefender().defend(this.getAgility(), this.getStrength())
	 * @post	  Set the fight counter equals to the fight time
	 * 			|	new.setFightCoutner() == this.getFightTime()
	 * TODO: 16/03/16 If attacker or defender is falling, abort.
     * TODO: 16/03/16 Attacker and defender must be of different factions.
     * TODO: 16/03/16 Increase experience points by 20 if successful.
     */
	public void attack(Unit defender) throws IllegalStateException, IllegalArgumentException {
		// when there is no unit 
		if (defender == null || defender.getFaction()== this.getFaction()) {
			throw new IllegalArgumentException();
		}
		// Can't attack units that not on a neighboring cube of the attacker
		if(!(this.isNeighboringCube(defender.getPosition().getCubeCoordinates())))
			throw new IllegalStateException();
		if(defender.getCurrentHitPoints()<=0)
			this.setState(State.NONE);
		else{
			this.setFightCounter(this.getFightTime());
			// set the state of the defender on attacking
			this.setState(State.ATTACKING);
			this.setDefender(defender);
									// the attackers agility and strength
			this.getDefender().defend(this.getAgility(), this.getStrength(), this);
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
		if(cubeCoordinatesOfPossibleNeighbor == null ||
				(! this.isValidPosition(cubeCoordinatesOfPossibleNeighbor))) {
			return false;
		}
		if (Math.abs(cubeCoordinatesOfPossibleNeighbor[0] - this.getPosition().getCubeCoordinates()[0]) == 1) {
			if (Arrays.
					stream(new int[]{-1, 0, 1}).
					anyMatch(i -> i == cubeCoordinatesOfPossibleNeighbor[1] - this.getPosition().getCubeCoordinates()[1])) {
				return true;
			}
		}
		if (cubeCoordinatesOfPossibleNeighbor[0] == this.getPosition().getCubeCoordinates()[0]) {
			if (Math.abs(cubeCoordinatesOfPossibleNeighbor[1] - this.getPosition().getCubeCoordinates()[1]) == 1) {
				if (cubeCoordinatesOfPossibleNeighbor[2] == this.getPosition().getCubeCoordinates()[2]) {
					return true;
				}
			} else if (cubeCoordinatesOfPossibleNeighbor[1] == this.getPosition().getCubeCoordinates()[1]) {
				if (Math.abs(cubeCoordinatesOfPossibleNeighbor[2] - this.getPosition().getCubeCoordinates()[2]) == 1) {
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
	 * 				| new.theDefender == defender
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
	 * @post 	  When the block is smaller then chance for dodging,
	 * 			  this unit dodges the attack and moves to a
	 * 			  random neighBouring cube.
	 * 			| if(dodge < this.chanceForDodging(attackerAgility)
	 * 			|	then this.dodge
	 * @effect	  the unit moves to a random neighboring cube
	 * 			| new.position.setUnitCoordinates(randomNeighboringCube) 
	 * @post 	  When this unit fails to dodge the attack,
	 * 			  this unit chance for blocking the attack is equals to chanceForBlocking(attackerAgility,attackerStrength)
	 * 			| if(blocking <this.chanceForBlocking(attackerAgility,attackerStrength)
	 * 			|	then return
	 * 			| Math.random() < 0.25 * ( (this.getStrength() + this.getAgility())
	 * 			|	/ (attacker.getStrength() + attacker.getAgility()) )
	 * @post	  When this unit fails to dodge or block the attack,
	 * 			  this unit's hitPoints are lowered,
	 * 			  relative to the strength of the attacker.
	 * 			| new.getCurrentHitPoints() == this.getCurrentHitPoints() - this.damge(attackerStrength)
     *
     * TODO: 16/03/16 Increase experience points by 20 if successful.
     *
	 */
	private void defend(double attackerAgility, double attackerStrength,Unit attacker) {
		this.isDefending = true;
		if (!world.getCube(this.position.getCubeCoordinates()[0], 
				this.position.getCubeCoordinates()[1], 
				this.position.getCubeCoordinates()[2]).hasSolidNeighboringCubes()) {
			double dodge = Math.random();
			if(dodge < this.chanceForDodging(attackerAgility)){
				this.dodge();
				this.setCurrentHitPoints(this.getCurrentExperiencePoints()+20);
				return;
			}
		}else{
			double block = Math.random();
			if(block< this.chanceForBlocking(attackerAgility,attackerStrength)){
				this.setCurrentHitPoints(this.getCurrentExperiencePoints()+20);
				return;
			}else{
				attacker.setCurrentExperiencePoints(this.getCurrentExperiencePoints()+20);
				this.setCurrentHitPoints(this.getCurrentHitPoints() - this.damage(attackerStrength));
			}
		}
		this.saveUnitSate();
		this.setState(State.NONE);
	}

	/**
	 * @note	  this is equals to the defender 
	 * 
	 * @param	  attackerAgility
	 * 			  The attackers agility
	 *
	 * @return	  Gives the chance for dodging an attack
	 * 			| return 0.20*(this.getAgility()/attackerAgility)
	 */
	private double chanceForDodging(double attackerAgility){
		return (0.20*(this.getAgility()/attackerAgility));
	}

	/**
	 * 			  This Method set the change the units position when dodging
	 *
	 * @post 	  The new UnitCoordinates are the coordinates of a randomNeighboringCube that is a valid cube
	 * 			| while(!this.position.isValidPosition(randomNeighboringCube)
	 * 			| 	then randomNeighboringCube = calculateRandomNeighboringCube()
	 *
	 * @post	  Set the position of the unit to those coordinates
	 * 			| new.position.setUnitCoordinates(randomNeighboringCube)
	 *
     * TODO: 16/03/16 Cube to dodge to must feature passable terrain.
	 *
	 */
	private void dodge() {
	int[] randomNeighboringCube = calculateRandomNeighboringCube();
	while (! this.isValidPosition(randomNeighboringCube) && !(world.getCube(randomNeighboringCube[0],
																		  randomNeighboringCube[1],
																		  randomNeighboringCube[2]).isSolid())){
		randomNeighboringCube = calculateRandomNeighboringCube();
	}
	this.setPosition(new Position(randomNeighboringCube));	
	}

	/**
	 *
	 *
	 * @return	  This method returns a random calculate a random neighboring cube of the unit
	 * 			| Result == new int[][]{equalXDifferentY, 
	 * 			| 	equalYDifferentX, 
	 * 			| 	differentXDifferentY}[new Random().nextInt(3)]
	 *
	 *
	 */
	private int[] calculateRandomNeighboringCube() {
		int[] equalXDifferentY = new int[]{
				this.getPosition().getCubeCoordinates()[0],
				this.getPosition().getCubeCoordinates()[1] + new int[]{-1, 1}[new Random().nextInt(2)],
				this.getPosition().getCubeCoordinates()[2]
		};
		int[] equalYDifferentX = new int[]{
				this.getPosition().getCubeCoordinates()[0] + new int[]{-1, 1}[new Random().nextInt(2)],
				this.getPosition().getCubeCoordinates()[1],
				this.getPosition().getCubeCoordinates()[2]
		};
		int[] differentXDifferentY = new int[]{
				this.getPosition().getCubeCoordinates()[0] + new int[]{-1, 1}[new Random().nextInt(2)],
				this.getPosition().getCubeCoordinates()[1] + new int[]{-1, 1}[new Random().nextInt(2)],
				this.getPosition().getCubeCoordinates()[2]
		};
		return new int[][]{equalXDifferentY, equalYDifferentX, differentXDifferentY}[new Random().nextInt(3)];
	}

	/**
	 * @note	 this is equals to the defender 
	 * 
	 * @param	 attackerAgility
	 * 			  The attackers agility
	 *
	 * @param 	 attackerStrength
	 * 			  attackers strength
	 *
	 * @return    Gives the chance for a defending unit to block the attack
	 *			| Result == 0.25*(this.getAgility() + this.getStrength())/(attackerAgility + attackerStrength)
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

//	/**
//	 * Set the unit coordinates of this unit position to the sum of
//	 * the given cube coordinates and 1/2 of a cube side.
//	 * @post The unit coordinates of this new unit position are equal to the given cube coordinates + 1/2 of a cube side.
//	 * | new.getUnitCoordinates() ==
//	 * |	{
//	 * |	  (cubeCoordinates[0] + 1/2 * cubeSideLength),
//	 * | 	  (cubeCoordinates[1] + 1/2 * cubeSideLength),
//	 * | 	  (cubeCoordinates[2] + 1/2 * cubeSideLength)
//	 * | 	}
//	 * @param cubeCoordinates The cube coordinates of this unit position.
//	 */
//	@Raw
//	private void setUnitCoordinates(int[] cubeCoordinates) throws hillbillies.model.IllegalCoordinateException {
//		position = new Position(cubeCoordinates);
//	}

	/**
	 * Check whether the given coordinates are valid coordinates for
	 * any position.
	 * @param cubeCoordinates The coordinates to check.
	 * @return True if all coordinates are within range, false otherwise. | result ==
	 * |	(coordinates[0] >= 0) && (coordinates[0] < 50) &&
	 * |	(coordinates[1] >= 0) && (coordinates[1] < 50) &&
	 * | 	(coordinates[2] >= 0) && (coordinates[2] < 50)
	 */
	private boolean isValidPosition(int[] cubeCoordinates) {
		 return	(cubeCoordinates[0] >= 0) && (cubeCoordinates[0] < this.getWorld().getNbCubesX()) &&
		 	(cubeCoordinates[1] >= 0) && (cubeCoordinates[1] < this.getWorld().getNbCubesY()) &&
		 	(cubeCoordinates[2] >= 0) && (cubeCoordinates[2] < this.getWorld().getNbCubesZ());
	}

//	/**
//	 * Set the cube coordinates of this position to the given coordinates.
//	 * @post The cube coordinates of this position are equal to the given coordinates. | new.getCubeCoordinates() == {cubeCoordinates[0], cubeCoordinates[1], cubeCoordinates[2]}
//	 * @param cubeCoordinates The coordinates to initialize this position's cube coordinates with.
//	 */
//	@Raw
//	private void setCubeCoordinates(int[] cubeCoordinates) {
//		this.position = new Position(cubeCoordinates);
//	}

	public Faction getFaction() {
		return this.faction;
	}

	public void setFaction(Faction faction) {
		this.faction = faction;
	}

	/**
	 * 
	 * @param faction
	 */
	private boolean canHaveAsFaction(Faction faction) {
		// TODO - implement Unit.canHaveAsFaction
		throw new UnsupportedOperationException();
	}

	public int getCurrentExperiencePoints() {
		return this.currentExperiencePoints;
	}

	public void setCurrentExperiencePoints(int currentExperiencePoints) {
		this.currentExperiencePoints = currentExperiencePoints;
	}

	public int getRandomAttributePointCounter() {
		return this.randomAttributePointCounter;
	}

	public void setRandomAttributePointCounter(int randomAttributePointCounter) {
		this.randomAttributePointCounter = randomAttributePointCounter;
	}

	/**
	 * increase 
	 */
	public void incrementRandomAtrributeValue() {
		this.setRandomAttributePointCounter(this.getCurrentExperiencePoints()/10);
		Random rd = new Random();
		int atribute;
		for (int i = 0; i < this.getRandomAttributePointCounter(); i++) {
			atribute = rd.nextInt(3);
			this.AttributeValueIncrease(atribute);
			this.setCurrentExperiencePoints(this.getCurrentExperiencePoints()-10);
		}
		
	}
	
	/**
	 * @param atribute
	 */
	private void AttributeValueIncrease(int atribute) {
		switch (atribute) {
		case 0:
			 this.setAgility(this.getAgility()+1);
			 break;
		case 1:
			this.setStrength(this.getStrength()+1);
			
			break;
		case 2:
			this.setToughness(this.getToughness()+1);
			//this.setCurrentHitPoints(this.getCurrentHitPoints()+1);
			//this.setCurrentStaminaPoints(this.getCurrentStaminaPoints()+1);
			break;
		case 3:
			this.setWeight(this.getWeight()+1);
			break;
		}
	}

	/**
	 * 
	 *@return if the unit has more or equals to 10 xp. then return true
	 */
	public boolean hasEnoughExperiencePoints() {
		return this.getCurrentExperiencePoints() >= 10;
	}

	public boolean isFalling() {
		return this.falling;
	}

	public void setFalling(boolean falling) {
		this.falling = falling;
	}

	/**
	 * @return True if one of the neighboring cubes has a solid terrain type, false otherwise.
	 */
	public boolean hasSolidNeighboringCube() {
		return Arrays.stream(this.getNeighboringCubes()).anyMatch(cube -> cube.isSolid());
	}

    private Cube[] getNeighboringCubes() {
        int posX = this.getPosition().getCubeCoordinates()[0];
        int posY = this.getPosition().getCubeCoordinates()[1];
        int posZ = this.getPosition().getCubeCoordinates()[2];
        return new Cube[]{
                this.getWorld().getCube(posX-1, posY, posZ),
                this.getWorld().getCube(posX+1, posY, posZ),
                this.getWorld().getCube(posX, posY-1, posZ),
                this.getWorld().getCube(posX, posY+1, posZ),
                this.getWorld().getCube(posX, posY, posZ-1),
                this.getWorld().getCube(posX, posY, posZ+1),
        };
    }

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public void die() {
		// TODO - implement Unit.die
		throw new UnsupportedOperationException();
	}


	public boolean isCarryingLog() {
		return (this.log != null);
	}

	public boolean isCarryingBoulder() {
		return (this.boulder != null);
	}


    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

	/**
	 * Return the cubeToWorkOn of this Unit.
	 */
	public int[] getCubeToWorkOn() {
		return cubeWorkOn;
	}

	/**
	 * Set the cubeToWorkOn of this Unit to the given cubeToWorkOn.
	 *
	 * @post   The cubeToWorkOn of this of this Unit is equal to the given cubeToWorkOn.
	 *       | new.getcubeToWorkOn() == cubeToWorkOn
	 */
	public void setCubeToWorkOn(int x, int y, int z) {
		this.cubeWorkOn = new int[]{x,y,z};
	}

}