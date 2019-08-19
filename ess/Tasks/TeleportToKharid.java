package ess.Tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Equipment.*;
import org.powerbot.script.rt4.Game;

import ess.utils.Items;

import java.util.concurrent.Callable;
import java.util.Random;

import static ess.utils.Areas.*;

public class TeleportToKharid extends Task {

    public TeleportToKharid(ClientContext ctx){
        super(ctx);
    }

    public boolean activate(){
        return !players.ctx.inventory.select().id(Items.PURE_ESS_ID).isEmpty()
                && CASTLE_WARS_AREA.getCentralTile().distanceTo(players.local()) < 25
                && !players.ctx.players.local().inMotion();
    }

    public void execute(){

        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return (players.ctx.game.tab(Game.Tab.EQUIPMENT));
            }
        },100, 100);

        Condition.sleep(new Random().nextInt(5));

        Condition.wait(new Callable<Boolean>() {
          @Override
            public Boolean call() throws Exception {
               return (players.local().ctx.equipment.itemAt(Slot.RING).interact("Duel Arena"));
           }
       },100, 100);
        Condition.sleep(500);
    }
}
