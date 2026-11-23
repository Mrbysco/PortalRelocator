package com.mrbysco.portalrelocator.config;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PortalConfig {
	public static class Common {
		public final ModConfigSpec.ConfigValue<List<? extends String>> netherPortalRedirects;

		Common(ModConfigSpec.Builder builder) {
			builder.comment("Portal settings")
					.push("portal");

			netherPortalRedirects = builder
					.comment("Changes what dimension the Nether Portal will link to when entering. [Format: starting_dimension=ending_dimension]")
					.defineListAllowEmpty("netherPortalRedirects",
							List.of(
									"minecraft:overworld=minecraft:the_nether", "minecraft:the_nether=minecraft:overworld"
							),
							String::new,
							o -> o instanceof String s && s.split("=").length == 2);

			builder.pop();
		}
	}

	@Nullable
	public static ResourceLocation findNetherRedirect(ResourceLocation originalDimension) {
		for (String entry : COMMON.netherPortalRedirects.get()) {
			String[] parts = entry.split("=");
			if (ResourceLocation.parse(parts[0].trim()).equals(originalDimension)) {
				return ResourceLocation.parse(parts[1].trim());
			}
		}
		return null;
	}

	public static final ModConfigSpec commonSpec;
	public static final Common COMMON;

	static {
		final Pair<Common, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(Common::new);
		commonSpec = specPair.getRight();
		COMMON = specPair.getLeft();
	}

}
