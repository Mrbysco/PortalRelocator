package com.mrbysco.portalrelocator.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mrbysco.portalrelocator.config.PortalConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.NetherPortalBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(NetherPortalBlock.class)
public class NetherPortalBlockMixin {
	@ModifyExpressionValue(
			method = "getPortalDestination",
			at = @At(value = "INVOKE",
					target = "Lnet/minecraft/server/MinecraftServer;getLevel(Lnet/minecraft/resources/ResourceKey;)Lnet/minecraft/server/level/ServerLevel;"))
	private ServerLevel PortalRelocation$getPortalDestination(ServerLevel originalDimension, @Local(argsOnly = true) ServerLevel currentLevel) {
		ResourceLocation targetDimension = PortalConfig.findNetherRedirect(currentLevel.dimension().location());
		if (targetDimension == null) return originalDimension;

		ServerLevel dest = currentLevel.getServer().getLevel(ResourceKey.create(Registries.DIMENSION, targetDimension));
		return dest != null ? dest : originalDimension;
	}

}
