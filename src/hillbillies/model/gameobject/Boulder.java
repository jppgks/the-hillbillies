package hillbillies.model.gameobject;

import hillbillies.model.Position;

public class Boulder extends Material {

    /**
     * Creates a new boulder object and initializes it with the given position.
     *
     * @post      The position of this new boulder is equal to the given position.
     *
     * @param position
     *            The position to initialize this boulder with.
     */
    public Boulder(Position position) {
        this.position = position;
    }

}