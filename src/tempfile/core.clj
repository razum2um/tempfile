(ns tempfile.core
  (:require [clojure.java.io :as io])
  (:import [java.io File]
           [java.nio.file Files]
           [java.nio.file.attribute FileAttribute]
           [org.apache.commons.io FileUtils]))

(defn- get-tempfile
  []
  (let [fd (File/createTempFile (str (System/nanoTime)) nil)]
    (.deleteOnExit fd)
    fd))

(defn tempdir
  "Creates tempdir and returns java.io.File"
  []
  (let [unix-path (Files/createTempDirectory (str (System/nanoTime)) (into-array FileAttribute []))
        fd (.toFile unix-path)]
    (FileUtils/forceDeleteOnExit fd)
    fd))

(defn tempfile
  "Writes stringified args to tempfile and returns java.io.File"
  [& args]
  (let [fd (get-tempfile)
        writer (io/writer fd)]
    (.write writer (apply str args))
    (.close writer)
    fd))

(defmacro with-tempfile
  "Uses a tempfile for some content and delete it immediately"
  [bindings & body]
  (cond
    (= (count bindings) 0) `(do ~@body)
    (symbol? (bindings 0)) `(let ~(subvec bindings 0 2)
                              (try
                                (with-tempfile ~(subvec bindings 2) ~@body)
                                (finally
                                  (FileUtils/forceDelete ~(bindings 0)))))
    :else (throw (IllegalArgumentException.
                   "with-tempfile only allows Symbols in bindings"))))

