package scripts.ess.Tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Equipment.*;
import org.powerbot.script.rt4.Game;

import scripts.ess.utils.Items;

import java.util.concurrent.Callable;
import java.util.Random;

import static scripts.ess.utils.Areas.*;

public class TeleportToKharid extends Task {

    public TeleportToKharid(ClientContext ctx){
        super(ctx);
    }

    public boolean activate(){
        return !ctx.inventory.select().id(Items.PURE_ESS_ID).isEmpty()
                && CASTLE_WARS_AREA.getCentralTile().distanceTo(ctx.players.local()) < 25
                && !ctx.players.local().inMotion();
    }

    public void execute(){

        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return (ctx.game.tab(Game.Tab.EQUIPMENT));
            }
        },100, 100);

        Condition.sleep(new Random().nextInt(5));

        Condition.wait(new Callable<Boolean>() {
          @Override
            public Boolean call() throws Exception {
               return (ctx.players.local().ctx.equipment.itemAt(Slot.RING).interact("Duel Arena"));
           }
       },100, 100);
        Condition.sleep(500);
    }
}
