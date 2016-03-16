package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import be.kuleuven.cs.som.annotate.Value;

@Value
public class Position {

	/**
	 * Variables registering the unit coordinates of this unit position.
	 */
	public double unitX;
	/**
	 * Variables registering the unit coordinates of this unit position.
	 */
	public double unitY;
	/**
	 * Variables registering the unit coordinates of this unit position.
	 */
	public double unitZ;
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

	public Position(int[] initialPosition) {

	}

	/**
	 * Return the unit coordinates of this unit position.
	 * @return The unit coordinates. | result = {this.unitX, this.unitY, this.unitZ}
	 */
	@Basic
	@Raw
	public double[] getDoubleCoordinates() {
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