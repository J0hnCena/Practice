package katytaylor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class Demonic {

	public static void main(String[] args) {
		try(BufferedReader reader = new BufferedReader(new FileReader(new File("demonic.dat")))) {
			int cases = Integer.parseInt(reader.readLine());
			for(int i = 0; i<cases; i++) {
				String[] unparsed = reader.readLine().split(" ");
				StringBuilder exp = new StringBuilder(unparsed.length);
				for(int c = 0; c<unparsed.length; c++) {
					String token = unparsed[c];
					if(token.indexOf("-") != -1 && token.length() >= 2) {
						exp.append(-Integer.parseInt(token.substring(1, 2)));
					} else if(token.indexOf("(") != -1) {
						//System.out.println(token);
						exp.append(token.substring(token.indexOf("("), token.indexOf("(") + 1));
						exp.append(" ");
						exp.append(token.replaceAll("[(]", ""));
					} else if(token.indexOf(")") != -1) {
						exp.append(token.replaceAll("[)]", ""));
						exp.append(" ");
						exp.append(token.substring(token.length() - 1, token.length()));
					} else {
						exp.append(token);
					}
					exp.append(" ");
				}
				
				String ops = "+-/*^";
				int value = 0;
				if(ops.indexOf(exp.substring(0,1)) != -1) {
					value = evaluatePrefix(exp.toString().split(" "));
				} else if(ops.indexOf(exp.substring(exp.length() - 2, exp.length() - 1)) != -1) {
					value = evaluatePostfix(exp.toString().split(" "));
				} else {
					value = evaluateInfix(exp.toString().split(" "));
				}
				System.out.println(value);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int evaluatePostfix(String[] expression) {
		Stack<Integer> values = new Stack<>();
		for(int i = 0; i<expression.length; i++) {
			String token = expression[i];
			if(token.startsWith("-") && token.length() >= 2 || token.charAt(0) >= '0' && token.charAt(0) <= '9') {
				values.push(Integer.parseInt(token));
			} else {
				int val1 = values.pop();
				int val2 = values.pop();
				values.push(applyOp(token, val1, val2));
			}
		}
		return values.pop();
	}
	
	public static int evaluatePrefix(String[] expression) {
		Stack<Integer> values = new Stack<>();
		for(int i = expression.length - 1; i>-1; i--) {
			String token = expression[i];
			if(token.startsWith("-") && token.length() >= 2 || token.charAt(0) >= '0' && token.charAt(0) <= '9') {
				values.push(Integer.parseInt(token));
			} else {
				int val1 = values.pop();
				int val2 = values.pop();
				values.push(applyOp(token, val2, val1));
			}
		}
		return values.pop();
	}

	public static int evaluateInfix(String[] expression) {
		Stack<Integer> values = new Stack<>();
		Stack<String> operators = new Stack<>();
		for(int i = 0; i<expression.length; i++) {
			String token = expression[i];
			if(token.startsWith("-") && token.length() >= 2 || token.charAt(0) >= '0' && token.charAt(0) <= '9') {
				values.push(Integer.parseInt(token));
			} else if(token.equals("(")) {
				operators.push(token);
			} else if(token.equals(")")) {
				while(!operators.peek().equals("(")) {
					values.push(applyOp(operators.pop(), values.pop(), values.pop()));
				}
				operators.pop();
			} else {
				while(!operators.isEmpty() && hasPrecedence(token, operators.peek())) {
					values.push(applyOp(operators.pop(), values.pop(), values.pop()));
				}
				operators.push(token);
			}
		}
		while(!operators.isEmpty()) {
			values.push(applyOp(operators.pop(), values.pop(), values.pop()));
		}
		return values.pop();
	}

	/**
	 * I don't like really long boolean expressions
	 * @param op1
	 * @param op2
	 * @return true if op2 has higher or equal precedence to op1
	 */
	public static boolean hasPrecedence(String op1, String op2) {
		if("()".indexOf(op2) != -1) {
			return false;
		}
		if("*/".indexOf(op1) != -1 && "+-".indexOf(op2) != -1)
			return false;
		if(op1.equals("^") && !op2.equals("^"))
			return false;
		return true;
	}

	public static int applyOp(String op, int val1, int val2) {
		switch(op) {
		case "+":
			return val2 + val1;
		case "-":
			return val2 - val1;
		case "*":
			return val2 * val1;
		case "/":
			return val2 / val1;
		case "^":
			return (int)Math.pow(val2, val1);
		default:
			return 0;
		}
	}

}
