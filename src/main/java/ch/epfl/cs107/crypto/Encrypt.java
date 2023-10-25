package ch.epfl.cs107.crypto;

import ch.epfl.cs107.Helper;

import java.util.Arrays;

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
        byte[] cipherText = new byte[plainText.length];

        for (int i = 0; i < plainText.length; i++) {
            int result = plainText[i] + key;

            if (result > 127) {
                result -= 256;
            } else if (result < -128) {
                result += 256;
            }

            cipherText[i] = (byte) result;
        }

        return cipherText;
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
        // ça c'est notre nouvelle phrase (de la même taille)
        // parce que c'est juste un décalage
        byte[] cipherText = new byte[plainText.length];

        // on boucle sur chaque caractère de notre phrase d'origine
        for (int i = 0; i < plainText.length; i++) {
            // on récupère l'élément et on lui ajoute le la clef qui correspond
            int idxOfKey = i % keyword.length;
            int shifted = (byte) plainText[i] + (byte) keyword[idxOfKey];
            if (shifted >= 128) {
                shifted -= 256;
            }
            if (shifted < -128) {
                shifted += 256;
            }
            cipherText[i] = (byte) shifted;
        }

        return cipherText;
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
        final int blockSize = iv.length;

        final int completeIterationCount = plainText.length / blockSize;
        final byte[] cipherText = new byte[plainText.length];

        byte[] lastPad = iv;

        for (int i = 0; i < completeIterationCount; i++) {
            // we take the corresponding text
            byte[] textPart = Arrays.copyOfRange(plainText, i * blockSize, (i + 1) * blockSize);
            // we encrypt it and save the result as the next pad
            lastPad = oneTimePad(textPart, lastPad);
            // then we add the ciphered text to the final res
            for (int j = 0; j < lastPad.length; j++) {
                cipherText[i*blockSize + j] = lastPad[j];
            }
        }

        if (plainText.length % blockSize != 0) {
            int startIndex = completeIterationCount * blockSize;
            byte[] lastTextPart = Arrays.copyOfRange(plainText, startIndex, plainText.length);
            lastPad = oneTimePad(lastTextPart, lastPad);
            for (int i = 0; i < lastPad.length; i++) {
                cipherText[completeIterationCount * blockSize + i] = lastPad[i];
            }
        }

        return cipherText;
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
        byte[] cipherText = new byte[plainText.length];

        for (int i = 0; i < plainText.length; i++) {
            cipherText[i] = (byte) (plainText[i] ^ key);
        }

        return cipherText;
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
        assert pad.length >= plainText.length;
        final byte[] cipherText = new byte[plainText.length];

        for (int i = 0; i < plainText.length; i++) {
            cipherText[i] = (byte) (plainText[i] ^ pad[i]);
        }

        return cipherText;
    }

    /**
     * Method to encode a byte array using a one-time pad
     * @param plainText Plain text to encode
     * @param pad Array containing the used pad after the execution
     * @param result Array containing the result after the execution
     */
    public static void oneTimePad(byte[] plainText, byte[] pad, byte[] result) {
        // ???
    }

}