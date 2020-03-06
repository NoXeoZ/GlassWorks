package project.entities;

import java.util.*;

public class Sells {

    /*private static Map<Integer,Long> sells=new HashMap<Integer,Long>();

    public static void addSell(Sell s){
        if(sells.containsKey(s.getCip())){
            long nb=sells.get(s)+1;
            System.out.println("ldjfkhukghdfkkjerf "+nb);
            sells.replace(s.getCip(),nb);
        }else{
            sells.put(s.getCip(),1L);
        }
    }

    public static Map<Integer,Long> getTotal(){
        return Collections.unmodifiableMap(sells);
    }*/

    private static List<Sell> sells= new ArrayList<Sell>();

    public static void addSell(Sell s){
        sells.add(s);
    }

    public static List<Sell> getTotal(){
        return sells;
    }

}
