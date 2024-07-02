package com.github.thethingyee.mcinmcproject.util;

import org.bukkit.block.data.BlockData;

public class BlockColorAssignment {

    private int red;
    private int green;
    private int blue;
    private String fileName;
    private BlockData blockData;

    public BlockColorAssignment(int red, int green, int blue, String fileName, BlockData blockData) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.fileName = fileName;
        this.blockData = blockData;
    }

    public BlockColorAssignment() {

    }


    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public String getFileName() {
        return fileName;
    }

    public BlockData getBlockData() {
        return blockData;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setBlockData(BlockData blockData) {
        this.blockData = blockData;
    }
}
