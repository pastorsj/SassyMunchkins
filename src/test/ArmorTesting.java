package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import munchkin.cards.treasures.BowlingPin;
import munchkin.cards.treasures.BrassKnucks;
import munchkin.cards.treasures.MinersHelmet;
import munchkin.cards.treasures.MiskatonicUniversityTShirt;
import munchkin.cards.treasures.MonsterStompers;
import munchkin.cards.treasures.api.ArmorSet;
import munchkin.cards.treasures.api.ITreasure;
import munchkin.game.Action;
import munchkin.game.Game;

public class ArmorTesting {

	private Action action;
	private ArmorSet armor;
	private Game game;
	private ITreasure helmet;
	
	@Before
	public void setUp(){
		action = Action.getInstance();
		armor = new ArmorSet();
		game = new Game(2);

		helmet = new MinersHelmet();
		helmet.cardInPlay();
	}
	
	@Test
	public void testAddFootGear() {
		armor.removeFootgear();
		
		ITreasure boots = new MonsterStompers(game);
		armor.addFootGear(helmet);
		assertEquals("Not Footgear armor",action.getAction());
		assertEquals(0, armor.getFootgear().size());
		
		boots.cardInPlay();
		armor.addFootGear(boots);
		assertEquals(1, armor.getFootgear().size());
		assertEquals(boots, armor.getFootgear().get(0));
		
		armor.addFootGear(boots);
		assertEquals("Already at max armor for footgear", action.getAction());
		assertEquals(1, armor.getFootgear().size());
		
		armor.removeFootgear();
		armor.addFootGear(boots);
		assertEquals(1, armor.getFootgear().size());
		assertEquals(boots, armor.getFootgear().get(0));
	}
	
	@Test
	public void testAddHeadGear(){
		armor.removeHeadgear();
		
		ITreasure notHeadGear = new MonsterStompers(game);
		notHeadGear.cardInPlay();
		
		armor.addHeadGear(notHeadGear);
		assertEquals("Not HeadGear armor",action.getAction());
		assertEquals(0, armor.getHeadgear().size());
		
		armor.addHeadGear(helmet);
		assertEquals(1, armor.getHeadgear().size());
		assertEquals(helmet, armor.getHeadgear().get(0));
		
		armor.addHeadGear(helmet);
		assertEquals("Already at max armor for headgear", action.getAction());
		assertEquals(1, armor.getHeadgear().size());
		
		armor.removeHeadgear();
		armor.addHeadGear(helmet);
		assertEquals(1, armor.getHeadgear().size());
		assertEquals(helmet, armor.getHeadgear().get(0));
	}
	
	@Test
	public void testAddArmor(){
		
		ITreasure chest = new MiskatonicUniversityTShirt(game);
		
		armor.addArmor(helmet);
		assertEquals("Not armor type",action.getAction());
		assertEquals(0, armor.getArmor().size());
		
		chest.cardInPlay();
		armor.addArmor(chest);
		assertEquals(1, armor.getArmor().size());
		assertEquals(chest, armor.getArmor().get(0));
		
		armor.addArmor(chest);
		assertEquals("Already at max armor for armor", action.getAction());
		assertEquals(1, armor.getArmor().size());
		
		armor.removeArmor();
		armor.addArmor(chest);
		assertEquals(1, armor.getArmor().size());
		assertEquals(chest, armor.getArmor().get(0));
		
	}
	
	@Test
	public void testAddHands(){
		ITreasure twoHand = new BrassKnucks();
		ITreasure oneHand = new BowlingPin();
		
		armor.addHands(helmet);
		assertEquals("Not hands armor",action.getAction());
		assertEquals(0, armor.getArmor().size());
		
		twoHand.cardInPlay();
		armor.addHands(twoHand);
		assertEquals(1, armor.getHands().size());
		assertEquals(twoHand, armor.getHands().get(0));
		
		armor.addHands(twoHand);
		assertEquals("Either you are at max armor for hand gear or you are attempting to add a piece of two handed armor to an already equipped one handed piece", action.getAction());
		assertEquals(1, armor.getHands().size());
		
		armor.removeHands();
		
		oneHand.cardInPlay();
		armor.addHands(oneHand);
		assertEquals(1, armor.getHands().size());
		assertEquals(oneHand, armor.getHands().get(0));
		
		armor.addHands(twoHand);
		assertEquals("Either you are at max armor for hand gear or you are attempting to add a piece of two handed armor to an already equipped one handed piece", action.getAction());
		assertEquals(1, armor.getHands().size());
		
		armor.addHands(oneHand);
		assertEquals(2, armor.getHands().size());
		assertEquals(oneHand, armor.getHands().get(1));
		
		armor.addHands(oneHand);
		assertEquals(2, armor.getHands().size());
		assertEquals("Already at max armor for hands",action.getAction());
	}
}