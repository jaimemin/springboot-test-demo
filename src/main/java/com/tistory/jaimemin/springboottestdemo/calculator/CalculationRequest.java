package com.tistory.jaimemin.springboottestdemo.calculator;

import java.util.List;

public class CalculationRequest {

	private final long num1;

	private final long num2;

	private final String operator;

	public CalculationRequest(String[] parts) {
		if (parts.length != 3) {
			throw new BadRequestException();
		}

		if (parts[1].length() != 1) {
			throw new InvalidOperatorException();
		}

		if (!List.of("+", "-", "*", "/").contains(parts[1])) {
			throw new InvalidOperatorException();
		}

		this.num1 = Long.parseLong(parts[0]);
		this.num2 = Long.parseLong(parts[2]);
		this.operator = parts[1];
	}

	public long getNum1() {
		return num1;
	}

	public long getNum2() {
		return num2;
	}

	public String getOperator() {
		return operator;
	}
}
