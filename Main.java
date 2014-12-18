package LZ77;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class Main {

	public static ArrayList<tag> compress(String data, binNum poi, binNum le) {
		ArrayList<tag> list = new ArrayList<tag>();
		data += '\1';
		String temp = "";
		int point;
		char ch;
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		
//		tag t = new tag();
//		t.length = t.pointer = 0;
//		t.cha = data.charAt(0);
//		map.put(t.cha  + "" , 0);
//		list.add(t);
		
		for (int i = 0; i < data.length(); i++) {

			ch = data.charAt(i);

			if (temp == "" && ch == '\1')
				break;

			if (!map.containsKey(temp + ch)) {

				point = Math.max(data.substring(0, i - temp.length()  ).lastIndexOf(temp),
						(map.containsKey(temp) ? map.get(temp) : -1));

				// System.out.println("  point " + point + " " +
				// data.substring(0, i).lastIndexOf(temp) + " "
				// +(map.containsKey(temp) ? map.get(temp) : -1) );

				// /System.out.println("  point " + point);
				if (point == -1)
					point = i;

				tag ta = new tag();
				ta.cha = ch;
				ta.length = temp.length();
				
				if (ta.length != 0)
					ta.pointer = i - point - ta.length;
				else
					ta.pointer = 0;

				poi.maxNum(ta.pointer);
				le.maxNum(ta.length);
				// ta.prepareTag(poi, le);

				// System.out.println(ta.toString());
				list.add(ta);
				map.put(temp + ch, i - temp.length());

				for (int z = 0; z < temp.length(); z++)
					map.put(temp.substring(0, z), i - temp.length());

				temp = "";
				ch = '\1';
				continue;
			} // else
				// map.put(temp + ch , i - temp.length() );

			temp += ch;
		}
		return list;
	}

	public static String deCompress(ArrayList<tag> l) {
		String data = "";
		for (int i = 0; i < l.size(); i++) {
			//System.out.println(l.get(i).toString());
			if (l.get(i).pointer == 0)
				data += l.get(i).cha;
			else {
				
//				System.out.println(data.length()+" "+l.get(i).toString() + " "
//						+ (data.length() - l.get(i).pointer) + " "
//						+ (data.length() - l.get(i).pointer + l.get(i).length) + "\n" + data);
				
				data += data.substring(data.length() - l.get(i).pointer,
						data.length() - l.get(i).pointer + l.get(i).length);
				if (l.get(i).cha != '\1')
					data += l.get(i).cha;
				//System.out.println(data + " " + data.length() );
			}
		}
		return data;
	}

	public static void main(String[] args) {
		// ** // GUI
		 new LZ77APP();
		// ** // number Handling
		// binNum x = new binNum();

		// ** // write to file
		// FileOutputStream outPut = new FileOutputStream("Copress.txt");
		// DataOutputStream out = new DataOutputStream(outPut);
		// out.close();

		// ** // read from file
		// File inpt = new File("Copress.txt");
		// InputStream in = new FileInputStream(inpt);
		// binNum le = new binNum();
		// le.SetMaxBinLen(in.read());
		// binNum poi = new binNum();
		// poi.SetMaxBinLen(in.read());
		// in .close();

		// ** // comprassing
//		binNum poi = new binNum(), le = new binNum();
//		String data = "aablkjfslf;lawkvjnsdkhnsiovjcoisdjviwhiuwhjoiajpdoshbokooooonfbhddlifabab";
//		ArrayList<tag> l = compress(data, poi, le);
		// ArrayList<tag> l = compress("abaa");
		// for (int i = 0; i <l.size(); i++) {
		// System.out.println(l.get(i).toString());
		// }

		// ** // decomprassing
//		String deCompresdData = deCompress(l);
//		System.out.println(deCompresdData);
//		System.out.print(data);

		// ** // testing
		// int x= 608571914;
		// while(x > 0 ){
		// System.out.print(x % 2);
		// x /= 2;
		// }
		// System.out.print(Integer.toBinaryString(5));

		// String data = "";
		// //System.out.println(1 %2 + " "+0%2);
		// Scanner reader = new Scanner(new File("mld.txt"));
		// while (reader.hasNext()) {
		// data += (reader.nextLine() + "\n");
		// }
		// //System.out.println(data);
		//
		// binNum le = new binNum(), poi = new binNum();
		// FileOutputStream outPut = new FileOutputStream("1.Lz77");
		// DataOutputStream out = new DataOutputStream(outPut);
		//
		// ArrayList<tag> l = compress(data, poi, le);
		//
		// le.genrate();
		// poi.genrate();
		//
		// for (int i = 0; i < l.size(); i++) {
		// l.get(i).prepareTag(poi, le);
		// //System.out.println(l.get(i).toString());
		// }
		//
		// // System.out.println(poi.binLen + "*" + le.binLen);
		// tag.saveTag(out, poi, le);
		// out.close();

		// binNum le = new binNum(), poi = new binNum();
		// String deCompreseddata = "";
		// InputStream i1n = new FileInputStream(new File("1.Lz77"));
		// DataInputStream in = new DataInputStream(i1n);
		//
		// tag.readTag(in, poi, le);
		// ArrayList<tag> kk = tag.deGenrate(poi, le) ;
		// in.close();
		//
		// //tag.saveTag(out, poi, le);
		//
		// deCompreseddata = deCompress(kk);
		// System.out.println(data);
		// System.out.print(deCompreseddata);

		// for (int i = 0; i < kk.size(); i++) {
		// System.out.println(l.get(i).toString());
		// }
		// Scanner x = new Scanner(new File("1.Lz77"));
		// System.out.println(x.nextBoolean());
		//
		// for (int i1 = 0; i1 < kk.size(); i1++) {
		// System.out.println(kk.get(i1).toString());
		// }

	}

}
/*
 * for (int j = i - temp.length() - 1; j >= 0; j--) { //
 * System.out.println(data.substring(j, j + // temp.length()+ 1) + "_" + temp +
 * (ch == '\1' ? "" : // ch)); if (data.substring(j, j + temp.length() +
 * 1).equals( (temp + (ch == '\1' ? "" : ch)))) { point = j;
 * //System.out.println("Yes" + j); break; } else System.out.println("No" + j);
 * }
 */
