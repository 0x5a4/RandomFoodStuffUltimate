package shit.randomfoodstuff.cooking;

public class Reaction {
	
	protected String spice;
	protected String reagent;
	protected String resultEffect;
	
	public Reaction(String spice, String reagent, String resultEffect) {
		this.spice = spice;
		this.reagent = reagent;
		this.resultEffect = resultEffect;
	}
	
	public boolean canTrigger(String spice, String reagent) {
		return spice.equals(this.spice) && reagent.equals(this.reagent);
	}

	public String getResultEffect() {
		return resultEffect;
	}
	
}
