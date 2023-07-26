import java.util.*;
/**
 * Your implementation of various different graph algorithms.
 *
 * @author Junseob Lee
 * @version 1.0
 * @userid jlee3624
 * @GTID 903493189
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        // 시작 정점과 그래프가 null인지 확인하고, 그렇다면 IllegalArgumentException을 발생시킵니다.
        if (start == null || graph == null) {
            throw new IllegalArgumentException("입력이 올바르지 않습니다 - NULL");
        }
        // 시작 정점이 그래프에 존재하는지 확인하고, 그렇지 않다면 IllegalArgumentException을 발생시킵니다.
        if (!graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("이 그래프에 해당하는 정점이 존재하지 않습니다.");
        }
        // 방문된 정점들의 목록을 저장할 리스트를 생성합니다.
        List<Vertex<T>> list = new ArrayList<>();
        // 탐색을 위한 큐를 생성합니다.
        Queue<Vertex<T>> queue = new LinkedList<>();
        // 그래프의 인접 리스트를 가져옵니다.
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        // 시작 정점을 리스트에 추가하고 큐에도 추가합니다.
        list.add(start);
        queue.add(start);
        // 큐가 비어있지 않은 동안 반복합니다.
        while (!queue.isEmpty()) {
            // 큐에서 정점을 하나 가져옵니다.
            Vertex<T> vtx = queue.remove();
            // 해당 정점과 연결된 정점들의 리스트를 가져옵니다.
            List<VertexDistance<T>> vertexDistance = adjList.get(vtx);
            // 연결된 정점들을 순회합니다.
            for (VertexDistance<T> v : vertexDistance) {
                Vertex<T> w = v.getVertex();
                // 아직 방문되지 않은 정점이라면 큐에 추가하고 리스트에도 추가합니다.
                if (!list.contains(w)) {
                    queue.add(w);
                    list.add(w);
                }
            }
        }
        // 방문된 정점들의 목록을 반환합니다.
        return list;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */

    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        // 시작 정점과 그래프가 null인지 확인하고, 그렇다면 IllegalArgumentException을 발생시킵니다.
        if (start == null || graph == null) {
            throw new IllegalArgumentException("입력이 올바르지 않습니다 - NULL");
        }
        // 시작 정점이 그래프에 존재하는지 확인하고, 그렇지 않다면 IllegalArgumentException을 발생시킵니다.
        if (!graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("이 그래프에 해당하는 정점이 존재하지 않습니다.");
        }
        // 방문된 정점들의 목록을 저장할 리스트를 생성합니다.
        List<Vertex<T>> list = new ArrayList<>();
        // 정점 방문 여부를 나타내는 집합을 생성합니다.
        Set<Vertex<T>> visited = new HashSet<>();
        // 깊이 우선 탐색을 수행하는 재귀 메서드를 호출합니다.
        dfsRecursive(start, graph, list, visited);
        // 방문된 정점들의 목록을 반환합니다.
        return list;
    }
    
    /**
     * 깊이 우선 탐색(DFS)을 재귀적으로 수행하는 보조 메서드입니다.
     *
     * @param <T>       데이터의 일반적인 유형
     * @param current   현재 탐색 중인 정점
     * @param graph     탐색할 그래프
     * @param list      방문된 정점들의 목록
     * @param visited   정점 방문 여부를 나타내는 집합
     */
    private static <T> void dfsRecursive(Vertex<T> current, Graph<T> graph, List<Vertex<T>> list, Set<Vertex<T>> visited) {
        // 현재 정점을 방문 처리합니다.
        visited.add(current);
        // 방문된 정점들의 목록에 현재 정점을 추가합니다.
        list.add(current);
        // 현재 정점과 연결된 정점들의 리스트를 가져옵니다.
        List<VertexDistance<T>> vertexDistance = graph.getAdjList().get(current);
        // 연결된 정점들을 순회합니다.
        for (VertexDistance<T> v : vertexDistance) {
            Vertex<T> next = v.getVertex();
            // 아직 방문되지 않은 정점이라면 재귀적으로 탐색을 수행합니다.
            if (!visited.contains(next)) {
                dfsRecursive(next, graph, list, visited);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start, Graph<T> graph) {
        // 시작 정점과 그래프가 null인지 확인하고, 그렇다면 IllegalArgumentException을 발생시킵니다.
        if (start == null || graph == null) {
            throw new IllegalArgumentException("입력이 올바르지 않습니다 - NULL");
        }
        // 시작 정점이 그래프에 존재하는지 확인하고, 그렇지 않다면 IllegalArgumentException을 발생시킵니다.
        if (!graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("이 그래프에 해당하는 정점이 존재하지 않습니다.");
        }
        // 각 정점까지의 최단 거리를 저장할 Map을 생성합니다.
        Map<Vertex<T>, Integer> map = new HashMap<>();
        // 그래프의 인접 리스트를 가져옵니다.
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        // 우선순위 큐(PriorityQueue)를 생성합니다.
        PriorityQueue<VertexDistance<T>> pq = new PriorityQueue<>();
        // 모든 정점에 대해 최단 거리를 무한대(Integer.MAX_VALUE)로 초기화합니다.
        for (Vertex<T> v : adjList.keySet()) {
            map.put(v, Integer.MAX_VALUE);
        }
        // 출발점의 최단 거리를 0으로 설정하고, 우선순위 큐에 출발점과 해당 거리를 추가합니다.
        map.put(start, 0);
        pq.add(new VertexDistance<>(start, 0));
        // 우선순위 큐가 비어있지 않은 동안 반복합니다.
        while (!pq.isEmpty()) {
            // 우선순위 큐에서 가장 작은 거리의 정점을 가져옵니다.
            VertexDistance<T> vtx = pq.remove();
            // 해당 정점과 연결된 정점들의 리스트를 가져옵니다.
            List<VertexDistance<T>> vertexDistance = adjList.get(vtx.getVertex());
            // 연결된 정점들을 순회합니다.
            for (VertexDistance<T> v : vertexDistance) {
                Vertex<T> w = v.getVertex();
                int distance = v.getDistance();
                // 새로운 최단 거리를 찾았을 경우 Map을 업데이트하고, 우선순위 큐에 정점과 해당 거리를 추가합니다.
                if (map.get(w) > map.get(vtx.getVertex()) + distance) {
                    map.put(w, map.get(vtx.getVertex()) + distance);
                    pq.add(new VertexDistance<>(w, map.get(w)));
                }
            }
        }
        // 모든 정점까지의 최단 거리를 담은 Map을 반환합니다.
        return map;
    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the DisjointSet and DisjointSetNode classes that
     * have been provided to you for more information.
     *
     * You should NOT allow self-loops or parallel edges into the MST.
     *
     * By using the Disjoint Set provided, you can avoid adding self-loops and
     * parallel edges into the MST.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Set, and any class that implements the aforementioned
     * interfaces.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        // 그래프가 null인지 확인하고, 그렇다면 IllegalArgumentException을 발생시킵니다.
        if (graph == null) {
            throw new IllegalArgumentException("입력이 올바르지 않습니다.");
        }
        // 최소 비용 신장 트리(MST)를 저장할 Set을 생성합니다.
        Set<Edge<T>> mst = new HashSet<>();
        // Disjoint Set을 생성합니다.
        DisjointSet<Vertex<T>> ds = new DisjointSet<>();
        // 간선들을 가중치 기준으로 오름차순으로 정렬하기 위해 우선순위 큐(PriorityQueue)를 사용합니다.
        PriorityQueue<Edge<T>> pq = new PriorityQueue<>(graph.getEdges());
        // 그래프의 정점의 개수를 가져옵니다.
        int size = graph.getVertices().size();
        // 우선순위 큐가 비어있지 않은 동안 반복합니다.
        while (!pq.isEmpty()) {
            // 가중치가 가장 작은 간선을 가져옵니다.
            Edge<T> uv = pq.remove();
            Vertex<T> u = uv.getU();
            Vertex<T> v = uv.getV();
            // 정점 u와 v의 루트 노드를 가져옵니다.
            Vertex<T> uds = ds.find(u);
            Vertex<T> vds = ds.find(v);
            // 정점 u와 v가 서로 연결되어 있지 않거나, 둘 중 하나라도 null인 경우,
            // 즉, 이 간선을 추가하여 사이클이 생성되지 않을 경우 MST에 간선을 추가합니다.
            if (!uds.equals(vds) || (uds == null || vds == null)) {
                // 역방향 간선도 MST에 추가합니다.
                Edge<T> vu = new Edge<>(v, u, uv.getWeight());
                mst.add(uv);
                mst.add(vu);
                // 정점 u와 v를 연결시킵니다.
                ds.union(u, v);
            }
            if (mst.size() >= (size - 1) * 2) {
                break;
            }
        }
        // 만약 MST에 있는 간선의 수가 (정점의 개수 - 1) * 2와 같다면 유효한 MST가 존재하므로 MST를 반환합니다.
        if (mst.size() == (size - 1) * 2) {
            return mst;
        } else {
            // 그렇지 않다면 유효한 MST가 없으므로 null을 반환합니다.
            return null;
        }
    }
}
