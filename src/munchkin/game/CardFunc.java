package munchkin.game;

import java.util.ArrayList;

public class CardFunc {
	public Game myGame;
	public Player p;

	public CardFunc(Game game) {
		this.myGame = game;
		if (myGame.turnPlayer == 1) {
			p = myGame.p1;
		} else {
			p = myGame.p2;
		}

	}

	public boolean checkHands(int numHands) {
		return (myGame.currentPlayer.handLevel + numHands) <= 2;
	}

	public boolean checkArmor(int numArmor) {
		return (myGame.currentPlayer.armorLevel + numArmor) <= 1;
	}

	public boolean checkHeadGear(int numHead) {
		return (myGame.currentPlayer.headLevel + numHead) <= 1;
	}

	public boolean checkFootLevel(int numFoot) {
		return (myGame.currentPlayer.footLevel + numFoot) <= 1;
	}

	public void func1Init() {
		if (!myGame.monster) {
			cantPlay();
		} else {
			myGame.mLevel += 5;
		}

	}

	public void func1(boolean checkWin) {
		// +5 to Monster
		// If win, draw extra treasure
		if (checkWin) {
			myGame.currentPlayer.treasuresWonEachTurn += 1;
			// getTreasFromWin();

		}
	}

	public void func2Init() {
		// +3 against Monster Whackers
		if ((myGame.monster && !myGame.currentPlayer.pPlay.contains(84)) || myGame.currentPlayer.drewCard) {
			cantPlay();
		} else {
			myGame.monster = true;
			myGame.currentPlayer.monster = true;
			if (myGame.currentPlayer.className.equals("Monster Whacker")) {
				myGame.currentPlayer.cLevel -= 3;
				// myGame.mframe.mainPanel.bCardPanel.playerCLevel.setText("player
				// clevel: "+myGame.currentPlayer.cLevel);
			}
			myGame.mLevel += 2;

		}

	}

	public void func2(boolean checkWin) {
		// Bad: Lose your class card except Cultist
		if (checkWin) {
			myGame.currentPlayer.treasuresWonEachTurn += 1;
		} else {
			if (!myGame.currentPlayer.className.equals("Cultist")) {
				myGame.currentPlayer.className = "";
				// Need to discard any class card in play
			}
		}
	}

	public void func3Init() {
		// +3 to monster
		// If played on a Cultist, it gives them a permanent bonus of +3
		// until the class card is lost or the player dies
		if (!myGame.monster) {
			cantPlay();
		} else {
			myGame.mLevel += 3;
		}

	}

	public void func4Init() {
		// No class abilities can be used
		if ((myGame.monster && !myGame.currentPlayer.pPlay.contains(84)) || myGame.currentPlayer.drewCard) {
			cantPlay();
		} else {
			myGame.monster = true;
			myGame.currentPlayer.monster = true;
			myGame.mLevel += 1;
		}

	}

	public void func4(boolean checkWin) {
		// Gain 1 treasure
		// Bad: Lose your armor

		if (checkWin) {
			myGame.currentPlayer.treasuresWonEachTurn += 1;
			// getTreasFromWin();
		} else {
			for (int i = 0; i < myGame.currentPlayer.pHand.size(); i++) {
				// if((myGame.ic.getCardHash().get(myGame.currentPlayer.pHand.get(i)).numGold>0)||
				// (myGame.ic.getCardHash().get(myGame.currentPlayer.pHand.get(i)).headGear)||
				// (myGame.ic.getCardHash().get(myGame.currentPlayer.pHand.get(i)).footGear)){
				// myGame.currentPlayer.pHand.remove(i);
				// i--;
				if (myGame.ic.getCardHash().get(myGame.currentPlayer.pHand.get(i)).armor) {
					myGame.currentPlayer.pHand.remove(i);
					i--;
				}
			}
		}
	}

	public void func5Init() {
		// -2 against Professors
		// +2 against Cultists
		if ((myGame.monster && !myGame.currentPlayer.pPlay.contains(84)) || myGame.currentPlayer.drewCard) {
			cantPlay();
		} else {
			myGame.monster = true;
			myGame.currentPlayer.monster = true;
			if (myGame.currentPlayer.className.equals("Professor")) {
				myGame.currentPlayer.cLevel -= 2;
			}
			myGame.mLevel += 6;
		}

	}

	public void func5(boolean checkWin) {
		if (checkWin) {
			myGame.currentPlayer.treasuresWonEachTurn += 2;
		} else {
			myGame.currentPlayer.winStatus = -1;
			myGame.mframe.mainPanel.bCardPanel.etb.setVisible(false);
			myGame.mframe.mainPanel.bCardPanel.diwb.setVisible(false);
			myGame.mframe.mainPanel.bCardPanel.dgb.setVisible(false);
			myGame.mframe.mainPanel.bCardPanel.sgb.setVisible(false);
			myGame.mframe.mainPanel.bCardPanel.pcb.setVisible(false);
			myGame.mframe.revalidate();
			myGame.mframe.repaint();
			// need cultist/you lose screen!
		}
		// Gain 2 treasures
		// Bad: Become a cultist
		// If already a cultist, donate 1000 gold pieces
	}

	public void func6() {
		// Cheat card, probably should not implement...
	}

	public void func7Init() {
		// -4 against females
		if ((myGame.monster && !myGame.currentPlayer.pPlay.contains(84)) || myGame.currentPlayer.drewCard) {
			cantPlay();
		} else {
			myGame.monster = true;
			myGame.currentPlayer.monster = true;
			myGame.mLevel += 2;
			if (myGame.currentPlayer.gender == 'F') {
				myGame.mLevel += 4;
			}
		}

	}

	public void func7(boolean checkWin) {
		// Draw one treasure
		// Bad: Cultists discard their best card
		// Others must discard a card
		if (checkWin) {
			myGame.currentPlayer.treasuresWonEachTurn += 1;
		} else {
			// Discard a card in your hand
			// myGame.discardCard(myGame.currentPlayer,
			// myGame.currentPlayer.pHand.get(0));
			myGame.mframe.mainPanel.bCardPanel.add(myGame.mframe.mainPanel.bCardPanel.db);
			myGame.mframe.mainPanel.bCardPanel.etb.setVisible(false);
			myGame.mframe.mainPanel.bCardPanel.db.setVisible(true);
			myGame.mframe.revalidate();
			myGame.mframe.repaint();
			deleteWhenDiscard();
		}
	}

	public void func8Init() {
		// Monster gets +3 if size of hand is even
		if ((myGame.monster && !myGame.currentPlayer.pPlay.contains(84))
				|| myGame.currentPlayer.drewCard && myGame.currentPlayer.drewCard) {
			cantPlay();
		} else {
			myGame.monster = true;
			myGame.currentPlayer.monster = true;
			myGame.mLevel += 8;
			if (myGame.currentPlayer.pHand.size() % 2 == 0) {
				myGame.mLevel += 3;
			}
		}

	}

	public void func8(boolean checkWin) {
		// Draw two treasures
		// Bad: Lose two levels and discard a card from your hand
		if (checkWin) {
			myGame.currentPlayer.treasuresWonEachTurn += 2;
		} else {
			myGame.currentPlayer.pLevel -= 2;
			if (myGame.currentPlayer.pLevel < 1) {// CHANGE
				myGame.currentPlayer.pLevel = 1;
			}

			myGame.mframe.mainPanel.bCardPanel.etb.setVisible(false);
			myGame.mframe.mainPanel.bCardPanel.db.setVisible(true);
			myGame.mframe.revalidate();
			myGame.mframe.repaint();
		}
	}

	public void func9() {
		// Can play on someone who goes up a level without killing a monster
		// Player can not get help until they kill a monster or use Wishing Ring

	}

	public void func10Init() {
		// Can have an extra set of hand armor
		myGame.currentPlayer.handLevel--;
	}

	public void func11Init() {
		// Cult meeting
		// Cultists can exchange cards
		// Any non cultist with the cultist card can join
	}

	public void func12Init() {
		myGame.currentPlayer.className = "Cultist";
	}

	public void func12() {
		// The player gets a bonus of +2 for every cultist in the game
		// Can not voluntarily stop being a cultist
	}

	public void func13Init() {
		// Curse
		// Discard your class
		// If you don't have a class, lose a level

		if (myGame.mLevel > 0 || myGame.currentPlayer.drewCard) {
			cantPlay();
		} else {
			if (!myGame.currentPlayer.className.equals("")) {
				if (myGame.currentPlayer.className.equals("Monster Whacker")) {
					// Discard card 49
					myGame.discardCard(myGame.currentPlayer, 49);
					myGame.currentPlayer.className = "";
				} else if (myGame.currentPlayer.className.equals("Professor")) {
					// Discard card 57
					myGame.discardCard(myGame.currentPlayer, 57);
					myGame.currentPlayer.className = "";
				} else if (myGame.currentPlayer.className.equals("Investigator")) {
					// Discard card 44
					myGame.discardCard(myGame.currentPlayer, 44);
					myGame.currentPlayer.className = "";
				} else if (myGame.currentPlayer.className.equals("Cultist")) {
					// Discard card 12
					myGame.discardCard(myGame.currentPlayer, 12);
					myGame.currentPlayer.className = "";
				}
			} else {
				myGame.currentPlayer.pLevel -= 1;
			}
		}

	}

	public void func14Init() {
		// Curse
		// Discard all extra cards in your hand
		if (myGame.mLevel > 0 || myGame.currentPlayer.drewCard) {
			cantPlay();
		} else {
			if (myGame.currentPlayer.pHand.size() > 8) {
				int card = myGame.currentPlayer.pHand.get(myGame.currentPlayer.pHand.size() - 1);
				myGame.discardCard(myGame.currentPlayer, card);
				func14Init();
			}
		}

	}

	public void func15Init() {
		// Curse
		// Change sex
		// -5 to your next combat
		if (myGame.mLevel > 0 || myGame.currentPlayer.drewCard) {
			cantPlay();
		} else {
			if (myGame.currentPlayer.gender == 'F') {
				myGame.currentPlayer.gender = 'M';
			} else {
				myGame.currentPlayer.gender = 'F';
			}
			myGame.currentPlayer.cLevel -= 5; // this will delete immediately if
												// don't play monster now

		}

	}

	public void func16() {
		// If you are a cultist, discard your class and pick up a new class from
		// the discard pile
		/// If you are not a cultist, become a cultist
	}

	public void func17Init() {
		// Curse
		// -3 on your next combat
		// If you have miners helmet or two handed flashlight, discard them

		if (myGame.mLevel > 0 || myGame.currentPlayer.drewCard) {
			cantPlay();
		} else {
			myGame.currentPlayer.cLevel -= 3;
			if (myGame.currentPlayer.pHand.contains(111)) {
				myGame.discardCard(myGame.currentPlayer, 111);
			}
			if (myGame.currentPlayer.pHand.contains(146)) {
				myGame.discardCard(myGame.currentPlayer, 146);
			}
		}

	}

	public void func18() {
		// Curse: Need to discard all armor in play
	}

	public void func19() {
		// Curse: Need to discard all headgear in play
	}

	public void func20Init() {
		// Curse: Discard all class cards except Cultist

		if (myGame.mLevel > 0 || myGame.currentPlayer.drewCard) {
			cantPlay();
		}

		else {
			myGame.changePlayer();
			myGame.mframe.revalidate();
			myGame.mframe.repaint();
			myGame.mframe.mainPanel.bCardPanel.etb.setVisible(true);
			myGame.mframe.mainPanel.bCardPanel.pcb.setVisible(false);
			myGame.mframe.mainPanel.bCardPanel.sgb.setVisible(false);
			myGame.mframe.mainPanel.bCardPanel.diwb.setVisible(false);

			if (myGame.currentPlayer.pHand.contains(49)) {
				// Discard card 49
				myGame.discardCard(myGame.currentPlayer, 49);
			}
			if (myGame.currentPlayer.pHand.contains(57)) {
				// Discard card 57
				myGame.discardCard(myGame.currentPlayer, 57);
			}
			if (myGame.currentPlayer.pHand.contains(44)) {
				// Discard card 44
				myGame.discardCard(myGame.currentPlayer, 44);
			}
			if (myGame.currentPlayer.pPlay.contains(49)) {
				// Discard card 49
				myGame.discardCard(myGame.currentPlayer, 49);
				myGame.currentPlayer.className = "";
			}
			if (myGame.currentPlayer.pHand.contains(57)) {
				// Discard card 57
				myGame.discardCard(myGame.currentPlayer, 57);
				myGame.currentPlayer.className = "";
			}
			if (myGame.currentPlayer.pHand.contains(44)) {
				// Discard card 44
				myGame.discardCard(myGame.currentPlayer, 44);
				myGame.currentPlayer.className = "";
			}
		}

	}

	public void func21() {
		// Other player choose an item that you must discard
		// that is already in play
	}

	public void func22() {
		// Curse: Lose a level
		if (myGame.mLevel > 0 || myGame.currentPlayer.drewCard) {
			cantPlay();
		} else {
			myGame.currentPlayer.pLevel -= 1;
		}

	}

	public void func23Init() {
		// Curse: Must discard at least 1000 gold pieces

		if (myGame.mLevel > 0 || myGame.currentPlayer.drewCard) {
			cantPlay();

		} else {
			myGame.currentPlayer.sentCurse = true;
			myGame.changePlayer();
			myGame.mframe.revalidate();
			myGame.mframe.repaint();
			myGame.mframe.mainPanel.bCardPanel.etb.setVisible(false);
			myGame.mframe.mainPanel.bCardPanel.pcb.setVisible(false);
			myGame.mframe.mainPanel.bCardPanel.sgb.setVisible(false);
			myGame.mframe.mainPanel.bCardPanel.diwb.setVisible(false);
			myGame.mframe.mainPanel.bCardPanel.dgb.goldToDiscard = 1000;
			myGame.mframe.mainPanel.bCardPanel.dgb.setVisible(true);
		}

	}

	public void func23(boolean checkWin) {

	}

	public void func24() {
		// Curse: Must discard your footgear...
	}

	public void func25() {
		// Another curse, need algorithm...
	}

	public void func26Init() {
		// If Ichor cards are in play that give them combat bonus, double the
		// effect.
		// Perhaps Cards No: 100, 132
		myGame.changePlayer();
		if (myGame.currentPlayer.pPlay.contains(100)) {
			myGame.changePlayer();
			myGame.currentPlayer.cLevel -= 6;
		}
		if (myGame.currentPlayer.pPlay.contains(132)) {
			myGame.changePlayer();
			myGame.currentPlayer.cLevel -= 4;
		}
	}

	public void func26(boolean checkWin) {
		if (checkWin) {
			myGame.currentPlayer.treasuresWonEachTurn += 3;
		} else {
			// Right now, we will take the last card from the current player and
			// give it to the other player
			int card = myGame.currentPlayer.pHand.get(myGame.currentPlayer.pHand.size() - 1);
			myGame.currentPlayer.pHand.remove(myGame.currentPlayer.pHand.indexOf(card));
			myGame.changePlayer();
			myGame.currentPlayer.pHand.add(card);
			myGame.changePlayer();
		}
	}

	public void func27Init() {
		if ((myGame.monster && !myGame.currentPlayer.pPlay.contains(84)) || myGame.currentPlayer.drewCard) {
			cantPlay();
		} else {
			myGame.monster = true;
			myGame.currentPlayer.monster = true;
			if (myGame.currentPlayer.className.equals("Professor")) {
				myGame.currentPlayer.cLevel -= 2;
			}
			myGame.mLevel += 1;
		}
	}

	public void func27(boolean checkWin) {
		if (checkWin) {
			myGame.currentPlayer.treasuresWonEachTurn += 1;
		} else {
			// Discard cards depending on the user, must add up to 500 gold.

			// myGame.mframe.mainPanel.bCardPanel.removeETB();
			// myGame.mframe.dispose();

			// myGame.mframe=new MFrame(myGame);
			// myGame.mframe.mainPanel.bCardPanel.etb.setVisible(false);
			myGame.mframe.mainPanel.bCardPanel.etb.setVisible(false);
			myGame.mframe.mainPanel.bCardPanel.dgb.goldToDiscard = 500;
			myGame.mframe.mainPanel.bCardPanel.dgb.setVisible(true);
			myGame.mframe.mainPanel.bCardPanel.db.setVisible(false);

			myGame.mframe.revalidate();
			myGame.mframe.repaint();

			// myGame.mframe = new MFrame(myGame);
			// myGame.mframe.repaint();
			// myGame.mframe.mainPanel.bCardPanel.revalidate();
			// DiscardButton db = new DiscardButton(myGame);
			// db.setVisible(true);
			// myGame.mframe.mainPanel.bCardPanel.add(db);

		}
	}

	public void discardGold() {
		myGame.mframe.mainPanel.bCardPanel.dgb.goldLeft = 0;
		System.out.println("gold you need to discount total: " + myGame.mframe.mainPanel.bCardPanel.dgb.goldToDiscard);
		for (int i = 0; i < myGame.currentPlayer.pHand.size(); i++) {
			myGame.mframe.mainPanel.bCardPanel.dgb.goldLeft += myGame.ic.getCardHash()
					.get(myGame.currentPlayer.pHand.get(i)).numGold;
		}
		if (myGame.mframe.mainPanel.bCardPanel.dgb.goldCount >= myGame.mframe.mainPanel.bCardPanel.dgb.goldToDiscard
				|| myGame.mframe.mainPanel.bCardPanel.dgb.goldLeft == 0) {
			myGame.mframe.mainPanel.bCardPanel.etb.setVisible(true);
			myGame.mframe.mainPanel.bCardPanel.dgb.setVisible(false);
			myGame.mframe.revalidate();
			myGame.mframe.repaint();
		}

	}

	public void deleteWhenDiscard() {

		if (myGame.mframe.mainPanel.bCardPanel.db.numDiscarded > 0 || myGame.currentPlayer.pHand.size() == 0) {
			myGame.mframe.mainPanel.bCardPanel.etb.setVisible(true);
			myGame.mframe.mainPanel.bCardPanel.db.setVisible(false);
			myGame.mframe.revalidate();
			myGame.mframe.repaint();
		}
	}

	public void func28Init() {
		myGame.currentPlayer.cLevel = myGame.currentPlayer.pLevel;
		// Will add something like this once the rest of the cards are
		// implemented in
		// if(myGame.currentPlayer.pPlay.contains(85)) {
		// myGame.funcs.func85Init();
		// }
		// if(myGame.currentPlayer.pPlay.contains(100)) {
		// myGame.funcs.func100Init();
		// }
		// if(myGame.currentPlayer.pPlay.contains(101)) {
		// myGame.funcs.func101Init();
		// }
		// if(myGame.currentPlayer.pPlay.contains(105)) {
		// myGame.funcs.func105Init();
		// }
		// if(myGame.currentPlayer.pPlay.contains(127)) {
		// myGame.funcs.func127Init();
		// }
		// if(myGame.currentPlayer.pPlay.contains(85)) {
		// myGame.funcs.func132Init();
		// }
		// if(myGame.currentPlayer.pPlay.contains(85)) {
		// myGame.funcs.func137Init();
		// }
		// if(myGame.currentPlayer.pPlay.contains(85)) {
		// myGame.funcs.func137Init();
		// }
		// if(myGame.currentPlayer.pPlay.contains(142)) {
		// myGame.funcs.func142Init();
		// }
	}

	public void func28(boolean checkWin) {
		if (checkWin) {
			myGame.currentPlayer.treasuresWonEachTurn += 2;
		} else {
			// Give a card to the player on either side of you
			int card = myGame.currentPlayer.pHand.get(myGame.currentPlayer.pHand.size() - 1);
			myGame.currentPlayer.pHand.remove(myGame.currentPlayer.pHand.size() - 1);
			myGame.changePlayer();
			myGame.currentPlayer.pHand.add(card);
			myGame.changePlayer();
		}
	}

	public void func29Init() {
		// + 10 to the monster
		myGame.mLevel += 10;
		// myGame.mframe.mainPanel.bCardPanel.monsterLevel.setText("monster
		// level: "+myGame.mLevel);
		myGame.otherPlayer.treasuresWonEachTurn += 2;
	}

	public void func29(boolean checkWin) {
		// If win, draw two extra treasures
		if (checkWin) {
			// myGame.currentPlayer.treasuresWonEachTurn += 2;
		}
	}

	public void func30Init() {
		// -2 against Monster Whacker's
		if ((myGame.monster && !myGame.currentPlayer.pPlay.contains(84)) || myGame.currentPlayer.drewCard) {
			cantPlay();
		} else {
			myGame.monster = true;
			myGame.currentPlayer.monster = true;
			myGame.mLevel += 12;
			if (myGame.currentPlayer.className.equals("Monster Whacker")) {
				myGame.currentPlayer.cLevel += 2;
			}

		}

	}

	public void func30(boolean checkWin) {
		// Draw 3 treasures
		// Bad: You are dead
		if (checkWin) {
			myGame.currentPlayer.treasuresWonEachTurn += 3;
		} else {
			myGame.currentPlayer.winStatus = -1;
			myGame.mframe.mainPanel.bCardPanel.etb.setVisible(false);
			myGame.mframe.mainPanel.bCardPanel.diwb.setVisible(false);
			myGame.mframe.mainPanel.bCardPanel.dgb.setVisible(false);
			myGame.mframe.mainPanel.bCardPanel.sgb.setVisible(false);
			myGame.mframe.mainPanel.bCardPanel.pcb.setVisible(false);
			myGame.mframe.revalidate();
			myGame.mframe.repaint();
		}
	}

	public void func31Init() {
		// +3 to monster
		// or play on cultist until they die or they lose the class
		// +3 permanant bonus
		// same as func3()
		func3Init();
	}

	public void func32Init() {
		// Must discard card in your hand
		// Right now, it will delete the last card in your hand
		// NEED TO CHANGE
		myGame.currentPlayer.pHand.remove(myGame.currentPlayer.pHand.size() - 1);
	}

	public void func32(boolean checkWin) {
		// Draw three treasures
		// Discard all of your ichor cards
		if (checkWin) {
			myGame.currentPlayer.treasuresWonEachTurn += 3;
		} else {
			// Need to discard all Ichor cards
			if (myGame.currentPlayer.pHand.contains(85)) {
				myGame.discardCard(myGame.currentPlayer, 85);
			}
			if (myGame.currentPlayer.pHand.contains(100)) {
				myGame.discardCard(myGame.currentPlayer, 100);
			}
			if (myGame.currentPlayer.pHand.contains(101)) {
				myGame.discardCard(myGame.currentPlayer, 101);
			}
			if (myGame.currentPlayer.pHand.contains(105)) {
				myGame.discardCard(myGame.currentPlayer, 105);
			}
			if (myGame.currentPlayer.pHand.contains(127)) {
				myGame.discardCard(myGame.currentPlayer, 127);
			}
			if (myGame.currentPlayer.pHand.contains(132)) {
				myGame.discardCard(myGame.currentPlayer, 132);
			}
			if (myGame.currentPlayer.pHand.contains(137)) {
				myGame.discardCard(myGame.currentPlayer, 137);
			}
			if (myGame.currentPlayer.pHand.contains(142)) {
				myGame.discardCard(myGame.currentPlayer, 142);
			}
		}
	}

	public void func33Init() {
		// -2 against females
		if (myGame.currentPlayer.gender == 'F') {
			myGame.currentPlayer.cLevel += 2;
		}
	}

	public void func33(boolean checkWin) {
		// Draw 2 treasures
		// Bad: Can't help anyone out until you go up a level
		if (checkWin) {
			myGame.currentPlayer.treasuresWonEachTurn += 2;
		} else {
			// Can't help others until you go up a level...
		}
	}

	public void func34() {
		// Same as func3()
	}

	public void func35Init() {
		// -2 against professors and investigators
		if (myGame.currentPlayer.className.equals("Professor")) {
			myGame.currentPlayer.cLevel += 2;
		}
		if (myGame.currentPlayer.className.equals("Investigator")) {
			myGame.currentPlayer.cLevel += 2;
		}
	}

	public void func35(boolean checkWin) {
		// Draw 4 treasures
		// Bad: Until your next turn, you help the other person in combat
		if (checkWin) {
			myGame.currentPlayer.treasuresWonEachTurn += 4;
		} else {
			// You must help the other person in combat
		}
	}

	public void func36Init() {
		// Male munchkins cannot help...
		// +3 against males
		if (myGame.currentPlayer.gender == 'M') {
			myGame.currentPlayer.cLevel -= 3;
		}
	}

	public void func36(boolean checkWin) {
		// Draw 1 treasure
		// Bad: Lose a level
		if (checkWin) {
			myGame.currentPlayer.treasuresWonEachTurn += 1;
		} else {
			myGame.currentPlayer.pLevel -= 1;
		}
	}

	public void func37Init() {
		// -2 against females
		if (myGame.currentPlayer.gender == 'F') {
			myGame.currentPlayer.cLevel += 2;
		}
	}

	public void func37(boolean checkWin) {
		// Draw 3 treasures
		// Bad: Discard entire hand
		if (checkWin) {
			myGame.currentPlayer.treasuresWonEachTurn += 3;
		} else {
			myGame.currentPlayer.pHand = new ArrayList<Integer>();// CHANGE
		}
	}

	public void func38Init() {
		// +4 to any non cultists
		if (!myGame.currentPlayer.className.equals("Cultist")) {
			myGame.currentPlayer.cLevel -= 4;
		}
	}

	public void func38(boolean checkWin) {
		// Draw 5 treasures and go up two levels
		// Bad: Anyone who helped goes up a level
		// Your new character is a cultist
		if (checkWin) {
			myGame.currentPlayer.treasuresWonEachTurn += 5;
			myGame.currentPlayer.pLevel += 2;
		} else {
			myGame.currentPlayer.className = "Cultist";
			myGame.currentPlayer.pHand = new ArrayList<Integer>();
		}
	}

	public void func39Init() {
		// +3 against Investigators (Check the card)
		// Automatically become a cultist
		if (myGame.currentPlayer.className.equals("Investigator")) {
			myGame.currentPlayer.cLevel -= 3;
		}
		myGame.currentPlayer.className = "Cultist";
	}

	public void func39(boolean checkWin) {
		// Draw two treasures
		// Bad: You are still a cultist
		if (checkWin) {
			myGame.currentPlayer.treasuresWonEachTurn += 2;
		}
	}

	public void func40() {
		// Play this when you draw a curse
		// Will play it on another player of your choice
	}

	public void func41() {
		// Can grab the top card off the discard pile and play it if you want to
	}

	public void func42Init() {
		// -5 to the monster
		myGame.mLevel -= 5;
		myGame.currentPlayer.treasuresWonEachTurn -= 1;
		// myGame.mframe.mainPanel.bCardPanel.monsterLevel.setText("monster
		// level: " +myGame.mLevel);
	}

	public void func42(boolean checkWin) {
		// Draw one fewer treasure if you win
		if (checkWin) {
			if (myGame.currentPlayer.treasuresWonEachTurn < 1) {
				myGame.currentPlayer.treasuresWonEachTurn = 0;
			}
		}
	}

	public void func43Init() {
		// +10 to the monster
		myGame.currentPlayer.cLevel -= 10;
	}

	public void func43(boolean checkWin) {
		// Draw two extra treasures if you win
		if (checkWin) {
			myGame.currentPlayer.treasuresWonEachTurn += 2;
		}
	}

	public void func44Init() {
		// Class: Investigator
		myGame.currentPlayer.className = "Investigator";
	}

	public void func44(boolean checkWin) {
		// Need to add class abilities...
		// Look secretly at your first door card draw each turn
		// Can either take that door and discard three cards or draw the next
		// door
		// If win, draw an extra treasure
	}

	public void func45(boolean checkWin) {
		// -2 to run away
		// Draw one treasure
		// Bad: Discard one card chosen by the player with the lowest level
		if (checkWin) {
			myGame.currentPlayer.treasuresWonEachTurn += 1;
		}
	}

	public void func46Init() {
		// -2 against Cultists
		if (myGame.currentPlayer.className.equals("Cultist")) {
			myGame.currentPlayer.cLevel += 2;
		}
	}

	public void func46(boolean checkWin) {
		// Draw two treasures
		// Bad: Lose two levels
		if (checkWin) {
			myGame.currentPlayer.treasuresWonEachTurn += 2;
		} else {
			myGame.currentPlayer.pLevel -= 2;
		}
	}

	public void func47(boolean checkWin) {
		// Draw 2 treasures
		// Bad: Anyone who helped lose a level
		if (checkWin) {
			myGame.currentPlayer.treasuresWonEachTurn += 1;
		} else {
			// Anyone who helps loses a level
			// You don't lose anything if you lose
		}
	}

	public void func48Init() {
		func1Init();
	}

	public void func48(boolean checkWin) {
		func1(checkWin);
	}

	public void func49Init() {
		// Class: Monster Whacker
		myGame.currentPlayer.className = "Monster Whacker";
	}

	public void func49() {
		// Need to add class abilities
		// -2 against any monsters of level 10 and below
		// -2 against monster of level 16 and above
	}

	public void func50() {
		// Take a monster out of combat, put it in your hand,
		// and replace it with one from your hand
	}

	public void func51Init() {
		// +3 against males
		if (myGame.currentPlayer.gender == 'M') {
			myGame.currentPlayer.cLevel -= 3;
		}
	}

	public void func51(boolean checkWin) {
		// Draw 4 treasures
		// Discard 3 items NEED TO DO
		if (checkWin) {
			myGame.currentPlayer.treasuresWonEachTurn += 4;
		}
	}

	public void func52Init() {
		// If you are the lowest level, they get +4 on you
		if (myGame.turnPlayer == 1) {
			if (myGame.p1.pLevel < myGame.p2.pLevel) {
				myGame.currentPlayer.cLevel -= 4;
			}
		} else {
			if (myGame.p2.pLevel < myGame.p1.pLevel) {
				myGame.currentPlayer.cLevel -= 4;
			}
		}
	}

	public void func52(boolean checkWin) {
		// Draw two treasures
		// Bad: All other players will take one card from you
		if (checkWin) {
			myGame.currentPlayer.treasuresWonEachTurn += 2;
		} else {
			// Other players takes one of your cards in play
		}
	}

	public void func53() {
		// Play this card to cancel any effect of a just drawn or just played
		// card
		// If drawn, a replacement is drawn
	}

	public void func54Init() {
		// Does not pursue of Level 4 or below (No roll dice)
		// Defeated by Song of Madness, but you become a Cultist
	}

	public void func54(boolean checkWin) {
		// Draw 5 treasures and 2 levels
		// Bad: You are dead
		if (checkWin) {
			myGame.currentPlayer.treasuresWonEachTurn += 6;
			myGame.currentPlayer.pLevel += 2;
		} else {
			// You die, need to call the player lose for this person.
		}
	}

	public void func55Init() {
		// +3 against professors
		if (myGame.currentPlayer.className.equals("Professor")) {
			myGame.currentPlayer.cLevel -= 3;
		}
	}

	public void func55(boolean checkWin) {
		// Draw 1 treasure
		// Bad: Lose a level
		if (checkWin) {
			myGame.currentPlayer.treasuresWonEachTurn += 1;
		} else {
			myGame.currentPlayer.pLevel -= 1;
		}
	}

	public void func56() {
		// If you draw this card face up, discard it and draw two door card, one
		// face down
		// and one face up
		// If you draw it face down, you can excahnge it anytime for two face
		// down door cards
	}

	public void func57Init() {
		// Class: Professor
		myGame.currentPlayer.className = "Professor";
	}

	public void func57() {
		// Need to add class abilities
		// If your are the lowest level player, or tied for it, you can
		// go up a level for every three cards you discard
		// If you run away successfully and whose monster's bad stuff included
		// death
		// go up a level
	}

	public void func58(boolean checkWin) {
		// Same as func3()
	}

	public void func59(boolean checkWin) {
		// No special powers
		// Draw one treasure
		// Bad: Become a cultist
		if (checkWin) {
			myGame.currentPlayer.treasuresWonEachTurn += 1;
		} else {
			myGame.currentPlayer.className = "Cultist";
		}
	}

	public void func60Init() {
		func1Init();
	}

	public void func60(boolean checkWin) {
		func1(checkWin);
	}

	public void func61Init() {
		// +2 against professors
		if (myGame.currentPlayer.className.equals("Professor")) {
			myGame.currentPlayer.cLevel -= 2;
		}
	}

	public void func61(boolean checkWin) {
		// Draw 2 treasures
		// Bad: You are dead
		if (checkWin) {
			myGame.currentPlayer.treasuresWonEachTurn += 2;
		} else {
			// Call the die method on current player
		}
	}

	public void func62Init() {
		// +3 against cultists
		if (myGame.currentPlayer.className.equals("Cultist")) {
			myGame.currentPlayer.cLevel -= 3;
		}
	}

	public void func62(boolean checkWin) {
		// Draw 1 treasures
		// Bad: Lose a level
		if (checkWin) {
			myGame.currentPlayer.treasuresWonEachTurn += 1;
		} else {
			myGame.currentPlayer.pLevel -= 1;
		}
	}

	public void func63Init() {
		// -2 against investigators
		// Will not pursue anyone of level 3 or below
		if (myGame.currentPlayer.className.equals("Investigator")) {
			myGame.currentPlayer.cLevel += 2;
		}
	}

	public void func63(boolean checkWin) {
		// Draw 4 treasures and gain 2 levels
		// Bad: Become a cultist
		if (checkWin) {
			myGame.currentPlayer.treasuresWonEachTurn += 4;
			myGame.currentPlayer.pLevel += 2;
		} else {
			myGame.currentPlayer.className = "Cultist";
		}
	}

	public void func64Init() {
		// Won't purse anyone of level 3 or below
	}

	public void func64(boolean checkWin) {
		// Draw 4 treasures and gain two levels
		// Bad: Lose three levels and your footgear
		if (checkWin) {
			myGame.currentPlayer.treasuresWonEachTurn += 4;
			myGame.currentPlayer.pLevel += 2;
		} else {
			// Need to discard all footgear in play
			myGame.currentPlayer.pLevel -= 3;
		}
	}

	public void func65Init() {
		// -3 against Monster Whackers
		if (myGame.currentPlayer.className.equals("Monster Whacker")) {
			myGame.currentPlayer.cLevel += 3;
		}
	}

	public void func65(boolean checkWin) {
		// Draw 4 treasures
		// Bad: Give a card to the lowest level player of your choice
		// If you are the lowest level, lose a level
		if (checkWin) {
			myGame.currentPlayer.treasuresWonEachTurn += 4;
		} else {
			int level1 = myGame.currentPlayer.pLevel;
			myGame.changePlayer();
			int level2 = myGame.currentPlayer.pLevel;
			if (level1 < level2) {
				myGame.changePlayer();
				myGame.currentPlayer.pLevel -= 1;
			} else if (level1 > level2) {
				myGame.currentPlayer.pLevel += 1;
				myGame.changePlayer();
			} else {
				// Do nothing I think
			}
		}
	}

	public void func66Init() {
		func1Init();
	}

	public void func66(boolean checkWin) {
		func1(checkWin);
	}

	public void func67() {
		// If you are a cultist, discard the cultist card
		// If you play it on someone else, they become a cultist and lose on of
		// their class cards
		if (myGame.currentPlayer.className.equals("Cultist")) {
			myGame.currentPlayer.className = "";
		} else if (!myGame.currentPlayer.className.equals("")) {
			myGame.currentPlayer.className = "Cultist";
		}
	}

	public void func68() {
		// Super Munchkin: Lets not implement this card
	}

	public void func69() {
		// Tchoo Tchoo people
		// Lets not implement this card either...
	}

	public void func70(boolean checkWin) {
		// Draw two treasures and a face down door card
		// Bad: Discard all door card from your hand
		if (checkWin) {
			myGame.currentPlayer.treasuresWonEachTurn += 2;
			myGame.dealNewCardTest(myGame.doors, myGame.currentPlayer);
		} else {
			// Discard all door cards
			for (int i = 0; i < myGame.currentPlayer.pHand.size(); i++) {
				if (myGame.currentPlayer.pHand.get(i) <= 82) {
					myGame.discardCard(myGame.currentPlayer, myGame.currentPlayer.pHand.get(i));
				}
			}
		}
	}

	public void func71Init() {
		// +2 against females
		if (myGame.currentPlayer.gender == 'F') {
			myGame.currentPlayer.cLevel -= 2;
		}
	}

	public void func71(boolean checkWin) {
		// Draw three treasures
		// Bad: Discard all helpful class cards
		if (checkWin) {
			myGame.currentPlayer.treasuresWonEachTurn += 3;
		} else {
			// Discard all helpful class cards
		}
	}

	public void func72Init() {
		// +6 against Monster Whackers
		if (myGame.currentPlayer.className.equals("Monster Whacker")) {
			myGame.currentPlayer.cLevel -= 6;
		}
	}

	public void func72(boolean checkWin) {
		// Draw two treasures
		// Bad: Lose your headgear and footgear
		if (checkWin) {
			myGame.currentPlayer.treasuresWonEachTurn += 2;
		} else {
			// Need to discard headgear and footgear somehow
		}
	}

	public void func73(boolean checkWin) {
		// No idea how to implement
	}

	public void func74Init() {
		// -2 against males
		// Won't pursue anyone of level 3 or below.
		if (myGame.currentPlayer.gender == 'M') {
			myGame.currentPlayer.cLevel += 2;
		}
	}

	public void func74(boolean checkWin) {
		// Draw 4 treasures and gain two levels
		// Bad: Lose one level for every card in your hand
		if (checkWin) {
			myGame.currentPlayer.treasuresWonEachTurn += 4;
			myGame.currentPlayer.pLevel += 2;
		} else {
			myGame.currentPlayer.pLevel -= myGame.currentPlayer.pHand.size();
		}
	}

	public void func75() {
		// Need to implement somehow...
	}

	public void func76Init() {
		// Monster gets +3 bonus
		myGame.currentPlayer.cLevel -= 3;
	}

	public void func77() {
		// Either discard a monster in combat or subtract 2 from the combat
		// strength of each monster
	}

	public void func78Init() {
		// -2 against Cultists
		if (myGame.currentPlayer.className.equals("Cultist")) {
			myGame.currentPlayer.cLevel += 2;
		}
	}

	public void func78(boolean checkWin) {
		// Draw three treasures
		// Bad: Can't help until you go up a level
		if (checkWin) {
			myGame.currentPlayer.treasuresWonEachTurn += 3;
		} else {
			// Can't help others until you go up a level
		}
	}

	public void func79Init() {
		// Add a field for pursuit (no pursuit of anyone lower than level 4)
	}

	public void func79(boolean checkWin) {
		// Draw 5 treasures and gain two levels
		// Bad: Discard all card that begin with a vowel (See below)
		if (checkWin) {
			myGame.currentPlayer.treasuresWonEachTurn += 5;
			myGame.currentPlayer.pLevel += 2;
		} else {
			// Discard all cards (in hand or in play) that begin with a vowel
		}
	}

	public void func80Init() {
		// If professor is in combat, +5 to the monster
		if (myGame.currentPlayer.className.equals("Professor")) {
			myGame.currentPlayer.cLevel -= 10;
		}
	}

	public void func80(boolean checkWin) {
		func1(checkWin);
	}

	// deal cards after a win
	public void getTreasFromWin() {
		for (int i = 0; i < myGame.currentPlayer.treasuresWonEachTurn; i++) {
			if (myGame.treasures.size() == 0) {
				myGame.treasures = myGame.shuffle(myGame.treasDiscards);

			}
			myGame.currentPlayer.pHand.add(myGame.treasures.get(myGame.treasures.size() - 1));
			myGame.treasures.remove(myGame.treasures.size() - 1);
			// myGame.dealNewCard(myGame.treasures, myGame.currentPlayer);
		}

	}

	public void func84Init() {
		if (myGame.monster) {
			int die = myGame.rollDice(0); // 0 indicates return random number
			myGame.currentPlayer.dice = die;
			System.out.println("dice is: " + die);
			myGame.mframe.mainPanel.bCardPanel.diceLevel.setText("num rolled is: " + die);
			myGame.mframe.mainPanel.bCardPanel.diceLevel.setVisible(true);
			if (die == 6) {
				myGame.currentPlayer.handLevel += 2;
			} else {

				// myGame.ic.getCardHash().get(84).discard=false;
				myGame.currentPlayer.handLevel += 2;
				myGame.currentPlayer.cLevel += 4;
				// myGame.mframe.mainPanel.bCardPanel.playerCLevel.setText("player
				// clevel: "+myGame.currentPlayer.cLevel);

			}
			myGame.mframe.revalidate();
			myGame.mframe.repaint();
		}

	}

	public void func84(boolean checkwin) {

		if (checkwin) {
			myGame.ic.getCardHash().get(84).discard = false;
			System.out.println("set discard to false");
		} else {
			if (myGame.currentPlayer.dice == 6) {
				myGame.ic.getCardHash().get(84).discard = true;
			}

		}

	}

	public void func85() {

	}

	public void func86Init() {
		if (!myGame.monster) {
			myGame.currentPlayer.handLevel += 1;
			myGame.currentPlayer.cLevel += 2;
		} else {
			cantPlay();
		}

		// myGame.mframe.mainPanel.bCardPanel.playerCLevel.setText("player
		// clevel: "+myGame.currentPlayer.cLevel);

	}

	public void func86(boolean checkwin) {

	}

	public void func87Init() {
		if (!myGame.monster)
			myGame.currentPlayer.cLevel += 1;
		myGame.ic.getCardHash().get(87).discard = false;
		// myGame.mframe.mainPanel.bCardPanel.playerCLevel.setText("player
		// clevel: "+myGame.currentPlayer.cLevel);
	}

	public void func87(boolean checkwin) {

	}

	public void func88Init() {
		func86Init();
	}

	public void func88(boolean checkwin) {

	}

	public void func89Init() {
		myGame.currentPlayer.pLevel++;
		myGame.currentPlayer.cLevel++;
		// myGame.mframe.mainPanel.bCardPanel.playerLevel.setText("player level:
		// "+myGame.currentPlayer.pLevel);
		// myGame.mframe.mainPanel.bCardPanel.playerCLevel.setText("player
		// clevel: "+myGame.currentPlayer.cLevel);
	}

	public void func89(boolean checkWin) {

	}

	public void func90Init() {

		if (myGame.currentPlayer.monster) {
			myGame.currentPlayer.cLevel += 3;
			// myGame.mframe.mainPanel.bCardPanel.playerCLevel.setText("player
			// clevel: "+myGame.currentPlayer.cLevel+"");

		} else {
			myGame.mLevel += 3;
			// myGame.mframe.mainPanel.bCardPanel.monsterLevel.setText("monster
			// level: "+myGame.mLevel);
		}

	}

	public void func90(boolean checkwin) {
		myGame.ic.getCardHash().get(90).discard = true;
	}

	public void func94Init() {
		func89Init();
	}

	public void func94(boolean checkWin) {

	}

	public void func96Init() {
		// if you lose, and roll dice, you get a +1. this is checked in the
		// consequence area.
		myGame.ic.getCardHash().get(96).headGear = true;
		myGame.currentPlayer.headLevel += 0; // DO NOT ADJUST HEAD LEVEL.

	}

	public void func96(boolean checkWin) {

	}

	public void func97Init() {
		if (!myGame.monster) {
			myGame.currentPlayer.cLevel += 3;
		} else {
			cantPlay();
		}

		// myGame.mframe.mainPanel.bCardPanel.playerCLevel.setText("player
		// clevel: "+myGame.currentPlayer.cLevel);

	}

	public void func97(boolean checkwin) {

	}

	public void func98Init() {
		if (!myGame.monster) {
			myGame.currentPlayer.cLevel += 4;
			myGame.currentPlayer.handLevel += 2;
		} else {
			cantPlay();
		}

		// myGame.mframe.mainPanel.bCardPanel.playerCLevel.setText("player
		// clevel: "+myGame.currentPlayer.cLevel);
	}

	public void func98(boolean checkwin) {

	}

	public void func99Init() {
		if (!myGame.monster) {
			myGame.currentPlayer.cLevel += 1;
		} else {
			cantPlay();
		}

		// myGame.mframe.mainPanel.bCardPanel.playerCLevel.setText("player
		// clevel: "+myGame.currentPlayer.cLevel);
	}

	public void func99(boolean checkwin) {

	}

	public void func100Init() {
		func90Init();
	}

	public void func100(boolean checkWin) {

	}

	public void func101Init() {

	}

	public void func102Init() {
		func89Init();
	}

	public void func102(boolean checkWin) {

	}

	public void func104Init() {
		func89Init();
	}

	public void func104(boolean checkWin) {

	}

	public void func105Init() {
		// Triple combat bonus for all footgear in combat
	}

	public void func106Init() {
		// Same as func100
		func90Init();
	}

	public void func106(boolean checkWin) {

	}

	public void func107Init() {
		func89Init();
	}

	public void func107(boolean checkWin) {

	}

	public void func108Init() {
		// Probably shouldn't implement
	}

	public void func109Init() {
		// Headgear for cultist only
		if (myGame.currentPlayer.className.equals("Cultist") && checkHeadGear(1)) {
			myGame.currentPlayer.headLevel += 1;
			myGame.currentPlayer.cLevel += 4;
		} else {
			System.out.println("You cannot play this card 109");
		}
	}

	public void func110Init() {
		// +2 to either side of combat
		// If other person is a lower level, they can pick it up
	}

	public void func111Init() {
		if (!myGame.monster) {
			myGame.currentPlayer.headLevel += 1;
			myGame.currentPlayer.cLevel += 2;
			myGame.ic.getCardHash().get(111).discard = false;
		} else {
			cantPlay();
		}
	}

	public void func111(boolean checkWin) {

	}

	public void func112Init() {
		if (!myGame.monster) {
			myGame.ic.getCardHash().get(112).discard = false;
			myGame.currentPlayer.cLevel += 1;
		} else {
			cantPlay();
		}
	}

	public void func113Init() {
		if (myGame.currentPlayer.className.equals("Monster Whacker") && checkFootLevel(1)) {
			myGame.currentPlayer.footLevel += 1;
			myGame.currentPlayer.cLevel += 3;
		} else {
			System.out.println("You cannot use this item 113");
		}
	}

	public void func113(boolean checkWin) {

	}

	public void func114Init() {
		if (!myGame.currentPlayer.className.equals("Cultist")) {
			myGame.currentPlayer.cLevel += 2;
			myGame.ic.getCardHash().get(114).discard = false;
		} else {
			cantPlay();
		}
	}

	public void func114(boolean checkWin) {

	}

	public void func115Init() {
		func89Init();
	}

	public void func115(boolean checkwin) {

	}

	public void func116Init() {
		if (!myGame.monster) {
			myGame.currentPlayer.cLevel += 1;
			myGame.ic.getCardHash().get(116).discard = false;
		} else {
			cantPlay();
		}
	}

	public void func116(boolean checkwin) {

	}

	public void func117Init() {
		if (!myGame.monster) {
			myGame.currentPlayer.cLevel += 2;
			myGame.ic.getCardHash().get(117).discard = false;
		} else {
			cantPlay();
		}
	}

	public void func117(boolean checkwin) {

	}

	public void func118Init() {
		if (myGame.currentPlayer.className.equals("Professor") && checkHands(2)) {
			myGame.currentPlayer.handLevel += 2;
			myGame.currentPlayer.cLevel += 5;
		} else {
			System.out.println("You cannot use this item 118");
		}
	}

	public void func119Init() {
		// If you are fighting a monster, you can end your turn by discarding
		// the monster and this card
		// You don't need to discard this card though
	}

	public void func120Init() {
		if (!myGame.monster) {
			myGame.currentPlayer.cLevel += 2;
			myGame.ic.getCardHash().get(120).discard = false;
		} else {
			cantPlay();
		}
	}

	public void func121Init() {
		// -1 for running away
	}

	public void func121(boolean checkWin) {
		// Draw one extra treasure every time you kill a monster
	}

	public void func122Init() {
		// Difficult to implement....
	}

	public void func123Init() {
		// If this card is played with a hands card, it is worth and extra +2 in
		// combat
	}

	public void func124Init() {
		// Same as 110
		func110Init();
	}

	public void func125Init() {
		if (checkHands(1)) {
			myGame.currentPlayer.handLevel += 1;
			if (myGame.mLevel >= 15) {
				myGame.currentPlayer.cLevel += 5;
			} else {
				myGame.currentPlayer.cLevel += 2;
			}
		} else {
			System.out.println("You cannot use this item 125");
		}
	}

	public void func126Init() {
		if (!myGame.monster) {
			myGame.currentPlayer.cLevel += 4;
		} else {
			cantPlay();
		}
	}

	public void func126(boolean b) {

	}

	public void func127Init() {
		// +2 to run away to either side
		// Usable only once
	}

	public void func128Init() {
		if (myGame.currentPlayer.className.equals("Monster Whacker") && checkHands(1)) {
			myGame.currentPlayer.handLevel += 1;
			myGame.currentPlayer.cLevel += 1;
		} else {
			System.out.println("You cannot use this item 128");
		}
	}

	public void func129Init() {
		func89Init();
	}

	public void func129(boolean c) {

	}

	public void func130Init() {
		if (checkFootLevel(1)) {
			// You escape automatically and the other person get -2 to his roll
		} else {
			System.out.println("You cannot use this item 130");
		}
	}

	public void func131Init() {
		if (checkHands(2)) {
			myGame.currentPlayer.handLevel += 2;
			// if monster card (shoggoth) =
			// else if monster (goth) card =
			// else +2
		} else {
			System.out.println("You cannot use this item 131");
		}
	}

	public void func132Init() {
		// -2 to either side of combat, usable only once.
		if (myGame.currentPlayer.monster) {
			myGame.currentPlayer.cLevel += 2;
			// myGame.mframe.mainPanel.bCardPanel.playerCLevel.setText("player
			// clevel: "+myGame.currentPlayer.cLevel+"");

		} else {
			myGame.mLevel += 2;
			// myGame.mframe.mainPanel.bCardPanel.monsterLevel.setText("monster
			// level: "+myGame.mLevel);
		}
	}

	public void func132(boolean b) {

	}

	public void func133Init() {
		// Immune to all ichor effect except the ones you play
		if (checkArmor(1)) {
			myGame.currentPlayer.armorLevel += 1;
			myGame.currentPlayer.cLevel += 2;
		} else {
			System.out.println("You cannot use this item 133");
		}
	}

	public void func134Init() {
		// Difficult to implement
	}

	public void func135Init() {
		// If you help in combat and the other munchkin loses, you go up two
		// levels
		// Otherwise, go up a level
	}

	public void func136Init() {
		// +2 in combat unless you sing, then +5 i combat
		// Defeats great cthulu, already implemented
	}

	public void func137Init() {
		// +4 to either side of combat
		// Usable only once
		if (myGame.currentPlayer.monster) {
			myGame.currentPlayer.cLevel += 4;
			// myGame.mframe.mainPanel.bCardPanel.playerCLevel.setText("player
			// clevel: "+myGame.currentPlayer.cLevel+"");

		} else {
			myGame.mLevel += 4;
			// myGame.mframe.mainPanel.bCardPanel.monsterLevel.setText("monster
			// level: "+myGame.mLevel);
		}
	}

	public void func137(boolean b) {

	}

	public void func138Init() {
		if (!myGame.monster) {
			myGame.currentPlayer.cLevel += 2;
		} else {
			cantPlay();
		}
	}

	public void func138(boolean c) {

	}

	public void func139Init() {
		func89Init();
	}

	public void func139(boolean c) {

	}

	public void func140Init() {
		func89Init();
	}

	public void func140(boolean c) {

	}

	public void func141Init() {
		if (myGame.currentPlayer.className.equals("Cultist")) {
			myGame.currentPlayer.handLevel -= 1;
		} else {
			System.out.println("You cannot use this item 141");
		}
	}

	public void func142Init() {
		// Other ichor effect is doubled
	}

	public void func143Init() {
		if (checkHands(2)) {
			myGame.currentPlayer.handLevel += 2;
			if (myGame.currentPlayer.username.equals("Tommy") || myGame.currentPlayer.username.equals("Tom")
					|| myGame.currentPlayer.username.equals("Tommie")) {
				myGame.currentPlayer.cLevel += 1;
			}
			myGame.currentPlayer.cLevel += 4;
		} else {
			System.out.println("You cannot use this item 143");
		}
	}

	public void func144Init() {
		if (myGame.currentPlayer.className.equals("Investigator") && checkHands(2)) {
			myGame.currentPlayer.handLevel += 2;
			myGame.currentPlayer.cLevel += 4;
		} else {
			System.out.println("You cannot use this item 144");
		}
	}

	public void func145Init() {
		if (myGame.currentPlayer.className.equals("Professor") && checkArmor(1)) {
			myGame.currentPlayer.armorLevel += 1;
			myGame.currentPlayer.cLevel += 2;
		} else {
			// If you lose your armour, you lose this too
			System.out.println("You cannot use this item 145");
		}
	}

	public void func146Init() {
		if (!myGame.currentPlayer.className.equals("Professor") && !myGame.monster) {
			myGame.currentPlayer.cLevel += 3;
		} else {
			cantPlay();
		}
	}

	public void func146(boolean c) {

	}

	public void func147Init() {
		// Draw three treasure cards immediately
	}

	public void func148Init() {
		if (!myGame.currentPlayer.className.equals("Investigator") && !myGame.monster) {
			myGame.currentPlayer.cLevel += 3;
		} else {
			cantPlay();
		}
	}

	public void func149Init() {
		// Cancels any curse
		// Usable only once
	}

	public void cantPlay() {
		if(myGame.currentPlayer.pPlay.size()<1) {
			System.err.println("The players played hand is empty");
			return;
		}
		myGame.currentPlayer.playCard = false;
		System.out.println("PHand"+myGame.currentPlayer.pHand);
		System.out.println("PPlay"+myGame.currentPlayer.pPlay);
		System.out.println();
		myGame.currentPlayer.pHand.add(myGame.currentPlayer.pPlay.get(myGame.currentPlayer.pPlay.size() - 1));
		myGame.currentPlayer.pPlay.remove(myGame.currentPlayer.pPlay.size() - 1);
	}

	/*
	 * We need an end function that deals out the treasure cards, so that means
	 * that the player needs to have a number of treasures to be dealt field.
	 */

	public void end() {
		if (myGame.turnPlayer == 1) {
			myGame.p1 = p;
		} else {
			myGame.p2 = p;
		}
	}

}