import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main {
	private int windowWidth = 1280;
	private int windowHeight = 720;
	
	private JFrame frame;
	private JButton uploadButton;
	private JLabel imageDisplay;
	
	public Main() {
		frame = new JFrame("Image Displayer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(windowWidth, windowHeight);
		
		
		JPanel buttonPanel = new JPanel();
		uploadButton = new JButton("Upload");
		uploadButton.setBounds(0,0,100,50);
		uploadButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					uploadHostImage();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
			
		});
		imageDisplay = new JLabel();
		
		buttonPanel.add(uploadButton);
		
		frame.add(BorderLayout.CENTER, imageDisplay);
		frame.add(BorderLayout.SOUTH, buttonPanel);
		frame.setVisible(true);
	}
	
	private void uploadHostImage() throws IOException {
		FileDialog dialog = new FileDialog(frame, "Select");
		dialog.setMode(FileDialog.LOAD);
		dialog.setVisible(true);
		
		String path = dialog.getDirectory() + dialog.getFile();
		
		BufferedImage image = ImageIO.read(new File(path));

		Dimension newImageDimension = getNewDimensions(new Dimension(image.getWidth(), image.getHeight()));

		ImageIcon imgIcon = new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(newImageDimension.width, newImageDimension.height, Image.SCALE_DEFAULT));
		imageDisplay.setIcon(imgIcon);
		imageDisplay.setMaximumSize(new Dimension(1000, 600));
		frame.revalidate();
		frame.repaint();
	}
	
	// return new dimensions to fit canvas
	Dimension getNewDimensions(Dimension image) {
	    double widthRatio = windowWidth / image.getWidth();
	    double heightRatio = windowHeight / image.getHeight();
	    double ratio = Math.min(widthRatio, heightRatio);
	    return new Dimension((int) (image.width  * ratio), (int) (image.height * ratio));
	}
	
	public static void main(String[] args) {
		new Main();
	}
	
}