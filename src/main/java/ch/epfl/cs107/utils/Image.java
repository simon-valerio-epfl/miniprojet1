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


//methods from/to Gray and from/to Binary should work with empty input image



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
        int pixel = 0;
        byte[] components = {blue, green, red, alpha};
        for (int i = 0; i < components.length; i++) {
            int componentValue = Byte.toUnsignedInt(components[i]) << i*Byte.SIZE;
            pixel |= componentValue;
        }
        return pixel;
    }

    /**
     * Extract the alpha component of a given pixel
     *
     * @param pixel packed value of the pixel
     * @return the alpha component of the pixel
     */
    public static byte alpha(int pixel){
        return extractPrimaryComponent(pixel, 3);
    }

    /**
     * Extract the red component of a given pixel
     *
     * @param pixel packed value of the pixel
     * @return the red component of the pixel
     */
    public static byte red(int pixel){
        return extractPrimaryComponent(pixel, 2);
    }

    /**
     * Extract the green component of a given pixel
     *
     * @param pixel packed value of the pixel
     * @return the green component of the pixel
     */
    public static byte green(int pixel){
        return extractPrimaryComponent(pixel, 1);
    }

    /**
     * Extract the blue component of a given pixel
     *
     * @param pixel packed value of the pixel
     * @return the blue component of the pixel
     */
    public static byte blue(int pixel){
        return extractPrimaryComponent(pixel, 0);
    }

    /**
     * Compute the gray scale of the given pixel
     *
     * @param pixel packed value of the pixel
     * @return gray scaling of the given pixel
     */
    public static int gray(int pixel){
        int red = Byte.toUnsignedInt(red(pixel));
        int green = Byte.toUnsignedInt(green(pixel));
        int blue = Byte.toUnsignedInt(blue(pixel));
        return (red + green + blue) / 3;
    }

    /**
     * Compute the binary representation of a given pixel.
     *
     * @param gray gray scale value of the given pixel
     * @param threshold when to consider a pixel white
     * @return binary representation of a pixel
     */
    public static boolean binary(int gray, int threshold){
        //we assert that the gray value is in a reasonable range
        assert gray >= 0;
        assert gray <= 255;

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
        assert image != null;
        for (int[] ints : image) {
            assert (ints != null);
        }

        // handle empty image
        if (image.length == 0) return new int[0][0];

        int[][] newImage = new int[image.length][image[0].length];

        for (int x = 0; x < image.length; x++) {
            for (int y = 0; y < image[0].length; y++) {
                newImage[x][y] = gray(image[x][y]);
            }
        }

        return newImage;
    }

    /**
     * Build the binary representation of an image from the gray scale version
     *
     * @param image Image in gray scale representation
     * @param threshold Threshold to consider
     * @return binary representation of the image
     */
    public static boolean[][] toBinary(int[][] image, int threshold){
        assert image != null;
        for (int[] ints : image) {
            assert (ints != null);
        }

        // handle empty image
        if (image.length == 0) return new boolean[0][0];

        boolean[][] newImage = new boolean[image.length][image[0].length];

        for (int x = 0; x < image.length; x++) {
            for (int y = 0; y < image[0].length; y++) {
                newImage[x][y] = binary(image[x][y], threshold);
            }
        }

        return newImage;
    }

    /**
     * Build an ARGB image from the gray-scaled image
     * @implNote The result of this method will a gray image, not the original image
     * @param image grayscale image representation
     * @return <b>gray ARGB</b> representation
     */
    public static int[][] fromGray(int[][] image){
        assert image != null;
        for (int[] ints : image) {
            assert (ints != null);
        }

        // handle empty image
        if (image.length == 0) return new int[0][0];

        int[][] newImage = new int[image.length][image[0].length];

        for (int x = 0; x < image.length; x++) {
            for (int y = 0; y < image[0].length; y++) {
                byte grayPixel = (byte) image[x][y];
                newImage[x][y] = argb((byte) 0xFF, grayPixel, grayPixel, grayPixel);
            }
        }

        return newImage;
    }

    /**
     * Build an ARGB image from the binary image
     * @implNote The result of this method will a black and white image, not the original image
     * @param image binary image representation
     * @return <b>black and white ARGB</b> representation
     */
    public static int[][] fromBinary(boolean[][] image){
        assert image != null;
        for (boolean[] bools : image) {
            assert (bools != null);
        }

        // handle empty image
        if (image.length == 0) return new int[0][0];

        int[][] newImage = new int[image.length][image[0].length];

        for (int x = 0; x < image.length; x++) {
            for (int y = 0; y < image[0].length; y++) {
                boolean isWhite = image[x][y];
                byte blackOrWhite = (byte) (isWhite ? 0xFF : 0);
                newImage[x][y] = argb((byte) 0xFF, blackOrWhite, blackOrWhite, blackOrWhite);
            }
        }

        return newImage;
    }

    public static byte extractPrimaryComponent(int pixel, int componentPos){
        //we assert that the component has a reasonable position
        assert componentPos <= 3;
        assert componentPos >= 0;

        int mask = 255;
        int shift = Byte.SIZE * componentPos;
        pixel >>= shift;
        pixel &= mask;
        return (byte) pixel;
    }
}
