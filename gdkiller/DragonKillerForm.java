package gdkiller;

import org.powerbot.script.rt4.ClientContext;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DragonKillerForm extends JFrame{
    private JCheckBox supersCheckbox;
    private JComboBox foodBox;
    private JCheckBox prayerCheckBox;
    private JButton startButton;
    private JPanel mainPanel;
    private JCheckBox lootbagCheckBox;

    public DragonKillerForm(final ClientContext ctx){

        createUIComponents();
        setTitle("Green Dragon Killer");
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 325, 135);

        setContentPane(mainPanel);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Start button pressed");

                if (supersCheckbox.isSelected()){
                    GreenDragonKiller.usesuperPotion = true;
                    GreenDragonKiller.haveRunStart = true;
                    setVisible(false);
                }

                if (prayerCheckBox.isSelected()){
                    GreenDragonKiller.usePrayerPotions = true;
                    GreenDragonKiller.haveRunStart = true;
                    setVisible(false);
                }

                if (lootbagCheckBox.isSelected()){
                    GreenDragonKiller.useLootBag = true;
                    GreenDragonKiller.haveRunStart = true;
                    setVisible(false);
                }
                if (foodBox.getSelectedItem().equals("None")){
                    JOptionPane.showMessageDialog(null, "You must select a food item.", "", JOptionPane.WARNING_MESSAGE);
                }

                GreenDragonKiller.foodSelection = foodBox.getSelectedItem().toString();


            }
        });
    }

    private void createUIComponents() {
        foodBox = new JComboBox(GreenDragonKiller.foodChoices);
    }
}
