package katytaylor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

public class Number {

	public static void main(String[] args) {
		try(BufferedReader reader = new BufferedReader(new FileReader(new File("number.dat")))) {
			String line;
			while((line = reader.readLine()) != null) {
				Set<String> attributeSet = new TreeSet<>();
				long number = Long.parseLong(line);
				long divisorSum = calculateSumOfDivisors(number);
				
				if(divisorSum == number) {
					attributeSet.add("Perfect");
				} else if(divisorSum > number){
					attributeSet.add("Abundant");
				} else {
					attributeSet.add("Deficient");
				}
				attributeSet.add(calculateLengthOfBinaryString(number) % 2 == 0 ? "Evil" : "Odious");
				
				long primeFactorDigits = calculateNumberOfDigitsInPrimeFactorization(number);
				if(primeFactorDigits == calculateNumberOfDigits(number)) {
					attributeSet.add("Equidigital");
				} else if(primeFactorDigits < calculateNumberOfDigits(number)) {
					attributeSet.add("Frugal");
				} else {
					attributeSet.add("Wasteful");
				}
				if(isUgly(number)) {
					attributeSet.add("Ugly");
				}
				
				attributeSet.forEach(System.out::println);
				System.out.println();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static long calculateSumOfDivisors(long num) {
		if(num == 2)
			return 1;
		long sum = 1;
		for(int i = 2; i<Math.sqrt(num) + 1; i++) {
			if(num % i == 0) {
				if(num / i == i) 
					sum += i;
				else
					sum+=i+num/i;
			}
		}
		return sum;
	}
	
	public static long calculateLengthOfBinaryString(long num) {
		long sum = 0;
		while(num > 0) {
			num &= (num - 1);
			sum++;
		}
		return sum;
	}
	
	public static long calculateNumberOfDigitsInPrimeFactorization(long num) {
		long completeSum = 0;
		long sum = 0;
		while(num % 2 == 0) {
			num /= 2;
			sum++;
		}
		completeSum += numberOfDigitsInFactor(sum);
		
		for(long i = 3; i < Math.sqrt(num) +1; i+=2) {
			sum = 0;
			while(num % i == 0) {
				sum++;
				num /= i;
			}
			completeSum += numberOfDigitsInFactor(sum);
		}
		if(num > 2)
			completeSum+= calculateNumberOfDigits(num);
		return completeSum;
	}
	
	public static boolean isUgly(long num) {
		if(num == 5 || num == 3 || num == 2)
			return true;
		for(long i = 7; i < Math.sqrt(num) + 1; i+=2) {
			if(isPrime(i) && ((num % i) == 0)) {
				return false;
			}
		}
		if(isPrime(num)) {
			return false;
		}
		return true;
	}
	
	public static boolean isPrime(long num) {
		if(num <= 3)
			return true;
		if(num % 2 == 0 || num % 3 == 0)
			return false;
		for(long i = 5; i*i<num; i+=6) {
			if(num % i == 0 || num %(i+2) == 0)
				return false;
		}
		return true;
	}
	
	public static long numberOfDigitsInFactor(long num) {
		long sum = 0;
		if(num >= 1) {
			sum++;
			if(num >= 2) {
				sum+=calculateNumberOfDigits(num);
			}
		}
		return sum;
	}
	
	public static long calculateNumberOfDigits(long num) {
		long sum = 0;
		while(num / 10 > 0) {
			sum++;
			num/=10;
		}
		if(num > 0)
			sum++;
		return sum;
	}

}
