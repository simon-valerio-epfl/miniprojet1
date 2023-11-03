package ch.epfl.cs107.crypto;

import ch.epfl.cs107.Helper;

import java.util.Random;

import static ch.epfl.cs107.utils.Text.*;
import static ch.epfl.cs107.utils.Image.*;
import static ch.epfl.cs107.utils.Bit.*;
import static ch.epfl.cs107.stegano.ImageSteganography.*;
import static ch.epfl.cs107.stegano.TextSteganography.*;
import static ch.epfl.cs107.crypto.Encrypt.*;
import static ch.epfl.cs107.crypto.Decrypt.*;
import static ch.epfl.cs107.Main.*;

/**
 * <b>Task 2: </b>Utility class to encrypt a given plain text.
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Encrypt {

    // DO NOT CHANGE THIS, MORE ON THAT ON WEEK 7
    private Encrypt(){}

    // ============================================================================================
    // ================================== CAESAR'S ENCRYPTION =====================================
    // ============================================================================================

    /**
     * Method to encode a byte array message using a single character key
     * the key is simply added to each byte of the original message
     *
     * @param plainText The byte array representing the string to encode
     * @param key the byte corresponding to the char we use to shift
     * @return an encoded byte array
     */
    public static byte[] caesar(byte[] plainText, byte key) {
        assert plainText!=null;
        byte[] caesarText = new byte[plainText.length];
        for (int i = 0; i < plainText.length; i++) {
            caesarText[i] = (byte) (plainText[i] + key);
        }
        return caesarText;

    }

    // ============================================================================================
    // =============================== VIGENERE'S ENCRYPTION ======================================
    // ============================================================================================

    /**
     * Method to encode a byte array using a byte array keyword
     * The keyword is repeated along the message to encode
     * The bytes of the keyword are added to those of the message to encode
     * @param plainText the byte array representing the message to encode
     * @param keyword the byte array representing the key used to perform the shift
     * @return an encoded byte array
     */
    public static byte[] vigenere(byte[] plainText, byte[] keyword) {
        assert plainText!=null;
        assert keyword!=null;
        assert keyword.length!=0;
        byte[] vigenereText = new byte[plainText.length];
        for (int i = 0; i < plainText.length; i++) {
            vigenereText[i] = (byte) (plainText[i] + keyword[i]);
        }
        return vigenereText;
    }

    // ============================================================================================
    // =================================== CBC'S ENCRYPTION =======================================
    // ============================================================================================

    /**
     * Method applying a basic chain block counter of XOR without encryption method.
     * @param plainText the byte array representing the string to encode
     * @param iv the pad of size BLOCKSIZE we use to start the chain encoding
     * @return an encoded byte array
     */
    public static byte[] cbc(byte[] plainText, byte[] iv) {
        assert (plainText!=null && iv!=null);
        byte[] cbcText = new byte[plainText.length];
        final int SIZE = iv.length;
        int byteCounter;
        int blockCounter =0;
        do {
            byteCounter = blockCounter*SIZE;
            for (int i = 0; i < SIZE && (i < plainText.length-byteCounter); i++) {
                cbcText[i + byteCounter] = (byte) (plainText[i + byteCounter] ^ iv[i]);
                iv[i] = cbcText[i + byteCounter];
            }
            blockCounter++;
        }while (byteCounter < plainText.length);
        return cbcText;
    }

    // ============================================================================================
    // =================================== XOR'S ENCRYPTION =======================================
    // ============================================================================================

    /**
     * Method to encode a byte array using a XOR with a single byte long key
     * @param plainText the byte array representing the string to encode
     * @param key the byte we will use to XOR
     * @return an encoded byte array
     */
    public static byte[] xor(byte[] plainText, byte key) {
        assert  (plainText!=null);
        byte[] xorText = new byte[plainText.length];
        for (int i = 0; i < plainText.length; i++) {
            xorText[i] = (byte) (plainText[i] ^ key);
        }
        return xorText;
    }

    // ============================================================================================
    // =================================== ONETIME'S PAD ENCRYPTION ===============================
    // ============================================================================================

    /**
     * Method to encode a byte array using a one-time pad of the same length.
     *  The method XOR them together.
     * @param plainText the byte array representing the string to encode
     * @param pad the one-time pad
     * @return an encoded byte array
     */
    public static byte[] oneTimePad(byte[] plainText, byte[] pad) {
        assert plainText!= null;
        assert pad!=null;
        assert pad.length==plainText.length;
        byte[] oneTimeText = new byte[plainText.length];
        for (int i = 0; i < plainText.length; i++) {
            oneTimeText[i] = (byte) (plainText[i] ^ pad[i]);
        }
        return oneTimeText;

    }

    /**
     * Method to encode a byte array using a one-time pad
     * @param plainText Plain text to encode
     * @param pad Array containing the used pad after the execution
     * @param result Array containing the result after the execution
     */
    public static void oneTimePad(byte[] plainText, byte[] pad, byte[] result) {
        assert (plainText!=null && pad.length >= plainText.length);
        Random randomGenerator = new Random(2l);
        randomGenerator.nextBytes(pad);
        result = oneTimePad(plainText, pad);


    }

}