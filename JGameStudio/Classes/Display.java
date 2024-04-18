package Classes;


import java.util.ArrayList;

import javax.swing.*;

public class Display extends JFrame {
    
    public Signal<Void, Void> NewBox = new Signal<>();

    private ArrayList<JComponent> components = new ArrayList<>();


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

        staticConstruct();
    }

    private void showComponents(){
        for (JComponent jc : components){
            this.add(jc);
        }
    }

    private void initComponents(){
        JButton newInstanceButton = new JButton();
        newInstanceButton.setBounds(200,100, 100, 50);
        components.add(newInstanceButton);

        showComponents();
    }


    
    
}
