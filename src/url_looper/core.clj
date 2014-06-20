(ns url-looper.core
   (  :require
      [digest]
      [clj-http.client :as http]
      [clojure.string    :refer [join]]
      [clojure.tools.cli :refer [parse-opts]]
      [clojure.tools.logging :as log]  )
   (:gen-class)  )

(def cli-options
   [  [  "-d"
         "--delta INT"
         "delta - seconds to wait between attempts"
         :parse-fn #(Integer/parseInt %)
         :validate [integer? "not an integer"]
         :default 60  ]
      [  "-f"
         "--filename FILENAME"
         "the filename to write to"
         :default "output.txt"  ]
      [  "-h" "--help" "help"  ]
      [  "-u"
         "--url URL"
         "the URL to fetch"
         :default "http://localhost:8080/"  ]  ]  )

(defn usage
   [exit-code options-summary & [error-msg]]
   (if error-msg (println error-msg "\n"))
   (println
      (join \newline
         [  "usage: write me"
            ""
            "Options:"
            options-summary  ]  )  )
   (System/exit exit-code)  )

(defn main-loop
   [  {  {:keys [delta filename help url]} :options
          :keys [arguments errors summary]  }  ]
   (if help   (usage 0 summary errors))
   (if errors (usage 1 summary errors))
   (log/info (format "fetching %s every %s seconds, keeping state across runs in %s" url delta filename))
   (loop
      [  oldmd5
            (digest/md5
               (try
                  (slurp filename)
                  (catch java.io.FileNotFoundException foo "")  )  )  ]
      (Thread/sleep (* 1000 delta))
      (let
         [  {status :status body :body}
               (http/get url {:insecure? true})
            newmd5 (digest/md5 body)  ]
         (recur
            (if
               (= status 200)
               (do
                  (if
                     (= newmd5 oldmd5)
                     (log/info (format "unchanged response returned by %s" url))
                     (do
                        (spit filename body)
                        (log/debug (format "md5 = %s" newmd5))
                        (log/info (format "different response returned by %s" url)  )  )  )
                  newmd5  )
               (do
                  (log/info
                     (format "invalid response from server (%s) - keeping last known good state" status)  )
                  oldmd5  )  )  )  )  )  )

(defn -main
   [& args]
   (main-loop (parse-opts args cli-options))  )
