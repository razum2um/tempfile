(defproject tempfile "0.2.0"
  :description "Provides a natural way to handle temponary files in Clojure"
  :url "http://github.com/razum2um/tempfile"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [commons-io/commons-io "2.4"]]
  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.4"]
                                  [expectations "2.0.6"]
                                  [lein-expectations "0.0.5"]]}}
  :plugins [[lein-expectations "0.0.7"]
            [lein-autoexpect "1.0"]])
