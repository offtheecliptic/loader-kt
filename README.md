# Library Loader

## Introduction

This is a simple library fdemonstrating the use of post-delegation classloaders for isolating plugins from their host components.

## Motivation

There are plenty of examples for Java, but few for Kotlin.  This means to fill that gap.

## Design Philosophy

Everything is interface-based. So the code is organized into pairs of files for interfaces and implementations.

## Overview of Code Organization

 
## Files

## Architecture



### Core Data Model

### Operations Module


## Usage

Maven coordinates: [offtheecliptic/loader 0.0.1]

### Import Statements

## Concepts

These are the key concepts the library deals with.

* **Class Loading**  - Loading a class from a class path.

* **Instantiation**  - Creating an instance of a class.

* **Initialization** - Initializing a newly-instantiated class instance so it can be used. 

### Special Considerations


## Examples

### Example 1 - 



### Example 2 - 


### Example 3 - 
                     
### Example 4 - 

### Example 5 - 

_________________________________________________________________________________________________________________
## Library Developers - Basics

### Maven 

To compile:
   mvn clean
   mvn compile
   mvn package

## Test

### Maven 

You can run the test cases using Maven.

To run, can do either of these:

java  -jar target/clocca-0.2.0-jar-with-dependencies.jar

kotlin -cp target/clocca-0.2.0-jar-with-dependencies.jar ....

_________________________________________________________________________________________________________________

_________________________________________________________________________________________________________________
## Test Cases


### Imports

#### Simple Test Cases


#### Mapping Test Cases


_________________________________________________________________________________________________________________
## Contributions


_________________________________________________________________________________________________________________
## License

Copyright © 2021-2022 OffTheEcliptic

This program and the accompanying materials are made available under the terms of the Eclipse Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary Licenses when the conditions for such availability set forth in the Eclipse Public License, v. 2.0 are satisfied: GNU General Public License as published by the Free Software Foundation, either version 2 of the License, or (at your option) any later version, with the GNU Classpath Exception which is available at https://www.gnu.org/software/classpath/license.html.

