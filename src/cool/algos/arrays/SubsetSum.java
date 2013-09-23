package cool.algos.arrays;
import java.util.Arrays;

public class SubsetSum {
	public static void main(String[] args) {
		int[] arr = {4, 5, 7, 9, 10, 2 ,2, 3, 4};
		int s = 0;
		for(int i=0; i<arr.length; i++) {
			s += arr[i];
		}
		boolean[] sum = new boolean[s+1];
		sum[0] = true;
		int[][] nums = new int[arr.length][sum.length +1];
		for(int[] r : nums) {
			Arrays.fill(r, -1);
		}
		go(arr, sum, nums);
		int s5 = 8;
		int[] res = new int[arr.length]; 
		printSubsets(nums, s5, 0, arr, res, 0);
	}
	
	public static void printSubsets(int[][] nums, int sum, int start, int[] arr, int[] res, int i) {
		if(sum == 0 || start>=arr.length) {
			for(int j=0; j<i; j++) {
				System.out.print(res[j] + " ");
			}
			System.out.println();
		} else {
			for(int j=start; j<arr.length; j++) {
				if(nums[j][sum] != -1) {
					res[i] = nums[j][sum];
					printSubsets(nums, sum-nums[j][sum], j+1, arr, res, i+1);
				}
			}
		}
	}
	
	public static void go(int[] arr, boolean[] sum, int[][] nums) {
		for(int i=arr.length-1; i>=0 ; i--) {
			for(int s=sum.length-1; s>=0; s--) {
				if(s-arr[i]>=0 && sum[s-arr[i]]) {
					sum[s] = true;
					nums[i][s] = arr[i];
				}
			}
		}
	}	
}
