(ns reagent-material-ui-1.handler
  (:require [compojure.core :refer [GET defroutes]]
            [compojure.route :refer [not-found resources]]
            [hiccup.page :refer [include-js include-css html5]]
            [reagent-material-ui-1.middleware :refer [wrap-middleware]]
            [config.core :refer [env]]))

(def mount-target
  [:div#app
      [:h3 "Loading content!"]
      [:p "please be patient "]])

(defn head []
  [:head
   [:meta {:charset "utf-8"}]
   [:meta {:name "viewport"
           :content "width=device-width, initial-scale=1"}]
   (include-css (if (env :dev) "/css/site.css" "/css/site.min.css")
                "https://fonts.googleapis.com/css?family=Roboto+Condensed:300,400,500")])

(defn loading-page []
  (html5
    (head)
    [:body {:class "body-container"}
     mount-target
     (include-js "/js/app.js")]))


(defroutes routes
  (GET "/" [] (loading-page))
  (GET "/about" [] (loading-page))
  (GET "/examples/app-bar" [] (loading-page))
  (GET "/examples/avatar" [] (loading-page))
  (GET "/examples/drag-drop" [] (loading-page))
  (GET "/examples/pickers" [] (loading-page))
  (GET "/examples/tab" [] (loading-page))
  (GET "/examples/table" [] (loading-page))
  (GET "/examples/text-field" [] (loading-page))
  
  (resources "/")
  (not-found "Not Found"))

(def app (wrap-middleware #'routes))
