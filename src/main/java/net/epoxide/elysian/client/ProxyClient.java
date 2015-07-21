package net.epoxide.elysian.client;

import net.epoxide.elysian.common.ProxyCommon;
import net.epoxide.elysian.entity.EntityEnvironementCreature;
import net.epoxide.elysian.entity.EntityHandler;
import net.epoxide.elysian.entity.render.RenderElysianEnvCreature;
import cpw.mods.fml.client.registry.RenderingRegistry;

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
	public void registerRenderers() {
		EntityHandler.registerRendering();
	}
}
