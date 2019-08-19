package gdkiller.utils;

import gdkiller.GreenDragonKiller;
import org.powerbot.script.rt4.*;

import java.util.regex.Pattern;

public class SelfService extends ClientContext {

    private String gamesRegex = ".*Games.*";
    private Pattern gamesPattern = Pattern.compile(gamesRegex);

    private static String foodRegex = ".*"+ GreenDragonKiller.foodSelection+".*";
    private static Pattern foodPattern = Pattern.compile(foodRegex);


    public SelfService(ClientContext ctx){
        super(ctx);
    }

    public static int getFoodIDFromInventory(ClientContext ctx){
        ItemQuery<Item> foodQueryInventory = ctx.inventory.select().name(foodPattern);

        if (!foodQueryInventory.isEmpty()){
            return foodQueryInventory.poll().id();
        }

        return -1;
    }

    public static int getFoodIDFromBank(ClientContext ctx){
        ItemQuery<Item> foodQueryBank = ctx.bank.select().name(foodPattern);

        if (!foodQueryBank.isEmpty()){
            return foodQueryBank.poll().id();
        }

        return -1;
    }

    public static boolean haveFoodInInventory(ClientContext ctx){
        return ctx.inventory.id(getFoodIDFromInventory(ctx)).count() > 0;
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

    public boolean haveGamesNecklace(){
        players.ctx.game.tab(Game.Tab.INVENTORY);
        ItemQuery<Item> gamesQuery = players.ctx.inventory.select().name(gamesPattern);

        if (gamesQuery.isEmpty()){
            System.out.println("No games necklace found");
        }

        return gamesQuery.poll().valid();
    }
}
