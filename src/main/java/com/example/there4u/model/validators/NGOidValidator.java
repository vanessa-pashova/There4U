package com.example.there4u.model.validators;

public class NGOidValidator {
    private static int digitCount(long num) {
        int count = 0;

        while (num > 0) {
            num /= 10;
            count++;
        }

        return count;
    }

    public static void validateNGOid(long NGOid) {
        int len = digitCount(NGOid);
        if(len != 6) {
            throw new IllegalArgumentException("NGOid must be 6 digits, [isValidNGOid(), NGOidValidator]");
        }

        if(NGOid / 1000 != 100) {
            throw new IllegalArgumentException("NGOid must begin with \"100 \", [isValidNGOid(), NGOidValidator]");
        }
    }
}
