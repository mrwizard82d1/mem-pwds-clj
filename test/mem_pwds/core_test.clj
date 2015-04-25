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



  
  
