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
keyword id (optional), a map (optional) with any of :x, :y, :z, :scale, :rotate, and
:class attributes, plus any additional arbitrary (data) attributes to pass,
and the slide content as hiccup vectors.

Example:
(step :id-of-slide {:x 0 :y 0 :z 0 :scale 4 :rotate 270 :class \"green\"}
      [:h1 \"Something\"])"
  [& args]
  (let [id      (first (filter #(or (keyword? %) (string? %)) args))
        opts    (merge {:x 0 :y 0} (first (filter map? args)))
        content (first (filter vector? args))
        class   (cond (vector? (:class opts)) (:class opts)
                      (string? (:class opts)) (vector (:class opts)))
        classes (apply str (interpose " " (list* "step" class)))
        attrs   (rename-keys opts {:x :data-x, :y :data-y,
                                   :z :data-z, :scale :data-scale,
                                   :rotate :data-rotate})
        attrs   (into {} (remove nil? (merge attrs {:id id :class classes})))]
    (html
     [:div attrs content])))
