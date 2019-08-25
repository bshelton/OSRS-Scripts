package gdkiller.utils;

import gdkiller.GreenDragonKiller;
import org.powerbot.script.Client;
import org.powerbot.script.Filter;
import org.powerbot.script.rt4.*;
import z.I;

import java.util.regex.Pattern;

public class SelfService extends ClientContext {

    private static String gamesRegex = ".*Games.*";
    private static Pattern gamesPattern = Pattern.compile(gamesRegex);


    private String supersRegex = ".*Super.*";
    private static String prayerRegex = ".*Prayer.*";
    private static Pattern prayerPatten = Pattern.compile(prayerRegex);

    private Pattern supersPatten = Pattern.compile(supersRegex);

    private static String superAttackRegex = ".*Super attack.*";
    private static String superDefenceRegex = ".*Super defence.*";
    private static String superStrengthRegex = ".*Super strength.*";

    private static Pattern supersAttackPatten = Pattern.compile(superAttackRegex);
    private static Pattern superDefencePattern = Pattern.compile(superDefenceRegex);
    private static Pattern superStrengthPatten = Pattern.compile(superStrengthRegex);




    public SelfService(ClientContext ctx){
        super(ctx);
    }


    public static  boolean itemsOnGround(ClientContext ctx){
        GroundItem itemToPickup = ctx.groundItems.select().id(Items.PICKUP_ITEMS).nearest().poll();
        return itemToPickup.valid() && itemToPickup.inViewport();
    }
    public static boolean superAttackInInventory(ClientContext ctx){

        ItemQuery<Item> supersAttackQuery = ctx.inventory.select().name(supersAttackPatten);

        if (supersAttackQuery.isEmpty()){
            return false;
        } else return true;
    }

    public static Item getsuperAttack(ClientContext ctx){
        ItemQuery<Item> superAttack = ctx.inventory.select().name(supersAttackPatten);

        return superAttack.poll();
    }

    public static Item getsuperDefence(ClientContext ctx){
        ItemQuery<Item> superDefence = ctx.inventory.select().name(superDefencePattern);

        return superDefence.poll();
    }
    public static Item getsuperStrength(ClientContext ctx){
        ItemQuery<Item> superStrength = ctx.inventory.select().name(superStrengthPatten);

        return superStrength.poll();
    }

    public static Item getprayerPotion(ClientContext ctx){
        ItemQuery<Item> prayerPotion = ctx.inventory.select().name(prayerPatten);

        return prayerPotion.poll();

    }

    public static boolean prayerInInventory(ClientContext ctx){
        ItemQuery<Item> prayerPotion = ctx.inventory.select().name(prayerPatten);

        if (prayerPotion.isEmpty()){
            return false;
        } else return true;
    }

    public static boolean superDefenceInInventory(ClientContext ctx){

        ItemQuery<Item> supersDefenceQuery = ctx.inventory.select().name(superDefencePattern);

        if (supersDefenceQuery.isEmpty()){
            return false;
        } else return true;
    }

    public static Npc getDrag(ClientContext ctx){
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

    public static boolean superStrengthInInventory(ClientContext ctx){

        ItemQuery<Item> supersStrengthQuery = ctx.inventory.select().name(superStrengthPatten);

        if (supersStrengthQuery.isEmpty()){
            return false;
        } else return true;
    }

    public static int getFoodIDFromInventory(ClientContext ctx){
        String foodRegex = ".*"+ GreenDragonKiller.foodSelection+".*";
        Pattern foodPattern = Pattern.compile(foodRegex);
        ItemQuery<Item> foodQueryInventory = ctx.inventory.select().name(foodPattern);

        if (!foodQueryInventory.isEmpty()){
            return foodQueryInventory.poll().id();
        }

        return -1;
    }

    public static int getFoodIDFromBank(ClientContext ctx){
        String foodRegex = ".*"+ GreenDragonKiller.foodSelection+".*";
        Pattern foodPattern = Pattern.compile(foodRegex);
        ItemQuery<Item> foodQueryBank = ctx.bank.select().name(foodPattern);

        if (!foodQueryBank.isEmpty()){
            return foodQueryBank.poll().id();
        }

        return -1;
    }

    public static boolean haveFoodInInventory(ClientContext ctx){
        return ctx.inventory.id(getFoodIDFromInventory(ctx)).count() > 0;
    }

    public static Item getFood(ClientContext ctx){
        ItemQuery<Item> foodQuery = ctx.inventory.select().id(SelfService.getFoodIDFromInventory(ctx));
        Item foodToEat = foodQuery.poll();
        return foodToEat;
    }


    public static boolean wearingGlory(ClientContext ctx){
        ctx.game.tab(Game.Tab.EQUIPMENT);

        String glory = "";

        int i = 0;
        while (!ctx.players.local().ctx.equipment.itemAt(Equipment.Slot.NECK).name().equals(glory)){
            i++;
            glory = "Amulet of glory(" + i + ")";

            if (ctx.players.local().ctx.equipment.itemAt(Equipment.Slot.NECK).name().equals(glory)) {
                System.out.println("Wearing the amulet");
                return true;
            }
        }
        return false;
    }


    public static boolean idling(ClientContext ctx) {
        Player self = ctx.players.local();
        return self.animation() == -1
                && !self.healthBarVisible()
                && !self.inMotion();
    }

    public boolean wearingGlory(){
        players.ctx.game.tab(Game.Tab.EQUIPMENT);

        String glory = "";

        int i = 0;
        while (!players.local().ctx.equipment.itemAt(Equipment.Slot.NECK).name().equals(glory)){
            i++;
            glory = "Amulet of glory(" + i + ")";

            if (players.local().ctx.equipment.itemAt(Equipment.Slot.NECK).name().equals(glory)) {
                System.out.println("Wearing the amulet");
                return true;
            }
        }
        return false;
    }

    public static boolean haveGamesNecklaceinInventory(ClientContext ctx){
        ctx.game.tab(Game.Tab.INVENTORY);
        ItemQuery<Item> gamesQuery = ctx.inventory.select().name(gamesPattern);

        if (!gamesQuery.isEmpty()){
            return true;
        }
        return false;
    }
}
