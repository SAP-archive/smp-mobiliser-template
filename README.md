[![REUSE status](https://api.reuse.software/badge/github.com/SAP-samples/smp-mobiliser-template)](https://api.reuse.software/info/github.com/SAP-samples/smp-mobiliser-template)
# Mobiliser Customisation Template

This is the official customisation template for building server-side
customisations on the Mobiliser Platform, version 5.6.11 (SMP 3.1 SP0 PL6).

## Prerequisites

JDK = 1.8
Maven >= 3.6.3

## Versioning

This customisation template is always tailored to a specific release of the SAP
Mobile Platform. Each release with be tracked by a git branch so before
beginning your customisation, checkout the matching branch.

## Getting Started

Obtain and install your copy of the SAP Mobile Platform, version 3.1 SP0 PL6.
Mobiliser is packaged as an optional feature inside SMP. To begin work on your
customisation, you need to install the mobiliser WAR file into your local maven
repository so it can be used as a dependency of this project.

If the mobiliser feature is active in your SMP, you'll find the mobiliser WAR
file in $SMP\_HOME/pickup. You can also extract the WAR file from the mobiliser
feature without activating it. This can be done like this:

        jar xf $SMP_HOME/p2/com.sap.mobile.platform.server.repository/binary/com.sap.mobile.platform.server.build.feature.mobiliser_root_* pickup/mobiliser.war && mv pickup/mobiliser.war . && rm -r pickup


Once you have located the war file, install it into your local repository.

        mvn install:install-file -Dfile=mobiliser.war -DgroupId=com.sap.mobile.platform.server.appservices.money.vanilla  -DartifactId=com.sybase365.mobiliser.vanilla.war -Dversion=5.6.11 -Dpackaging=war

### Reporting Mobiliser

If you want to include Reporting Mobiliser in your customisation, download it
from the software centre and install it like this:

        mvn install:install-file -Dfile=com.sybase365.mobiliser.dist.full-5.6.11-reporting.zip -DgroupId=com.sap.mobile.platform.server.appservices.money.dist  -DartifactId=com.sybase365.mobiliser.dist.full -Dversion=5.6.11 -Dpackaging=zip -Dclassifier=reporting

Then you will need to uncomment the section for reporting mobiliser in the
dist/pom.xml to have it included in your final WAR.

### Web-UI

If the mobiliser portal feature is active your SMP, you'll find the portal WAR
file in $SMP\_HOME/pickup. You can also extract the WAR file from the portal
feature without activating it. This can be done like this:

        jar xf $SMP_HOME/p2/com.sap.mobile.platform.server.repository/binary/com.sap.mobile.platform.server.build.feature.mobiliser.web.portal_root_* pickup/portal.war && mv pickup/portal.war . && rm -r pickup

Once you have the war file, install it into your local repository.

        mvn install:install-file -Dfile=portal.war -DgroupId=com.sap.mobile.platform.server.appservices.money.web -DartifactId=com.sybase365.mobiliser.ui.web.application -Dversion=5.6.11 -Dpackaging=war

The example Web-UI sources are included in SMP in
$SMP\_HOME/extras/mobiliser/custom/com.sybase365.mobiliser.ui.web.application-5.6.11-project.zip.
You can extract these into your customisation template like this:

        jar xf $SMP_HOME/extras/mobiliser/custom/com.sybase365.mobiliser.ui.web.application-5.6.11-project.zip src

Run this from the web submodule to extract the src folder from the packaged zip
file. You can then add the web module to the list of modules to build in
pom.xml. Be sure to also set the property webui.disabled to false in pom.xml to
enable using the contents of portal.war as a repository and to deploy the
portal.war to jetty in the test submodule.

## Customising the template

### Database

Your customisation will likely target a single specific RDBMS. The customisation
template is preconfigured to build artefacts for all types of databases
supported by Mobiliser. You can remove the extra configuration from your
template to simplify it and avoid producing unused artefacts. We recommend
however to keep the derby configurations to allow for quick developer testing.

Look for the `<-- CUSTOMISATION POINT` blocks in dist/pom.xml and service/pom.xml
to remove unneeded database specific configuration.

### Package Naming

The template uses the package name com.sybase365.mobiliser.custom.project
throughout. You should change this to something relevant for your project.

To ease this process, there is a script included, `rename_customisation.sh`, which
can automatically rename the packages and artefacts in the project. Typical
usage would be:

        ./rename_customisation.sh com.github.sap.mobiliser.template

### Integration Tests

The customisation project will build derby database scripts and prepare a derby
database to use for integration tests. There is a sample test for blacklisting
in the test submodule which uses Apache JMeter to run the tests. You can start
this test by running your build with the profile 'it'. The mobiliser war will be
deployed onto an jetty server, the jmeter tests will be run and then the server
will be stopped again.

        mvn clean install -Pit

If you want to bring up mobiliser to do some manual testing, you can do this by
running the following command in the test module:

        mvn jetty:run-war -Djetty.port=8080 -Ddaemon=false

Like with the 'it' profile, mobiliser will be deployed onto a jetty server and
will use a derby database. If you have enabled the customisation of the
Web-UI, it will also be deployed to jetty.

If you are testing the example weather service and you need to use a proxy
server, you need to pass along the environment variables when you invoke the
jetty plugin:

        mvn jetty:run-war -Djetty.port=8080 -Ddaemon=false -Dhttp.proxyHost=myproxy -Dhttp.proxyPort=8080 -Dhttps.proxyHost=myproxy -Dhttps.proxyPort=8080

### JMS Integration with Brand Mobiliser

If you want to build your mobiliser.war with preconfigured JMS bundles for
integration with Brand mobiliser, find the two `<-- CUSTOMISATION POINT` blocks
in dist/pom.xml mentioning JMS and uncomment the relevant sections to activate
packaging the JMS bundles.

## Getting Help

Documentation for installation, administration, and developers can be found
[here](http://help.sap.com/mobile-platform/).

The developer documentation can be found under Partner Documentation (and then:
SAP Mobile -> Sybase Mobiliser Platform). Access to these documents is
restricted and only available to SAP Partners.

To get started with development for Mobiliser Platform, please consult the
document "Mobiliser Platform Framework Development Guide".

The Lab-sessions (http://scn.sap.com/docs/DOC-40367) provide a step-by-step
guide for some of the most common tasks in Mobiliser.

## Contributing

Pull requests are always welcome if you find a bug or problems with the
template. We are happy to consider adding new example customisations to the
template if you think they might be of use to other customisation teams. If you
are unsure, open a pull request with your changes and a discussion can take
place as to whether it makes sense to add it to the template.

### Developer Certificate of Origin (DCO)

Due to legal reasons, contributors will be asked to accept a DCO before they
submit the first pull request to this project. This happens in an automated
fashion during the submission process. SAP uses [the standard DCO text of the
Linux Foundation](https://developercertificate.org).
