package ch.epfl.cs107.utils;

import java.nio.charset.StandardCharsets;

public class Test {
    public static void main(String[] args) {
        /*
        int value = 255;
        boolean[] computed = new boolean[Integer.SIZE];
        for (int i = 0; i < Integer.SIZE; ++i) {
            computed[i] = getXthBit(value , i);
            System.out.println(computed[i]);
        }
        */

        for (int i = 0; i < atoBitArray(toString(atoBitArray("ô$"))).length; i++) {
            System.out.println(atoBitArray(toString(atoBitArray("ô$")))[i]);
        }

    }

    public static boolean getXthBit(int value, int pos) {
        if (pos > Integer.SIZE) {
            //return Helper.fail("Pos should be less than 32.");
        }
        String binaryString = Integer.toBinaryString(value); // for e.g. 1111111 for 255
        final int zerosToAdd = Integer.SIZE - binaryString.length();
        binaryString = "0".repeat(zerosToAdd) + binaryString;
        final int newPos = Integer.SIZE - 1 - pos;
        return binaryString.charAt(newPos) == '1';
    }

    public static int embedInXthBit(int value, boolean m, int pos) {
        StringBuilder newBinaryString = new StringBuilder();
        for (int i = 0; i < Integer.SIZE; i++) {
            if (pos == i) {
                newBinaryString.append(m ? '1' : '0');
            } else {
                newBinaryString.insert(0, getXthBit(value, i) ? '1' : '0');
            }
        }
        return Integer.parseUnsignedInt(newBinaryString.toString(), 2);
    }

    public static int embedInLSB(int value, boolean m){
        int LSBPosition = 0;
        return embedInXthBit(value, m, LSBPosition);
    }

    public static boolean[] toBitArray(byte value){
        // there are 8 bits in a byte
        // so let's create our array with our final values
        boolean[] res = new boolean[8];
        for (int i = 0; i < 8; i++) {
            res[7 - i] = (value & (1 << i)) != 0;
        }
        return res;
    }

    /**
     * Convert a boolean array to a byte
     * <p>
     * Suppose we have the following input :
     * <b>[<code>false</code>,
     *     <code>false</code>,
     *     <code>true</code>,
     *     <code>true</code>,
     *     <code>false</code>,
     *     <code>true</code>,
     *     <code>true</code>,
     *     <code>false</code>]</b>
     * We can expect this function to return the following byte : <b><code>0b00_11_01_10</code></b>.
     * @param bitArray bit array representation to convert
     * @return the byte representation of the bit array
     */
    public static byte toByte(boolean[] bitArray){
        byte res = 0;
        for (int i = 0; i < 8; i++) {
            if (bitArray[7 - i]) {
                res |= (byte) (1 << i);
            }
        }
        return res;
    }

    public static boolean[] atoBitArray(String str){
        byte[] bytesInString = str.getBytes(StandardCharsets.UTF_8);
        boolean[] res = new boolean[bytesInString.length * 8];

        for (int i = 0; i < bytesInString.length; i++) {
            boolean[] bytesAsBits = Bit.toBitArray(bytesInString[i]);
            for (int j = 0; j < 8; j++) {
                res[ i * 8 + j ] = bytesAsBits[j];
            }
        }
        return res;
    }

    public static String toString(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * Convert a given bit array to a String
     * @param bitArray <b>UTF-8</b> compatible bit array
     * @return <b>UTF-8 String</b> representation of the bit array
     */
    public static String toString(boolean[] bitArray) {
        int charInString = bitArray.length / 8;
        byte[] finalBytes = new byte[charInString];
        for (int i = 0; i < charInString; i++){
            boolean[] charBytes = new boolean[8];
            // TODO: should we replace with Sys array copy?
            for (int j = 0; j < 8; j++) {
                charBytes[j] = bitArray[i * 8 + j];
            }
            byte charByte = Bit.toByte(charBytes);
            finalBytes[i] = charByte;
        }
        return toString(finalBytes);
    }
    
}
