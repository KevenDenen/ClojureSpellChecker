(ns clojure-spellchecker.core
  (:require [clojure.string :as str])
  (:import (org.apache.commons.lang3 StringUtils)))

(def words 
  (set (map str/trim (str/split-lines (slurp ".\\resources\\dictionary.txt")))))

(defn correct? [word] 
  (contains? words word))

(defn getDistance [word1 word2]
  (StringUtils/getLevenshteinDistance word1 word2))

(defn minDistance [word]
  (apply min-key (partial getDistance word) words))

(defn -main [& args]
  (let [word (first args)]
    (if (correct? word)
      (println "Correct!")
      (println (str "Did you mean " (minDistance word) "?"))
    )
  )
)