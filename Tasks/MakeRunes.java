package LavaCrafter.Tasks;

import LavaCrafter.LavaCrafter;
import LavaCrafter.Tasks.Task;
import LavaCrafter.Tasks.Walk;
import LavaCrafter.utils.Items;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.Item;

import java.util.Random;
import java.util.concurrent.Callable;

import static ess.utils.Areas.INSIDE_ALTAR;

public class MakeRunes extends Task {

    private final int ALTAR_ID = 34764;
    public final static int EARTH_TALISMAN_ID = 1440;
    private final Walk walker = new Walk(players.ctx);
    public static final Tile[] path = {new Tile(2575, 4848, 0), new Tile(2578, 4845, 0), new Tile(2582, 4844, 0)};

    public MakeRunes(ClientContext ctx) {
        super(ctx);
    }

    public boolean activate() {
        return !players.ctx.inventory.select().id(Items.PURE_ESS_ID).isEmpty() && INSIDE_ALTAR.getCentralTile().distanceTo(players.local()) < 50;
    }

    public void execute() {
        System.out.println("inside make");
        GameObject altar = players.ctx.objects.select().id(ALTAR_ID).poll();
        Item talisman =  players.ctx.inventory.select().id(EARTH_TALISMAN_ID).poll();

        if (!altar.inViewport()){
            camera.turnTo(altar);
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return (walker.walkPath(path));
                }
            },100, 100);
        }

        if (players.ctx.inventory.select().id(Items.FIRE_RUNE_ID).count() < 1 && !players.local().inMotion()){
            talisman.interact("Use");
            altar.interact("Use");
        }
        Condition.sleep(new Random().nextInt(1500));
    }
}