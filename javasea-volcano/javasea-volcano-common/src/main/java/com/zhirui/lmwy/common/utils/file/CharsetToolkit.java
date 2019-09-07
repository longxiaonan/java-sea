package com.zhirui.lmwy.common.utils.file;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Collection;

/**
 * Created by 包维君 on 2018/1/15.
 */

public class CharsetToolkit
{
    private byte[] buffer;
    private Charset defaultCharset;
    private boolean enforce8Bit = false;

    public CharsetToolkit(byte[] buffer)
    {
        this.buffer = buffer;
        this.defaultCharset = getDefaultSystemCharset();
    }

    public CharsetToolkit(byte[] buffer, Charset defaultCharset)
    {
        this.buffer = buffer;
        setDefaultCharset(defaultCharset);
    }

    public void setDefaultCharset(Charset defaultCharset)
    {
        if (defaultCharset != null)
            this.defaultCharset = defaultCharset;
        else
            this.defaultCharset = getDefaultSystemCharset();
    }

    public void setEnforce8Bit(boolean enforce)
    {
        this.enforce8Bit = enforce;
    }

    public boolean getEnforce8Bit()
    {
        return this.enforce8Bit;
    }

    public Charset getDefaultCharset()
    {
        return this.defaultCharset;
    }

    public Charset guessEncoding()
    {
        if (hasUTF8Bom(this.buffer))
            return Charset.forName("UTF-8");
        if (hasUTF16LEBom(this.buffer))
            return Charset.forName("UTF-16LE");
        if (hasUTF16BEBom(this.buffer)) {
            return Charset.forName("UTF-16BE");
        }

        boolean highOrderBit = false;

        boolean validU8Char = true;

        int length = this.buffer.length;
        int i = 0;
        while (i < length - 6) {
            byte b0 = this.buffer[i];
            byte b1 = this.buffer[(i + 1)];
            byte b2 = this.buffer[(i + 2)];
            byte b3 = this.buffer[(i + 3)];
            byte b4 = this.buffer[(i + 4)];
            byte b5 = this.buffer[(i + 5)];
            if (b0 < 0)
            {
                highOrderBit = true;

                if (isTwoBytesSequence(b0))
                {
                    if (!(isContinuationChar(b1)))
                        validU8Char = false;
                    else {
                        ++i;
                    }
                }
                else if (isThreeBytesSequence(b0))
                {
                    if ((!(isContinuationChar(b1))) || (!(isContinuationChar(b2))))
                        validU8Char = false;
                    else {
                        i += 2;
                    }
                }
                else if (isFourBytesSequence(b0))
                {
                    if ((!(isContinuationChar(b1))) || (!(isContinuationChar(b2))) || (!(isContinuationChar(b3))))
                        validU8Char = false;
                    else {
                        i += 3;
                    }
                }
                else if (isFiveBytesSequence(b0))
                {
                    if ((!(isContinuationChar(b1))) || (!(isContinuationChar(b2))) || (!(isContinuationChar(b3))) || (!(isContinuationChar(b4))))
                        validU8Char = false;
                    else {
                        i += 4;
                    }
                }
                else if (isSixBytesSequence(b0))
                {
                    if ((!(isContinuationChar(b1))) || (!(isContinuationChar(b2))) || (!(isContinuationChar(b3))) || (!(isContinuationChar(b4))) || (!(isContinuationChar(b5))))
                    {
                        validU8Char = false;
                    }
                    else i += 5;
                }
                else validU8Char = false;
            }
            if (!(validU8Char))
                break;
            ++i;
        }

        if (!(highOrderBit))
        {
            if (this.enforce8Bit) {
                return this.defaultCharset;
            }
            return Charset.forName("US-ASCII");
        }

        if (validU8Char) {
            return Charset.forName("UTF-8");
        }
        return this.defaultCharset;
    }

    public static Charset guessEncoding(File f, int bufferLength) throws FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream(f);
        byte[] buffer = new byte[bufferLength];
        fis.read(buffer);
        fis.close();
        CharsetToolkit toolkit = new CharsetToolkit(buffer);
        toolkit.setDefaultCharset(getDefaultSystemCharset());
        return toolkit.guessEncoding();
    }

    public static Charset guessEncoding(InputStream in, int bufferLength, String defaultEncoding)
            throws IOException
    {
        byte[] buffer = new byte[bufferLength];
        in.read(buffer);
        CharsetToolkit toolkit = new CharsetToolkit(buffer);
        toolkit.setDefaultCharset(Charset.forName(defaultEncoding));
        return toolkit.guessEncoding();
    }

    public static Charset guessEncoding(File f, int bufferLength, Charset defaultCharset) throws FileNotFoundException, IOException
    {
        FileInputStream fis = new FileInputStream(f);
        byte[] buffer = new byte[bufferLength];
        fis.read(buffer);
        fis.close();
        CharsetToolkit toolkit = new CharsetToolkit(buffer);
        toolkit.setDefaultCharset(defaultCharset);
        return toolkit.guessEncoding();
    }

    private static boolean isContinuationChar(byte b)
    {
        return ((-128 <= b) && (b <= -65));
    }

    private static boolean isTwoBytesSequence(byte b)
    {
        return ((-64 <= b) && (b <= -33));
    }

    private static boolean isThreeBytesSequence(byte b)
    {
        return ((-32 <= b) && (b <= -17));
    }

    private static boolean isFourBytesSequence(byte b)
    {
        return ((-16 <= b) && (b <= -9));
    }

    private static boolean isFiveBytesSequence(byte b)
    {
        return ((-8 <= b) && (b <= -5));
    }

    private static boolean isSixBytesSequence(byte b)
    {
        return ((-4 <= b) && (b <= -3));
    }

    public static Charset getDefaultSystemCharset()
    {
        return Charset.forName(System.getProperty("file.encoding"));
    }

    private static boolean hasUTF8Bom(byte[] bom)
    {
        return ((bom[0] == -17) && (bom[1] == -69) && (bom[2] == -65));
    }

    private static boolean hasUTF16LEBom(byte[] bom)
    {
        return ((bom[0] == -1) && (bom[1] == -2));
    }

    private static boolean hasUTF16BEBom(byte[] bom)
    {
        return ((bom[0] == -2) && (bom[1] == -1));
    }

    public static Charset[] getAvailableCharsets()
    {
        Collection collection = Charset.availableCharsets().values();
        return ((Charset[])collection.toArray(new Charset[collection.size()]));
    }

    public static void main(String[] args) {

    }
}
