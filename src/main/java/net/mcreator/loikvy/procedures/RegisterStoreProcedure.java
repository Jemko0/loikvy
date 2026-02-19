package net.mcreator.loikvy.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.network.chat.Component;

public class RegisterStoreProcedure {
	public static void execute(LevelAccessor world, String additionalInfo, String dateOfReg, String ownerName, String responsibleParty, String storeAddress, String storeDesiredName, String storeRegistryName, String storeType, String storeX,
			String storeY, String storeZ) {
		if (additionalInfo == null || dateOfReg == null || ownerName == null || responsibleParty == null || storeAddress == null || storeDesiredName == null || storeRegistryName == null || storeType == null || storeX == null || storeY == null
				|| storeZ == null)
			return;
		String jsonString = "";
		jsonString = MakeJSONPropertyProcedure.execute(false, true, storeDesiredName, "store_name") + "" + MakeJSONPropertyProcedure.execute(false, true, storeType, "store_type") + MakeJSONPropertyProcedure.execute(false, true, ownerName, "owner")
				+ MakeJSONPropertyProcedure.execute(true, true,
						MakeJSONPropertyProcedure.execute(false, true, storeX, "x") + "" + MakeJSONPropertyProcedure.execute(false, true, storeY, "y") + MakeJSONPropertyProcedure.execute(false, true, storeZ, "z")
								+ MakeJSONPropertyProcedure.execute(false, false, storeAddress, "address"),
						"store_location")
				+ MakeJSONPropertyProcedure.execute(false, true, dateOfReg, "date_of_registration") + MakeJSONPropertyProcedure.execute(false, true, responsibleParty, "responsible_party")
				+ MakeJSONPropertyProcedure.execute(false, false, additionalInfo, "additional_data_csl");
		if (!world.isClientSide() && world.getServer() != null)
			world.getServer().getPlayerList().broadcastSystemMessage(Component.literal(("{" + jsonString + "}")), false);
		DataModifyStorageProcedure.execute(world, "set", "minecraft:registry", "stores" + "." + storeRegistryName, "{" + jsonString + "}");
	}
}