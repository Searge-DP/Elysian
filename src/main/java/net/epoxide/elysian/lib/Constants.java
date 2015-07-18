package net.epoxide.elysian.lib;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Constants {

	public static final String MOD_ID = "elysian";
	public static final String MOD_NAME = "Elysian";
	public static final String VERSION_NUMBER = "0.0.0";
	public static final String CLIENT_PROXY_CLASS = "net.epoxide.elysian.client.ProxyClient";
	public static final String SERVER_PROXY_CLASS = "net.epoxide.elysian.common.ProxyCommon";
	public static final String FACTORY = "net.epoxide.elysian.client.gui.GuiFactoryElysian";
	public static final Random RANDOM = new Random();
	public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);
}
