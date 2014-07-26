# Tempfile

[![Build Status][BS img]][Build Status]

[![Clojars Project](http://clojars.org/tempfile/latest-version.svg)](http://clojars.org/tempfile)

Provides a natural way to handle temponary files and directories in Clojure.
For more info see [clojure-cookbook](https://github.com/clojure-cookbook/clojure-cookbook/blob/master/04_local-io/4-10_using-temp-files.asciidoc)

`with-tempfile` can be useful for packaging `.dll` or `.dylib` into jar, because [JNA](http://mvnrepository.com/artifact/com.sun.jna/jna)
can read them only from filesystem.

## Usage

    ;; (:require [tempfile.core :refer [tempdir tempfile with-tempfile])
    (def text "some string")

    ;; writes stringified args to tempfile and returns java.io.File
    (tempfile "some" text)

    ;; creates tempdir and returns java.io.File
    (tempdir)

    ;; uses a tempfile for some scope and
    ;; deletes it immediately afterwards
    (with-tempfile [t (tempfile text)]  ;; with a tempfile
      (pprint (.exists t)))             ;; "true"

    (with-tempfile [t (tempdir)]        ;; now with a temponary directory
      (pprint (.exists t)))             ;; "true"

## License

Copyright © 2014 Vlad Bokov

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

[BS img]: https://travis-ci.org/razum2um/tempfile.png
[Build Status]: https://travis-ci.org/razum2um/tempfile

