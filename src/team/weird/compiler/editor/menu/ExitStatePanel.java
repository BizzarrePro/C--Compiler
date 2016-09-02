package team.weird.compiler.editor.menu;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicButtonUI;

public class ExitStatePanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final ExitStatePanel INSTANCE = new ExitStatePanel(); 
	private ExitStatePanel(){
		Button exit = new Button();
		BorderLayout bord = new BorderLayout();
		setLayout(bord);
		add(exit, BorderLayout.EAST);
		File file = new File("src");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JLabel lab = new JLabel("<Terminated> [C-MINUS Application] "+file.getAbsolutePath() + " ( "+df.format(new Date())+" )");
		Font f = new Font("Monofur", Font.PLAIN, 11);
		lab.setFont(f);
		
		add(lab, BorderLayout.WEST);
//		FlowLayout flow = new FlowLayout();
//		flow.setVgap(1);
//		setLayout(flow);
	}
	public static ExitStatePanel getInstance(){
		return INSTANCE;
	}
	private class Button extends JButton {  
        public Button() {  
            int size = 17;  
            setPreferredSize(new Dimension(size, size));  
            setToolTipText("�ر�");  
            setUI(new BasicButtonUI());  
            setContentAreaFilled(false);  
            setFocusable(false);  
            setBorder(BorderFactory.createEtchedBorder());  
            setBorderPainted(false);  
            
            //Roll over action
            setRolloverEnabled(true);  
            
            //Mouse event, Draw frame when mouse entered, cancel frame when mouse exited
            addMouseListener(new MouseAdapter() {  
                @Override  
                public void mouseEntered(MouseEvent e) {  
                    Component component = e.getComponent();  
                    if (component instanceof AbstractButton) {  
                        AbstractButton button = (AbstractButton) component;  
                        button.setBorderPainted(true);  
                    }  
                }  
                @Override  
                public void mouseExited(MouseEvent e) {  
                    Component component = e.getComponent();  
                    if (component instanceof AbstractButton) {  
                        AbstractButton button = (AbstractButton) component;  
                        button.setBorderPainted(false);  
                    }  
                }  
            });  
            //Exit tab when exit button was clicked
            addActionListener(new ActionListener() {  
                public void actionPerformed(ActionEvent evt) {  
                	ThrowExceptionPanel.getInstance().setVisible(false);
                	ExitStatePanel.getInstance().setVisible(false);
                }  
            });  
        }  
        @Override  
        public void updateUI() {  
        }  
        @Override  
        protected void paintComponent(Graphics g) {  
            super.paintComponent(g);  
            Graphics2D g2 = (Graphics2D) g.create();  
            //Move an point when mouse was clicked
            if (getModel().isPressed()) {  
                g2.translate(1, 1);  
            }  
            g2.setStroke(new BasicStroke(2));  
            g2.setColor(Color.BLACK);  
            //Button color change to red when mouse was on button
            if (getModel().isRollover()) {  
                g2.setColor(Color.RED);  
            }  
            int delta = 6;  
            g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);  
            g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);  
            g2.dispose();  
        }  
    }     
}

