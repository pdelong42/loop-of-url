(defproject loop-of-url "2.0.0-dev"
   :dependencies
   [  [clj-http                  "2.0.0"]
      [digest                    "1.4.4"]
      [log4j/log4j               "1.2.17"
         :exclusions
         [  javax.mail/mail
            javax.jms/jms
            com.sun.jmdk/jmxtools
            com.sun.jmx/jmxri  ]  ]
      [org.clojure/clojure       "1.6.0"]
      [org.clojure/tools.cli     "0.3.1"]
      [org.clojure/tools.logging "0.2.6"]
      [org.slf4j/slf4j-log4j12   "1.7.1"]  ]
   :main ^:skip-aot loop-of-url.core
   :profiles {:uberjar {:aot :all}}  )
