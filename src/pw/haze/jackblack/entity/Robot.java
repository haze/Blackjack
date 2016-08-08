package pw.haze.jackblack.entity;

/**
 * @author haze
 * @since 8/5/16
 */
public abstract class Robot extends Entity {

    public Robot(String name) {
        super(name);
    }

    public abstract RobotMoveType executeMove();
}
