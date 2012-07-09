(ns impressive.core
  (:use [hiccup.core :only [html]]
        [hiccup.page :only [html5]]))

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
:class attributes, and the slide content as hiccup vectors.

Example:
(step :slide-id {:x 1000 :y 0 :rotate 180 :scale 2 :class [\"slide\" \"dark\"]}
      [:h1 \"This is my slide\"])"
  [& args]
  (let [first-arg                          (first args)
        has-id?                            (or (string? first-arg) (keyword? first-arg))
        id                                 (if has-id? (name first-arg))
        {:keys [x y z scale rotate class]} (if has-id? (second args) first-arg)
        class                              (if (string? class) (vector class) class)
        classes                            (apply str (interpose " " (list* "step" class)))
        attrs                              (into {} (filter
                                                     #(not (nil? (val %)))
                                                     {:id id, :class classes, :data-x x
                                                      :data-y y, :data-z z, :data-scale scale
                                                      :data-rotate rotate}))
        content                            (nthnext args (or (and has-id? 2) 1))]

    (html
     [:div attrs content])))



