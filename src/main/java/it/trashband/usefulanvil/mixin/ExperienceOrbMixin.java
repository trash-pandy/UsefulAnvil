package it.trashband.usefulanvil.mixin;

import it.trashband.usefulanvil.UsefulAnvil;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ExperienceOrb.class)
public class ExperienceOrbMixin {
    @Redirect(method = "repairPlayerItems", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;setDamageValue(I)V"))
    public void ItemStackSetDamageValue(ItemStack item, int i) {
        var item_damage = item.getDamageValue();
        var max_damage = item.getMaxDamage();
        var repair_amount_was = item_damage - i;
        var tag = item.getOrCreateTagElement("it.trashband.usefulanvil");
        var current_mended_health = tag.getInt("mended_health");

        int repair_amount = 0;
        var max_mended_health = max_damage * UsefulAnvil.CONFIG.repairsBeforeBreak();

        switch (UsefulAnvil.CONFIG.mendingDecayStyle()) {
            case LINEAR -> {
                // percentage of max mend cap taken
                var mended_percent = Math.min((double) current_mended_health / max_mended_health, 1.0);
                // reduce the amount that will be repaired to a minimum of 1, as long as the total mended health isn't over the
                // maximum amount of health that can be mended
                repair_amount = (int) Math.max(Math.round(repair_amount_was * (1.0 - mended_percent)), current_mended_health >= max_mended_health ? 0 : 1);
            }
            case STOP_AT_END -> {
                if (current_mended_health < max_mended_health) {
                    repair_amount = Math.min(repair_amount_was, item_damage);
                }
            }
        }

        repair_amount = Math.min(Math.min(repair_amount, max_mended_health - current_mended_health - repair_amount), item_damage);
        // save total mended health
        tag.putInt("mended_health", current_mended_health + repair_amount);
        // repair item
        item.setDamageValue(Math.max(item_damage - repair_amount, 0));
    }
}
