package net.mcreator.loikvy.procedures;

import net.minecraft.world.entity.Entity;

import net.mcreator.loikvy.entity.ShopCashierEntity;
import net.mcreator.loikvy.LoikvyMod;

public class StoreGUIThisGUIIsOpenedProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		String value = "";
		String shopData = "";
		shopData = entity instanceof ShopCashierEntity _datEntS ? _datEntS.getEntityData().get(ShopCashierEntity.DATA_shop_data) : "";
		value = GetJSONValueProcedure.execute(shopData, "key");
		LoikvyMod.LOGGER.info(value);
		LoikvyMod.LOGGER.info(shopData);
	}
}