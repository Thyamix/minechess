package com.thyamix.utility;

import com.thyamix.game.ChessBoard;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Player;

public class RayTraceGridSelector {
    public Player player;
    public ChessBoard chessBoard;

    public int[] select() {
        Pos playerPos = this.player.getPosition();
        Vec lookingDir = playerPos.direction();

        if (lookingDir.y() >= 0) {
            return null;
        }

        double t = -playerPos.y() / lookingDir.y();

        double x = playerPos.x() + t * lookingDir.x();
        double z = playerPos.z() + t * lookingDir.z();

        return new int[]{(int) (x/4), (int) (z/4)};
    }
}
