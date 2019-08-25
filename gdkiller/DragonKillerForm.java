package gdkiller;

import org.powerbot.script.rt4.ClientContext;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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

                if (GreenDragonKiller.foodSelection.equals("None")){
                    JOptionPane.showMessageDialog(null, "You must select a food item.", "", JOptionPane.WARNING_MESSAGE);
                } else{
                    GreenDragonKiller.haveRunStart = true;
                    setVisible(false);
                }
            }
        });

    }

    private void createUIComponents() {
        foodBox = new JComboBox(GreenDragonKiller.foodChoices);

        foodBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    GreenDragonKiller.foodSelection = e.getItem().toString();
                }
            }
        });
    }
}
