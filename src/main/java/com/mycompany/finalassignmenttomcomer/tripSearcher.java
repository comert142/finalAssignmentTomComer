/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalassignmenttomcomer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class tripSearcher{//this should look for a trip and print out my values in a correct format
	public  ArrayList<String[]> search(String searchTerm) {
		ArrayList<String[]> detailsOfTrip = new ArrayList<>(0);
		if(tripSearcher.timeChecker(searchTerm)){
                    return detailsOfTrip;
                } else {
                }
		try {
        File map = new File("C:\\Users\\tcome\\OneDrive\\Desktop\\busInfo\\stop_times.txt");
	    Scanner myReader = new Scanner(map);
    	myReader.nextLine();
        while (myReader.hasNextLine()) {
          String[] currentLine = myReader.nextLine().split(",");
          if(currentLine.length>2){
          if(currentLine[1].replace(" ","0").equals(searchTerm)){
        	  String[] tempArray = {currentLine[0],currentLine[1].replace(" ","0"),currentLine[2].replace(" ","0"),currentLine[3]
                      ,currentLine[4],currentLine[5],currentLine[6],currentLine[7]};
              detailsOfTrip.add(tempArray);
          }}
        }
        myReader.close();  } catch (FileNotFoundException ex){
            return detailsOfTrip;
        }
        return detailsOfTrip;
        }



    public static Boolean timeChecker(String time){
        String[] timeSplit = time.split(":");//format checker to insure that the time is correct and not nonsense
        if(timeSplit.length != 3){
            return false;//returning false if it is 
        }
        int hour = Integer.parseInt(timeSplit[0]);
        if(hour>24 || hour<0){
            return false;//checking if the time is over 24hours it is incorrect (24 hours in a day)
        }
        int minute = Integer.parseInt(timeSplit[1]);
        if(minute < 0 || minute > 60){
            return false;//Checking for minutes
        }
        int second = Integer.parseInt(timeSplit[2]);
        if(second < 0 || second >60){
            return false;// and now seconds
        }
        return true;//if all of these criteria are satisfied we can go on ahead
        

    }
    
}
