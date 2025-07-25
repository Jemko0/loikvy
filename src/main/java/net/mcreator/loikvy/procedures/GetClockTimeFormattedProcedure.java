package net.mcreator.loikvy.procedures;

import net.mcreator.loikvy.network.LoikvyModVariables;

public class GetClockTimeFormattedProcedure {
	public static String execute() {
		return GetClockTimeDigitProcedure.execute(Math.floor(LoikvyModVariables.gWorldHours + 30) % 24) + ":" + GetClockTimeDigitProcedure.execute(Math.floor(LoikvyModVariables.gWorldMinutes % 60)) + ":"
				+ GetClockTimeDigitProcedure.execute(Math.floor(LoikvyModVariables.gWorldSeconds % 60));
	}
}