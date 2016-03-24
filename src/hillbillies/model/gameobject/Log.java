package hillbillies.model.gameobject;

import hillbillies.model.Position;

public class Log extends Material {

    public Log(Position position) {
        this.position = position;
    }

    private Position position;

    public Position getPosition() {
        return position;
    }
}