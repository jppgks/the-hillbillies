package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;

/**
 * A class for a cubical object that occupies a position in the game world.
 * 
 * @author 	Iwein Bau & Joppe Geluykens
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
	public float getOrientation() {
		return -1;
	}
	
	/**
	 * @param 	orientation
	 * 			The new orientation for this unit.
	 * @post 	
	 */
	private void setOrientation(float orientation) {
		
	}
	
	/**
	 * Variable registering the orientation of this unit.
	 */
	private float orientation = (float) (Math.PI / 2);
	
}
