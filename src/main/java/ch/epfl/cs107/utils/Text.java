package ch.epfl.cs107.utils;

import ch.epfl.cs107.Helper;

import java.nio.charset.StandardCharsets;

import static ch.epfl.cs107.utils.Text.*;
import static ch.epfl.cs107.utils.Image.*;
import static ch.epfl.cs107.utils.Bit.*;
import static ch.epfl.cs107.stegano.ImageSteganography.*;
import static ch.epfl.cs107.stegano.TextSteganography.*;
import static ch.epfl.cs107.crypto.Encrypt.*;
import static ch.epfl.cs107.crypto.Decrypt.*;
import static ch.epfl.cs107.Main.*;


/**
 * <b>Task 1.2: </b>Utility class to manipulate texts ({@link String}) objects.
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Text {

    // DO NOT CHANGE THIS, MORE ON THAT ON WEEK 7
    private Text(){}

    // ============================================================================================
    // ==================================== STRING MANIPULATION ===================================
    // ============================================================================================

    /**
     * Convert a given <b>String</b> into a <b>byte[]</b> following the <b>UTF-8</b> convention
     * @param str String to convert
     * @return bytes representation of the String in the <b>UTF-8</b> format
     */
    public static byte[] toBytes(String str){
        return str.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Convert a given String into a boolean array representation
     * @implNote the bit array should be based on an <b>UTF-8</b> representation
     * @param str String to convert
     * @return <b>UTF-8</b> representation of the string in the <b>bit array</b> format
     */
    public static boolean[] toBitArray(String str){
        // first we convert our string to bytes
        byte[] strBytes = toBytes(str);
        // here, we will store our bits
        int totalBitCount = strBytes.length * Byte.SIZE;
        boolean[] strBits = new boolean[totalBitCount];
        // for every byte
        for (int i = 0; i < strBytes.length; i++) {
            // we extract the bits from the byte
            // and we add them to the final array
            boolean[] byteBits = Bit.toBitArray(strBytes[i]);
            for (int j = 0; j < Byte.SIZE; j++) {
                int index = i * Byte.SIZE + j;
                strBits[index] = byteBits[j];
            }
        }
        return strBits;
    }

    /**
     * Convert a given <b>byte[]</b> into a <b>String</b> following the <b>UTF-8</b> convention
     * @param bytes String in the byte array format
     * @return String representation using the {@link String} type
     */
    public static String toString(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * Convert a given bit array to a String
     * @param bitArray <b>UTF-8</b> compatible bit array
     * @return <b>UTF-8 String</b> representation of the bit array
     */
    public static String toString(boolean[] bitArray) {
        assert bitArray.length > 0;

        int processedBitCount = 0;
        int processedByteCount = 0;

        int padding = bitArray.length % Byte.SIZE == 0 ? 0 : 1;
        int byteCount = bitArray.length / Byte.SIZE + padding;

        // this will store all the final bytes in the string
        byte[] strBytes = new byte[byteCount];

        // while we have bits to process
        while (bitArray.length > processedBitCount) {

            // create an array that will store all the bits related to our current byte
            int bitsToProcessCount = Math.min(Byte.SIZE, bitArray.length - processedBitCount);
            boolean[] byteBitArray = new boolean[bitsToProcessCount];
            for (int i = 0; i < bitsToProcessCount; i++) {
                byteBitArray[i] = bitArray[i + processedBitCount];
            }

            // we now have made an array of max 8 bits that we can transform into a byte
            strBytes[processedByteCount] = Bit.toByte(byteBitArray);
            processedByteCount++;
            processedBitCount += bitsToProcessCount;
        }
        String outputText = Text.toString(strBytes);
        return outputText;
    }

}
