package scripts.ess.utils;

import org.powerbot.script.rt4.*;


//Need to make use of later.
public class SelfService {

    public SelfService(){}

    public static boolean idling(ClientContext ctx) {
        Player self = ctx.players.local();
        return self.animation() == -1
                && !self.healthBarVisible()
                && !self.inMotion();
    }

    public static boolean oneCharge(ClientContext ctx){
        ctx.game.tab(Game.Tab.EQUIPMENT);
        return ctx.equipment.itemAt(Equipment.Slot.RING).id() == Items.RING_OF_DUELING_ID1;
    }

    public static boolean wearingDuelingRing(ClientContext ctx){
        ctx.game.tab(Game.Tab.EQUIPMENT);
        for (int ring : Items.rings) {
            if (wearingItem(ctx, ring)) {
                return true;
            }
        }
        return false;
    }

    public static boolean wearingItem(ClientContext ctx, int itemid){
        ctx.game.tab(Game.Tab.EQUIPMENT);
        for (Equipment.Slot s : Equipment.Slot.values()){
            if (ctx.equipment.itemAt(s).id() == itemid){
                return true;
            }
        }
        return false;
    }
}
