package com.java.window.brifutope;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import jxl.read.biff.BiffException;

import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JFileChooser;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.awt.Color;

public class BriFutoPeWindow {
	
	
	private JFrame frmBrifutope;
	private JTextField textField;
	private JTextField textField_1;
	JxlControler controler;
	String [] fileLocationTable = new String [] {"","","",""};
//	String user = "%username%";
	String oscar = "\\\\oscar\\";

//	 * Launch the application.
	public void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BriFutoPeWindow window = new BriFutoPeWindow();
					window.frmBrifutope.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

//	 * Create the application.

	public BriFutoPeWindow() throws BiffException, IOException {
		initialize();
	}
	
//	 Initialize the contents of the frame.
	
	private void initialize() throws BiffException, IOException {
		frmBrifutope = new JFrame();
		frmBrifutope.setTitle("Brifutope");
		frmBrifutope.setBounds(100, 100, 450, 300);
		frmBrifutope.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBrifutope.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 9));
		textField.setForeground(Color.DARK_GRAY);
		textField.setBounds(180, 69, 209, 20);
		frmBrifutope.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 9));
		textField_1.setForeground(Color.DARK_GRAY);
		textField_1.setBounds(180, 125, 209, 20);
		frmBrifutope.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblChooseCsvFile = new JLabel("Choose csv file destinaton:");
		lblChooseCsvFile.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblChooseCsvFile.setBounds(13, 132, 133, 14);
		frmBrifutope.getContentPane().add(lblChooseCsvFile);
		
		final JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setForeground(new Color(0, 128, 0));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(13, 11, 376, 36);
		frmBrifutope.getContentPane().add(lblNewLabel);
		
		JLabel lblAuthorDariuszuczyski = new JLabel("Author: Dariusz Łuczyński");
		lblAuthorDariuszuczyski.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblAuthorDariuszuczyski.setHorizontalAlignment(SwingConstants.LEFT);
		lblAuthorDariuszuczyski.setBounds(13, 224, 196, 14);
		frmBrifutope.getContentPane().add(lblAuthorDariuszuczyski);
		
		JLabel lblOnABlicense = new JLabel("On a B-License");
		lblOnABlicense.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblOnABlicense.setHorizontalAlignment(SwingConstants.LEFT);
		lblOnABlicense.setBounds(13, 236, 125, 14);
		frmBrifutope.getContentPane().add(lblOnABlicense);		
		
		//COntroler constructor
		controler = new JxlControler();
		
		JButton btnProceed = new JButton("Proceed");
		btnProceed.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				try {
					
					
					// Oscar choosing option
					if(!fileLocationTable[1].isEmpty() && fileLocationTable[3].isEmpty())
					{
						//Clear label
						lblNewLabel.setText("");
						
						// Save at oscar location
						String username = controler.getEnviromentVariable();
						fileLocationTable[2] = oscar + username;
						fileLocationTable[3] = fileLocationTable[1];
				
						//File location setting
						controler.toSetFileLocationTable(fileLocationTable);
						fileLocationTable[3] = controler.removeXls() + ".csv";
						
						// To check if file exists
//						String [] u = controler.toGetFileLocationTable();						
						
						//Copy to clipboard
						StringSelection selection = new StringSelection(fileLocationTable[3]);
						Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
						clipboard.setContents(selection, selection);
						clipboard.setContents(selection, null);
						
						//Set the textfield 
						textField_1.setText(fileLocationTable[2]+"\\"+fileLocationTable[3]);
											
						//Data collecting and saving
						controler.toCollectData();
						controler.setCsvData();

						//File exists is true, does not is false
						if(controler.toFileExistCheck())
						{
							int optionReturn = JOptionPane.showConfirmDialog(null, "There's such file. Overwirie?", "Overwrite Message", JOptionPane.YES_NO_OPTION);
							
							//Option return : YES_OPTION , NO_OPTION , Close_Option
							if(optionReturn == JOptionPane.YES_OPTION)
							{
								// Overwrite is 0, append is 1
								controler.toOverWrite(0);
								controler.toSaveData();
								controler.toClearData();
								
								//Write on label
								lblNewLabel.setText("The csv file has been saved to oscar");
							}
							else
							{
								//Write on label
								lblNewLabel.setText("The csv was not saved to oscar");
							}
							
						}
						else
						{
							// Overwrite is 0, append is 1
							controler.toOverWrite(0);
							controler.toSaveData();
							controler.toClearData();
							
							//Write on label
							lblNewLabel.setText("The csv file has been saved to oscar");
						}
						
						
						// Return empty file location table
						fileLocationTable[2] = "";
						fileLocationTable[3] = "";
						

					}
					else if(fileLocationTable[1].isEmpty() && fileLocationTable[3].isEmpty())
					{
						lblNewLabel.setText("Nothing was chosen");
					}
					else
					{
						//Clear label
						lblNewLabel.setText("");
						
						//Data collection and saving
						controler.toSetFileLocationTable(fileLocationTable);
						
						// To check if file exists					
//						String [] u = controler.toGetFileLocationTable();
						
						//Data collecting and saving
						controler.toCollectData();
						controler.setCsvData();
						
						//File exists is true, does not is false			
						if(controler.toFileExistCheck())
						{
							int optionReturn = JOptionPane.showConfirmDialog(null, "There's such file. Overwirie?", "Overwrite Message", JOptionPane.YES_NO_OPTION);
							//Option return : YES_OPTION , NO_OPTION , Close_Option
							if(optionReturn == JOptionPane.YES_OPTION)
							{
								// Overwrite is 0, append is 1
								controler.toOverWrite(0);
								controler.toSaveData();
								controler.toClearData();
								//Write on label
								lblNewLabel.setText("The csv file has been saved");
							}
							else
							{
								//Write on label
								lblNewLabel.setText("The csv was not saved");
							}
							

						}
						else
						{
							// Overwrite is 0, append is 1
							controler.toOverWrite(0);
							controler.toSaveData();
							controler.toClearData();
							
							//Write on label
							lblNewLabel.setText("The csv file has been saved");
						}
		

					}

				} catch (BiffException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnProceed.setBounds(338, 214, 86, 20);
		frmBrifutope.getContentPane().add(btnProceed, BorderLayout.CENTER);
		
		JButton btnNewButton = new JButton("...");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Opens xls file location
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
					    "XLS files", "xls");
				fileChooser.setFileFilter(filter);
				fileChooser.setCurrentDirectory(new java.io.File("."));
				fileChooser.setDialogTitle("Open .xls file");
				fileChooser.setBounds(348, 128, 582, 397);
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    fileLocationTable[0] = file.getParent();
                    fileLocationTable[1] = file.getName();
                    textField.setText(file.getPath());			
				}			
			}
		});
		
		btnNewButton.setBounds(388, 69, 36, 21);
		frmBrifutope.getContentPane().add(btnNewButton);
		
		JLabel lblChooseXlsFile = new JLabel("Choose xls file source:");
		lblChooseXlsFile.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblChooseXlsFile.setBounds(13, 76, 133, 14);
		frmBrifutope.getContentPane().add(lblChooseXlsFile);
		
		JButton button = new JButton("...");
		button.addMouseListener(new MouseAdapter() {
			@Override
			// Opens csv file location
			public void mouseClicked(MouseEvent e) {
				
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
					    "CSV files", "csv");
				fileChooser.setFileFilter(filter);
				fileChooser.setCurrentDirectory(new java.io.File("."));
				fileChooser.setDialogTitle("Save .csv file");
				fileChooser.setBounds(348, 128, 582, 397);
				int returnValue = fileChooser.showSaveDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    
                    
                    if(file.getName().contains(".csv"))
                    {
	                    fileLocationTable[2] = file.getParent();
	                    fileLocationTable[3] = file.getName();
	                    textField_1.setText(file.getPath());
                    }
                    else
                    {
	                    fileLocationTable[2] = file.getParent();
	                    fileLocationTable[3] = file.getName() + ".csv";
	                    textField_1.setText(file.getPath() + ".csv");
                    }			
					
				}
			}
		});

		button.setBounds(388, 125, 36, 21);
		frmBrifutope.getContentPane().add(button);

	}
}
