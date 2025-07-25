package net.mcreator.loikvy.procedures;

import net.mcreator.loikvy.network.LoikvyModVariables;

public class GetCalendarDaysProcedure {
	public static String execute() {
		return new java.text.DecimalFormat("##.##").format(Math.floor(LoikvyModVariables.gWorldDays % 30 + 1)) + "/" + new java.text.DecimalFormat("##.##").format(Math.floor(LoikvyModVariables.gWorldMonths % 12 + 1)) + "/"
				+ new java.text.DecimalFormat("##.##").format(Math.floor(LoikvyModVariables.gWorldYears));
	}
}