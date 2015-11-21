import java.io.*;
import java.util.Arrays;
import java.util.Random;

public class MatrixOps {
	
	// Given graph, convert to String
		public static String convertToString(int[][] matrix){
			 String buffer = "";
			 int length = matrix[0].length;
			 for(int i = 0; i < length; i++){
				 for(int j = 0; j < length; j++)
					 buffer += matrix[i][j];
			 }
			 return buffer;
		 }
		
		// Given a string matrix like from a file, convert to matrix
		public static int[][] convertToMatrix(String buffer){	
			int size = (int)Math.sqrt(buffer.length());
			int[][] adjMatrix = new int[size][size];
			for(int i = 0; i < size; i++){
				for(int j = 0; j < size; j++)
					adjMatrix[i][j] = (int)buffer.charAt(i*size+j);
			}
			return adjMatrix;
		}
		
		
	
	public static boolean isEmpty(int[][] A){
		int c = 0;
		for(int i = 0; i < A[0].length;i++){
			for(int j= 0; j <= i; j++){
				if(A[i][j] !=0){
					c++;
				}
			}
		}
		if(c == 0){ return true;}
		else{ return false;}
	}
	
	public static int[][] fill(int[][] A, double p){
		if(p<0 || p>1){
			throw new RuntimeException("Probability must be in [0,1]");
		}
		//declaring a random
		double r;
		int entry;
		
		
		//filling the matrix by taking a random bit at each step and then OR-ing it with the current entry. This will allow us to reuse
		// the function on already-filled matrices so as to randomly add edges to the graph
		for(int i=0; i < A[0].length;i++){
			for(int j = 0; j <= i; j++){
				
				// Here we initialize a random between 0,1, if the random is less than a fixed "p" then the bit becomes 1. 
				// This allows us to set the probability with which we add an edge.
				r = Math.random();
				if(r <= p){
					entry = 1;
				}
				else{
					entry = 0;
				}
				A[i][j] = (A[i][j] | entry);
				A[j][i] = A[i][j];
			}
		}
		for(int i = 0; i < A[0].length;i++){
			System.out.println(Arrays.toString(A[i]));
		}
		
		if(isEmpty(A)){
			A = fill(A,p);
		}
		return A;
	}
	
	// matrix multiplier
    public static int[][] multiply(int[][] A, int[][] B) {
        int mA = A.length;
        int nA = A[0].length;
        int mB = B.length;
        int nB = B[0].length;
        if (nA != mB) throw new RuntimeException("Illegal matrix dimensions.");
        int[][] C = new int[mA][nB];
        for (int i = 0; i < mA; i++)
            for (int j = 0; j < nB; j++)
                for (int k = 0; k < nA; k++)
                    C[i][j] += A[i][k] * B[k][j];
        return C;
    }
    
    // return C = A^T
    public static int[][] transpose(int[][] A) {
        int m = A.length;
        int n = A[0].length;
        int[][] C = new int[n][m];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                C[j][i] = A[i][j];
        return C;
    }
	
    // matrix write to file
    public static void matrix_write(int[][] A){
    	
    }
    // print to screen matrix
    public static void matrix_print(int[][] A){
    	for(int i = 0; i < A[0].length;i++){
    		System.out.println(Arrays.toString(A[i]));
    	}
    }
    /*public static int[][] matrix_read(String[] arg){
    	
    }*/
    
    //==================================================
    //Generate permutation matrix
    // Swap is used by perm_mat
    public static void swap(int[] L, int a, int b){
		int t = L[a];
		L[a] = L[b];
		L[b] = t;	
	}
    public static int[] perm_gen(int size){
	    int[] Z_n = new int[size];
		for (int i = 0; i < size; i++){
			Z_n[i] = i;
		}
		
		Random rand_no = new Random();
		for(int i = 0; i < Z_n.length; i++){
			int sw = rand_no.nextInt(Z_n.length);
			swap(Z_n,i,sw);
			}
		//System.out.println(Arrays.toString(Z_n));
		return Z_n;
    }
    public static int[][] perm_mat(int n){
    	int[] perm = perm_gen(n);
    	//System.out.println("Permuation created: " + Arrays.toString(perm));
    	
    	//creating the permutation matrix using the permutation array "perm"
    	int[][] P = new int[n][n];
    	for(int i = 0; i < perm.length; i++){
    		int j = perm[i];
    		P[j][i] = 1;
    	}
    	//System.out.println("Permutation Matix Created: ");
    	matrix_print(P);
    	return P;
    }
    
    
    //==================================================
    // Intake an adjacency matrix A and permutation matrix P and return a permuted Adjacency matrix
    public static int[][] permute(int[][] A,int[][] P){
    	int n = A[0].length;
    	int[][] Pt = transpose(P);
    	int[][] temp = multiply(P,A);
    	int[][] B = multiply(temp,Pt);
    	
    	return B;
    }
    
    public static void main(String []args){
		/*int m = 3;
		int[][] G = new int[m][m];
		G = fill(G,0.5);
		permute(G);*/
	}
}
