(ns task01.core
  (:require [pl.danieljanus.tagsoup :refer :all])
  (:gen-class))

(defn is-elem? [item]
  (and (= (tag item) :h3) (= (:class (attributes item)) "r")))

(defn extract-data [item]
  (:href (attributes (first (children item))))
)

(defn scan-tree [result tree]
  (cond 
    (not (vector? tree)) 
      result
    (is-elem? tree) (conj result (extract-data tree))
    :else 
      (reduce scan-tree result (children tree))))


(defn get-links []
" 1) Find all elements containing {:class \"r\"}.

Example:
[:h3 {:class \"r\"} [:a {:shape \"rect\", :class \"l\",
                         :href \"https://github.com/clojure/clojure\",
                         :onmousedown \"return rwt(this,'','','','4','AFQjCNFlSngH8Q4cB8TMqb710dD6ZkDSJg','','0CFYQFjAD','','',event)\"}
                     [:em {} \"clojure\"] \"/\" [:em {} \"clojure\"] \" · GitHub\"]]

   2) Extract href from the element :a.

The link from the example above is 'https://github.com/clojure/clojure'.

  3) Return vector of all 10 links.

Example: ['https://github.com/clojure/clojure', 'http://clojure.com/', . . .]
"
  (let [data (parse "clojure_google.html")]
    (scan-tree [] data)))

(defn -main []
  (println (str "Found " (count (get-links)) " links!")))


