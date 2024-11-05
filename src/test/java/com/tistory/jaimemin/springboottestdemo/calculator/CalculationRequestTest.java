package com.tistory.jaimemin.springboottestdemo.calculator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CalculationRequestTest {

	@Test
	public void 유효한_숫자를_파싱할_수_있다() {
		// given
		String[] parts = new String[] {"2", "+", "3"};

		// when
		CalculationRequest calculationRequest = new CalculationRequest(parts);

		// then
		assertEquals(2, calculationRequest.getNum1());
		assertEquals(3, calculationRequest.getNum2());
		assertEquals("+", calculationRequest.getOperator());
	}

	@Test
	public void 세자리_숫자가_넘어가는_유효한_숫자를_파싱할_수_있다() {
		// given
		String[] parts = new String[] {"232", "+", "333"};

		// when
		CalculationRequest calculationRequest = new CalculationRequest(parts);

		// then
		assertEquals(232, calculationRequest.getNum1());
		assertEquals(333, calculationRequest.getNum2());
		assertEquals("+", calculationRequest.getOperator());
	}

	@Test
	public void 유효한_길이의_숫자가_들어오지_않으면_에러를_던진다() {
		// given
		String[] parts = new String[] {"232", "+"};

		// when
		// then
		assertThrows(BadRequestException.class, () -> {
			new CalculationRequest(parts);
		});
	}

	@Test
	public void 유효하지_않은_연산자가_들어오면_에러를_던진다() {
		// given
		String[] parts = new String[] {"232", "x", "2"};

		// when
		// then
		assertThrows(InvalidOperatorException.class, () -> {
			new CalculationRequest(parts);
		});
	}

	@Test
	public void 유효하지_않은_길이의_연산자가_들어오면_에러를_던진다() {
		// given
		String[] parts = new String[] {"232", "*-", "2"};

		// when
		// then
		assertThrows(InvalidOperatorException.class, () -> {
			new CalculationRequest(parts);
		});
	}
}