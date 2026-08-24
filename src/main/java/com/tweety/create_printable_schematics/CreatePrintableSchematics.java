package com.tweety.create_printable_schematics;

import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(CreatePrintableSchematics.MODID)
public class CreatePrintableSchematics {
    public static final String MODID = "create_printable_schematics";
    private static final Logger LOGGER = LogUtils.getLogger();

    public CreatePrintableSchematics() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.BUILDER.build());
    }

}
