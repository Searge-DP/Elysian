package net.epoxide.elysian.common;

import net.minecraft.client.model.ModelBiped;

public class ProxyCommon {
    
    /**
     * A sided method used to get the armor model for rendering. Server version will always
     * return null, as rendering is exclusively done on the client
     */
    public ModelBiped getArmorModel (int id) {
    
        return null;
    }
    
    /**
     * A sided version of the preInit event. This method allows for code to be executed
     * exclusively on the client or server side during the pre-initialization phase.
     */
    public void preInit () {
    
    }
    
    /**
     * A sided version of the init event. This method allows for code to be executed
     * exclusively on the client or server side during the initialization phase.
     */
    public void init () {
    
    }
    
    /**
     * A sided version of the postInit event. This method allows for code to be executed
     * exclusively on the client or server side during the post-initialization phase.
     */
    public void postInit () {
    
    }
}