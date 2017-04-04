package ak.AdditionalEnchantments;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = AdditionalEnchantments.MOD_ID,
        name = AdditionalEnchantments.MOD_NAME,
        version = AdditionalEnchantments.MOD_VERSION,
        dependencies = AdditionalEnchantments.MOD_DEPENDENCIES,
        useMetadata = true,
        acceptedMinecraftVersions = AdditionalEnchantments.MOD_MC_VERSION)
public class AdditionalEnchantments {
    public static final String MOD_ID = "AdditionalEnchantments";
    public static final String MOD_NAME = "AdditionalEnchantments";
    public static final String MOD_VERSION = "@VERSION@";
    public static final String MOD_DEPENDENCIES = "required-after:Forge@[11.14.0.1237,)";
    public static final String MOD_MC_VERSION = "[1.9,1.10.99]";

//	@SidedProxy(clientSide = "ClientProxy", serverSide = "CommonProxy")
//	public static CommonProxy proxy;

    public static Enchantment vorpal;
    public static boolean addVorpal;
    public static int idVorpal;
    public static final String NAME_VORPAL = "vorpal";
    public static Enchantment disjunction;
    public static boolean addDisjunction;
    public static int idDisjunction;
    public static final String NAME_DISJUNCTION = "disjunction";
    public static Enchantment waterAspect;
    public static boolean addWaterAspect;
    public static int idWaterAspect;
    public static final String NAME_WATER_ASPECT = "water_aspect";
    public static Enchantment magicProtection;
    public static boolean addMagicProtection;
    public static int idMagicProtection;
    public static final String NAME_MAGIC_PROTECTION = "magic_protection";
    public static Enchantment voidJump;
    public static boolean addVoidJump;
    public static int idVoidJump;
    public static final String NAME_VOID_JUMP = "void_jump";

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
        idVorpal = config.get(Configuration.CATEGORY_GENERAL, "idVorpal", 22, "Vorpal Enchantment Id").getInt();
        addDisjunction = config.get(Configuration.CATEGORY_GENERAL, "addDisjunction", true, "add Disjunction Enchantment").getBoolean(true);
        idDisjunction = config.get(Configuration.CATEGORY_GENERAL, "idDisjunction", 23, "Disjunction Enchantment Id").getInt();
        addWaterAspect = config.get(Configuration.CATEGORY_GENERAL, "addWaterAspect", true, "add WaterAspect Enchantment").getBoolean(true);
        idWaterAspect = config.get(Configuration.CATEGORY_GENERAL, "idWaterAspect", 24, "WaterAspect Enchantment Id").getInt();
        addMagicProtection = config.get(Configuration.CATEGORY_GENERAL, "addMagicProtection", true, "add Magic Protection Enchantment").getBoolean(true);
        idMagicProtection = config.get(Configuration.CATEGORY_GENERAL, "idMagicProtection", 10, "Magic Protection Enchantment Id").getInt();
        addVoidJump = config.get(Configuration.CATEGORY_GENERAL, "addVoidJump", true, "add Void Jump Enchantment").getBoolean(true);
        idVoidJump = config.get(Configuration.CATEGORY_GENERAL, "idVoidJump", 11, "Void Jump Enchantment Id").getInt();
        config.save();
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {
        if (addVorpal) {
            vorpal = new EnchantmentVorpal(idVorpal, Enchantment.Rarity.RARE).setName("vorpal");
            MinecraftForge.EVENT_BUS.register(new VorpalEventHook());
        }
        if (addDisjunction) {
            disjunction = new EnchantmentDisjunction(idDisjunction, Enchantment.Rarity.UNCOMMON).setName("disjunction");
            MinecraftForge.EVENT_BUS.register(disjunction);
        }
        if (addWaterAspect) {
            waterAspect = new EnchantmentWaterAspect(idWaterAspect, Enchantment.Rarity.UNCOMMON).setName("wateraspect");
            MinecraftForge.EVENT_BUS.register(waterAspect);
        }
        if (addMagicProtection) {
            magicProtection = new EnchantmentMagicProtection(idMagicProtection, Enchantment.Rarity.UNCOMMON).setName("magicprotection");
        }
        if (addVoidJump) {
            voidJump = new EnchantmentVoidJump(idVoidJump, Enchantment.Rarity.VERY_RARE).setName("voidjump");
            MinecraftForge.EVENT_BUS.register(new VoidJumpEventHook());
        }
        sendIMCMessageForEC(idDisjunction, 13);
        sendIMCMessageForEC(idMagicProtection, 8);
        sendIMCMessageForEC(idWaterAspect, 13);
        sendIMCMessageForEC(idVorpal, 13);
        sendIMCMessageForEC(idVoidJump, 14);

    }

    private void sendIMCMessageForEC(int enchantId, int texId) {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setInteger("enchantId", enchantId);
        nbtTagCompound.setInteger("materiaTexId", texId);
        FMLInterModComms.sendMessage("EnchantChanger", "registerExtraMateria", nbtTagCompound);
    }

    public static ResourceLocation getResourceLocation(String name) {
        return new ResourceLocation(AdditionalEnchantments.MOD_ID + ":" + name);
    }
}