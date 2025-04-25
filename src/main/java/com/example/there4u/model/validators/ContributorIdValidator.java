package com.example.there4u.model.validators;

public class ContributorIdValidator {
    private static int len(long num) {
        int count = 0;

        while (num > 0) {
            num /= 10;
            count++;
        }

        return count;
    }

    public static boolean isValidContributorId(long contributorId) {
        int idLength = len(contributorId);

        if(idLength != 5) {
            return false;
        }

        int code = (int) contributorId / 1000;
        if(code == 90 || code == 91 || code == 92) {
            return true;
        }

        return false;
    }
}
