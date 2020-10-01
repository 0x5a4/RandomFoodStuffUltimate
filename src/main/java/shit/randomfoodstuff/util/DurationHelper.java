package shit.randomfoodstuff.util;

public class DurationHelper {

	public static int secondsToTicks(int seconds) {
		return seconds * 20;
	}
	
	public static int minutesToTicks(int minutes) {
		return minutes * 1200;
	}
	
	public static int ticksToSeconds(int ticks) {
		return ticks / 20;
	}
	
	public static int minutesToSeconds(int minutes) {
		return minutes * 60;
	}
	
}
