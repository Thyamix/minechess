package com.thyamix.game;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.instance.block.Block;
import net.minestom.server.registry.DynamicRegistry;
import net.minestom.server.utils.NamespaceID;
import net.minestom.server.world.DimensionType;

import java.util.ArrayList;
import java.util.List;

public class GameHandler {
    private static InstanceContainer lobby;
    private static final List<ChessGame> chessGames = new ArrayList<ChessGame>();

    public static InstanceContainer initInstanceContainer(Boolean isLobby) {
        DimensionType fullbright = DimensionType.builder()
                .ambientLight(2.0f)
                .build();
        DynamicRegistry.Key<DimensionType> key = MinecraftServer.getDimensionTypeRegistry().register(NamespaceID.from("minestom:full_bright"), fullbright);

        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        InstanceContainer instanceContainer = instanceManager.createInstanceContainer(key);
        instanceContainer.setChunkSupplier(LightingChunk::new);

        instanceContainer.setGenerator(unit -> unit.modifier().fillHeight(-1, 0, isLobby ? Block.WHITE_CONCRETE : Block.BLACK_CONCRETE));

        instanceContainer.setTimeRate(0);
        instanceContainer.setTime(6000);

        return instanceContainer;
    }

    public static ChessGame newGame() {
        ChessGame chessGame = new ChessGame();
        chessGames.add(chessGame);
        return chessGame;
    }

    public static void gameJoinRequest(Player player) {
        for (ChessGame game : GameHandler.chessGames) {
            if (!game.compelete && !game.running && (game.player1 == null || game.player2 == null)) {
                game.join(player, game.instanceContainer);
                return;
            }

        }
        ChessGame game = new ChessGame();
        GameHandler.chessGames.add(game);
        game.join(player, game.instanceContainer);
    }

    public static InstanceContainer getLobby() {
        if (GameHandler.lobby == null) {
            GameHandler.lobby = GameHandler.initInstanceContainer(true);
        }
        return GameHandler.lobby;
    }
}
