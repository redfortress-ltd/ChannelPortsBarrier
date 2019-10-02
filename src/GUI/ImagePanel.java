package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class ImagePanel extends JPanel {
    private Image img;

    public ImagePanel(String img) {
        this((new ImageIcon(img)).getImage());
    }

    public ImagePanel(Image img) {
        this.img = img;
        Dimension size = new Dimension(img.getWidth((ImageObserver)null), img.getHeight((ImageObserver)null));
        this.setPreferredSize(size);
        this.setMinimumSize(size);
        this.setMaximumSize(size);
        this.setSize(size);
        this.setLayout((LayoutManager)null);
    }

    public void paintComponent(Graphics g) {
        int x = (this.getWidth() - this.img.getWidth(null)) / 2;
        int y = (this.getHeight() - this.img.getHeight(null)) / 2;
        g.drawImage(this.img, x, y, (ImageObserver)null);
    }
}
