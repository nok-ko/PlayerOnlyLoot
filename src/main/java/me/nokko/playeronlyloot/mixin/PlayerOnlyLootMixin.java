package me.nokko.playeronlyloot.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
// Lnet/minecraft/entity/player/PlayerEntity;jump()V
public abstract class PlayerOnlyLootMixin extends Entity {
	public PlayerOnlyLootMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Inject(at = @At("HEAD"), method = "drop(Lnet/minecraft/entity/damage/DamageSource;)V", cancellable = true)
	private void preventNonPlayerDrops(DamageSource damageSource, CallbackInfo ci) {
		Entity attacker = damageSource.getAttacker();
		if (attacker == null || !attacker.isPlayer()) {
			ci.cancel();
		}
	}
}
