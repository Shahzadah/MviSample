package com.example.compose


import com.example.compose.utils.DirectedGraph
import com.example.compose.utils.UnDirectedGraph
import org.junit.Test

class GraphBasicTest {

    @Test
    fun test() {
        val graph = DirectedGraph(5).apply {
            addEdge(0, 1)
            addEdge(0, 2)
            addEdge(0, 3)
            addEdge(1, 3)
            addEdge(1, 4)
            addEdge(2, 3)
            addEdge(2, 4)
        }
//        println("Total path - " + graph.isPathAvailable(0, 4))
//        graph.waterJugProblem(jug1Limit = 3, jug2Limit = 4, target = 2).let {
//            println("Waterjug possible - $it")
//        }
    }

    @Test
    fun undirected() {
        val graph = UnDirectedGraph(10).apply {
            addEdge(0, 1)
            addEdge(0, 3)
            addEdge(0, 8)
            addEdge(2, 3)
            addEdge(3, 6)
            addEdge(3, 7)
            addEdge(3, 5)
            addEdge(4, 5)
            addEdge(5, 9)
        }
        val marked = arrayOf(1, 2, 4)
        val k = 3
        println("Nodes less than $k  - ${graph.countInKDistance(k, marked)}")
    }
}