package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

/**
 * A class for a cubical object that occupies a position in the game world.
 * 
 * @author 	Iwein Bau & Joppe Geluykens
 * 
 * @invar 	The activity is always equals to a valid activity.
 * 			| isValidAcivity(activity)
 * @invar 	The weight is always equals to a valid weight
 * 			| isValidWeight(weight)
 * 
 */
public class Unit {
	
	
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
	 * @return 	Returns the current weight of the unit.
	 * 			| result == this.weight
	 */
	public int getWeight(){
		return this.weight;
	}
	
	/**
	 * @param weight
	 * 
	 * @pre the new weight must be valid
	 * 		|isValisWeight(weight)
	 * 
	 * @post the new weight is equals as the given weight 
	 * 		|new.weight == weight
	 * 
	 */
	public void setWeight(int weight){
		assert isValidWeight(weight);
		
	}
	/**
	 * @param weight
	 * 			the weight that needs to be checked
	 * @return true if the weight is larger then MIN_WEIGHT and smaller then MAX_WEIGHT
	 * 			and must all times be at least be (strength + agility)/2
	 * 			|return if ( weight >MIN_WEIGHT)&&(weight < MAX_WEIGHT)
	 * 			
	 */
	public static boolean isValidWeight(int weight){
		return true;
	}
	public static final int MAX_WEIGHT = 200;
	public static final int	MIN_WEIGHT = 1;
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

	//her we put all the variables
	private int hitPoints;
	private String activity;
	private int weight;
}
