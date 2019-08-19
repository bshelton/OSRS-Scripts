package ess.Tasks;

import static ess.utils.Areas.DUEL_ARENA_AREA;

import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.Tile;

public class WalkToAltar extends Task {
    private final Walk walker = new Walk(players.ctx);
    private static final Tile[] PATH_TO_FIRE_ALTAR = {new Tile(3307, 3236, 0), new Tile(3307, 3242, 0), new Tile(3306, 3236, 0), new Tile(3306, 3240, 0), new Tile(3307, 3244, 0), new Tile(3309, 3248, 0), new Tile(3311, 3252, 0)};

    public WalkToAltar(ClientContext ctx){
        super(ctx);
    }

    public boolean activate() {
        return  players.ctx.inventory.select().count() > 26  && DUEL_ARENA_AREA.getCentralTile().distanceTo(players.local()) < 105;

    }

    public void execute(){

        if(!players.ctx.movement.running() && players.ctx.movement.energyLevel() > 45){
            players.ctx.movement.running(true);
        }

        if (!players.local().inMotion())
        walker.walkPath(PATH_TO_FIRE_ALTAR);
//        Condition.wait(new Callable<Boolean>() {
//            @Override
//            public Boolean call() throws Exception {
//                return (walker.walkPath(PATH_TO_FIRE_ALTAR));
//            }
//        },100, 100);

    }
}
