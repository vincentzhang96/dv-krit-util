package com.divinitor.kritika.util.crypto;

public class RotatingXor {

    /**
     * XOR key.
     */
    private final int key;

    /**
     * XOR key as bytes.
     */
    protected byte[] keyBytes;

    /**
     * Current XOR offset. Always [0, 3].
     */
    private int offset;

    /**
     * Precomputed rotated keys. Each index equates to an offset, so 4 entries total.
     */
    protected int[] rotations;

    /**
     * Creates a rotating XOR with the given key and offset mod 4.
     * @param key The XOR key
     * @param offset The XOR offset, mod 4
     */
    public RotatingXor(int key, int offset) {
        this.key = key;
        this.offset = offset % 4;
        this.init();
    }

    /**
     * Creates a rotating XOR with the given key and no offset.
     * @param key The XOR key
     */
    public RotatingXor(int key) {
        this.key = key;
        this.offset = 0;
        this.init();
    }

    /**
     * Initializes internal structures for faster XORing (key as bytes, rotated keys, etc).
     */
    private void init() {
        this.keyBytes = new byte[4];
        this.keyBytes[0] = (byte) ((this.key >>> 24) & 0xFF);
        this.keyBytes[1] = (byte) ((this.key >>> 16) & 0xFF);
        this.keyBytes[2] = (byte) ((this.key >>> 8) & 0xFF);
        this.keyBytes[3] = (byte) (this.key & 0xFF);

        rotations = new int[4];

        rotations[0] = key;
        rotations[1] = ((key << 8) | ((key & 0xFF000000) >>> 24));
        rotations[2] = ((key << 16) | ((key & 0xFFFF0000) >>> 16));
        rotations[3] = ((key << 24) | ((key & 0xFFFFFF00) >>> 8));
    }

    /**
     * Increments the offset, mod 4.
     */
    private void modIncr() {
        ++this.offset;
        if (this.offset > 3) {
            this.offset = 0;
        }
    }

    /**
     * Gets the current XOR offset.
     * @return The current XOR offset
     */
    public int getOffset() {
        return this.offset;
    }

    /**
     * Gets the XOR key.
     * @return The XOR key
     */
    public int getKey() {
        return key;
    }

    /**
     * Sets the current XOR offset (mod 4).
     * @param i The new offset
     * @return The new offset mod 4
     */
    public int setOffset(int i) {
        this.offset = i % 4;
        return this.offset;
    }

    /**
     * XORs a single byte.
     * @param b The byte to XOR
     * @return The XORed byte
     */
    public byte xor(byte b) {
        byte ret = (byte) ((this.keyBytes[offset] ^ b) & 0xFF);
        this.modIncr();
        return ret;
    }

    /**
     * XORs a single int.
     * @param i The int to XOR
     * @return The XORed int
     */
    public int xor(int i) {
        //  Don't need to increment since multiple of 4
        return this.rotations[this.offset] ^ i;
    }

    /**
     * XORs a single long.
     * @param l The long to XOR
     * @return The XORed long
     */
    public long xor(long l) {
        int a = this.xor((int) l);
        int b = this.xor((int) (l >>> 32));
        return a | (((long) b) << 32L);
    }

    /**
     * XORs a range within a byte array in-place.
     * @param b The byte array to XOR
     * @param pos Starting index
     * @param len Number of bytes to XOR
     */
    public void xor(byte[] b, int pos, int len) {
        byte[] rotatedKeyBytes = new byte[4];
        int rotatedKey = this.rotations[this.offset];
        rotatedKeyBytes[0] = (byte) ((rotatedKey >>> 24) & 0xFF);
        rotatedKeyBytes[1] = (byte) ((rotatedKey >>> 16) & 0xFF);
        rotatedKeyBytes[2] = (byte) ((rotatedKey >>> 8) & 0xFF);
        rotatedKeyBytes[3] = (byte) (rotatedKey & 0xFF);

        //  Unroll the xor loop a bit (Candidate for SIMD)
        for (int i = 0; i < len - 4; i += 4) {
            int j = i + pos;
            b[j] ^= rotatedKeyBytes[0];
            b[j + 1] ^= rotatedKeyBytes[1];
            b[j + 2] ^= rotatedKeyBytes[2];
            b[j + 3] ^= rotatedKeyBytes[3];
        }

        //  Remaining elements
        int remaining  = len % 4;
        for (int i = 0; i < remaining; ++i) {
            b[pos + len - i - 1] ^= this.keyBytes[this.offset];
            this.modIncr();
        }
    }

    /**
     * XORs the entire byte array in-place.
     * @param b The byte array to XOR
     */
    public void xor(byte[] b) {
        this.xor(b, 0, b.length);
    }

    /**
     * Resets the offset.
     */
    public void reset() {
        this.setOffset(0);
    }
}
