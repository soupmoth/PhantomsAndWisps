package net.scord.wisps.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.RandomChanceWithLootingLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtInt;
import net.minecraft.predicate.NbtPredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.util.Identifier;
import org.spongepowered.include.com.google.gson.JsonObject;

import java.io.FileReader;
import java.io.Reader;

public class ModLootTables {

    private static final Identifier PHANTOM_ID = new Identifier("minecraft", "entities/phantom");


    public static void modifyLootTables() {


        LootTableLoadingCallback.EVENT.register((resourceManager, manager, id, supplier, setter) -> {
            if (PHANTOM_ID.equals(id)) {

                Object obj = JsonParser.parseReader(new FileReader())

                NbtCompound nbt = new NbtCompound();
                nbt.putInt("Size", 16);

                FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceWithLootingLootCondition.builder(1f, 0.15f))
                        .conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().nbt(new NbtPredicate(nbt))))
                        .with(ItemEntry.builder(Items.DIAMOND));
                }
            });
    }



}
