package net.epoxide.elysian.client.renderer;

import net.epoxide.elysian.client.model.entity.ModelElysianGolem;
import net.epoxide.elysian.client.renderer.entity.RenderElysianGolem;
import net.epoxide.elysian.client.renderer.entity.RenderWhisp;
import net.epoxide.elysian.entity.EntityRuneGolem;
import net.epoxide.elysian.entity.EntityWhisp;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class RenderingHandler {
    
    public RenderingHandler() {
    
        registerEntityRenderers();
        registerTileRenderers();
    }
    
    private static void registerEntityRenderers () {
    
        RenderingRegistry.registerEntityRenderingHandler(EntityRuneGolem.class, new RenderElysianGolem(new ModelElysianGolem(), 0.0f));
        RenderingRegistry.registerEntityRenderingHandler(EntityWhisp.class, new RenderWhisp());
    }
    
    private static void registerTileRenderers () {
    
    }
}
