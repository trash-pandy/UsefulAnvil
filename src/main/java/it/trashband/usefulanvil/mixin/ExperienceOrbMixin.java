package it.trashband.usefulanvil.mixin;

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
        var tag = item.getOrCreateTag();
        if (!tag.contains("usefulanvil.mended_health")) {
            tag.putInt("usefulanvil.mended_health", 0);
        }
        // maximum mendable health = 3 complete repairs
        var max_mended_health = max_damage * 3;
        // current amount of health mended
        var mended_health = tag.getInt("usefulanvil.mended_health");
        // percentage of max mend cap taken
        var mended_percent = Math.min((double) mended_health / max_mended_health, 1.0);
        // reduce the amount that will be repaired to a minimum of 1, as long as the total mended health isn't over the
        // maximum amount of health that can be mended
        var repair_amount = (int) Math.max(Math.round(repair_amount_was * (1.0 - mended_percent)), mended_health >= max_mended_health ? 0 : 1);
        // save total mended health
        tag.putInt("usefulanvil.mended_health", Math.min(mended_health + repair_amount, max_mended_health));

        // repair item
        item.setDamageValue(Math.max(item_damage - repair_amount, 0));
    }
}
