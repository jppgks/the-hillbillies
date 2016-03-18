package hillbillies.model;

import java.util.List;

public class Cube {

	private Terrain terrain;
	private List<Cube> directlyAdjacentCubes;
	private Position position;
	private boolean isConnectedToBorder;
	private boolean passable;
	private int terainOfCube;
	
	public Cube(int x, int y, int z, int type){
		position = new Position(new int[] {x,y,z});
		this.setTerainOfCube(type);
	}
	private List<Cube> calculateDirectlyAdjacentCubes() {
		// TODO - implement Cube.calculateDirectlyAdjacentCubes
		throw new UnsupportedOperationException();
	}

	private boolean isConnectedToBorder() {
		// TODO - implement Cube.isConnectedToBorder
		throw new UnsupportedOperationException();
	}

	private boolean isBorderCube() {
		// TODO - implement Cube.isBorderCube
		throw new UnsupportedOperationException();
	}

	private void caveIn() {
		// TODO - implement Cube.caveIn
		throw new UnsupportedOperationException();
	}

	private double getChanceForMaterial() {
		// TODO - implement Cube.getChanceForMaterial
		throw new UnsupportedOperationException();
	}

	public boolean isPassable() {
		return this.passable;
	}

	public void setPassable(boolean isPassable) {
		this.passable = isPassable;
	}
	public void setTerainOfCube(int type){
		this.terainOfCube = type;
	}
	public int getTerainOfCube(){
		return this.terainOfCube;
	}

}