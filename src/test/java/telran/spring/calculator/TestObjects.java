package telran.spring.calculator;

import telran.spring.calculator.dto.ArithmeticOperationData;
import telran.spring.calculator.dto.DateDaysOperationData;
import telran.spring.calculator.dto.DatesOperationData;

public class TestObjects {
    public static ArithmeticOperationData arithmeticData = new ArithmeticOperationData();
    public static DatesOperationData datesData = new DatesOperationData();
    public static DateDaysOperationData dateDaysData = new DateDaysOperationData();

    public static void restoreArithmeticData() {
        arithmeticData.operationName = "arithmetic-operation";
        arithmeticData.additionalData = "+";
        arithmeticData.operand1 = 3.0;
        arithmeticData.operand2 = 7.0;
    }

    public static void restoreDateData() {
        datesData.operationName = "dates-between";
        datesData.additionalData = null;
        datesData.dateFrom = "2020-10-10";
        datesData.dateTo = "2020-10-17";
    }

    public static void restoreDateDaysData() {
        dateDaysData.operationName = "dates-simple";
        dateDaysData.additionalData = null;
        dateDaysData.date = "2020-10-10";
        dateDaysData.days = 7;
    }
}
