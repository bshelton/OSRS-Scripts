package gdkiller.Tasks;

import gdkiller.utils.Areas;

import gdkiller.utils.SelfService;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import java.util.concurrent.Callable;

import org.powerbot.script.Tile;
import org.powerbot.script.rt4.Game;

public class WalkToDrags extends Task{

    public static final Tile[] path = {new Tile(3206, 3682, 0), new Tile(3203, 3685, 0), new Tile(3199, 3685, 0), new Tile(3195, 3687, 0), new Tile(3199, 3687, 0), new Tile(3199, 3691, 0), new Tile(3198, 3695, 0), new Tile(3194, 3695, 0), new Tile(3190, 3695, 0), new Tile(3186, 3697, 0), new Tile(3182, 3699, 0), new Tile(3178, 3701, 0), new Tile(3174, 3704, 0), new Tile(3170, 3706, 0), new Tile(3166, 3708, 0), new Tile(3162, 3710, 0), new Tile(3158, 3710, 0)};
    public static final Tile[] pathToDrags = {new Tile(3209, 3689, 0), new Tile(3207, 3693, 0), new Tile(3203, 3695, 0), new Tile(3199, 3698, 0), new Tile(3195, 3699, 0), new Tile(3192, 3702, 0), new Tile(3188, 3703, 0), new Tile(3184, 3703, 0), new Tile(3181, 3706, 0), new Tile(3178, 3709, 0), new Tile(3174, 3708, 0), new Tile(3170, 3709, 0), new Tile(3166, 3710, 0)};

    private final Walk walker = new Walk(ctx);

    public WalkToDrags(ClientContext ctx){super(ctx);}

    @Override
    public boolean activate(){return Areas.OUTSIDE_CORP_BEAST.getCentralTile().distanceTo(ctx.players.local()) < 45;
    }

    @Override
    public void execute(){
        if(!ctx.movement.running() && ctx.movement.energyLevel() > 45){
            ctx.movement.running(true);
        }

        ctx.game.tab(Game.Tab.INVENTORY);

//        if (!players.local().inMotion()){
//            walker.walkPath(path);
//        }

        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return (walker.walkPath(pathToDrags));
            }
        },100, 100);
    }


}
