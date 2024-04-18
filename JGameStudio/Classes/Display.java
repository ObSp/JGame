package Classes;


import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class Display extends JFrame implements ActionListener{
    
    public Signal<Void, Void> NewBox = new Signal<>();

    private ArrayList<JComponent> components = new ArrayList<>();

    public JButton newInstanceButton;

    private int width;
    @SuppressWarnings("unused")
    private int height;


    public Display(String title){
        super(title);
    }

    private void staticConstruct(){
        this.setLayout(null);

        this.setAlwaysOnTop(true);
        this.setVisible(true);

        initComponents();
    }

    public void init(){
        this.setExtendedState(MAXIMIZED_BOTH);

        staticConstruct();
    }

    public void init(int width, int height){

        this.setSize(width,height);

        this.width = width;
        this.height = height;

        staticConstruct();
    }

    private void showComponents(){
        for (JComponent jc : components){
            if (jc instanceof JButton){
                ((JButton) jc).addActionListener(this);
            }
            this.add(jc);
        }

        this.repaint();
    }

    private void initComponents(){

        JButton newInstanceButton = new JButton();
        newInstanceButton.setFocusable(false);
        newInstanceButton.setBounds(width/2-50, 0, 100,25);
        newInstanceButton.setText("New Box");
        newInstanceButton.setHorizontalAlignment(JButton.RIGHT);
        newInstanceButton.setVerticalAlignment(JButton.CENTER);
        newInstanceButton.setFont(new Font("Arial", Font.BOLD, 15));

        components.add(newInstanceButton);
        this.newInstanceButton = newInstanceButton;

        showComponents();
    }


    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == this.newInstanceButton){
            this.NewBox.Fire(null, null);
        }
    }
    
}
