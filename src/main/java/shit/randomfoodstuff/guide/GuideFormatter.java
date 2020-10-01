package shit.randomfoodstuff.guide;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.FontRenderer;
import shit.randomfoodstuff.util.ColorHelper;

@SideOnly(Side.CLIENT)
public class GuideFormatter {
	
	//Formatting Tokens
	public static String NEWLINE = "nl";
	public static String NEWPAGE = "p";
	public static String COLOR = "c";
	public static String RESETCOLOR = "r";
	
	public static int FONT_HEIGHT = 9;
	
	private GuideArticle parent;
	private String defaultTextColor = ColorHelper.getColorByName("RESET");
	private String color = defaultTextColor;
	private int lineSpacing = 5;
	
	public GuideFormatter(GuideArticle parent) {
		this.parent = parent;
	}
	
	/**
	 * @param renderer The FontRenderer that is used to render Text inside the Guide. Nedded to calculate if certain Text fits in a line
	 */
	@SideOnly(Side.CLIENT)
	public void processText(FontRenderer renderer) {
		
		System.out.println("Processing Guide Text for Entry " + parent.getIdentifier());
		
		//Grab the Raw Text;
		String rawText = parent.getRawText();
		
		//Calculate Line Amount
		int lines = parent.heigth / (FONT_HEIGHT + lineSpacing);
		
		//Create GuidePage Obj
		GuideTextPage page = new GuideTextPage(lines);
		
		//Start Parsing
		String currLine = "";
		for (int i = 0; i < rawText.length(); i++) {
			char c = rawText.charAt(i);
			if (Character.isWhitespace(c) || i == 0) {
				String nextWord = " " + color;
				String nextWordOnlyChars = " ";
				
				if (i == 0) {
					nextWord += color + c;
				}
				
				boolean flag = true;
				for (int j = i + 1; j < rawText.length(); j++) {
					char c2 = rawText.charAt(j);
					
					//Next Word detected
					if (Character.isWhitespace(c2)  || j == rawText.length() - 1) {
						if (j == rawText.length() - 1) {
							nextWord += color + c2;
						}
						
						if (renderer.getStringWidth(currLine + nextWordOnlyChars) >= parent.width) {
							if (!page.addLine(color + currLine)) {
								parent.addTextPage(page);
								page = new GuideTextPage(lines);
								page.addLine(color + currLine);
							}
							currLine = "";
							currLine += nextWord;
						} else {
							currLine += nextWord;
						}
						break;
					}
					
					//Formatting
					flag = (c2 == '{') ? false : (c2 == '}') ? true : flag;
					if (!flag) {
						if (c2 == '{') {
							String formatting = "";
							for (int k = j + 1; rawText.charAt(k) != '}'; k++) {
								if (rawText.charAt(k) == '}') {
									j = k;
									break;
								}
								if (!Character.isWhitespace(rawText.charAt(k))) formatting += rawText.charAt(k);
							}
							
							String token = getFormattingToken(formatting);
							
							//New Line
							if (token.equals(NEWLINE)) {
								currLine += nextWord;
								if (!page.addLine(currLine)) {
									parent.addTextPage(page);
									page = new GuideTextPage(lines);
									page.addLine(currLine);
								}
								currLine = "";
								nextWord = " ";
								nextWordOnlyChars = " ";
							}
							
							//NEWPAGE
							if (token.equals(NEWPAGE)) {
								currLine += nextWord;
								if (!page.addLine(currLine)) {
									parent.addTextPage(page);
									page = new GuideTextPage(lines);
									page.addLine(currLine);
								} 
								parent.addTextPage(page);
								page = new GuideTextPage(lines);
								currLine = "";
								nextWord = " ";
								nextWordOnlyChars = " ";
							}
							
							//Color
							if (token.equals(COLOR)) {
								String value = getFormattingValue(formatting);
								if (value == null) {
									System.out.println("Color Token needs a Value");
									System.out.println("Example: {c:RED}");
									break;
								}
								String color = ColorHelper.getColorByName(value);
								if (color == null) {
									System.out.println("Color " + color + " wasnt found. Maybe a Typo?");
									break;
								}
								this.color = color;
							}
							
							//Color Reset
							if (token.equals(RESETCOLOR)) {
								this.color = defaultTextColor;
							}
						}
					} else if (c2 != '}') {
						nextWordOnlyChars += c2;
						nextWord += color + c2;
					}
				}
			}
		}
		if (!page.addLine(currLine)) {
			parent.addTextPage(page);
			page = new GuideTextPage(lines);
			page.addLine(currLine);
		}
		parent.addTextPage(page);
		color = defaultTextColor;
	}
	
	public static String getFormattingToken(String s) {
		int i = s.indexOf(':');
		String token = "";
		if (i != -1) {
			token = s.substring(0, i);
		} else {
			token = s;
		}
		return token;
	}
	
	public static String getFormattingValue(String s) {
		int i = s.indexOf(':');
		if (i != -1) {
			return s.substring(i + 1);
		} else {
			return null;
		}
	}
	
	public static String removeFormatting(String s) {
		String result = "";
		boolean flag = true;
		for (char c : s.toCharArray()) {
			if (c == '{') flag = false;
			if (c == '}') flag = true;
			if (flag) result += c;
		}
		return result;
	}
	
	//Setter
	public void setDefaultTextColor(String defaultTextColor) {
		if (ColorHelper.getColorByName(defaultTextColor) != null) {
			this.defaultTextColor = ColorHelper.getColorByName(defaultTextColor);
			this.color = defaultTextColor;
		} else {
			System.out.println("Invalid Color: " + defaultTextColor);
		}
	}
	
	public void setColor(String color) {
		if (ColorHelper.getColorByName(defaultTextColor) != null) {
			this.color = ColorHelper.getColorByName(color);
		} else {
			System.out.println("Invalid Color: " + defaultTextColor);
		}
	}
	
	public void setLineSpacing(int lineSpacing) {
		this.lineSpacing = lineSpacing;
	}
	
	//Getter
	public String getDefaultTextColor() {
		return defaultTextColor;
	}
	
	public GuideArticle getParent() {
		return parent;
	}
	
	public int getLineSpacing() {
		return lineSpacing;
	}
	
}
