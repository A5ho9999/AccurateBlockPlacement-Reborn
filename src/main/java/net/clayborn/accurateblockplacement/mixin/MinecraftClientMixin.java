package net.clayborn.accurateblockplacement.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.clayborn.accurateblockplacement.AccurateBlockPlacementMod;
import net.clayborn.accurateblockplacement.IMinecraftClientAccessor;
import net.minecraft.client.MinecraftClient;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin implements IMinecraftClientAccessor
{
	@Shadow
	protected abstract void doItemUse();

	@Shadow
	private int itemUseCooldown;
	
	@Override
	public void accurateblockplacement_DoItemUseBypassDisable()
	{
		Boolean oldValue = AccurateBlockPlacementMod.disableNormalItemUse;
		AccurateBlockPlacementMod.disableNormalItemUse = false;
		doItemUse();
		AccurateBlockPlacementMod.disableNormalItemUse = oldValue;
	}
	
	@Inject(method = "doItemUse", at = @At("HEAD"), cancellable = true)
	void OnDoItemUse(CallbackInfo info)
	{
		if(AccurateBlockPlacementMod.disableNormalItemUse) {
			info.cancel();
		}
	}

    @Override
	public int accurateblockplacement_GetItemUseCooldown()
	{
		return itemUseCooldown;
	}
}
