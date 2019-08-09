package LavaCrafter.Tasks;

import LavaCrafter.Tasks.Task;
import LavaCrafter.utils.Items;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Equipment.Slot;
import org.powerbot.script.rt4.Game;

import java.util.Random;
import java.util.concurrent.Callable;

import static ess.utils.Areas.AT_FIRE_ALTAR;

public class TeleportToCastleWars extends Task {

    public TeleportToCastleWars(ClientContext ctx) {
        super(ctx);
    }

    public boolean activate() {
        return players.ctx.inventory.select().id(Items.FIRE_RUNE_ID).count() > 0 && AT_FIRE_ALTAR.getCentralTile().distanceTo(players.local()) < 30;
    }

    public void execute() {

        players.ctx.game.tab(Game.Tab.EQUIPMENT);

        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return (players.local().ctx.equipment.itemAt(Slot.RING).interact("Castle Wars"));
            }
        }, 300, 100);
        Condition.sleep(new Random().nextInt(1500));
    }
}
