package ch.epfl.cs107.utils;
import java.nio.charset.StandardCharsets;

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

    /**
     * Converts a given bit array (represented with booleans) to an array of actual bytes
     * @param bitArray
     * @return the array of bytes
     */
    public static byte[] toBytes(boolean[] bitArray) {
        assert bitArray != null;
        assert bitArray.length != 0;

        int charCount = bitArray.length / Byte.SIZE;
        byte[] strBytes = new byte[charCount];
        for (int i = 0; i < charCount; i++){
            for (int j = 0; j < Byte.SIZE; j++) {
                if (bitArray[i * Byte.SIZE + j]) {
                    strBytes[i] |= (byte) (1 << (Byte.SIZE - (j+1)));
                }
            }
        }

        return strBytes;
    }


}
