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
    private InstanceContainer lobby;
    private final List<ChessGame> chessGames = new ArrayList<>();

    public InstanceContainer initInstanceContainer(Boolean isLobby) {
        DimensionType fullBright = DimensionType.builder()
                .ambientLight(2.0f)
                .build();
        DynamicRegistry.Key<DimensionType> key = MinecraftServer.getDimensionTypeRegistry().register(NamespaceID.from("minestom:full_bright"), fullBright);

        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        InstanceContainer instanceContainer = instanceManager.createInstanceContainer(key);
        instanceContainer.setChunkSupplier(LightingChunk::new);

        instanceContainer.setGenerator(unit -> unit.modifier().fillHeight(-1, 0, isLobby ? Block.WHITE_CONCRETE : Block.BLACK_CONCRETE));

        instanceContainer.setTimeRate(0);
        instanceContainer.setTime(6000);

        return instanceContainer;
    }

    public ChessGame newGame() {
        ChessGame chessGame = new ChessGame(this);
        this.chessGames.add(chessGame);
        return chessGame;
    }

    public void gameJoinRequest(Player player) {
        for (ChessGame game : this.chessGames) {
            if (!game.complete && !game.running && (game.player1 == null || game.player2 == null)) {
                game.join(player, game.instanceContainer);
                return;
            }

        }
        ChessGame chessGame = this.newGame();
        chessGame.join(player, chessGame.instanceContainer);
    }

    public InstanceContainer getLobby() {
        if (this.lobby == null) {
            this.lobby = this.initInstanceContainer(true);
        }
        return this.lobby;
    }
}
