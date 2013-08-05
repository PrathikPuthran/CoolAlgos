package cool.algos.strings;

import java.util.Arrays;

public class SuffixLcpArray {
	private String s;
	private int[] sA;
	private int[] lcpA;
	private int[][] sortIndex;
	public SuffixLcpArray(String res) {
		s = res;
		sA = new int[s.length()];
		lcpA = new int[s.length()];
	}
	
	// Returns ceil(log(n) base 2)
	public int ceilLogN(int n) {
		int c = 0;
		int no = n;
		while(no!=0) {
			no = no>>1;
			c++;
		}
		if((n&n-1) == 0) {
			return c-1;
		} else {
			return c;
		}
	}
	//Generate suffix array using Suffix sorting
	//This is the O(Nlog2(N)) implementation
	public void generateSuffixArray() {
		int logN = ceilLogN(s.length());
		sortIndex = new int[logN + 1][s.length()];
		for(int i=0; i<s.length(); i++) {
			sortIndex[0][i] = s.charAt(i);
		}
		int doneTill = 1;
		int step = 1;
		while(doneTill < s.length()) {
			Data[] d = new Data[s.length()];
			for(int i=0; i<s.length(); i++) {
				Data d1 = new Data(sortIndex[step-1][i], ((i+doneTill)<s.length() ? sortIndex[step-1][i+doneTill] : -1), i);
				d[i] = d1;
			}
			sortIndex[step][d[0].i] = 0;
			Arrays.sort(d);
			for(int i=1; i<s.length(); i++) {
				int inc = 0;
				if(!d[i].equals(d[i-1])) {
					inc = 1;
				}
				sortIndex[step][d[i].i] = sortIndex[step][d[i-1].i] + inc;
			}
			step++;
			doneTill <<= 1;
			if(doneTill>=s.length()) {
				for(int i=0; i<s.length(); i++) {
					sA[i] = d[i].i;
				}
			}
		}
	}
	
	public int findLCP(int x, int y) {
		int ans = 0;
		int logN = ceilLogN(s.length());
		for(int i=logN; i>=0; i--) {
			if(x>=s.length() || y>=s.length()) {
				break;
			}
			if(sortIndex[i][x] == sortIndex[i][y]) {
				ans = ans + (1<<i);
				x += (1<<i);
				y += (1<<i);
			}
		}
		return ans;
	}
	
	public void generateLCPArray() {
		lcpA = new int[s.length()];
		if(s.length() <= 0) {
			return;
		} else {
			lcpA[0] = 0;
			for(int i=1; i<s.length(); i++) {
				lcpA[i] = findLCP(sA[i], sA[i-1]);
			}
		}
	}
	
	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public int[] getsA() {
		return sA;
	}

	public void setsA(int[] sA) {
		this.sA = sA;
	}

	public int[] getLcpA() {
		return lcpA;
	}

	public void setLcpA(int[] lcpA) {
		this.lcpA = lcpA;
	}
	
	public static class Data implements Comparable<Data>{
		int s1;
		int s2;
		int i;
		public Data(int s1, int s2, int i) {
			this.s1 = s1;
			this.s2 = s2;
			this.i = i;
		}
		@Override
		public int compareTo(Data o) {
			if(s1 != o.s1) {
				return s1-o.s1;
			} else {
				return s2 - o.s2;
			}
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + i;
			result = prime * result + s1;
			result = prime * result + s2;
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Data other = (Data) obj;
			if (s1 != other.s1)
				return false;
			if (s2 != other.s2)
				return false;
			return true;
		}
		
		
	}
}
