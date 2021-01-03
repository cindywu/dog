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
  [:p (str req)])

(defc friends-page []
  [:p "This is the friends page, it is empty, yet"])

(defroutes app
  (GET "/" [req] (render-static-markup (template "Hello, World!" (main-page req))))
  (GET "/friends" [] (render-static-markup (template "No friends, yet :(" (friends-page)))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (run-jetty (wrap-defaults app site-defaults) {:port (:port env)}))