package pw.haze.jackblack.entity.impl;

import pw.haze.jackblack.entity.Robot;
import pw.haze.jackblack.entity.RobotMoveType;

import java.util.Random;

/**
 * @author haze
 * @since 8/8/16
 */
public class RandomBot extends Robot {

    public RandomBot(String name) {
        super(name + "Bot");
    }

    @Override

    public RobotMoveType executeMove() {
        return RobotMoveType.values()[new Random().nextInt(RobotMoveType.values().length)];
    }
}
