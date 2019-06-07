(ns clojure-spellchecker.core
  (:require [clojure.string :as str])
  (:import (org.apache.commons.lang3 StringUtils)))

;;;; Functions for spell checking a word against a dictionary.

(def words 
  (set (map str/trim (str/split-lines (slurp ".\\resources\\dictionary.txt")))))

(defn correct? 
  "Does our dictionary contain this word?"
  [word] 
  (contains? words word))

(defn getDistance 
  "Gets distance between two words, where distance is how different the words are."
  [word1 word2]
  (StringUtils/getLevenshteinDistance word1 word2))

(defn minDistance 
  "Find the word that is the most similar to the searched word."
  [word]
  (apply min-key (partial getDistance word) words))

(defn testWord
  "Test to see if a word is correct. If not, find closest match."
  [word]
  (if (correct? word)
      (println "Correct!")
      (println (str "Did you mean " (minDistance word) "?"))))
  

(defn -main [& args]
  (let [word (first args)]
    (testWord word)))
