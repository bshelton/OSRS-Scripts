package LavaCrafter.Tasks;

import LavaCrafter.Tasks.Task;
import org.powerbot.script.rt4.Bank;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Equipment.Slot;
import org.powerbot.script.rt4.Game;
import org.powerbot.script.rt4.Item;

import javax.swing.*;

import static LavaCrafter.utils.Areas.CASTLE_WARS_AREA;
import static LavaCrafter.utils.Items.*;

public class Banking extends Task {

    public Banking(ClientContext ctx){
        super(ctx);
    }

    @Override
    public boolean activate() {
        return CASTLE_WARS_AREA.getCentralTile().distanceTo(players.local()) < 15;
    }

    @Override
    public void execute() {

        if (!wearingRing()) {
            if (!bank.inViewport())
                camera.turnTo(bank.nearest());

            if (!bank.opened()) {
                bank.open();
                bank.depositInventory();

                String dueling = "";
                for (int i = 2; i <= 8; i++) {
                    dueling = "Ring of dueling(" + i + ")";

                    for (Item item : bank.select().name(dueling)) {
                        if (item.name().equals(dueling)){
                            bank.withdraw(item.id(), Bank.Amount.ONE);
                        }
                    }
                }

                bank.close();
                players.ctx.game.tab(Game.Tab.INVENTORY);
                for (Item item: players.ctx.inventory.items()) {

                    if (item.name().contains("dueling")){
                        item.interact("Wear");
                    }
                }

                players.ctx.game.tab(Game.Tab.EQUIPMENT);

                bank.open();
                bank.withdraw(PURE_ESS_ID, Bank.Amount.ALL);
            }

        } else {
            if (!bank.inViewport())
                camera.turnTo(bank.nearest());
            if (!bank.opened()) {
                bank.open();
                bank.depositInventory();

                if (bank.withdraw(EARTH_TALISMAN_ID, Bank.Amount.ONE) && bank.withdraw(EARTH_RUNE_ID, Bank.Amount.ALL) ){
                    bank.withdraw(PURE_ESS_ID, Bank.Amount.ALL);
                }
                else {
                    System.out.println("No earth talisman");
                    JOptionPane.showMessageDialog(null, "Please get Earth Talisman.", "", JOptionPane.WARNING_MESSAGE);
                    players.ctx.controller.stop();
                }

                bank.close();
            }

            bank.close();
        }
        bank.close();

    }

    private boolean wearingRing(){

        players.ctx.game.tab(Game.Tab.EQUIPMENT);

        String dueling = "";

        int i = 0;
        while (!players.local().ctx.equipment.itemAt(Slot.RING).name().equals(dueling)){
            i++;
            dueling = "Ring of dueling(" + i + ")";

            if (players.local().ctx.equipment.itemAt(Slot.RING).name().equals(dueling)) {
                System.out.println("Wearing the ring");
                return true;
            }
        }

        return false;
    }


}