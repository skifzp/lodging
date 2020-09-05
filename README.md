## Java autotesting web app

#### This repository contains some part of positive testing web app for rent apartments:
-	CRU contact
-	CRU group
-	Join/Unjoin contact to group

#### The tests have:
*	caching and pre-checks
*	fluent interface
*	hashing results that take a long time to get
*	launch with parameters (browser, switched testing and etc.)
*	changing test environment with help configuration files (local, remote and etc.)
*	screenshots in case failed test

#### Used technologies:
-	Gradle
-	TestNG
-	Selenium
-	LogBack
-	Hibernate
-	GSON
-	AllureReport

![architecture scheme](https://github.com/skifzp/lodging/blob/master/architecture.jpg)

#### Please pay attention:
`In this demo project I has used ThreadLocal to create object AppManager only to show how we can launch test suites in multithread mode. Here it is no correct to use multithread`
