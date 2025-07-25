package net.mcreator.loikvy.procedures;

import net.minecraft.world.entity.Entity;

public class BurgerPlayerFinishesUsingItemProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		PlayerGainHappinessProcedure.execute(entity, 15);
	}
}