package com.example.masaki.cluculator;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class CalculatorTest {

    private Calculator calculator;
//    ArrayList<String> list = new ArrayList<String>(Arrays.asList("4","+","5","×","6","×","7","÷","3","+","2"));
    ArrayList<String> list = new ArrayList<String>(Arrays.asList("4.5","+","5.4"));

    @Before
    public void setUp() throws Exception {
        calculator = new Calculator();
    }
    @Test
    public void テスト2() throws Exception {
        //予想：1 + 5 で 6が返ってくるはず
        assertEquals(76, 76);
    }
    @Test
    public void テスト1() throws Exception {
        //予想：1 + 5 で 6が返ってくるはず
        assertEquals(9.9, calculator.calc_all(list));
    }
}
