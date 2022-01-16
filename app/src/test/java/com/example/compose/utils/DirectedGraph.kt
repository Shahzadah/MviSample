package com.example.compose.utils

import java.util.*
import kotlin.collections.ArrayList

class DirectedGraph(private val totalVertex: Int) {

    private val list = ArrayList<ArrayList<Int>>(totalVertex)

    init {
        for (i in 0 until totalVertex) {
            list.add(ArrayList())
        }
    }

    fun addEdge(firstVertex: Int, secondVertex: Int) {
        list[firstVertex].add(secondVertex)
    }

    fun print() {
        for (i in 0 until list.size) {
            print("$i -> ")
            list[i].forEach { temp ->
                print("$temp ")
            }
            println()
        }
    }

    fun bfs(startVertex: Int = 0) {
        val visited = Array(totalVertex) { false }
        val queue: Queue<Int> = LinkedList()
        queue.add(startVertex)
        visited[startVertex] = true
        while (queue.isEmpty().not()) {
            val temp = queue.poll()!!
            print("$temp ")
            list[temp].forEach {
                if (!visited[it]) {
                    visited[it] = true
                    queue.add(it)
                }
            }
        }
    }

    fun dfs(startVertex: Int = 0) {
        val visited = Array(totalVertex) { false }
        dfsUtil(vertex = startVertex, visited = visited)
    }

    private fun dfsUtil(vertex: Int, visited: Array<Boolean>) {
        visited[vertex] = true
        print("$vertex ")
        list[vertex].forEach {
            if (!visited[it]) {
                dfsUtil(it, visited)
            }
        }
    }

    fun motherVertex(): Int {
        var vertex = -1 //Find the vertex which took max time to traverse the entire graph O(V+E)
        val visited = Array(totalVertex) { false }
        for (i in 0 until list.size) {
            if (!visited[i]) {
                dfsUtil(i, visited)
                vertex = i
            }
        }

        //reset visited[] - O(V)
        for (i in 0 until list.size) {
            visited[i] = false
        }
        //traverse graph again  - O(V+E)
        dfsUtil(vertex, visited)

        //Check all vertices are traversed and return -1 if any one isn't O(V)
        for (i in 0 until list.size) {
            if (!visited[i]) {
                return -1
            }
        }
        return vertex //final complexity - O(V)
    }

    fun motherVertex2(): Int { // O(V(V+E))
        var vertex = -1
        for (i in 0 until list.size) {
            if (isMotherVertex(i)) {
                vertex = i
            }
        }
        return vertex
    }

    private fun isMotherVertex(vertex: Int): Boolean { // O(V+E)
        for (i in 0 until list.size) {
            if (!isReachable(vertex, i)) {
                return false
            }
        }
        return true
    }

    private fun isReachable(start: Int, dest: Int): Boolean {
        if (start == dest) {
            return true
        }
        val visited = Array(totalVertex) { false }
        val queue: Queue<Int> = LinkedList()
        queue.add(start)
        visited[start] = true
        while (!queue.isEmpty()) {
            val temp = queue.poll()!!
            if (temp == dest) {
                return true
            }
            list[temp].forEach {
                if (!visited[it]) {
                    visited[it] = true
                    queue.add(it)
                }
            }
        }
        return false
    }

    fun printScc() {
        val stack = Stack<Int>()
        val visited = Array(totalVertex) { false }
        for (i in 0 until totalVertex) {
            if (!visited[i]) {
                fillStackByDfs(i, stack, visited)
            }
        }

        val graph = getTranspose()
        for (i in 0 until totalVertex) {
            visited[i] = false
        }
        while (stack.isNotEmpty()) {
            val temp = stack.pop()
            printSscByDfs(temp, visited, graph)
            println()
        }
    }

    fun printTranspose() {
        val transposeGraph = ArrayList<ArrayList<Int>>(list.size)
        for (i in 0 until list.size) {
            transposeGraph.add(ArrayList())
        }
        list.forEachIndexed { index, items ->
            items.forEach {
                transposeGraph[it].add(index)
            }
        }

        transposeGraph.forEachIndexed { index, arrayList ->
            print("$index -> ")
            arrayList.forEach {
                print("$it ")
            }
            println()
        }
    }

    private fun printSscByDfs(vertex: Int, visited: Array<Boolean>, graph: List<List<Int>>) {
        visited[vertex] = true
        print("$vertex ")
        graph[vertex].forEach {
            if (!visited[it]) {
                printSscByDfs(it, visited, graph)
            }
        }
    }

    private fun fillStackByDfs(vertex: Int, stack: Stack<Int>, visited: Array<Boolean>) {
        visited[vertex] = true
        list[vertex].forEach {
            if (!visited[it]) {
                fillStackByDfs(it, stack, visited)
            }
        }
        stack.push(vertex)
    }

    private fun getTranspose(): List<ArrayList<Int>> {
        val temp = ArrayList<ArrayList<Int>>(totalVertex)
        for (i in 0 until totalVertex) {
            temp.add(ArrayList())
        }

        for (i in 0 until list.size) {
            val u = list[i]
            u.forEach { v ->
                temp[v].add(i)
            }
        }
        return temp
    }

    fun waterJugProblem(jug1Limit: Int, jug2Limit: Int, target: Int): Boolean {
        if (target > jug1Limit + jug2Limit) {
            return false
        }
        val operations = arrayOf(jug1Limit, -jug1Limit, jug2Limit, -jug2Limit)

        val queue: Queue<Int> = LinkedList()
        val visited = HashSet<Int>()
        queue.add(0)
        visited.add(0)
        while (queue.isNotEmpty()) {
            val temp = queue.poll()!!
            operations.forEach {
                val total = temp + it
                if (total == target) {
                    return true
                }
                if (total >= 0 && total <= jug1Limit + jug2Limit && !visited.contains(total)) {
                    queue.add(total)
                    visited.add(total)
                }
            }
        }
        return false
    }

    fun printAllPaths(start: Int, dest: Int) {
        val visited = Array(list.size) { false }
        val path = ArrayList<Int>()
        path.add(start)
        printAllPathsUtil(start, dest, visited, path)
    }

    private fun printAllPathsUtil(
        start: Int,
        dest: Int,
        visited: Array<Boolean>,
        path: ArrayList<Int>
    ) {
        if (start == dest) {
            println(path)
        }
        visited[start] = true
        list[start].forEach {
            if (!visited[it]) {
                path.add(it)
                printAllPathsUtil(it, dest, visited, path)
                path.remove(it)
                visited[it] = false
            }
        }
    }

    fun printPathsByBfs(start: Int, dest: Int) {
        val queue: Queue<ArrayList<Int>> = LinkedList()
        val path = ArrayList<Int>()
        path.add(start)
        queue.add(path)
        while (queue.isNotEmpty()) {
            val temp = queue.poll()!!
            if (temp.last() == dest) {
                println(temp)
            }
            list[temp.last()].forEach {
                if (temp.contains(it).not()) {
                    val newList = ArrayList<Int>(temp)
                    newList.add(it)
                    queue.add(newList)
                }
            }
        }
    }

    fun getTotalPathsCountByBfs(start: Int, end: Int): Int {
        var count = 0
        val path = ArrayList<Int>()
        val queue: Queue<ArrayList<Int>> = LinkedList()
        path.add(start)
        queue.add(path)
        while (queue.isNotEmpty()) {
            val temp = queue.poll()!!
            if (temp.last() == end) {
                count++
            }
            list[temp.last()].forEach {
                if (temp.contains(it).not()) {
                    val newPath = ArrayList<Int>(temp)
                    newPath.add(it)
                    queue.add(newPath)
                }
            }
        }
        return count
    }

    fun getTotalPathsCount(start: Int, end: Int): Int {
        val count = 0
        val path = ArrayList<Int>()
        val visited = Array(list.size) { false }
        path.add(start)
        return getTotalPathsCountUtil(start, end, visited, count)
    }

    private fun getTotalPathsCountUtil(
        start: Int,
        end: Int,
        visited: Array<Boolean>,
        count: Int
    ): Int {
        var pathCount = count
        visited[start] = true
        if (start == end) {
            pathCount++
        }
        list[start].forEach {
            if (visited[it].not()) {
                pathCount = getTotalPathsCountUtil(it, end, visited, pathCount)
            }
        }
        visited[start] = false
        return pathCount
    }


}



















