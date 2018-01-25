package poly;

import java.io.*;
import java.util.StringTokenizer;

/**
 * This class implements a term of a polynomial.
 * 
 * @author runb-cs112
 *
 */
class Term {
	/**
	 * Coefficient of term.
	 */
	public float coeff;
	
	/**
	 * Degree of term.
	 */
	public int degree;
	
	/**
	 * Initializes an instance with given coefficient and degree.
	 * 
	 * @param coeff Coefficient
	 * @param degree Degree
	 */
	public Term(float coeff, int degree) {
		this.coeff = coeff;
		this.degree = degree;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		return other != null &&
		other instanceof Term &&
		coeff == ((Term)other).coeff &&
		degree == ((Term)other).degree;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (degree == 0) {
			return coeff + "";
		} else if (degree == 1) {
			return coeff + "x";
		} else {
			return coeff + "x^" + degree;
		}
	}
}

/**
 * This class implements a linked list node that contains a Term instance.
 * 
 * @author runb-cs112
 *
 */
class Node {
	
	/**
	 * Term instance. 
	 */
	Term term;
	
	/**
	 * Next node in linked list. 
	 */
	Node next;
	
	/**
	 * Initializes this node with a term with given coefficient and degree,
	 * pointing to the given next node.
	 * 
	 * @param coeff Coefficient of term
	 * @param degree Degree of term
	 * @param next Next node
	 */
	public Node(float coeff, int degree, Node next) {
		term = new Term(coeff, degree);
		this.next = next;
	}
}

/**
 * This class implements a polynomial.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Pointer to the front of the linked list that stores the polynomial. 
	 */ 
	Node poly;
	
	/** 
	 * Initializes this polynomial to empty, i.e. there are no terms.
	 *
	 */
	public Polynomial() {
		poly = null;
	}
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param br BufferedReader from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 */
	public Polynomial(BufferedReader br) throws IOException {
		String line;
		StringTokenizer tokenizer;
		float coeff;
		int degree;
		
		poly = null;
		
		while ((line = br.readLine()) != null) {
			tokenizer = new StringTokenizer(line);
			coeff = Float.parseFloat(tokenizer.nextToken());
			degree = Integer.parseInt(tokenizer.nextToken());
			poly = new Node(coeff, degree, poly);
		}
	}
	
	
	/**
	 * Returns the polynomial obtained by adding the given polynomial p
	 * to this polynomial - DOES NOT change this polynomial
	 * 
	 * @param p Polynomial to be added
	 * @return A new polynomial which is the sum of this polynomial and p.
	 */
	public Polynomial add(Polynomial p) {
		Polynomial result = new Polynomial(); //makes polynomial to store resultant poly
		Node p1 = p.poly; 
		Node p2 = this.poly; 
		Node p3; 
		
		while(p1 != null && p2 != null){ //this part adds coeffs and makes linked list with degrees
			if (p1.term.degree < p2.term.degree){ 
				p3 = new Node(p1.term.coeff,p1.term.degree,null);			
				if(p3.term.coeff==0){
					}
				else{
					addToFront(result, p3);
				}
				p1 = p1.next;
			}
			else if (p1.term.degree > p2.term.degree){
				p3 = new Node(p2.term.coeff,p2.term.degree,null);
				if(p3.term.coeff==0){
					}
				else{
					addToFront(result, p3);
				}
				p2 = p2.next;
			}

			else if(p1.term.degree == p2.term.degree){  //adds coeff
				p3 = new Node(p1.term.coeff+p2.term.coeff,p1.term.degree,null);//keep doing .next to add on to that <-- (addToFront)
				if(p3.term.coeff==0){
					}
				else{
					addToFront(result, p3);
				}
				p1 = p1.next;
				p2 = p2.next;
			} 
			
		}
		while(p1 != null){
			addToFront(result, new Node(p1.term.coeff, p1.term.degree, null));
			p1 = p1.next;
		}
		while (p2 != null){               //p2 is the 1st file entered in console
			addToFront(result, new Node(p2.term.coeff, p2.term.degree, null));
			p2 = p2.next;
			
			
		}
		result = reverse(result);
		return result;
	}
	
	/**
	 * Returns the polynomial obtained by multiplying the given polynomial p
	 * with this polynomial - DOES NOT change this polynomial
	 * 
	 * @param p Polynomial with which this polynomial is to be multiplied
	 * @return A new polynomial which is the product of this polynomial and p.
	 */
	public Polynomial multiply(Polynomial p) {
		Polynomial result  = new Polynomial();
		Polynomial sum = new Polynomial();
		addToFront(sum, new Node(0, 0, null)); //so that sum will add on line 225 and not be null
		Node p1 = this.poly;
		Node p2 = p.poly;
			
		if (p.poly.term.coeff == 0){
			return p;
		}
		if(this.poly.term.coeff == 0){
			return this;
			
		}
		while(p1 != null){
			result = mult(p1, p2);
			sum = sum.add(result); 
			result = null;
			p1 = p1.next; 
		}
		Polynomial curr = new Polynomial();
		curr = sum;
		if(curr.poly.term.coeff == 0){
			curr.poly = curr.poly.next;
			return curr;
		}
		
		return sum;
		
	}
	
	/**
	 * Evaluates this polynomial at the given value of x
	 * 
	 * @param x Value at which this polynomial is to be evaluated
	 * @return Value of this polynomial at x
	 */
	public float evaluate(float x) {
		Node p1 = this.poly;
		float sum = 0;
		while (p1 != null){
			sum = sum + (float) ((Math.pow(x, p1.term.degree))*p1.term.coeff);
			p1 = p1.next;
		}
		return sum;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String retval;
		
		if (poly == null) {
			return "0";
		} else {
			retval = poly.term.toString();
			for (Node current = poly.next ;
			current != null ;
			current = current.next) {
				retval = current.term.toString() + " + " + retval;
			}
			return retval;
		}
	}
	
	
	private static Polynomial addToFront(Polynomial p, Node u){
		u.next = p.poly;
		p.poly = u;
		return p;
	}
	
	

	private static Polynomial reverse(Polynomial p4){
		Polynomial reversed = new Polynomial();
		Node curr;
		for(curr = p4.poly; curr!=null; curr=curr.next){
			addToFront(reversed, new Node(curr.term.coeff, curr.term.degree, null));
		}
		
		return reversed;
	}

	private static Polynomial mult(Node p1, Node p2){
		//assuming both polynomials are not null. check before calling method
		Polynomial hold = new Polynomial(); 
		Node p3;
		while(p2 !=null){
			p3 = new Node(p1.term.coeff*p2.term.coeff,p1.term.degree+p2.term.degree,null);
			addToFront(hold , p3);
			p2 = p2.next;
		}
		hold = reverse(hold);
		
		return hold; //you now have a linked list with all the multiplied terms of the 2 polynomials. 1 term of pol 1 and all of pol2
		
	}
	 //i could make a delete method that checks all the coeffs in the list and if 0 delete. 
}