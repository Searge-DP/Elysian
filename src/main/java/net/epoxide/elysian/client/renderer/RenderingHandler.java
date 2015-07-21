package net.epoxide.elysian.client.renderer;

import net.epoxide.elysian.client.model.entity.ModelElysianGolem;
import net.epoxide.elysian.client.renderer.entity.RenderElysianEnvCreature;
import net.epoxide.elysian.client.renderer.entity.RenderElysianGolem;
import net.epoxide.elysian.entity.EntityEnvironementCreature;
import net.epoxide.elysian.entity.EntityRuneGolem;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class RenderingHandler {
    
    public RenderingHandler() {
    
        registerEntityRenderers();
        registerTileRenderers();
    }
    
    private static void registerEntityRenderers () {
    
        RenderingRegistry.registerEntityRenderingHandler(EntityRuneGolem.class, new RenderElysianGolem(new ModelElysianGolem(), 0.0f));
        RenderingRegistry.registerEntityRenderingHandler(EntityEnvironementCreature.class, new RenderElysianEnvCreature());
    }
    
    private static void registerTileRenderers () {
    
    }
}
