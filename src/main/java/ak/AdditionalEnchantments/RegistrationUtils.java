package ak.AdditionalEnchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import static ak.AdditionalEnchantments.AdditionalEnchantments.*;

/**
 * Created by A.K. on 2018/06/16.
 */
public class RegistrationUtils {
    @SubscribeEvent
    @SuppressWarnings("unused")
    public void registerEnchantment(RegistryEvent.Register<Enchantment> event) {
        IForgeRegistry<Enchantment> registry = event.getRegistry();

        if (addVorpal) {
            registry.register(vorpal);
        }
        if (addDisjunction) {
            registry.register(disjunction);
        }
        if (addWaterAspect) {
            registry.register(waterAspect);
        }
        if (addMagicProtection) {
            registry.register(magicProtection);
        }
        if (addVoidJump) {
            registry.register(voidJump);
        }
    }
}
