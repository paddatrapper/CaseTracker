CaseTracker
===========
[![Build Status](https://travis-ci.org/paddatrapper/CaseTracker.svg?branch=master)](https://travis-ci.org/paddatrapper/CaseTracker)

CaseTracker is a software system designed to help manage cases opened by the
National Society for the Prevention of Cruelty to Animals (NSPCA) in South 
Africa.

This is a re-work of the initial development so as to follow [Domain Driven
Design](http://en.wikipedia.org/wiki/Domain-driven_design) and [Clean Code](https://www.ufm.edu/images/0/04/Clean_Code.pdf) 
principles.

Status
------

Currently CaseTracker is under active development and is in alpha stage.

Usage
-----

CaseTracker consists of two applications - namely the client and the server. 
Most domain logic happens on the client side of the connection with the server
focusing on interfacing with the persistence system (currently a MySQL 
database).

Maven is being used to manage the project life cycle. This includes compiling,
packaging, testing and documentation. All maven commands must be run from
within the client or server root directories.

To generate a runnable .jar file of the client or server:

    mvn package

To generate the documentation:

    mvn site

The project documentation can be viewed on the CaseTracker [client website](http://kritsit.ddns.net/casetracker/client/project-info.html)
and [sever website](http://kritsit.ddns.net/casetracker/server/project-info.html).

Development
-----------

New developers are welcome. The process to follow when contributing is:

1. Fork CaseTracker using the button on the top right.
1. Create an issue for the feature you are adding or bug you are fixing.
1. Create a branch:

    `git checkout -b <branch-name>`

1. CaseTracker follows [Test Driven Development](en.wikipedia.org/wiki/Test-driven_development),
   so ensure that all pull requests include test cases for all elements of the
   feature or fix.
1. Create a pull request to the CaseTracker master repository. The description
   should include what implements and the reasons behind why you did it the way
   you did. Do not include how you did it - your code should explain this for
   you.

Coding Style
------------

* Indents are four spaces.
* Spaces, not tabs.
* Everything else follows [Google Java Style](https://google-styleguide.googlecode.com/svn/trunk/javaguide.html).
* If there are any grey areas create an issue for it and it can be discussed.
