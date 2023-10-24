package ch.epfl.cs107.stegano;

import ch.epfl.cs107.Helper;
import ch.epfl.cs107.utils.Bit;
import ch.epfl.cs107.utils.Image;

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
        assert load.length <= cover.length && load[0].length <= cover[0].length;
        System.out.println("cover is " + cover.length + "x" + cover[0].length);
        System.out.println("load is " + load.length + "x" + load[0].length);

        final int[][] newCover = new int[cover.length][];
        for (int i = 0; i < cover.length; i++) {
            newCover[i] = new int[cover[i].length];
        }

        for (int coordsH = 0; coordsH < cover.length; coordsH++) {
            for (int coordsL = 0; coordsL < cover[coordsH].length; coordsL++) {
                int pixelCover = cover[coordsH][coordsL];
                if (load.length > coordsH && load[coordsH].length > coordsL) {
                    boolean pixelLoad = load[coordsH][coordsL];
                    int embeddedPixel = Bit.embedInLSB(pixelCover, pixelLoad);
                    newCover[coordsH][coordsL] = embeddedPixel;
                } else {
                    newCover[coordsH][coordsL] = pixelCover;
                }
            }
        }
        System.out.println("final is " + newCover.length + "x" + newCover[0].length);
        return newCover;
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
        final boolean[][] res = new boolean[image.length][];
        for (int coordsH = 0; coordsH < image.length; coordsH++) {
            for (int coordsL = 0; coordsL < image[coordsH].length; coordsL++) {
                int pixelCover = image[coordsH][coordsL];
                boolean LSB = Bit.getLSB(pixelCover);
                if (res[coordsH]== null) res[coordsH] = new boolean[image[coordsH].length];
                res[coordsH][coordsL] = LSB;
            }
        }
        return res;
    }

}
