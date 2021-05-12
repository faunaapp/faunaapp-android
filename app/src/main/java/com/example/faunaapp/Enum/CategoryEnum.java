package com.example.faunaapp.Enum;

public enum CategoryEnum {
    MEDICINE(0),
    FOOD(1),
    ACTIVITY(2),
    APPOINTMENT(3),
    HEAT(4);

    private int value;
    private CategoryEnum(int value) {
       this.value = value;
    }
    public int getValue() {
        return value;
    }
    public String getName(int number){
        return this.name();
    }
}
