# impressive

Simplifies writing impress.js presentations with Clojure and hiccup. Goal: to reduce some of
the boilerplate and swap Clojure for HTML.

Further ideas and additional feature suggestions most welcome.

## Usage

The library only has two functions, `impress` and `step`, which correspond to the
entire HTML page and individual slides, respectively.

A simple presentation might look like this:

```clojure
(ns presentation.index
  (:use [impressive.core :only [impress step]]))
  
(def slides
  
  (impress
   "My Big Presentation"

   (step :intro {:x -1000 :y -1000 :class "slide"}

         [:h1 "Welcome to the show"]
         [:p "Sit back and relax"])

   (step :swirl {:x 0 :y -1000 :rotate 480 :class "slide"}

         [:p "Did you just see that?"])

   (step {:x 1000 :y -1000 :scale 4 :class ["slide" "green"]}

         [:div
          [:ul
           [:li "Learn Clojure"]
           [:li "Write Lisp"]
		   [:li "..."]
           [:li [:em "Profit"]]]])

   (step :big {:x 0 :y 0 :scale 2}

         [:img {:src "img/funnymeme.jpg"}])
   
   (step :overview {:x 0 :y 0 :scale 10})))
```

The `impress` function assumes a standardized structure with js/impress.js and css/style.css.
See docstrings for `impress` and `step` for more information.

Once the slides have been written, compilation happens as soon as the namespace is loaded
at the REPL and the var `slides` is evaluated:

```clojure
user=> (use 'presentation.index)
nil
user=> (spit "index.html" slides)
nil
```

## License

Copyright © 2012 Murphy McMahon

Distributed under the Eclipse Public License, the same as Clojure.
