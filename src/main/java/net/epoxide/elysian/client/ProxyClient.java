package net.epoxide.elysian.client;

import net.epoxide.elysian.common.ProxyCommon;
import net.epoxide.elysian.entity.EntityHandler;

public class ProxyClient extends ProxyCommon {
    
    @Override
    public void preInit () {
    
    }
    
    @Override
    public void init () {
    
    }
    
    @Override
    public void postInit () {
    
    }
    
    @Override
    public void registerRenderers () {
    
        EntityHandler.registerRendering();
    }
}
