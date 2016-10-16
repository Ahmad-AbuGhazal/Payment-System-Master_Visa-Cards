package edu.mum.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PaymentUtils {
private static final Pattern PATTERN_NUMARIC = Pattern.compile("^[0-9]*$");
public final static boolean isNumeric(String code) {
	if(code==null)
		return false;
	final Matcher matcher = PATTERN_NUMARIC.matcher(code);
	if (matcher.matches()) {
		return true;
	}
	return false;
}
public final static boolean CheckValidNumber(String ccNumber){
        int sum = 0;
        boolean alternate = false;
        for (int i = ccNumber.length() - 1; i >= 0; i--)
        {
                int n = Integer.parseInt(ccNumber.substring(i, i + 1));
                if (alternate)
                {
                        n *= 2;
                        if (n > 9)
                        {
                                n = (n % 10) + 1;
                        }
                }
                sum += n;
                alternate = !alternate;
        }
        return (sum % 10 == 0);
}
}