//name : Ranul Akmeemana
//UoW  : w1761360
//reference : https://www.geeksforgeeks.org/ford-fulkerson-algorithm-for-maximum-flow-problem/


import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;


public class Algo {

    Scanner sc;
    ArrayList<ArrayList<Integer>> listOfArrays = new ArrayList<ArrayList<Integer>>();
    int nodes = 0;
    int matrixArray[][];


    public void getFile() {
        File file = new File("src/test.txt");       //select the file name

        try {
            sc = new Scanner(file);                              //we are reading the file with a Scanner so we can get value by value
        } catch (Exception e) {
            System.out.println("File not found");
        }
    }

    public void makeArray() {

        nodes = sc.nextInt();                                      //get the values in next line of the document as the node count
        System.out.println("Node Count : " + nodes + "\n");

        while (sc.hasNext()) {                                      //create while loop to get all the data from the document

            ArrayList<Integer> listOfData = new ArrayList<>();     //making new arraylists which contains starting node,target node and capacities
            int startingNode = sc.nextInt();                       //get the next integer in line
            int endingNode = sc.nextInt();
            int capacity = sc.nextInt();

            listOfData.add(startingNode);                          //adding starting node ending node and capacities to the sub array list
            listOfData.add(endingNode);
            listOfData.add(capacity);

            listOfArrays.add(listOfData);                          //adding list of nodes and capacities to the main array list

            System.out.println(startingNode + " " + endingNode + " " + capacity);


        }
    }
    public void matrix(){                                          //making a 2D data structure for the algorithm
        matrixArray = new int[nodes][nodes];
        for (ArrayList<Integer> element: listOfArrays){            //get one by one elements from the main arraylist
            matrixArray[element.get(0)][element.get(1)] = element.get(2);   //set the values that are getting from sub array lists to the 2D data structure
        }
    }


    boolean bfs(int rGraph[][], int s, int t, int parent[])
    {

        boolean visited[] = new boolean[nodes];                     //creating a boolean array to mark visited nodes
        for (int i = 0; i < nodes; ++i)
            visited[i] = false;

        LinkedList<Integer> queue = new LinkedList<Integer>();      //making a queue to mark
        queue.add(s);
        visited[s] = true;
        parent[s] = -1;

        while (queue.size() != 0) {                                 //BFS loop
            int u = queue.poll();

            for (int i = 0; i < nodes; i++) {
                if (visited[i] == false
                        && rGraph[u][i] > 0) {                      //if 
                    if (i == t) {
                        parent[i] = u;
                        return true;
                    }
                    queue.add(i);
                    parent[i] = u;
                    visited[i] = true;
                }
            }
        }
        return false;
    }

    int fordFulkerson(int graph[][], int s, int t)
    {
        double start = System.nanoTime();                       //getting the algorithms starting time
        int u, v;

        int rGraph[][] = new int[nodes][nodes];                 //creating a residual graph and fill the residual graph
                                                                //with given capacitiesin the original graph
                                                                //as residual capacities in residual graph
        for (u = 0; u < nodes; u++)
            for (v = 0; v < nodes; v++)
                rGraph[u][v] = graph[u][v];


        int parent[] = new int[nodes];                          //filled by bfs to store path

        int max_flow = 0;                                       //declaring max_flow to 0

        while (bfs(rGraph, s, t, parent)) {

            int path_flow = Integer.MAX_VALUE;
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                path_flow
                        = Math.min(path_flow, rGraph[u][v]);
            }

                                                                //update residual capacities and reverse edges along the path
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                rGraph[u][v] -= path_flow;
                rGraph[v][u] += path_flow;
            }

            max_flow += path_flow;      //add flow to overall flow
        }
        double end = System.nanoTime();             //getting the algorithms ending time
        double elapsed = (double) (end - start);       // getting the elapsed time
        System.out.println("\nTotal time : " + elapsed / 1000000 + " ms");        //converting nanosecond to millisecond


        return max_flow;        // Return the max flow
    }


    public static void main(String[] args) throws java.lang.Exception {
        Algo a1 = new Algo();

        a1.getFile();
        a1.makeArray();
        a1.matrix();
        System.out.println("\n  graph view\n");

        for(int i = 0; i< a1.nodes; i++)                //create rows and columns in the graph
        {
            for(int x = 0; x< a1.nodes; x++)
            {
                System.out.print(" "+a1.matrixArray[i][x]);
            }
            System.out.println();
        }

        System.out.println("\nThe Max Flow is : " + a1.fordFulkerson(a1.matrixArray, 0, a1.nodes -1 ));

    }
}