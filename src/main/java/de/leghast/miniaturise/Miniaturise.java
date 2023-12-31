package de.leghast.miniaturise;

import de.leghast.miniaturise.command.*;
import de.leghast.miniaturise.completer.PositionTabCompleter;
import de.leghast.miniaturise.completer.ToolTabCompleter;
import de.leghast.miniaturise.listener.InventoryListener;
import de.leghast.miniaturise.listener.SelectionListener;
import de.leghast.miniaturise.manager.ConfigManager;
import de.leghast.miniaturise.manager.MiniatureManager;
import de.leghast.miniaturise.manager.RegionManager;
import de.leghast.miniaturise.ui.AdjusterUserInterface;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Miniaturise extends JavaPlugin {

    private ConfigManager configManager;
    private MiniatureManager miniatureManager;
    private RegionManager regionManager;

    private AdjusterUserInterface adjusterUserInterface;

    @Override
    public void onEnable() {

        //EVENTS
        Bukkit.getPluginManager().registerEvents(new SelectionListener(this), this);
        Bukkit.getPluginManager().registerEvents(new InventoryListener(this), this);

        //COMMANDS
        getCommand("position").setExecutor(new PositionCommand(this));
        getCommand("position").setTabCompleter(new PositionTabCompleter());
        getCommand("select").setExecutor(new SelectCommand(this));
        getCommand("cut").setExecutor(new CutCommand(this));
        getCommand("paste").setExecutor(new PasteCommand(this));
        getCommand("remove").setExecutor(new RemoveCommand(this));
        getCommand("scale").setExecutor(new ScaleCommand(this));
        getCommand("tool").setExecutor(new ToolCommand(this));
        getCommand("tool").setTabCompleter(new ToolTabCompleter());
        getCommand("clear").setExecutor(new ClearCommand(this));
        getCommand("adjust").setExecutor(new AdjustCommand(this));

        //INIT
        configManager = new ConfigManager(this);
        miniatureManager = new MiniatureManager(this);
        regionManager = new RegionManager(this);

        adjusterUserInterface = new AdjusterUserInterface(this);
    }

    @Override
    public void onDisable() {
        saveConfigFile();
    }

    public void saveConfigFile(){
        this.saveConfig();
    }

    public ConfigManager getConfigManager(){
        return configManager;
    }

    public MiniatureManager getMiniatureManager(){
        return miniatureManager;
    }

    public RegionManager getRegionManager(){
        return regionManager;
    }

    public AdjusterUserInterface getAdjusterUserInterface(){
        return adjusterUserInterface;
    }

}