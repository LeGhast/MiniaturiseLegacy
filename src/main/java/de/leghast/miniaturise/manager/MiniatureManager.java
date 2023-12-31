package de.leghast.miniaturise.manager;

import de.leghast.miniaturise.Miniaturise;
import de.leghast.miniaturise.miniature.MiniatureBlock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Transformation;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.ceil;

public class MiniatureManager {

    private Miniaturise main;
    private List<MiniatureBlock> miniature = new ArrayList<>();
    private List<BlockDisplay> placedMiniature = new ArrayList<>();
    private int default_size = 1;

    public MiniatureManager(Miniaturise main){
        this.main = main;
    }

    public void miniaturiseSelection(Location origin){
        miniature.clear();
        for(Block block : main.getRegionManager().getRegion().getBlocks()){
            MiniatureBlock mb;
                mb = new MiniatureBlock(
                        block.getX() - (int) origin.getX(),
                        block.getY() - (int) origin.getY(),
                        block.getZ() - (int) origin.getZ(),
                        block.getBlockData(),
                        default_size);
                        miniature.add(mb);
        }
    }

    public boolean bordersAir(Block block) {
        if (block.getRelative(0, 1, 0).getType() == Material.AIR) {
            return true;
        }
        if (block.getRelative(0, -1, 0).getType() == Material.AIR) {
            return true;
        }
        if (block.getRelative(1, 0, 0).getType() == Material.AIR ||
                block.getRelative(-1, 0, 0).getType() == Material.AIR ||
                block.getRelative(0, 0, 1).getType() == Material.AIR ||
                block.getRelative(0, 0, -1).getType() == Material.AIR) {
            return true;
        }
        return false;
    }

    public boolean bordersSolid(Block block) {
        if (block.getRelative(0, 1, 0).isSolid()) {
            return true;
        }
        if (block.getRelative(0, -1, 0).isSolid()) {
            return true;
        }
        if (block.getRelative(1, 0, 0).isSolid() ||
                block.getRelative(-1, 0, 0).isSolid() ||
                block.getRelative(0, 0, 1).isSolid() ||
                block.getRelative(0, 0, -1).isSolid()) {
            return true;
        }
        return false;
    }

    public void pasteMiniature(Player player){
        placedMiniature.clear();
        if(!miniature.isEmpty()) {
            for (MiniatureBlock mb : miniature) {
                spawnBlockDisplays(mb, player);
            }
        }else{
            player.sendMessage("§cPlease select a region first");
        }
    }

    public void spawnBlockDisplays(MiniatureBlock mb, Player player){
        BlockDisplay bd;
        bd = (BlockDisplay) player.getWorld().spawnEntity(new Location(
                player.getWorld(),
                mb.getX() + ceil(player.getLocation().getX()),
                mb.getY() + ceil(player.getLocation().getY()),
                mb.getZ() + ceil(player.getLocation().getZ())),
                EntityType.BLOCK_DISPLAY);
        bd.setBlock(mb.getBlockData());
        Transformation transformation = bd.getTransformation();
        transformation.getScale().set(mb.getSize());
        bd.setTransformation(transformation);
        placedMiniature.add(bd);
        if (bd.getBlock().equals(Material.AIR.createBlockData())) {
            bd.remove();
        }
    }

    public void deleteMiniature(){
        for(BlockDisplay bd : placedMiniature){
            bd.remove();
        }
    }

    public void scaleMiniature(double scale, Player player){
        if(!miniature.isEmpty()){
            for(MiniatureBlock mb : miniature){
                mb.setX(mb.getX() * scale);
                mb.setY(mb.getY() * scale);
                mb.setZ(mb.getZ() * scale);
                mb.setSize(mb.getSize() * scale);
            }
        }else{
            player.sendMessage("§cPlease select a region first");
        }
    }

    public List<BlockDisplay> getPlacedMiniature(){
        return placedMiniature;
    }

}
