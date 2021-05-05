package Game;

public class Player {
	private String name;
	private Boolean isPlayingWhite;
	private Boolean hasLoosed = false;
	
	public Player (String name, Boolean state) {
		this.name = name;
		isPlayingWhite = state;
	}
	
	public String getName() {
		return name;
	}
	
	public Boolean getIsPlayingWhite () {
		return isPlayingWhite;
	}
	
	public Boolean getHasLoosed() {
		return hasLoosed;
	}
}
