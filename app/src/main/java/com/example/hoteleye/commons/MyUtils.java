package com.example.hoteleye.commons;

import android.util.Log;

import com.google.firebase.Timestamp;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyUtils {
    public static Date covertStringtoDate(String str_date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = format.parse(str_date);
        return date;
    }
    public static Date covertStringtoDate2(String str_datetime) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        Date date = format.parse(str_datetime);
        return date;
    }

    public static String covertDateToString(String date) throws ParseException {

        SimpleDateFormat spf=new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        Date newDate=spf.parse(date);
        spf= new SimpleDateFormat("dd/MM/yyyy");
        date = spf.format(newDate);
        return date;
    }
    public static String covertDateToString2(String date) throws ParseException {

        SimpleDateFormat spf=new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        Date newDate=spf.parse(date);
        spf= new SimpleDateFormat("HH:mm dd/MM/yyyy");
        date = spf.format(newDate);
        return date;
    }

    public static Timestamp covertStringtoTimestamp(String str_date) throws ParseException {
        return new Timestamp(covertStringtoDate(str_date));
    }
    public static Timestamp covertStringtoTimestamp2(String str_date) throws ParseException {
        return new Timestamp(covertStringtoDate2(str_date));
    }
    public static String covertTimestamptoString(Timestamp timestamp) throws ParseException {
        Date reservationdate = timestamp.toDate();
        String datee = reservationdate.toString();
        return covertDateToString(datee);
    }
    public static String covertTimestamptoString2(Timestamp timestamp) throws ParseException {
        Date reservationdate = timestamp.toDate();
        String datee = reservationdate.toString();
        return covertDateToString2(datee);
    }

    public static int calculateRoomPriceNight(String nightprice_string){
        return Integer.parseInt(nightprice_string);
    }
    public static String calll(){
        String dayDifference = "0";
        try {
            String CurrentDate= "12:00 20/10/2019";
            String FinalDate= "12:00 21/10/2019";
            Date date1;
            Date date2;
            SimpleDateFormat dates = new SimpleDateFormat("HH:mm dd/MM/yyyy");
            date1 = dates.parse(CurrentDate);
            date2 = dates.parse(FinalDate);
            long difference = Math.abs(date1.getTime() - date2.getTime());
            long differenceDates = difference / (24 * 60 * 60 * 1000);
            dayDifference = Long.toString(differenceDates);
//            textView.setText("The difference between two dates is " + dayDifference + " days");
            Log.e("thanhphan1", "calculateRoomPrice: "+dayDifference );
        } catch (Exception exception) {
//            Toast.makeText(this, "Unable to find difference", Toast.LENGTH_SHORT).show();
        }
        return dayDifference;

    }
    public static int calculateRoomPrice(String openprice_str, String hourprice_str, String nightprice_str, String checkinDate_str, String checkoutDate_str){
        int room_price = 0;
//        String CurrentDate= "20:00 09/09/2019";
//        String FinalDate= "23:15 18/01/2021";
//        checkinDate_str = CurrentDate;
//        checkoutDate_str = FinalDate;

        int openprice = Integer.parseInt(openprice_str);
        int hourprice = Integer.parseInt(hourprice_str);
        int nightprice = Integer.parseInt(nightprice_str);
        int day_room_price = nightprice*2;

        int hour_checkin = Integer.parseInt(String.valueOf(checkinDate_str.charAt(0))+String.valueOf(checkinDate_str.charAt(1)));
        int minutes_checkin = Integer.parseInt(String.valueOf(checkinDate_str.charAt(3))+String.valueOf(checkinDate_str.charAt(4)));
        int day_checkin = Integer.parseInt(String.valueOf(checkinDate_str.charAt(6))+String.valueOf(checkinDate_str.charAt(7)));
        int month_checkin = Integer.parseInt(String.valueOf(checkinDate_str.charAt(9))+String.valueOf(checkinDate_str.charAt(10)));
        int year_checkin = Integer.parseInt(String.valueOf(checkinDate_str.charAt(12))+String.valueOf(checkinDate_str.charAt(13)));

        int hour_checkout = Integer.parseInt(String.valueOf(checkoutDate_str.charAt(0))+String.valueOf(checkoutDate_str.charAt(1)));
        int minutes_checkout = Integer.parseInt(String.valueOf(checkoutDate_str.charAt(3))+String.valueOf(checkinDate_str.charAt(4)));
        int day_checkout = Integer.parseInt(String.valueOf(checkoutDate_str.charAt(6))+String.valueOf(checkoutDate_str.charAt(7)));
        int month_checkout = Integer.parseInt(String.valueOf(checkoutDate_str.charAt(9))+String.valueOf(checkoutDate_str.charAt(10)));
        int year_checkout = Integer.parseInt(String.valueOf(checkinDate_str.charAt(12))+String.valueOf(checkinDate_str.charAt(13)));


        int hour_calculate = 0;
        int day_calculate = 0;
        int moth_calculate = 0;
        int year_calculate = 0;
        String dayDifference = "0";
        try {

            Date date1;
            Date date2;
            SimpleDateFormat dates = new SimpleDateFormat("HH:mm dd/MM/yyyy");
            date1 = dates.parse(checkinDate_str);
            date2 = dates.parse(checkoutDate_str);
            long difference = Math.abs(date1.getTime() - date2.getTime());
            long differenceDates = difference / (24 * 60 * 60 * 1000);
            dayDifference = Long.toString(differenceDates);
//            textView.setText("The difference between two dates is " + dayDifference + " days");
            Log.e("thanhphan1", "calculateRoomPrice: "+dayDifference );
            day_calculate = Integer.parseInt(dayDifference);
            if(day_calculate>0){
                if(hour_checkout>hour_checkin){
                    hour_calculate = hour_checkout - hour_checkin;
                }else if(hour_checkout <hour_checkin){
                    hour_calculate = (24-hour_checkin)+hour_checkout;
                }
                if(hour_calculate<1){
                    return room_price =  day_calculate*day_room_price;
                }else{

                    return room_price =  hour_calculate*hourprice + day_calculate*day_room_price;
                }
            }
            else{ //day_calculate ==0
                if(hour_checkout>hour_checkin){
                    hour_calculate = hour_checkout - hour_checkin;

                }else if(hour_checkout <hour_checkin){
                    hour_calculate = (24-hour_checkin)+hour_checkout;

                }
                //số giờ nghỉ < 1h
                if(hour_calculate<1){
                    return room_price = openprice;
                }
                else{
                    //nghỉ đêm
                    if(hour_checkin>=20){ //check in sau 20h
                        if(hour_checkout<12||hour_checkout>20){ //check out trước 12h or sau 20h
                            Log.e("lolhaha", "calculateRoomPrice: "+hour_checkout );
                            return room_price = nightprice;
                        }
                        else {
                            Log.e("lolhahal", "calculateRoomPrice: "+hour_checkout );
                            return room_price = (hour_checkout-12)*hourprice + nightprice;
                        }
                    }else if(hour_checkin<3){ //check in trước 3h
                        if(hour_checkout<12){
                            Log.e("lolhaha", "calculateRoomPrice: "+hour_checkout );
                            return room_price = nightprice;
                        }
                        else {
                            Log.e("lolhahal", "calculateRoomPrice: "+hour_checkout );
                            return room_price = (hour_checkout-12)*hourprice + nightprice;
                        }
                    }
                    // nghỉ bình thường
                    return room_price = openprice+ (hour_calculate-1)*hourprice;
                }
            }

        } catch (Exception exception) {
//            Toast.makeText(this, "Unable to find difference", Toast.LENGTH_SHORT).show();
        }

        return room_price;
    }
    public static String priceWithDecimal (Double price) {
        DecimalFormat formatter = new DecimalFormat("###,###,###.00");
        return formatter.format(price);
    }

    public static String priceWithoutDecimal (Double price) {
        DecimalFormat formatter = new DecimalFormat("###,###,###.##");
        return formatter.format(price);
    }

    public static String priceToString(Double price) {
        String toShow = priceWithoutDecimal(price);
        if (toShow.indexOf(".") > 0) {
            return priceWithDecimal(price);
        } else {
            return priceWithoutDecimal(price);
        }
    }
    public static String convertToVNDFormat(float cost){
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        return currencyVN.format(cost);
    }
    public static String convertToCurrencyFormat(float cost){
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getInstance(localeVN);

        return currencyVN.format((double)cost).replace(".",",");
    }
    public static String convertStringToFloat(String cost){
     return cost.replace(",","");
    }
    public static String convertToCurrencyFormat1(float cost){
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getInstance(localeVN);

        return currencyVN.format((double)cost).replace(",","!").replace(".",",").replace("!",".");
    }

    public static List<Object> fromJSon(String jsonString){
        Gson gson = new Gson();
        try {
            List<Object> list = Arrays.asList(gson.fromJson(jsonString, Object[].class));
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String  toJson(Object o){
        return new Gson().toJson(o);
    }
}
