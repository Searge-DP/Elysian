package net.epoxide.elysian.client;

import net.epoxide.elysian.client.renderer.RenderingHandler;
import net.epoxide.elysian.common.ProxyCommon;
import net.minecraft.client.Minecraft;

public class ProxyClient extends ProxyCommon {
    
    @Override
    public void preInit () {
    
        new RenderingHandler();
    }
    
    @Override
    public void init () {
    
    }
    
    @Override
    public void postInit () {
    
    }
    
    @Override
    public Minecraft getMinecraft(){
    	return Minecraft.getMinecraft();
    }
}
