import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.Border;
import java.util.Random;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main {
	private int windowWidth = 1920;
	private int windowHeight = 1000;
	private int bitsMax = 8;
	
	//Encode View
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
	private JSlider bitSlider = new JSlider(JSlider.HORIZONTAL, 0, bitsMax, 0);
	private JSlider bitSliderDecode = new JSlider(JSlider.HORIZONTAL, 0, bitsMax, 0);
	
	private JButton generateButton;
	
	//Decode View
	private JLabel encodedDisplay = new JLabel();
	private JLabel decodedDisplay = new JLabel();
	private JButton encodedButton;
	private JButton decodeButton1;
	private JButton decodeButton2;
	private JButton decodeButton3;
	
	BufferedImage hostImage;
	BufferedImage donorImage;
	
	//Used to do adjustments 
	BufferedImage finalImage1;
	BufferedImage finalImage2;
	BufferedImage finalImage3;
	
	BufferedImage encodedImage;
	BufferedImage decodedImage;
	
	private JButton viewButton;
	private JPanel viewButtonPanel = new JPanel();
	private JPanel encodeView = new JPanel();
	private JPanel decodeView = new JPanel();
	
	JPanel panelContent = new JPanel();
	CardLayout c1 = new CardLayout();
	public Main() {
		frame = new JFrame("Image Displayer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(windowWidth, windowHeight);
		
		panelContent.setLayout(c1);
		
		panelContent.add(encodeView, "1");
		panelContent.add(decodeView, "2");
		
		c1.show(panelContent, "1");
		
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
		

		encodedDisplay.setPreferredSize(new Dimension(windowWidth / 2 - 10, windowHeight - 150));
		decodedDisplay.setPreferredSize(new Dimension(windowWidth / 2 - 10, windowHeight - 150));
		
		adjustmentPanel.setPreferredSize(new Dimension(windowWidth/5 * 3, windowHeight / 2 - 170));
		
		//Temporary border to view bounds
		Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
		Border border2 = BorderFactory.createLineBorder(Color.RED, 2);
//		previewPanel.setBorder(border2);
//		encodeView.setBorder(border2);
		encodedDisplay.setBorder(border);
		decodedDisplay.setBorder(border);
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
		
		bitSliderDecode.setMajorTickSpacing(2);
		bitSliderDecode.setMinorTickSpacing(1);
		bitSliderDecode.setPaintTicks(true);
		bitSliderDecode.setPaintLabels(true);
		
		//View Button
		viewButton = new JButton("Decode Mode");
		viewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean visible = encodeView.isVisible();
				encodeView.setVisible(!visible);
				decodeView.setVisible(visible);
				encodeView.setEnabled(!visible);
				decodeView.setEnabled(visible);
				
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
					uploadImage(hostDisplay);
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
					uploadImage(donorDisplay);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		//Encoded Image Button 
		encodedButton = new JButton("Upload");
		encodedButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					uploadImage(hostDisplay);
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
				encodeImage();
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
		
		decodeButton1 = new JButton("Alg 1");
		decodeButton2 = new JButton("Alg 2");
		decodeButton3 = new JButton("Alg 3"); 
		
		decodeButton1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				decodeImage(1);
			}
		});
		
		decodeButton2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				decodeImage(2);
			}
		});
		
		decodeButton1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				decodeImage(3);
			}
		});
		
		hostDisplay.add(hostButton);
		donorDisplay.add(donorButton);
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
		encodeView.add(BorderLayout.EAST, rightPanel);
		
		//Decode View
		decodeView.setLayout(new BorderLayout());
		buttonPanel.add(bitSliderDecode);
		buttonPanel.add(decodeButton1);
		buttonPanel.add(decodeButton2);
		buttonPanel.add(decodeButton3);
		decodeView.add(BorderLayout.NORTH, buttonPanel);
		
		encodedDisplay.add(encodedButton);
		encodedButton.setBounds((int) (encodedDisplay.getPreferredSize().getWidth()/2) - 50, (int) encodedDisplay.getPreferredSize().getHeight() - 20, 100, 20);

		decodeView.add(BorderLayout.WEST, encodedDisplay);
		decodeView.add(BorderLayout.EAST, decodedDisplay);
		
		decodeView.setEnabled(false);
		decodeView.setVisible(false);
		
		
		frame.add(panelContent);
		frame.add(BorderLayout.NORTH, viewButtonPanel);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	private void uploadImage(JLabel display) throws IOException {
		FileDialog dialog = new FileDialog(frame, "Select");
		dialog.setMode(FileDialog.LOAD);
		dialog.setVisible(true);
		
		String path = dialog.getDirectory() + dialog.getFile();
		BufferedImage image = ImageIO.read(new File(path));
		if(display == hostDisplay) {
			hostImage = image;
		} else {
			donorImage = image;
		}
		
		if(!encodeView.isVisible()) {
			encodedImage = image;
			displayImage(image, encodedDisplay);
		} else {
			displayImage(image, display);
		}
	}
	
	private void downloadImage(BufferedImage image) {
		if (image == null) {
			System.out.println("null image");
			return;
		}
		
		String chooserTitle = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.showSaveDialog(null);
		
		try {
			//Output file path
			File f = new File(fileChooser.getSelectedFile().getAbsolutePath() + "\\" + chooserTitle + ".png");

			System.out.println(f);
			ImageIO.write(image, "png", f);
			System.out.println("Writing complete.");
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
	}
	
	private void displayImage(BufferedImage image, JLabel display) {
		Dimension newImageDimension = getNewDimensions(new Dimension(image.getWidth(), image.getHeight()), new Dimension(display.getWidth(), display.getHeight()));
		ImageIcon imgIcon = new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(newImageDimension.width, newImageDimension.height, Image.SCALE_DEFAULT));
		display.setIcon(imgIcon);
		frame.revalidate();
		frame.repaint();
	}
	
	private int[] getHex(int bits) {
		int hex[] = {0xFF000000, 0xFFFFFFFF};
		
		switch(bits) {
		case 0:
			return new int[] {0xFF000000, 0xFFFFFFFF, 0xFFFFFFFF};
		case 1:
			return new int[] {0xFF808080, 0xFFFEFEFE, 0xFF7F7F7F};
		case 2:
			return new int[] {0xFFC0C0C0, 0xFFFCFCFC, 0xFF7F7F7F};
		case 3:
			return new int[] {0xFFE0E0E0, 0xFFF8F8F8, 0xFF1F1F1F};
		case 4:
			return new int[] {0xFFF0F0F0, 0xFFF0F0F0, 0xFF0F0F0F};
		case 5:
			return new int[] {0xFFF8F8F8, 0xFFE0E0E0, 0xFF070707};
		case 6:
			return new int[] {0xFFFCFCFC, 0xFFC0C0C0, 0xFF030303};
		case 7:
			return new int[] {0xFFFEFEFE, 0xFF808080, 0xFF010101};
		case 8:
			return new int[] {0xFFFFFFFF, 0xFF000000, 0xFF000000};
		default: 
			return new int[] {0xFFF0F0F0, 0xFFF0F0F0, 0xFF0F0F0F};
		}
	}
	
	private void encodeImage() {
		//Add code here to generate results
		finalImage1 = new BufferedImage(hostImage.getWidth(), hostImage.getHeight(), hostImage.getType());
		finalImage2 = new BufferedImage(hostImage.getWidth(), hostImage.getHeight(), hostImage.getType());
		finalImage3 = new BufferedImage(hostImage.getWidth(), hostImage.getHeight(), hostImage.getType());
		int bits = bitsMax - bitSlider.getValue();
				
		for(int h=0; h < finalImage1.getHeight(); h++ ) {
			for(int w=0; w < finalImage1.getWidth(); w++ ) {
				//Algorithm 1
				int rgb1 = hostImage.getRGB(w, h) & getHex(bits)[0];
				
				if(h + 1 > donorImage.getHeight() || w + 1> donorImage.getWidth()) {
					finalImage1.setRGB(w, h, (new Color(getRed(rgb1), getGreen(rgb1), getBlue(rgb1)).getRGB()));
				} else {
					int rgb2 = donorImage.getRGB(w, h) & getHex(bits)[1];
					int r2 = getRed(rgb2) >> bits;
					int g2 = getGreen(rgb2) >> bits;
					int b2 = getBlue(rgb2) >> bits;
					rgb2 = new Color(r2, g2, b2).getRGB();
					int newRGB = rgb1 | rgb2;
					finalImage1.setRGB(w, h, newRGB);
				}
			}
		}

		for(int h=0; h < finalImage2.getHeight(); h++ ) {
			if (h == 0) {
				for (int w = 4; w < finalImage2.getWidth(); w++) {
					//Algorithm 2
					int rgb3 = hostImage.getRGB(w, h) & getHex(bits)[0];

					if(h + 1 > donorImage.getHeight() || w + 1> donorImage.getWidth()) {
						finalImage2.setRGB(w, h, (new Color(getRed(rgb3), getGreen(rgb3), getBlue(rgb3)).getRGB()));
					} else {
						int rgb4 = donorImage.getRGB(w, h) & getHex(bits)[1];
					System.out.println("bits:" + bits);
					int r22 = getRed(rgb4) >> bits;
					int g22 = getGreen(rgb4) >> bits;
					int b22 = getBlue(rgb4) >> bits;
					rgb4 = new Color(r22, g22, b22).getRGB();
					int newRGB2 = rgb3 | rgb4;
					finalImage2.setRGB(w, h, newRGB2);

				}}
			} else {
				for (int w = 0; w < finalImage2.getWidth(); w++) {
					int rgb3 = hostImage.getRGB(w, h) & getHex(bits)[0];

					if(h + 1 > donorImage.getHeight() || w + 1> donorImage.getWidth()) {
						finalImage2.setRGB(w, h, (new Color(getRed(rgb3), getGreen(rgb3), getBlue(rgb3)).getRGB()));
					} else {
						int rgb4 = donorImage.getRGB(w, h) & getHex(bits)[1];
					System.out.println("bits:" + bits);
					int r22 = getRed(rgb4) >> bits;
					int g22 = getGreen(rgb4) >> bits;
					int b22 = getBlue(rgb4) >> bits;
					rgb4 = new Color(r22, g22, b22).getRGB();
					int newRGB2 = rgb3 | rgb4;
					finalImage2.setRGB(w, h, newRGB2);

				}}
			}
		}
		int rgbEncrypt = new Color(255, 255, 255).getRGB();
		for(int w=0; w < 5; w++ ) {
			finalImage2.setRGB(w, 0, rgbEncrypt);
		}
		
		for(int h=0; h < finalImage3.getHeight(); h++ ) {
			for(int w=0; w < finalImage3.getWidth(); w++ ) {
				//Algorithm 3
				int rgb1 = hostImage.getRGB(w, h) & 0xFFF0F0F0;
				
				int dw = w >= donorImage.getWidth() ? w % donorImage.getWidth() : w;
				int dh =  h >= donorImage.getHeight() ? h % donorImage.getHeight() : h;
				
				int rgb2 = donorImage.getRGB(dw, dh) & 0xFFF0F0F0;

				int newR = (int) (0.85 * getRed(rgb1) + 0.15 * getRed(rgb2));
				int newG = (int) (0.85 * getGreen(rgb1) + 0.15 * getGreen(rgb2));
				int newB = (int) (0.85 * getBlue(rgb1) + 0.15 * getBlue(rgb2));

				finalImage3.setRGB(w, h, (new Color(newR, newG, newB).getRGB()));
			}
		}

		displayImage(finalImage1, finalDisplay1);
		displayImage(finalImage2, finalDisplay2);
		displayImage(finalImage3, finalDisplay3);
	}
	
	private void decodeImage(int algorithm) {
		int bits = bitsMax - bitSliderDecode.getValue();
		Random rand = new Random();
		decodedImage = new BufferedImage(encodedImage.getWidth(),encodedImage.getHeight(), encodedImage.getType());
		int rgbDecrypt = encodedImage.getRGB(0,0) & 0xFF0F0F0F;
		int newRGBDecrypt = new Color(getRed(rgbDecrypt), getGreen(rgbDecrypt), getBlue(rgbDecrypt)).getRGB();

		System.out.println(newRGBDecrypt);
		if (algorithm == 1) {//Algorithm 1
//			if (newRGBDecrypt == -15790321) {
				for (int i = 0; i < decodedImage.getWidth(); i++) {
					for (int j = 0; j < decodedImage.getHeight(); j++) {

//						int rgb = encodedImage.getRGB(rand.nextInt(decodedImage.getWidth()), rand.nextInt(decodedImage.getHeight())) & 0xFF7F7F7F;
						int rgb = encodedImage.getRGB(i, j) & getHex(bits)[2];

						int r = getRed(rgb) << bits;
						int g = getGreen(rgb) << bits;
						int b = getBlue(rgb) << bits;
						int newRGB = new Color(r, g, b).getRGB();
						decodedImage.setRGB(i, j, newRGB);
					}
				}
//			} else {
//				for (int i = 0; i < decodedImage.getWidth(); i++) {
//					for (int j = 0; j < decodedImage.getHeight(); j++) {
//						int rgb = encodedImage.getRGB(i, j) & 0xFF0F0F0F;
//
//						int bits = bitsMax - bitSliderDecode.getValue();
//						int r = getRed(rgb) << bits;
//						int g = getGreen(rgb) << bits;
//						int b = getBlue(rgb) << bits;
//						int newRGB = new Color(r, g, b).getRGB();
//						decodedImage.setRGB(i, j, newRGB);
//					}
//				}
//			}
		}

		if (algorithm == 2) {//Algorithm 2
			if (newRGBDecrypt == -15790321) {
				for (int i = 0; i < decodedImage.getWidth(); i++) {
					for (int j = 0; j < decodedImage.getHeight(); j++) {
						int rgb = encodedImage.getRGB(i, j) & getHex(bits)[2];

						//int bits = bitsMax - bitSliderDecode.getValue();
						int r = getRed(rgb) << bits;
						int g = getGreen(rgb) << bits;
						int b = getBlue(rgb) << bits;
						int newRGB = new Color(r, g, b).getRGB();
						decodedImage.setRGB(i, j, newRGB);
					}
				}
			} else {
				for (int i = 0; i < decodedImage.getWidth(); i++) {
					for (int j = 0; j < decodedImage.getHeight(); j++) {
						int rgb = encodedImage.getRGB(rand.nextInt(decodedImage.getWidth()), rand.nextInt(decodedImage.getHeight()))& getHex(bits)[2];

						//int bits = bitsMax - bitSliderDecode.getValue();
						int r = getRed(rgb) << bits;
						int g = getGreen(rgb) << bits;
						int b = getBlue(rgb) << bits;
						int newRGB = new Color(r, g, b).getRGB();
						decodedImage.setRGB(i, j, newRGB);
					}
				}
			}
		}

		if (algorithm == 3) {//Algorithm 3

		}

		displayImage(decodedImage, decodedDisplay);
	}
	
	// helper functions
	
	protected int getRed(int pixel) {
		return (pixel >>> 16) & 0xFF;
	}

	protected int getGreen(int pixel) {
		return (pixel >>> 8) & 0xFF;
	}

	protected int getBlue(int pixel) {
		return pixel & 0xFF;
	}
	private int clip(int v) {
		v = v > 255 ? 255 : v;
		v = v < 0 ? 0 : v;
		return v;
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