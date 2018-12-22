/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package photoedittingapplication;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

/**
 *
 * @author Kusal
 */
public class ImageInput {

    public BufferedImage image = null;
    public PhotoInterface photoInterface;
    public PointOperations operations = null;
    List<BufferedImage> imageStack;

    public void getImage() {
        JFileChooser filechooser = new JFileChooser();
        filechooser.showOpenDialog(null);
        File imageFile = filechooser.getSelectedFile();
        try {
            image = ImageIO.read(imageFile);
            imageStack.add(image);
            photoInterface.lblimage.setIcon(new ImageIcon(image));
        } catch (IOException ex) {
            Logger.getLogger(ImageInput.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
