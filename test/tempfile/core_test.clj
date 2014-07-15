(ns tempfile.core-test
  (:use expectations)
  (:require [tempfile.core :refer :all]
            [clojure.java.shell :refer [sh]]))

(def text "abcdefg")

(expect
  text (slurp (tempfile text)))

(expect
  true (.exists (tempfile text)))

(with-tempfile [t (tempfile text)]
  (let [exists (.exists t)
        fname (.getAbsolutePath t)
        content (sh "cat" fname)]
    (expect true exists)
    (expect text (:out content)))
  ;; executed after `finally` deletion!
  (expect false (.exists t)))

