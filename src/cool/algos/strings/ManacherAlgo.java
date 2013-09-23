package cool.algos.strings;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


public class ManacherAlgo {
	
	public static void main(String[] args) throws MalformedURLException, IOException {
		InputStream stream = new URL("http://challenge.greplin.com/static/gettysburg.txt").openStream();
		String res = "";
		int d;
		int c = 0;
		byte[] b = new byte[1024];
		while((d = stream.read(b)) != -1) {
			for(int i=0; i<d; i++) {
				res += (char)b[i];
			}
			c++;
		}
		System.out.println(new ManacherAlgo().getLongestPalindromeSubstring(res));
	}
	
	/**
	 * This is implemented using Manacher's algorithm
	 * @param inp
	 * @return longest palindromic substring
	 */
	public static String getLongestPalindromeSubstring(String inp) {
		if(inp != null && inp.length()>0) {
			String hashed = getHashReplacedString(inp);
			int[] p = new int[hashed.length()];
			int c=0, r = 0;
			for(int i=1; i<hashed.length()-1; i++) {
				int im = 2*c - i;
				p[i] = r>i? (Math.min(p[im], r-i)) : 0;
				while(hashed.charAt(i+1+p[i]) == hashed.charAt(i-1-p[i])) {
					p[i] ++;
				}
				if(i+p[i]>r) {
					c = i;
					r = i + p[i];
				}
			}
			int max = 0;
			int ind = 0;
			for(int i=1; i<p.length; i++) {
				if(p[i]>max) {
					max = p[i];
					ind = i;
				}
			}
			int st = (ind-1-max)/2;
			return inp.substring(st, st+max);
		}
		throw new IllegalArgumentException("Invalid input");
	}
	
	public static String getHashReplacedString(String input) {
		String res = "^";
		for(int i=0;i<input.length(); i++) {
			res += "#" + input.charAt(i);
		}
		res += "$";
		return res;
	}
}
