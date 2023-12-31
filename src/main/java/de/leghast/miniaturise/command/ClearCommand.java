package de.leghast.miniaturise.command;

import de.leghast.miniaturise.Miniaturise;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ClearCommand implements CommandExecutor {

    private Miniaturise main;

    public ClearCommand(Miniaturise main){
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(sender instanceof Player player){
            if(main.getRegionManager().getRegion() != null){
                for(BlockDisplay bd : main.getMiniatureManager().getPlacedMiniature()){
                    bd.remove();
                }
                player.sendMessage("§aSuccessfully removed all miniatures in the region");
            }else{
                player.sendMessage("§cPlease select a region first");
            }
        }


        return false;
    }
}
