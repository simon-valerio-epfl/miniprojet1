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

/**
 * <b>Task 1.1: </b>Utility class to manipulate bits
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Bit {

    // DO NOT CHANGE THIS, MORE ON THAT ON WEEK 7
    private Bit(){}

    // ============================================================================================
    // ==================================== BIT MANIPULATION ======================================
    // ============================================================================================

    public static final int MAX_VALUE_FOR_BYTE = 255;

    /**
     * Embed a bit in a given integer bits
     * <p>
     * @param value value to embed in
     * @param m <code>true</code> to embed 1, <code>false</code> to embed 0
     * @param pos position of the bit to change
     * @return embedded value
     */
    public static int embedInXthBit(int value, boolean m, int pos) {
        assert value !=0;
        assert  pos >=0;
        assert pos<=Integer.SIZE ;
        int mask = 1 << pos;
        int reversedMask = ~mask;
        int newValue;
        // if we want to replace our actual bit by a 1
        if (m) newValue = value | mask;
        // or 0
        else newValue = value & reversedMask;
        return newValue;
    }

    /**
     * Embed a bit in the "least significant bit" (LSB)
     * <p>
     * @param value value to embed in
     * @param m <code>true</code> to embed 1, <code>false</code> to embed 0
     * @return embedded value
     */
    public static int embedInLSB(int value, boolean m){
        assert value!=0;
        int LSBPosition = 0;
        return embedInXthBit(value, m, LSBPosition);
    }

    /**
     * Extract a bit in a given position from a given value
     * <p>
     * @param value value to extract from
     * @param pos position of the bit to extract
     * @return <code>true</code> if the bit is '1' and <code>false</code> otherwise
     */
    public static boolean getXthBit(int value, int pos) {
        assert pos <= Integer.SIZE;
        assert pos>=0;
        int mask = 1 << pos;
        return (value & mask) != 0;
    }

    /**
     * Extract the 'least significant bit' from a given value
     * <p>
     * @param value value to extract from
     * @return <code>true</code> if the bit is '1' and <code>false</code> otherwise
     */
    public static boolean getLSB(int value) {
        assert value!=0;
        int LSBPosition = 0;
        return getXthBit(value, LSBPosition);
    }

    // ============================================================================================
    // ==================================== BYTE MANIPULATION =====================================
    // ============================================================================================

    /**
     * Convert a byte value to the bit array representation.
     * <p>
     * Suppose we have the following input <b><code>0b00_11_01_10</code></b>. We can expect this function
     * to return the following array :
     * <b>[<code>false</code>,
     *     <code>false</code>,
     *     <code>true</code>,
     *     <code>true</code>,
     *     <code>false</code>,
     *     <code>true</code>,
     *     <code>true</code>,
     *     <code>false</code>]</b>
     * @param value byte representation of the value
     * @return bit array representation of the value
     */
    public static boolean[] toBitArray(byte value){
        boolean[] bitArray = new boolean[Byte.SIZE];
        for (int i = 0; i < Byte.SIZE; i++) {
            bitArray[Byte.SIZE-(i+1)] = getXthBit(value, i);
        }
        return bitArray;
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
        assert bitArray.length != 0;
        byte myByte = 0;
        for(boolean bit: bitArray){
            myByte <<= 1;
            if (bit) {
                myByte += 1;
            }
        }
        return myByte;
    }

    /*
     * Converts an array of booleans to an array of bytes
     *
     * @param bitArray bit array representation to convert
     * @return the array of bytes
     */
    public static byte[] toBytes (boolean[] bitArray) {
        // A CONTROLER
        assert bitArray!=null;
        assert bitArray.length==8;
        int processedBitCount = 0;
        int padding = bitArray.length % Byte.SIZE == 0 ? 0 : 1;
        int byteCount = bitArray.length / Byte.SIZE + padding;

        // this will store all the final bytes in the string
        byte[] strBytes = new byte[byteCount];

        // while we have bits to process
        for (int i = 0; i < byteCount; i++) {

            // create an array that will store all the bits related to our current byte
            int bitsToProcessCount = Math.min(Byte.SIZE, bitArray.length - processedBitCount);
            boolean[] byteBitArray = new boolean[bitsToProcessCount];
            for (int j = 0; j < bitsToProcessCount; j++) {
                byteBitArray[j] = bitArray[j + processedBitCount];
            }

            // we now have made an array of max 8 bits that we can transform into a byte
            strBytes[i] = Bit.toByte(byteBitArray);
            processedBitCount += bitsToProcessCount;

        }

        return strBytes;
    }

}
