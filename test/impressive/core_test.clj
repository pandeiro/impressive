(ns impressive.core-test
  (:use clojure.test impressive.core))

(def hello-html
  "<div class=\"step\" data-x=\"0\" data-y=\"0\"><h1>Hello</h1></div>")

(def hello-html-with-id
  "<div class=\"step\" data-x=\"0\" data-y=\"0\" id=\"some-id\"><h1>Hello</h1></div>")

(deftest step-one-param
  (testing "step with just content"
    (is (= hello-html
           (step [:h1 "Hello"])))))

(deftest step-two-params-opts
  (testing "step with opts and content"
    (is (= hello-html
           (step {:x 0 :y 0} [:h1 "Hello"])))))

(deftest step-two-params-id
  (testing "step with id and content"
    (is (= hello-html-with-id
           (step :some-id [:h1 "Hello"])))))