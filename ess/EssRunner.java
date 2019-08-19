package ess;

import ess.Tasks.*;

import org.powerbot.script.*;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.rt4.ClientContext;


import java.util.ArrayList;
import java.awt.*;


@Script.Manifest(
        name = "ESS RUNNER",
        description = "Basic Script Example",
        properties = "client = 4;"
)

public class EssRunner extends PollingScript<ClientContext> implements PaintListener {

    private ArrayList<Task> crafterTaskList;
    private ArrayList<Task> runnerTaskList;
    public static boolean haveRunStart = false;
    public static String crafterUsername;
    public static String mode;
    private int startingXP = 0;

    public EssRunner(){
        runnerTaskList = new ArrayList<Task>();
        crafterTaskList = new ArrayList<Task>();
    }

    @Override
    public void start() {
        System.out.println("ESS RUNNER Started!");


        crafterTaskList.add(new Banking(ctx));
        crafterTaskList.add(new TeleportToKharid(ctx));
        crafterTaskList.add(new WalkToAltar(ctx));
        crafterTaskList.add(new EnterFire(ctx));
        crafterTaskList.add(new MakeRunes(ctx));
        crafterTaskList.add(new TeleportToCastleWars(ctx));

        runnerTaskList.add(new Banking(ctx));
        runnerTaskList.add(new TeleportToKharid(ctx));
        runnerTaskList.add(new WalkToAltar(ctx));
        runnerTaskList.add(new EnterFire(ctx));
        runnerTaskList.add(new TradeCharacter(ctx));
        runnerTaskList.add(new TeleportToCastleWars(ctx));



        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Gui gui = new Gui(ctx);
                gui.setVisible(true);
            }
        });
    }

    @Override
    public void poll() {

        if (!haveRunStart){
            //Set anything I want at the beginning.
            startingXP = ctx.skills.experience(Constants.SKILLS_RUNECRAFTING);

        } else {

            if (mode.equals("crafter")) {

                for (Task task : crafterTaskList) {
                    if (task.activate())
                        task.execute();
                }
            }

            if (mode.equals("runner")){
                for (Task task : runnerTaskList) {
                    if (task.activate())
                        task.execute();
                }
            }
        }
    }

    @Override
    public void stop() {
        System.out.println("Script Stopped!");
    }

    public void repaint(Graphics graphics){
        int xpGained = ctx.skills.experience(Constants.SKILLS_RUNECRAFTING) - startingXP;

        long milliseconds   = getTotalRuntime();
        long seconds        = (milliseconds / 1000) % 60;
        long minutes        = (milliseconds / (1000 * 60) % 60);
        long hours          = (milliseconds / (1000 * 60 * 60) % 24);

        Graphics2D g = (Graphics2D)graphics;

        g.setColor(new Color(0,0,0,180));
        g.fillRect(0, 0, 250, 100);

        g.setColor(new Color(255,255,255));
        g.drawString("EssRunner", 20, 20);
        g.drawString("Running: " + String.format("%02d:%02d:%02d", hours, minutes, seconds), 20, 40);
        //g.drawString("TradeCharacter: " + CRAFTER_USERNAME, 20, 60);
        g.drawString("Runecrafting xp gained: " + xpGained, 20, 60);
    }

}