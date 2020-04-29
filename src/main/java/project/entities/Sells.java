package project.entities;

import java.util.*;

public class Sells {

    private static Map<Integer,List<Sell>>sellsMap=new HashMap<Integer,List<Sell>>();

    private static Map<Integer,List<Sell>>sellsMapDrug=new HashMap<Integer,List<Sell>>();

    public static void addSellList(Sell s){
        if(sellsMap.containsKey(s.getIdPharm())){
            List<Sell>sl=sellsMap.get(s.getIdPharm());
            sl.add(s);
            sellsMap.put(s.getIdPharm(),sl);
        }else{
            List<Sell>sl=new ArrayList<Sell>();
            sl.add(s);
            sellsMap.put(s.getIdPharm(),sl);
        }
    }

    public static void addSellListDrug(Sell s){
        if(sellsMapDrug.containsKey(s.getCip())){
            List<Sell>sl=sellsMapDrug.get(s.getCip());
            sl.add(s);
            sellsMapDrug.put(s.getCip(),sl);
        }else{
            List<Sell>sl=new ArrayList<Sell>();
            sl.add(s);
            sellsMapDrug.put(s.getCip(),sl);
        }
    }

    public static Map<Integer,List<Sell>> getTotalList(){
        return Collections.unmodifiableMap(sellsMap);
    }

    public static Map<Integer,List<Sell>> getTotalListDrug(){
        return Collections.unmodifiableMap(sellsMapDrug);
    }

    private static List<Sell> sells= new ArrayList<Sell>();

    public static void addSell(Sell s){
        sells.add(s);
    }

    public static List<Sell> getTotal(){
        return sells;
    }

}
