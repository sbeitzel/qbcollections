# qbcollections
Occasionally, in my work, I find I require a collection that acts like a Set (it doesn't allow duplicate elements, and
checking to see if a given element is already in the set needs to be cheap) but in other circumstances I want
that same collection to have the properties of a List (stable traversal order, maybe quick random access into the middle,
or cheap to add and remove elements at a consistent location -- usually the head or tail). I went to the trouble of
writing the ListSet class and implementing it with both a LinkedList and with an ArrayList. I'm releasing this work
to the public because it's stupid to have to write this stuff over and over again.