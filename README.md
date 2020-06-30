# GGSearchApp
Google Custom Search Implementation (MVVM+DataBinding+RxKotlin+Room)

Data retrieval is divided in 2 threads, 15 results from each, taking into consideration the limitations of GCustom Search:
- max 10 results per page
- not 0(zero)-based pages, but 1-based
- obligatory 'cx'(GCustom Search Engine key) parameter

+ On screen start last successfully performed request data is acquired.
+ The cancellation of search is achievable when in progress.
+ For test purposes the tablet layout, similar to the smartphone one, was added.
