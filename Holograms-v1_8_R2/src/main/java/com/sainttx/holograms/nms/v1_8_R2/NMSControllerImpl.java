package com.sainttx.holograms.nms.v1_8_R2;

import com.sainttx.holograms.api.HologramLine;
import com.sainttx.holograms.api.NMSController;
import com.sainttx.holograms.api.NMSEntityBase;
import net.minecraft.server.v1_8_R2.Entity;
import net.minecraft.server.v1_8_R2.WorldServer;
import org.bukkit.Chunk;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;

/**
 * Created by Matthew on 08/01/2015.
 */
public class NMSControllerImpl implements NMSController {

    @Override
    public NMSEntityArmorStandExtend spawnArmorStand(org.bukkit.World world, double x, double y, double z, HologramLine parentPiece) {
        WorldServer nmsWorld = ((CraftWorld) world).getHandle();
        NMSEntityArmorStandExtend armorStand = new NMSEntityArmorStandExtend(nmsWorld, parentPiece);
        armorStand.setLocationNMS(x, y, z);
        if (!addEntityToWorld(nmsWorld, armorStand)) {
            System.out.print("Could not spawn armor stand");
        }

        return armorStand;
    }

    private boolean addEntityToWorld(WorldServer nmsWorld, Entity nmsEntity) {
        net.minecraft.server.v1_8_R2.Chunk nmsChunk = nmsWorld.getChunkAtWorldCoords(nmsEntity.getChunkCoordinates());

        if (nmsChunk != null) {
            Chunk chunk = nmsChunk.bukkitChunk;

            if (!chunk.isLoaded()) {
                chunk.load();
                System.out.print("Loaded chunk " + chunk + " for hologram");
            }
        } else {
            System.out.print("Chunk not found for Hologram");
        }

        return nmsWorld.addEntity(nmsEntity, CreatureSpawnEvent.SpawnReason.CUSTOM);
    }

    @Override
    public NMSEntityBase getNMSEntityBase(org.bukkit.entity.Entity bukkitEntity) {
        Entity nmsEntity = ((CraftEntity) bukkitEntity).getHandle();
        return nmsEntity instanceof NMSEntityBase ? (NMSEntityBase) nmsEntity : null;
    }
}
