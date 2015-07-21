package net.epoxide.elysian.entity;

import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityHandler {
    
    public static IIcon entity;
    
    public EntityHandler() {
    
        MinecraftForge.EVENT_BUS.register(this);
        registerLivingEntities();
    }
    
    private static void registerLivingEntities () {
    
        EntityRegistry.registerGlobalEntityID(EntityEnvironementCreature.class, "elysianWhisp", EntityRegistry.findGlobalUniqueEntityId(), 0xf0f0ff, 0x0ff0f0);
        EntityRegistry.registerGlobalEntityID(EntityRuneGolem.class, "elysianGolem", EntityRegistry.findGlobalUniqueEntityId(), 0xf0ffff, 0x0f00ff);
    }
    
    // TODO this shouldn't be existing
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void icons (TextureStitchEvent.Pre evt) {
    
        if (evt.map.getTextureType() == 0) { // 0 is blocks, 1 items
            entity = evt.map.registerIcon("elysian:particles/particleEnvironement");
        }
    }
}
