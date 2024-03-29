package scripts.ess.Tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.Tile;
import java.util.Random;
import scripts.ess.utils.Items;

import java.util.concurrent.Callable;

import static scripts.ess.utils.Areas.*;

public class MakeFireRunes extends Task {

    private final int ALTAR_ID = 34764;
    private final Walk walker = new Walk(ctx);
    public static final Tile[] path = {new Tile(2575, 4848, 0), new Tile(2578, 4845, 0), new Tile(2582, 4844, 0)};

    public MakeFireRunes(ClientContext ctx) {
        super(ctx);
    }

    public boolean activate() {
        return !ctx.inventory.select().id(Items.PURE_ESS_ID).isEmpty() && INSIDE_ALTAR.getCentralTile().distanceTo(ctx.players.local()) < 50;
    }

    public void execute() {

        GameObject altar = ctx.objects.select().id(ALTAR_ID).poll();

        if (!altar.inViewport()){
            ctx.camera.turnTo(altar);
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return (walker.walkPath(path));
                }
            },100, 100);
        }

        if (ctx.inventory.select().id(Items.FIRE_RUNE_ID).count() < 1 && !ctx.players.local().inMotion()){
            altar.interact("Craft-rune");
        }
        Condition.sleep(new Random().nextInt(1500));
    }
}