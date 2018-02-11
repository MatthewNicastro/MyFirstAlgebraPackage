package algebra;

import java.io.*;

public class Matrix implements Cloneable{
	
	private Double[][] matrix;
	
	/*
	 * |˜a b˜|
	 * |_c d_|
	 * Creates a two by two matrix
	*/
	public Matrix(Double a, Double b, Double c, Double d){
		matrix = new Double[][] {{a, b}, {c, d}};
	}
	
	//Creates an n by n matrix 
	public Matrix(int n) {
		matrix = new Double[n][n];
	}
	
	//Creates an n by m matrix
	public Matrix(int rows, int cols) {
		matrix = new Double[rows][cols];
	}
	
	/*
	 *Initializes values for the Matrix
	 *Takes file name
	 *Format separate each value with ',' (do not include ') for each row separate rows by lines
	*/
	public void setValue(String file){
		
		try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
			
			for(int i = 0; i < matrix.length; i++) {
				String[] temp  = reader.readLine().split(",");
				if(temp.length != matrix[0].length) {
					throw new NotEnoughException("Data in txt file does not match size of matrix");
				}
				else {
					for(int j = 0; j < temp.length; j++) {
						matrix[i][j] = Double.parseDouble(temp[j]);
					}
				}
			}
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		catch(NotEnoughException e) {
			e.printStackTrace();
		}
	}
	
	//Sets a specific value in matrix
	public void setValue(int row, int col, Double val) {
		matrix[row][col] = val;
	}
	
	/*
	 * |˜a b c˜|
	 * |_d e f_|
	 * Creates a quick two by three matrix
	*/
	public void setMatrixL(Double a, Double b, Double c, Double d, double e, double f) throws NotEnoughException {
		if(matrix.length == 2 && matrix[0].length == 3) {
			matrix[0][0] = a;
			matrix[0][1] = b;
			matrix[0][2] = c;
			matrix[1][0] = d;
			matrix[1][1] = e;
			matrix[1][2] = f;
		}
		else {
			throw new NotEnoughException("Not a 2x3 matrix");
		}
	}
	
	/*
	 * |˜a b˜|
	 * | c d |
	 * |_e f_|
	 * Creates a quick two by three matrix
	*/
	public void setMatrixT(Double a, Double b, Double c, Double d, double e, double f) throws NotEnoughException {
		if(matrix.length == 3 && matrix[0].length == 2) {
			matrix[0][0] = a;
			matrix[0][1] = b;
			matrix[1][0] = c;
			matrix[1][1] = d;
			matrix[2][0] = e;
			matrix[2][1] = f;
		}
		else {
			throw new NotEnoughException("Not a 2x3 matrix");
		}
	}
	
	//Gets number of rows
	public int getNumRows() {
		return matrix.length;
	}
	
	//Gets number of columns
	public int getNumCols() {
		return matrix[0].length;
	}
	
	//Displays the matrix
	public void display() {
		System.out.print("|˜");
		for(int i = 0; i < matrix.length; i++) {
			if(i != 0 && i != (matrix.length-1)) {
				System.out.printf("|");
			}
			if(i == (matrix.length - 1)) {
				System.out.printf("|_");
			}
				
			for(int j = 0; j < matrix[i].length; j++) {
				System.out.printf(" %.2f ", matrix[i][j]);
			}
				
			if(i != (matrix.length - 1) && i != 0) {
				System.out.printf("|\n");
			}
			else if(i == 0) {
				System.out.printf("˜|\n");
			}
			else {
				System.out.printf("_|\n");
			}
		}
	}
	
	public Double getValue(int row, int col) {
		return matrix[row][col];
	}
	
	//Multiplies two matrix operations
	public  static Matrix mulitply(Matrix A, Matrix B) throws NotEnoughException {
		Matrix result = new Matrix(A.getNumRows(), B.getNumCols());
		if(A.getNumRows() != B.getNumCols()) {
			throw new NotEnoughException("Columns doesn't match rows");
		}
		else {
			Double temp = 0.0;
			int Amatrix = 0;
			int Bmatrix = 0;
			
			while(Amatrix != A.getNumRows()) {
				for(int i = 0; i < A.getNumCols(); i++) {
					temp += (A.getValue(Amatrix, i)*B.getValue(i, Bmatrix));
				}
				result.setValue(Amatrix, Bmatrix, temp);
				temp = 0.0;
				Bmatrix++;
				if(Bmatrix == B.getNumCols()) {
					Amatrix++;
					Bmatrix = 0;
				}
			}
		}
		return result;
	}
	
	//Multiplies by matrix by a constant
	public static Matrix multiply(Matrix A, Double b) {
		Matrix result = A.clone();
		for(int i = 0; i < A.getNumRows(); i++) {
			for(int j = 0; j < A.getNumCols(); j++) {
				result.setValue(i, j, b*A.getValue(i, j));
			}
		}
		return result;
	}
	
	//Adds two matrices
	public static Matrix add(Matrix A, Matrix B) throws MatrixException {
		Matrix result = new Matrix(A.getNumRows(), A.getNumCols());
		if(A.getNumRows() != B.getNumRows() && A.getNumCols() != B.getNumCols()) {
			throw new MatrixException("Matrices not same size");
		}
		else {
			for(int i = 0; i < A.getNumRows(); i++) {
				for(int j = 0; j < A.getNumCols(); j++) {
					result.setValue(i, j, A.getValue(i, j) + B.getValue(i, j));
				}
			}
			return result;
		}
	}
	
	//Subtracts two matrices
	public static Matrix sub(Matrix A, Matrix B) throws MatrixException {
		Matrix result = new Matrix(A.getNumRows(), A.getNumCols());
		if(A.getNumRows() != B.getNumRows() && A.getNumCols() != B.getNumCols()) {
			throw new MatrixException("Matrices not same size");
		}
		else {
			for(int i = 0; i < A.getNumRows(); i++) {
				for(int j = 0; j < A.getNumCols(); j++) {
					result.setValue(i, j, A.getValue(i, j) - B.getValue(i, j));
				}
			}
			return result;
		}
	}
	
	
	//Gets determinate of n x n matrix
	public static double det(Matrix A) throws MatrixException {
		
		Double determinate = 0.0;
		
		if(A.getNumCols() != A.getNumRows()) {
			throw new MatrixException("Cannot take determinate of none n x n matrix");
		}
		else {
			if(A.getNumCols() == 2 && A.getNumRows() == 2) {
				return A.getValue(0, 0)*A.getValue(1, 1) - A.getValue(1, 0)*A.getValue(0, 1);
			}
			
			Matrix temp = new Matrix(A.getNumCols()-1);
			
			for(int i = 0; i < A.getNumCols(); i++) {
				
				int col;
				for(int j = 1; j < A.getNumCols(); j++) {
					col = 0;
					for(int k = 0; k < A.getNumCols(); k++) {
						if(k != i) {
							temp.setValue(j-1, col, A.getValue(j, k));
							col++;
						}
					}
				}
				if(i%2 == 1) {
					determinate += det(temp)*-A.getValue(0, i);
				}
				else {
					determinate += det(temp)*A.getValue(0, i);
				}
			}
			return determinate;
		}
	}
	
	//Checks if matrix is orthogonal
	public static boolean isOrthogonal(Matrix A) throws MatrixException {
		if(A.getNumRows() != A.getNumCols()) {
			return false;
		}
		
		Double det = det(A);
		
		if(det != 1.0 || det != -1.0) {
			return false;
		}
		return true;
	}

	//Checks if matrix is special orthogonal
	public static boolean isSpecialOrthogonal(Matrix A) throws MatrixException {
		if(A.getNumRows() != A.getNumCols()) {
			return false;
		}
		
		Double det = det(A);
		
		if(det != 1.0) {
			return false;
		}
		return true;
	}
	
	//Creates deep copy of matrix object
	@Override
	public Matrix clone() {
		
		Matrix result = new Matrix(matrix.length, matrix[0].length);
		
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[0].length; j++) {
				result.setValue(i, j, matrix[i][j]);
			}
		}
		return result;
	}
}
