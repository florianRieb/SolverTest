package SolverImpl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;


public class Main {
    static  int conditionCount;
    static int chromaticNum;

    public static void main(String... args) throws IOException {

        /**
        int mb = 1024 * 1024;
        Runtime instance =  Runtime.getRuntime();
        System.out.println( "Verfügbarer Speicher in MB:" + instance.totalMemory()/mb);
        System.out.println("Feier Speicher in MB: "+ instance.freeMemory()/mb);
        System.out.println("genutzter Speicher in MB: " + (instance.totalMemory()-instance.freeMemory())/mb);
        System.out.println("Max Speicher in MB: "+ instance.maxMemory()/mb);


        String writeLine;
        **/

        Logger logger = Logger.getLogger(Main.class.getName());
        logger.setUseParentHandlers(false);
        //Datei muss bereits bestehen
        //FileHandler fh = new FileHandler("log.txt",true);
        MyFormatter formatter = new MyFormatter();
        //logger.addHandler(fh);
        //fh.setFormatter(new SimpleFormatter());



        if(args.length==2){
           // BufferedWriter writer = new BufferedWriter(new FileWriter("log.txt", true));
           // writer.newLine();

            int modules = Integer.valueOf(args[0]);
            double waight = Double.valueOf(args[1]);

            long duration = startTest(modules,waight);


            //duration = duration/1000; // in Microsek
            int i = (int) (modules * waight);
            StringBuilder sb = new StringBuilder();
            sb.append(duration).append("; ").append(modules).append("; ").append(waight).append("; ").append(conditionCount).append("; ").append(chromaticNum);
            //logger.info(sb.toString());
            //writer.append(sb.toString());
            //writer.close();

            System.out.println(sb.toString());


        }

    }

    public static long startTest(int moduleCount, double waight){

        Set<String> validStrings = new HashSet<>();
        for(int i = 0; i<moduleCount;i++){
            validStrings.add(String.valueOf(i));

        }

        List<String> modules = new ArrayList<>();
        modules.addAll(validStrings);


        List<Edge<String,String>>  cons = newRndCon(modules,waight);


        conditionCount = cons.size();



        //System.out.println(cons);
        Graph g = new Graph("",cons,validStrings);
        //System.out.println("start coloring");
        long start = System.nanoTime();
        Map<String,String> resolverResult = g.colourVertices();
        long end = System.nanoTime();
      //  System.out.println("Anzahl Module:"+ modules + "Anzahl vollständigisolierter Module: "+(int)(moduleCount*waight) );
        Map<String, String> result = swap(resolverResult);
//        System.out.println(result);
        chromaticNum = g.chromaticNum;
        return end-start;

    }



    public static List newRndCon(List<String> modules, double conditions ){


        //Set<String> dup = new HashSet<>();

        List<Edge<String,String>> returnConditions = new LinkedList<>();
        int upperbound = (int)(conditions*modules.size());
        int rnd2;

        long startRnd = System.nanoTime();
        for(String from : modules){
            int rnd = ThreadLocalRandom.current().nextInt(0, upperbound + 1);
            for(int i = 0; i<rnd;i++){

                rnd2 = ThreadLocalRandom.current().nextInt(0, modules.size()  );
                while(modules.get(rnd2).equals(from)){
                    rnd2 = ThreadLocalRandom.current().nextInt(0, modules.size()  );

                }
                returnConditions.add(new Edge<>(from,modules.get(rnd2)));

            }


        }
        long endRnd = System.nanoTime();
        //System.out.println("time to create conditions:" +(endRnd-startRnd));
        //System.out.println("Condition[] size: " + returnConditions.size());
    return returnConditions;
    }




    public static Map swap(Map<String, String> map){
        Map<String,Set<String>> returnMap = new HashMap<>();

        for(Map.Entry<String,String> entry:map.entrySet())
            if(returnMap.keySet().contains(entry.getValue())){
                returnMap.get(entry.getValue()).add(entry.getKey());
            }else{
                returnMap.put(entry.getValue(),new HashSet<String>(Set.of(entry.getKey())));
            }

        return  returnMap;


    }
}
