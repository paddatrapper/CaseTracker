CaseTracker
===========
[![Build Status](https://travis-ci.org/paddatrapper/CaseTracker.svg?branch=master)](https://travis-ci.org/paddatrapper/CaseTracker)
[![Coverage Status](https://coveralls.io/repos/paddatrapper/CaseTracker/badge.svg?branch=master&service=github)](https://coveralls.io/github/paddatrapper/CaseTracker?branch=master)
[![License](https://img.shields.io/badge/license-GPLv3-blue.svg)](LICENSE.md)

CaseTracker is a software system designed to help manage animal cruelty cases.

This is a re-work of the initial development so as to follow [Domain Driven
Design](http://en.wikipedia.org/wiki/Domain-driven_design) and [Clean Code (PDF)](http://www.planetgeek.ch/wp-content/uploads/2013/06/Clean-Code-V2.2.pdf)
principles.

Status
------

Currently CaseTracker is under active development.

Usage
-----

CaseTracker consists of two applications - namely the client and the server.
Most domain logic happens on the client side of the connection with the server
focusing on interfacing with the persistence system (a MySQL database). They
both Java JDK 8 or higher. The client also depends on JavaFX, which in OpenJDK
is not bundled in.

Maven is being used to manage the project life cycle. This includes compiling,
packaging, testing and documentation. All maven commands can be run from the
root of the project or in the client or server directories for specific client
or server tests, etc.

On Debian based systems, the dependencies can be installed by the following
command:

    $ sudo apt install openjdk-8-jdk openjfx maven

The development scripts assume that the MySQL server is running on the localhost
with the username `CaseTracker` and the password `casetracker`. The database is
expected to be called `CaseTracker`. These can be changed in [server/config.properties](server/config.properties).
To set this database up, run the following commands:

    $ sudo apt install default-mysql-server
    $ sudo mysql_secure_installation
    $ sudo mysql < scripts/setup-database.sql
    $ echo "CREATE USER 'CaseTracker'@'localhost' IDENTIFIED BY 'casetracker'" | sudo mysql
    $ echo "GRANT ALL ON CaseTracker.* TO 'CaseTracker'@'localhost'" | sudo mysql

Change username, password and table details according to the configuration you
are using. `sudo mysql` is how root connects to the MySQL/MariaDB server by
default on Debian Stretch/Ubuntu 15.10 onwards. For more information see the
[MariaDB docs](https://mariadb.com/kb/en/library/authentication-plugin-unix-socket/)

Running can be done through [scripts/run.sh](scripts/run.sh):

    $ ./scripts/run.sh run

Contributing
-----------

* Create an issue for a [feature](https://github.com/paddatrapper/CaseTracker/issues/new?labels%5B%5D=enhancement)
  you want to see in CaseTracker.
* File a [bug](https://github.com/paddatrapper/CaseTracker/issues/new?labels%5B%5D=bug)
  you have found.

See [CONTRIBUTING.md](CONTRIBUTING.md) for details about developing CaseTracker
