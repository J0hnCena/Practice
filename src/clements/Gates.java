package clements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;


public class Gates {

	public static void main(String[] args) {
		try(BufferedReader reader = new BufferedReader(new FileReader(new File("gates.dat")))) {
			int cases = Integer.parseInt(reader.readLine());
			for(int i = 0; i<cases; i++) {
				String[] info = reader.readLine().split(" ");
				int variables = Integer.parseInt(info[0]);
				for(int j = 0; j< (1 << variables); j++) {
					String truths = Integer.toBinaryString(j);
					while(truths.length() != variables) {
						truths = "0" + truths;
					}
					String[] truthRow = truths.split("");
					int counter = 0;
					StringBuilder expression = new StringBuilder();
					for(int c = 0; c<info[1].length(); c++) {
						if(info[1].charAt(c) == '!') {
							expression.append(truthRow[counter].equals("1") ? "0" : "1");
							c++;
							counter++;
						} else if(info[1].charAt(c) >= 'A' && info[1].charAt(c) <= 'G') {
							expression.append(truthRow[counter]);
							counter++;
						} else {
							expression.append(info[1].charAt(c));
						}
					}
					for(int k = 0; k<truthRow.length; k++) {
						System.out.print(truthRow[k].equals("1") ? "TRUE  " : "FALSE ");
					}
					System.out.println(evaluate(expression.toString()));
				}
				System.out.println();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String evaluate(String expression) {
		char[] exp = expression.toCharArray();
		Stack<Character> ops = new Stack<>();
		Stack<Integer> bools = new Stack<>();
		for(int i = 0; i<exp.length; i++) {
			if(exp[i] == '0' || exp[i] == '1') {
				bools.push(Integer.parseInt("" + exp[i]));
			} else {
				while(!ops.isEmpty() && hasPrecedence(ops.peek(), exp[i])) {
					bools.push(applyOp(bools.pop(), bools.pop(), ops.pop()));
				}
				ops.push(exp[i]);
			}
		}
		while(!ops.isEmpty()) {
			bools.push(applyOp(bools.pop(), bools.pop(), ops.pop()));
		}
		return bools.pop() == 1 ? "TRUE " : "FALSE";
	}

	/**
	 * @param op1 the op peeked from the stack
	 * @param op2
	 * @return true if op1 has precedence over op2
	 */
	public static boolean hasPrecedence(char op1, char op2) {
		if(op1 == '&') 
			return true;
		else if(op1 == '^' && !(op2 == '&'))
			return true;
		else if(op1 == '|' && op2 == '|')
			return true;
		else
			return false;
	}
	
	public static int applyOp(int value1, int value2, char op) {
		switch(op) {
		case '&':
			return value1 & value2;
		case '^':
			return value1 ^ value2;
		case '|':
			return value1 | value2;
		default:
			throw new IllegalArgumentException("THAT OP DON'T EXIST");
		}
	}

}
