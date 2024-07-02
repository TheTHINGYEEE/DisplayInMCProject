package com.github.thethingyee.mcinmcproject.util;

import com.github.thethingyee.mcinmcproject.MCinMCProject;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.data.BlockData;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TextureUtil {

    private static final ArrayList<BlockColorAssignment> allowedBlocks = new ArrayList<>();

    public static BlockData findNearestMatch(Color c) {
        double nearestMatch = 100000000.0d;
        BlockData matchedBlock = null;
        for(BlockColorAssignment block : allowedBlocks) {
            float diffR = Math.abs(c.getRed() - block.getRed());
            float diffG = Math.abs(c.getGreen() - block.getGreen());
            float diffB = Math.abs(c.getBlue() - block.getBlue());
            double difference = Math.sqrt(Math.pow(diffR, 2) + Math.pow(diffG, 2) + Math.pow(diffB, 2));
            if(difference < nearestMatch) {
                nearestMatch = difference;
                matchedBlock = block.getBlockData();
            }
        }
        return matchedBlock;
    }

    public static void initializeAllTextureColors(File textureDirectory) {
        try {
            for (String fileName : Objects.requireNonNull(textureDirectory.list())) {
                File textureFile = new File(textureDirectory, fileName);
                BufferedImage texture = ImageIO.read(textureFile);
                allowedBlocks.add(examineTexture(texture, fileName));
            }
            if(allowedBlocks.isEmpty()) {
                IntegratedLogger.logError("Texture directory empty. Textures needed to render image.");
                Bukkit.getPluginManager().disablePlugin(MCinMCProject.getPlugin());
                return;
            }
            IntegratedLogger.logDebug("Successfully parsed color for " + allowedBlocks.size() + " textures!");
        } catch(IOException e) {
            IntegratedLogger.logError(ChatColor.RED + "Failed to get texture directory!");
        }
    }

    // https://github.com/TheBizii/Pixelator/blob/master/src/me.EncryptedShoesHD.pixel8or/util/ImageExaminer.java
    public static BlockColorAssignment examineTexture(BufferedImage texture, String fileName) {
        int r = 0, g = 0, b = 0, size = texture.getWidth() * texture.getHeight();

        List<Integer> pixels = new ArrayList<>();

        for(int i = 0; i < texture.getHeight(); i++) {
            for(int j = 0; j < texture.getWidth(); j++) {
                int pixel = texture.getRGB(j, i);
                pixels.add(pixel);
            }
        }

        for(int i : pixels) {
            int red = (i >> 16) & 0xff;
            int green = (i >> 8) & 0xff;
            int blue = (i) & 0xff;
            r += red;
            g += green;
            b += blue;
        }
        BlockColorAssignment block = new BlockColorAssignment();
        block.setRed(r / size);
        block.setGreen(g / size);
        block.setBlue(b / size);
        block.setFileName(fileName);
        BlockDataParser.parseBlockData(block, fileName);
        return block;
    }

    public static ArrayList<BlockColorAssignment> getAllowedBlocks() {
        return allowedBlocks;
    }
}
