package com.pecpaker.interestcalculatorapp.calculator;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CalculatorViewModel extends ViewModel {

    private MutableLiveData<Double> calculatedInterestMutableLiveData;

    public LiveData<Double> getCalculatedInterest() {
        if (calculatedInterestMutableLiveData == null) {
            calculatedInterestMutableLiveData = new MutableLiveData<>();
        }
        return calculatedInterestMutableLiveData;
    }

    private MutableLiveData<Integer> yearsMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<Double> rateMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<Double> savingsAmountMutableLiveData = new MutableLiveData<>();

    private void calculate() {
        double savingsAmount = savingsAmountMutableLiveData.getValue() != null ? savingsAmountMutableLiveData.getValue() : 0.0;
        double rate = rateMutableLiveData.getValue() != null ? rateMutableLiveData.getValue() : 0.1;
        int years = yearsMutableLiveData.getValue() != null ? yearsMutableLiveData.getValue() : 1;

        double amount;
        if (rate == 0.0) {
            amount = savingsAmount * years;
        } else {
            amount = savingsAmount * (1 + rate * years);
        }
        calculatedInterestMutableLiveData.setValue(amount);
    }

    void updateYears(String yearsString) {
        yearsMutableLiveData.setValue(Integer.parseInt(yearsString));
        if (yearsMutableLiveData.getValue() != null) {
            if (yearsMutableLiveData.getValue() == 1) {
                rateMutableLiveData.setValue(0.6 / 12);
            } else if (yearsMutableLiveData.getValue() > 1 && yearsMutableLiveData.getValue() < 3) {
                rateMutableLiveData.setValue(0.8 / 12);
            } else if (yearsMutableLiveData.getValue() > 2 && yearsMutableLiveData.getValue() < 25) {
                rateMutableLiveData.setValue(0.13 / 12);
            } else if (yearsMutableLiveData.getValue() > 24) {
                rateMutableLiveData.setValue(0.155 / 12);
            }
        }
        calculate();
    }

    void updateSavingsAmount(String savingsAmount) {
        savingsAmountMutableLiveData.setValue(Double.parseDouble(savingsAmount));
        calculate();
    }

    void updateSelectedPackage(String selectedPackage) {
        switch (selectedPackage) {
            case "Flex Naira":
                rateMutableLiveData.setValue(0.10);
                break;
            case "SafeLock":
                if (yearsMutableLiveData.getValue() != null) {
                    if (yearsMutableLiveData.getValue() == 1) {
                        rateMutableLiveData.setValue(0.6 / 12);
                    } else if (yearsMutableLiveData.getValue() > 1 && yearsMutableLiveData.getValue() < 3) {
                        rateMutableLiveData.setValue(0.8 / 12);
                    } else if (yearsMutableLiveData.getValue() > 2 && yearsMutableLiveData.getValue() < 25) {
                        rateMutableLiveData.setValue(0.13 / 12);
                    } else if (yearsMutableLiveData.getValue() > 24) {
                        rateMutableLiveData.setValue(0.155 / 12);
                    }
                }
                break;
            case "Targets":
                rateMutableLiveData.setValue(0.0);
                break;
            case "Flex Dollar":
                rateMutableLiveData.setValue(0.6);
                if (yearsMutableLiveData.getValue() != null) {
                    if (yearsMutableLiveData.getValue() < 11) {
                        rateMutableLiveData.setValue(0.0);
                    }
                }
                break;
        }
        calculate();
    }
}
