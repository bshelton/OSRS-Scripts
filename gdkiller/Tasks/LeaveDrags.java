package gdkiller.Tasks;

import gdkiller.utils.Items;
import gdkiller.utils.SelfService;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Equipment;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.GameObject;

public class LeaveDrags extends Task {

    public static final Tile[] path = {new Tile(3136, 3708, 0), new Tile(3136, 3704, 0), new Tile(3136, 3700, 0), new Tile(3136, 3696, 0), new Tile(3133, 3692, 0), new Tile(3134, 3688, 0), new Tile(3135, 3684, 0), new Tile(3135, 3680, 0), new Tile(3135, 3676, 0), new Tile(3135, 3672, 0), new Tile(3135, 3668, 0), new Tile(3135, 3664, 0), new Tile(3135, 3660, 0), new Tile(3139, 3657, 0), new Tile(3138, 3653, 0), new Tile(3135, 3650, 0), new Tile(3132, 3646, 0), new Tile(3132, 3642, 0), new Tile(3129, 3638, 0), new Tile(3128, 3634, 0), new Tile(3128, 3630, 0), new Tile(3128, 3626, 0), new Tile(3128, 3622, 0), new Tile(3128, 3618, 0), new Tile(3128, 3614, 0), new Tile(3128, 3610, 0), new Tile(3127, 3606, 0), new Tile(3127, 3602, 0), new Tile(3126, 3598, 0), new Tile(3126, 3594, 0), new Tile(3125, 3590, 0), new Tile(3122, 3587, 0), new Tile(3122, 3583, 0), new Tile(3122, 3579, 0), new Tile(3119, 3575, 0), new Tile(3119, 3571, 0), new Tile(3121, 3567, 0), new Tile(3121, 3563, 0), new Tile(3118, 3560, 0), new Tile(3114, 3558, 0), new Tile(3110, 3558, 0), new Tile(3106, 3555, 0), new Tile(3105, 3551, 0), new Tile(3105, 3547, 0), new Tile(3105, 3543, 0), new Tile(3102, 3540, 0), new Tile(3100, 3536, 0), new Tile(3100, 3532, 0), new Tile(3100, 3528, 0), new Tile(3103, 3525, 0)};

    public static final Tile[] ditchToBank = {new Tile(3079, 3520, 0), new Tile(3075, 3518, 0), new Tile(3075, 3514, 0), new Tile(3075, 3510, 0), new Tile(3075, 3506, 0), new Tile(3079, 3506, 0), new Tile(3082, 3502, 0), new Tile(3086, 3500, 0), new Tile(3090, 3500, 0), new Tile(3093, 3497, 0)};
    private final int crossing = 6132;

    private final Walk walker = new Walk(players.ctx);


    public LeaveDrags(ClientContext ctx){
        super(ctx);
    }

    @Override
    public boolean activate(){
        return !SelfService.haveFoodInInventory(players.ctx)
//                /&& players.local().healthPercent() < 50
                && SelfService.idling(players.ctx)
                && players.local().tile().y() > 3520;
    }

    @Override
    public void execute(){
        GameObject ditch = players.ctx.objects.select().id(Items.DITCH).poll();


        if (SelfService.wearingGlory(players.ctx)){
            players.ctx.equipment.itemAt(Equipment.Slot.NECK).interact("Edgeville");
        }

        System.out.println(ditch.name());

        ditch.interact("Cross");

    }
}
