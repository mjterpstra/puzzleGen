package edu.umass.mterpstra;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Window extends JFrame
{
	int length = 12;
	int cubeSize = 1;

	int maxPieces = 15;
	int minPieces = 10;
	
	String filename;

	public static void main(String[] args)
	{
		new Window();
	}

	public Window()
	{
		Color lightblue = new Color(109, 229, 242);
		getContentPane().setBackground(new Color(115, 108, 240));
		JPanel allPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		allPanel.setOpaque(false);

		JPanel paramPanel = new JPanel(new GridLayout(1, 0, 10, 10));
		paramPanel.setOpaque(false);

		JPanel minPanel = new JPanel(new GridLayout(2, 0));
		JLabel minLabel = new JLabel("Minimum pieces");
		minLabel.setForeground(lightblue);
		JTextField minText = new JTextField();
		minText.setText("10");
		minText.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String min = minText.getText();
				minPieces = Integer.parseInt(min);
			}
		});
		minPanel.add(minLabel);
		minPanel.add(minText);
		minPanel.setOpaque(false);

		JPanel maxPanel = new JPanel(new GridLayout(2, 0));
		JLabel maxLabel = new JLabel("Maximum pieces");
		maxLabel.setForeground(lightblue);
		JTextField maxText = new JTextField();
		maxText.setText("15");
		maxText.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String max = maxText.getText();
				maxPieces = Integer.parseInt(max);
			}
		});
		maxPanel.add(maxLabel);
		maxPanel.add(maxText);
		maxPanel.setOpaque(false);

		JPanel sizePanel = new JPanel(new GridLayout(2, 0));
		JLabel sizeLabel = new JLabel("Cube size");
		JTextField sizeText = new JTextField();
		sizeText.setText("12");
		sizeLabel.setForeground(lightblue);
		sizeText.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String size = minText.getText();
				length = Integer.parseInt(size);
			}
		});
		sizePanel.add(sizeLabel);
		sizePanel.add(sizeText);
		sizePanel.setOpaque(false);

		JPanel filenamePanel = new JPanel(new GridLayout(2, 0));
		JLabel filenameLabel = new JLabel("No file selected");
		filenameLabel.setForeground(lightblue);
		JButton filenameButton = new JButton("Select File");
		filenameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser jfc = new JFileChooser();
				int val = jfc.showSaveDialog(Window.this);
				if(val == JFileChooser.APPROVE_OPTION)
				{
					filename = jfc.getCurrentDirectory().toString() + "/" + jfc.getSelectedFile().getName();
					filenameLabel.setText(jfc.getSelectedFile().getName());
				}
			}
		});
		filenamePanel.add(filenameLabel);
		filenamePanel.add(filenameButton);
		filenamePanel.setOpaque(false);

		JPanel genPanel = new JPanel();
		JButton genButton = new JButton("GENERATE");
		genButton.addActionListener(new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent e)
			{
				if(minPieces <= 0 || maxPieces <= 0)
				{
					JOptionPane.showMessageDialog(null, "ERROR: INVALID PARAMETERS");
				}
				else if(minPieces > maxPieces)
				{	
					JOptionPane.showMessageDialog(null, "ERROR: INVALID PARAMETERS");
				}
				else if(filename == null || filename.equals(""))
				{
					JOptionPane.showMessageDialog(null, "ERROR: INVALID FILEPATH");
				}
				else
				{
					Puzzle.generate(maxPieces, minPieces, length, filename);
					JOptionPane.showMessageDialog(null, "Generated!");
				}
			}
		});
		genPanel.add(genButton);
		genPanel.setOpaque(false);

		paramPanel.add(minPanel);
		paramPanel.add(maxPanel);
		paramPanel.add(sizePanel);
		paramPanel.add(filenamePanel);

		c.insets = new Insets(10, 10, 10, 10);
		c.gridy = 0;
		allPanel.add(paramPanel, c);
		c.gridy = 1;
		allPanel.add(genPanel, c);

		add(allPanel);
		setLocationRelativeTo(null);
		
		setTitle("3D Puzzle Generator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		pack();
		setVisible(true);
	}
}
