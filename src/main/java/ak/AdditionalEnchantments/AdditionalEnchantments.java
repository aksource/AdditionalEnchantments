package ak.AdditionalEnchantments;

import ak.AdditionalEnchantments.Enchantment.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static ak.AdditionalEnchantments.Constants.*;

@Mod(modid = AdditionalEnchantments.MOD_ID,
        name = AdditionalEnchantments.MOD_NAME,
        version = AdditionalEnchantments.MOD_VERSION,
        dependencies = AdditionalEnchantments.MOD_DEPENDENCIES,
        useMetadata = true,
        acceptedMinecraftVersions = AdditionalEnchantments.MOD_MC_VERSION)
public class AdditionalEnchantments {
    public static final String MOD_ID = "additionalenchantments";
    public static final String MOD_NAME = "AdditionalEnchantments";
    public static final String MOD_VERSION = "@VERSION@";
    public static final String MOD_DEPENDENCIES = "required-after:forge";
    public static final String MOD_MC_VERSION = "[1.11,1.99.99]";

//	@SidedProxy(clientSide = "ClientProxy", serverSide = "CommonProxy")
//	public static CommonProxy proxy;

    public static Enchantment vorpal;
    public static boolean addVorpal;
    public static Enchantment disjunction;
    public static boolean addDisjunction;
    public static Enchantment waterAspect;
    public static boolean addWaterAspect;
    public static Enchantment magicProtection;
    public static boolean addMagicProtection;
    public static Enchantment voidJump;
    public static boolean addVoidJump;

    public static final EntityEquipmentSlot[] SLOTS_MAIN_HAND = new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND};
    public static final EntityEquipmentSlot[] SLOTS_PROTECTS = new EntityEquipmentSlot[]{
            EntityEquipmentSlot.HEAD,
            EntityEquipmentSlot.CHEST,
            EntityEquipmentSlot.LEGS,
            EntityEquipmentSlot.FEET};

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        addVorpal = config.get(Configuration.CATEGORY_GENERAL, "addVorpal", true, "add Vorpal Enchantment").getBoolean(true);
        addDisjunction = config.get(Configuration.CATEGORY_GENERAL, "addDisjunction", true, "add Disjunction Enchantment").getBoolean(true);
        addWaterAspect = config.get(Configuration.CATEGORY_GENERAL, "addWaterAspect", true, "add WaterAspect Enchantment").getBoolean(true);
        addMagicProtection = config.get(Configuration.CATEGORY_GENERAL, "addMagicProtection", true, "add Magic Protection Enchantment").getBoolean(true);
        addVoidJump = config.get(Configuration.CATEGORY_GENERAL, "addVoidJump", true, "add Void Jump Enchantment").getBoolean(true);
        config.save();
        if (addVorpal) {
            vorpal = new EnchantmentVorpal(Enchantment.Rarity.RARE).setName("vorpal");
            MinecraftForge.EVENT_BUS.register(new VorpalEventHook());
        }
        if (addDisjunction) {
            disjunction = new EnchantmentDisjunction(Enchantment.Rarity.UNCOMMON).setName("disjunction");
            MinecraftForge.EVENT_BUS.register(disjunction);
        }
        if (addWaterAspect) {
            waterAspect = new EnchantmentWaterAspect(Enchantment.Rarity.UNCOMMON).setName("wateraspect");
            MinecraftForge.EVENT_BUS.register(waterAspect);
        }
        if (addMagicProtection) {
            magicProtection = new EnchantmentMagicProtection(Enchantment.Rarity.UNCOMMON).setName("magicprotection");
        }
        if (addVoidJump) {
            voidJump = new EnchantmentVoidJump(Enchantment.Rarity.VERY_RARE).setName("voidjump");
            MinecraftForge.EVENT_BUS.register(new VoidJumpEventHook());
        }
        MinecraftForge.EVENT_BUS.register(new RegistrationUtils());
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {
        sendIMCMessageForEC(NAME_DISJUNCTION, 13);
        sendIMCMessageForEC(NAME_MAGIC_PROTECTION, 8);
        sendIMCMessageForEC(NAME_WATER_ASPECT, 13);
        sendIMCMessageForEC(NAME_VORPAL, 13);
        sendIMCMessageForEC(NAME_VOID_JUMP, 14);

    }

    private void sendIMCMessageForEC(String registerName, int texId) {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setInteger("enchantId", 0);
        nbtTagCompound.setString("register_name", registerName);
        nbtTagCompound.setInteger("materiaTexId", texId);
        FMLInterModComms.sendMessage(MOD_ID_ENCHANT_CHANGER, IMC_MESSAGE_KEY_ENCHANT_CHANGER, nbtTagCompound);
    }
}