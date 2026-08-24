package com.tweety.create_printable_schematics.mixin;

import com.simibubi.create.content.schematics.packet.SchematicPlacePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = SchematicPlacePacket.class)
public class SchematicPlacePacketMixin {

    @Shadow(remap = false)
    public ItemStack stack;

    @Inject(method = "handle", at = @At("TAIL"), remap = false)
    private void consumeSchematicIfPrinted(NetworkEvent.Context context, CallbackInfoReturnable<Boolean> cir) {

        ServerPlayer player = context.getSender();

        if (player == null || player.isCreative() || stack.getTag() == null || !stack.getTag().getBoolean("Printable"))
            return;

        ItemStack heldStack = player.getMainHandItem();

        if (heldStack.is(stack.getItem()) && heldStack.hasTag() && heldStack.getTag().getBoolean("Printable")) {
            heldStack.shrink(1);
        }
    }

    @Redirect(method = "lambda$handle$2", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;isCreative()Z"))
    private boolean allowPlaceableItemOrCreative(ServerPlayer player) {
        return player.isCreative() || (stack.getTag() != null && stack.getTag().getBoolean("Printable"));
    }
}