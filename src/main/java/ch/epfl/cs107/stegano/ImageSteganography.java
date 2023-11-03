package ch.epfl.cs107.stegano;

import ch.epfl.cs107.Helper;
import ch.epfl.cs107.utils.Bit;

import static ch.epfl.cs107.utils.Text.*;
import static ch.epfl.cs107.utils.Image.*;
import static ch.epfl.cs107.utils.Bit.*;
import static ch.epfl.cs107.stegano.ImageSteganography.*;
import static ch.epfl.cs107.stegano.TextSteganography.*;
import static ch.epfl.cs107.crypto.Encrypt.*;
import static ch.epfl.cs107.crypto.Decrypt.*;
import static ch.epfl.cs107.Main.*;

/**
 * <b>Task 3.1: </b>Utility class to perform Image Steganography
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 * @version 1.0.0
 * @since 1.0.0
 */
public final class ImageSteganography {

    // DO NOT CHANGE THIS, MORE ON THAT ON WEEK 7
    private ImageSteganography(){}

    // ============================================================================================
    // ================================== EMBEDDING METHODS =======================================
    // ============================================================================================

    /**
     * Embed an ARGB image on another ARGB image (the cover)
     * @param cover Cover image
     * @param argbImage Embedded image
     * @param threshold threshold to use for binary conversion
     * @return ARGB image with the image embedded on the cover
     */
    public static int[][] embedARGB(int[][] cover, int[][] argbImage, int threshold){
        int[][] grayImage = toGray(argbImage);
        return embedGray(cover, grayImage, threshold);
    }

    /**
     * Embed a Gray scaled image on another ARGB image (the cover)
     * @param cover Cover image
     * @param grayImage Embedded image
     * @param threshold threshold to use for binary conversion
     * @return ARGB image with the image embedded on the cover
     */
    public static int[][] embedGray(int[][] cover, int[][] grayImage, int threshold){
        assert grayImage !=null;
        assert cover.length!=0;
        assert grayImage.length!=0;
        boolean[][] binaryImage = toBinary(grayImage, threshold);
        return embedBW(cover, binaryImage);
    }

    /**
     * Embed a binary image on another ARGB image (the cover)
     * @param cover Cover image
     * @param load Embedded image
     * @return ARGB image with the image embedded on the cover
     */
    public static int[][] embedBW(int[][] cover, boolean[][] load){
        assert cover!=null;
        assert load!=null;
        assert load.length!=0;
        assert cover.length >= load.length;
        for (int i = 0; i < load.length; i++) {
            assert load[i] !=null;
            assert cover[i] != null;
            assert load[i].length != 0;
            assert cover[i].length >=load[i].length;
        }

        int[][] newImage = new int[cover.length][cover[0].length];
        // we copy all the pixel in our load
        // to the new image
        for (int x = 0; x < cover.length; x++) {
            for (int y = 0; y < cover[0].length; y++) {
                int pixelCover = cover[x][y];
                if (load.length > x && load[x].length > y) {
                    boolean pixelValue = load[x][y];
                    newImage[x][y] = embedInLSB(pixelCover, pixelValue);
                } else {
                    newImage[x][y] = pixelCover;
                }
            }
        }

        return newImage;
    }

    // ============================================================================================
    // =================================== REVEALING METHODS ======================================
    // ============================================================================================

    /**
     * Reveal a binary image from a given image
     * @param image Image to reveal from
     * @return binary representation of the hidden image
     */
    public static boolean[][] revealBW(int[][] image) {

        assert image != null;
        assert image.length != 0;
        for (int i = 0; i < image.length; i++) {
            assert image[i] != null;
            assert image[i].length == image[0].length;
        }

        boolean[][] revealedImage = new boolean [image.length][image[0].length];
        for (int x = 0; x < image.length; x++) {
            for (int y = 0; y < image[0].length; y++) {
                revealedImage[x][y] = Bit.getLSB(image[x][y]);
            }
        }
        return revealedImage;
    }

}
