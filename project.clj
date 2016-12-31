(defproject loop-of-url "2.0.0-dev"
   :dependencies
   [  [clj-http                  "3.4.1"]
      [digest                    "1.4.5"]
      [log4j/log4j               "1.2.17"
         :exclusions
         [  javax.mail/mail
            javax.jms/jms
            com.sun.jmdk/jmxtools
            com.sun.jmx/jmxri  ]  ]
      [org.clojure/clojure       "1.8.0"]
      [org.clojure/tools.cli     "0.3.5"]
      [org.clojure/tools.logging "0.3.1"]
      [org.slf4j/slf4j-log4j12   "1.7.22"]  ]
   :main ^:skip-aot loop-of-url.core
   :profiles {:uberjar {:aot :all}}  )
