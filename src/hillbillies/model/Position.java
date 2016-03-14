package hillbillies.model;

@Value
public class Position {

	/**
	 * Variables registering the unit coordinates of this unit position.
	 */
	private double unitX;
	/**
	 * Variables registering the unit coordinates of this unit position.
	 */
	private double unitY;
	/**
	 * Variables registering the unit coordinates of this unit position.
	 */
	private double unitZ;
	/**
	 * Variables registering the cube coordinates of this unit position.
	 */
	private int cubeX;
	/**
	 * Variables registering the cube coordinates of this unit position.
	 */
	private int cubeY;
	/**
	 * Variables registering the cube coordinates of this unit position.
	 */
	private int cubeZ;
	/**
	 * Variable registering the length of a cube side in meters.
	 */
	public final double cubeSideLength = 1;

	/**
	 * Return the unit coordinates of this unit position.
	 * @return The unit coordinates. | result = {this.unitX, this.unitY, this.unitZ}
	 */
	@Basic
	@Raw
	public double[] getUnitCoordinates() {
		// TODO - implement Position.getUnitCoordinates
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns this position's cube coordinates.
	 * @return The cube coordinates. | result == {this.cubeX, this.cubeY, this.cubeZ}
	 */
	@Basic
	@Raw
	public int[] getCubeCoordinates() {
		// TODO - implement Position.getCubeCoordinates
		throw new UnsupportedOperationException();
	}
}