package com.tweety.create_printable_schematics.mixin;

import com.simibubi.create.content.schematics.packet.SchematicPlacePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = SchematicPlacePacket.class)
public class SchematicPlacePacketMixin {

    @Shadow(remap = false)
    public ItemStack stack;

    @Redirect(method = "lambda$handle$2", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;isCreative()Z"))
    private boolean allowPlaceableItemOrCreative(ServerPlayer player) {
        return player.isCreative() || (stack.getTag() != null && stack.getTag().getBoolean("Printable"));
    }
}