
package net.mcreator.buxin.color;

import net.minecraft.ChatFormatting;

public class BuxinWeaponColor {
	private static final ChatFormatting[] colour;
	private static final ChatFormatting[] colour2;

	public BuxinWeaponColor() {
	}

	public static String formatting(String input, ChatFormatting[] colours, double delay) {
		StringBuilder rainbow = new StringBuilder(input.length() * 3);
		if (delay <= 0.0) {
			delay = 0.001;
		}

		int offset = (int)Math.floor((double)(System.currentTimeMillis() & 16383L) / delay) % colours.length;

		for(int i = 0; i < input.length(); ++i) {
			char c = input.charAt(i);
			rainbow.append(colours[(colours.length + i - offset) % colours.length].toString());
			rainbow.append(c);
		}

		return rainbow.toString();
	}

	public static String makeColour(String input) {
		return formatting(input, colour, 91.7813);
	}

	public static String makeColour2(String input) {
		return formatting(input, colour2, 91.7813);
	}

	static {
		colour = new ChatFormatting[]{ChatFormatting.RED, ChatFormatting.GOLD, ChatFormatting.YELLOW, ChatFormatting.GREEN, ChatFormatting.AQUA, ChatFormatting.BLUE, ChatFormatting.LIGHT_PURPLE};
		colour2 = new ChatFormatting[]{ChatFormatting.AQUA};
	}
}
