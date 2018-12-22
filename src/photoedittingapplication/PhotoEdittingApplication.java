/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package photoedittingapplication;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kusal
 */
public class PhotoEdittingApplication {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        List<BufferedImage> imageStack = new ArrayList<>();
        
        ImageInput imgInput = new ImageInput();
        
        PhotoInterface photo = new PhotoInterface();
        photo.setVisible(true);
        
        Operations photoOperations = new Operations();
        PointOperations pointOperations = new PointOperations();
        FilterOperations filterOperations = new FilterOperations();
        ResamplingOperations resamplingOperations = new ResamplingOperations();
        EdgeOperations edgeOperations = new EdgeOperations();
        ImageHistogram histogram = new ImageHistogram();
        
        imgInput.imageStack = imageStack;
        imgInput.photoInterface = photo;
        
        photo.imageInput = imgInput;
        photo.photoOperations = photoOperations;
        
        photoOperations.pointOperations=pointOperations;
        pointOperations.imageStack = imageStack;
        imgInput.operations = pointOperations;
        pointOperations.imageInput = imgInput;
        pointOperations.photoInterface = photo;
        
        photoOperations.filterOperations = filterOperations;
        filterOperations.imageStack = imageStack;
        filterOperations.photoInterface = photo;
        filterOperations.imageInput = imgInput;
        
        photoOperations.resamplingOperation = resamplingOperations;
        resamplingOperations.photoOperation = photo;
        resamplingOperations.imageStack = imageStack;
        resamplingOperations.imageInput = imgInput;
        
        photoOperations.edgeOperations= edgeOperations;
        edgeOperations.imageStack = imageStack;
        edgeOperations.photoInterface = photo;
        edgeOperations.imageInput = imgInput;
        
        photoOperations.histogram = histogram;
        histogram.imageStack = imageStack;
        histogram.photoInterface = photo;
        histogram.imageInput = imgInput;
        
    }
    
}
