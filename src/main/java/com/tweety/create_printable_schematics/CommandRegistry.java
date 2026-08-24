package com.tweety.create_printable_schematics;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class CommandRegistry {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("create")
                        .then(Commands.literal("makeHandPrintable").requires(cs -> cs.hasPermission(2))
                                .executes(context -> {
                                    ServerPlayer player = context.getSource().getPlayerOrException();
                                    ItemStack stack = player.getMainHandItem();

                                    if (ForgeRegistries.ITEMS.getKey(stack.getItem()).toString().equals("create:schematic")) {
                                        stack.getOrCreateTag().putBoolean("Printable", true);
                                        return 1;
                                    }

                                    context.getSource().sendFailure(Component.literal("You are not holding a schematic.").withStyle(ChatFormatting.RED));

                                    return 0;
                                })
                        )
        );
    }
}