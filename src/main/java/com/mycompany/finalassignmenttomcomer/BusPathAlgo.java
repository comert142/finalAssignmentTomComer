
package com.mycompany.finalassignmenttomcomer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
private void pathWork() {//from my diijsktra last assignment which was with tree map 
       
       slwPath = Math.min(spA,  spB);
       slwPath = Math.min(slwPath, spC);
       
       if (filename == null) {
    	   slwPath = -1;
       }
       
       cityMap = new TreeMap<>();
       try {
    	   Scanner scanner = new Scanner(new FileInputStream(filename));
    	   	int V = scanner.nextInt();
    	   	int S = scanner.nextInt();
    	   	for(int i = 0; i < S; i++) {
    	   		if (scanner.hasNext()) {
    	   			int inter1 = scanner.nextInt();
    	   			int inter2 = scanner.nextInt ();
    	   			double dist = scanner.nextDouble();
    	   			Node node1, node2;
    	   			
    	   			if (cityMap.get(inter1) == null) {
    	   				node1 = new Node(inter1);
    	   				cityMap.put(inter1, node1);
    	   			}
    	   			else {
    	   				node1 = cityMap.get(inter1);
    	   			}
    	   			
    	   			if (cityMap.get(inter2) == null) {
    	   				node2 = new Node(inter2);
    	   				cityMap.put(inter2, node2);
    	   			}
    	   			else {
    	   				node2 = cityMap.get(inter2);
    	   			}
    	   			
    	   			node1.addAdjacent(node2, dist);
    	   		}
    	   		else {
    	   			break;
    	   		}
    	   	}
    		  
    	}
    		  
    	catch (Exception e) { slwPath = -1; }
       
    }
    
    private class Node {
    	int id;
    	double cost = Double.MAX_VALUE;
    	ArrayList<Path> paths = new ArrayList<>();
    	
    	Node(int id) {
    		this.id = id;
    	}
    	
    	void addAdjacent(Node node, double cost) {
    		paths.add(new Path(node, cost));
    	}
    }
    
    private class Path {
    	Node meetingPlace;
    	double cost;
    	
    	Path(Node meetingPlace, double cost) {
    		this.meetingPlace = meetingPlace;
    		this.cost = cost;
    	}
    }
    
          }
  
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

       

       
       
   






                    
                

                       


                       





               
               
       




       
   
   
   
 
       


