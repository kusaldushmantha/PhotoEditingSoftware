/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package photoedittingapplication;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.ImageIcon;

/**
 *
 * @author Kusal
 */
public class ResamplingOperations {

    public BufferedImage tempImage;
    public PhotoInterface photoOperation;
    public List<BufferedImage> imageStack;
    public ImageInput imageInput;

    public void nearestNeighbour(BufferedImage image, int scale) {
        tempImage = new BufferedImage(image.getWidth() * scale, image.getHeight() * scale, image.getType());

        for (int i = 0; i < tempImage.getWidth(); i++) {
            for (int j = 0; j < tempImage.getHeight(); j++) {
                tempImage.setRGB(i, j, image.getRGB((int) (i / scale), (int) (j / scale)));
            }
        }

        photoOperation.lblimage.setIcon(new ImageIcon(tempImage));
        imageStack.add(tempImage);
    }

    public void reset() {
        imageStack.removeAll(imageStack);
        imageStack.add(imageInput.image);
        photoOperation.lblimage.setIcon(new ImageIcon(imageStack.get(0)));
    }

    public void linearInterpolation(BufferedImage image, int scale) {
        tempImage = new BufferedImage(Math.round(image.getWidth() * scale), Math.round(image.getHeight() * scale),
                BufferedImage.TYPE_INT_RGB);

        int imgWidth = tempImage.getWidth();
        int imgHeight = tempImage.getHeight();

        int originalImageWidth = image.getWidth();
        int originalImageHeight = image.getHeight();

        Color A = new Color(0), B = new Color(0), C = new Color(0), D = new Color(0);
        int r, g, b, x, y;
        float dx, dy;

        for (int i = 0; i < imgWidth; i++) {
            for (int j = 0; j < imgHeight; j++) {
                x = (int) Math.floor(i / scale);
                y = (int) Math.floor(j / scale);
                dx = (i / scale) - x;
                dy = (j / scale) - y;
                A = new Color(image.getRGB(x, y));
                if (x + 1 < originalImageWidth) {
                    B = new Color(image.getRGB(x + 1, y));
                }
                if (x + 1 < originalImageWidth && y + 1 < originalImageHeight) {
                    C = new Color(image.getRGB(x + 1, y + 1));
                }
                if (y + 1 < originalImageHeight) {
                    D = new Color(image.getRGB(x, y + 1));
                }

                r = (int) ((1 - dx) * (1 - dy) * A.getRed() + dx * (1 - dy) * B.getRed() + dx * dy * C.getRed() + (1 - dx) * dy * D.getRed());
                g = (int) ((1 - dx) * (1 - dy) * A.getGreen() + dx * (1 - dy) * B.getGreen() + dx * dy * C.getGreen() + (1 - dx) * dy * D.getGreen());
                b = (int) ((1 - dx) * (1 - dy) * A.getBlue() + dx * (1 - dy) * B.getBlue() + dx * dy * C.getBlue() + (1 - dx) * dy * D.getBlue());

                tempImage.setRGB(i, j, new Color(r, g, b, 255).getRGB());

            }
        }

        photoOperation.lblimage.setIcon(new ImageIcon(tempImage));
        imageStack.add(tempImage);
    }
}
