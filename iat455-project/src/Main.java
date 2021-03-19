import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.Border;

public class Main {
	private int windowWidth = 1920;
	private int windowHeight = 1000;
	
	private JFrame frame;
	private JButton hostButton;
	private JButton donorButton;
	
	private JLabel hostDisplay;
	private JLabel donorDisplay;

	private JPanel buttonPanel = new JPanel();
	private JPanel previewPanel = new JPanel();
	private JPanel rightPanel = new JPanel();
	private JPanel resultPanel = new JPanel();
	private JPanel adjustmentPanel = new JPanel();
	
	private JLabel finalDisplay1;
	private JLabel finalDisplay2;
	private JLabel finalDisplay3;
	
	private JButton finalButton1;
	private JButton finalButton2;
	private JButton finalButton3;
	private JSlider bitSlider = new JSlider(JSlider.HORIZONTAL, 0, 8, 0);
	
	private JButton generateButton;

	//Used to do adjustments 
	BufferedImage finalImage1;
	BufferedImage finalImage2;
	BufferedImage finalImage3;
	
	private JButton viewButton;
	private JPanel viewButtonPanel = new JPanel();
	private JPanel encodeView = new JPanel();
	private JPanel decodeView = new JPanel();
	
	public Main() {
		frame = new JFrame("Image Displayer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(windowWidth, windowHeight);
		
		hostDisplay = new JLabel();
		donorDisplay = new JLabel();
		finalDisplay1 = new JLabel();
		finalDisplay2 = new JLabel();
		finalDisplay3 = new JLabel();
		
		
		hostDisplay.setPreferredSize(new Dimension(windowWidth / 5 - 10, windowHeight - 100));
		donorDisplay.setPreferredSize(new Dimension(windowWidth / 5 - 10, windowHeight - 100));
		finalDisplay1.setPreferredSize(new Dimension(windowWidth / 5 - 10, windowHeight / 2 ));
		finalDisplay2.setPreferredSize(new Dimension(windowWidth / 5 - 10, windowHeight / 2 ));
		finalDisplay3.setPreferredSize(new Dimension(windowWidth / 5 - 10, windowHeight / 2 ));
		rightPanel.setPreferredSize(new Dimension(windowWidth/5 * 3, windowHeight));
		
		adjustmentPanel.setPreferredSize(new Dimension(windowWidth/5 * 3, windowHeight / 2 - 170));
		
		//Temporary border to view bounds
		Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
		Border border2 = BorderFactory.createLineBorder(Color.RED, 2);
		previewPanel.setBorder(border2);
		encodeView.setBorder(border2);
		hostDisplay.setBorder(border);
		donorDisplay.setBorder(border);
		finalDisplay1.setBorder(border);
		finalDisplay2.setBorder(border);
		finalDisplay3.setBorder(border);
		
		//initialize slider
		bitSlider.setMajorTickSpacing(2);
		bitSlider.setMinorTickSpacing(1);
		bitSlider.setPaintTicks(true);
		bitSlider.setPaintLabels(true);
		
		//View Button
		viewButton = new JButton("Decode Mode");
		viewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean visible = encodeView.isVisible();
				encodeView.setVisible(!visible);
				
				if (visible) {
					viewButton.setText("Encode Mode");
				} else {
					viewButton.setText("Decode Mode");
				}
			}
		});
		
		//Host Button
		hostButton = new JButton("Host");
		hostButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					uploadHostImage(hostDisplay);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		//Donor Button
		donorButton = new JButton("Donor");
		donorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					uploadHostImage(donorDisplay);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		
		//Generate results
		generateButton = new JButton("Generate");
		generateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				genereateResults();
			}
			
		});
		
		//Download results
		finalButton1 = new JButton("Alg 1");
		finalButton2 = new JButton("Alg 2");
		finalButton3 = new JButton("Alg 3"); 
		
		finalButton1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				downloadImage(finalImage1);
			}
		});
		
		finalButton2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				downloadImage(finalImage2);
			}
		});
		
		finalButton3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				downloadImage(finalImage3);
			}
		});
		
		hostDisplay.add(hostButton);
		donorDisplay.add(donorButton);
		System.out.println(hostDisplay.getWidth());
		hostButton.setBounds((int) (hostDisplay.getPreferredSize().getWidth()/2) - 50, (int) hostDisplay.getPreferredSize().getHeight() - 20, 100, 20);
		donorButton.setBounds((int) (donorDisplay.getPreferredSize().getWidth()/2) - 50, (int) donorDisplay.getPreferredSize().getHeight() - 20, 100, 20);
		
		previewPanel.add(hostDisplay);
		previewPanel.add(donorDisplay);
		
		finalDisplay1.add(finalButton1);
		finalButton1.setBounds((int) (finalDisplay1.getPreferredSize().getWidth()/2) - 50, (int) finalDisplay1.getPreferredSize().getHeight() - 20, 100, 20);
		finalDisplay2.add(finalButton2);
		finalButton2.setBounds((int) (finalDisplay2.getPreferredSize().getWidth()/2) - 50, (int) finalDisplay2.getPreferredSize().getHeight() - 20, 100, 20);
		finalDisplay3.add(finalButton3);
		finalButton3.setBounds((int) (finalDisplay3.getPreferredSize().getWidth()/2) - 50, (int) finalDisplay3.getPreferredSize().getHeight() - 20, 100, 20);
		
		
		resultPanel.add(finalDisplay1);
		resultPanel.add(finalDisplay2);
		resultPanel.add(finalDisplay3);
		rightPanel.add(BorderLayout.NORTH, resultPanel);

		adjustmentPanel.add(bitSlider);
		adjustmentPanel.add(generateButton);
		
		rightPanel.add(adjustmentPanel);
		viewButtonPanel.add(viewButton);
		
		//Encode View
		encodeView.setLayout(new BorderLayout());
		encodeView.add(BorderLayout.WEST, previewPanel);
		encodeView.add(BorderLayout.SOUTH, buttonPanel);
		encodeView.add(BorderLayout.EAST, rightPanel);
		

		//Decode View
		decodeView.setLayout(new BorderLayout());
		
		frame.add(BorderLayout.NORTH, viewButtonPanel);
		frame.add(encodeView);
		frame.setVisible(true);
	}
	
	private void uploadHostImage(JLabel display) throws IOException {
		FileDialog dialog = new FileDialog(frame, "Select");
		dialog.setMode(FileDialog.LOAD);
		dialog.setVisible(true);
		
		String path = dialog.getDirectory() + dialog.getFile();
		
		BufferedImage image = ImageIO.read(new File(path));

		Dimension newImageDimension = getNewDimensions(new Dimension(image.getWidth(), image.getHeight()), new Dimension(display.getWidth(), display.getHeight()));

		ImageIcon imgIcon = new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(newImageDimension.width, newImageDimension.height, Image.SCALE_DEFAULT));
		display.setIcon(imgIcon);
		frame.revalidate();
		frame.repaint();
	}
	
	private void downloadImage(BufferedImage image) {
		//Add code here to download image
		
		System.out.println("Download Image");
	}
	
	private void genereateResults() {
		//Add code here to generate results
	}
	
	// return new dimensions to fit display
	Dimension getNewDimensions(Dimension image, Dimension display) {
	    double widthRatio = display.getWidth() / image.getWidth();
	    double heightRatio = display.getHeight() / image.getHeight();
	    double ratio = Math.min(widthRatio, heightRatio);
	    return new Dimension((int) (image.width  * ratio), (int) (image.height * ratio));
	}
	
	public static void main(String[] args) {
		new Main();
	}
	
}