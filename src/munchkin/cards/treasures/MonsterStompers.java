package munchkin.cards.treasures;

import munchkin.cards.treasures.api.Armor;
import munchkin.cards.treasures.api.Faction;
import munchkin.cards.treasures.api.Treasure;
import munchkin.game.Game;

public class MonsterStompers extends Treasure {

    public MonsterStompers(Game game) {
        super(game);
    }

    @Override
    public void cardInHand() {
        this.setGold(600);
        if(!this.getOwner().getFaction().equals(Faction.MonsterWhacker)) {
            System.out.println("MonsterStompers disabled: " + this.getOwner().getFaction().name());
            this.disable();
        }
        this.setArmor(Armor.FootGear);
    }

    @Override
    public void cardInPlay() {
        this.setBonus(3);
    }
}