import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Game {
	ArrayList<Integer> doors;
	ArrayList<Integer> treasures;
	

	public Game(ArrayList<Integer> doors, ArrayList<Integer> treasures) {
 		this.doors = doors;
		this.treasures = treasures;
	
		
	}

	public ArrayList<Integer> shuffle(ArrayList<Integer> cards) {
		Random rand = new Random();
		for (int i = cards.size() - 1; i > 0; i--) {
			int randNum = rand.nextInt(i);
			int temp = cards.get(randNum);
			cards.set(randNum, cards.get(i));
			cards.set(i, temp);
		}
		return cards;
	}
	
	public ArrayList<Integer> dealCards() {
		ArrayList<Integer> deal = new ArrayList<Integer>();
		shuffle(doors);
		shuffle(treasures);
		for (int i = 0; i <= 3; i++) {
			deal.add(doors.get(doors.size() - 1));
			deal.add(treasures.get(treasures.size()-1));
			doors.remove(doors.get(doors.size() - 1));
			treasures.remove(treasures.get(treasures.size()-1));
		}
		return deal;
	}
	
	

}