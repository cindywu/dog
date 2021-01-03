(ns dog.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [config.core :refer [env]]
            [rum.core :refer [defc render-static-markup]])
  (:gen-class))

(defc template [headline component]
  [:div {:id "main-div"
         :class "main-page-div"}
   [:h1 headline]
   [:ul {:class "nav"}
     [:li [:a {:href "/"} "Home"]]
     [:li [:a {:href "/friends"} "Friends"]]]
   component])

(defc main-page [req]
  [:p (str req)]
  [:div {:id "cv-div"
         :class "cv-component-div"}
    [:p "'89 born on new years day"]
    [:p "'95 move from northgate to bellevue"]
    [:p "'95 kindergarten at somerset elementary"]
    [:p "'96 first grade at eton school"]
    [:p "'97 back at somerset for second grade"]
    [:p "'00 graduate elementary school"]])

(defc friends-page []
  [:p "'09 denny befriends cindy"])

(defroutes app
  (GET "/" [req] (render-static-markup (template "hi, i am cindy" (main-page req))))
  (GET "/friends" [] (render-static-markup (template "cindy has one friend" (friends-page)))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (run-jetty (wrap-defaults app site-defaults) {:port (:port env)}))