(ns mem-pwds.core-test
  (:require [clojure.test :refer :all]
            [mem-pwds.core :refer :all])
  (:import [java.nio.file Files LinkOption Paths]
           [java.nio.file.attribute FileAttribute]))


(defn- create-test-file [test-dir-name test-file-name contents]
  (let [test-dir-path (Paths/get "." (into-array [test-dir-name]))
        test-file-path (Paths/get (.toString test-dir-path)
                                   (into-array [test-file-name]))]
    (when (not (Files/exists test-dir-path 
                           (into-array LinkOption [])))
    (Files/createDirectory test-dir-path
                           (into-array FileAttribute [])))
    (spit (.toString test-file-path) contents)
    test-file-path))



(deftest reading-test
  (testing "read word source"
    (let [test-dir-name ".tmp"
          test-file-name "source.txt"
          words ["alpha" "mike" "zulu"]
          contents (clojure.string/join \newline words)
          test-file-path (create-test-file test-dir-name
                                           test-file-name contents)]
      (let [actual-contents
            (mem-pwds.core/read-content (.toString test-file-path))]
        (is (= contents actual-contents))))))


(deftest next-test
  (testing "next-password"
    (let [words ["alpha" "foxtrot" "kilo" "papa" "uniform"]
          prev-words-index (atom -1)
          words-f (fn [ws]
                    (nth ws (swap! prev-words-index inc)))
          chars "0123456789!@#$%^&*()"
          prev-chars-index (atom -1)
          chars-f (fn [ws]
                    (nth ws (swap! prev-chars-index inc)))]
      (doseq [expected ["alpha0foxtrot" "kilo1papa" "uniform2alpha" "foxtrot3kilo" "papa4uniform"]]
        (is (= expected (next-password (cycle words) words-f (cycle chars) chars-f)))))))