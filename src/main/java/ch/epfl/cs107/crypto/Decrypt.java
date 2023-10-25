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
 * <b>Task 2: </b>Utility class to decrypt a given cipher text.
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Decrypt {

    // DO NOT CHANGE THIS, MORE ON THAT ON WEEK 7
    private Decrypt(){}

    // ============================================================================================
    // ================================== CAESAR'S ENCRYPTION =====================================
    // ============================================================================================

    /**
     * Method to decode a byte array message using a single character key
     * <p>
     * @param cipher Cipher message to decode
     * @param key Key to decode with
     * @return decoded message
     */
    public static byte[] caesar(byte[] cipher, byte key) {
        byte[] plainText = new byte[cipher.length];

        for (int i = 0; i < cipher.length; i++) {
            int result = cipher[i] - key;

            if (result > 127) {
                result -= 256;
            } else if (result < -128) {
                result += 256;
            }

            plainText[i] = (byte) result;
        }

        return plainText;
    }

// ============================================================================================
    // =============================== VIGENERE'S ENCRYPTION ======================================
    // ============================================================================================

    /**
     * Method to encode a byte array using a byte array keyword
     * @param cipher Cipher message to decode
     * @param keyword Key to decode with
     * @return decoded message
     */
    public static byte[] vigenere(byte[] cipher, byte[] keyword) {
        // ça c'est notre nouvelle phrase (de la même taille)
        // parce que c'est juste un décalage
        byte[] plainText = new byte[cipher.length];

        // on boucle sur chaque caractère de notre phrase d'origine
        for (int i = 0; i < cipher.length; i++) {
            // on récupère l'élément et on lui ajoute le la clef qui correspond
            int idxOfKey = i % keyword.length;
            int shifted = (byte) cipher[i] - (byte) keyword[idxOfKey];
            if (shifted >= 128) {
                shifted -= 256;
            }
            if (shifted < -128) {
                shifted += 256;
            }
            plainText[i] = (byte) shifted;
        }

        return plainText;
    }

    // ============================================================================================
    // =================================== CBC'S ENCRYPTION =======================================
    // ============================================================================================

    /**
     * Method to decode cbc-encrypted ciphers
     * @param cipher message to decode
     * @param iv the pad of size BLOCKSIZE we use to start the chain encoding
     * @return decoded message
     */
    public static byte[] cbc(byte[] cipher, byte[] iv) {
        final int blockSize = iv.length;
        final int completeIterationCount = cipher.length / blockSize;
        final byte[] plainText = new byte[cipher.length];

        byte[] lastCipherBlock = iv;

        for (int i = 0; i < completeIterationCount; i++) {
            byte[] currentCipherBlock = Arrays.copyOfRange(cipher, i * blockSize, (i + 1) * blockSize);
            byte[] decryptedBlock = oneTimePad(currentCipherBlock, lastCipherBlock);
            System.arraycopy(decryptedBlock, 0, plainText, i * blockSize, decryptedBlock.length);
            lastCipherBlock = currentCipherBlock;
        }

        if (cipher.length % blockSize != 0) {
            int startIndex = completeIterationCount * blockSize;
            byte[] currentCipherBlock = Arrays.copyOfRange(cipher, startIndex, plainText.length);
            lastCipherBlock = oneTimePad(currentCipherBlock, lastCipherBlock);
            for (int i = 0; i < lastCipherBlock.length; i++) {
                plainText[completeIterationCount * blockSize + i] = lastCipherBlock[i];
            }
        }

        return plainText;
    }

    // ============================================================================================
    // =================================== XOR'S ENCRYPTION =======================================
    // ============================================================================================

    /**
     * Method to decode xor-encrypted ciphers
     * @param cipher text to decode
     * @param key the byte we will use to XOR
     * @return decoded message
     */
    public static byte[] xor(byte[] cipher, byte key) {
        return Encrypt.xor(cipher, key);
    }

    // ============================================================================================
    // =================================== ONETIME'S PAD ENCRYPTION ===============================
    // ============================================================================================

    /**
     * Method to decode otp-encrypted ciphers
     * @param cipher text to decode
     * @param pad the one-time pad to use
     * @return decoded message
     */
    public static byte[] oneTimePad(byte[] cipher, byte[] pad) {
        return Encrypt.oneTimePad(cipher, pad);
    }

}
