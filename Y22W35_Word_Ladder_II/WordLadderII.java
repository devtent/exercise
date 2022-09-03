package Y22W35_Word_Ladder_II;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ph87
 * @since 2022/9/3
 */
public class WordLadderII {
    @Test
    public void testFindLadders() {
        // beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog",
        // "lot","log","cog"]
        //
        // [["hit","hot","dot","dog","cog"],["hit","hot","lot","log","cog"]]
        assert 2 == findLadders(
                "hit", "cog",
                Arrays.asList("hot", "dot", "dog", "lot", "log", "cog")
        ).size();

    }

    public List<List<String>> findLadders(String beginWord, String endWord,
                                          List<String> wordList) {
        // 返回所有最短的变换路径
        // 关键词「最短」，一般使用广度优先遍历
        // 关键词「变换」，转换成图中两个连通的节点
        // 对于任意一个单词， 找到所有未遍历过的相邻节点，检查是否包含目标词
        //  * 如果包含，收集对应的路径，注意需要收集当前层所有可能的节点
        //  * 如果不包含，准备下一层遍历
        // 关键词「路径」，使用类似链表的结构表达
        if (!wordList.contains(endWord) || beginWord.length() != endWord.length()){
            return new ArrayList<>();
        }
        // {word: {neighbour1, ...}
        Map<String, Set<String>> graph = buildGraph(beginWord, wordList);
        Queue<WordNode> queue = new LinkedList<>();
        queue.add(buildNode(beginWord, null));
        List<WordNode> endNodes = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        while (!queue.isEmpty() && endNodes.isEmpty()) {
            Queue<WordNode> nextLevel = new LinkedList<>();
            for (WordNode node : queue) {
                if (endWord.equals(node.word)) {
                    endNodes.add(node);
                    continue;
                }
                Set<String> neighbours = graph.getOrDefault(
                        node.word, new HashSet<>()
                );
                nextLevel.addAll(neighbours.stream()
                                         .filter(n -> !visited.contains(n))
                                         .map(n -> buildNode(n, node))
                                         .collect(Collectors.toList()));
            }
            visited.addAll(
                    queue.stream().map(n -> n.word).collect(Collectors.toList())
            );
            queue = nextLevel;
        }
        return serializeWordLink(endNodes);
    }

    // {word: {word1, word2, ...}}, word's neighbours
    Map<String, Set<String>> buildGraph(
            String beginWord, List<String> wordList
    ) {
        Map<String, Set<String>> graph = new HashMap<>();
        Set<String> wordSet = new HashSet<>(wordList);
        wordSet.add(beginWord);
        for (String word1 : wordSet) {
            Set<String> neighbours = new HashSet<>();
            graph.put(word1, neighbours);
            for (String word2 : wordList) {
                if (isNeighbour(word1, word2)) {
                    neighbours.add(word2);
                }
            }
        }
        return graph;
    }

    boolean isNeighbour(String word1, String word2) {
        if (word1.length() != word2.length()) {
            return false;
        }
        int diff = 0;
        for (int i = 0; i < word1.length(); i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                diff++;
            }
            if (diff == 2) {
                return false;
            }
        }
        return diff == 1;

    }


    WordNode buildNode(String word, WordNode parent) {
        WordNode node = new WordNode();
        node.word = word;
        node.parent = parent;
        return node;
    }


    class WordNode {
        WordNode parent;
        String word;
    }

    List<List<String>> serializeWordLink(List<WordNode> endNodes) {
        List<List<String>> res = new LinkedList<>();
        for (WordNode endNode : endNodes) {
            List<String> path = new LinkedList<>();
            res.add(path);
            while (null != endNode) {
                path.add(0, endNode.word);
                endNode = endNode.parent;
            }
        }
        return res;
    }

}
