package com.example.compose.utils

import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

class UnDirectedGraph(totalVertex: Int) {

    private val list = ArrayList<ArrayList<Int>>(totalVertex)

    init {
        for (i in 0 until totalVertex) {
            list.add(ArrayList())
        }
    }

    fun addEdge(u: Int, v: Int) {
        list[u].add(v)
        list[v].add(u)
    }

    fun print() {
        list.forEachIndexed { index, arrayList ->
            print("$index -> ")
            arrayList.forEach {
                print("$it ")
            }
            println()
        }
    }

    fun dfs(startVertex: Int = 0) {
        val visited = Array(list.size) { false }
        dfsUtil(startVertex, visited)
    }

    private fun dfsUtil(vertex: Int, visited: Array<Boolean>) {
        visited[vertex] = true
        print("$vertex ")
        list[vertex].forEach {
            if (!visited[it]) {
                visited[it] = true
                dfsUtil(it, visited)
            }
        }
    }

    fun kCore(k: Int) {
        val visited = Array(list.size) { false }
        val degrees = Array(list.size) { 0 }

        var minDegree = list.size + 1
        var startVertex = 0
        list.forEachIndexed { index, arrayList ->
            degrees[index] = arrayList.size
            if (arrayList.size < minDegree) {
                minDegree = arrayList.size
                startVertex = index
            }
        }
        dfsUtil(startVertex, visited, degrees, k)

        list.forEachIndexed { index, _ ->
            if (!visited[index]) {
                dfsUtil(index, visited, degrees, k)
            }
        }

        printKCores(degrees, k)
    }

    private fun dfsUtil(
        vertex: Int,
        visited: Array<Boolean>,
        degrees: Array<Int>,
        k: Int
    ): Boolean {
        visited[vertex] = true
        list[vertex].forEach {
            if (degrees[it] < k) {
                degrees[it]--
            }
            if (!visited[it]) {
                dfsUtil(it, visited, degrees, k)
            }
        }
        return degrees[vertex] < k
    }

    private fun printKCores(degrees: Array<Int>, k: Int) {
        degrees.forEachIndexed { index, count ->
            if (count >= k) {
                print("[$index] -> ")
                list[index].forEach {
                    if (degrees[it] > k) {
                        print("$it ->")
                    }
                }
                println()
            }
        }
    }

    fun countNodesAtLevel(level: Int, startVertex: Int = 0): Int {
        var count = 0
        val visited = Array(list.size) { false }
        val levels = Array(list.size) { 0 }

        val queue: Queue<Int> = LinkedList()
        queue.add(startVertex)
        levels[startVertex] = 0
        visited[startVertex] = true

        while (!queue.isEmpty()) {
            val temp = queue.poll()!!
            list[temp].forEach {
                if (!visited[it]) {
                    visited[it] = true
                    queue.add(it)
                    levels[it] = levels[temp] + 1
                }
            }
        }

        levels.forEach {
            if (it == level) {
                count++
            }
        }

        return count
    }

    fun countTrees(): Int {
        var count = 0
        val visited = Array(list.size) { false }
        list.forEachIndexed { index, _ ->
            if (visited[index].not()) {
                count++
                dfsUtil(index, visited)
            }
        }

        return count
    }

    fun findLevelOfEachNode(startVertex: Int = 0): Array<Int> {
        val levels = Array(list.size) { 0 }
        val visited = Array(list.size) { false }

        val queue: Queue<Int> = LinkedList()
        queue.add(startVertex)
        levels[startVertex] = 0
        visited[startVertex] = true
        while (queue.isNotEmpty()) {
            val temp = queue.poll()!!
            list[temp].forEach {
                if (visited[it].not()) {
                    levels[it] = levels[temp] + 1
                    queue.add(it)
                    visited[it] = true
                }
            }
        }
        return levels
    }

    private var minimum by Delegates.notNull<Int>()

    fun minEdgeBetweenVertices(vertex1: Int, vertex2: Int): Int {
        minimum = list.size + 1
        val visited = Array(list.size) { false }
        val path = ArrayList<Int>()
        path.add(vertex1)
        minEdgeBetweenVerticesUtil(vertex1, vertex2, path, visited)
        return minimum
    }

    private fun minEdgeBetweenVerticesUtil(
        vertex1: Int,
        vertex2: Int,
        path: ArrayList<Int>,
        visited: Array<Boolean>
    ) {
        visited[vertex1] = true
        if (vertex1 == vertex2) {
            if (path.size < minimum) {
                minimum = path.size
            }
        }
        list[vertex1].forEach {
            if (visited[it].not()) {
                path.add(it)
                minEdgeBetweenVerticesUtil(it, vertex2, path, visited)
                path.remove(it)
                visited[it] = false
            }
        }
    }

    fun minEdgeBetweenVerticesByBfs(vertex1: Int, vertex2: Int): Int {
        val distance = Array(list.size) { 0 }
        val visited = Array(list.size) { false }
        val queue: Queue<Int> = LinkedList()
        queue.add(vertex1)
        visited[vertex1] = true
        while (queue.isNotEmpty()) {
            val temp = queue.poll()!!
            list[temp].forEach {
                if (visited[it].not()) {
                    visited[it] = true
                    distance[it] = distance[temp] + 1
                    queue.add(it)
                }
            }
        }
        return distance[vertex2]
    }

    fun countInKDistance(k: Int, marked: Array<Int>) {
        val result = Array(list.size) { 0 }
        for (i in 0 until list.size) {
            marked.forEach {
                val distance = getDistance(i, it)
                if (distance <= k) {
                    result[i] += 1
                }
            }
            if (result[i] >= k) {
                print("$i ")
            }
        }
    }

    private fun getDistance(start: Int, dest: Int): Int {
        if (start == dest) {
            return 0
        }
        val queue: Queue<Int> = LinkedList()
        val visited = Array(list.size) { false }
        val distance = Array(list.size) { 0 }
        queue.add(start)
        visited[start] = true
        while (queue.isNotEmpty()) {
            val temp = queue.poll()!!
            if (temp == dest) {
                break
            }
            list[temp].forEach {
                if (visited[it].not()) {
                    visited[it] = true
                    distance[it] = distance[temp] + 1
                    queue.add(it)
                }
            }
        }
        return distance[dest]
    }
}