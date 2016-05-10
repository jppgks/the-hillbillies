package hillbillies.model.expression;

import hillbillies.model.Cube;
import hillbillies.model.Position;
import hillbillies.model.World;

import java.util.Optional;

/*
 * TODO: To be shown on defense
 *         (use of generics, polymorphism, streams, lambda expressions, Optional, conditional operator)
 */
public final class PositionExpressionNextTo extends PositionExpression<World> {

    public PositionExpressionNextTo(Expression<Position> positionExpression) {
        this.value = positionExpression.evaluate();
    }

    @Override
    Position evaluate(World world) {
        // Next to position need be passable and solid neighbouring cube
        Optional<Cube> cubeOptional =
                world.getCube(this.evaluate()).getNeighboringCubes().stream()
                        .filter(cube -> !cube.isSolid())
                        .filter(Cube::hasSolidNeighboringCubes)
                        .findAny();
        return cubeOptional.isPresent() ? cubeOptional.get().getPosition() : null;
    }

}
