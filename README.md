# javelin-deref-demo
This is a small single page web app to demo a problem a had whilst using javelin with clojurescript.

## The problem
I found that when I called the view element of any of my pages all of my formula cells would stop working and only return `javelin.core/none`.

This was happing in the routes file `javelin-deref-demo.route`. I was resetting the cell which holds the view inside a `dosync`. This wouldn't be a problem but I was also calling my view element in the `dosync`. This stops the propergation of the formula cells so they don't even get a chance to set there initial value so will return `javelin.core/none`.

Example of the broken part:

    (dosync (reset! view (page-view))
            (reset! page-title "title"))
    
Working examples:  
Get rid of dosync and reset each cell seperately

    (reset! view (page-view))
    (reset! page-title "title")

Pre-proccess the view

    (let [v (page-view)]
      (dosync (reset! view v)
              (reset! page-title "title")))

## Running
To run the demo please clone the repo and execute:

    boot dev
This will build the project and run a local server on port 8000.  

Navigate to:  

    http://localhost:8000
