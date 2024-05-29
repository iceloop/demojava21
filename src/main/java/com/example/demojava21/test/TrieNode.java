package com.example.demojava21.test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    boolean isEndOfWord = false;
}

class BiDirectionalTrie {
    private final TrieNode forwardRoot = new TrieNode();
    private final TrieNode backwardRoot = new TrieNode();

    public void insert(String word) {
        insert(forwardRoot, word);
        insert(backwardRoot, new StringBuilder(word).reverse().toString());
    }

    private void insert(TrieNode root, String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            node = node.children.computeIfAbsent(c, k -> new TrieNode());
        }
        node.isEndOfWord = true;
    }

    public Set<String> searchWithSubstring(String substring) {
        Set<String> forwardMatches = search(forwardRoot, substring);
        Set<String> backwardMatches = search(backwardRoot, new StringBuilder(substring).reverse().toString());

        Set<String> result = new HashSet<>();
        for (String word : forwardMatches) {
            if (backwardMatches.contains(new StringBuilder(word).reverse().toString())) {
                result.add(word);
            }
        }
        return result;
    }

    private Set<String> search(TrieNode root, String prefix) {
        Set<String> result = new HashSet<>();
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            node = node.children.get(c);
            if (node == null) {
                return result;
            }
        }
        collectWords(node, new StringBuilder(prefix), result);
        return result;
    }

    private void collectWords(TrieNode node, StringBuilder prefix, Set<String> result) {
        if (node.isEndOfWord) {
            result.add(prefix.toString());
        }
        for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
            prefix.append(entry.getKey());
            collectWords(entry.getValue(), prefix, result);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    public static void main(String[] args) {
        BiDirectionalTrie trie = new BiDirectionalTrie();
        trie.insert("apple");
        trie.insert("apricot");
        trie.insert("banana");
        trie.insert("blackberry");

        String substring = "a"; // 要查询的子串

        Set<String> result = trie.searchWithSubstring(substring);
        System.out.println(result.size());
        result.forEach(System.out::println);
    }
}
