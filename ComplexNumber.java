package algebra;

public class ComplexNumber{
	
	private boolean polar = false;
	private Vector num;
	
	private Vector getVec() {
		return num;
	}
	
	//Creates complex number
	public ComplexNumber(Double x, Double y) {
		num = new Vector(x, y);
	}
	
	//Gets real part of complex number
	public Double real() {
		return num.getValue(0);
	}
	
	//Gets imaginary part of complex number
	public Double imaginary() {
		return num.getValue(1);
	}
	
	//Gets magnitude of complex number for polar form
	 public Double magnitude() {
		return this.num.magnitude();
	}
		
	//Gets argument of complex number for polar form
	public Double arg() {
		return Double.valueOf(Math.atan2(this.num.getValue(1), this.num.getValue(0)));
	}
	
	//Adds new complex number to current
	public void add(ComplexNumber i){
		if(polar) {
			try {
				throw new PolarFormException("Add in cartiesian");
			} catch (PolarFormException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				this.num.add(i.getVec());
			} catch (NotEnoughException e) {
				e.printStackTrace();
			}
		}
	}
	
	//Subtracts new complex number from current
	public void sub(ComplexNumber i){
		if(polar) {
			try {
				throw new PolarFormException("Subtract in cartiesian");
			} catch (PolarFormException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				this.num.sub(i.getVec());
			} catch (NotEnoughException e) {
				e.printStackTrace();
			}
		}
	}
	
	//Multiplies new complex number by current one
	public void mult(ComplexNumber i) {
		if(polar) {
			this.num.setValue(0, this.magnitude()*i.magnitude());
			this.num.setValue(0, this.arg()+i.arg());
		}
		else {
			this.num.setValue(0, this.real()*i.real() - this.imaginary()*i.imaginary());
			this.num.setValue(1, this.real()*i.imaginary() + this.imaginary()*i.real());
		}
	}
	
	//Multiplies complex number by a Double
	public void mult(Double i) {
		if(polar)
			this.num.setValue(0, this.magnitude()*i);
		else
			this.num.multiply(i);
	}
	
	public void div(ComplexNumber i ) {
		if(polar) {
			this.num.setValue(0, this.magnitude()/i.magnitude());
			this.num.setValue(1, this.arg() - i.arg());
		}
		else {
			Double v = Math.pow(i.real(), 2)+Math.pow(i.imaginary(), 2);
			this.num.setValue(0, (this.real()*i.real() + this.imaginary()*i.imaginary())/v);
			this.num.setValue(1, (i.imaginary()*i.real() + this.real()*i.imaginary())/v);
		}
	}
	
	//Returns conjugate of complex number
	public ComplexNumber conj() {
		ComplexNumber z = new ComplexNumber(this.real(), -this.imaginary());
		return z;
	}
	
	//Returns inverse of complex number
	public ComplexNumber invrs() {
		Double v = Math.pow(this.real(), 2)+Math.pow(this.imaginary(), 2);
		ComplexNumber z = new ComplexNumber(this.real()/v, -this.imaginary()/v);
		return z;
	}
	
	//Converts to polar form
	public void toPolar() {
		this.num.setValue(0, this.magnitude());
		this.num.setValue(1, this.arg());
	}
	
	//Converts to Cartesian 
	public void toCatesian() {
		this.num.setValue(0, this.magnitude()*Math.cos(this.arg()));
		this.num.setValue(1, this.magnitude()*Math.sin(this.arg()));
	}
	
	//Overide's to string method
	@Override
 	public String toString() {
		if(num.getValue(0) == 0.0 && num.getValue(1) == 0.0)
			return "0.0";
		else if(polar)
			return String.format("%.2fe^(%.2f)",this.magnitude(), this.arg());
		else
			return String.format("%.2f + %.2fi", this.real(), this.imaginary());
	}

}
