package com.thyamix.utility;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Player;

public class RayTraceGridSelector {

    private static Pos playerPos;

    public static PiecePosition select(Player player) {
        Pos playerPos = player.getPosition().add(0, player.getEyeHeight(), 0);
        Vec lookingDir = playerPos.direction();

        if (lookingDir.y() >= 0) {
            return null;
        }

        double t = -playerPos.y() / lookingDir.y();

        double x = playerPos.x() + t * lookingDir.x();
        double z = playerPos.z() + t * lookingDir.z();

        return new PiecePosition((int) (x / 4), (int) (z / 4));
    }
}
