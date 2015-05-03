(ns mem-pwds.core
  (require [clojure.string :as str])
  (:gen-class))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))


(defn read-content [file-name]
  (slurp file-name))


(defn next-password
  ([words] (next-password words rand-nth (str/join (map char (range 33 64))) rand-nth))
  ([words words-f chars chars-f]
   (str (words-f words) (chars-f chars) (words-f words))))
