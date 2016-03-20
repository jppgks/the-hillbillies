package hillbillies.model;

import java.util.List;

public class Cube {

	private Terrain terrain;
	private List<Cube> directlyAdjacentCubes;
	private Position position;
	private boolean isConnectedToBorder;
	private boolean passable;
	private int terainOfCube;
	
	/**
	 * @param x
	 * 			coordinate
	 * @param y
	 * 			coordinate
	 * @param z
	 * 			coordinate 
	 * @param type
	 * 			the terrain type if the cube
	 * 
	 * @post create an new cube with the given coordinates and terrain type
	 * 	
	 */
	public Cube(int x, int y, int z, int type){
		position = new Position(new int[] {x,y,z});
		this.setTerainOfCube(type);
	}
	private List<Cube> calculateDirectlyAdjacentCubes() {
		// TODO - implement Cube.calculateDirectlyAdjacentCubes
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns whether the cube at the given position is a solid cube that is
	 * connected to a border of the world through other directly adjacent solid
	 * cubes.
	 * 
	 * @note The result is pre-computed, so this query returns immediately.
	 * 
	 * @param x
	 *            The x-coordinate of the cube to test
	 * @param y
	 *            The y-coordinate of the cube to test
	 * @param z
	 *            The z-coordinate of the cube to test
	 * @return true if the cube is connected; false otherwise
	 */
	private boolean isConnectedToBorder() {
		// TODO - implement Cube.isConnectedToBorder
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Method that's invoke when a solid cup is nog connected to border whit other solid cubes
	 * 			
	 * 
	 */
	private void caveIn() {
		// TODO - implement Cube.caveIn
		throw new UnsupportedOperationException();
	}

	/**
	 * @return the change when a cube is caving in to spawn a log or a boulder 
	 */
	private double getChanceForMaterial() {
		// TODO - implement Cube.getChanceForMaterial
		throw new UnsupportedOperationException();
	}

	/**
	 * @return true if the cube is passeble and the unit can walk through the cube false otherwise 
	 */
	public boolean isPassable() {
		return this.passable;
	}

	/**
	 * @param isPassable
	 * 
	 * @post set the instance true or false if the cube is passable or inpasseble
	 * 			  
	 */
	public void setPassable(boolean isPassable) {
		this.passable = isPassable;
	}
	/**
	 * @param type
	 * 
	 * @psot the terain type of the cube is set to the given type
	 */
	public void setTerainOfCube(int type){
		this.terainOfCube = type;
	}
	/**
	 * @return the terrain type of the cube.
	 */
	public int getTerainOfCube(){
		return this.terainOfCube;
	}

}