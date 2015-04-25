(ns mem-pwds.core-test
  (:require [clojure.test :refer :all]
            [mem-pwds.core :refer :all])
  (:import [java.nio.file Files LinkOption Paths]
           [java.nio.file.attribute FileAttribute]))

(deftest reading-test
  (testing "read word source"
    (let [test-dir-name ".tmp"
          test-dir-path (Paths/get "." (into-array [test-dir-name]))
          test-file-name "source.txt"
          test-file-path (Paths/get (.toString test-dir-path)
                                   (into-array [test-file-name]))
          words ["alpha" "mike" "zulu"]
          contents (clojure.string/join \newline words)
          ]
      (when (not (Files/exists test-dir-path 
                               (into-array LinkOption [])))
        (Files/createDirectory test-dir-path
                             (into-array FileAttribute [])))
      (spit (.toString test-file-path) contents)
      (let [actual-contents
            (mem-pwds.core/read-content (.toString test-file-path))]
        (is (= contents actual-contents))))))
