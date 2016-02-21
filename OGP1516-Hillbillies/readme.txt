What to do if you get compile errors in the provided code?
==========================================================
Most likely, some class cannot be found. First, read the error carefully.

* If the error is related to a class you should write, then do that first and make sure it is in the correct folder/package:
  - Facade (implements IFacade) in src/hillbillies/part1/facade/Facade.java
  - Unit in src/hillbillies/model/Unit.java

* If the error is related to the TextInputDialog or Spinner class from JavaFX:
  These classes are only available in JavaFX version 8u40 and higher.
  You probably have an older JRE/SDK; install the most recent one from
    http://www.oracle.com/technetwork/java/javase/downloads/index.html.
  To configure it in eclipse:
  1. Go to Eclipse preferences > Java > Installed JREs,
     and make sure a JRE >= 1.8.0_40 is available. If not, search or add manually.
  2. Go to Eclipse preferences > Java > Installed JREs > Execution environments,
     and make sure the most recent JRE is selected for the JavaSE-1.8 environment

* If the error is related to access restrictions for any JavaFX class:
  You can try 3 things:
  1. Use the pre-configured project from Toledo, and import it according to the instructions.
     Everything is set up as it should be.
  2. Go to Project properties > Java compiler > Errors/warnings, and in the
     Deprecated and restricted API settings, set both Forbidden and Discouraged reference (access rules) to 'Ignore'.
  3. Configure the build path of the project, and in the Libraries tab, expand the JRE System Library.
     Edit the access rules, and add an Accessible rule for the pattern javafx/**