package com.example.shipmentservicelight.letcode;

import org.glassfish.grizzly.utils.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Graph {
    List<List<Integer>> listOfE = new ArrayList<>();
    List<Boolean> visited = new ArrayList<>();
    List<Integer> components = new ArrayList<>();
    public Graph(List<Pair<Integer, Integer>> e,boolean dual) {
        // Определение максимального индекса вершины для инициализации списка смежности
        int maxVertex = 0;
        for (Pair<Integer, Integer> p : e) {
            maxVertex = Math.max(maxVertex, Math.max(p.getFirst(), p.getSecond()));
        }

        // Инициализация пустых списков для каждой вершины
        for (int i = 0; i <= maxVertex; i++) {
            listOfE.add(new ArrayList<>());
            visited.add(false);
            components.add(0);
        }

        // Заполнение списка смежности
        for (Pair<Integer, Integer> p : e) {
            listOfE.get(p.getFirst()).add(p.getSecond());
            if(dual){
                listOfE.get(p.getSecond()).add(p.getFirst());
            }
        }
    }

    public void dfs(int i){
        visited.set(i,true);
        for(Integer n: listOfE.get(i)){
            if(visited.get(n) == false){
                dfs(n);
            }
        }
    }
    public void components(){
        int c = 1;
        for(int i = 1;i<visited.size();i++){
            if(visited.get(i) == false)
                dfs(i);
        }
    }

    public static void main(String[] args) {
        Graph g =new Graph(Arrays.asList(new Pair<>(4,3), new Pair<>(3,1), new Pair<>(1,2)), false);
        System.out.println(g);
//        g.dfs(4);
        System.out.println(g);
    }
}
