package LavaCrafter.lavaruneCrafter.Tasks;

import LavaCrafter.lavaruneCrafter.utils.Items;
import LavaCrafter.lavaruneCrafter.utils.SelfService;
import org.powerbot.script.rt4.Bank;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Equipment.Slot;
import org.powerbot.script.rt4.Game;
import org.powerbot.script.rt4.Item;

import javax.swing.*;

import static LavaCrafter.lavaruneCrafter.utils.Areas.CASTLE_WARS_AREA;

import static LavaCrafter.lavaruneCrafter.utils.Items.EARTH_TALISMAN_ID;
import static LavaCrafter.lavaruneCrafter.utils.Items.EARTH_RUNE_ID;
import static LavaCrafter.lavaruneCrafter.utils.Items.PURE_ESS_ID;



public class Banking extends Task {

    public Banking(ClientContext ctx){
        super(ctx);
    }

    @Override
    public boolean activate() {
        return CASTLE_WARS_AREA.getCentralTile().distanceTo(ctx.players.local()) < 15;
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
            ctx.bank.withdraw(Items.BINDING_NECKLACE, Bank.Amount.ONE);
        }

        if (!wearingRing()) {
            if (!ctx.bank.inViewport())
                ctx.camera.turnTo(ctx.bank.nearest());

            if (!ctx.bank.opened()) {
                ctx.bank.open();
                ctx.bank.depositInventory();

                String dueling = "";
                for (int i = 2; i <= 8; i++) {
                    dueling = "Ring of dueling(" + i + ")";

                    for (Item item : ctx.bank.select().name(dueling)) {
                        if (item.name().equals(dueling)){
                            ctx.bank.withdraw(item.id(), Bank.Amount.ONE);
                        }
                    }
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
                if (ctx.bank.withdraw(EARTH_TALISMAN_ID, Bank.Amount.ONE) && ctx.bank.withdraw(EARTH_RUNE_ID, Bank.Amount.ALL) ) {
                    ctx.bank.withdraw(PURE_ESS_ID, Bank.Amount.ALL);
                }

            }

        } else {
            if (!ctx.bank.inViewport())
                ctx.camera.turnTo(ctx.bank.nearest());
            if (!ctx.bank.opened()) {
                ctx.bank.open();
                ctx.bank.depositInventory();

                if (ctx.bank.withdraw(EARTH_TALISMAN_ID, Bank.Amount.ONE) && ctx.bank.withdraw(EARTH_RUNE_ID, Bank.Amount.ALL) ){
                    ctx.bank.withdraw(PURE_ESS_ID, Bank.Amount.ALL);
                }
                else {
                    System.out.println("No items to withdraw");
                }

                ctx.bank.close();
            }

            ctx.bank.close();
        }
        ctx.bank.close();

    }

    private boolean wearingBindingNecklace(){

        ctx.game.tab(Game.Tab.EQUIPMENT);
        System.out.println( ctx.equipment.itemAt(Slot.NECK).id() == Items.BINDING_NECKLACE);
        return ctx.equipment.itemAt(Slot.NECK).id() == Items.BINDING_NECKLACE;
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

    private boolean oneCharge(){
        ctx.game.tab(Game.Tab.EQUIPMENT);
        return ctx.players.local().ctx.equipment.itemAt(Slot.RING).name().equals("Ring of dueling(1)");
    }


}