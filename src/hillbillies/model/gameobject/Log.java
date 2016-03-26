package hillbillies.model.gameobject;

import hillbillies.model.Position;

public class Log extends Material {

    /**
     * Creates a new log object and initializes it with the given position.
     *
     * @post      The position of this new log is equal to the given position.
     *
     * @param position
     *            The position to initialize this log with.
     */
    public Log(Position position) {
        this.position = position;
    }
}