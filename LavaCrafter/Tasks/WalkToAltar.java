package scripts.LavaCrafter.Tasks;

import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

import static scripts.ess.utils.Areas.DUEL_ARENA_AREA;

public class WalkToAltar extends Task {
    private final Walk walker = new Walk(ctx);
    private static final Tile[] PATH_TO_FIRE_ALTAR = {new Tile(3307, 3236, 0), new Tile(3307, 3242, 0), new Tile(3306, 3236, 0), new Tile(3306, 3240, 0), new Tile(3307, 3244, 0), new Tile(3309, 3248, 0), new Tile(3311, 3252, 0)};

    public WalkToAltar(ClientContext ctx){
        super(ctx);
    }

    public boolean activate() {
        return  ctx.inventory.select().count() > 26  && DUEL_ARENA_AREA.getCentralTile().distanceTo(ctx.players.local()) < 105;

    }

    public void execute(){

        if(!ctx.movement.running() && ctx.movement.energyLevel() > 45){
            ctx.movement.running(true);
        }

        if (!ctx.players.local().inMotion())
            walker.walkPath(PATH_TO_FIRE_ALTAR);
    }
}
