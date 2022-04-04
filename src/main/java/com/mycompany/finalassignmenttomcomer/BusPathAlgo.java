
package com.mycompany.finalassignmenttomcomer;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author tcome
 */
 class BusPathAlgo {
    //this file and class should find the shortest path between two bus stops 
    //has to be able to access the files and find out information then process them 
    //going to try and integrate a lot of the code from my last assignment 
    
   int maxId = 0;
   ArrayList<busStop> allbusStops;
   String fileOfTransfers, timesOfbusStopsFile, justbusStopsFile;//file declaration
   double[] distanceBetween;
   
   public BusPathAlgo(String fileOfTransfers, String timesOfbusStopsFile, String justbusStopsFile){
               this.fileOfTransfers = fileOfTransfers;
               this.timesOfbusStopsFile = timesOfbusStopsFile;
               this.justbusStopsFile = justbusStopsFile;
               allbusStops = new ArrayList<busStop>(); // this is the array list of all my bus stops 
   }
   public void findbusStops() {
               File stops = new File(justbusStopsFile);
               Scanner stopsScanner = new Scanner(stops);
               stopsScanner.nextLine();
               String stop;
                    String[] stopInfo;
                       int stopId;
                       
                while (stopsScanner.hasNextLine()) {//implementing scanner
                stop = stopsScanner.nextLine();
                stopInfo = stop.split(",");//ensuring seperation between information
                stopId = Integer.parseInt(stopInfo[0]);
                allbusStops.add(new busStop(stopId, stopInfo[2]));
                maxId = Math.max(stopId, maxId);
                }
                maxId++;//pursuing scanner through file
                stopsScanner.close();
   }
       class busStop {//declaring variables and fetching in these next few 

        int id;
        String name;
        
                busStop(int id, String name) {
            this.id = id;
            this.name = name;
        }
                 public String gettingTheName() {
            return name;
        }
                  public int getIdentif() {
            return id;
        }

       
       
   






                    
                }

                       


                       





               
               
       




       
   }
   
   
 
       


