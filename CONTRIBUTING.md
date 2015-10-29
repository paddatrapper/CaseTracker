Development
-----------

New developers are welcome. The process to follow when contributing is:

1. Fork CaseTracker.
1. Create an issue for the [feature](https://github/com/paddatrapper/CaseTracker/issues/new?labels%5B%5D=enhancement,help%20wanted) 
   you are adding or [bug](https://github/com/paddatrapper/CaseTracker/issues/new?labels%5B%5D=bug,help%20wanted) you are fixing.
1. Create a branch:

    `git checkout -b <branch-name>`

1. CaseTracker follows [Test Driven Development](http://en.wikipedia.org/wiki/Test-driven_development),
   so ensure that all pull requests include test cases for all elements of the
   feature or fix.
1. Create a pull request to the CaseTracker master repository. The description
   should include what implements and the reasons behind why you did it the way
   you did. Do not include how you did it - your code should explain this for
   you.

Pull Requests should be made early in the development process, even before any 
code has been committed if necessary. This can be done using:

    `git commit --allow-empty`

Coding Style
------------

* Spaces, not tabs.
* Indents are four spaces.
* Everything else follows [Google Java Style](https://google-styleguide.googlecode.com/svn/trunk/javaguide.html).
* If there are any grey areas create an issue for it and it can be discussed.
