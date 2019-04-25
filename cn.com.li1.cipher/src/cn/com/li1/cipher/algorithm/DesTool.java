package cn.com.li1.cipher.algorithm;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * DES加密工具。
 * 
 * @author mlrain
 * @version 1.0.0, 2009-9-14 下午02:42:01
 */
public class DesTool
{

    private static final char[] KEY_DEFAULT = { '1', '2', '3', '4', '5', '6',
            '7', '8' };

    private char[][] C = new char[17][28];

    private char[][] D = new char[17][28];

    private char[][] K = new char[17][48];

    private int[] pc_1_c = new int[] { 57, 49, 41, 33, 25, 17, 9, 1, 58, 50,
            42, 34, 26, 18, 10, 2, 59, 51, 43, 35, 27, 19, 11, 3, 60, 52, 44,
            36 };

    private int[] pc_1_d = new int[] { 63, 55, 47, 39, 31, 23, 15, 7, 62, 54,
            46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29, 21, 13, 5, 28, 20, 12, 4 };

    private int[] pc_2 = new int[] { 14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21,
            10, 23, 19, 12, 4, 26, 8, 16, 7, 27, 20, 13, 2, 41, 52, 31, 37, 47,
            55, 30, 40, 51, 45, 33, 48, 44, 49, 39, 56, 34, 53, 46, 42, 50, 36,
            29, 32 };

    private int[] ls_count = new int[] { 1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2,
            2, 2, 1 };

    private int[] ip_tab = new int[] { 58, 50, 42, 34, 26, 18, 10, 2, 60, 52,
            44, 36, 28, 20, 12, 4, 62, 54, 46, 38, 30, 22, 14, 6, 64, 56, 48,
            40, 32, 24, 16, 8, 57, 49, 41, 33, 25, 17, 9, 1, 59, 51, 43, 35,
            27, 19, 11, 3, 61, 53, 45, 37, 29, 21, 13, 5, 63, 55, 47, 39, 31,
            23, 15, 7 };

    private int[] _ip_tab = new int[] { 40, 8, 48, 16, 56, 24, 64, 32, 39, 7,
            47, 15, 55, 23, 63, 31, 38, 6, 46, 14, 54, 22, 62, 30, 37, 5, 45,
            13, 53, 21, 61, 29, 36, 4, 44, 12, 52, 20, 60, 28, 35, 3, 43, 11,
            51, 19, 59, 27, 34, 2, 42, 10, 50, 18, 58, 26, 33, 1, 41, 9, 49,
            17, 57, 25 };

    private int[] e_r = new int[] { 32, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 9, 8, 9,
            10, 11, 12, 13, 12, 13, 14, 15, 16, 17, 16, 17, 18, 19, 20, 21, 20,
            21, 22, 23, 24, 25, 24, 25, 26, 27, 28, 29, 28, 29, 30, 31, 32, 1 };

    private int[] P = new int[] { 16, 7, 20, 21, 29, 12, 28, 17, 1, 15, 23, 26,
            5, 18, 31, 10, 2, 8, 24, 14, 32, 27, 3, 9, 19, 13, 30, 6, 22, 11,
            4, 25 };

    private int[][][] SSS = new int[][][] {
            { { 14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7 },
                    { 0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8 },/*
                                                                              * err on
                                                                              */
                    { 4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0 },
                    { 15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13 } },

            { { 15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10 },
                    { 3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5 },
                    { 0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15 },
                    { 13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9 } },

            { { 10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8 },
                    { 13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1 },
                    { 13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7 },
                    { 1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12 } },

            { { 7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15 },
                    { 13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9 },
                    { 10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4 },
                    { 3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14 } }, /*
                                                                                 * err on
                                                                                 */

            { { 2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9 },
                    { 14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6 }, /*
                                                                               * err on
                                                                               */
                    { 4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14 },
                    { 11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3 } },

            { { 12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11 },
                    { 10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8 },
                    { 9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6 },
                    { 4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13 } },

            { { 4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1 },
                    { 13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6 },
                    { 1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2 },
                    { 6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12 } },

            { { 13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7 },
                    { 1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2 },
                    { 7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8 },
                    { 2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11 } } };

    void DES1(char[] key, char[] text, char[] mtext)
    {
        byte[] tmp = new byte[64];
        expand0(key, tmp);
        setkeystar(tmp);
        encrypt0(text, mtext);
    }

    void _DES1(char[] key, char[] text, char[] mtext)
    {
        byte[] tmp = new byte[64];
        expand0(key, tmp);
        setkeystar(tmp);
        discrypt0(text, mtext);
    }

    void encrypt0(char[] text, char[] mtext)
    {
        byte[] ll = new byte[64];
        byte[] rr = new byte[64];
        byte[] LL = new byte[64];
        byte[] RR = new byte[64];
        byte[] tmp = new byte[64];
        int i, j;
        ipYD(text, ll, rr);

        for (i = 1; i < 17; i++)
        {
            F(i, ll, rr, LL, RR);
            for (j = 0; j < 32; j++)
            {
                ll[j] = LL[j];
                rr[j] = RR[j];
            }
        }

        _ip(tmp, rr, ll);

        compress0(tmp, mtext);
    }

    void discrypt0(char[] mtext, char[] text)
    {
        byte[] ll = new byte[64];
        byte[] rr = new byte[64];
        byte[] LL = new byte[64];
        byte[] RR = new byte[64];
        byte[] tmp = new byte[64];
        int i, j;
        ipYD(mtext, ll, rr);

        for (i = 16; i > 0; i--)
        {
            F(i, ll, rr, LL, RR);
            for (j = 0; j < 32; j++)
            {
                ll[j] = LL[j];
                rr[j] = RR[j];
            }
        }

        _ip(tmp, rr, ll);

        compress0(tmp, text);
    }

    void expand0(char[] in, byte[] out)
    {
        int divide;
        int i, j;

        int position = 0;
        for (i = 0; i < 8; i++)
        {
            divide = 0x80;
            for (j = 0; j < 8; j++)
            {
                out[position++] = (byte) ((in[i] / divide) & 0x01);
                divide /= 2;
            }
        }
    }

    void compress0(byte[] out, char[] in)
    {
        int times;
        int i, j;

        int position = 0;
        for (i = 0; i < 8; i++)
        {
            times = 0x80;
            in[i] = 0;
            for (j = 0; j < 8; j++)
            {
                in[i] += (out[position++]) * times;
                times /= 2;
            }
        }
    }

    void compress016(byte[] out, char[] in)
    {
        int times;
        int i, j;

        int position = 0;
        for (i = 0; i < 16; i++)
        {
            times = 0x8;
            in[i] = '0';
            for (j = 0; j < 4; j++)
            {
                in[i] += (out[position++]) * times;
                times /= 2;
            }
        }
    }

    void setkeystar(byte[] bits)
    {
        int i, j;

        for (i = 0; i < 28; i++)
            C[0][i] = (char) bits[pc_1_c[i] - 1];
        for (i = 0; i < 28; i++)
            D[0][i] = (char) bits[pc_1_d[i] - 1];
        for (j = 0; j < 16; j++)
        {
            LS(C[j], C[j + 1], ls_count[j]);
            LS(D[j], D[j + 1], ls_count[j]);
            son(C[j + 1], D[j + 1], K[j + 1]);
        }
    }

    void LS(char[] bits, char[] buffer, int count)
    {
        int i;
        for (i = 0; i < 28; i++)
        {
            buffer[i] = bits[(i + count) % 28];
        }
    }

    void son(char[] cc, char[] dd, char[] kk)
    {
        int i;
        byte[] buffer = new byte[56];
        int position1 = 0;
        int position2 = 0;
        int position3 = 0;
        for (i = 0; i < 28; i++)
            buffer[i] = (byte) cc[position1++];

        for (i = 28; i < 56; i++)
            buffer[i] = (byte) dd[position2++];

        for (i = 0; i < 48; i++)
            kk[position3++] = (char) buffer[pc_2[i] - 1];
    }

    void ipYD(char[] text, byte[] ll, byte[] rr)
    {
        int i;
        byte[] buffer = new byte[64];
        expand0(text, buffer);

        for (i = 0; i < 32; i++)
            ll[i] = buffer[ip_tab[i] - 1];

        for (i = 0; i < 32; i++)
            rr[i] = buffer[ip_tab[i + 32] - 1];
    }

    void _ip(byte[] text, byte[] ll, byte[] rr)
    {
        int i;
        byte[] tmp = new byte[64];
        for (i = 0; i < 32; i++)
            tmp[i] = ll[i];
        for (i = 32; i < 64; i++)
            tmp[i] = rr[i - 32];
        for (i = 0; i < 64; i++)
            text[i] = tmp[_ip_tab[i] - 1];
    }

    void F(int n, byte[] ll, byte[] rr, byte[] LL, byte[] RR)
    {
        int i;
        byte[] buffer = new byte[64];
        byte[] tmp = new byte[64];
        for (i = 0; i < 48; i++)
            buffer[i] = rr[e_r[i] - 1];
        for (i = 0; i < 48; i++)
            buffer[i] = (byte) ((buffer[i] + K[n][i]) & 1);

        s_box(buffer, tmp);

        for (i = 0; i < 32; i++)
            buffer[i] = tmp[P[i] - 1];

        for (i = 0; i < 32; i++)
            RR[i] = (byte) ((buffer[i] + ll[i]) & 1);

        for (i = 0; i < 32; i++)
            LL[i] = rr[i];

    }

    void s_box(byte[] aa, byte[] bb)
    {
        int i, j, k, m;
        int y, z;
        byte[] ss = new byte[8];
        m = 0;
        for (i = 0; i < 8; i++)
        {
            j = 6 * i;
            y = aa[j] * 2 + aa[j + 5];
            z = aa[j + 1] * 8 + aa[j + 2] * 4 + aa[j + 3] * 2 + aa[j + 4];
            ss[i] = (byte) SSS[i][y][z];
            y = 0x08;
            for (k = 0; k < 4; k++)
            {
                bb[m++] = (byte) ((ss[i] / y) & 1);
                y /= 2;
            }

        }
    }

    public static String desEncrypt(String s)
    {
        return desEncrypt(s, DesTool.KEY_DEFAULT);
    }

    public static String desEncrypt(String s, char[] key)
    {
        if (key == null || key.length != 8)
            throw new IllegalArgumentException(
                    "Key must be a char array with a length of eight");

        String encstr = "";
        String hex = null;
        if (s.length() > 8)
        {
            throw new IllegalArgumentException(s + " length > 9 not allow");
        }
        char[] source = new char[] { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' };
        for (int i = 0; i < s.length(); i++)
        {
            source[i] = s.charAt(i);
        }
        DesTool tool = new DesTool();
        char[] encsource = new char[8];
        tool._DES1(key, source, encsource);
        for (int i = 0; i < encsource.length; i++)
        {
            hex = Integer.toHexString(encsource[i]);
            if (hex.length() == 1)
            {
                encstr += "0" + hex;
            } else if (hex.length() == 2)
            {
                encstr += hex;
            } else
            {
                throw new IllegalArgumentException(hex + " must < 255 ");
            }
        }
        return encstr.toUpperCase();
    }

    public static String desDiscrypt(String s) 
    {
        return desDiscrypt(s, DesTool.KEY_DEFAULT);
    }
    /**
     * 支持长度大于8的字符串
     * @param s
     * @return
     * @throws Exception
     */
    public static String desEncryptPlus(String s)
    {
        String code = "";
        int num = 0;
        if (s.length() % 8 == 0)
        {
            num = s.length() / 8;
        } else
        {
            num = s.length() / 8 + 1;
        }
        if (s.length() > 8)
        {
            int index = 0;
            for (int i = 0; i < num; i++)
            {
                index = ((i * 8 + 8) > s.length()) ? s.length()
                        : i * 8 + 8;
                code = code + desEncrypt(s.substring(i * 8, index));
            }
        } else
        {
            code = desEncrypt(s);
        }
        return code;
    }

    public static String desDiscrypt(String s, char[] key)
    {
        if (key == null || key.length != 8)
            throw new IllegalArgumentException(
                    "Key must be a char array with a length of eight");

        if (s.length() != 16)
        {
            throw new IllegalArgumentException(s + " desDiscrypt string's length must be 16");
        }
        char[] encsource = new char[8];
        for (int i = 0; i < 8; i++)
        {
            encsource[i] = (char) Integer.parseInt(
                    s.substring(i * 2, i * 2 + 2), 16);
        }
        DesTool tool = new DesTool();
        char[] source = new char[8];
        tool.DES1(key, encsource, source);
        return new String(source);
    }

    public static String desDiscryptPlus(String code)
    {
        String decode = "";
        for (int i = 0; i < code.length() / 16; i++)
        {
            decode = decode + desDiscrypt(code.substring(i * 16, i * 16 + 16));
        }
        return decode.trim();
    }

    public static void main(String[] s) throws Exception
    {
        String arg1 = null;
        String arg2 = null;
        while (true)
        {
            System.out.println("(1)请输入密码,(按回车结束）");
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    System.in));
            arg1 = br.readLine();
            System.out.println("(2)请再输入密码,(按回车结束）");
            br = new BufferedReader(new InputStreamReader(System.in));
            arg2 = br.readLine();
            if ((arg1 != null) && (arg1.equals(arg2)))
            {
                break;
            }
            System.out.println("两次密码不一至，请重新输入");
        }
        String code = desEncryptPlus(arg1);
        System.out.println("加密后密文[" + code + "]");
//        System.out.println("解密后的明文[" + DesTool.desDiscryptPlus(code) + "]"
//                + "   " + DesTool.desDiscryptPlus(code).length());

    }
}
