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
        assert !str.isEmpty();
        return str.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Convert a given String into a boolean array representation
     * @implNote the bit array should be based on an <b>UTF-8</b> representation
     * @param str String to convert
     * @return <b>UTF-8</b> representation of the string in the <b>bit array</b> format
     */
    public static boolean[] toBitArray(String str){
        assert str != null;
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
        assert bytes!=null;
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * Convert a given bit array to a String
     * @param bitArray <b>UTF-8</b> compatible bit array
     * @return <b>UTF-8 String</b> representation of the bit array
     */
    public static String toString(boolean[] bitArray) {
        return toString(toBytes(bitArray));
    }

    public static byte[] toBytes(boolean[] bitArray) {
        assert bitArray != null;
        assert bitArray.length!=0;
        int charCount = bitArray.length / Byte.SIZE;
        byte[] strBytes = new byte[charCount];
        for (int i = 0; i < charCount; i++){
            for (int j = 0; j < 8; j++) {
                if (bitArray[i * 8 + j]) {
                    strBytes[i] |= (byte) (1 << (7 - j));
                }
            }
        }
        return strBytes;
    }


}
