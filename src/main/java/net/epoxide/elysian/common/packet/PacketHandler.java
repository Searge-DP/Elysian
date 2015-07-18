package net.epoxide.elysian.common.packet;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class PacketHandler {

	SimpleNetworkWrapper network;

	public PacketHandler() {

		network = NetworkRegistry.INSTANCE.newSimpleChannel("Elysian");
	}
}
