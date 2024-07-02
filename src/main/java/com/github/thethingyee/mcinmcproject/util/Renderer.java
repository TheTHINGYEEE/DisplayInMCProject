package com.github.thethingyee.mcinmcproject.util;

import com.github.thethingyee.mcinmcproject.MCinMCProject;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Renderer {

    public static void startBlockRendering(Player player, String pipePath, RenderOrientation orientation) {
        Location playerLoc = player.getLocation();
        BlockData[][] previousBlockData = new BlockData[320][200];
        byte[] buffer = new byte[512 * 512];

        Bukkit.getScheduler().scheduleSyncRepeatingTask(MCinMCProject.getPlugin(), new Runnable() {
            @Override
            public void run() {
                try (RandomAccessFile pipe = new RandomAccessFile(pipePath, "r")) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    int bytesRead;

                    // Read data in chunks to handle large images
                    while ((bytesRead = pipe.read(buffer)) != -1) {
                        baos.write(buffer, 0, bytesRead);
                    }

                    byte[] imageData = baos.toByteArray();

                    try (ByteArrayInputStream bais = new ByteArrayInputStream(imageData)) {
                        BufferedImage image = ImageIO.read(bais);
                        for (int x = 0; x < 320; x++) {
                            for (int y = 0; y < 200; y++) {
                                BlockData blockData = TextureUtil.findNearestMatch(new Color(image.getRGB(x, y)));
                                Location blockLoc = getBlockLoc(x, y);

                                // Optimization: Skip sending block updates if the block data hasn't changed
                                if (previousBlockData[x][y] != null && previousBlockData[x][y].equals(blockData)) {
                                    continue;
                                }

                                player.sendBlockChange(blockLoc, blockData);
                                previousBlockData[x][y] = blockData;
                            }
                        }
                    }
                } catch (IOException e) {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Error reading from named pipe: " + e.getMessage());
                    // Consider adding a short delay to avoid excessive logging.
                }
            }

            private Location getBlockLoc(int x, int y) {
                int blockX = playerLoc.getBlockX() + (orientation == RenderOrientation.HORIZONTAL ?  -x : x);
                int blockY = playerLoc.getBlockY() + (orientation == RenderOrientation.HORIZONTAL ? 0 : -y);
                int blockZ = playerLoc.getBlockZ() + (orientation == RenderOrientation.HORIZONTAL ? -y : 0);
                return new Location(player.getWorld(), blockX, blockY, blockZ);
            }
        }, 1, 1);
    }

}
