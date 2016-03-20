package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import be.kuleuven.cs.som.annotate.Value;

@Value
public class Position {

	/**
	 * Variables registering the unit coordinates of this unit position.
	 */
	public double doubleX;
	/**
	 * Variables registering the unit coordinates of this unit position.
	 */
	public double doubleY;
	/**
	 * Variables registering the unit coordinates of this unit position.
	 */
	public double doubleZ;
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
		this.cubeX = initialPosition[0];
		this.cubeY = initialPosition[1];
		this.cubeZ = initialPosition[2];
		this.setDoubleCoordinates(initialPosition);
	}

	/**
	 * @param initialPosition
	 */
	private void setDoubleCoordinates(int[] initialPosition) {
		this.doubleX = initialPosition[0] +  cubeSideLength/2;
		this.doubleY = initialPosition[1] +  cubeSideLength/2;
		this.doubleZ = initialPosition[2] +  cubeSideLength/2;
		
	}

	/**
	 * Return the unit coordinates of this unit position.
	 * @return The unit coordinates. | result = {this.unitX, this.unitY, this.unitZ}
	 */
	@Basic
	@Raw
	public double[] getDoubleCoordinates() {
		return new double[] {this.doubleX,this.doubleY,this.doubleZ};
	}

	/**
	 * Returns this position's cube coordinates.
	 * @return The cube coordinates. | result == {this.cubeX, this.cubeY, this.cubeZ}
	 */
	@Basic
	@Raw
	public int[] getCubeCoordinates() {
		return new int[] {this.cubeX,this.cubeY,this.cubeZ};
	}
}