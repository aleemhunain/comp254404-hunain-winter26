package Ex_1;

public class ProductOfTwoPosIntegers {
	
	public static int product(int m, int n) {
		//base assumption that only positive numbers will be given by name of the the class
		if (n > 0) {
			return m + product(m, n - 1); //the multiplication is a sum of m0 + m1 + m2 ... mN, reduce 1 every time from N/n and you return the product
		} else {
			return 0;
		}
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(product(4,3));
		System.out.println(product(5,7));
		System.out.println(product(11,11));
	}

}
