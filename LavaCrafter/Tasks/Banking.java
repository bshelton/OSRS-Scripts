package scripts.LavaCrafter.Tasks;

import org.powerbot.script.rt4.*;
import org.powerbot.script.rt4.Equipment.Slot;

import scripts.LavaCrafter.Utils.Items;
import scripts.LavaCrafter.Utils.Areas;

public class Banking extends Task {

    private boolean withdrewRing = false;

    public Banking(ClientContext ctx){
        super(ctx);
    }

    @Override
    public boolean activate() {
        return Areas.CASTLE_WARS_AREA.getCentralTile().distanceTo(ctx.players.local()) < 15;
    }

    @Override
    public void execute() {

        if (oneCharge()){
            ctx.players.ctx.game.tab(Game.Tab.EQUIPMENT);
            ctx.equipment.itemAt(Slot.RING).interact("Remove");
            ctx.bank.open();
            ctx.bank.depositInventory();
            ctx.bank.close();
        }

        if (!wearingBindingNecklace()){
            ctx.bank.open();
            ctx.bank.withdraw(Items.BINDING_NECKLACE_ID, Bank.Amount.ONE);
            ctx.bank.close();

            ItemQuery<Item> bindingNecklace = ctx.inventory.select().id(Items.BINDING_NECKLACE_ID);
            Item necklace = bindingNecklace.poll();
            ctx.game.tab(Game.Tab.INVENTORY);
            necklace.interact("Wear");
        }

        if (!wearingFireTiara()){
            ctx.bank.open();
            ctx.bank.withdraw(Items.FIRE_TIARA_ID, Bank.Amount.ONE);
            ctx.bank.close();

            ItemQuery<Item> fireTiara = ctx.inventory.select().id(Items.FIRE_TIARA_ID);
            Item tiara = fireTiara.poll();
            ctx.game.tab(Game.Tab.INVENTORY);
            tiara.interact("Wear");
        }

        if (!wearingRing()) {
            if (!ctx.bank.inViewport())
                ctx.camera.turnTo(ctx.bank.nearest());

            if (!ctx.bank.opened()) {
                ctx.bank.open();
                ctx.bank.depositInventory();

                if (!withdrewRing){

                    String dueling = "";

                    for (int i = 2; i <= 8; i++) {
                        if (withdrewRing)
                            break;

                        dueling = "Ring of dueling(" + i + ")";

                        for (Item item : ctx.bank.select().name(dueling)) {
                            if (item.name().equals(dueling)) {
                                ctx.bank.withdraw(item.id(), Bank.Amount.ONE);
                                withdrewRing = true;
                            }
                        }
                    }
                    withdrewRing = false;
                }

                ctx.bank.close();
                ctx.game.tab(Game.Tab.INVENTORY);
                for (Item item: ctx.inventory.items()) {
                    if (item.name().contains("dueling")){
                        item.interact("Wear");
                    }
                }

                ctx.game.tab(Game.Tab.EQUIPMENT);
                ctx.bank.open();

                if (ctx.bank.withdraw(Items.EARTH_TALISMAN_ID, Bank.Amount.ONE) && ctx.bank.withdraw(Items.EARTH_RUNE_ID, Bank.Amount.ALL) ) {
                    ctx.bank.withdraw(Items.PURE_ESS_ID, Bank.Amount.ALL);
                }
            }
        } else {
            if (!ctx.bank.inViewport())
                ctx.camera.turnTo(ctx.bank.nearest());
            if (!ctx.bank.opened()) {
                ctx.bank.open();
                ctx.bank.depositInventory();

                if (ctx.bank.withdraw(Items.EARTH_TALISMAN_ID, Bank.Amount.ONE) && ctx.bank.withdraw(Items.EARTH_RUNE_ID, Bank.Amount.ALL) ){
                    ctx.bank.withdraw(Items.PURE_ESS_ID, Bank.Amount.ALL);
                }
                ctx.bank.close();
            }
            ctx.bank.close();
        }
        ctx.bank.close();
    }

    private boolean wearingBindingNecklace(){
        ctx.game.tab(Game.Tab.EQUIPMENT);
        return ctx.equipment.itemAt(Slot.NECK).id() == Items.BINDING_NECKLACE_ID;
    }

    private boolean wearingRing(){
       ctx.game.tab(Game.Tab.EQUIPMENT);
       String dueling = "";

        int i = 0;
        while (!ctx.players.local().ctx.equipment.itemAt(Slot.RING).name().equals(dueling)){
            i++;
            dueling = "Ring of dueling(" + i + ")";

            if (ctx.players.local().ctx.equipment.itemAt(Slot.RING).name().equals(dueling)) {
                return true;
            }
        }
        return false;
    }

    private boolean wearingFireTiara(){
        ctx.game.tab(Game.Tab.EQUIPMENT);
        return ctx.equipment.itemAt(Slot.HEAD).id() == Items.FIRE_TIARA_ID;
    }

    private boolean oneCharge(){
        ctx.game.tab(Game.Tab.EQUIPMENT);
        return ctx.players.local().ctx.equipment.itemAt(Slot.RING).name().equals("Ring of dueling(1)");
    }

}