package ch.epfl.cs107.stegano;

import ch.epfl.cs107.Helper;
import ch.epfl.cs107.utils.Bit;
import ch.epfl.cs107.utils.Text;

import static ch.epfl.cs107.utils.Text.*;
import static ch.epfl.cs107.utils.Image.*;
import static ch.epfl.cs107.utils.Bit.*;
import static ch.epfl.cs107.stegano.ImageSteganography.*;
import static ch.epfl.cs107.stegano.TextSteganography.*;
import static ch.epfl.cs107.crypto.Encrypt.*;
import static ch.epfl.cs107.crypto.Decrypt.*;
import static ch.epfl.cs107.Main.*;

/**
 * <b>Task 3.2: </b>Utility class to perform Text Steganography
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 * @version 1.0.0
 * @since 1.0.0
 */
public class TextSteganography {

    // DO NOT CHANGE THIS, MORE ON THAT ON WEEK 7
    private TextSteganography(){}

    // ============================================================================================
    // =================================== EMBEDDING BIT ARRAY ====================================
    // ============================================================================================

    /**
     * Embed a bitmap message in an ARGB image
     * @param cover Cover image
     * @param message Embedded message
     * @return ARGB image with the message embedded
     */
    public static int[][] embedBitArray(int[][] cover, boolean[] message) {
        int[][] newCover = new int[cover.length][];

        for (int coordsH = 0; coordsH < cover.length; coordsH++) { // for each row
            for (int coordsL = 0; coordsL < cover[coordsH].length; coordsL++) { // for each pixel in the row
                if (newCover[coordsH]== null) newCover[coordsH] = new int[cover[coordsH].length];

                int pixel = cover[coordsH][coordsL];

                int currentMessageIdx = coordsH * cover[coordsH].length + coordsL;
                boolean hasNextChar = message.length > currentMessageIdx;
                if (hasNextChar) {
                    boolean nextChar = message[currentMessageIdx];
                    int newPixel = embedInLSB(pixel, nextChar);
                    newCover[coordsH][coordsL] = newPixel;
                } else {
                    newCover[coordsH][coordsL] = pixel;
                }
            }
        }

        return newCover;
    }

    /**
     * Extract a bitmap from an image
     * @param image Image to extract from
     * @return extracted message
     */
    public static boolean[] revealBitArray(int[][] image) {
        final boolean[] res = new boolean[image.length * image[0].length];

        for (int coordsH = 0; coordsH < image.length; coordsH++) { // for each row
            for (int coordsL = 0; coordsL < image[coordsH].length; coordsL++) { // for each pixel in the row
                int pixel = image[coordsH][coordsL];
                int currentMessageIdx = coordsH * image[coordsH].length + coordsL;
                res[currentMessageIdx] = Bit.getLSB(pixel);
            }
        }
        return res;
    }



    // ============================================================================================
    // ===================================== EMBEDDING STRING =====================================
    // ============================================================================================

    /**
     * Embed a String message in an ARGB image
     * @param cover Cover image
     * @param message Embedded message
     * @return ARGB image with the message embedded
     */
    public static int[][] embedText(int[][] cover, byte[] message) {
        return embedBitArray(cover, Text.toBitArray(Text.toString(message)));
    }

    /**
     * Extract a String from an image
     * @param image Image to extract from
     * @return extracted message
     */
    public static byte[] revealText(int[][] image) {
        return Text.toBytes(Text.toString(revealBitArray(image)));
    }

}
