package gdkiller.Tasks;

import gdkiller.GreenDragonKiller;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.*;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gdkiller.utils.*;

public class Banking extends Task{

    private String gloryRegex = ".*glory\\(.*";
    private String gamesRegex = ".*Games.*";

    private String supersRegex = ".*Super.*";
    private String prayerRegex = ".*Prayer.*";
    private Pattern prayerPatten = Pattern.compile(prayerRegex);

    private Pattern supersPatten = Pattern.compile(supersRegex);

    private String superAttackRegex = ".*Super attack.*";
    private String superDefenceRegex = ".*Super defence.*";
    private String superStrengthRegex = ".*Super strength.*";

    private Pattern supersAttackPatten = Pattern.compile(superAttackRegex);
    private Pattern superDefencePattern = Pattern.compile(superDefenceRegex);
    private Pattern superStrengthPatten = Pattern.compile(superStrengthRegex);

    private Pattern gloryPattern = Pattern.compile(gloryRegex);
    private Pattern gamesPattern = Pattern.compile(gamesRegex);

    private boolean wearingGlory = false;

    public Banking(ClientContext ctx){
        super(ctx);
    }

    @Override
    public boolean activate(){
        return  Areas.EDGEVILLE.getCentralTile().distanceTo(ctx.players.local()) < 7
                && SelfService.idling(ctx)
                && !ctx.chat.chatting()
                && !ctx.bank.opened();
    }

    @Override
    public void execute(){
        wearingGlory = wearingGlory();

        if (!ctx.bank.inViewport()){
            ctx.camera.turnTo(ctx.bank.nearest());
        }

        if (!ctx.bank.opened()) {
            ctx.bank.open();
            ctx.bank.depositInventory();

            withdrawGames();

            if (!wearingGlory){
                withdrawGlory();
            }

            Condition.sleep(new Random().nextInt(500));

            if (GreenDragonKiller.usesuperPotion){
                withdrawSuperAttack();
                withdrawSuperDefence();

                Condition.sleep(new Random().nextInt(500));

                withdrawSuperStrength();
            }

            if (GreenDragonKiller.usePrayerPotions) {
                withdrawPrayers();
                Condition.sleep(new Random().nextInt(500));
            }

            if (GreenDragonKiller.useLootBag && lootBagInBank() && !lootBagInInventory()) {
                ctx.bank.withdraw(Items.LOOT_BAG, Bank.Amount.ONE);
            }

            Condition.sleep(new Random().nextInt(500));

            if (!GreenDragonKiller.foodSelection.equals("None")) {
                ctx.bank.withdraw(SelfService.getFoodIDFromBank(ctx), 22);
                //bank.withdraw(SelfService.getFoodIDFromBank(players.ctx), Bank.Amount.FIVE);
            }
            ctx.bank.close();

        }
            wearGlory();
    }

    private boolean wearingGlory(){
        ItemQuery<Item> gloryQuery = ctx.equipment.select().name(gloryPattern);

        if (gloryQuery.isEmpty()){
            return false;
        } else {
            return true;
        }
    }

    private boolean prayersInInventory(){
        ctx.game.tab(Game.Tab.INVENTORY);

        ItemQuery<Item> prayersQuery = ctx.inventory.select().name(prayerPatten);

        return prayersQuery.isEmpty();
    }

    private boolean withdrawPrayers(){
        ItemQuery<Item> prayersQuery = ctx.bank.select().name(prayerPatten);
        return ctx.bank.withdraw(prayersQuery.poll(), 1);
    }

    private boolean withdrawGames(){
        ItemQuery<Item> gamesQuery = ctx.bank.select().name(gamesPattern);
        return ctx.bank.withdraw(gamesQuery.poll(), 1);

    }


    private boolean haveSupersInBank(){

        ItemQuery<Item> supersQuery = ctx.bank.select().name(supersPatten);

        if (supersQuery.isEmpty()){
            return false;
        } else return true;

    }


    private boolean withdrawGlory(){
        ItemQuery<Item> gloryQuery = ctx.bank.select().name(gloryPattern);
        if (gloryQuery.isEmpty()) {
            return false;
        } else {
            return ctx.bank.withdraw(gloryQuery.poll(), 1);
        }
    }

    private boolean wearGlory(){
        ctx.game.tab(Game.Tab.INVENTORY);
        ItemQuery<Item> gloryInventoryQuery = ctx.inventory.select().name(gloryPattern);

        if (gloryInventoryQuery.isEmpty()){
            return false;

        } else{
            return gloryInventoryQuery.poll().interact("Wear");
        }
    }

    private boolean withdrawSuperAttack(){
        ItemQuery<Item> superAttackQuery = ctx.bank.select().name(supersAttackPatten);

        if(!superAttackQuery.isEmpty()){
            return ctx.bank.withdraw(superAttackQuery.poll(), 1);
        } else return false;
    }

    private boolean withdrawSuperDefence(){
        ItemQuery<Item> superDefenceQuery = ctx.bank.select().name(superDefencePattern);

        if (!superDefenceQuery.isEmpty()){
            return ctx.bank.withdraw(superDefenceQuery.poll(), 1);
        } else return false;
    }


    private boolean withdrawSuperStrength(){

        ItemQuery<Item> superStrengthQuery = ctx.bank.select().name(superStrengthPatten);

        if(!superStrengthQuery.isEmpty()){
            return ctx.bank.withdraw(superStrengthQuery.poll(), 1);
        } else return false;
    }

    private boolean supersInInventory(){
        ctx.game.tab(Game.Tab.INVENTORY);

        ItemQuery<Item> supersQuery = ctx.inventory.select().name(supersPatten);

        if (supersQuery.isEmpty()){
            return false;
        } else return true;
    }

    private boolean lootBagInBank(){
        ItemQuery<Item> lootQuery = ctx.bank.select().id(Items.LOOT_BAG);
        return lootQuery.isEmpty();
    }

    private boolean lootBagInInventory(){
        ItemQuery<Item> lootQuery = ctx.inventory.select().id(Items.LOOT_BAG);
        return lootQuery.isEmpty();
    }
}
