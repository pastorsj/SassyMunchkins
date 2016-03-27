package munchkin.cards.treasures;

import munchkin.cards.treasures.api.Armor;
import munchkin.cards.treasures.api.Treasure;
import munchkin.game.Game;

public class ShoesOfOnlyHavingToRunFasterThanYOU extends Treasure {

    public ShoesOfOnlyHavingToRunFasterThanYOU(Game game) {
        super(game);
    }

    @Override
    public void cardInHand() {
        this.setGold(500);
    }

    @Override
    public void cardInPlay() {
        this.setArmor(Armor.FootGear);
        //If running away, you escape automatically, and the other players get -2 on their roll
    }
}