import javax.swing.JPanel;
import javax.swing.OverlayLayout;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;



public class BackgroundPanel extends JPanel {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Icon wallpaper = new ImageIcon("./img/giphy.gif");	

    public BackgroundPanel() {
    }
    protected void paintComponent(Graphics g) {
        if (null != wallpaper) {
            processBackground(g);
        }
    }
    public void setBackground(Icon wallpaper) {
        this.wallpaper = wallpaper;
        this.repaint();
    }
    private void processBackground(Graphics g) {
    	JLabel imageLabel = new JLabel();
    	
    	imageLabel.setIcon(wallpaper);
    	imageLabel.setLayout(new OverlayLayout(imageLabel));
    	
        ImageIcon icon = (ImageIcon) wallpaper;
        Image image = icon.getImage();
        int cw = getWidth();
        int ch = getHeight();
        int iw = image.getWidth(this);
        int ih = image.getHeight(this);
        int x = 0;
        int y = 0;
        while (y <= ch) {
            g.drawImage(image, x, y, this);
            x += iw;
            if (x >= cw) {
                x = 0;
                y += ih;
            }
        }
    }
}
