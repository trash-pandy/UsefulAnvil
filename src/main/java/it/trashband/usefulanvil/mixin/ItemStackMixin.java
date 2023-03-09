package it.trashband.usefulanvil.mixin;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Inject(method = "getTooltipLines", at = @At("RETURN"))
    public void ItemStackAppendEnchantmentNames(Player player, TooltipFlag tooltipFlag, CallbackInfoReturnable<List<Component>> cir) {
        var item = (ItemStack) (Object) this;
        var list = cir.getReturnValue();
        for (Component component : list) {
            if (component instanceof MutableComponent) {
                var contents = component.getContents();
                if (contents instanceof TranslatableContents) {
                    if (((TranslatableContents) contents).getKey().equals("enchantment.minecraft.mending")) {
                        var tag = item.getOrCreateTagElement("it.trashband.usefulanvil");
                        if (tag.contains("mended_health")) {
                            var mended_health = (double) tag.getInt("mended_health");
                            var efficacy = 1.0 - mended_health / (item.getMaxDamage() * 3);
                            ((MutableComponent) component)
                                .append(String.format(" (%d%%)", (int) Math.ceil(efficacy * 100)));
                        } else {
                            ((MutableComponent) component)
                                .append(" (100%)");
                        }
                    }
                }
            }
        }
    }
}
