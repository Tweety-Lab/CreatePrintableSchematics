package com.tweety.create_printable_schematics.mixin;

import com.simibubi.create.content.schematics.SchematicItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = SchematicItem.class)
public class SchematicItemMixin {

    @Inject(method = "appendHoverText", at = @At("HEAD"))
    @OnlyIn(value = Dist.CLIENT)
    private void appendIfPrintable(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn, CallbackInfo ci) {
        if (stack.getTag() != null && stack.getTag().getBoolean("Printable")) {
            tooltip.add(Component.translatable("item.create_printable_schematics.schematic.tooltip.printable").withStyle(ChatFormatting.DARK_GRAY));
        }
    }
}
