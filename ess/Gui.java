package scripts.ess;

import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui extends JFrame {
    private JPanel essPanel;
    private JButton okButton;
    private JTextField charTextField;
    private JLabel modeLabel;
    private JRadioButton runnerButton;
    private JRadioButton crafterRadioButton;
    private JLabel charLabel;
    private ButtonGroup buttonGroup;


    public Gui(final ClientContext ctx) {

        setTitle("EssRunner");
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 325, 135);
        setContentPane(essPanel);

        charTextField.setVisible(false);
        charLabel.setVisible(false);


        buttonGroup = new ButtonGroup();
        buttonGroup.add(runnerButton);
        buttonGroup.add(crafterRadioButton);

        essPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "EssRunner"));

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("OK Button ");

                if (ctx.game.clientState() != Constants.GAME_LOADED){
                    JOptionPane.showMessageDialog(null, "Please start logged in.", "", JOptionPane.WARNING_MESSAGE);
                    ctx.controller.stop();
                    dispose();

                } else {
                    if (!crafterRadioButton.isSelected() && ! runnerButton.isSelected()){
                        JOptionPane.showMessageDialog(null, "You must choose a mode.", "", JOptionPane.WARNING_MESSAGE);
                    }

                    if (charTextField.getText().isEmpty() && runnerButton.isSelected()){
                        JOptionPane.showMessageDialog(null, "Please fill in a character name to trade.", "", JOptionPane.WARNING_MESSAGE);
                    }

                    if (!charTextField.getText().isEmpty()){
                        EssRunner.crafterUsername = charTextField.getText();
                        EssRunner.mode = "runner";
                        EssRunner.haveRunStart = true;
                        setVisible(false);
                    }

                    if (crafterRadioButton.isSelected()){
                        EssRunner.mode = "crafter";
                        EssRunner.haveRunStart = true;
                        setVisible(false);
                    }

                }
            }
        });

        runnerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (runnerButton.isSelected()){
                    System.out.println("Runner mode selected.");
                    charLabel.setVisible(true);
                    charTextField.setVisible(true);
                }
            }
        });

        crafterRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (crafterRadioButton.isSelected()){
                    charTextField.setVisible(false);
                    charLabel.setVisible(false);
                }
            }
        });

    }

}