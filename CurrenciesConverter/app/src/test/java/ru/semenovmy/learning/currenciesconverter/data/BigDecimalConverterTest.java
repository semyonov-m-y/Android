package ru.semenovmy.learning.currenciesconverter.data;

import org.junit.Before;
import org.junit.Test;
import org.simpleframework.xml.stream.InputNode;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BigDecimalConverterTest {

    private BigDecimalConverter mBigDecimalConverter;

    @Before
    public void setUp() {
        mBigDecimalConverter = new BigDecimalConverter();
    }

    @Test
    public void testRead() throws Exception {
        // arrange
        InputNode input = mock(InputNode.class);
        String value = "45,678";
        when(input.getValue()).thenReturn(value);
        BigDecimal expectedOutput = new BigDecimal("45.678");

        // act
        BigDecimal output = mBigDecimalConverter.read(input);

        // assert
        assertThat(output, is(expectedOutput));
    }
}