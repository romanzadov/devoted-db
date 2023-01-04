# In Memory Database Exercise

### How to run
1. Download `database.jar` from the root directory
2. Navigate to it locally and run `java -jar database.jar`

### Design choices
I broke the work into two parts - designing the basic commands, and designing the transactions. 

For the basic commands, I needed a data structure that would beat `O(log(n))` speeds for 
`SET`, `GET`, and `DELETE`. `HashMap` has the average time complexity of `O(1)` for these 
operations, though a worst-case complexity of `O(n)`. If we're expecting more worst-case 
scenarios, I could switch this to a `B-tree`, which has `O(log(n))` for both the average and 
worst cases.

`Count` needed a separate map to avoid counting the values each time.

I thought about two ways transactions could work. The first was having a stack of inverse commands,
and the second was storing a set of updated results alongside the existing store. One of the problem 
requirements was not doubling the memory with a transaction, so I couldn't just save an updated copy of the
store, but storing a set of updated rows is probably reasonable. In the end, it seemed like a more complex 
approach, so I went with storing the inverse of the commands, given the limited complexity of the commands,
but if this was to be productionized, I would definitely try to make it work the other way.

### TODOs
1. Sanitize inputs
2. Serve user-readable errors