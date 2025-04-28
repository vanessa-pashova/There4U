package com.example.there4u.model.validators;

import com.example.there4u.model.contributor.TypeOfContributor;
public class ContributorIdValidator {
    private static int len(long num) {
        int count = 0;

        while (num > 0) {
            num /= 10;
            count++;
        }

        return count;
    }

    public static boolean isValidContributorId(long contributorId, TypeOfContributor typeOfContributor) {
        int idLength = len(contributorId);

        if(idLength != 5) {
            return false;
        }

        int code = (int) contributorId / 1000;
        switch (code) {
            case 91: {
                if(typeOfContributor == TypeOfContributor.CANTEEN) {
                    return true;
                }

                break;
            }

            case 92: {
                if(typeOfContributor == TypeOfContributor.GROCERY_STORE) {
                    return true;
                }

                break;
            }

            case 93: {
                if(typeOfContributor == TypeOfContributor.RESTAURANT) {
                    return true;
                }

                break;
            }

            default: {
                throw new IllegalArgumentException(">! Invalid contributor ID or Type [isValidContributorId(), ContributorIdValidator]");
            }
        }

        return false;
    }
}
