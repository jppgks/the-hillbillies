package hillbillies.model;

import java.util.Arrays;

/**
 * A class for signaling exceptions in coordinates.
 *
 */
public class IllegalCoordinateException extends IllegalArgumentException {

    /**
     * Initialize this new IllegalCoordinateException with given coordinates.
     *
     * @param   coordinates
     *          The array of coordinates containing an invalid coordinate.
     * @effect  This new IllegalCoordinateException is initialized in the same way
     *          an IllegalArgumentException with error message is initialized.
     */
    public IllegalCoordinateException(int[] coordinates) {
        super(String.format("One of the following coordinates is invalid: %s", Arrays.toString(coordinates)));
    }

}
