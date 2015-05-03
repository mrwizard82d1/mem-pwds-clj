(ns mem-pwds.core
  (require [clojure.string :as str]
           [clojure.tools.cli :as cli])
  (:gen-class))


(def cli-options
  [["-h" "--help"]
   ["-c" "--count COUNT" "Passwords to generate."
    :default 5
    :parse-fn #(Integer/parseInt %)]])


(defn usage [summary]
  (->> ["Generates random but memorable passwords."
        ""
        "Usage: program-name [options] action"
        ""
        "Options:"
        summary
        ""]
       (str/join \newline)))


(defn error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (str/join \newline errors)))


(defn- exit [status msg]
  (println msg)
  (System/exit status))


(defn read-content
  ([file-name]
   (slurp file-name)))


(def content (atom nil))


(defn next-password
  ([] (if (not @content)
        (swap! content (fn [_old]
                         (str/split (read-content (.getFile (clojure.java.io/resource "dict.txt"))) #"\s+"))))
   (next-password @content))
  ([words] (next-password words rand-nth (str/join (map char (range 33 64))) rand-nth))
  ([words words-f chars chars-f]
   (str (words-f words) (chars-f chars) (words-f words))))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [{:keys [options _arguments errors summary]}
        (cli/parse-opts args cli-options)]
    (cond (:help options) (exit 0 summary)
          errors (exit 1 (error-msg errors)))
    (dotimes [_5 (:count options)]
      (println (next-password)))))
