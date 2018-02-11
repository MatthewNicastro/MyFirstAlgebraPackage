package algebra;

import java.io.*;

public class Vector {
	
	private Double[] vector;
	
	//Creates a n dimensional vector
	public Vector(int length) {
		vector = new Double[length];
	}
	
	//Creates a 2 dimensional vector 
	public Vector(Double x, Double y) {
		vector = new Double[] {x, y};
	}
	
	//Creates a 3 dimensional vector
	public Vector(Double x, Double y, Double z) {
		vector = new Double[] {x, y, z};
	}
	
	//Creates a 4 dimensional vector
	public Vector(Double x, Double y, Double z, Double w) {
		vector = new Double[] {x, y, z, w};
	}
	
	/*
	 * Initializes vector from text file if length of vector greater then 4
	 * Format separate each value with ',' no spaces keep on same line
	*/
	public void setVector(String file) {
		try(BufferedReader reader = new BufferedReader(new FileReader(file))){
			String[] temp = reader.readLine().split(",");
			if(temp.length != vector.length) {
				throw new NotEnoughException("Data in text file does not match size of vector");
			}
			else {
				for(int i = 0; i < vector.length; i++) {
					vector[i] = Double.parseDouble(temp[i]);
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
	
	//Sets specific vector value
	public void setValue(int i, Double value) {
		vector[i] = value;
	}
	
	//Diplay's vector across screen
	public void displayL() {
		System.out.print("[");
		for(int i = 0; i < vector.length-1; i++) {
			System.out.print(vector[i]+", ");
		}
		System.out.print(vector[vector.length-1]);
		System.out.print("]");
	}
	
	//Displays vector down screen
	public void displayT() {
		System.out.print("|˜"+vector[0]+"˜|");
		for(int i = 1; i < vector.length-1; i++) {
			System.out.print("| "+vector[i]+" |");
		}
		System.out.print("|_"+vector[vector.length-1]+"_|");
	}
	
	//Gets vectors length
	public int getLength() {
		return vector.length;
	}
	
	//Returns a specific vector value
	public Double getValue(int i) {
		return vector[i];
	}
	
	//Gets inverse of vector
	public Vector getInverse() throws CloneNotSupportedException {
		Vector inverse = (Vector) this.clone();
		for(int i = 0; i < this.getLength(); i++) {
			if(this.getValue(i) == 0) {
				inverse.setValue(i, 0.0);
			}
			else {
				inverse.setValue(i, 1/this.getValue(i));
			}
		}
		return inverse;
	}
	
	//Gets magnitude of vector
	public Double magnitude() {
		Double magnitude = 0.0;
		for(int i = 0; i < vector.length; i++) {
			magnitude += Math.pow(vector[i], 2);
		}
		magnitude = Math.sqrt(magnitude);
		return magnitude;
	}
	
	//Adds new vector to current
	public void add(Vector x_1) throws NotEnoughException {
		if(this.getLength() != x_1.getLength()) 
			throw new NotEnoughException("Vectors must be of same size: \n"
					+this.getLength()+" != "+x_1.getLength());
		else {
			for(int i = 0; i < this.getLength(); i++) 
				this.setValue(i, this.getValue(i) + x_1.getValue(i));
		}
	}
	
	//Subtracts new vector from current
	public void sub(Vector x_1) throws NotEnoughException {
		if(this.getLength() != x_1.getLength())
			throw new NotEnoughException("Vectors must be of same size: \n"
					+this.getLength()+" != "+x_1.getLength());
		else {
			for(int i = 0; i < this.getLength(); i++)
				this.setValue(i, this.getValue(i) - x_1.getValue(i));
		}
	}
	
	//Multiplies current vector by new one
	public void multiply(Vector x_1) throws NotEnoughException {
		if(this.getLength() != x_1.getLength())
			throw new NotEnoughException("Vectors must be of same size: \n"
					+this.getLength()+" != "+x_1.getLength());
		else {
			for(int i = 0; i < this.getLength(); i++)
				this.setValue(i, this.getValue(i) * x_1.getValue(i));
		}
	}
	
	//Multiplies vector by constant
	public void multiply(Double x){
		for(int i = 0; i < this.getLength(); i++)
			this.setValue(i, this.getValue(i) * x);	
	}
	
	//Matrix transformation
	public Vector transformation(Matrix A) throws NotEnoughException {
		Vector transformed = new Vector(this.getLength());
		double result;
		
		if(A.getNumCols() != this.getLength())
			throw new NotEnoughException("Number of columns in matrix is not same size as vector");
		
		else {
			for(int i = 0; i < A.getNumRows(); i++) {
				result = 0.0;
				for(int j = 0; j < A.getNumCols(); j++)
					result += A.getValue(i, j)*this.getValue(j);
				transformed.setValue(i, result);
			}
		}
		return transformed;
	}
	
	//Calculates dot product of two vectors
	public Double dot(Vector x_1) throws NotEnoughException {
		Double result = 0.0;
		if(this.getLength() != x_1.getLength())
			throw new NotEnoughException("The vectors must be of same size to take dot product");
		else {
			for(int i = 0; i < this.getLength(); i++)
				result += (this.getValue(i)*x_1.getValue(i));
		}
		return result;
	}
	
}
