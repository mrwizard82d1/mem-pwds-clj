(ns mem-pwds.core
  (:gen-class))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))


(defn read-content [file-name]
  (slurp file-name))


(defn next-word
  "Returns a stream consisting of the next word from word-source."
  [words]
  (repeatedly (fn [] (rand-nth words))))
