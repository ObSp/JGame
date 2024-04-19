package Classes;


import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.*;

import JGamePackage.JGame.Instances.Instance;
import JGamePackage.lib.ArrayDictionary;

public class Display extends JFrame implements ActionListener{
    
    public Signal<Void, Void> Instantiate = new Signal<>();
    public Signal<Void,Void> RequestDestroy = new Signal<>();

    public Instance currentSelected;

    public Signal<Instance, ArrayDictionary<String, Object>> updateProperties = new Signal<>();

    private ArrayList<JComponent> components = new ArrayList<>();

    GridBagConstraints globalGBC = new GridBagConstraints();

    public JButton newInstanceButton;
    public JButton destroyButton;

    public int width;
    public int height;


    public Display(String title){
        super(title);
    }

    private void staticConstruct(){
        GridBagLayout l = new GridBagLayout();
        this.setLayout(l);

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
            if (jc instanceof JButton){
                ((JButton) jc).addActionListener(this);
            }
            this.add(jc, globalGBC);
            globalGBC.gridy++;
        }

        this.repaint();
        this.revalidate();
    }

    private void initComponents(){

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints bpGBC = new GridBagConstraints();
        bpGBC.gridx = 0;
        bpGBC.gridy = 0;

        JButton newInstanceButton = new JButton();
        newInstanceButton.setFocusable(false);
        newInstanceButton.setText("Instantiate");
        newInstanceButton.setFont(new Font("Arial", Font.BOLD, 15));
        this.newInstanceButton = newInstanceButton;

        JButton destroy = new JButton();
        destroy.setFocusable(false);
        destroy.setText("Delete");
        destroy.setFont(new Font("Arial", Font.BOLD, 15));
        this.destroyButton = destroy;

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

        JPanel propertiesContainer = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        

        JTextField size = new JTextField(15);
        size.setHorizontalAlignment(SwingConstants.CENTER);
        propertiesContainer.add(new JLabel("Position: "), gbc);
        propertiesContainer.add(size, gbc);

        gbc.gridy++;


        components.add(propertiesContainer);

        showComponents();
    }

    {
        
    }


    @Override
    public void actionPerformed(ActionEvent e){
        Object src = e.getSource();

        if (src == this.newInstanceButton){
            this.Instantiate.Fire(null, null);
        } else if (src == this.destroyButton){
            this.RequestDestroy.Fire(null, null);
        }
    }
    
}
