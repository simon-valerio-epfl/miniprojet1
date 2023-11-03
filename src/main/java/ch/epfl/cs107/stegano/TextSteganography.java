package ch.epfl.cs107.stegano;

import ch.epfl.cs107.Helper;
import ch.epfl.cs107.crypto.Decrypt;
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
        assert cover!=null;
        assert message!=null;
        assert cover.length!=0;
        assert cover[0] !=null;
        assert cover[0].length != 0;
        for(int[] row : cover){
            assert row != null;
            assert row.length == cover[0].length;
        }

        int[][] newImage = new int[cover.length][cover[0].length];

        for (int x = 0; x < cover.length; x++) {
            for (int y = 0; y < cover[0].length; y++) {
                int processedBool = x * cover[0].length + y;
                int pixelCover = cover[x][y];
                if (message.length > processedBool) {
                    newImage[x][y] = embedInLSB(pixelCover, message[processedBool]);
                } else {
                    newImage[x][y] = pixelCover;
                }
            }
        }

        return newImage;
    }

    /**
     * Extract a bitmap from an image
     * @param image Image to extract from
     * @return extracted message
     */
    public static boolean[] revealBitArray(int[][] image) {
        assert image != null;
        if (image.length == 0) {
            return new boolean[0];
        }
        assert image[0] != null;
        assert image[0].length!=0;
        for (int i = 1; i < image.length; i++) {
            assert image[i] != null;
            assert image[i].length == image[0].length;
        }
        boolean[] revealedText = new boolean[image.length * image[0].length];

        for (int x = 0; x < image.length; x++) {
            for (int y = 0; y < image[0].length; y++) {
                int pixelImage = image[x][y];
                boolean lsb = getLSB(pixelImage);
                int processedBool = x * image[0].length + y;
                revealedText[processedBool] = lsb;
            }
        }

        return revealedText;
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
        assert cover!=null;
        assert message!=null;
        if (message.length == 0) return cover;
        for (int i = 0; i < cover.length; i++) {
            assert cover[i] != null;
            assert cover[i].length > 0;
            assert cover[i].length == cover[0].length;
        }
        return embedBitArray(cover, Text.toBitArray( // from string to boolean[]
                Text.toString(message) // from byte[] to string
        ));
    }

    /**
     * Extract a String from an image
     * @param image Image to extract from
     * @return extracted message
     */
    public static byte[] revealText(int[][] image) {
        return Text.toBytes(
                revealBitArray(image));
    }

    public static void revealChallengeText(int[][] image) {
        var key = Text.toBytes("adele");
        System.out.println(Text.toString(Decrypt.vigenere(revealText(image), key)));
    }

}
