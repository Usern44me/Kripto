import java.util.*;
import java.io.*;

public class Cipher {
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
		int shift = Integer.parseInt(args[2]) % lengthalf;
		caesarCipher(str, alf, shift, length, lengthalf, w);
	}

	public static void caesarCipher(char[] str, char[] alf, int shift, int length, int lengthalf, BufferedWriter w) throws IOException{
		for (int i = 0, j = 0; i < length; i++){
			j = 0;
			if ((str[i] < 'a' || str[i] > 'z') && str[i] != ' ') { 
				continue;
			}
			while (str[i] != alf[j]) {
				j++;
			}
			w.write(alf[(j + shift) % lengthalf]); 
		}
		w.close();
	}
}
