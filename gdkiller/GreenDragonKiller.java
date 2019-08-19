package gdkiller;

import gdkiller.Tasks.*;

import org.powerbot.script.*;
import org.powerbot.script.rt4.ClientContext;


import java.util.ArrayList;
import java.awt.*;


@Script.Manifest(
        name = "Green Dragon Killer",
        description = "Kills Green Dragons in wilderness.",
        properties = "client = 4;"
)

public class GreenDragonKiller extends PollingScript<ClientContext> implements PaintListener {

    private ArrayList<Task> taskList;
    public static boolean haveRunStart = false;
    public static String foodSelection;
    public static String[] foodChoices = {"None", "Shark", "Lobster", "Monkfish", "Swordfish"};
    public static boolean usesuperPotion = false;
    public static boolean usePrayerPotions = false;
    public static boolean useLootBag = false;

    public GreenDragonKiller(){
        taskList = new ArrayList<Task>();
    }

    @Override
    public void start() {
        System.out.println("Green Dragon Killer Started!");

        //taskList.add(new Banking(ctx));
        //taskList.add(new TeleportToCorpBeast(ctx));
        //taskList.add(new ExitCorpCave(ctx));
        //taskList.add(new WalkToDrags(ctx));
        //taskList.add(new AttackDrags(ctx));
        //taskList.add(new Loot(ctx));
        taskList.add(new LeaveDrags(ctx));

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                DragonKillerForm gui = new DragonKillerForm(ctx);
                gui.setVisible(true);
            }
        });

    }

    @Override
    public void poll() {

        if (!haveRunStart){
            //Set anything I want at the beginning

       }

        for (Task task : taskList) {
            if (task.activate())
                task.execute();
        }
    }

    @Override
    public void stop() {
        System.out.println("Script Stopped!");
    }

    public void repaint(Graphics graphics){

        long milliseconds   = getTotalRuntime();
        long seconds        = (milliseconds / 1000) % 60;
        long minutes        = (milliseconds / (1000 * 60) % 60);
        long hours          = (milliseconds / (1000 * 60 * 60) % 24);

        Graphics2D g = (Graphics2D)graphics;

        g.setColor(new Color(0,0,0,180));
        g.fillRect(0, 0, 250, 100);

        g.setColor(new Color(255,255,255));
        g.drawString("Green Dragon Killer", 20, 20);
        g.drawString("Running: " + String.format("%02d:%02d:%02d", hours, minutes, seconds), 20, 40);
        //g.drawString("TradeCharacter: " + CRAFTER_USERNAME, 20, 60);
        //g.drawString("Runecrafting xp gained: " + xpGained, 20, 60);
    }

}