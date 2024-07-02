package com.github.thethingyee.mcinmcproject;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Objects;
import java.util.logging.Level;

/*
    https://github.com/TheBizii/Pixelator
    Huge thanks to TheBizii's Pixelator for the whole core of this project. Most of his works are implemented at this project!
 */
public final class MCinMCProject extends JavaPlugin {

    private String pipePath = "";
    private RenderOrientation orientation = null;
    private static MCinMCProject plugin;

    @Override
    public void onEnable() {
        plugin = this;
        
        saveDefaultConfig();

        pipePath = this.getConfig().getString("pipe-path");
        orientation = this.getConfig().getString("orientation").equalsIgnoreCase("horizontal") ? RenderOrientation.HORIZONTAL : RenderOrientation.VERTICAL;

        File textureDir = new File(this.getDataFolder(), "textures");
        IntegratedLogger.log(
                textureDir.mkdirs() ? IntegratedLogger.Level.FINE : IntegratedLogger.Level.INFO,
                "Texture folder " + (textureDir.mkdirs() ? "created." : "already exists. Skipping."));

        TextureUtil.initializeAllTextureColors(textureDir);
    }

    @Override
    public void onDisable() {
        IntegratedLogger.logError("Stopping all tasks..");
        isRendering = false;
        Bukkit.getScheduler().cancelTasks(this);
    }

    public boolean isRendering = false;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!command.getName().equalsIgnoreCase("renderscreen")) return true;
        if(!(sender instanceof Player)) return true;

        Player player = (Player) sender;

        if(!isRendering) {
            isRendering = true;
            Renderer.startBlockRendering(player, pipePath, orientation);
            player.sendMessage(ChatColor.GREEN + "Started rendering!");
            return true;
        }

        Bukkit.getScheduler().cancelTasks(this);
        isRendering = false;
        player.sendMessage(ChatColor.RED + "Stopped rendering!");
        return true;

    }
    public static MCinMCProject getPlugin() {
        return plugin;
    }


}
