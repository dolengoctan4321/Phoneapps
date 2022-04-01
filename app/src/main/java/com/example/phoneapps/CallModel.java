package com.example.phoneapps;

public class CallModel {

    String callername, name, number, calltype, callduration, time;
    //có protect, private mới đảm bảo đến tính đóng gói

    public CallModel(String name, String calltype, String callduration, String time) {
        this.name= name;
        this.calltype = calltype;
        this.callduration = callduration;
        this.time = time;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCalltype() {
        return calltype;
    }

    public void setCalltype(String calltype) {
        this.calltype = calltype;
    }

    public String getCallduration() {
        return callduration;
    }

    public void setCallduration(String callduration) {
        this.callduration = callduration;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

//    public String getNumber() {
//        return number;
//    }
//
//    public void setNumber(String number) {
//        this.number = number;
//    }
}
