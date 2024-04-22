package Classes;


import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import JGamePackage.JGame.Instances.Instance;
import JGamePackage.JGame.Types.*;
import JGamePackage.lib.ArrayDictionary;

public class Display extends JFrame {
    
    public Signal<Void, Void> Instantiate = new Signal<>();
    public Signal<Instance,Void> RequestDestroy = new Signal<>();

    public Instance currentSelected;

    public Signal<Instance, ArrayDictionary<String, Object>> updateProperties = new Signal<>();

    private ArrayList<JComponent> components = new ArrayList<>();

    GridBagConstraints globalGBC = new GridBagConstraints();

    public JButton newInstanceButton;
    public JButton destroyButton;
    public JTextField positionInput;
    public JTextField sizeInput;
    public JButton applyButton;
    public JTextField colorChoose;
    public JButton exportButton;

    public int width;
    public int height;


    public Display(String title){
        super(title);
    }

    private void staticConstruct(){
        GridBagLayout l = new GridBagLayout();
        this.setLayout(l);

        this.setIconImage(new ImageIcon("JGameStudio\\Assets\\icon.png").getImage());

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
        globalGBC.fill = GridBagConstraints.NONE;
        globalGBC.gridx = 0;
        globalGBC.gridy = 0;

        for (JComponent jc : components){

            this.add(jc, globalGBC);
            globalGBC.gridy++;
        }

        this.repaint();
        this.revalidate();
    }

    private void initComponents(){

        {
            JPanel buttonPanel = new JPanel(new GridBagLayout());
            GridBagConstraints bpGBC = new GridBagConstraints();
            bpGBC.gridx = 0;
            bpGBC.gridy = 0;

            JButton newInstanceButton = new JButton();
            newInstanceButton.setFocusable(false);
            newInstanceButton.setText("Instantiate");
            newInstanceButton.setFont(new Font("Arial", Font.BOLD, 15));
            this.newInstanceButton = newInstanceButton;

            newInstanceButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Instantiate.Fire(null, null);
                }
            });

            JButton destroy = new JButton();
            destroy.setFocusable(false);
            destroy.setText("Delete");
            destroy.setFont(new Font("Arial", Font.BOLD, 15));
            this.destroyButton = destroy;

            destroy.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    RequestDestroy.Fire(currentSelected, null);
                }
            });

            buttonPanel.add(newInstanceButton, bpGBC);
            bpGBC.gridx++;
            buttonPanel.add(destroy, bpGBC);

            bpGBC.gridy += 50;

            JLabel linebreak = new JLabel(" ");
            linebreak.setFont(new Font("Arial", Font.BOLD, 30));

            buttonPanel.add(linebreak, bpGBC);
            

            components.add(buttonPanel);
            

            JLabel propHeader = new JLabel("Properties");
            propHeader.setFont(new Font("Arial", Font.BOLD, 20));
            components.add(propHeader);
        }

        {
            JPanel propertiesContainer = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            
            
    
            JTextField pos = new JTextField(15);
            pos.setHorizontalAlignment(SwingConstants.CENTER);
            propertiesContainer.add(new JLabel("Position: "), gbc);
            propertiesContainer.add(pos, gbc);
            this.positionInput = pos;
    
            gbc.gridy+=2;

            JTextField size = new JTextField(15);
            size.setHorizontalAlignment(SwingConstants.CENTER);
            propertiesContainer.add(new JLabel("Size: "), gbc);
            propertiesContainer.add(size, gbc);
            this.sizeInput = size;
    
            gbc.gridy+=2;

            JTextField color = new JTextField(15);
            color.setHorizontalAlignment(SwingConstants.CENTER);
            propertiesContainer.add(new JLabel("Color: "), gbc);
            propertiesContainer.add(color, gbc);
            this.colorChoose = color;
    
            gbc.gridy+=2;
    
    
            JButton apply = new JButton("Apply");
            apply.setHorizontalAlignment(SwingConstants.CENTER);
            apply.setFocusable(false);

            apply.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    updateProps();
                }
                
            });

            gbc.gridy += 1;
            gbc.gridx += 1;
            propertiesContainer.add(apply, gbc);

            this.applyButton = apply;
    
            components.add(propertiesContainer);

        }
        showComponents();
    }

    public void updateShownProperties(){
        if (currentSelected == null) {
            this.positionInput.setText("");
            this.sizeInput.setText("");
            this.colorChoose.setText("");
            return;
        }
        this.positionInput.setText(currentSelected.Position.toString());
        this.sizeInput.setText(currentSelected.Size.toString());
        this.colorChoose.setText(Util.ColorToString(currentSelected.FillColor));
    }

    public void setCurrentSelected(Instance selected){
        this.currentSelected = selected;
        this.updateShownProperties();
    }

    private void updateProps(){
        ArrayDictionary<String, Object> dict = new ArrayDictionary<>();

        dict.put("Position", Vector2.fromString(this.positionInput.getText()));
        dict.put("Size", Vector2.fromString(this.sizeInput.getText()));

        Color c = null;

        try {
            c = Util.StringToColor(this.colorChoose.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid RGB color", "Error: Invalid Color", JOptionPane.ERROR_MESSAGE );
        }

        dict.put("Color", c != null ? c : currentSelected.FillColor);

        
        updateProperties.Fire(currentSelected, dict);
    }
    
}
class Util {
    public static String ColorToString(Color c){
        return "("+c.getRed()+", "+c.getGreen()+", "+c.getBlue()+")";
    }

    public static Color StringToColor(String s){
        int red;
        int green;
        int blue;

        s = s.replace(" ", "").replace("(", "").replace(")", "");

        System.out.println(s);

        String[] split = s.split(",");

        red = Integer.valueOf(split[0]);
        green = Integer.valueOf(split[1]);
        blue = Integer.valueOf(split[2]);


        return new Color(red, green, blue);
    }
}