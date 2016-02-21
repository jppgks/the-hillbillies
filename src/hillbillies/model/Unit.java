package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;

/**
 * A class for a cubical object that occupies a position in the game world.
 * 
 * @author 	Iwein Bau & Joppe Geluykens
 * 
 * @invar the activity is always equals to a valid activity
 * 			| isvalidAcivity(activity)
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
	 * @param activity
	 * 
	 * @return this gives the current activity of the unit
	 * 			| result == this.activity
	 */
	public String getActivity(String activity){
		return this.activity;
	}
	/**
	 * @param activity
	 * 
	 * @pre the activity has to be a valid activity
	 * 		| isvalidAcivity(activity)
	 * @post the current activity is equals to the new activity
	 * 		| new.getActivity() == activity
	 * 
	 */
	public void setActivity(String activity){
		assert isValidActivity(activity);
		this.activity = activity;	
	}
	/**
	 * @param activity
	 * 
	 * 
	 * @return true if the activity equals to a valid activity
	 * 			|for each i in validActivities()
	 * 			|	if( activity == j in validAcitivties())
	 * 			|		then return true
	 * 			|return false
	 */
	public static boolean isValidActivity(String activity){
		return true;
	}
	
	/**
	 * 
	 * @return all the activities an unit van do
	 */
	public static String[] validActivities(){
		String[] activities = {"work,attack","defend","rest","move"};
		return activities;
	}
	/**
	 * @return this returns the current weight of the unit
	 * 			| result == this.weight
	 */
	public int getWeight(){
		return this.weight;
	}
	public void setWeight(int weight){
		
	}
	/**
	 * @param weight
	 * 			the weight that needs to be checked
	 * @return true if the weight is larger then 1 and 
	 */
	public static boolean isValidWeight(int weight){
		return true;
	}

	//her we put all the variables
	private int hitPoints;
	private String activity;
	private int weight;
}
