package gdkiller.Tasks;

import gdkiller.GreenDragonKiller;
import org.powerbot.script.rt4.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gdkiller.utils.*;

public class Banking extends Task{

    private String gloryRegex = ".*glory.*";
    private String gamesRegex = ".*Games.*";

    private String supersRegex = ".*Super.*";
    private String prayerRegex = ".*Prayer.*";
    private Pattern prayerPatten = Pattern.compile(prayerRegex);

    private Pattern supersPatten = Pattern.compile(supersRegex);

    private String superAttackRegex = ".*Super attack.*";
    private String superDefenseRegex = ".*Super defense.*";
    private String superStrengthRegex = ".*Super strength.*";
    private Pattern supersAttackPatten = Pattern.compile(superAttackRegex);
    private Pattern superDefensePattern = Pattern.compile(superDefenseRegex);
    private Pattern superStrengthPatten = Pattern.compile(superStrengthRegex);
    private Pattern gloryPattern = Pattern.compile(gloryRegex);
    private Pattern gamesPattern = Pattern.compile(gamesRegex);


    public Banking(ClientContext ctx){
        super(ctx);
    }

    @Override
    public boolean activate(){
        return  Areas.EDGEVILLE.getCentralTile().distanceTo(players.local()) < 7
                && SelfService.idling(players.ctx)
                && !players.ctx.chat.chatting()
                && !bank.opened();
    }

    @Override
    public void execute(){

        if (!bank.inViewport()){
            camera.turnTo(bank.nearest());
        }

        if (!bank.opened()) {
            bank.open();
            bank.depositInventory();

            withdrawGames();

//            if (haveGamesNecklaceinBank() && SelfService.haveGamesNecklaceinInventory(players.ctx)) {
//                withdrawGames();
//            }

            withdrawGlory();

            if (GreenDragonKiller.usesuperPotion && haveSupersInBank() && !supersInInventory()) {
                withdrawSupers();
            }

            if (GreenDragonKiller.usePrayerPotions && havePrayersInBank() && !prayersInInventory()) {
                withdrawPrayers();
            }

            if (GreenDragonKiller.useLootBag && lootBagInBank() && !lootBagInInventory()) {
                bank.withdraw(Items.LOOT_BAG, Bank.Amount.ONE);
            }

            if (!GreenDragonKiller.foodSelection.equals("None")) {
                bank.withdraw(SelfService.getFoodIDFromBank(players.ctx), 22);
                //bank.withdraw(SelfService.getFoodIDFromBank(players.ctx), Bank.Amount.FIVE);
            }
            bank.close();

        }

    }

    private boolean havePrayersInBank(){

        if(!bank.inViewport()){
            camera.turnTo(bank.nearest());
        }

        if (!bank.opened()) {
            bank.open();
        }
        ItemQuery<Item> prayerQuery = players.ctx.bank.select().name(prayerPatten);


        if (bank.opened()){
            bank.close();
        }

        return prayerQuery.isEmpty();

    }

    private boolean prayersInInventory(){
        players.ctx.game.tab(Game.Tab.INVENTORY);

        ItemQuery<Item> prayersQuery = players.ctx.inventory.select().name(prayerPatten);

        return prayersQuery.isEmpty();
    }

    private boolean withdrawPrayers(){
        ItemQuery<Item> prayersQuery = players.ctx.bank.select().name(prayerPatten);
        return bank.withdraw(prayersQuery.poll(), 1);
    }

    private boolean withdrawGames(){
        ItemQuery<Item> gamesQuery = players.ctx.bank.select().name(gamesPattern);
        return bank.withdraw(gamesQuery.poll(), 1);

    }

    private boolean haveGamesNecklaceinBank(){
        if(!bank.inViewport()){
            camera.turnTo(bank.nearest());
        }

        if (!bank.opened()) {
            bank.open();
        }

        ItemQuery<Item> gamesQuery = players.ctx.bank.select().name(gamesPattern);

        if (!gamesQuery.isEmpty()){
            return true;
        }

        return false;
    }

    private boolean haveSupersInBank(){

        if(!bank.inViewport()){
            camera.turnTo(bank.nearest());
        }

        if (!bank.opened()){
            bank.open();
        }
        ItemQuery<Item> supersQuery = players.ctx.bank.select().name(supersPatten);

        if (bank.opened()){
            bank.close();
        }

        return supersQuery.isEmpty();

    }

    private boolean haveGloryinBank(){

        if(!bank.inViewport()){
            camera.turnTo(bank.nearest());
        }

        if (!bank.opened()) {
            bank.open();
        }

        ItemQuery<Item> gloryQuery = players.ctx.bank.select().name(gloryPattern);

        if (bank.opened()){
            bank.close();
        }

        return !gloryQuery.isEmpty();

    }

    private boolean withdrawGlory(){
        ItemQuery<Item> gloryQuery = players.ctx.bank.select().name(gloryPattern);
        if (gloryQuery.isEmpty()) {
            System.out.println("Glory Query has no items");
            return false;
        } else {
            return bank.withdraw(gloryQuery.poll(), 1);
        }
    }

    private boolean wearGlory(){
        players.ctx.game.tab(Game.Tab.INVENTORY);
        ItemQuery<Item> gloryInventoryQuery = players.ctx.inventory.select().name(gloryPattern);

        if (gloryInventoryQuery.isEmpty()){
            return false;

        } else{
            return gloryInventoryQuery.poll().interact("Wear");
        }
    }

    private void withdrawSupers(){
        ItemQuery<Item> superAttackQuery = players.ctx.bank.select().name(supersAttackPatten);
        ItemQuery<Item> superDefenseQuery = players.ctx.bank.select().name(superDefensePattern);
        ItemQuery<Item> superStrengthQuery = players.ctx.bank.select().name(superStrengthPatten);


        //Withdraw one of each kind of super pot that is in the bank.

        if(!superAttackQuery.isEmpty()){
            bank.withdraw(superAttackQuery.poll(), 1);
        }

        if(!superDefenseQuery.isEmpty()){
            bank.withdraw(superDefenseQuery.poll(), 1);
        }

        if(!superStrengthQuery.isEmpty()){
            bank.withdraw(superStrengthQuery.poll(), 1);
        }
    }

    private boolean supersInInventory(){
        players.ctx.game.tab(Game.Tab.INVENTORY);

        ItemQuery<Item> supersQuery = players.ctx.inventory.select().name(supersPatten);

        return supersQuery.isEmpty();

    }

    private boolean lootBagInBank(){
        ItemQuery<Item> lootQuery = players.ctx.bank.select().id(Items.LOOT_BAG);
        return lootQuery.isEmpty();
    }

    private boolean lootBagInInventory(){
        ItemQuery<Item> lootQuery = players.ctx.inventory.select().id(Items.LOOT_BAG);
        return lootQuery.isEmpty();
    }
}
