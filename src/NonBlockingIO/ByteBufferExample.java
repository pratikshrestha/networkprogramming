import java.nio.ByteBuffer;

public class ByteBufferExample {
    public static void main(String[] args) {
        // Create a ByteBuffer with a capacity of 16 bytes
        ByteBuffer buffer = ByteBuffer.allocate(16);

        // Put a char value into the buffer
        buffer.putChar('A');

        // Put a short value into the buffer
        buffer.putShort((short) 123);

        // Put an int value into the buffer
        buffer.putInt(456789);

        // Put a long value into the buffer
        buffer.putLong(9876543210L);

        // Rewind the buffer to prepare for reading
        buffer.rewind();

        // Get and print the char value from the buffer
        char charValue = buffer.getChar();
        System.out.println("Char value: " + charValue);

        // Get and print the short value from the buffer
        short shortValue = buffer.getShort();
        System.out.println("Short value: " + shortValue);

        // Get and print the int value from the buffer
        int intValue = buffer.getInt();
        System.out.println("Int value: " + intValue);

        // Get and print the long value from the buffer
        long longValue = buffer.getLong();
        System.out.println("Long value: " + longValue);
    }
}