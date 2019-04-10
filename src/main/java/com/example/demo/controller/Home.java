package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class Home {

    @RequestMapping("/")
    public List<List<Long>> hello(){
        /*
          Input:
          "Hola me llamo Ekaitz"
          "Hola Hola me Hola que tal"
          "Osea hellou que ase"

          Dictionary:
          [Hola me llamo Ekaitz que tal osea hellou ase]

          Expected output:
          [1, 1, 1, 1, 0, 0, 0, 0, 0]
          [3, 1, 0, 0, 1, 1, 0, 0, 0]
          [0, 0, 0, 0, 1, 1, 1, 1, 1]

          //BUILD DICTIONARY OF WORDS
          //divide each list into list of words
          //join all words
          //distinct

          //COUNT IN EACH SENTENCE
          //divide each list into list of words
          //count how many times is each word in the sentence
          //build result list comparing each word to the dictionary
         */

        List<String> sentences = new ArrayList<>();
        sentences.add("Hola me llamo Ekaitz");
        sentences.add("Hola Hola me Hola que tal");
        sentences.add("Osea hellou que ase");

        Stream<String[]> allSentencesDividedByWordsStream = sentences.stream()
                .map(sentence -> sentence.split(" "));

        Stream<String> dictionary = allSentencesDividedByWordsStream
                .flatMap(Arrays::stream)
                .distinct();

        return allSentencesDividedByWordsStream
                .map(sentence -> buildListOfFrequencies(sentence, dictionary))
                .collect(Collectors.toList());
    }

    private List<Long> buildListOfFrequencies(String[] words, Stream<String> dictionary) {
        Map<String, Long> countedWords = Arrays.stream(words)
                .collect(Collectors.groupingBy(word -> word, Collectors.counting()));

        return dictionary
                .map(entry -> countedWords.get(entry) == null ? new Long(0) : countedWords.get(entry))
                .collect(Collectors.toList());
    }
}
