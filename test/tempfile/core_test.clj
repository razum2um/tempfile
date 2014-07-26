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

(def tempname (atom ""))
(with-tempfile [t (tempfile text)]
  (reset! tempname (.getAbsolutePath t)))
(expect #"No such file or directory" (:err (sh "cat" @tempname)))

;; tempdir

(with-tempfile [t (tempdir)]
  (let [exists (.exists t)
        fname (.getAbsolutePath t)
        content (sh "file" fname)]
    (expect true exists)
    (expect #": directory" (:out content)))
  ;; executed after `finally` deletion!
  (expect false (.exists t)))

(def tempdirname (atom ""))
(with-tempfile [t (tempdir)]
  (reset! tempdirname (.getAbsolutePath t)))
(expect #"No such file or directory" (:err (sh "ls" @tempdirname)))

