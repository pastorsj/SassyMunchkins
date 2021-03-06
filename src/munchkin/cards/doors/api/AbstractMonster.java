package munchkin.cards.doors.api;

public abstract class AbstractMonster extends Door {



    @Override
    public void cardPlayed(boolean win) {
        if(win) {
            this.getOwner().addTreasures(this.getTreasures());
        } else {
            this.badStuff();
        }
        this.setDiscard();
    }

    public abstract void badStuff();

}
