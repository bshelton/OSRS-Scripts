package LavaCrafter.Gui;

import LavaCrafter.LavaCrafter;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui extends JFrame {
    private JPanel essPanel;
    private JButton okButton;
    private JTextField charTextField;
    private JLabel charLabel;


    public Gui(final ClientContext ctx) {

        setTitle("LavaCrafter");
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 325, 135);
        setContentPane(essPanel);

        charTextField.setVisible(false);
        charLabel.setVisible(false);

        essPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "LavaCrafter"));

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("OK Button ");

                if (ctx.game.clientState() != Constants.GAME_LOADED){
                    JOptionPane.showMessageDialog(null, "Please start logged in.", "", JOptionPane.WARNING_MESSAGE);
                    ctx.controller.stop();
                    dispose();
                }

                LavaCrafter.crafterUsername = charTextField.getText();
                LavaCrafter.haveRunStart = true;
            }
        });

    }

}