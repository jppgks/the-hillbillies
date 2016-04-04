package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import be.kuleuven.cs.som.annotate.Value;

@Value
public class Position {

	/**
	 * Creates a new position from given integer coordinates.
	 *
	 * @param 	  initialPosition
     *			  The coordinates for this position.
	 */
	public Position(int[] initialPosition) {
		this.cubeX = initialPosition[0];
		this.cubeY = initialPosition[1];
		this.cubeZ = initialPosition[2];
		this.setDoubleCoordinates(initialPosition);
	}

	/**
	 * Creates a new position from given double coordinates.
	 *
	 * @param 	  initialPosition
	 *			  The coordinates for this position.
	 */
    public Position(double[] initialPosition) {
        this.doubleX = initialPosition[0];
        this.doubleY = initialPosition[1];
        this.doubleZ = initialPosition[2];
        this.setCubeCoordinates(initialPosition);
    }

	/**
	 * Variables registering the unit coordinates of this unit position.
	 */
	private double doubleX;
	/**
	 * Variables registering the unit coordinates of this unit position.
	 */
	private double doubleY;
	/**
	 * Variables registering the unit coordinates of this unit position.
	 */
	private double doubleZ;
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
	 * Sets the double coordinates of this position from the given integer coordinates.
	 *
	 * @param 	  initialPosition
	 */
	private void setDoubleCoordinates(int[] initialPosition) {
		this.doubleX = initialPosition[0] +  cubeSideLength/2;
		this.doubleY = initialPosition[1] +  cubeSideLength/2;
		this.doubleZ = initialPosition[2] +  cubeSideLength/2;

	}

    /**
	 * Sets the cube coordinates of this position from the given double coordinates.
	 *
     * @param 	  initialPosition
     */
    private void setCubeCoordinates(double[] initialPosition) {
        this.cubeX = (int) initialPosition[0];
        this.cubeY = (int) initialPosition[1];
        this.cubeZ = (int) initialPosition[2];

    }

	/**
	 * Return the unit coordinates of this unit position.
	 *
	 * @return 	  The unit coordinates.
	 * 			| result = {this.unitX, this.unitY, this.unitZ}
	 */
	@Basic
	@Raw
	public double[] getDoubleCoordinates() {
		return new double[] {this.doubleX,this.doubleY,this.doubleZ};
	}

	/**
	 * Returns this position's cube coordinates.
	 *
	 * @return 	  The cube coordinates.
	 * 			| result == {this.cubeX, this.cubeY, this.cubeZ}
	 */
	@Basic
	@Raw
	public int[] getCubeCoordinates() {
		return new int[] {this.cubeX,this.cubeY,this.cubeZ};
	}
}