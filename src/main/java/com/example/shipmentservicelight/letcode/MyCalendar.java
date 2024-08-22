package com.example.shipmentservicelight.letcode;

import java.util.ArrayList;
import java.util.List;

public class MyCalendar {
    List<Event> events = new ArrayList<>();

    public MyCalendar() {
        
    }
    
    public boolean book(int start, int end) {
        if(events.size() ==0){
            events.add(new Event(true,start));
            events.add(new Event(false,end));
            return true;
        }
        int l =0, r = events.size();
        int indexOfLeft = -1;
        while (l<r){
            int m = l+(r-l)/2;
            Event mEvent = events.get(m);
            if(mEvent.time <= start){
                indexOfLeft = m;
                l = m+1;
            }else {
                r = m;
            }
        }
        if(indexOfLeft == -1){
            events.add(new Event(true,start));
            events.add(new Event(false,end));
            events.sort((one,two) -> {
                if(one.time > two.time){
                    return 1;
                }else if(one.time < two.time){
                    return -1;
                }else {
                    return 0;
                }
            });
            return true;
        }else {
            if(indexOfLeft+1 >=events.size()){
                if(events.get(indexOfLeft).isStartEvent){
                    return false;
                }else {
                    events.add(new Event(true,start));
                    events.add(new Event(false,end));
                    events.sort((one,two) -> {
                        if(one.time > two.time){
                            return 1;
                        }else if(one.time < two.time){
                            return -1;
                        }else {
                            return 0;
                        }
                    });
                    return true;
                }
            }else {
                if(events.get(indexOfLeft).isStartEvent || events.get(indexOfLeft+1).time<= end){
                    return false;
                }else {
                    events.add(new Event(true,start));
                    events.add(new Event(false,end));
                    events.sort((one,two) -> {
                        if(one.time > two.time){
                            return 1;
                        }else if(one.time < two.time){
                            return -1;
                        }else {
                            return 0;
                        }
                    });
                    return true;
                }
            }

        }
    }

    public static void main(String[] args) {
        MyCalendar m  = new MyCalendar();

        System.out.println(m.book(47,50));
        System.out.println(m.book(33,41));
        System.out.println(m.book(39,45));
        System.out.println(m.book(33,42));
        System.out.println(m.book(25,32));

        System.out.println(m.book(26,35));
        System.out.println(m.book(19,25));
        System.out.println(m.book(3,8));
        System.out.println(m.book(8,13));
        System.out.println(m.book(18,27));
    }
}
class Event{
    public boolean isStartEvent;
    //true - start false- end
    public int time;

    public Event(boolean isStartEvent, int time) {
        this.isStartEvent = isStartEvent;
        this.time = time;
    }
}
