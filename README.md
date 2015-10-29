CaseTracker
===========
[![Build Status](https://travis-ci.org/paddatrapper/CaseTracker.svg?branch=master)](https://travis-ci.org/paddatrapper/CaseTracker)
[![Coverage Status](https://coveralls.io/repos/paddatrapper/CaseTracker/badge.svg?branch=master&service=github)](https://coveralls.io/github/paddatrapper/CaseTracker?branch=master)
[![License](https://img.shields.io/badge/license-GPLv3-blue.svg)](LICENSE.md)

CaseTracker is a software system designed to help manage cases opened by the
National Society for the Prevention of Cruelty to Animals (NSPCA) in South 
Africa.

This is a re-work of the initial development so as to follow [Domain Driven
Design](http://en.wikipedia.org/wiki/Domain-driven_design) and [Clean Code](http://www.planetgeek.ch/wp-content/uploads/2013/06/Clean-Code-V2.2.pdf) 
principles.

Status
------

Currently CaseTracker is under active development and is in alpha stage.

Usage
-----

CaseTracker consists of two applications - namely the client and the server. 
Most domain logic happens on the client side of the connection with the server
focusing on interfacing with the persistence system (a MySQL database).

Maven is being used to manage the project life cycle. This includes compiling,
packaging, testing and documentation. All maven commands can be run from the 
root of the project or in the client or server directories for specific client
or server tests, etc.

Running can be done through [scripts/project.sh](https://github.com/paddatrapper/CaseTracker/blob/master/scripts/project.sh):

    scripts/project.sh run

The project documentation can be viewed on the [CaseTracker website](http://kritsit.ddns.net/casetracker).

Contributing
-----------

* Create an issue for a [feature](https://github.com/paddatrapper/CaseTracker/issues/new?labels%5B%5D=enhancement) 
  you want to see in CaseTracker. 
* File a [bug](https://github.com/paddatrapper/CaseTracker/issues/new?labels%5B%5D=bug) 
  you have found.

See [CONTRIBUTING.md](CONTRIBUTING.md) for details about developing CaseTracker
