(ns loop-of-url.core-test
   (:require
      [clojure.test     :refer :all]
      [clj-http.fake    :refer :all]
      [loop-of-url.core :refer :all]  )  )
;      [stub-http.core   :refer :all]

(deftest basic-http-get
   (testing "basic exercise of the http-get function"
      (is
         (= {  :status 200 :body "Testing 1...2...3"  }
            (with-global-fake-routes
               {  "http://example.com/foo"
                  (fn
                     [request]
                     {  :status 200
                        :body "Testing 1...2...3"  }  )  }
               (let
                  [  [status body remaining message]
                     (http-get "http://example.com/foo" 1)  ]
                  {  :status status :body body  }  )  )  )  )  )  )

;(deftest http-get-of-nxdomain
;   (testing "validate whether http-get handles java.net.UnknownHostException"
;      (is
;         (= {  :status "DNS resolution failure" :body ""  }
;            (with-global-fake-routes
;               {  "http://example.com/foo"
;                  (fn
;                     [request]
;                     {  :status 200
;                        :body "Testing 1...2...3"  }  )  }
;               (let
;                  [  [status body remaining message]
;                     (http-get "http://foo.paul.delong.name/" 1)  ]
;                  {  :status status :body body  }  )  )  )  )  )  )

;(deftest http-get-of-nxdomain
;   (testing "validate whether http-get handles java.net.UnknownHostException"
;      (is
;         (= {  :status "DNS resolution failure" :body ""  }
;            (let
;               [  [status body remaining message]
;                  (http-get "http://foo.paul.delong.name/" 1)  ]
;               {  :status status :body body  }  )  )  )  )  )
