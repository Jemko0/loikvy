package net.mcreator.loikvy.procedures;

import net.mcreator.loikvy.network.LoikvyModVariables;

public class BasicClockPropertyValueProviderProcedure {
	public static double execute() {
		return LoikvyModVariables.gWorldHours % 24;
	}
}