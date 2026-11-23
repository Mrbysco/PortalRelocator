package com.mrbysco.portalrelocator;

import com.mojang.logging.LogUtils;
import com.mrbysco.portalrelocator.config.PortalConfig;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.slf4j.Logger;

@Mod(PortalRelocatorMod.MOD_ID)
public class PortalRelocatorMod {
	public static final String MOD_ID = "portalrelocator";
	public static final Logger LOGGER = LogUtils.getLogger();

	public PortalRelocatorMod(IEventBus eventBus, Dist dist, ModContainer container) {
		container.registerConfig(ModConfig.Type.COMMON, PortalConfig.commonSpec);

		if (dist.isClient()) {
			container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
		}
	}
}
