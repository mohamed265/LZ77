package LZ77;

import java.awt.List;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.text.AbstractDocument.BranchElement;

public class tag {
	public int pointer;
	public int length;
	public char cha;
	public static ArrayList<Boolean> binaryTag = new ArrayList<Boolean>(32);
	public static ArrayList<Integer> binaryNum = new ArrayList<Integer>(32);
	public static int numberTages;

	public String toString() {
		return pointer + " " + length + " " + cha;
	}

	public void prepareTag(binNum poi, binNum le) {
		ArrayList<Boolean> arPoi = poi.toBinary(pointer), arLen = le
				.toBinary(length), ch = binNum.fromChar(cha);

		for (int i = 0; i < arPoi.size(); i++) {
			binaryTag.add(arPoi.get(i));
		}

		for (int i = 0; i < arLen.size(); i++) {
			binaryTag.add(arLen.get(i));
		}
		for (int i = 0; i < ch.size(); i++) {
			binaryTag.add(ch.get(i));
		}
	}

	public static void genrate() {
		int num = 0;
		for (int i = 0, j = 1; i < binaryTag.size(); i++, j++) {

			if (j % 32 == 0) {
				j = 1;
				binaryNum.add(num);
				num = (int) ((binaryTag.get(i) ? 1 : 0) * Math.pow(2, j - 1));
			} else
				num += (binaryTag.get(i) ? 1 : 0) * Math.pow(2, j - 1);
		}
		if (num > 0)
			binaryNum.add(num);
	}

	public static void createBinaryNum() {
		binaryTag = new ArrayList<Boolean>(32);
		int num = 0;
		for (int i = 0; i < binaryNum.size(); i++) {
			num = binaryNum.get(i);
			for (int j2 = 0; j2 < 31; j2++) {
				binaryTag.add((num % 2 == 1 ? true : false));
				num /= 2;
			}
		}
	}

	public static ArrayList<tag> deGenrate(binNum poi, binNum le) {

		createBinaryNum();

		ArrayList<tag> list = new ArrayList<tag>();
		int tagSize = 8 + poi.binLen + le.binLen;
		for (int i = 0, j2 = 0; i < binaryTag.size() && j2 < numberTages; i += tagSize, j2++) {

			tag temp = new tag();

			ArrayList<Boolean> po = new ArrayList<Boolean>();

			for (int j = i; j < i + poi.binLen; j++) {
				po.add(binaryTag.get(j));
			}

			temp.pointer = poi.toDecimal(po);

			ArrayList<Boolean> l = new ArrayList<Boolean>();

			for (int j = i + poi.binLen; j < i + poi.binLen + le.binLen; j++) {
				l.add(binaryTag.get(j));
			}

			temp.length = le.toDecimal(l);

			ArrayList<Boolean> ch = new ArrayList<Boolean>();

			for (int j = i + poi.binLen + le.binLen; j < i + poi.binLen
					+ le.binLen + 8
					&& j < binaryTag.size(); j++) {
				ch.add(binaryTag.get(j));
			}

			temp.cha = binNum.toChar(ch);

			list.add(temp);

		}

		return list;
	}

	public static void saveTag(DataOutputStream out, binNum poi, binNum le)
			throws IOException {
		out.writeShort(poi.binLen);
		out.writeShort(le.binLen);
		genrate();
		out.writeShort(binaryTag.size() / (poi.binLen + le.binLen + 8));
		for (int i = 0; i < binaryNum.size(); i++) {
			out.writeInt(binaryNum.get(i));
		}

	}

	public static void readTag(DataInputStream in, binNum poi, binNum le) {
		binaryNum = new ArrayList<Integer>(32);
		try {
			poi.SetMaxBinLen(in.readShort());
			le.SetMaxBinLen(in.readShort());
			numberTages = in.readShort();
			for (int i = 0; true; i++) {
				binaryNum.add(in.readInt());
			}
		} catch (Exception e) {
			return;
		} 
	}

}
