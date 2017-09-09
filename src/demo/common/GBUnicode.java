package demo.common;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;

public class GBUnicode {
	byte high[] = new byte[6763], low[] = new byte[6763];

	char unichar[] = new char[6763];

	Hashtable UniGB;

	public GBUnicode(String table_file) throws IOException {
		BufferedInputStream tables = new BufferedInputStream(
				new FileInputStream(table_file));
		// DataInputStream tablesD=new DataInputStream(new
		// FileInputStream(table_file));

		int i, n = 0;
		byte b, bl, bh, num[] = new byte[20];

		UniGB = new Hashtable(7000, 1);
		while (n < 6763) {
			do {
				bh = (byte) tables.read();
			} while ((char) bh <= ' '); // find first non-blank char

			bl = (byte) tables.read();
			high[n] = bh;
			low[n] = bl;
			do {
				b = (byte) tables.read();
			} while (b != (byte) ':'); // find ':'

			do {
				b = (byte) tables.read();
			} while ((char) b <= ' '); // find next non-blank char to reaas
										// number

			i = 0;
			while ((char) b >= '0' && (char) b <= '9') {
				num[i++] = b;
				b = (byte) tables.read();
			}
			unichar[n] = (char) Integer.parseInt(new String(num, 0, 0, i));

			if (UniGB.get(new Character(unichar[n])) != null)
				System.out.println("Duplicated : " + unichar[n]);
			UniGB.put(new Character(unichar[n]), new Integer(n));
			n = n + 1;
		}
		tables.close();
	}

	private int getGBindex(byte high, byte low) {
		int i, j;
		i = high - (byte) 0xb0;
		j = low - (byte) 0xa1;
		if (i < 39) {
			// L1 Chinese
			if (j < 0 || j > 94)
				return -1;
			return (i * 94 + j);
		} else if (i == 39) {
			// one of the last 89 L1 Chinese
			if (j < 0 || j > 89)
				return -1;
			return (i * 94 + j);
		} else {
			// L2 Chinese
			if (j < 0 || j > 94)
				return -1;
			return (i * 94 + j - 5);
		}
	}

	public byte[] Uni2GB(char unicode) {

		Integer index = (Integer) UniGB.get(new Character(unicode));
		if (index == null)
			return null;
		byte ch[] = new byte[2];
		ch[0] = high[index.intValue()];
		ch[1] = low[index.intValue()];
		return ch;
	}

	public char GB2Uni(byte high, byte low) {
		int index = getGBindex(high, low);
		if (index == -1) // not GB Chinese
			return 0;
		return (unichar[index]);
	}

	public static void main(String[] args) {
		// D:/eclipse/workspace/ecside/src/org/extremecomponents/table/resource/
		// extremetableResourceBundle_zh_CN.properties
	}
}
