package net.epoxide.elysian.client;

import net.epoxide.elysian.common.ProxyCommon;
import net.epoxide.elysian.items.models.ModelTurtleSuit;
import net.minecraft.client.model.ModelBiped;

public class ProxyClient extends ProxyCommon {

	private static final ModelTurtleSuit generalArmor = new ModelTurtleSuit(1.0f); 
	private static final ModelTurtleSuit legsArmor = new ModelTurtleSuit(0.5f);

	public ModelBiped getArmorModel(int id){
		
		switch (id) {
		case 0:
			return generalArmor;

		case 1:
			return legsArmor;
			
		}
		return null;
	}

	@Override
	public void preInit () {

	}

	@Override
	public void init () {

	}

	@Override
	public void postInit () {

	}
}
