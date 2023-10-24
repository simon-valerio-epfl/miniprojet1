package ch.epfl.cs107.utils;

import ch.epfl.cs107.Helper;

import static ch.epfl.cs107.utils.Text.*;
import static ch.epfl.cs107.utils.Image.*;
import static ch.epfl.cs107.utils.Bit.*;
import static ch.epfl.cs107.stegano.ImageSteganography.*;
import static ch.epfl.cs107.stegano.TextSteganography.*;
import static ch.epfl.cs107.crypto.Encrypt.*;
import static ch.epfl.cs107.crypto.Decrypt.*;
import static ch.epfl.cs107.Main.*;

/**
 * <b>Task 1.3: </b>Utility class to manipulate ARGB images
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Image {

    // DO NOT CHANGE THIS, MORE ON THAT ON WEEK 7
    private Image(){}

    // ============================================================================================
    // ==================================== PIXEL MANIPULATION ====================================
    // ============================================================================================

    /**
     * Build a given pixel value from its respective components
     *
     * @param alpha alpha component of the pixel
     * @param red red component of the pixel
     * @param green green component of the pixel
     * @param blue blue component of the pixel
     * @return packed value of the pixel
     */
    public static int argb(byte alpha, byte red, byte green, byte blue){
        return ((alpha & 0xFF) << 24) |
                ((red & 0xFF) << 16) |
                ((green & 0xFF) << 8) |
                (blue & 0xFF);
    }

    /**
     * Extract the alpha component of a given pixel
     *
     * @param pixel packed value of the pixel
     * @return the alpha component of the pixel
     */
    public static byte alpha(int pixel){
        System.out.println(pixel);
        // we move the bits to the right (bc alpha is 31-24)
        return (byte) (pixel >> 24);
    }

    /**
     * Extract the red component of a given pixel
     *
     * @param pixel packed value of the pixel
     * @return the red component of the pixel
     */
    public static byte red(int pixel) {
        // we move the bits to the right (bc red is 23-16) and we only keep the first 8 bits
        return (byte) ((pixel >> 16) & 0xFF);
    }

    /**
     * Extract the green component of a given pixel
     *
     * @param pixel packed value of the pixel
     * @return the green component of the pixel
     */
    public static byte green(int pixel){
        // same here, green is 15-8
        return (byte) ((pixel >> 8) & 0xFF);
    }

    /**
     * Extract the blue component of a given pixel
     *
     * @param pixel packed value of the pixel
     * @return the blue component of the pixel
     */
    public static byte blue(int pixel){
        return (byte) (pixel & 0xFF);
    }

    /**
     * Compute the gray scale of the given pixel
     *
     * @param pixel packed value of the pixel
     * @return gray scaling of the given pixel
     */
    public static int gray(int pixel){
        final int unsignedRed = (Image.red(pixel) & 0xFF);
        final int unsignedGreen = (Image.green(pixel) & 0xFF);
        final int unsignedBlue = (Image.blue(pixel) & 0xFF);

        // final byte alpha = Image.alpha(pixel);

        return (unsignedRed + unsignedGreen + unsignedBlue) / 3;
    }

    /**
     * Compute the binary representation of a given pixel.
     *
     * @param gray gray scale value of the given pixel
     * @param threshold when to consider a pixel white
     * @return binary representation of a pixel
     */
    public static boolean binary(int gray, int threshold){
        return gray >= threshold;
    }

    // ============================================================================================
    // =================================== IMAGE MANIPULATION =====================================
    // ============================================================================================

    /**
     * Build the gray scale version of an ARGB image
     *
     * @param image image in ARGB format
     * @return the gray scale version of the image
     */
    public static int[][] toGray(int[][] image){
        assert image.length > 0;
        final int[][] res = new int[image.length][];
        for (int coordH = 0; coordH < image.length; coordH++) {
            for (int coordL = 0; coordL < image[coordH].length; coordL++) {
                int pixel = image[coordH][coordL];
                if (res[coordH] == null) res[coordH] = new int[image[coordH].length];
                res[coordH][coordL] = Image.gray(pixel);
            }
        }
        return res;
    }

    /**
     * Build the binary representation of an image from the gray scale version
     *
     * @param image Image in gray scale representation
     * @param threshold Threshold to consider
     * @return binary representation of the image
     */
    public static boolean[][] toBinary(int[][] image, int threshold){
        assert image.length > 0;
        final boolean[][] res = new boolean[image.length][];
        for (int coordH = 0; coordH < image.length; coordH++) {
            for (int coordL = 0; coordL < image[coordH].length; coordL++) {
                int pixel = image[coordH][coordL];
                if (res[coordH] == null) res[coordH] = new boolean[image[coordH].length];
                res[coordH][coordL] = Image.binary(pixel, threshold);
            }
        }
        return res;
    }

    /**
     * Build an ARGB image from the gray-scaled image
     * @implNote The result of this method will a gray image, not the original image
     * @param image grayscale image representation
     * @return <b>gray ARGB</b> representation
     */
    public static int[][] fromGray(int[][] image){
        return Helper.fail("NOT IMPLEMENTED");
    }

    /**
     * Build an ARGB image from the binary image
     * @implNote The result of this method will a black and white image, not the original image
     * @param image binary image representation
     * @return <b>black and white ARGB</b> representation
     */
    public static int[][] fromBinary(boolean[][] image){
        return Helper.fail("NOT IMPLEMENTED");
    }

}
