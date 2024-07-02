package com.github.thethingyee.mcinmcproject;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Piston;

public class BlockDataParser {

    public static void parseBlockData(BlockColorAssignment b, String fileName) {
        fileName = fileName.replace(".png", "").toUpperCase();
        if(fileName.endsWith("_TOP")) {
            fileName = fileName.replace("_TOP", "");
            Material m;
            if(fileName.equals("DRIED_KELP")) m = Material.DRIED_KELP_BLOCK;
            else m = Material.valueOf(fileName);
            BlockData bd = m.createBlockData();
            if(bd.getMaterial().equals(Material.PISTON)){
                Piston piston = (Piston) bd;
                piston.setFacing(BlockFace.UP);
                b.setBlockData(piston);
            }
            b.setBlockData(bd);
        }else if(fileName.endsWith("_TOP_STICKY")) {
            Material m = Material.STICKY_PISTON;
            BlockData bd = m.createBlockData("[facing=up]");
            b.setBlockData(bd);
        } else if(fileName.endsWith("_BOTTOM")) {
            fileName = fileName.replace("_BOTTOM", "");
            Material m = Material.valueOf(fileName);
            b.setBlockData(m.createBlockData("[facing=down]"));
        } else if(fileName.endsWith("_SIDE")) {
            fileName = fileName.replace("_SIDE", "");
            Material m = Material.valueOf(fileName);
            b.setBlockData(m.createBlockData("[facing=north]"));
        } else if(fileName.endsWith("_COMPOST")) {
            Material m = Material.COMPOSTER;
            b.setBlockData(m.createBlockData("[level=6]"));
        } else if(fileName.endsWith("_READY")) {
            Material m = Material.COMPOSTER;
            b.setBlockData(m.createBlockData("[level=8]"));
        } else if(fileName.endsWith("_FRONT")) {
            Material m = Material.OBSERVER;
            BlockData bd = m.createBlockData("[facing=up]");
            b.setBlockData(bd);
        } else if(fileName.endsWith("_FRONT_VERTICAL")) {
            fileName = fileName.replace("_FRONT_VERTICAL", "");
            Material m = Material.valueOf(fileName);
            BlockData bd = m.createBlockData("[facing=up]");
            b.setBlockData(bd);
        } else if(fileName.endsWith("_BACK")) {
            Material m = Material.OBSERVER;
            BlockData bd = m.createBlockData("[facing=down]");
            b.setBlockData(bd);
        } else if(fileName.endsWith("_ON")) {
            Material m = Material.OBSERVER;
            BlockData bd = m.createBlockData("[facing=down,powered=true]");
            b.setBlockData(bd);
        } else {
            Material m;
            if(fileName.equals("SNOW")) m = Material.SNOW_BLOCK;
            else m = Material.valueOf(fileName);
            BlockData bd;
            if(fileName.endsWith("_LOG") || fileName.endsWith("_PILLAR")) {
                bd = m.createBlockData("[axis=x]");
            }else {
                bd = m.createBlockData();
            }
            b.setBlockData(bd);
        }
    }
}
