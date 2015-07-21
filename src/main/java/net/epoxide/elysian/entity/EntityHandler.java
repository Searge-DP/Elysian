package net.epoxide.elysian.entity;

import net.epoxide.elysian.entity.model.ModelElysianGolem;
import net.epoxide.elysian.entity.render.RenderElysianEnvCreature;
import net.epoxide.elysian.entity.render.RenderElysianGolem;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityHandler {
    
    public static IIcon entity;
    
    public EntityHandler() {
    
        MinecraftForge.EVENT_BUS.register(this);
        register();
    }
    
    private void register () {
    
        EntityRegistry.registerGlobalEntityID(EntityEnvironementCreature.class, "elysianWhisp", EntityRegistry.findGlobalUniqueEntityId(), 0xf0f0ff, 0x0ff0f0);
        EntityRegistry.registerGlobalEntityID(EntityRuneGolem.class, "elysianGolem", EntityRegistry.findGlobalUniqueEntityId(), 0xf0ffff, 0x0f00ff);
        
    }
    
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void icons (TextureStitchEvent.Pre evt) {
    
        if (evt.map.getTextureType() == 0) { // 0 is blocks, 1 items
            entity = evt.map.registerIcon("elysian:particles/particleEnvironement");
        }
    }
    
    /** registers the rendering for our entities to the main renderer map trough Forge */
    public static void registerRendering () {
    
        RenderingRegistry.registerEntityRenderingHandler(EntityRuneGolem.class, new RenderElysianGolem(new ModelElysianGolem(), 0.0f));
        RenderingRegistry.registerEntityRenderingHandler(EntityEnvironementCreature.class, new RenderElysianEnvCreature());
    }
}
