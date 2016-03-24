package hillbillies.model.gameobject;

import hillbillies.model.Position;

public class Boulder extends Material {

    public Boulder(Position position) {
        this.position = position;
    }

    private Position position;

    public Position getPosition() {
        return position;
    }

}