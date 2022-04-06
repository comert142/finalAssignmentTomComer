/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalassignmenttomcomer;

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
    

