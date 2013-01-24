(ns impressive.core
  (:use [hiccup.core :only [html]]
        [hiccup.page :only [html5]])
  (:require [clojure.set :refer [rename-keys]]))

(defn impress
  "Creates an HTML5 skeleton for an impress.js project, assuming the project
uses the following structure:

|-index.html
|-js
|  `-impress.js
`-css
   `-style.css

"
  [title & content]
  (html5
   [:head
    [:meta {:charset "utf-8"}]
    [:meta {:name "viewport" :content "width=1024"}]
    [:meta {:name "apple-mobile-web-app-capable" :content "yes"}]
    [:title title]
    [:link {:href "css/style.css" :rel "stylesheet"}]]
   [:body.impress-not-supported
    [:div#impress content]
    [:script {:src "js/impress.js"}]
    [:script "impress().init();"]]))

(defn step
  "Returns an impress.js slide (<div class=\"step\">) in html. Arguments: a string or
keyword id (optional), a map with :x, :y and optional :z, :scale, :rotate, and
:class attributes, plus any additional arbitrary (data) attributes to pass,
and the slide content as hiccup vectors.

Example:
(step :slide-id {:x 1000 :y 0 :rotate 180 :scale 2 :class [\"slide\" \"dark\"]}
      [:h1 \"This is my slide\"])"
  [& args]
  (let [first-arg  (first args)
        has-id?    (or (string? first-arg) (keyword? first-arg))
        id         (if has-id? (name first-arg))
        no-opts?   (or (and has-id? (vector? (second args)) (vector? (first args))))
        opts-map   (merge {:x 0 :y 0} (or (when no-opts? nil)
                                          (and has-id? (second args))
                                          (first args)))
        class      (if (string? (:class opts-map))
                     (vector (:class opts-map))
                     (:class opts-map))
        classes    (apply str (interpose " " (list* "step" class)))
        attrs      (rename-keys opts-map {:x :data-x, :y :data-y,
                                          :z :data-z, :scale :data-scale,
                                          :rotate :data-rotate})
        attrs      (into {} (remove nil? (merge attrs {:id id :class classes})))
        content    (nthnext args (or (and has-id? 2) 1))]
    (html
     [:div attrs content])))



