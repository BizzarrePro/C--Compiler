package team.weird.texteditor.implement;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Find extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 8674569541853793419L;
    private JPanel contentPane;
    private JTextField searchTextField;
    private JTextField replaceTextField;
    private JRadioButton forward;
    private JRadioButton backward;
    private JCheckBox caseSen;
    private JCheckBox regex;
    private boolean finishedFinding = true;
    private Matcher matcher;
    private JTabbedPane tab;
    /**
     * Create the frame.
     */
    public Find(Frame frame, JTabbedPane tab) {
    	this.tab = tab;
        setResizable(false);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setAlwaysOnTop(true);
        setTitle("Find/Replace");
        Point location = frame.getLocation();
        setBounds((int)(location.getX()+600), (int)location.getY(), 220, frame.getHeight());
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.NORTH);
        
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 0 };
        gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0 };
        gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0,
                Double.MIN_VALUE };
        gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
        		Double.MIN_VALUE };
        panel.setLayout(gbl_panel);

        GridBagConstraints gbc_button = new GridBagConstraints();
        gbc_button.anchor = GridBagConstraints.NORTHWEST;
        gbc_button.insets = new Insets(0, 0, 5, 5);
        gbc_button.gridx = 0;
        gbc_button.gridy = 0;

        GridBagConstraints gbc_fileField = new GridBagConstraints();
        gbc_fileField.gridwidth = 2;
        gbc_fileField.insets = new Insets(0, 0, 5, 0);
        gbc_fileField.fill = GridBagConstraints.HORIZONTAL;
        gbc_fileField.gridx = 1;
        gbc_fileField.gridy = 0;

        JLabel label = new JLabel("Find: ");
        GridBagConstraints gbc_label = new GridBagConstraints();
        gbc_label.anchor = GridBagConstraints.WEST;
        gbc_label.insets = new Insets(0, 0, 5, 5);
        gbc_label.gridx = 0;
        gbc_label.gridy = 1;
        panel.add(label, gbc_label);

        searchTextField = new JTextField();
        searchTextField.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				finishedFinding = true;
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				finishedFinding = true;
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
        GridBagConstraints gbc_searchTextField = new GridBagConstraints();
        gbc_searchTextField.gridwidth = 3;
        gbc_searchTextField.insets = new Insets(0, 0, 5, 0);
        gbc_searchTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_searchTextField.gridx = 1;
        gbc_searchTextField.gridy = 1;
        panel.add(searchTextField, gbc_searchTextField);
        searchTextField.setColumns(10);

        JLabel label_1 = new JLabel("Replace with: ");
        GridBagConstraints gbc_label_1 = new GridBagConstraints();
        gbc_label_1.anchor = GridBagConstraints.WEST;
        gbc_label_1.insets = new Insets(0, 0, 5, 5);
        gbc_label_1.gridx = 0;
        gbc_label_1.gridy = 2;
        panel.add(label_1, gbc_label_1);

        replaceTextField = new JTextField();
        GridBagConstraints gbc_replaceTextField = new GridBagConstraints();
        gbc_replaceTextField.gridwidth = 3;
        gbc_replaceTextField.insets = new Insets(0, 0, 5, 0);
        gbc_replaceTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_replaceTextField.gridx = 1;
        gbc_replaceTextField.gridy = 2;
        panel.add(replaceTextField, gbc_replaceTextField);
        replaceTextField.setColumns(10);
        
        GridBagConstraints gbc_panel_3 = new GridBagConstraints();
        JLabel direct = new JLabel("Direction");
        gbc_panel_3.gridwidth = 1;
        gbc_panel_3.gridheight = 1;
        gbc_panel_3.gridx = 0;
        gbc_panel_3.gridy = 3;
        gbc_panel_3.anchor = GridBagConstraints.WEST;
        gbc_panel_3.insets =  new Insets(0, 5, 0, 0);
        panel.add(direct, gbc_panel_3);
       
        GridBagConstraints gbc_panel_2 = new GridBagConstraints();
        gbc_panel_2.gridwidth = 4;
        gbc_panel_2.gridheight = 1;
        gbc_panel_2.gridx = 0;
        gbc_panel_2.gridy = 4;
        gbc_panel_2.fill = GridBagConstraints.HORIZONTAL;
        gbc_panel_2.insets =  new Insets(0, 5, 0, 5);
        JSeparator sep1 = new JSeparator();
        sep1.setOrientation(JSeparator.HORIZONTAL);
        panel.add(sep1, gbc_panel_2);
        
        JPanel panel_0 = new JPanel();
        GridBagConstraints gbc_panel_0 = new GridBagConstraints();
        gbc_panel_0.gridwidth = 0;
        gbc_panel_0.anchor = GridBagConstraints.CENTER;
        gbc_panel_0.gridx = 0;
        gbc_panel_0.gridy = 5;
        forward = new JRadioButton("Forward", true);
        backward = new JRadioButton("Backward", false);
        panel_0.add(forward);
        panel_0.add(backward);
        ButtonGroup direction = new ButtonGroup();
        direction.add(forward);
        direction.add(backward);
        panel.add(panel_0, gbc_panel_0);
        
        GridBagConstraints gbc_panel_4 = new GridBagConstraints();
        JPanel panel_4 = new JPanel();
        JLabel option = new JLabel("Option");
        gbc_panel_4.gridwidth = 1;
        gbc_panel_4.gridheight = 1;
        gbc_panel_4.gridx = 0;
        gbc_panel_4.gridy = 6;
        gbc_panel_4.anchor = GridBagConstraints.SOUTHWEST;
        panel_4.add(option);
        panel.add(panel_4, gbc_panel_4);
        
        GridBagConstraints gbc_panel_5 = new GridBagConstraints();
        gbc_panel_5.gridwidth = 4;
        gbc_panel_5.gridheight = 1;
        gbc_panel_5.gridx = 0;
        gbc_panel_5.gridy = 7;
        gbc_panel_5.fill = GridBagConstraints.HORIZONTAL;
        gbc_panel_5.insets =  new Insets(0, 5, 0, 5);
        JSeparator sep2 = new JSeparator();
        sep2.setOrientation(JSeparator.HORIZONTAL);
        panel.add(sep2, gbc_panel_5);
        
        JPanel panel_2 = new JPanel();
        GridBagConstraints gbc_panel_6 = new GridBagConstraints();
        gbc_panel_6.gridwidth = 0;
        gbc_panel_6.gridheight = 1;
        gbc_panel_6.anchor = GridBagConstraints.CENTER;
        gbc_panel_6.gridx = 0;
        gbc_panel_6.gridy = 8;
        caseSen = new JCheckBox("Case Sensitive", true);
        regex = new JCheckBox("Regex", false);
        panel_2.add(caseSen);
        panel_2.add(regex);
        ButtonGroup direction1 = new ButtonGroup();
        direction1.add(caseSen);
        direction1.add(regex);
        panel.add(panel_2, gbc_panel_6);
        
        JPanel panel_1 = new JPanel();
        GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.gridwidth = 4;
        gbc_panel_1.fill = GridBagConstraints.SOUTH;
        gbc_panel_1.gridx = 0;
        gbc_panel_1.gridy = 9;
        panel.add(panel_1, gbc_panel_1);

        JButton replaceButton = new JButton("Replace");
        replaceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                do_replaceAllButton_actionPerformed(e);
            }
        });
        
        JButton findButton = new JButton("Find");
        findButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                do_findButton_actionPerformed(e);
            }
        });
        panel_1.add(findButton);
        panel_1.add(replaceButton);
    }

    protected void do_replaceAllButton_actionPerformed(ActionEvent event) {
    	String input = searchTextField.getText();
    	JTextArea text = (JTextArea) ((JScrollPane) tab.getSelectedComponent()).getViewport().getView();
    	if(input != null){
    		String replaceText = replaceTextField.getText();
    		StringBuilder pattern = new StringBuilder();
        	if(!caseSen.isSelected())
        		pattern.append("(?i)");
        	pattern.append(input);
        	while(replace(pattern.toString(), replaceText, text));
    	}
    }

    private boolean replace(String pattern, String replaceText, JTextArea text) {
    	matcher = Pattern.compile(pattern).matcher(text.getText());
    	if(matcher.find()){
    		int selectionStart = matcher.start();
    		int selectionEnd = matcher.end();
    		text.moveCaretPosition(matcher.start());
    		text.select(selectionStart, selectionEnd);
    		text.replaceSelection(replaceText);
    		return true;
    	}
    	return false;
	}

	protected void do_findButton_actionPerformed(ActionEvent e) {
        String input = searchTextField.getText();
        JTextArea text = (JTextArea) ((JScrollPane) tab.getSelectedComponent()).getViewport().getView();
        if(input != null){
        	StringBuilder pattern = new StringBuilder();
        	if(!caseSen.isSelected())
        		pattern.append("(?i)");
        	pattern.append(input);
        	find(pattern.toString(), text);
        }	
    }
    private void find(String pattern, JTextArea text) {
		if (!finishedFinding) {
			if (matcher.find()) {
				int selectionStart = matcher.start();
				int selectionEnd = matcher.end();
				text.moveCaretPosition(matcher.start());
				text.select(selectionStart, selectionEnd);
			} else {
				finishedFinding = true;
				JOptionPane.showMessageDialog(this, "You have reached the end of the file", "End of file",
						JOptionPane.INFORMATION_MESSAGE);
				// closeDialog();
			}
		} else {
			matcher = Pattern.compile(pattern).matcher(text.getText());
			finishedFinding = false;
			find(pattern, text);
		}
}
}