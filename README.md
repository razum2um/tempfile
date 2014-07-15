# Tempfile

[![Build Status][BS img]][Build Status]

[![Clojars Project](http://clojars.org/tempfile/latest-version.svg)](http://clojars.org/tempfile)

Provides a natural way to handle temponary files in Clojure.
For more info see [clojure-cookbook](https://github.com/clojure-cookbook/clojure-cookbook/blob/master/04_local-io/4-10_using-temp-files.asciidoc)

`with-tempfile` can be useful for packaging `.dll` or `.dylib` into jar, because [JNA](http://mvnrepository.com/artifact/com.sun.jna/jna)
can read them only from filesystem.

## Usage

    ;; (:require [tempfile.core :refer :all])
    (def text "some string")

    ;; writes stringified args to tempfile and returns java.io.File
    (tempfile "some" text)

    ;; uses a tempfile for some content and delete it immediately
    (with-tempfile [t (tempfile text)]

      ;; remember to do what you need in 'let' block!
      (let [exists  (.exists t)
            fname   (.getAbsolutePath t)
            content (clojure.java.shell/sh "cat" fname)]
        (pprint exists)                                   ;; "true"
        (pprint (:out content)))                          ;; "some string"

      (pprint (.exists t)))                               ;; "false"

## License

Copyright © 2014 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

[BS img]: https://travis-ci.org/razum2um/tempfile.png
[Build Status]: https://travis-ci.org/razum2um/tempfile

