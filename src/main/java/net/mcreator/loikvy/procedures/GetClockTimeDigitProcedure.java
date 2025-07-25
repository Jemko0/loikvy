package net.mcreator.loikvy.procedures;

public class GetClockTimeDigitProcedure {
	public static String execute(double clockDigit) {
		if (clockDigit > 9) {
			return new java.text.DecimalFormat("##.##").format(clockDigit);
		}
		return "0" + new java.text.DecimalFormat("##.##").format(clockDigit);
	}
}