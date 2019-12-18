package scripts.ess.utils;

import org.powerbot.script.rt4.Equipment;
import org.powerbot.script.rt4.Game;
import org.powerbot.script.rt4.ClientContext;

public class DuelingRing {

    public final int RING_OF_DUELING_ID8;
    public final int RING_OF_DUELING_ID7;
    public final int RING_OF_DUELING_ID6;
    public final int RING_OF_DUELING_ID5;
    public final int RING_OF_DUELING_ID4;
    public final int RING_OF_DUELING_ID3;
    public final int RING_OF_DUELING_ID2;
    public final int RING_OF_DUELING_ID1;

    public final int[] rings;

    public DuelingRing(){

        this.RING_OF_DUELING_ID1 = 2566;
        this.RING_OF_DUELING_ID2 = 2564;
        this.RING_OF_DUELING_ID3 = 2562;
        this.RING_OF_DUELING_ID4 = 2560;
        this.RING_OF_DUELING_ID5 = 2558;
        this.RING_OF_DUELING_ID6 = 2556;
        this.RING_OF_DUELING_ID7 = 2554;
        this.RING_OF_DUELING_ID8 = 2552;

        rings = new int[] {
                RING_OF_DUELING_ID1, RING_OF_DUELING_ID2,
                RING_OF_DUELING_ID3, RING_OF_DUELING_ID4,
                RING_OF_DUELING_ID5, RING_OF_DUELING_ID6,
                RING_OF_DUELING_ID7, RING_OF_DUELING_ID8
        };
    }

    public boolean oneCharge(ClientContext ctx){
        ctx.game.tab(Game.Tab.EQUIPMENT);
        //return players.local().ctx.equipment.itemAt(Equipment.Slot.RING).name().equals("Ring of dueling(1)");
        return ctx.equipment.itemAt(Equipment.Slot.RING).id() == getRING_OF_DUELING_ID1();
    }

    public boolean wearingRing(ClientContext ctx){
        ctx.game.tab(Game.Tab.EQUIPMENT);

        for (int ring : rings) {
            if (ctx.equipment.itemAt(Equipment.Slot.RING).id() == ring) {
                return true;
            }
        }
        return false;
    }

    public int getRING_OF_DUELING_ID8() {
        return RING_OF_DUELING_ID8;
    }

    public int getRING_OF_DUELING_ID7() {
        return RING_OF_DUELING_ID7;
    }

    public int getRING_OF_DUELING_ID6() {
        return RING_OF_DUELING_ID6;
    }

    public int getRING_OF_DUELING_ID5() {
        return RING_OF_DUELING_ID5;
    }

    public int getRING_OF_DUELING_ID4() {
        return RING_OF_DUELING_ID4;
    }

    public int getRING_OF_DUELING_ID3() {
        return RING_OF_DUELING_ID3;
    }

    public int getRING_OF_DUELING_ID2() {
        return RING_OF_DUELING_ID2;
    }

    public int getRING_OF_DUELING_ID1() {
        return RING_OF_DUELING_ID1;
    }

}
