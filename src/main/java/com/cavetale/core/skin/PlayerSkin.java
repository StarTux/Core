package com.cavetale.core.skin;

import java.awt.image.BufferedImage;
import java.util.UUID;
import java.util.function.Consumer;

public interface PlayerSkin {
    String getTextureUrl();

    BufferedImage getTextureImage();

    String getTextureBase64();

    BufferedImage getFaceImage();

    String getFaceBase64();

    static PlayerSkin getPlayerSkin(UUID uuid) {
        return PlayerSkinProvider.Companion.provider.getPlayerSkin(uuid);
    }

    static void getPlayerSkinAsync(UUID uuid, Consumer<PlayerSkin> callback) {
        PlayerSkinProvider.Companion.provider.getPlayerSkinAsync(uuid, callback);
    }
}
