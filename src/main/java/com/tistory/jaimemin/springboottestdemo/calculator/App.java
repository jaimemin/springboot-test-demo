package com.tistory.jaimemin.springboottestdemo.calculator;

public class App {

	public static void main(String[] args) {
		CalculationRequest request = new CalculationRequestReader().read();
		long answer = new Calculator().calculate(request.getNum1(), request.getNum2(), request.getOperator());

		System.out.println(answer);
	}
}
