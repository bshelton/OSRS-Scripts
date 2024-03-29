package scripts.ess.Tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.*;
import org.powerbot.script.rt4.Equipment.*;

import scripts.ess.utils.Items;
import scripts.ess.utils.SelfService;

import java.util.concurrent.Callable;

import static scripts.ess.utils.Areas.*;

public class TeleportToCastleWars extends Task {

    public TeleportToCastleWars(ClientContext ctx) {
        super(ctx);
    }

    public boolean activate() {
        return ctx.inventory.select().id(Items.FIRE_RUNE_ID).count() > 0
                && AT_FIRE_ALTAR.getCentralTile().distanceTo(ctx.players.local()) < 30
                && !ctx.players.local().inMotion();
    }

    public void execute() {

        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return (ctx.game.tab(Game.Tab.EQUIPMENT));
            }
        },100, 100);

//        Condition.wait(new Callable<Boolean>() {
//            @Override
//          public Boolean call() throws Exception {
//               return players.local().animation() != -1;
//            }
//            }, 100, 100);

        if (SelfService.idling(ctx)) {
            ctx.players.local().ctx.equipment.itemAt(Slot.RING).interact("Castle Wars");
        }

//        Condition.wait(new Callable<Boolean>() {
//            @Override
//            public Boolean call() throws Exception {
//                return (players.local().ctx.equipment.itemAt(Slot.RING).interact("Castle Wars"));
//            }
//            }, 100, 100);
        Condition.sleep(500);
    }
}
