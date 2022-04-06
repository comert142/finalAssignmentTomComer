
package com.mycompany.finalassignmenttomcomer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.TreeMap;
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
   assembleGraph cityBusGraph;
   
   public BusPathAlgo(String fileOfTransfers, String timesOfbusStopsFile, String justbusStopsFile){
               this.fileOfTransfers = fileOfTransfers;
               this.timesOfbusStopsFile = timesOfbusStopsFile;
               this.justbusStopsFile = justbusStopsFile;
               allbusStops = new ArrayList<busStop>(); // this is the array list of all my bus stops 
   }
   public void findbusStops() throws FileNotFoundException {
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
                stopsScanner.close();//now create edges between these indexes in arrays 
                
   }//create edges 
   
      class Edge {

        int from, to;
        double cost;

        Edge(int from, int to, double cost) {
            this.to = to;
            this.from = from;
            this.cost = cost;
        }

      }
      void createEdges() throws FileNotFoundException{
                  File stopTimes = new File(timesOfbusStopsFile);
                  File transfers = new File(fileOfTransfers);
                  Scanner info;
       info = new Scanner(transfers);
                  info.nextLine();
                          cityBusGraph = new assembleGraph(maxId);//why cant you find my graph???
                                  String transfer;
                                  String[] transferValues;
                                          while (info.hasNextLine()) {
                                            transfer = info.nextLine();
                                            transferValues = transfer.split(",");
                                            int from = Integer.parseInt(transferValues[0]);
            int to = Integer.parseInt(transferValues[1]);
            int type = Integer.parseInt(transferValues[2]);
            int cost = (type == 0) ? 2 : (Integer.parseInt(transferValues[3]) / 100);

            cityBusGraph.addEdge(from, to, cost);
        }info.close();

        info = new Scanner(stopTimes);
        info.nextLine();

        String journey;
        String[] tripValue;
        int from = 1;
        int to, sequenceValue;
        boolean skipEdge;

        while (info.hasNextLine()) {
            journey = info.nextLine();
            tripValue = journey.split(",");
            sequenceValue = Integer.parseInt(tripValue[4]);

            skipEdge = false;

            if (sequenceValue != 1) {
                to = Integer.parseInt(tripValue[3]);
                for (Edge ed : cityBusGraph.adj(from)) {
                    if (ed.to == to) {
                        skipEdge = true;
                        break;
                    }
                }
                if (!skipEdge) {
                    cityBusGraph.addEdge(from, to, 1);
                }
            }

            from = Integer.parseInt(tripValue[3]);
        }
        info.close();
    }
          public void relax(double[] distanceTobusStop, Edge[] edgeTo, int v) {
        for (Edge e : cityBusGraph.adj(v)) {
            int w = e.to;
            if (distanceTobusStop[w] > distanceTobusStop[v] + e.cost) {//relaxing edges between nodes if null
                distanceTobusStop[w] = distanceTobusStop[v] + e.cost;
                edgeTo[w] = e;
            }
        }
          }
          
      
    public Edge[] dijkstra(int firstbusStop) {//couldnt do DFS 
        double[] distanceTobusStop = new double[maxId];
        boolean[] relaxed = new boolean[maxId];
        Edge[] edgeTo = new Edge[maxId];

        for (busStop stop : allbusStops) {
            distanceTobusStop[stop.getIdentif()] = Double.POSITIVE_INFINITY;
            relaxed[stop.getIdentif()] = false;
            edgeTo[stop.getIdentif()] = null;
        }
        distanceTobusStop[firstbusStop] = 0;

        int currentbusStop = firstbusStop;

        for (int i = 0; i < allbusStops.size(); i++) {
            relax(distanceTobusStop, edgeTo, currentbusStop);
            relaxed[currentbusStop] = true;
            double min = Double.POSITIVE_INFINITY;
            for (busStop stop : allbusStops) {
                if (distanceTobusStop[stop.getIdentif()] < min && !relaxed[stop.getIdentif()]) {
                    min = distanceTobusStop[stop.getIdentif()];
                    currentbusStop = stop.getIdentif();
                }
            }
        }
        distanceBetween = distanceTobusStop;
        return edgeTo;
    }
//now implement a way to recall the id of the bus/stop by the user 
    //superceding this I will write a path accquiring method which 
    
     public String getbusStopById(int id) {
        for (busStop t : allbusStops) {
            if (t.getIdentif() == id) {
                return t.gettingTheName();
            }
        }
        return "NO BUS STOP/INCORRECT BUS STOP";
    }


 

                                                           
  




             
         

            
         

              
   class assembleGraph {//would much prefer to put in a multi map TODO THIS LATER !
               ArrayList<ArrayList<Edge>> adjL = new ArrayList<ArrayList<Edge>>();//EDGE TO BE DEFINED LATER ON SOME METHOD 
                       assembleGraph(int edges) {
                                       for (int i = 0; i < edges; i++) {
                                                        adjL.add(new ArrayList<Edge>());
                                       }//multiMap INSTEAD WOULD BE BETTER 
                       }
                                               ArrayList<Edge> adj(int vertex) {
                                       return adjL.get(vertex);
                                               }
                                               void addEdge(int from, int to, int cost) {
                                                               adjL.get(from).add(new Edge(from, to, cost));
                                               }
   }
      public String bringMeTheTripInfo(int firstbusStop, int nextbusStop) throws FileNotFoundException {
        createEdges();

        if (getbusStopById(firstbusStop) == null) {
            return "The stop you are planning on leaving from, does not exist";
        }
        if (getbusStopById(nextbusStop) == null) {
            return "The stop you are planning on going to, does not exist";
        }

        Edge[] edgeTo = dijkstra(firstbusStop);

        ArrayList<String> sequence = new ArrayList<String>();

        int currentbusStopbusStop = nextbusStop;

        sequence.add(getbusStopById(nextbusStop));
        do {
            try {
                currentbusStopbusStop = edgeTo[currentbusStopbusStop].from;
            } catch (NullPointerException e) {
                return "There is no path !";
            }
            sequence.add(getbusStopById(currentbusStopbusStop));
        } while (currentbusStopbusStop != firstbusStop);
        Collections.reverse(sequence);

        sequence.add(0, distanceBetween[nextbusStop] + "");

        String returnString = "";
        for (String string : sequence) {
            returnString += string + "\n";
        }
        return returnString;
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


/**
 *
 * @author tcome
 */
public class BusStopSearch {//this method should search for a bus stop and recall all the details of it 
    //essentially the plan here to is to create a tree by which all the details are then drawn from 
    	
	private NodeInTree root;
	
	private class NodeInTree {
                private char value;          
        private int details;       //this here will contain all the data associated with the stop - itll be the 'key'
                private NodeInTree leftNode, thisNode, rightNode;  //left,right and current nodes 

        private NodeInTree(char value) {
            this.value = value;details = 0;//initiallising all the variables 
            leftNode = null;thisNode = null;rightNode = null;
            }
        }
	
	public BusStopSearch(){root = null;
	}
	public void insert(String word, int k){
		root = insert(root,word,k);
	}private NodeInTree insert(NodeInTree rn, String word, int x){
            if (rn == null)
            rn = new NodeInTree(word.charAt(0));
 
        if (word.charAt(0) < rn.value)
            rn.leftNode = insert(rn.leftNode, word, x);
        else if (word.charAt(0) > rn.value)
            rn.rightNode = insert(rn.rightNode, word, x);
        else
        {
            if (word.substring(1).length() != 0)
                rn.thisNode = insert(rn.thisNode, word.substring(1), x);
            else
                rn.details = x;
        }
        return rn;
	}
	
	private NodeInTree search(String busStopInput)
	{
		return search(root, busStopInput); 
	}
	
	private NodeInTree search(NodeInTree rn, String busStopInput)
	{
		if(rn == null || busStopInput.length() == 0){return null;
		}if(busStopInput.charAt(0) < rn.value)
		{return search(rn.leftNode, busStopInput);
		}   else if(busStopInput.charAt(0) > rn.value)
		{return search(rn.rightNode, busStopInput);
		}else{
                if(busStopInput.substring(1).length() == 0){
				return rn;}
                else return search(rn.thisNode, busStopInput.substring(1));
		}
		
	}
    }

    


 }

       

       
       
   






                    
                

                       


                       





               
               
       




       
   
   
   
 
       


