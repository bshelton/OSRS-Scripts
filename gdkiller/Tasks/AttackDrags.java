package scripts.gdkiller.Tasks;

import scripts.gdkiller.GreenDragonKiller;
import scripts.gdkiller.utils.Areas;
import scripts.gdkiller.GreenDragonKiller.*;

import scripts.gdkiller.utils.Items;
import scripts.gdkiller.utils.SelfService;
import org.powerbot.script.Filter;
import org.powerbot.script.rt4.*;
import org.powerbot.script.Condition;


import java.util.concurrent.Callable;
import java.util.regex.Pattern;
import java.util.Random;
public class AttackDrags extends Task{


    private String foodRegex = ".*"+GreenDragonKiller.foodSelection+".*";
    private Pattern foodPattern = Pattern.compile(foodRegex);

    public AttackDrags(ClientContext ctx) {super(ctx);}

    @Override
    public boolean activate(){

        final Npc DRAG = getDrag(ctx);

        return  Areas.GREEN_DRAGS_LVL24.getCentralTile().distanceTo(ctx.players.local()) < 75
                && canAttackDrag(ctx)
                && hasFoodInInventory()
                && !ctx.players.local().healthBarVisible()
                && !interactingwithDrag(DRAG)
                && ctx.combat.healthPercent() > 45
                && !itemsOnGround();
    }

    @Override
    public void execute(){

        ctx.combat.autoRetaliate(true);
        ctx.game.tab(Game.Tab.INVENTORY);

        if (GreenDragonKiller.usesuperPotion && SelfService.superAttackInInventory(ctx) && ctx.skills.level(0) == ctx.skills.realLevel(0)){
            SelfService.getsuperAttack(ctx).interact("Drink");
        }

        if (GreenDragonKiller.usesuperPotion && SelfService.superDefenceInInventory(ctx) && ctx.skills.level(1) == ctx.skills.realLevel(1)){
            SelfService.getsuperDefence(ctx).interact("Drink");
        }

        if (GreenDragonKiller.usesuperPotion && SelfService.superStrengthInInventory(ctx) && ctx.skills.level(2) <= ctx.skills.realLevel(2)){

            SelfService.getsuperStrength(ctx).interact("Drink");
        }

        if (GreenDragonKiller.usePrayerPotions && SelfService.prayerInInventory(ctx )&& ctx.skills.level(5) == ctx.skills.realLevel(5)){
            SelfService.getprayerPotion(ctx).interact("Drink");
        }

        ItemQuery<Item> foodQuery = ctx.inventory.select().id(SelfService.getFoodIDFromInventory(ctx));
        Item foodToEat = foodQuery.poll();

        final Npc DRAGON = getDrag(ctx);


        if (ctx.combat.healthPercent() < 60){
            foodToEat.interact("Eat");
        }

        if (!getDrag(ctx).inViewport()){
            ctx.movement.step(DRAGON);
            ctx.camera.turnTo(DRAGON);
        }

        if (DRAGON.animation() != 92 ) {// not dying
            DRAGON.interact("Attack");
        }

        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return !DRAGON.interacting().equals(ctx.players.local()) || DRAGON.healthPercent() == 0;
            }
        }, 250, 12);

        Condition.sleep(new Random().nextInt(1500));
    }

    private boolean hasFoodInInventory(){
        ItemQuery<Item> foodQuery = ctx.inventory.select().name(foodPattern);
        return foodQuery.isEmpty();
    }

    private boolean itemsOnGround(){
        GroundItem itemToPickup = ctx.groundItems.select().id(Items.PICKUP_ITEMS).nearest().poll();
        return itemToPickup.valid() && itemToPickup.inViewport();
    }

    private static Npc getDrag(ClientContext ctx){
        Npc dragToAttack = ctx.npcs.select().id(Items.DRAGON_IDS).select(new Filter<Npc>() {
            @Override
            public boolean accept(Npc npc) {
                return !npc.interacting().valid() && npc.healthPercent() > 0;
            }

            @Override
            public boolean test(Npc npc){return true;}
        }).nearest().poll();

        return dragToAttack;
    }

    private static boolean canAttackDrag(ClientContext ctx){
        return getDrag(ctx).valid();
    }

    private boolean interactingwithDrag(Npc npc){
        if (ctx.players.local().interacting() == npc){
            return true;
        } else return false;
    }
}
