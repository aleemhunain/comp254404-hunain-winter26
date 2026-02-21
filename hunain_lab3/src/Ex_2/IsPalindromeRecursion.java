package Ex_2;

public class IsPalindromeRecursion {
	
	public static boolean IsPalindrome(String test) {
		if (test.length() < 2) {
			return true;
		} else {
//			System.out.println(test.charAt(0) + " " + test.charAt(test.length() - 1));
//			System.out.println(test.substring(1, test.length() - 1));
			if (test.charAt(0) == test.charAt(test.length() - 1)) { //if the Matriochka matches, send the inner
				return (true && IsPalindrome(test.substring(1, test.length() - 1)));
			} else {
				return false;
			}		
		}
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(IsPalindrome("bob")); //should be true
		System.out.println(IsPalindrome("leo")); //clearly false
		System.out.println(IsPalindrome("silis")); //should be true
		System.out.println(IsPalindrome("nathan")); //almost could have been true but is false
		System.out.println(IsPalindrome("racecar")); //gold standard of true
	}

}
