package com.tweety.create_printable_schematics.mixin;

import com.simibubi.create.CreateClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import com.simibubi.create.content.schematics.client.tools.ToolType;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = ToolType.class, remap = false)
public class ToolTypeMixin {

    @Inject(method = "getTools(Z)Ljava/util/List;", at = @At("RETURN"), cancellable = true)
    private static void appendPrintToolIfPrintable(boolean creative, CallbackInfoReturnable<List<ToolType>> cir) {
        List<ToolType> originalTools = cir.getReturnValue();
        List<ToolType> mutableTools = new ArrayList<>(originalTools);

        if (!mutableTools.contains(ToolType.PRINT)) {
            LocalPlayer player = Minecraft.getInstance().player;

            if (player != null) {
                ItemStack activeSchematicItem = CreateClient.SCHEMATIC_HANDLER.getActiveSchematicItem();

                if (activeSchematicItem != null
                        && activeSchematicItem.getTag() != null
                        && activeSchematicItem.getTag().getBoolean("Printable")) {

                    mutableTools.add(ToolType.PRINT);
                }
            }
        }

        cir.setReturnValue(mutableTools);
    }
}