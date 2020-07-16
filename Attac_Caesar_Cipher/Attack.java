import java.util.*;
import java.io.*;

public class Attack {
	public static void main(String[] args) throws IOException {
		BufferedReader r = new BufferedReader (new InputStreamReader(new FileInputStream(args[0])));
		BufferedWriter w = new BufferedWriter (new OutputStreamWriter(new FileOutputStream(args[1])));
		StringBuilder sb = new StringBuilder();
		while(r.ready()) {
			sb.append(r.readLine().toLowerCase());
		}
		int length = sb.toString().length(), lengthalf = 27;
		char[] str = sb.toString().toCharArray();
		char[] alf = new char[lengthalf];
		for (int i = 0, j = 97; i < 26; i++, j++) {
			alf[i] = (char)j;
		}
		alf[26] = ' ';
		decryption(str, alf, length, lengthalf, w);
	}

	public static void decryption(char[] str, char[] alf, int length, int lengthalf, BufferedWriter w) throws IOException {
		Map<Character, Integer> hashMap = new HashMap<Character, Integer>();
		int max = 0, numberL = 0;
		char letter = ' ';
		for(int i = 0; i < length; i++) {
		    if (hashMap.containsKey(str[i])) 
		    	hashMap.put(str[i], (int)hashMap.get(str[i]) + 1);	
		    else
		    	hashMap.put(str[i], 1);
		}
		for(Map.Entry<Character, Integer> item : hashMap.entrySet()) {
			if (item.getValue() > max) {
				max = item.getValue();
				letter = item.getKey();
			}
		}
		for (int i = 0; i < lengthalf; i++) {
			if (alf[i] == letter) {
				numberL = i;
				break;
			}
		}
		int shift = ((numberL - 26) + lengthalf) % lengthalf;
		for(int i = 0, j = 0; i < length; i++) {
			j = 0;
			while (str[i] != alf[j]) {
				j++;
			}
			if (j - shift < 0)
				w.write(alf[(j - shift + lengthalf)]);
			else
				w.write(alf[(j - shift)]);
		}
		w.close();
	}
}