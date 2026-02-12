package net.mcreator.loikvy.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.mcreator.loikvy.entity.PlayerCorpseEntity;
import net.mcreator.loikvy.init.LoikvyModEntities;

public class SpawnCorpseProcedure {

    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null || !(entity instanceof Player) || !(world instanceof Level))
            return;
        
        Player player = (Player) entity;
        Level level = (Level) world;
        
        // Spawn the corpse
        PlayerCorpseEntity corpse = new PlayerCorpseEntity(LoikvyModEntities.PLAYER_CORPSE.get(), level);
        corpse.setPos(x, y, z);
        
        // Set the player's skin
        corpse.setPlayerProfile(player.getGameProfile());
        
        // Copy inventory from player to corpse
        corpse.copyInventoryFromPlayer(player);
        
        // Add corpse to world
        level.addFreshEntity(corpse);
    }
}