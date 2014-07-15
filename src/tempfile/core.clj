(ns tempfile.core
  (:require [clojure.java.io :as io])
  (:import (java.io File)))

(defn- get-tempfile
  []
  (let [fd (File/createTempFile (str (rand-int 100000)) nil)]
    (.deleteOnExit fd)
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
                                  (. ~(bindings 0) delete))))
    :else (throw (IllegalArgumentException.
                   "with-tempfile only allows Symbols in bindings"))))

