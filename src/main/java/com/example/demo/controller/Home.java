package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

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

        List<List<String>> allSentencesDividedByWords = sentences.stream()
                .map(sentence -> sentence.split(" "))
                .map(Arrays::asList)
                .collect(Collectors.toList());

        List<String> dictionary = allSentencesDividedByWords
                .stream()
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());

        return allSentencesDividedByWords.stream()
                .map(sentence -> buildListOfFrequencies(sentence, dictionary))
                .collect(Collectors.toList());
    }

    private List<Long> buildListOfFrequencies(List<String> words, List<String> dictionary) {
        Map<String, Long> countedWords = words.stream()
                .collect(Collectors.groupingBy(word -> word, Collectors.counting()));

        return dictionary.stream()
                .map(entry -> countedWords.get(entry) == null ? new Long(0) : countedWords.get(entry))
                .collect(Collectors.toList());
    }
}