package hillbillies.model;

import hillbillies.model.terrain.Terrain;

import java.util.List;

public class Cube {

	private Terrain terrain;
	private List<Cube> directlyAdjacentCubes;
	private Position position;
	private boolean isConnectedToBorder;
	private boolean passable;
	private int terainOfCube;
	private World worldOfCube;
	private Unit unitOnThisCube = null;
	
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
	public Cube(int x, int y, int z, int type, World world){
		this.setWorldOfCube(world);
		position = new Position(new int[] {x,y,z});
		this.setTerainOfCube(type);
	}
	/**
	 * @param world
	 */
	private void setWorldOfCube(World world) {
		this.worldOfCube = world;
		
	}
	private World getWorldOfCube() {
		return this.worldOfCube;
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
	/**
	 * @return
	 */
	public boolean hasSolidNeighboringCubes() {
		for (int i = -1; i < 1; i++) {
			if( 0 <= (position.getCubeCoordinates()[0]-i) && 
					(position.getCubeCoordinates()[0]-i) < this.worldOfCube.getNbCubesX())
				if(this.getWorldOfCube().getCube(position.getCubeCoordinates()[0]-i,
						 position.getCubeCoordinates()[1],
						 position.getCubeCoordinates()[2]).isSolid())
					return true;
			if( 0 <= (position.getCubeCoordinates()[1]-i) && 
					(position.getCubeCoordinates()[1]-i) < this.worldOfCube.getNbCubesY())
				if(this.getWorldOfCube().getCube(position.getCubeCoordinates()[0], 
						 position.getCubeCoordinates()[1]-i,
						 position.getCubeCoordinates()[2]).isSolid())
					return true;
			if( 0 <= (position.getCubeCoordinates()[2]-i) && 
					(position.getCubeCoordinates()[2]-i) < this.worldOfCube.getNbCubesZ())
				if(this.getWorldOfCube().getCube(position.getCubeCoordinates()[0], 
						 position.getCubeCoordinates()[1],
						 position.getCubeCoordinates()[2]-i).isSolid())
					return true;
		}
		return false;
	}
	public void  setUnitOnThisCube(Unit unit) {
		this.unitOnThisCube = unit;
	}
	
	private Unit getunitOnThisCube() {
		return unitOnThisCube;
	}
	/**
	 * @return
	 */
	public boolean isSolid() {
		return(this.getTerainOfCube()== 1 || this.getTerainOfCube()== 2);
	}
	public Boolean isOccupied() {
		return (this.getunitOnThisCube() != null);
	}
}